package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class ParticipationDependency implements SyntacticalRule {

    ParticipationDependency(Relationship self){
        this.errorMsg = 'The participant end (target) of a Participation must be set dependent.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetDependent(self, "isParticipation")
    }
}