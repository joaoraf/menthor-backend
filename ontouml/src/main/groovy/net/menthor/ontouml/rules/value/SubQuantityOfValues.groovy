package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.TransitivityValue

class SubQuantityOfValues implements SyntacticalRule {

    SubQuantityOfValues(Relationship self){
        this.errorMsg = 'A SubQuantityOf must be Non-Reflexive and Transitive'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isSubQuantityOf", ReflexivityValue.NON_REFLEXIVE, null, TransitivityValue.TRANSITIVE, null)
    }
}
