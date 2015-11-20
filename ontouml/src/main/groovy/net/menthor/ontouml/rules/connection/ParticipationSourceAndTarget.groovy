package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class ParticipationSourceAndTarget implements SyntacticalRule {

    ParticipationSourceAndTarget(Relationship self){
        this.errorMsg = 'A Participation must connect an Event to an Endurant'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.mustConnect(self, "isParticipation", "isEvent", "isEndurantClass")
    }
}