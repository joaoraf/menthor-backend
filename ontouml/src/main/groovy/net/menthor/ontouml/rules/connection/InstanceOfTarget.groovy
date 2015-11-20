package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class InstanceOfTarget implements SyntacticalRule {

    InstanceOfTarget(Relationship self){
        this.errorMsg = 'The target type of an InstanceOf must be a HighOrder Class'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetClassMustBe(self, "isInstanceOf", "isHighOrder")
    }
}