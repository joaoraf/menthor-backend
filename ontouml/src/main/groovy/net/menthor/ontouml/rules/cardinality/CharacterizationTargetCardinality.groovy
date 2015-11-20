package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class CharacterizationTargetCardinality implements SyntacticalRule {

    CharacterizationTargetCardinality(Relationship self){
        this.errorMsg = 'The characterized end (target) of a Characterization must have minimum cardinality of 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.targetMultiplicity(self, "isCharacterization", 1, null)
    }
}