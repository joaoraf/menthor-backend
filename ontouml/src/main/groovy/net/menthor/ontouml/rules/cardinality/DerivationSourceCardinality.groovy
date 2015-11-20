package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule


class DerivationSourceCardinality implements SyntacticalRule {

    DerivationSourceCardinality(Relationship self){
        this.errorMsg = 'The material end of a Derivation must have minimum cardinality of 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceMultiplicity(self, "isDerivation", 1, null)
    }
}