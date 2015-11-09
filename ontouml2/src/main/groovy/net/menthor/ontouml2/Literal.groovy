package net.menthor.ontouml2

import net.menthor.ontouml2.traits.Element
import org.codehaus.jackson.annotate.JsonIgnore
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
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
