package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class InstanceOfDependency implements SyntacticalRule {

    InstanceOfDependency(Relationship self){
        this.errorMsg = 'The high order end (target) of a InstanceOf must be set dependent iff the type end (source) is a rigid type.'
        this.self = self
    }

    @Override
    boolean condition() {
        if(self.isInstanceOf() && self.sourceClass()!=null && self.sourceClass().isRigid()){
            return self.targetEndPoint()!=null && self.targetEndPoint().isDependency()
        }
        return true
    }
}
