package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class AntiRigidProvider implements SyntacticalRule {

    AntiRigidProvider(Class self){
        this.errorMsg = 'An Anti-Rigid Sortal must have exactly one identity provider ancestor. If not, this leads to conflicting (or not having an) identity criteria'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.exactOne(self, "isAntiRigid","identityProviders")
    }
}