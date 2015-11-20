package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.DataType
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class DomainConnectedToStructuration implements SyntacticalRule {

    DomainConnectedToStructuration(DataType self){
        this.errorMsg = 'A Domain must be connected (directly or indirectly) to a Structuration'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self,"isDomain","isStructuration")
    }
}