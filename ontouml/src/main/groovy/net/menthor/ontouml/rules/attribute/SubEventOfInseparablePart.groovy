package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class SubEventOfInseparablePart implements SyntacticalRule {

    SubEventOfInseparablePart(Relationship self){
        this.errorMsg = 'The part of a SubEventOf is always inseparable if the part is rigid'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.isMetaAttributeIfPartIs(self, "isSubEventOf", "isPartInseparable", true, "isRigid")
    }
}