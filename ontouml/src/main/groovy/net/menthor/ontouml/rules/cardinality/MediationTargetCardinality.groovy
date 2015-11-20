package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class MediationTargetCardinality implements SyntacticalRule {

    MediationTargetCardinality(Relationship self){
        this.errorMsg = 'The mediated end (target) of a Mediation must have minimum cardinality of 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetMultiplicity(this.self, "isMediation", 1, null)
    }
}
