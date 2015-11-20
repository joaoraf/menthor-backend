package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class IdentityProviders implements SyntacticalRule {

    IdentityProviders(Class self){
        this.errorMsg = 'An identity provider type cannot have another identity provider ancestor or a SubKind ancestor. This leads to conflicting identity criteria.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.excludesAsAncestor(self, "isIdentityProviderClass","isIdentityProviderClass", "isSubKind")
    }
}