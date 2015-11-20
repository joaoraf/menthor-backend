package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.TransitivityValue

class ComponentOfValues implements SyntacticalRule {

    ComponentOfValues(Relationship self){
        this.errorMsg = 'A ComponentOf must be Non-Reflexive and Non-Transitive'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isComponentOf", ReflexivityValue.NON_REFLEXIVE, null,
            TransitivityValue.NON_TRANSITIVE, null)
    }
}
