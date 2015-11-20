package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class ModeConnectedToCharacterization implements SyntacticalRule {

    ModeConnectedToCharacterization(Class self){
        this.errorMsg = 'A Mode must be connected (directly or indirectly) to a Characterization'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self, "isNonQualitativeIntrinsicMoment", "isCharacterization")
    }
}
