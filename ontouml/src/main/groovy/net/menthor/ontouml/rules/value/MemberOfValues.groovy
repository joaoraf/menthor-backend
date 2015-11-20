package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.TransitivityValue

class MemberOfValues implements SyntacticalRule {

    MemberOfValues(Relationship self){
        this.errorMsg = 'A MemberOf must be Non-Reflexive and Intransitive'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isCausation", ReflexivityValue.NON_REFLEXIVE, null, TransitivityValue.INTRANSITIVE, null)
    }
}
