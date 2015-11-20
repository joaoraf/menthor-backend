package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class QuaPartOfWhole implements SyntacticalRule {

    QuaPartOfWhole(Relationship self){
        this.errorMsg = 'The whole of a QuaPartOf must be a Truth Maker (have a relator identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.wholeMustBe(self, "isQuaPartOf", "isTruthMaker")
    }
}
