package net.menthor.ontouml.map

import net.menthor.ontouml.*
import net.menthor.ontouml.stereotypes.PrimitiveStereotype
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum
import org.eclipse.emf.ecore.EEnumLiteral
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.BasicExtendedMetaData
import org.eclipse.emf.ecore.util.ExtendedMetaData
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl
import sun.security.krb5.internal.crypto.EType

class EcoreTargetMapper implements EMFTargetMapper {

    static EcoreFactory theCoreFactory = EcoreFactory.eINSTANCE
    static EcorePackage theCorePackage = EcorePackage.eINSTANCE

    boolean ignorePackages = false

    Object toEcore(Model ontomodel, boolean ignorePackage) {
        this.ignorePackages = ignorePackage
        return from(ontomodel)
    }

    Object toEcore(Model ontomodel) {
        ignorePackages = false
        return from(ontomodel)
    }

    Resource serialize (EPackage ecoremodel, String ecorepath){
        ResourceSet ecoreResourceSet = new ResourceSetImpl()
        URI ecoreURI = URI.createFileURI(ecorepath)
        ecoreResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMLResourceFactoryImpl())
        ecoreResourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE)
        // enable extended metadata
        final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(ecoreResourceSet.getPackageRegistry())
        ecoreResourceSet.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData)
        Resource resource = ecoreResourceSet.createResource(ecoreURI)
        resource.getContents().add(ecoremodel)
        try{
            resource.save(Collections.emptyMap())
        }catch(IOException e){
            e.printStackTrace()
        }
        return resource;
    }

    @Override
    Object cloneModel(Model ontomodel) {
        def ecoreRootModel = theCoreFactory.createEPackage()
        ecoreRootModel.setName(ontomodel.getName())
        tgtPackagesMap.put(ontomodel, ecoreRootModel)
        ecoreRootModel.setNsPrefix(ecoreRootModel.getName().toLowerCase().replace(" ","-"));
        ecoreRootModel.setNsURI("http://menthor.net/"+ecoreRootModel.getName().toLowerCase().replace(" ","-")+"/");
        return ecoreRootModel
    }

    @Override
    Object clonePackage(Package ontopackage, Object tgtParentPackage) {
        EPackage ecorepackage = theCoreFactory.createEPackage()
        ecorepackage.setName(ontopackage.getName())
        if (!ignorePackages) (tgtParentPackage as EPackage).getESubpackages().add(ecorepackage)
        return ecorepackage
    }

    @Override
    Object cloneClass(Class ontoclass) {
        EClass ecoreclass = theCoreFactory.createEClass()
        ecoreclass.setAbstract(ontoclass.isAbstract_())
        ecoreclass.setName(ontoclass.getName())
        def ontopack = tgtPackagesMap.get(ontoclass.getContainer())
        if (ignorePackages) (tgtModel as EPackage).getEClassifiers().add(ecoreclass)
        else (ontopack as EPackage).getEClassifiers().add(ecoreclass)
        return ecoreclass
    }

    Object cloneEnumeration(DataType ontodatatype){
        EEnum ecoreEnum = theCoreFactory.createEEnum()
        ecoreEnum.setName(ontodatatype.getName())
        for (Literal lit : ontodatatype.getLiterals()) {
            EEnumLiteral elit = theCoreFactory.createEEnumLiteral();
            elit.setName(lit.getText());
            ecoreEnum.getELiterals().add(elit);
        }
        def ontopack = tgtPackagesMap.get(ontodatatype.getContainer())
        if (ignorePackages) (tgtModel as EPackage).getEClassifiers().add(ecoreEnum);
        else (ontopack as EPackage).getEClassifiers().add(ecoreEnum);
        return ecoreEnum
    }

    @Override
    Object cloneDataType(DataType ontodatatype) {
        if(ontodatatype.isEnumeration()){
            return cloneEnumeration(ontodatatype)
        }
        EDataType ecoredt = theCoreFactory.createEDataType()
        ecoredt.setName(ontodatatype.getName())
        ecoredt.setSerializable(true)
        def ontopack = tgtPackagesMap.get(ontodatatype.getContainer())
        if (ignorePackages) (tgtModel as EPackage).getEClassifiers().add(ecoredt);
        else (ontopack as EPackage).getEClassifiers().add(ecoredt);
        return ecoredt
    }

    @Override
    Object cloneRelationship(Relationship ontorel) {
        if(ontorel.isDerivation()) return null
        EClassifier esource = tgtClassMap.get(ontorel.source())
        if(esource==null) esource= tgtDataTypeMap.get(ontorel.source())
        if(esource==null) esource= tgtRelationshipsMap.get(ontorel.source())
        EClassifier etarget = tgtClassMap.get(ontorel.target())
        if(etarget==null) etarget= tgtDataTypeMap.get(ontorel.target())
        if(etarget==null) etarget= tgtRelationshipsMap.get(ontorel.target())
        List<EReference> ereferenceList = []
        EReference eRef = null
        ontorel.getEndPoints().each{ ep ->
            eRef = theCoreFactory.createEReference()
            eRef.setLowerBound(ep.getLowerBound())
            eRef.setUpperBound(ep.getUpperBound())
            EClassifier etype = tgtClassMap.get(ep.getClassifier())
            if(etype==null) etype = tgtDataTypeMap.get(ep.getClassifier())
            if(etype==null) etype = tgtRelationshipsMap.get(ep.getClassifier())
            eRef.setEType(etype)
            if(ep.getName()==null || ep.getName().isEmpty()) eRef.setName(eRef.getEType().getName().toLowerCase())
            else eRef.setName(ep.getName())
            eRef.setDerived(ep.isDerived())
            if (ontorel.isMeronymic() && ep.equals(ontorel.sourceEndPoint())){
                eRef.setContainment(true)
            }
            ereferenceList.add(eRef)
            //store endpoint in the mapping
            tgtEndpointsMap.put(ep, eRef)
        }
        // adding to owner class
        if(etarget instanceof EClass) (etarget as EClass).getEStructuralFeatures().add(ereferenceList.get(0))
        if(esource instanceof EClass) (esource as EClass).getEStructuralFeatures().add(ereferenceList.get(1))
        // setting EOpposites
        ((EReference)ereferenceList.get(1)).setEOpposite(ereferenceList.get(0))
        ((EReference)ereferenceList.get(0)).setEOpposite(ereferenceList.get(1))
        return ereferenceList
    }

    EType getEPrimitiveType(Attribute attr){
        PrimitiveStereotype pt = (PrimitiveStereotype)attr.getStereotype()
        EType etype=null
        if (pt == PrimitiveStereotype.INTEGER) { etype = EcorePackage.eINSTANCE.getEInt() }
        else if (pt == PrimitiveStereotype.BOOLEAN) { etype = EcorePackage.eINSTANCE.getEBoolean() }
        else if (pt == PrimitiveStereotype.REAL) { etype = EcorePackage.eINSTANCE.getEDouble() }
        else if (pt == PrimitiveStereotype.STRING) { etype = EcorePackage.eINSTANCE.getEString() }
        else if (pt == PrimitiveStereotype.DATE) { etype = EcorePackage.eINSTANCE.getEDate() }
        else if (pt == PrimitiveStereotype.DATE_TIME) { etype = EcorePackage.eINSTANCE.getEDate() }
        return etype
    }

    @Override
    Object cloneAttribute(Attribute attr) {
        EStructuralFeature eSF = theCoreFactory.createEAttribute()
        def eOwner = tgtClassMap.get(attr.getOwner())
        if(eOwner instanceof EClass) {
            EClass eclass = tgtClassMap.get(attr.getOwner())
            eclass.getEStructuralFeatures().add(eSF)
        }
        eSF.setName(attr.getName())
        eSF.setDerived(attr.isDerived())
        eSF.setEType(getEPrimitiveType(attr))
        if(attr.getName()==null || attr.getName().isEmpty()) eSF.setName(eSF.getEType().getName().toLowerCase())
        else eSF.setName(attr.getName())
        return eSF
    }

    @Override
    Object cloneEndPoint(EndPoint ep) {
        return null //they were cloned along the relationships...
    }

    @Override
    def cloneSubsetsAndRedefines(EndPoint ep) {
        return null //they can not be represented in ecore
    }

    @Override
    Object cloneGeneralization(Generalization g) {
        EClass egeneral = null
        egeneral = tgtClassMap.get(g.getGeneral())
        if(egeneral==null) egeneral = tgtDataTypeMap.get(g.getGeneral())
        if(egeneral==null) egeneral = tgtRelationshipsMap.get(g.getGeneral())
        EClass especific = null
        especific = tgtClassMap.get(g.getSpecific())
        if(especific==null) especific = tgtDataTypeMap.get(g.getSpecific())
        if(especific==null) especific = tgtRelationshipsMap.get(g.getSpecific())
         // add to specific super types
        especific.getESuperTypes().add(egeneral);
        return [egeneral, especific]
    }

    @Override
    Object cloneGeneralizationSet(GeneralizationSet gs) {
        return null //they can not be represented in ecore
    }
}
