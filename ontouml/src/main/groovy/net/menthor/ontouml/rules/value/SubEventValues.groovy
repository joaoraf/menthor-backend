package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.TransitivityValue

class SubEventValues implements SyntacticalRule {

    SubEventValues(Relationship self){
        this.errorMsg = 'A SubEventOf must be Irreflexive and Transitive'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isSubEventOf", ReflexivityValue.IRREFLEXIVE, null, TransitivityValue.TRANSITIVE, null)
    }
}
