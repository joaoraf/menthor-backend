package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.SyntacticalRule

class MemberOfAndExtensionalWhole implements SyntacticalRule {

    MemberOfAndExtensionalWhole(Relationship self){
        this.errorMsg = 'A MemberOf with essential part must imply in an extensional whole Collection (class with a collective identity))'
        this.self = self
    }

    @Override
    boolean condition() {
        if(self.isMemberOf() && self.isPartEssential() && self.wholeClass()!=null){
            return self.wholeClass().isExtensional() == true
        }
        return true
    }
}