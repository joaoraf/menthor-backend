package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.ontouml.traits.Element

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Literal implements Element {

    protected String text
    protected DataType owner
    protected float upperBoundRegion
    protected float lowerBoundRegion

    //=============================
    //Getters
    //=============================

    String getText() { return text }

    @JsonIgnore
    DataType getOwner() { return owner }

    float getUpperBoundRegion() { return upperBoundRegion }

    float getLowerBoundRegion() { return lowerBoundRegion }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setText(String text) { this.text = text }

    void setUpperBoundRegion(float upperBoundRegion) { this.upperBoundRegion = upperBoundRegion }

    void setLowerBoundRegion(float lowerBoundRegion) { this.lowerBoundRegion = lowerBoundRegion }

    void setOwner(DataType owner){
        this.owner = owner
        if(owner==null) return
        //Ensuring opposite end
        if(!owner.literals.contains(this)){
            owner.literals.add(this)
        }
    }
}
