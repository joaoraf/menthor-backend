package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubQuantityOfPart implements SyntacticalRule {

    SubQuantityOfPart(Relationship self){
        this.errorMsg = 'The part of a SubQuantityOf must be an Amount of Matter (have a quantity identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partMustBe(self, "isSubQuantityOf", "isAmountOfMatter")
    }
}


