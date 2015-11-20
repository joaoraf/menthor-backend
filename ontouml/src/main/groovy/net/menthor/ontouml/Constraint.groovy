package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MConstraint
import net.menthor.ontouml.stereotypes.ConstraintStereotype

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Constraint extends MConstraint {

    protected ConstraintStereotype stereotype

    //=============================
    // Getters
    //=============================

    ConstraintStereotype getStereotype() { return stereotype }

    //=============================
    // Setters
    //=============================

    void setStereotype(ConstraintStereotype stereotype) { this.stereotype = stereotype }

    //=============================
    // Stereotype Checking
    //=============================

    @JsonIgnore
    boolean isInvariant(){ return stereotype == ConstraintStereotype.INVARIANT }

    @JsonIgnore
    boolean isDerivation(){ return stereotype == ConstraintStereotype.DERIVATION }

    @JsonIgnore
    boolean isTemporal(){ return stereotype == ConstraintStereotype.TEMPORAL }

    @JsonIgnore
    boolean isHistorical(){ return stereotype == ConstraintStereotype.HISTORICAL }

    String toString() { Printer.print(this) }
}
