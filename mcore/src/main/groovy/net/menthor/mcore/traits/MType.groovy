package net.menthor.mcore.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MAttribute

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait MType implements MClassifier {

    protected List<MAttribute> attributes

    //=============================
    // Getters
    //=============================

    List<MAttribute> getAttributes() { return attributes }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setAttribute(MAttribute attr){
        if(attr==null) return
        if(!attributes.contains(attr)){
            attributes.add(attr)
        }
        //Ensure the opposite end
        attr.setOwner(this)
    }

    void setAttributes(List<MAttribute> attributes){
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
    List<MType> oppositeTypes(){
        def result = []
        oppositeEndPoints().each{ ep ->
            def t = ep.getClassifier();
            if(t!=null){
                result.add(t as MType)
            }
        }
        return result;
    }

    /**Returns all types directly and indirectly connected to this type through a relationship. */
    List<MType> allOppositeTypes(){
        def result = []
        allOppositeEndPoints().each { ep ->
            def t = ep.getClassifier();
            if(t!=null){
                result.add(t as MType)
            }
        }
        return result;
    }
}