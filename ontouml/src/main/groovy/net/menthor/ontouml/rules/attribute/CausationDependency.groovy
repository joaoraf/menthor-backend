package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class CausationDependency implements SyntacticalRule {

    CausationDependency(Relationship self){
        this.errorMsg = 'Both sides of a Causation must be set dependent'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceAndTargetDependents(self, "isCausation")
    }
}