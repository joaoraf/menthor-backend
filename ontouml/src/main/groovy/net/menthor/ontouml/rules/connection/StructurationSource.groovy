package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class StructurationSource implements SyntacticalRule {

    StructurationSource(Relationship self){
        this.errorMsg = 'The structured type (source) of a Structuration must be a Quality'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceClassMustBe(self, "isStructuration", "isQualitativeIntrinsicMoment")
    }
}
