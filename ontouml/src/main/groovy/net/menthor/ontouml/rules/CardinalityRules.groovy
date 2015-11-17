package net.menthor.ontouml.rules

import net.menthor.ontouml.Class
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.ontouml.traits.Classifier

class CardinalityRules extends GenericRules {

    static List<SyntacticalError> check(Classifier self){
        if(self instanceof Class) return check(self as Class)
        if(self instanceof Relationship) return check(self as Relationship)
        return []
    }

    static List<SyntacticalError> check(Class self) {
        def list = []
        list += truthMakerCardinalityEnds(self)
        list += weakSupplementation(self)
        return list - null
    }

    static List<SyntacticalError> check(Relationship self){
        def list = []
        list += mediationTargetCardinality(self)
        list += characterizationSourceCardinality(self)
        list += characterizationTargetCardinality(self)
        list += instanceOfTargetCardinality(self)
        list += subCollectionPartCardinality(self)
        list += subQuantityWholeCardinality(self)
        list += subQuantityPartCardinality(self)
        list += structurationTargetCardinality(self)
        list += derivationSourceCardinality(self)
        list += derivationTargetCardinality(self)
        list += quaPartOfWholeCardinality(self)
        return list - null
    }

    static SyntacticalError mediationTargetCardinality(Relationship self){
        def errormsg = 'The mediated end (target) of a Mediation must have minimum cardinality of 1.'
        def itsTrue = targetMultiplicity(self, "isMediation", 1, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError characterizationSourceCardinality(Relationship self){
        def errormsg = 'The characterizing end (source) of a Characterization must have (minimum and maximum) cardinality of exactly 1.'
        def itsTrue = sourceMultiplicity(self, "isCharacterization", 1, 1)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError characterizationTargetCardinality(Relationship self){
        def errormsg = 'The characterized end (target) of a Characterization must have minimum cardinality of 1.'
        def itsTrue = targetMultiplicity(self, "isCharacterization", 1, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError instanceOfTargetCardinality(Relationship self){
        def errormsg = 'The high order end (target) of a InstanceOf must have minimum cardinality of 1.'
        def itsTrue = targetMultiplicity(self, "isInstanceOf", 1, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subCollectionPartCardinality(Relationship self){
        def errormsg = 'The subCollection end (part) of a SubCollectionOf must have maximium cardinality of 1.'
        def itsTrue = partMultiplicity(self, "isSubCollectionOf", null, 1)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subQuantityWholeCardinality(Relationship self){
        def errormsg = 'The amount of matter whole (source) of a SubQuantityOf must have maximum cardinality of 1.'
        def itsTrue = wholeMultiplicity(self, "isSubQuantityOf", null, 1)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subQuantityPartCardinality(Relationship self){
        def errormsg = 'The amount of matter part (target) of a SubQuantityOf must have maximum cardinality of 1.'
        def itsTrue = partMultiplicity(self, "isSubQuantityOf", null, 1)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError structurationTargetCardinality(Relationship self){
        def errormsg = 'The structure (domain, dimension) end of a Structuration must have cardinality (maximum and minimum) of exactly 1.'
        def itsTrue = targetMultiplicity(self, "isStructuration", 1, 1)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError derivationSourceCardinality(Relationship self){
        def errormsg = 'The material end of a Derivation must have minimum cardinality of 1.'
        def itsTrue = sourceMultiplicity(self, "isDerivation", 1, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError derivationTargetCardinality(Relationship self){
        def errormsg = 'The relator end of a Derivation must have minimum cardinality of 1.'
        def itsTrue = sourceMultiplicity(self, "isDerivation", 1, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError quaPartOfWholeCardinality(Relationship self){
        def errormsg = 'The relator end (whole) of a QuaPartOf must have cardinality (maximum and minimum) of exactly 1.'
        def itsTrue = wholeMultiplicity(self, "isQuaPartOf", 1, 1)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError truthMakerCardinalityEnds(Class self){
        def errormsg = 'The sum of the minimum cardinalities of the mediated ends of a Truth Maker (element with a relator identity) must be greater or equal to 2'
        def itsTrue = true
        def sum = 0
        if(self.isTruthMaker()){
            def oppositeEndPoints = self.allOppositeEndPoints(RelationshipStereotype.MEDIATION)
            if(oppositeEndPoints.size()==0) return null
            oppositeEndPoints.each { ep ->
                sum += ep.getLowerBound()
            }
        }else{
            return null
        }
        if(sum>=2) itsTrue = true
        else itsTrue = false
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError weakSupplementation(Class self){
        def errormsg = 'The sum of the minimum cardinalities of the part ends of a Whole (ComponentOf, MemberOf, SubCollectionOf, SubQuantityOf, SubEventOf, QuaPartOf) must be greater or equal to 2'
        def itsTrue = true
        def sum = 0
        def oppositeEndPoints = self.allOppositeEndPoints().findAll{ ep ->
            ep.getOwner().isMeronymic()
        }
        if(oppositeEndPoints.size()==0) return null
        oppositeEndPoints.each{ e ->
            sum += e.getLowerBound()
        }
        if(sum>=2) itsTrue = true
        else itsTrue = false
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }
}
