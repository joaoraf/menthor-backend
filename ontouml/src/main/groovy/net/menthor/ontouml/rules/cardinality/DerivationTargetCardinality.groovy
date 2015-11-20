package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class DerivationTargetCardinality implements SyntacticalRule {

    DerivationTargetCardinality(Relationship self){
        this.errorMsg = 'The relator end of a Derivation must have minimum cardinality of 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceMultiplicity(self, "isDerivation", 1, null)
    }
}