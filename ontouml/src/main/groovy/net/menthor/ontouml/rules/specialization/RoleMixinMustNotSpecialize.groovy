package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class RoleMixinMustNotSpecialize implements SyntacticalRule {

    RoleMixinMustNotSpecialize(Class self){
        this.errorMsg = 'A RoleMixin must not specialize (directly) a PhaseMixin'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.excludesAsParent(self, "isRoleMixin","isPhaseMixin")
    }
}