package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class RoleMustNotSpecialize implements SyntacticalRule {

    RoleMustNotSpecialize(Class self){
        this.errorMsg = 'A Role cannot specialize (directly) a Category or PhaseMixin'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.excludesAsParent(self, "isRole","isPhaseMixin", "isCategory")
    }
}