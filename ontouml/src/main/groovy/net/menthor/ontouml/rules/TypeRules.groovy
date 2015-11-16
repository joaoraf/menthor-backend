package net.menthor.ontouml.rules

import net.menthor.ontouml.Class
import net.menthor.ontouml.DataType
import net.menthor.ontouml.traits.Type

class TypeRules {

    static List<SyntacticalError> check(Type self){
        if(self instanceof Class) return check(self as Class)
        if(self instanceof DataType) return check(self as DataType)
    }

    static List<SyntacticalError> check(Class self){
        def list = []
        list.add(modeConnectedToCharacterization(self))
        list.add(qualityConnectedToStructuration(self))
        list.add(relatorConnectedToMediation(self))
        list.add(highOrderConnectedToInstanceOf(self))
        list.add(eventConnectedToParticipation(self))
        list.add(roleConnectedToMediationOrParticipation(self))
        list.add(roleMixinConnectedToMediationOrParticipation(self))
        return list - null
    }

    static List<SyntacticalError> check(DataType self){
        def list = []
        list.add(domainComposedDimensions(self))
        list.add(domainConnectedToStructuration(self))
        list.add(enumComposedLiterals(self))
        list.add(enumConnectedToStructuration(self))
        list.add(dimensionConnectedToStructuration(self))
        return list - null
    }

    //==========================================
    //Syntactical Rules: Must Be Connected
    //==========================================

    static SyntacticalError modeConnectedToCharacterization(Class self){
        def errormsg = 'A Mode (Mode, SubKind, Role, Phase, Category, RoleMixin, PhaseMixin, Mixin) must be connected (directly or indirectly) to a Characterization'
        def itsTrue = typeMustBeConnectedToRelationship(self, "isNonQualitativeIntrinsicMoment", "isCharacterization")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError qualityConnectedToStructuration(Class self){
        def errormsg = 'A Quality (Quality, SubKind, Role, Phase, Category, RoleMixin, PhaseMixin, Mixin) must be connected (directly or indirectly) to a Structuration'
        def itsTrue =  typeMustBeConnectedToRelationship(self,"isQualitativeIntrinsicMoment", "isStructuration")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError relatorConnectedToMediation(Class self){
        def errormsg = 'A Relator (Relator, SubKind, Role, Phase, Category, Mixin) must be connected (directly or indirectly) to a Mediation'
        def itsTrue = typeMustBeConnectedToRelationship(self, "isTruthMaker","isMediation")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }


    static SyntacticalError highOrderConnectedToInstanceOf(Class self){
        def errormsg = 'A HighOrder must be connected (directly or indirectly) to a InstanceOf'
        def itsTrue = typeMustBeConnectedToRelationship(self,"isHighOrder","isInstanceOf")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError eventConnectedToParticipation(Class self){
        def errormsg = 'An Event must be connected (directly or indirectly) to a Participation'
        def itsTrue = typeMustBeConnectedToRelationship(self,"isEvent","isParticipation")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError roleConnectedToMediationOrParticipation(Class self){
        def errormsg = 'A Role must be connected (directly or indirectly) to a Mediation or a Participation'
        def itsTrue = typeMustBeConnectedToRelationship(self,"isRole","isParticipation") ||
                      typeMustBeConnectedToRelationship(self,"isRole","isMediation")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError roleMixinConnectedToMediationOrParticipation(Class self){
        def errormsg = 'A RoleMixin must be connected (directly or indirectly) to a Mediation or a Participation'
        def itsTrue = typeMustBeConnectedToRelationship(self,"isRoleMixin","isParticipation") ||
                      typeMustBeConnectedToRelationship(self,"isRoleMixin","isMediation")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError dimensionConnectedToStructuration(DataType self){
        def errormsg = 'A Dimension must be connected (directly or indirectly) to a Structuration or be owned by a Domain'
        def itsTrue = typeMustBeConnectedToRelationship(self,"isDimension","isStructuration") ||
                      self.getOwnerDomain()!=null
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError domainConnectedToStructuration(DataType self){
        def errormsg = 'A Domain must be connected (directly or indirectly) to a Structuration'
        def itsTrue = typeMustBeConnectedToRelationship(self,"isDomain","isStructuration")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError enumConnectedToStructuration(DataType self){
        def errormsg = 'An Enumeration must be connected (directly or indirectly) to a Structuration'
        def itsTrue = typeMustBeConnectedToRelationship(self,"isEnumeration","isStructuration")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    //==========================================
    //Syntactical Rules: Composed by at Least
    //==========================================

    static SyntacticalError domainComposedDimensions(DataType self) {
        def errormsg = 'A Domain must be composed by (own) at least two Dimensions'
        def itsTrue = typeComposedByAtLeastTwoElements(self,"isDomain","getDimensions", 2)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError enumComposedLiterals(DataType self) {
        def errormsg = 'An Enumeration must have at least two owned Enumeration Literals'
        def itsTrue = typeComposedByAtLeastTwoElements(self, "isEnumeration","getLiterals", 2)
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    //==========================================
    //Generic/auxiliar methods
    //==========================================

    /** Returns null if this syntactical error is not found. */
    static private boolean typeMustBeConnectedToRelationship(Type self, String typeStereoCheckMethod, String relStereoCheckMethod){
        if(self."${typeStereoCheckMethod}"()){
            return self.allRelationships().any { r ->
                r."${relStereoCheckMethod}"()
            }
        }
        return true
    }

    /** Returns null if this syntactical error is not found. */
    static private boolean typeComposedByAtLeastTwoElements(Type self, String typeStereoCheckMethod, String listElemMethod, Integer number){
        if(self."${typeStereoCheckMethod}"()) {
            return self."${listElemMethod}"().size() >= number
        }
        return true
    }
}
