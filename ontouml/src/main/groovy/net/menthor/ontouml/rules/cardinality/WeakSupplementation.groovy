package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.SyntacticalRule

class WeakSupplementation implements SyntacticalRule {

    WeakSupplementation(Class self){
        this.errorMsg = 'The sum of the minimum cardinalities of the part ends of a Whole (ComponentOf, MemberOf, SubCollectionOf, SubQuantityOf, SubEventOf, QuaPartOf) must be greater or equal to 2'
        this.self = self
    }

    @Override
    boolean condition() {
        def sum = 0
        if((self as Class).isTruthMaker()){
            def oppositeEndPoints = self.allOppositeEndPoints().findAll{ ep ->
                (ep.getOwner() as Relationship).isMeronymic()
            }
            if(oppositeEndPoints.size()==0) return true
            oppositeEndPoints.each { ep ->
                sum += ep.getLowerBound()
            }
        }else{
            return true
        }
        if(sum>=2) return true
        else return false
    }
}