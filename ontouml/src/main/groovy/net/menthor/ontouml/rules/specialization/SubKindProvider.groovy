package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubKindProvider implements SyntacticalRule {

    SubKindProvider(Class self){
        this.errorMsg = 'A SubKind must have exactly one identity provider ancestor. If not, it leads to conflicting (or not having an) identity criteria'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.exactOne(self, "isSubKind","identityProviders")
    }
}