package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubEventOfPartAndWhole implements SyntacticalRule {

    SubEventOfPartAndWhole(Relationship self){
        this.errorMsg = 'A SubEventOf must only connect an Event to another Event'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.mustConnect(self, "isSubEventOf", "isEvent", "isEvent")
    }
}
