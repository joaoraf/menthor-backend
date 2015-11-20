package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class StructurationTargetCardinality implements SyntacticalRule {

    StructurationTargetCardinality(Relationship self){
        this.errorMsg = 'The structure (domain, dimension) end of a Structuration must have cardinality (maximum and minimum) of exactly 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetMultiplicity(self, "isStructuration", 1, 1)
    }
}