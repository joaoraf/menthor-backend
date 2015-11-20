package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class MaterialSourceAndTarget implements SyntacticalRule {

    MaterialSourceAndTarget(Relationship self){
        this.errorMsg = 'A Material cannot connect a Mode or a Quality'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.mustNotConnect(self, "isMaterial", "isIntrinsicMoment", "isIntrinsicMoment")
    }
}
