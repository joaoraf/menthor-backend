package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubEventOfImmutablePart implements SyntacticalRule {

    SubEventOfImmutablePart(Relationship self){
        this.errorMsg = 'The part of a SubEventOf is always immutable if the whole is non-rigid'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttributeIfWholeIs(self, "isSubEventOf", "isPartImmutable", true, "isNonRigid")
    }
}
