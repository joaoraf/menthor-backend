package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class QuaPartOfNonShareable implements SyntacticalRule {

    QuaPartOfNonShareable(Relationship self){
        this.errorMsg = 'The part of a QuaPartOf is always non-shareable.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttribute(self, "isQuaPartOf", "isPartShareable", false)
    }
}