package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class EndurantAncestors implements SyntacticalRule {

    EndurantAncestors(Class self){
        this.errorMsg = 'An Endurant cannot have a Perdurant (Event), DataType (Domain, Dimension, Enumeration, DataType) or HighOrder ancestor.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.includesAsAncestorsOnly(self, "isEndurantClass")
    }
}