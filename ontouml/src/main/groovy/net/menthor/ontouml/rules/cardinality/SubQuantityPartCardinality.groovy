package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubQuantityPartCardinality implements SyntacticalRule {

    SubQuantityPartCardinality(Relationship self){
        this.errorMsg = 'The amount of matter part (target) of a SubQuantityOf must have maximum cardinality of 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partMultiplicity(self, "isSubQuantityOf", null, 1)
    }
}
