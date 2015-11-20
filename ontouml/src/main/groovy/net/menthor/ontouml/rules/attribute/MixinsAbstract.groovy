package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class MixinsAbstract implements SyntacticalRule {

    MixinsAbstract(Class self){
        this.errorMsg = 'A Mixin class (Category, PhaseMixin, RoleMixin, Mixin) must always be abstract'
        this.self = self
    }

    @Override
    boolean condition() {
        if(self.isMixinClass()) {
            return self.isAbstract_()
        }
        return true
    }
}