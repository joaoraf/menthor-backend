package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.DataType
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class DimensionAncestors implements SyntacticalRule {

    DimensionAncestors(DataType self){
        this.errorMsg = 'A Dimension can only have a Dimension ancestor'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.includesAsAncestorsOnly(self, "isDimension")
    }
}
