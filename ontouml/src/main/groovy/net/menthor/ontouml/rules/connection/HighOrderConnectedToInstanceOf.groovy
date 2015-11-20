package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.Class
class HighOrderConnectedToInstanceOf implements SyntacticalRule {

    HighOrderConnectedToInstanceOf(Class self){
        this.errorMsg = 'A HighOrder must be connected (directly or indirectly) to a InstanceOf'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self,"isHighOrder","isInstanceOf")
    }
}