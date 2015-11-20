package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class InstanceOfSource implements SyntacticalRule {

    InstanceOfSource(Relationship self){
        this.errorMsg = 'The source type of an InstanceOf cannot be a High Order Class'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceClassMustNotBe(self, "isInstanceOf", "isHighOrder")
    }
}