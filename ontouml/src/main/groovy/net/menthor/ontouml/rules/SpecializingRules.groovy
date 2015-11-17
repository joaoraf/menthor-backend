package net.menthor.ontouml.rules

import net.menthor.ontouml.Class
import net.menthor.ontouml.DataType
import net.menthor.ontouml.traits.Classifier

class SpecializingRules extends GenericRules {

    static List<SyntacticalError> check(Classifier self){
        if(self instanceof Class) return check(self as Class)
        if(self instanceof DataType) return check(self as DataType)
        return []
    }

    static List<SyntacticalError> check(Class self){
        def list = []
        list += rigidityAncestors(self)
        list += mixinsAncestors(self)
        list += roleMixinMustNotSpecialize(self)
        list += eventAncestors(self)
        list += highOrderAncestors(self)
        list += endurantAncestors(self)
        list += subKindProvider(self)
        list += antiRigidProvider(self)
        list += roleMustNotSpecialize(self)
        list += phaseMustNotSpecialize(self)
        list += identityProviders(self)
        list += phasePartition(self)
        return list - null
    }

    static List<SyntacticalError> check(DataType self){
        def list = []
        list += datatypeAncestors(self)
        list += dimensionAncestors(self)
        list += domainAncestors(self)
        list += enumAncestors(self)
        return list - null
    }

    //=========================================
    //Rules
    //=========================================

    static SyntacticalError datatypeAncestors(DataType self){
        def errormsg = 'A DataType can only have a DataType ancestor'
        def itsTrue = includesAsAncestorsOnly(self, "isDataType")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError domainAncestors(DataType self){
        def errormsg = 'A Domain can only have a Domain ancestor'
        def itsTrue = includesAsAncestorsOnly(self, "isDomain")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError dimensionAncestors(DataType self){
        def errormsg = 'A Dimension can only have a Dimension ancestor'
        def itsTrue = includesAsAncestorsOnly(self, "isDimension")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError enumAncestors(DataType self){
        def errormsg = 'An Enumeration can only have an Enumeration ancestor'
        def itsTrue = includesAsAncestorsOnly(self, "isEnumeration")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError rigidityAncestors(Class self){
        def errormsg = 'A Rigid type cannot have an Anti-Rigid type ancestor. This leads to a logic inconsistency.'
        def itsTrue = excludesAsAncestor(self, "isRigid","isAntiRigid")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError mixinsAncestors(Class self){
        def errormsg = 'A Mixin type must not have a Sortal type ancestor. Mixins are abstract types and can only have mixins as ancestors.'
        def itsTrue = includesAsAncestorsOnly(self, "isMixinClass")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError roleMixinMustNotSpecialize(Class self){
        def errormsg = 'A RoleMixin must not specialize (directly) a PhaseMixin'
        def itsTrue = excludesAsParent(self, "isRoleMixin","isPhaseMixin")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError eventAncestors(Class self){
        def errormsg = 'An Event can only have an Event ancestor'
        def itsTrue = includesAsAncestorsOnly(self, "isEvent")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError highOrderAncestors(Class self){
        def errormsg = 'A HighOrder can only have a HighOrder ancestor'
        def itsTrue = includesAsAncestorsOnly(self, "isHighOrder")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError endurantAncestors(Class self){
        def errormsg = 'An Endurant cannot have a Perdurant (Event), DataType (Domain, Dimension, Enumeration, DataType) or HighOrder ancestor.'
        def itsTrue = includesAsAncestorsOnly(self, "isEndurantClass")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subKindProvider(Class self){
        def errormsg = 'A SubKind must have exactly one identity provider ancestor. If not, it leads to conflicting (or not having an) identity criteria'
        def itsTrue = exactOne(self, "isSubKind","identityProviders")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError antiRigidProvider(Class self){
        def errormsg = 'An Anti-Rigid Sortal must have exactly one identity provider ancestor. If not, this leads to conflicting (or not having an) identity criteria'
        def itsTrue = exactOne(self, "isAntiRigid","identityProviders")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError roleMustNotSpecialize(Class self){
        def errormsg = 'A Role cannot specialize (directly) a Category or PhaseMixin'
        def itsTrue = excludesAsParent(self, "isRole","isPhaseMixin", "isCategory")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError phaseMustNotSpecialize(Class self){
        def errormsg = 'A Phase cannot specialize (directly) a Category or RoleMixin'
        def itsTrue = excludesAsParent(self, "isPhase","isRoleMixin", "isCategory")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError identityProviders(Class self){
        def errormsg = 'An identity provider type cannot have another identity provider ancestor or a SubKind ancestor. This leads to conflicting identity criteria.'
        def itsTrue = excludesAsAncestor(self, "isIdentityProviderClass","isIdentityProviderClass", "isSubKind")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError phasePartition(Class self){
        def errormsg = 'A Phase must be grouped in exactly one {disjoint, complete} Generalization Set with other Phases (i.e. every phase must be in a partition with at least one more phase).'
        def itsTrue = true
        if(self.isPhase()){
            def gensets = self.getIsSpecificIn().collect{ g -> g.getGeneralizationSet() }
            def value = gensets.size()==1 && gensets.get(0).isCovering() && gensets.get(0).isDisjoint() && gensets.get(0).specifics().every{ s -> (s instanceof Class) && (s as Class).isPhase() }
            itsTrue = value
        }
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }
}
