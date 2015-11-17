package net.menthor.ontouml.rules

import net.menthor.ontouml.Class
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.traits.Classifier

class MetaAttributeRules extends GenericRules {

    static List<SyntacticalError> check(Classifier self){
        if(self instanceof Class) return check(self as Class)
        if(self instanceof Relationship) return check(self as Relationship)
        return []
    }

    static List<SyntacticalError> check(Class self){
        def list = []
        list += mixinsAreAbstract(self)
        list += collectionWithEssentialParts(self)
        return list - null
    }

    static List<SyntacticalError> check(Relationship self){
        def list = []
        list += memberOfAndExtensionalWhole(self)
        list += derivationDependency(self)
        list += temporalDependency(self)
        list += causationDependency(self)
        list += subEventDependencyDependency(self)
        list += mediationDependency(self)
        list += characterizationDependency(self)
        list += participationDependency(self)
        list += instanceOfDependency(self)
        list += subQuantityOfNonShareable(self)
        list += quaPartOfNonShareable(self)
        list += subQuantityOfEssentialPart(self)
        list += subQuantityOfImmutablePart(self)
        list += subEventOfEssentialPart(self)
        list += subEventOfImmutablePart(self)
        list += quaPartOfEssentialPart(self)
        list += quaPartOfImmutablePart(self)
        list += subEventOfInseparablePart(self)
        list += subEventOfImmutableWhole(self)
        list += quaPartOfInseparablePart(self)
        list += quaPartOfImmutableWhole(self)
        return list - null
    }

    static SyntacticalError mixinsAreAbstract(Class self){
        def errormsg = 'A Mixin class (Category, PhaseMixin, RoleMixin, Mixin) must always be abstract'
        def itsTrue = true
        if(self.isMixinClass()) itsTrue = self.isAbstract_() == true
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError collectionWithEssentialParts(Class self){
        def errormsg = 'All the parts of an extensional Collection must be essential'
        def itsTrue = true
        if(self.isCollection()) {
            itsTrue = self.allRelationships().every{ rel ->
                if(rel.isMeronymic() && rel.wholeClass()!=null && rel.partClass()!=null && rel.wholeClass().equals(self)){
                    rel.isPartEssential()==true
                }
            }
        }
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError memberOfAndExtensionalWhole(Relationship self){
        def errormsg = 'A MemberOf with essential part must imply in an extensional whole Collection (class with a collective identity))'
        def itsTrue = true
        if(self.isMemberOf() && self.isPartEssential() && self.wholeClass()!=null){
            itsTrue = self.wholeClass().isExtensional() == true
        }
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError derivationDependency(Relationship self){
        def errormsg = 'Both sides of a Derivation must be set dependent'
        def itsTrue = sourceAndTargetDependents(self, "isDerivation")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError temporalDependency(Relationship self){
        def errormsg = 'Both sides of a Temporal relationship must be set dependent'
        def itsTrue = sourceAndTargetDependents(self, "isTemporal")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError causationDependency(Relationship self){
        def errormsg = 'Both sides of a Causation must be set dependent'
        def itsTrue = sourceAndTargetDependents(self, "isCausation")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subEventDependencyDependency(Relationship self){
        def errormsg = 'Both sides of a SubEventOf must be set dependent'
        def itsTrue = partAndWholeDependents(self, "isSubEventOf")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError mediationDependency(Relationship self){
        def errormsg = 'The mediated end (target) of a Mediation must be dependent'
        def itsTrue = targetDependent(self, "isMediation")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError characterizationDependency(Relationship self){
        def errormsg = 'The characterized end (target) of a Characterization must be set dependent.'
        def itsTrue = targetDependent(self, "isCharacterization")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError participationDependency(Relationship self){
        def errormsg = 'The participant end (target) of a Participation must be set dependent.'
        def itsTrue = targetDependent(self, "isParticipation")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError instanceOfDependency(Relationship self){
        def errormsg = 'The high order end (target) of a InstanceOf must be set dependent iff the type end (source) is a rigid type.'
        def itsTrue = true
        if(self.isInstanceOf() && self.sourceClass()!=null && self.sourceClass().isRigid()){
            itsTrue = self.targetEndPoint()!=null && self.targetEndPoint().isDependency()
        }
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subQuantityOfNonShareable(Relationship self){
        def errormsg = 'The part of a SubQuantityOf is always non-shareable.'
        def itsTrue = isMetaAttribute(self, "isSubQuantityOf", "isPartShareable", false)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError quaPartOfNonShareable(Relationship self){
        def errormsg = 'The part of a QuaPartOf is always non-shareable.'
        def itsTrue = isMetaAttribute(self, "isQuaPartOf", "isPartShareable", false)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subQuantityOfEssentialPart(Relationship self){
        def errormsg = 'The part of a SubQuantitOf is always essential if the whole is rigid'
        def itsTrue = isMetaAttributeIfWholeIs(self, "isSubQuantityOf", "isPartEssential", true, "isRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subQuantityOfImmutablePart(Relationship self){
        def errormsg = 'The part of a SubQuantitOf is always immutable if the whole is non-rigid'
        def itsTrue = isMetaAttributeIfWholeIs(self, "isSubQuantityOf", "isPartImmutable", true, "isNonRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subEventOfEssentialPart(Relationship self){
        def errormsg = 'The part of a SubEventOf is always essential if the whole is rigid'
        def itsTrue = isMetaAttributeIfWholeIs(self, "isSubEventOf", "isPartEssential", true, "isRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subEventOfImmutablePart(Relationship self){
        def errormsg = 'The part of a SubEventOf is always immutable if the whole is non-rigid'
        def itsTrue = isMetaAttributeIfWholeIs(self, "isSubEventOf", "isPartImmutable", true, "isNonRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError quaPartOfEssentialPart(Relationship self){
        def errormsg = 'The part of a QuaPartOf is always essential if the whole is rigid'
        def itsTrue = isMetaAttributeIfWholeIs(self, "isQuaPartOf", "isPartEssential", true, "isRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError quaPartOfImmutablePart(Relationship self){
        def errormsg = 'The part of a QuaPartOf is always immutable if the whole is non-rigid'
        def itsTrue = isMetaAttributeIfWholeIs(self, "isQuaPartOf", "isPartImmutable", true, "isNonRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subEventOfInseparablePart(Relationship self){
        def errormsg = 'The part of a SubEventOf is always inseparable if the part is rigid'
        def itsTrue = isMetaAttributeIfPartIs(self, "isSubEventOf", "isPartInseparable", true, "isRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subEventOfImmutableWhole(Relationship self){
        def errormsg = 'The whole of a SubEventOf is always immutable if the part is non-rigid'
        def itsTrue = isMetaAttributeIfPartIs(self, "isSubEventOf", "isWholeImmutable", true, "isNonRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError quaPartOfInseparablePart(Relationship self){
        def errormsg = 'The part of a QuaPartOf is always inseparable if the part is rigid'
        def itsTrue = isMetaAttributeIfPartIs(self, "isQuaPartOf", "isPartInseparable", true, "isRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError quaPartOfImmutableWhole(Relationship self){
        def errormsg = 'The whole of a QuaPartOf is always immutable if the part is non-rigid'
        def itsTrue = isMetaAttributeIfPartIs(self, "isQuaPartOf", "isWholeImmutable", true, "isNonRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }
}
