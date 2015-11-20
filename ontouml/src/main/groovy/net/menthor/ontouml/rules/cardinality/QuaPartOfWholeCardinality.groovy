package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class QuaPartOfWholeCardinality implements SyntacticalRule {

    QuaPartOfWholeCardinality(Relationship self){
        this.errorMsg = 'The relator end (whole) of a QuaPartOf must have cardinality (maximum and minimum) of exactly 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.wholeMultiplicity(self, "isQuaPartOf", 1, 1)
    }
}