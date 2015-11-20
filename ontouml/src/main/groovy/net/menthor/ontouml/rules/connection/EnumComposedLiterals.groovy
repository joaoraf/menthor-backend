package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.DataType
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class EnumComposedLiterals implements SyntacticalRule {

    EnumComposedLiterals(DataType self){
        this.errorMsg = 'An Enumeration must have at least two owned Enumeration Literals'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeComposedByAtLeastTwoElements(self, "isEnumeration","getLiterals", 2)
    }
}
