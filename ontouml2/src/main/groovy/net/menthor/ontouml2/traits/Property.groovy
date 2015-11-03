package net.menthor.ontouml2.traits

trait Property implements NamedElement {

    boolean isOrdered
    boolean isDerived
    int lowerBound
    int upperBound
    boolean isDependency

    void setCardinalities(int lower, int upper){
        lowerBound = lower
        upperBound = upper
    }
}
