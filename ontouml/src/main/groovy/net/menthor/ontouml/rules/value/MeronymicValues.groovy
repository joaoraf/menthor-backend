package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue

class MeronymicValues implements SyntacticalRule {

    MeronymicValues(Relationship self){
        this.errorMsg = 'A Parthood (MemberOf, ComponentOf, SubEventOf, SubCollectionOf, SubQuantityOf, Consitution) must be Anti-Symmetric and Acyclic'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isMeronymic", null, SymmetryValue.ANTI_SYMMETRIC, null, CiclicityValue.ACYCLIC)
    }
}