package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class MemberOfWhole implements SyntacticalRule {

    MemberOfWhole(Relationship self){
        this.errorMsg = 'The whole of a MemberOf must be a Collection (have a collective identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.wholeMustBe(self, "isMemberOf", "isCollection")
    }
}
