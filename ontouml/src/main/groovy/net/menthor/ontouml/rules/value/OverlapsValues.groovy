package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.values.TransitivityValue

class OverlapsValues implements SyntacticalRule {

    OverlapsValues(Relationship self){
        this.errorMsg = 'A Temporal {overlaps} must be Reflexive, Symmetric, Non-Transitive and Non-Cyclic'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isOverlaps",
            ReflexivityValue.REFLEXIVE,
            SymmetryValue.SYMMETRIC,
            TransitivityValue.NON_TRANSITIVE,
            CiclicityValue.NON_CYCLIC)
    }
}
