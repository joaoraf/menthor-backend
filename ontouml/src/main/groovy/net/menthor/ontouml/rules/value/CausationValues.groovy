package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.values.TransitivityValue

class CausationValues implements SyntacticalRule {

    CausationValues(Relationship self){
        this.errorMsg = 'A Causation must be Irreflexive, Anti-Symmetric, Transitive and Acyclic.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isCausation",
            ReflexivityValue.IRREFLEXIVE,
            SymmetryValue.ANTI_SYMMETRIC,
            TransitivityValue.TRANSITIVE,
            CiclicityValue.ACYCLIC)
    }
}