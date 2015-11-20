package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.DataType
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class EnumConnectedToStructuration implements SyntacticalRule {

    EnumConnectedToStructuration(DataType self){
        this.errorMsg = 'An Enumeration must be connected (directly or indirectly) to a Structuration'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self,"isEnumeration","isStructuration")
    }
}
