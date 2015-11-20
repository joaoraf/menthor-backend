package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class QuaPartOfInseparablePart implements SyntacticalRule {

    QuaPartOfInseparablePart(Relationship self){
        this.errorMsg = 'The part of a QuaPartOf is always inseparable if the part is rigid'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttributeIfPartIs(self, "isQuaPartOf", "isPartInseparable", true, "isRigid")
    }
}