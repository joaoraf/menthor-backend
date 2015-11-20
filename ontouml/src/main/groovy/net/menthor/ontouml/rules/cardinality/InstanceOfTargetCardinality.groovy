package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class InstanceOfTargetCardinality implements SyntacticalRule {

    InstanceOfTargetCardinality(Relationship self){
        this.errorMsg = 'The high order end (target) of a InstanceOf must have minimum cardinality of 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetMultiplicity(self, "isInstanceOf", 1, null)
    }
}