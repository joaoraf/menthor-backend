package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class ComponentOfWhole implements SyntacticalRule {

    ComponentOfWhole(Relationship self){
        this.errorMsg = 'The whole of a ComponentOf must be a Functional Complex (have a kind identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.wholeMustBe(self, "isComponentOf", "isFunctionalComplex")
    }
}