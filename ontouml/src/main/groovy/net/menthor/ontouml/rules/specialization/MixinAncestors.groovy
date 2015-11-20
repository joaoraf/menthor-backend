package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class MixinAncestors implements SyntacticalRule {

    MixinAncestors(Class self){
        this.errorMsg = 'A Mixin type must not have a Sortal type ancestor. Mixins are abstract types and can only have mixins as ancestors.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.includesAsAncestorsOnly(self, "isMixinClass")
    }
}