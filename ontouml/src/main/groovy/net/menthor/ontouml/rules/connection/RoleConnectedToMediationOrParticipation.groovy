package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.Class

class RoleConnectedToMediationOrParticipation implements SyntacticalRule {

    RoleConnectedToMediationOrParticipation(Class self){
        this.errorMsg = 'A Role must be connected (directly or indirectly) to a Mediation or a Participation'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self,"isRole","isParticipation") ||
               GenericCondition.typeMustBeConnectedToRelationship(self,"isRole","isMediation")
    }
}