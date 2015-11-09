package net.menthor.ontouml2.traits

import net.menthor.ontouml2.Constraint
import net.menthor.ontouml2.DataType
import net.menthor.ontouml2.Generalization
import net.menthor.ontouml2.GeneralizationSet
import net.menthor.ontouml2.Relationship;
import net.menthor.ontouml2.Class
import net.menthor.ontouml2.Factory
import net.menthor.ontouml2.stereotypes.ClassStereotype
import net.menthor.ontouml2.stereotypes.ConstraintStereotype
import net.menthor.ontouml2.stereotypes.DataTypeStereotype
import net.menthor.ontouml2.stereotypes.RelationshipStereotype
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

    DataType createDataType(DataTypeStereotype c, String name){
        return Factory.createDataType(c,name,this)
    }

    Relationship createRelationship(RelationshipStereotype stereo, Classifier source, Classifier target){
        return Factory.createRelationship(stereo, source, target, this)
    }

    Relationship createRelationship(RelationshipStereotype stereo, Classifier source, int sourceLower, int sourceUpper, String name,
        Classifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(stereo, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
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

    DataType createEnumeration(String name, List<String> textValues){
        return Factory.createEnumeration(name, textValues)
    }
}
