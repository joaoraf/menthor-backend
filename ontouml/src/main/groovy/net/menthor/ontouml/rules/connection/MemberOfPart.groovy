package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class MemberOfPart implements SyntacticalRule {

    MemberOfPart(Relationship self){
        this.errorMsg = 'The part of a MemberOf must be a Functional Complex (have a kind identity'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partMustBe(self, "isMemberOf", "isFunctionalComplex")
    }
}
