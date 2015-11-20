package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubCollectionOfPart implements SyntacticalRule {

    SubCollectionOfPart(Relationship self){
        this.errorMsg = 'The part of a SubCollectionOf must be a Collection (have a collective identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partMustBe(self, "isSubCollectionOf", "isCollection")
    }
}
