package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class TemporalDependency implements SyntacticalRule {

    TemporalDependency(Relationship self){
        this.errorMsg = 'Both sides of a Temporal relationship must be set dependent'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceAndTargetDependents(self, "isTemporal")
    }
}