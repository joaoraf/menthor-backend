package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.DataType
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class DimensionConnectedToStructuration implements SyntacticalRule {

    DimensionConnectedToStructuration(DataType self){
        this.errorMsg = 'A Dimension must be connected (directly or indirectly) to a Structuration or be owned by a Domain'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self,"isDimension","isStructuration") ||
               (self as DataType).getOwnerDomain()!=null
    }
}