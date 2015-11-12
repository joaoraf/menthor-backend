package net.menthor.ontouml.map

import RefOntoUML.RefOntoUMLFactory
import RefOntoUML.util.RefOntoUMLResourceFactoryImpl

import net.menthor.ontouml.Attribute
import net.menthor.ontouml.DataType
import net.menthor.ontouml.EndPoint
import net.menthor.ontouml.Generalization
import net.menthor.ontouml.GeneralizationSet
import net.menthor.ontouml.Model
import net.menthor.ontouml.Class
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.traits.Classifier
import net.menthor.ontouml.traits.Container

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI;

class RefOntoMapper extends MapperFrom {

    static RefOntoUMLFactory factory = RefOntoUMLFactory.eINSTANCE
    RefOntoUML.Model refmodel

    Object toRefOntoUML(Model ontomodel) {
        return super.from(ontomodel)
    }

    def serialize(String path){
        ResourceSet rset = new ResourceSetImpl()
        rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("refontouml",new RefOntoUMLResourceFactoryImpl())
        rset.getPackageRegistry().put(RefOntoUML.RefOntoUMLPackage.eNS_URI,	RefOntoUML.RefOntoUMLPackage.eINSTANCE)
        URI fileURI = URI.createFileURI(path)
        final Resource resource = rset.createResource(fileURI)
        resource.getContents().add(refmodel)
        try{
            resource.save(Collections.emptyMap())
        }catch(IOException e){
            e.printStackTrace()
        }
        return resource
    }

    @Override
    Object cloneModel(Model ontomodel) {
        this.refmodel = factory.createModel()
        refmodel.setName(ontomodel.getName())
        return refmodel
    }

    @Override
    Object clonePackage(Container ontopackage) {
        String name = ontopackage.getName()
        RefOntoUML.Package refpack = factory.createPackage()
        refpack.setName(name)
        refmodel.getPackagedElement().add(refpack)
        return refpack
    }

    @Override
    Object cloneClass(Class ontoclass) {
        RefOntoUML.Class refClass=null
        if(ontoclass.isKind()) refClass = factory.createKind()
        else if(ontoclass.isSubKind()) refClass = factory.createSubKind()
        else if(ontoclass.isCollective()) { refClass = factory.createCollective(); refClass.setIsExtensional(ontoclass.isExtensional()) }
        else if(ontoclass.isQuantity()) refClass = factory.createQuantity()
        else if(ontoclass.isRole()) refClass = factory.createRole()
        else if(ontoclass.isPhase()) refClass = factory.createPhase()
        else if(ontoclass.isRelator()) refClass = factory.createRelator()
        else if(ontoclass.isMode()) refClass = factory.createMode()
        else if(ontoclass.isPerceivableQuality()) refClass = factory.createPerceivableQuality()
        else if(ontoclass.isNonPerceivableQuality()) refClass = factory.createNonPerceivableQuality()
        else if(ontoclass.isNominalQuality()) refClass = factory.createNominalQuality()
        else if(ontoclass.isCategory()) refClass = factory.createCategory()
        else if(ontoclass.isRoleMixin()) refClass = factory.createRoleMixin()
        else if(ontoclass.isPhaseMixin()) refClass = factory.createPhase()
        else if(ontoclass.isMixin()) refClass = factory.createMixin()
        else refClass = factory.createClass()
        if(refClass!=null) {
            String name = ontoclass.getName()
            boolean isAbstract = ontoclass.isAbstract_()
            refClass.setName(name)
            refClass.setIsAbstract(isAbstract)
            RefOntoUML.Package refpack = packagesMap.get(ontoclass.getContainer())
            refpack.getPackagedElement().add(refClass)
        }
        return refClass
    }

    @Override
    Object cloneDataType(DataType ontodatatype) {
        Object refdatatype=null
        if(ontodatatype.isDimension()){
            if(ontodatatype.isIntervalDecimal()) refdatatype = factory.createDecimalIntervalDimension()
            if(ontodatatype.isIntervalInteger()) refdatatype = factory.createIntegerIntervalDimension()
            if(ontodatatype.isRationalInteger()) refdatatype = factory.createIntegerRationalDimension()
            if(ontodatatype.isRationalDecimal()) refdatatype = factory.createDecimalRationalDimension()
            if(ontodatatype.isOrdinalDecimal()) refdatatype = factory.createDecimalOrdinalDimension()
            if(ontodatatype.isOrdinalInteger()) refdatatype = factory.createIntegerOrdinalDimension()
            if(ontodatatype.isNominalString()) refdatatype = factory.createStringNominalStructure()
            ((RefOntoUML.MeasurementDimension)refdatatype).setUnitOfMeasure(ontodatatype.getUnitOfMeasure())
        } else if(ontodatatype.isDomain()) {
            refdatatype = factory.createMeasurementDomain()
        } else if(ontodatatype.isEnumeration()){
            refdatatype = factory.createEnumeration()
            ontodatatype.getLiterals().each{ ontolit ->
                RefOntoUML.EnumerationLiteral lit = factory.createEnumerationLiteral()
                lit.setName(ontolit.getText())
                ((RefOntoUML.Enumeration)refdatatype).getOwnedLiteral().add(lit)
                lit.setEnumeration(((RefOntoUML.Enumeration)refdatatype))
            }
        } else if(ontodatatype.isDataType()) {
            refdatatype = factory.createDataType()
        } else {
            refdatatype = factory.createDataType()
        }
        if(refdatatype!=null) {
            String name = ontodatatype.getName()
            ((RefOntoUML.Type)refdatatype).setName(name)
            RefOntoUML.Package refpack = packagesMap.get(ontodatatype.getContainer())
            refpack.getPackagedElement().add(refdatatype);
        }
        return refdatatype
    }

    @Override
    Object cloneRelationship(Relationship ontorel) {
        RefOntoUML.Association refRel=null;
        if(ontorel.isCharacterization()) refRel = factory.createCharacterization();
        else if(ontorel.isMediation()) refRel = factory.createMediation();
        else if(ontorel.isDerivation()) refRel = factory.createDerivation();
        else if(ontorel.isFormal()) refRel = factory.createFormalAssociation();
        else if(ontorel.isMaterial()) refRel = factory.createMaterialAssociation();
        else if(ontorel.isComponentOf()) refRel = factory.createcomponentOf();
        else if(ontorel.isMemberOf()) refRel = factory.creatememberOf();
        else if(ontorel.isSubCollectionOf()) refRel = factory.createsubCollectionOf();
        else if(ontorel.isSubQuantityOf()) refRel = factory.createsubQuantityOf();
        else if(ontorel.isStructuration()) refRel = factory.createStructuration();
        else refRel = factory.createAssociation();
        if(refRel!=null){
            String name = ontorel.getName();
            refRel.setName(name);
            RefOntoUML.Package refpack = packagesMap.get(ontorel.getContainer())
            refpack.getPackagedElement().add(refRel);
        }
        return refRel
    }

    @Override
    Object cloneAttribute(Attribute attr) {
        int upper = attr.getUpperBound()
        int lower = attr.getLowerBound()
        boolean isDerived = attr.isDerived()
        boolean isDependency = attr.isDependency()
        String name = attr.getName()
        String primitiveName = attr.getStereotype().getName()
        RefOntoUML.Property refAttr=factory.createProperty()
        RefOntoUML.LiteralInteger lowerBound = factory.createLiteralInteger()
        RefOntoUML.LiteralUnlimitedNatural upperBound = factory.createLiteralUnlimitedNatural()
        lowerBound.setValue(lower)
        upperBound.setValue(upper)
        refAttr.setLowerValue(lowerBound)
        refAttr.setUpperValue(upperBound)
        refAttr.setIsDerived(isDerived)
        refAttr.setIsReadOnly(isDependency)
        refAttr.setName(name)
        RefOntoUML.PrimitiveType primitiveType = factory.createPrimitiveType()
        primitiveType.setName(primitiveName)
        refAttr.setType(primitiveType)
        RefOntoUML.Package refpack = packagesMap.get(attr.getOwner().getContainer())
        refpack.getPackagedElement().add(primitiveType)
        if(attr.getOwner() instanceof Class) {
            RefOntoUML.Type reftype = classMap.get(attr.getOwner())
            if(reftype!=null)((RefOntoUML.Class)reftype).getOwnedAttribute().add(refAttr)
        }
        if(attr.getOwner() instanceof DataType) {
            RefOntoUML.Type reftype = dataTypeMap.get(attr.getOwner())
            if(reftype!=null)((RefOntoUML.DataType)reftype).getOwnedAttribute().add(refAttr)
        }
        return refAttr
    }

    @Override
    Object cloneEndPoint(EndPoint ep) {
        int upper = ep.getUpperBound()
        int lower = ep.getLowerBound()
        boolean isDerived = ep.isDerived()
        boolean isDependency = ep.isDependency()
        String name = ep.getName()
        RefOntoUML.Association assoc = relationshipsMap.get(ep.getOwner())
        RefOntoUML.Property refEndPoint=factory.createProperty()
        RefOntoUML.LiteralInteger lowerBound = factory.createLiteralInteger()
        RefOntoUML.LiteralUnlimitedNatural upperBound = factory.createLiteralUnlimitedNatural()
        lowerBound.setValue(lower)
        upperBound.setValue(upper)
        refEndPoint.setLowerValue(lowerBound)
        refEndPoint.setUpperValue(upperBound)
        refEndPoint.setIsDerived(isDerived)
        refEndPoint.setIsReadOnly(isDependency)
        refEndPoint.setName(name)
        refEndPoint.setAssociation(assoc)
        assoc.getMemberEnd().add(refEndPoint)
        assoc.getOwnedEnd().add(refEndPoint)
        assoc.getNavigableOwnedEnd().add(refEndPoint)
        if(ep.getClassifier() instanceof Class) refEndPoint.setType(classMap.get(ep.getClassifier()))
        if(ep.getClassifier() instanceof DataType) refEndPoint.setType(dataTypeMap.get(ep.getClassifier()))
        if(ep.getClassifier() instanceof Relationship) refEndPoint.setType(relationshipsMap.get(ep.getClassifier()))
        return refEndPoint
    }

    @Override
    def cloneSubsetsAndRedefines(EndPoint ep) {
        RefOntoUML.Property refProp = endpointsMap.get(ep);
        for(EndPoint superProp: ep.getSubsets()){
            if(refProp!=null) refProp.getSubsettedProperty().add(endpointsMap.get(superProp));
        }
        for(EndPoint superProp: ep.getRedefines()){
            if(refProp!=null) refProp.getRedefinedProperty().add(endpointsMap.get(superProp));
        }
    }

    @Override
    Object cloneGeneralization(Generalization g) {
        Classifier general = g.getGeneral()
        Classifier specific = g.getSpecific()
        RefOntoUML.Classifier refGeneral=null
        if(general instanceof Class) refGeneral= classMap.get(general)
        if(general instanceof DataType) refGeneral = dataTypeMap.get(general)
        if(general instanceof Relationship) refGeneral = relationshipsMap.get(general)
        RefOntoUML.Classifier refSpecific = null
        if(specific instanceof Class) refSpecific= classMap.get(specific)
        if(specific instanceof DataType) refSpecific = dataTypeMap.get(specific)
        if(specific instanceof Relationship) refSpecific = relationshipsMap.get(specific)
        RefOntoUML.Generalization refGen = factory.createGeneralization()
        if(refGeneral!=null) {
            refGen.setGeneral(refGeneral)
            refGeneral.getGeneralization().add(refGen)
        }
        if(refSpecific!=null) {
            refGen.setSpecific(refSpecific)
            refSpecific.getGeneralization().add(refGen)
        }
        return refGen
    }

    @Override
    Object cloneGeneralizationSet(GeneralizationSet gs) {
        boolean isCovering = gs.isCovering()
        boolean isDisjoint = gs.isDisjoint()
        List<Generalization> gens = gs.getGeneralizations()
        List<RefOntoUML.Generalization> refGens = []
        gens.each{ g ->
            if(gensMap.get(g)!=null) refGens.add(gensMap.get(g))
        }
        RefOntoUML.GeneralizationSet refGenSet = factory.createGeneralizationSet()
        refGenSet.setIsCovering(isCovering)
        refGenSet.setIsDisjoint(isDisjoint)
        refGenSet.setName("")
        refGenSet.getGeneralization().addAll(refGens)
        RefOntoUML.Package refpack = packagesMap.get(gs.getContainer())
        refpack.getPackagedElement().add(refGenSet)
        return refGenSet
    }
}
