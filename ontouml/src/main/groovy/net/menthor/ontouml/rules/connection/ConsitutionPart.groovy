package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class ConsitutionPart implements SyntacticalRule {

    ConsitutionPart(Relationship self){
        this.errorMsg = 'The part of a Consitution must be a Collection or an Amount of Matter (have a quantity identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partMustBeEither(self, "isConstitution", "isAmountOfMatter", "isCollection")
    }
}