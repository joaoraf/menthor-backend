package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubQuantityOfEssentialPart implements SyntacticalRule {

    SubQuantityOfEssentialPart(Relationship self){
        this.errorMsg = 'The part of a SubQuantitOf is always essential if the whole is rigid'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttributeIfWholeIs(self, "isSubQuantityOf", "isPartEssential", true, "isRigid")
    }
}