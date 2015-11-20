package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class QuaPartOfImmutableWhole implements SyntacticalRule {

    QuaPartOfImmutableWhole(Relationship self){
        this.errorMsg = 'The whole of a QuaPartOf is always immutable if the part is non-rigid'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttributeIfPartIs(self, "isQuaPartOf", "isWholeImmutable", true, "isNonRigid")
    }
}