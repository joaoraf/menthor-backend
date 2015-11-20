package net.menthor.mcore.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MAttribute
import net.menthor.mcore.MClass
import net.menthor.mcore.MConstraint
import net.menthor.mcore.MDataType
import net.menthor.mcore.MEndPoint
import net.menthor.mcore.MFactory
import net.menthor.mcore.MGeneralization
import net.menthor.mcore.MGeneralizationSet
import net.menthor.mcore.MPackage
import net.menthor.mcore.MRelationship

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait MContainer implements MNamedElement {

    protected List<MContainedElement> elements = []

    //=============================
    // Getters
    //=============================

    List<MContainedElement> getElements() { return elements }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setElement(MContainedElement ce){
        if(ce == null) return
        if(!elements.contains(ce)){
            elements.add(ce)
        }
        //Ensure the opposite end
        ce.setContainer(this)
    }

    void setElements(List<MContainedElement> elements){
       if(elements==null || elements == []){
           this.elements.clear()
           return
       }
       elements.each{ e ->
           setElement(e)
       }
    }

    //=============================
    // Contained MElement
    //=============================

    /* Search for the elements of a particular type in this container. */
    List<MContainedElement> elements(java.lang.Class type){
        List result = []
        getElements().each{ e ->
            if(type.isInstance(e)) result.add(e)
        }
        return result
    }

    /** Search in depth for all elements of a particular type in this container */
    List<MContainedElement> allElements(java.lang.Class type){
        def result = []
        allElements(this, type, result)
        return result
    }

    /** Search in depth for all elements of a particular type in this container. */
    List<MContainedElement> allElements(MContainer c, java.lang.Class type, List result){
        c.getElements().each{ e->
            if(type.isInstance(e)) {
                result.add(e)
            }
            if(e instanceof MContainer) allElements(e, type, result)
        }
    }

    //=============================
    // Package
    //=============================

    /* Packages of this container. */
    List<MPackage> packages() {
       return elements(MPackage.class)
    }

     /** All packages of this container (searching in depth) */
     List<MPackage> allPackages(){
        return allElements(MPackage.class)
     }

    //=============================
    // Relationship
    //=============================

    /* Relationships of this container. */
    List<MRelationship> relationships() {
        return elements(MRelationship.class)
    }

    /** All relationships of this container (searching in depth) */
    List<MRelationship> allRelationships(){
        return allElements(MRelationship.class)
    }

    //=============================
    // Classes
    //=============================

    /* Classes of this container. */
    List<MClass> classes() {
        return elements(MClass.class)
    }

    /** All classes of this container (searching in depth) */
    List<MClass> allClasses(){
        return allElements(MClass.class)
    }

    //=============================
    // DataTypes
    //=============================

    /* DataTypes of this container. */
    List<MDataType> dataTypes() {
        return elements(MDataType.class)
    }

    /** All datatypes of this container (searching in depth) */
    List<MDataType> allDataTypes(){
        return allElements(MDataType.class)
    }

    //=============================
    // Generalization
    //=============================

    /* Generalizations of this container. */
    List<MGeneralization> generalizations() {
        return elements(MGeneralization.class)
    }

    /** All generalizations of this container (searching in depth) */
    List<MGeneralization> allGeneralizations(){
        return allElements(MGeneralization.class)
    }

    //=============================
    // Generalization Set
    //=============================

    /* Generalization Sets of this container. */
    List<MGeneralizationSet> generalizationSets() {
        return elements(MGeneralizationSet.class)
    }

    /** All generalization Sets of this container (searching in depth) */
    List<MGeneralizationSet> allGeneralizationSets(){
        return allElements(MGeneralizationSet.class)
    }

    //=============================
    // Constraint
    //=============================

    /* Constraints of this container. */
    List<MConstraint> constraints() {
        return elements(MConstraint.class)
    }

    /** All constraints of this container (searching in depth) */
    List<MConstraint> allConstraints(){
        return allElements(MConstraint.class)
    }

    //=============================
    // MType
    //=============================

    /* Types of this container. */
    List<MType> types() {
        return elements(MType.class)
    }

    /** All types of this container (searching in depth) */
    List<MType> allTypes(){
        return allElements(MType.class)
    }

    //=============================
    // MClassifier
    //=============================

    /* Classifiers of this container. */
    List<MClassifier> classifiers() {
        return elements(MClassifier.class)
    }

    /** All classifiers of this container (searching in depth) */
    List<MClassifier> allClassifiers(){
        return allElements(MClassifier.class)
    }

    //=============================
    // Attribute
    //=============================

     List<MAttribute> attributes(){
        List<MAttribute> result = []
        getElements().each{ elem ->
            if (elem instanceof MType){
                (elem as MType).getAttributes().each{ ontoAttr ->
                    result.add(ontoAttr)
                }
            }
        }
        return result
    }

    //=============================
    // EndPoints
    //=============================

    List<MEndPoint> endPoints(){
        List<MEndPoint> endpoints= []
        getElements().each { elem ->
            if (elem instanceof MRelationship){
                (elem as MRelationship).getEndPoints().each{ endpoint ->
                    endpoints.add(endpoint)
                }
            }
        }
        return endpoints
    }

    //=============================
    // Creation
    //=============================

    MPackage createPackage(String name){
        return MFactory.createPackage(name,this)
    }

    MClass createClass(String name){
        return MFactory.createClass(name,this)
    }

    MDataType createDataType(String name){
        return MFactory.createDataType(name,this)
    }

    MDataType createDataType(String name, List<String> textValues){
        return MFactory.createDataType(name, textValues)
    }

    MRelationship createRelationship(MClassifier source, MClassifier target){
        return MFactory.createRelationship(source, target, this)
    }

    MRelationship createRelationship(MClassifier source, int srcLower, int srcUpper, String name, MClassifier target, int tgtLower, int tgtUpper){
        return MFactory.createRelationship(source, srcLower, srcUpper, name, target, tgtLower, tgtUpper, this)
    }

    MGeneralization createGeneralization(MClassifier source, MClassifier target){
        return MFactory.createGeneralization(source, target, this)
    }

    MGeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisj, List<MGeneralization> gens){
        return MFactory.createGeneralizationSet(isCovering, isDisj, gens, this)
    }

    MGeneralizationSet createPartition(List<MClassifier> specifics, MClassifier general){
        return MFactory.createPartition(specifics, general, this)
    }

    MConstraint createConstraint(MNamedElement context, String name, String condition){
        return MFactory.createConstraint(context, name, condition, this)
    }
}
