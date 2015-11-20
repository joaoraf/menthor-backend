package net.menthor.ontouml.rules.value

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.TransitivityValue

class SubCollectionOfValues implements SyntacticalRule {

    SubCollectionOfValues(Relationship self){
        this.errorMsg = 'A SubCollectionOf must be Non-Reflexive and Transitive'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isValue(self, "isSubCollectionOf", ReflexivityValue.NON_REFLEXIVE, null, TransitivityValue.TRANSITIVE, null)
    }
}