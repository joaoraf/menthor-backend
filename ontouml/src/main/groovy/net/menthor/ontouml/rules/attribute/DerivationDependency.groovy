package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class DerivationDependency implements SyntacticalRule {

    DerivationDependency(Relationship self){
        this.errorMsg = 'Both sides of a Derivation must be set dependent'
        this.self = self
    }

    @Override
    boolean condition() {
       return GenericCondition.sourceAndTargetDependents(self, "isDerivation")
    }
}