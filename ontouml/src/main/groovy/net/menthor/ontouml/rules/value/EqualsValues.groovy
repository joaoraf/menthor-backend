package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.values.TransitivityValue

class EqualsValues implements SyntacticalRule {

    EqualsValues(Relationship self){
        this.errorMsg = 'A Temporal {equals} must be Reflexive, Symmetric, Transitive, and Cyclic'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isEquals",
            ReflexivityValue.REFLEXIVE,
            SymmetryValue.SYMMETRIC,
            TransitivityValue.TRANSITIVE,
            CiclicityValue.CYCLIC)
    }
}
