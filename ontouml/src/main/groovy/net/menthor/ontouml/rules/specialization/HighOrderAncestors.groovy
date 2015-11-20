package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class HighOrderAncestors implements SyntacticalRule {

    HighOrderAncestors(Class self){
        this.errorMsg = 'A HighOrder can only have a HighOrder ancestor'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.includesAsAncestorsOnly(self, "isHighOrder")
    }
}