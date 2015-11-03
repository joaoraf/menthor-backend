package net.menthor.ontouml2.traits

import net.menthor.ontouml2.Constraint
import net.menthor.ontouml2.DataType
import net.menthor.ontouml2.Generalization
import net.menthor.ontouml2.GeneralizationSet
import net.menthor.ontouml2.Relationship;
import net.menthor.ontouml2.Class

trait Container implements NamedElement {

    List<ContainedElement> elements

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
}
