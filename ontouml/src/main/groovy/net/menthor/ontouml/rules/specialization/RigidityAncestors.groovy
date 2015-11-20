package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class RigidityAncestors implements SyntacticalRule {

    RigidityAncestors(Class self){
        this.errorMsg = 'A Rigid type cannot have an Anti-Rigid type ancestor. This leads to a logic inconsistency.'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.excludesAsAncestor(self, "isRigid","isAntiRigid")
    }
}