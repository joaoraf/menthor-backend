package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubQuantityOfImmutablePart implements SyntacticalRule {

    SubQuantityOfImmutablePart(Relationship self){
        this.errorMsg = 'The part of a SubQuantitOf is always immutable if the whole is non-rigid'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttributeIfWholeIs(self, "isSubQuantityOf", "isPartImmutable", true, "isNonRigid")
    }
}