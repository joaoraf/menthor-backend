package net.menthor.ontouml.map

import RefOntoUML.RefOntoUMLFactory
import RefOntoUML.util.RefOntoUMLResourceFactoryImpl

import net.menthor.ontouml.Attribute
import net.menthor.ontouml.Class
import net.menthor.ontouml.DataType
import net.menthor.ontouml.EndPoint
import net.menthor.ontouml.Factory
import net.menthor.ontouml.Generalization
import net.menthor.ontouml.GeneralizationSet
import net.menthor.ontouml.Literal
import net.menthor.ontouml.Model
import net.menthor.ontouml.Package
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.stereotypes.ClassStereotype
import net.menthor.ontouml.stereotypes.DataTypeStereotype
import net.menthor.ontouml.stereotypes.PrimitiveStereotype
import net.menthor.ontouml.stereotypes.QualityStereotype
import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.ontouml.traits.Classifier
import net.menthor.ontouml.traits.Type
import net.menthor.ontouml.values.MeasurementValue
import net.menthor.ontouml.values.ScaleValue
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.ecore.xmi.impl.XMLParserPoolImpl
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl

import java.text.Normalizer

class RefSourceMapper implements EMFSourceMapper {

    static RefOntoUMLFactory factory = RefOntoUMLFactory.eINSTANCE

    Model fromRefOntoUML(Object refmodel){
        setup(RefOntoUML.Package.class,RefOntoUML.Type.class, RefOntoUML.Class.class, RefOntoUML.DataType.class,
                RefOntoUML.Association.class,RefOntoUML.Generalization.class, RefOntoUML.Property.class, RefOntoUML.GeneralizationSet.class)
        return from(refmodel)
    }

    def RefOntoUML.Package deserialize(String path){
        ResourceSet rset = new ResourceSetImpl();
        rset.getResourceFactoryRegistry().getExtensionToFactoryMap().put("refontouml",new RefOntoUMLResourceFactoryImpl());
        rset.getPackageRegistry().put(RefOntoUML.RefOntoUMLPackage.eNS_URI,	RefOntoUML.RefOntoUMLPackage.eINSTANCE);
        File file = new File(path);
        URI fileURI = URI.createFileURI(file.getAbsolutePath());
        Resource resource = rset.createResource(fileURI);
        /**Load options that significantly improved the performance of loading EMF Model instances*/
        Map<Object,Object> loadOptions = ((XMLResourceImpl)resource).getDefaultLoadOptions();
        loadOptions.put(XMLResource.OPTION_USE_PARSER_POOL, new XMLParserPoolImpl());
        loadOptions.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
        resource.load(loadOptions);
        return resource.getContents().get(0);
    }

    @Override
    Model cloneModel(Object srcModel) {
        def refmodel = srcModel as RefOntoUML.Package
        Model ontomodel = Factory.createModel(refmodel.getName())
        return ontomodel
    }

    @Override
    Package clonePackage(Object srcPackage, Package ontoParentPackage) {
        def refpack = srcPackage as RefOntoUML.Package
        def ontopack = ontoParentPackage.createPackage(refpack.getName())
        return ontopack
    }

    String getRefStereotype(Object srcElem){
        String type = srcElem.getClass().toString().replaceAll("class RefOntoUML.impl.","")
        type = type.replaceAll("Impl","")
        type = Normalizer.normalize(type, Normalizer.Form.NFD)
        if (!type.equalsIgnoreCase("association")) type = type.replace("Association","")
        return type
    }

    ClassStereotype getClassStereotype(RefOntoUML.Class refElem){
        ClassStereotype cs = ClassStereotype.getEnum(getRefStereotype(refElem))
        if(cs==null){
            if(refElem instanceof RefOntoUML.Kind) cs = ClassStereotype.KIND
            if(refElem instanceof RefOntoUML.Quantity) cs = ClassStereotype.QUANTITY
            if(refElem instanceof RefOntoUML.Collective) cs = ClassStereotype.COLLECTIVE
            if(refElem instanceof RefOntoUML.SubKind) cs = ClassStereotype.SUBKIND
            if(refElem instanceof RefOntoUML.Phase) cs = ClassStereotype.PHASE
            if(refElem instanceof RefOntoUML.Role) cs = ClassStereotype.ROLE
            if(refElem instanceof RefOntoUML.RoleMixin) cs = ClassStereotype.ROLEMIXIN
            if(refElem instanceof RefOntoUML.Category) cs = ClassStereotype.CATEGORY
            if(refElem instanceof RefOntoUML.Mixin) cs = ClassStereotype.MIXIN
            if(refElem instanceof RefOntoUML.Relator) cs = ClassStereotype.RELATOR
            if(refElem instanceof RefOntoUML.Mode) cs = ClassStereotype.MODE
            if(refElem instanceof RefOntoUML.PerceivableQuality) cs = ClassStereotype.QUALITY
            if(refElem instanceof RefOntoUML.NonPerceivableQuality) cs = ClassStereotype.QUALITY
            if(refElem instanceof RefOntoUML.NominalQuality) cs = ClassStereotype.QUALITY
        }
        return cs
    }

    DataTypeStereotype getDataTypeStereotype(RefOntoUML.DataType refElem)
    {
        DataTypeStereotype cs = DataTypeStereotype.getEnum(getRefStereotype(refElem))
        if(cs==null){
            if(refElem instanceof RefOntoUML.Enumeration) cs = DataTypeStereotype.ENUMERATION
            if(refElem instanceof RefOntoUML.StringNominalStructure) cs = DataTypeStereotype.DIMENSION
            if(refElem instanceof RefOntoUML.DecimalIntervalDimension) cs = DataTypeStereotype.DIMENSION
            if(refElem instanceof RefOntoUML.DecimalOrdinalDimension) cs = DataTypeStereotype.DIMENSION
            if(refElem instanceof RefOntoUML.DecimalRationalDimension) cs = DataTypeStereotype.DIMENSION
            if(refElem instanceof RefOntoUML.IntegerIntervalDimension) cs = DataTypeStereotype.DIMENSION
            if(refElem instanceof RefOntoUML.IntegerOrdinalDimension) cs = DataTypeStereotype.DIMENSION
            if(refElem instanceof RefOntoUML.IntegerRationalDimension) cs = DataTypeStereotype.DIMENSION
            if(refElem instanceof RefOntoUML.MeasurementDomain) cs = DataTypeStereotype.DOMAIN
        }
        return cs
    }

    RelationshipStereotype getRelationshipStereotype(RefOntoUML.Association refElem) {
        RelationshipStereotype cs = RelationshipStereotype.getEnum(getRefStereotype(refElem))
        if(cs==null){
            if(refElem instanceof RefOntoUML.Derivation) cs = RelationshipStereotype.DERIVATION
            if(refElem instanceof RefOntoUML.Characterization) cs = RelationshipStereotype.CHARACTERIZATION
            if(refElem instanceof RefOntoUML.Mediation) cs = RelationshipStereotype.MEDIATION
            if(refElem instanceof RefOntoUML.MaterialAssociation) cs = RelationshipStereotype.MATERIAL
            if(refElem instanceof RefOntoUML.FormalAssociation) cs = RelationshipStereotype.FORMAL
            if(refElem instanceof RefOntoUML.componentOf) cs = RelationshipStereotype.COMPONENTOF
            if(refElem instanceof RefOntoUML.subCollectionOf) cs = RelationshipStereotype.SUBCOLLECTIONOF
            if(refElem instanceof RefOntoUML.subQuantityOf) cs = RelationshipStereotype.SUBQUANTITYOF
            if(refElem instanceof RefOntoUML.memberOf) cs = RelationshipStereotype.MEMBEROF
            if(refElem instanceof RefOntoUML.Structuration) cs = RelationshipStereotype.STRUCTURATION;
        }
        return cs;
    }

    PrimitiveStereotype getPrimitiveStereotype(RefOntoUML.PrimitiveType elem){
        PrimitiveStereotype ps = PrimitiveStereotype.getEnum(elem.getName());
        if(ps==null && elem.getName()!=null){
            if(elem.getName().compareToIgnoreCase("Integer")==0) ps = PrimitiveStereotype.INTEGER;
            if(elem.getName().compareToIgnoreCase("int")==0) ps = PrimitiveStereotype.INTEGER;
            if(elem.getName().compareToIgnoreCase("Boolean")==0) ps = PrimitiveStereotype.BOOLEAN;
            if(elem.getName().compareToIgnoreCase("Real")==0) ps = PrimitiveStereotype.REAL;
            if(elem.getName().compareToIgnoreCase("String")==0) ps = PrimitiveStereotype.STRING;
            if(elem.getName().compareToIgnoreCase("Date")==0) ps = PrimitiveStereotype.DATE;
            if(elem.getName().compareToIgnoreCase("DateTime")==0) ps = PrimitiveStereotype.DATE_TIME;
        }
        return ps;
    }

    @Override
    Class cloneClass(Object srcClass) {
        def refclass = srcClass as RefOntoUML.Class
        boolean isAbstract = refclass.isIsAbstract()
        String name = refclass.getName()
        def cs = getClassStereotype(refclass)
        Class ontoclass = Factory.createClass(cs,name,srcPackagesMap.get(refclass.eContainer()))
        if(refclass instanceof RefOntoUML.PerceivableQuality) ontoclass.setQualityStereotype(QualityStereotype.PERCEIVABLE)
        else if(refclass instanceof RefOntoUML.NonPerceivableQuality) ontoclass.setQualityStereotype(QualityStereotype.NON_PERCEIVABLE)
        else if(refclass instanceof RefOntoUML.NominalQuality) ontoclass.setQualityStereotype(QualityStereotype.NOMINAL)
        if(refclass instanceof RefOntoUML.Collective) ontoclass.setIsExtensional((refclass as RefOntoUML.Collective).isIsExtensional())
        return ontoclass
    }

    @Override
    DataType cloneDataType(Object srcDataType) {
        def refdatatype = srcDataType as RefOntoUML.DataType
        String name = refdatatype.getName()
        DataTypeStereotype cs = getDataTypeStereotype(refdatatype)
        DataType ontodatatype = Factory.createDataType(cs, name, srcPackagesMap.get(refdatatype.eContainer()))
        if(refdatatype instanceof RefOntoUML.MeasurementDimension){
            ontodatatype.setUnitOfMeasure((refdatatype as RefOntoUML.MeasurementDimension).getUnitOfMeasure())
        }
        if(refdatatype instanceof RefOntoUML.IntegerIntervalDimension) {
            ontodatatype.setScale(ScaleValue.INTERVAL)
            ontodatatype.setMeasurement(MeasurementValue.INTEGER)
        }
        if(refdatatype instanceof RefOntoUML.IntegerOrdinalDimension) {
            ontodatatype.setScale(ScaleValue.ORDINAL)
            ontodatatype.setMeasurement(MeasurementValue.INTEGER)
        }
        if(refdatatype instanceof RefOntoUML.IntegerRationalDimension) {
            ontodatatype.setScale(ScaleValue.RATIONAL)
            ontodatatype.setMeasurement(MeasurementValue.INTEGER)
        }
        if(refdatatype instanceof RefOntoUML.DecimalIntervalDimension) {
            ontodatatype.setScale(ScaleValue.INTERVAL)
            ontodatatype.setMeasurement(MeasurementValue.DECIMAL)
        }
        if(refdatatype instanceof RefOntoUML.DecimalOrdinalDimension) {
            ontodatatype.setScale(ScaleValue.ORDINAL)
            ontodatatype.setMeasurement(MeasurementValue.DECIMAL)
        }
        if(refdatatype instanceof RefOntoUML.DecimalRationalDimension) {
            ontodatatype.setScale(ScaleValue.RATIONAL)
            ontodatatype.setMeasurement(MeasurementValue.DECIMAL)
        }
        if(refdatatype instanceof RefOntoUML.Enumeration){
            for(RefOntoUML.EnumerationLiteral lt: (refdatatype as RefOntoUML.Enumeration).getOwnedLiteral()){
                Literal ontoLit = Factory.createLiteral(lt.getName());
                ontoLit.setOwner(ontodatatype);
            }
        }
        return ontodatatype
    }

    @Override
    Relationship cloneRelationship(Object srcRel) {
        def refrel = srcRel as RefOntoUML.Association
        String name = refrel.getName();
        RelationshipStereotype rs = getRelationshipStereotype(srcRel);
        Relationship ontorel = Factory.createRelationship(rs, name, srcPackagesMap.get(refrel.eContainer()))
        return ontorel
    }

    @Override
    Attribute cloneAttribute(Object srcAttr) {
        def refAttr = srcAttr as RefOntoUML.Property
        Type ontotype = srcClassMap.get(refAttr.eContainer())
        if(ontotype==null) ontotype = srcDataTypeMap.get(refAttr.eContainer())
        PrimitiveStereotype ps = getPrimitiveStereotype((RefOntoUML.PrimitiveType)refAttr.getType())
        int lower = refAttr.getLower()
        int upper = refAttr.getUpper()
        String name = refAttr.getName()
        boolean isDerived = refAttr.isIsDerived()
        boolean isDependency = refAttr.isIsReadOnly()
        Attribute ontoattr = Factory.createAttribute(ontotype, ps, lower, upper, name, isDerived, isDependency)
        return ontoattr
    }

    @Override
    EndPoint cloneEndPoint(Object srcEp) {
        def ep = srcEp as RefOntoUML.Property
        Relationship rel = srcRelationshipsMap.get(ep.getAssociation())
        Classifier ontotype = srcClassMap.get(ep.getType())
        if(ontotype==null) ontotype = srcDataTypeMap.get(ep.getType())
        if(ontotype==null) ontotype = srcRelationshipsMap.get(ep.getType())
        int lower = ep.getLower()
        int upper = ep.getUpper()
        String name = ep.getName()
        boolean isDerived = ep.isIsDerived()
        boolean isDependency = ep.isIsReadOnly()
        EndPoint ontopoint = Factory.createEndPoint(rel, ontotype, lower, upper, name, isDerived, isDependency);
        return ontopoint
    }

    @Override
    def cloneSubsetsAndRedefines(Object srcEp) {
        def refEp = srcEp as RefOntoUML.Property
        EndPoint ontoep = srcEndpointsMap.get(refEp)
        for(RefOntoUML.Property subsetted: refEp.getSubsettedProperty()){
            if(ontoep!=null) ontoep.getSubsets().add(srcEndpointsMap.get(subsetted))
        }
        for(RefOntoUML.Property redefined: refEp.getRedefinedProperty()){
            if(ontoep!=null) ontoep.getRedefines().add(srcEndpointsMap.get(redefined))
        }
    }

    @Override
    Generalization cloneGeneralization(Object srcGen) {
        def g = srcGen as RefOntoUML.Generalization
        RefOntoUML.Classifier general = g.getGeneral()
        RefOntoUML.Classifier specific = g.getSpecific()
        Classifier ontogeneral = srcClassMap.get(general)
        if(ontogeneral==null) ontogeneral = srcDataTypeMap.get(general)
        if(ontogeneral==null) ontogeneral = srcRelationshipsMap.get(general)
        Classifier ontospecific = srcClassMap.get(specific)
        if(ontospecific==null) ontospecific = srcDataTypeMap.get(specific)
        if(ontospecific==null) ontospecific = srcRelationshipsMap.get(specific)
        Generalization ontog = Factory.createGeneralization(ontogeneral, ontospecific, srcPackagesMap.get(g.eContainer()))
        return ontog
    }

    @Override
    GeneralizationSet cloneGeneralizationSet(Object srcGs) {
        def refgs = srcGs as RefOntoUML.GeneralizationSet
        String name = refgs.getName()
        boolean isCovering = refgs.isIsCovering()
        boolean isDisjoint = refgs.isIsDisjoint()
        List<RefOntoUML.Generalization> refgens = refgs.getGeneralization()
        List<Generalization> gens = new ArrayList<Generalization>()
        for(RefOntoUML.Generalization g: refgens){
            if(srcGensMap.get(g)!=null) gens.add(srcGensMap.get(g))
        }
        GeneralizationSet ontogs = Factory.createGeneralizationSet(isCovering, isDisjoint, gens, srcPackagesMap.get(refgs))
        return ontogs
    }

}
