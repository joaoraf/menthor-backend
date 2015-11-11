package net.menthor.ontouml.traits

import org.codehaus.jackson.annotate.JsonTypeInfo

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
trait Property implements NamedElement {

    protected boolean ordered
    protected boolean dependency
    protected boolean derived
    protected int lowerBound
    protected int upperBound

    //=============================
    // Getters
    //=============================

    boolean isOrdered(){ return ordered }

    boolean isDerived(){ return derived }

    boolean isDependency() { return dependency }

    int getLowerBound() { return lowerBound }

    int getUpperBound() { return upperBound }

    //=============================
    // Setters
    //=============================

    void setOrdered(boolean value){ this.ordered = value }

    void setDerived(boolean value){ this.derived = value }

    void setDependency(boolean value){ this.dependency = value }

    void setLowerBound(int value) { this.lowerBound = value }

    void setUpperBound(int value) { this.upperBound = value }

    void setCardinalities(int lower, int upper){
        lowerBound = lower
        upperBound = upper
    }
}
