package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.values.TransitivityValue

class DuringValues implements SyntacticalRule {

    DuringValues(Relationship self){
        this.errorMsg = 'A Temporal {during} must be Reflexive, Asymmetric, Transitive and Acyclic'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isDuring",
            ReflexivityValue.REFLEXIVE,
            SymmetryValue.ASSYMETRIC,
            TransitivityValue.TRANSITIVE,
            CiclicityValue.ACYCLIC)
    }
}