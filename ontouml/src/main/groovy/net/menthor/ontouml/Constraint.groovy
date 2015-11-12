package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.ontouml.stereotypes.ConstraintStereotype
import net.menthor.ontouml.traits.ContainedElement

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Constraint implements ContainedElement {

    protected ConstraintStereotype stereotype
    protected String context
    protected String expression
    protected String identifier

    //=============================
    // Getters
    //=============================

    ConstraintStereotype getStereotype() { return stereotype }

    String getContext() { return context }

    String getExpression() { return expression }

    String getIdentifier() { return identifier }

    //=============================
    // Setters
    //=============================

    void setStereotype(ConstraintStereotype stereotype) { this.stereotype = stereotype }

    void setContext(String context) { this.context = context }

    void setExpression(String expression) { this.expression = expression }

    void setIdentifier(String identifier) { this.identifier = identifier }

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
}
