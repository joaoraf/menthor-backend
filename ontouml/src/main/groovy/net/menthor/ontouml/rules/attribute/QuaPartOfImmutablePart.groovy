package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class QuaPartOfImmutablePart  implements SyntacticalRule {

    QuaPartOfImmutablePart(Relationship self){
        this.errorMsg = 'The part of a QuaPartOf is always immutable if the whole is non-rigid'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttributeIfWholeIs(self, "isQuaPartOf", "isPartImmutable", true, "isNonRigid")
    }
}