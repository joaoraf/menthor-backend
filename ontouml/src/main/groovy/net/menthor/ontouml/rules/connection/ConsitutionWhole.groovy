package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class ConsitutionWhole implements SyntacticalRule {

    ConsitutionWhole(Relationship self){
        this.errorMsg = 'The whole of a Consitution must be a Functional Complex (have a kind identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.wholeMustBe(self, "isConstitution", "isFunctionalComplex")
    }
}