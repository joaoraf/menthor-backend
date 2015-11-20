package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubCollectionOfWhole implements SyntacticalRule {

    SubCollectionOfWhole(Relationship self){
        this.errorMsg = 'The whole of a SubCollectionOf must be a Collection (have a collective identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.wholeMustBe(self, "isSubCollectionOf", "isCollection")
    }
}

