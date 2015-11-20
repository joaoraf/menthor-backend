package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.DataType
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class MediationSource implements SyntacticalRule {

    MediationSource(Relationship self){
        this.errorMsg = 'The mediating type (source) of a Mediation must be a Truth Maker (relator descedant)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.sourceClassMustBe(self, "isMediation", "isTruthMaker")
    }
}