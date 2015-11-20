package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubQuantityWholeCardinality implements SyntacticalRule {

    SubQuantityWholeCardinality(Relationship self){
        this.errorMsg = 'The amount of matter whole (source) of a SubQuantityOf must have maximum cardinality of 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.wholeMultiplicity(self, "isSubQuantityOf", null, 1)
    }
}