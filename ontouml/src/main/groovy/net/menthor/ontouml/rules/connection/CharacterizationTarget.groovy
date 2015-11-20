package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class CharacterizationTarget implements SyntacticalRule {

    CharacterizationTarget(Relationship self){
        this.errorMsg = 'The characterized type (target) of a Characterization must be an Endurant'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetClassMustBe(self, "isCharacterization", "isEndurantClass")
    }
}