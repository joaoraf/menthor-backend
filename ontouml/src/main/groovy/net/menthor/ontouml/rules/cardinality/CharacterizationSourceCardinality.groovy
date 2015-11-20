package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class CharacterizationSourceCardinality implements SyntacticalRule {

    CharacterizationSourceCardinality(Relationship self){
        this.errorMsg = 'The characterizing end (source) of a Characterization must have (minimum and maximum) cardinality of exactly 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceMultiplicity(self, "isCharacterization", 1, 1)
    }
}
