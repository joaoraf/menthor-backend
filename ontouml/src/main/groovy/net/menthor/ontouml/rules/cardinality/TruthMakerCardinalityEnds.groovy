package net.menthor.ontouml.rules.cardinality

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.stereotypes.RelationshipStereotype

class TruthMakerCardinalityEnds implements SyntacticalRule {

    TruthMakerCardinalityEnds(Class self){
        this.errorMsg = 'The sum of the minimum cardinalities of the mediated ends of a Truth Maker (element with a relator identity) must be greater or equal to 2'
        this.self = self
    }

    @Override
    boolean condition() {
        def sum = 0
        if((self as Class).isTruthMaker()){
            def oppositeEndPoints = self.allOppositeEndPoints().findAll{ ep ->
                (ep.getOwner() as Relationship).getStereotype() == RelationshipStereotype.MEDIATION
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