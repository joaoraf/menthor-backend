package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.DataType
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class DomainComposedDimensions implements SyntacticalRule {

    DomainComposedDimensions(DataType self){
        this.errorMsg = 'A Domain must be composed by (own) at least two Dimensions'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.typeComposedByAtLeastTwoElements(self,"isDomain","getDimensions", 2)
    }
}