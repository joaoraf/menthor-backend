package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubEventDependency implements SyntacticalRule {

    SubEventDependency(Relationship self){
        this.errorMsg = 'Both sides of a SubEventOf must be set dependent'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partAndWholeDependents(self, "isSubEventOf")
    }
}
