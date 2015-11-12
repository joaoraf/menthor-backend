package net.menthor.ontouml.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.ontouml.Attribute

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait Type implements Classifier {

    protected List<Attribute> attributes

    //=============================
    // Getters
    //=============================

    List<Attribute> getAttributes() { return attributes }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setAttribute(Attribute attr){
        if(attr==null) return
        if(!attributes.contains(attr)){
            attributes.add(attr)
        }
        //Ensure the opposite end
        attr.setOwner(this)
    }

    void setAttributes(List<Attribute> attributes){
        if(attributes==null || attributes==[]){
            this.attributes.clear()
            return
        }
        attributes.each{ a ->
            setAttribute(a)
        }
    }

    //=============================
    // Opposite Types
    //=============================

    /** Returns all types directly connected to this type through a relationship. */
    List<Type> oppositeTypes(){
        def result = []
        oppositeEndPoints().each{ ep ->
            def t = ep.getClassifier();
            if(t!=null){
                result.add(t as Type)
            }
        }
        return result;
    }

    /**Returns all types directly and indirectly connected to this type through a relationship. */
    List<Type> allOppositeTypes(){
        def result = []
        allOppositeEndPoints().each { ep ->
            def t = ep.getClassifier();
            if(t!=null){
                result.add(t as Type)
            }
        }
        return result;
    }
}