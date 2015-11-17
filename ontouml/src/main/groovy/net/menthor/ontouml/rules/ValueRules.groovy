package net.menthor.ontouml.rules

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.traits.Classifier
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.values.TransitivityValue

class ValueRules extends GenericRules {

    static List<SyntacticalError> check(Classifier self){
        if(self instanceof Relationship) return check(self as Relationship)
        return []
    }

    static List<SyntacticalError> check(Relationship self){
        def list = []
        list += mediationProperties(self)
        list += characterizationProperties(self)
        list += causationProperties(self)
        list += meronymicProperties(self)
        list += memberOfProperties(self)
        list += componentOfProperties(self)
        list += subCollectionOfProperties(self)
        list += subQuantityOfProperties(self)
        list += subEventProperties(self)
        list += constitutionProperties(self)
        list += precedesProperties(self)
        list += duringProperties(self)
        list += meetsProperties(self)
        list += finishesProperties(self)
        list += startsProperties(self)
        list += equalsProperties(self)
        list += overlapsProperties(self)
        return list - null
    }

    static SyntacticalError mediationProperties(Relationship self){
        def errormsg = 'A Mediation must be Irreflexive'
        def itsTrue = isValue(self, "isMediation", ReflexivityValue.IRREFLEXIVE, null, null, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError characterizationProperties(Relationship self){
        def errormsg = 'A Characterization must be Irreflexive, Anti-Symmetric, Transitive and Acyclic'
        def itsTrue = isValue(self, "isCharacterization", ReflexivityValue.IRREFLEXIVE, SymmetryValue.ANTI_SYMMETRIC, TransitivityValue.TRANSITIVE, CiclicityValue.ACYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError causationProperties(Relationship self){
        def errormsg = 'A Causation must be Irreflexive, Anti-Symmetric, Transitive and Acyclic.'
        def itsTrue = isValue(self, "isCausation", ReflexivityValue.IRREFLEXIVE, SymmetryValue.ANTI_SYMMETRIC, TransitivityValue.TRANSITIVE, CiclicityValue.ACYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError meronymicProperties(Relationship self){
        def errormsg = 'A Parthood (MemberOf, ComponentOf, SubEventOf, SubCollectionOf, SubQuantityOf, Consitution) must be Anti-Symmetric and Acyclic'
        def itsTrue = isValue(self, "isMeronymic", null, SymmetryValue.ANTI_SYMMETRIC, null, CiclicityValue.ACYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError memberOfProperties(Relationship self){
        def errormsg = 'A MemberOf must be Non-Reflexive and Intransitive'
        def itsTrue = isValue(self, "isCausation", ReflexivityValue.NON_REFLEXIVE, null, TransitivityValue.INTRANSITIVE, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError componentOfProperties(Relationship self){
        def errormsg = 'A ComponentOf must be Non-Reflexive and Non-Transitive'
        def itsTrue = isValue(self, "isComponentOf", ReflexivityValue.NON_REFLEXIVE, null, TransitivityValue.NON_TRANSITIVE, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subCollectionOfProperties(Relationship self){
        def errormsg = 'A SubCollectionOf must be Non-Reflexive and Transitive'
        def itsTrue = isValue(self, "isSubCollectionOf", ReflexivityValue.NON_REFLEXIVE, null, TransitivityValue.TRANSITIVE, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subQuantityOfProperties(Relationship self){
        def errormsg = 'A SubQuantityOf must be Non-Reflexive and Transitive'
        def itsTrue = isValue(self, "isSubQuantityOf", ReflexivityValue.NON_REFLEXIVE, null, TransitivityValue.TRANSITIVE, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError constitutionProperties(Relationship self){
        def errormsg = 'A Constitution must be Irreflexive and Transitive'
        def itsTrue = isValue(self, "isConstitution", ReflexivityValue.IRREFLEXIVE, null, TransitivityValue.TRANSITIVE, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subEventProperties(Relationship self){
        def errormsg = 'A SubEventOf must be Irreflexive and Transitive'
        def itsTrue = isValue(self, "isSubEventOf", ReflexivityValue.IRREFLEXIVE, null, TransitivityValue.TRANSITIVE, null)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError precedesProperties(Relationship self){
        def errormsg = 'A Temporal {precedes} must be Irreflexive, Anti-Symmetric, Transitive and Acyclic'
        def itsTrue = isValue(self, "isPrecedes", ReflexivityValue.IRREFLEXIVE, SymmetryValue.ANTI_SYMMETRIC, TransitivityValue.TRANSITIVE, CiclicityValue.ACYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError duringProperties(Relationship self){
        def errormsg = 'A Temporal {during} must be Reflexive, Asymmetric, Transitive and Acyclic'
        def itsTrue = isValue(self, "isDuring", ReflexivityValue.REFLEXIVE, SymmetryValue.ASSYMETRIC, TransitivityValue.TRANSITIVE, CiclicityValue.ACYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError meetsProperties(Relationship self){
        def errormsg = 'A Temporal {meets} must be Irreflexive, Anti-Symmetric, Instransitive and Acyclic'
        def itsTrue = isValue(self, "isMeets", ReflexivityValue.IRREFLEXIVE, SymmetryValue.ANTI_SYMMETRIC, TransitivityValue.INTRANSITIVE, CiclicityValue.ACYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError finishesProperties(Relationship self){
        def errormsg = 'A Temporal {finishes} must be Reflexive, Anti-Symmetric, Transitive and Acyclic'
        def itsTrue = isValue(self, "isFinishes", ReflexivityValue.REFLEXIVE, SymmetryValue.ANTI_SYMMETRIC, TransitivityValue.TRANSITIVE, CiclicityValue.ACYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError startsProperties(Relationship self){
        def errormsg = 'A Temporal {starts} must be Reflexive, Anti-Symmetric, Transitive and Acyclic'
        def itsTrue = isValue(self, "isStarts", ReflexivityValue.REFLEXIVE, SymmetryValue.ANTI_SYMMETRIC, TransitivityValue.TRANSITIVE, CiclicityValue.ACYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError equalsProperties(Relationship self){
        def errormsg = 'A Temporal {equals} must be Reflexive, Symmetric, Transitive, and Cyclic'
        def itsTrue = isValue(self, "isEquals", ReflexivityValue.REFLEXIVE, SymmetryValue.SYMMETRIC, TransitivityValue.TRANSITIVE, CiclicityValue.CYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError overlapsProperties(Relationship self){
        def errormsg = 'A Temporal {overlaps} must be Reflexive, Symmetric, Non-Transitive and Non-Cyclic'
        def itsTrue = isValue(self, "isOverlaps", ReflexivityValue.REFLEXIVE, SymmetryValue.SYMMETRIC, TransitivityValue.NON_TRANSITIVE, CiclicityValue.NON_CYCLIC)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }
}
