package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.DataType
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class EnumAncestors implements SyntacticalRule {

    EnumAncestors(DataType self){
        this.errorMsg = 'An Enumeration can only have an Enumeration ancestor'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.includesAsAncestorsOnly(self, "isEnumeration")
    }
}