package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.values.TransitivityValue

class MeetsValues implements SyntacticalRule {

    MeetsValues(Relationship self){
        this.errorMsg = 'A Temporal {meets} must be Irreflexive, Anti-Symmetric, Instransitive and Acyclic'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isMeets",
            ReflexivityValue.IRREFLEXIVE,
            SymmetryValue.ANTI_SYMMETRIC,
            TransitivityValue.INTRANSITIVE,
            CiclicityValue.ACYCLIC)
    }
}
