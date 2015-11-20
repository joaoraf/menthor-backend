package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class CharacterizationSource implements SyntacticalRule {

    CharacterizationSource(Relationship self){
        this.errorMsg = 'The characterizing type (source) of a Characterization must be a Mode or Quality'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceClassMustBe(self, "isCharacterization", "isIntrinsicMoment")
    }
}