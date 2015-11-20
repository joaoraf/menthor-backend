package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.Class

class EventConnectedToParticipation implements SyntacticalRule {

    EventConnectedToParticipation(Class self){
        this.errorMsg = 'An Event must be connected (directly or indirectly) to a Participation'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self,"isEvent","isParticipation")
    }
}
