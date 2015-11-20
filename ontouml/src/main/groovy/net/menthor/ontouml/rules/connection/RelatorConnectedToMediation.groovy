package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class RelatorConnectedToMediation implements SyntacticalRule {

    RelatorConnectedToMediation(Class self){
        this.errorMsg = 'A Relator must be connected (directly or indirectly) to a Mediation'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self, "isTruthMaker","isMediation")
    }
}