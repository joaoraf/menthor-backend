package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class ComponentOfPart implements SyntacticalRule {

    ComponentOfPart(Relationship self){
        this.errorMsg = 'The part of a ComponentOf must be a Functional Complex'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partMustBe(self, "isComponentOf", "isFunctionalComplex")
    }
}