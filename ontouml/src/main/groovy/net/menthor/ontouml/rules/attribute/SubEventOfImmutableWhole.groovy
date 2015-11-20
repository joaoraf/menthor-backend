package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubEventOfImmutableWhole implements SyntacticalRule {

    SubEventOfImmutableWhole(Relationship self){
        this.errorMsg = 'The whole of a SubEventOf is always immutable if the part is non-rigid'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttributeIfPartIs(self, "isSubEventOf", "isWholeImmutable", true, "isNonRigid")
    }
}