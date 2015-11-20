package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class PhaseMustNotSpecialize implements SyntacticalRule {

    PhaseMustNotSpecialize(Class self){
        this.errorMsg = 'A Phase cannot specialize (directly) a Category or RoleMixin'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.excludesAsParent(self, "isPhase","isRoleMixin", "isCategory")
    }
}