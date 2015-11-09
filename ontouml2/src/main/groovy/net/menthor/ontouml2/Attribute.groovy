package net.menthor.ontouml2

import net.menthor.ontouml2.stereotypes.PrimitiveStereotype
import net.menthor.ontouml2.traits.Property
import net.menthor.ontouml2.traits.Type
import org.codehaus.jackson.annotate.JsonIgnore
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
class Attribute implements Property {

    protected PrimitiveStereotype stereotype
    protected Type owner

    //=============================
    // Getters
    //=============================

    PrimitiveStereotype getStereotype() { return stereotype }

    @JsonIgnore
    Type getOwner() { return owner }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setOwner(Type owner){
        this.owner = owner
        if(owner==null) return
        //Ensuring opposite end
        if(!owner.attributes.contains(this)){
            owner.attributes.add(this)
        }
    }

    void setStereotype(PrimitiveStereotype stereotype){
        this.stereotype = stereotype
    }

    //=============================
    // Stereotype Checking
    //=============================

    @JsonIgnore
    boolean isInteger() {
        return stereotype==PrimitiveStereotype.INTEGER;
    }
    @JsonIgnore
    boolean isBoolean() {
        return stereotype==PrimitiveStereotype.BOOLEAN;
    }
    @JsonIgnore
    boolean isReal() {
        return stereotype==PrimitiveStereotype.REAL;
    }
    @JsonIgnore
    boolean isDate() {
        return stereotype==PrimitiveStereotype.DATE;
    }
    @JsonIgnore
    boolean isDateTime() {
        return stereotype==PrimitiveStereotype.DATE_TIME;
    }
    @JsonIgnore
    boolean isString() {
        return stereotype==PrimitiveStereotype.STRING;
    }
}