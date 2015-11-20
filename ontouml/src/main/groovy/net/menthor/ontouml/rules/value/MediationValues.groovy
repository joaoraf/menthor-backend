package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.ReflexivityValue

class MediationValues implements SyntacticalRule {

    MediationValues(Relationship self){
        this.errorMsg = 'A Mediation must be Irreflexive'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isMediation", ReflexivityValue.IRREFLEXIVE, null, null, null)
    }
}