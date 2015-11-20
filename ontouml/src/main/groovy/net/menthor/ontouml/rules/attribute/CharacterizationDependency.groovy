package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class CharacterizationDependency implements SyntacticalRule {

    CharacterizationDependency(Relationship self){
        this.errorMsg = 'The characterized end (target) of a Characterization must be set dependent.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetDependent(self, "isCharacterization")
    }
}