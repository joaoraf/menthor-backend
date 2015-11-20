package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class MediationDependency implements SyntacticalRule {

    MediationDependency(Relationship self){
        this.errorMsg = 'The mediated end (target) of a Mediation must be dependent'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetDependent(self, "isMediation")
    }
}
