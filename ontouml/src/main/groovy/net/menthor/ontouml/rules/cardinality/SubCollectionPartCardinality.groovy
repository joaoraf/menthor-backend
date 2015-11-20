package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubCollectionPartCardinality implements SyntacticalRule {

    SubCollectionPartCardinality(Relationship self){
        this.errorMsg = 'The subCollection end (part) of a SubCollectionOf must have maximium cardinality of 1.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partMultiplicity(self, "isSubCollectionOf", null, 1)
    }
}