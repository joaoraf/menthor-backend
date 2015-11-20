package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.Class
class RoleMixinConnectedToMediationOrParticipation implements SyntacticalRule {

    RoleMixinConnectedToMediationOrParticipation(Class self){
        this.errorMsg = 'A RoleMixin must be connected (directly or indirectly) to a Mediation or a Participation'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self,"isRoleMixin","isParticipation") ||
               GenericCondition.typeMustBeConnectedToRelationship(self,"isRoleMixin","isMediation")
    }
}
