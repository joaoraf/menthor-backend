package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MLiteral

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Literal extends MLiteral {

    protected float upperBoundRegion
    protected float lowerBoundRegion

    //=============================
    //Getters
    //=============================

    float getUpperBoundRegion() { return upperBoundRegion }

    float getLowerBoundRegion() { return lowerBoundRegion }

    //=============================
    // Setters
    //=============================

    void setUpperBoundRegion(float upperBoundRegion) { this.upperBoundRegion = upperBoundRegion }

    void setLowerBoundRegion(float lowerBoundRegion) { this.lowerBoundRegion = lowerBoundRegion }

    String toString() { Printer.print(this) }
}
