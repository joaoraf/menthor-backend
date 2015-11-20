package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class EventAncestors implements SyntacticalRule {

    EventAncestors(Class self){
        this.errorMsg = 'An Event can only have an Event ancestor'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.includesAsAncestorsOnly(self, "isEvent")
    }
}