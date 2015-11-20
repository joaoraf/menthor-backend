package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.TransitivityValue

class ConstitutionValues implements SyntacticalRule {

    ConstitutionValues(Relationship self){
        this.errorMsg = 'A Constitution must be Irreflexive and Transitive'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isConstitution", ReflexivityValue.IRREFLEXIVE, null, TransitivityValue.TRANSITIVE, null)
    }
}
