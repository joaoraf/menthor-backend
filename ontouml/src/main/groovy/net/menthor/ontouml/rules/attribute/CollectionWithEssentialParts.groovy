package net.menthor.ontouml.rules.attribute

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.SyntacticalRule

class CollectionWithEssentialParts implements SyntacticalRule {

    CollectionWithEssentialParts(Class self){
        this.errorMsg = 'All the parts of an extensional Collection must be essential'
        this.self = self
    }

    @Override
    boolean condition() {
        if(self.isCollection()) {
            return self.allRelationships().every{ rel ->
                if(rel.isMeronymic() && rel.wholeClass()!=null && rel.partClass()!=null && rel.wholeClass().equals(self)){
                    rel.isPartEssential()==true
                }
            }
        }
        return true
    }
}