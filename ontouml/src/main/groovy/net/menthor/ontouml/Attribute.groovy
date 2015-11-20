package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MAttribute
import net.menthor.ontouml.stereotypes.PrimitiveStereotype

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Attribute extends MAttribute {

    protected PrimitiveStereotype stereotype

    //=============================
    // Getters
    //=============================

    PrimitiveStereotype getStereotype() { return stereotype }

    //=============================
    // Setters
    //=============================

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

    String toString() { Printer.print(this) }
}