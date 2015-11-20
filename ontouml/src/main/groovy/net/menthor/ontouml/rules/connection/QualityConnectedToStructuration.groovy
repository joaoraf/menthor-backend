package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class QualityConnectedToStructuration implements SyntacticalRule {

    QualityConnectedToStructuration(Class self){
        this.errorMsg = 'A Quality must be connected (directly or indirectly) to a Structuration'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeMustBeConnectedToRelationship(self,"isQualitativeIntrinsicMoment", "isStructuration")
    }
}