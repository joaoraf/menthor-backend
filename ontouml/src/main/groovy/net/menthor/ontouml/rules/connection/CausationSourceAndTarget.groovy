package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class CausationSourceAndTarget implements SyntacticalRule {

    CausationSourceAndTarget(Relationship self){
        this.errorMsg = 'A Causation must only connect an Event to another Event'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.mustConnect(self, "isCausation", "isEvent", "isEvent")
    }
}
