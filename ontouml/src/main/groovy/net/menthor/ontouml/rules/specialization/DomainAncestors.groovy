package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.DataType
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class DomainAncestors implements SyntacticalRule {

    DomainAncestors(DataType self){
        this.errorMsg = 'A Domain can only have a Domain ancestor'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.includesAsAncestorsOnly(self, "isDomain")
    }
}
