package net.menthor.ontouml.traits

import net.menthor.ontouml.Constraint
import net.menthor.ontouml.GeneralizationSet
import net.menthor.ontouml.Class
import net.menthor.ontouml.stereotypes.ClassStereotype
import net.menthor.ontouml.stereotypes.ConstraintStereotype
import net.menthor.ontouml.stereotypes.DataTypeStereotype
import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.ontouml.DataType
import net.menthor.ontouml.Factory
import net.menthor.ontouml.Generalization
import net.menthor.ontouml.Relationship
import org.codehaus.jackson.annotate.JsonTypeInfo

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
trait Container implements NamedElement {

    protected List<ContainedElement> elements = []

    //=============================
    // Getters
    //=============================

    List<ContainedElement> getElements() { return elements }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setElement(ContainedElement ce){
        if(ce == null) return
        if(!elements.contains(ce)){
            elements.add(ce)
        }
        //Ensure the opposite end
        ce.setContainer(this)
    }

    void setElements(List<ContainedElement> elements){
       if(elements==null || elements == []){
           this.elements.clear()
           return
       }
       elements.each{ e ->
           setElement(e)
       }
    }

    //=============================
    // Contained Element
    //=============================

    /* Search for the elements of a particular type in this container. */
    List<ContainedElement> elements(java.lang.Class type){
        List result = []
        getElements().each{ e ->
            if(type.isInstance(e)) result.add(e)
        }
        return result
    }

    /** Search in depth for all elements of a particular type in this container */
    List<ContainedElement> allElements(java.lang.Class type){
        def result = []
        allElements(this, type, result)
        return result
    }

    /** Search in depth for all elements of a particular type in this container. */
    List<ContainedElement> allElements(Container c, java.lang.Class type, List result){
        c.getElements().each{ e->
            if(type.isInstance(e)) {
                result.add(e)
                allElements(e, type, result)
            }
        }
    }

    //=============================
    // Package
    //=============================

    /* Packages of this container. */
    List<Package> packages() {
       return elements(Package.class)
    }

     /** All packages of this container (searching in depth) */
     List<Package> allPackages(){
        return allElements(Package.class)
     }

    //=============================
    // Relationship
    //=============================

    /* Relationships of this container. */
    List<Relationship> relationships() {
        return elements(Relationship.class)
    }

    /** All relationships of this container (searching in depth) */
    List<Relationship> allRelationships(){
        return allElements(Relationship.class)
    }

    //=============================
    // Classes
    //=============================

    /* Classes of this container. */
    List<Class> classes() {
        return elements(Class.class)
    }

    /** All classes of this container (searching in depth) */
    List<Class> allClasses(){
        return allElements(Class.class)
    }

    //=============================
    // DataTypes
    //=============================

    /* DataTypes of this container. */
    List<DataType> dataTypes() {
        return elements(DataType.class)
    }

    /** All datatypes of this container (searching in depth) */
    List<DataType> allDataTypes(){
        return allElements(DataType.class)
    }

    //=============================
    // Generalization
    //=============================

    /* Generalizations of this container. */
    List<Generalization> generalizations() {
        return elements(Generalization.class)
    }

    /** All generalizations of this container (searching in depth) */
    List<Generalization> allGeneralizations(){
        return allElements(Generalization.class)
    }

    //=============================
    // Generalization Set
    //=============================

    /* Generalization Sets of this container. */
    List<GeneralizationSet> generalizationSets() {
        return elements(GeneralizationSet.class)
    }

    /** All generalization Sets of this container (searching in depth) */
    List<GeneralizationSet> allGeneralizationSets(){
        return allElements(GeneralizationSet.class)
    }

    //=============================
    // Constraint
    //=============================

    /* Constraints of this container. */
    List<Constraint> constraints() {
        return elements(Constraint.class)
    }

    /** All constraints of this container (searching in depth) */
    List<Constraint> allConstraints(){
        return allElements(Constraint.class)
    }

    //=============================
    // Type
    //=============================

    /* Types of this container. */
    List<Type> types() {
        return elements(Type.class)
    }

    /** All types of this container (searching in depth) */
    List<Type> allTypes(){
        return allElements(Type.class)
    }

    //=============================
    // Classifier
    //=============================

    /* Classifiers of this container. */
    List<Classifier> classifiers() {
        return elements(Classifier.class)
    }

    /** All classifiers of this container (searching in depth) */
    List<Classifier> allClassifiers(){
        return allElements(Classifier.class)
    }

    //=============================
    // Creation
    //=============================

    Package createPackage(String name){
        return Factory.createPackage(name,this)
    }

    Class createClass(ClassStereotype c, String name){
        return Factory.createClass(c,name,this)
    }

    Class createKind(String name){
        return Factory.createClass(ClassStereotype.KIND,name,this)
    }

    Class createCollective(String name){
        return Factory.createClass(ClassStereotype.COLLECTIVE,name,this)
    }

    Class createQuantity(String name){
        return Factory.createClass(ClassStereotype.QUANTITY,name,this)
    }

    Class createSubKind(String name){
        return Factory.createClass(ClassStereotype.SUBKIND,name,this)
    }

    Class createPhase(String name){
        return Factory.createClass(ClassStereotype.PHASE,name,this)
    }

    Class createRole(String name){
        return Factory.createClass(ClassStereotype.ROLE,name,this)
    }

    Class createMixin(String name){
        return Factory.createClass(ClassStereotype.MIXIN,name,this)
    }

    Class createCategory(String name){
        return Factory.createClass(ClassStereotype.CATEGORY,name,this)
    }

    Class createRoleMixin(String name){
        return Factory.createClass(ClassStereotype.ROLEMIXIN,name,this)
    }

    Class createPhaseMixin(String name){
        return Factory.createClass(ClassStereotype.PHASEMIXIN,name,this)
    }

    Class createMode(String name){
        return Factory.createClass(ClassStereotype.MODE,name,this)
    }

    Class createRelator(String name){
        return Factory.createClass(ClassStereotype.RELATOR,name,this)
    }

    Class createQuality(String name){
        return Factory.createClass(ClassStereotype.QUALITY,name,this)
    }

    Class createEvent(String name){
        return Factory.createClass(ClassStereotype.EVENT,name,this)
    }

    Class createHighorder(String name){
        return Factory.createClass(ClassStereotype.HIGHORDER,name,this)
    }

    DataType createDataType(DataTypeStereotype c, String name){
        return Factory.createDataType(c,name,this)
    }

    DataType createDimension(String name){
        return Factory.createDataType(DataTypeStereotype.DIMENSION,name,this)
    }

    DataType createDomain(String name){
        return Factory.createDataType(DataTypeStereotype.DOMAIN,name,this)
    }

    DataType createEnumeration(String name, List<String> textValues){
        return Factory.createEnumeration(name, textValues)
    }

    Relationship createRelationship(RelationshipStereotype stereo, Classifier source, Classifier target){
        return Factory.createRelationship(stereo, source, target, this)
    }

    Relationship createDerivation(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.DERIVATION, source, target, this)
    }

    Relationship createTemporal(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.TEMPORAL, source, target, this)
    }

    Relationship createStructuration(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.STRUCTURATION, source, target, this)
    }

    Relationship createCausation(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.CAUSATION, source, target, this)
    }

    Relationship createCharacterization(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.CHARACTERIZATION, source, target, this)
    }

    Relationship createComponentOf(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.COMPONENTOF, source, target, this)
    }

    Relationship createConsitution(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.CONSTITUTION, source, target, this)
    }

    Relationship createFormal(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.FORMAL, source, target, this)
    }

    Relationship createInstanceOf(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.INSTANCEOF, source, target, this)
    }

    Relationship createMaterial(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.MATERIAL, source, target, this)
    }

    Relationship createMediation(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.MEDIATION, source, target, this)
    }

    Relationship createMemberOf(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.MEMBEROF, source, target, this)
    }

    Relationship createParticipation(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.PARTICIPATION, source, target, this)
    }

    Relationship createQuaPartOf(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.QUAPARTOF, source, target, this)
    }

    Relationship createSubCollectionOf(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.SUBCOLLECTIONOF, source, target, this)
    }

    Relationship createSubEventOf(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.SUBEVENTOF, source, target, this)
    }

    Relationship createSubQuantityOf(Classifier source, Classifier target){
        return Factory.createRelationship(RelationshipStereotype.SUBQUANTITYOF, source, target, this)
    }

    Relationship createRelationship(RelationshipStereotype stereo, Classifier source, int sourceLower, int sourceUpper, String name,
        Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(stereo, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createMediation(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.MEDIATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createMaterial(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.MATERIAL, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createFormal(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.FORMAL, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createCharacterization(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.CHARACTERIZATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createCausation(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.CAUSATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createComponentOf(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.COMPONENTOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createConstitution(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.CONSTITUTION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createDerivation(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.DERIVATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createInstanceOf(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.INSTANCEOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createMemberOf(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.MEMBEROF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createParticipation(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.PARTICIPATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createQuaPartOf(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.QUAPARTOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createStructuration(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.STRUCTURATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createSubCollectionOf(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.SUBCOLLECTIONOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createSubEventOf(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.SUBEVENTOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createSubQuantityOf(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.SUBQUANTITYOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createTemporal(Classifier source, int sourceLower, int sourceUpper, String name, Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.TEMPORAL, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Generalization createGeneralization(Classifier source, Classifier target){
        return Factory.createGeneralization(source, target, this)
    }

    GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisj, List<Generalization> gens){
        return Factory.createGeneralizationSet(isCovering, isDisj, gens, this)
    }

    GeneralizationSet createPartition(List<Classifier> specifics, Classifier general){
        return Factory.createPartition(specifics, general, this)
    }

    Constraint createConstraint(String context, ConstraintStereotype stereo, String identifier, String expression){
        return Factory.createConstraint(context, stereo, identifier, expression, this)
    }
}
