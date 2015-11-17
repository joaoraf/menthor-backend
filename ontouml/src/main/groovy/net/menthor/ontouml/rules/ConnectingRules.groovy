package net.menthor.ontouml.rules

import net.menthor.ontouml.Class
import net.menthor.ontouml.DataType
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.traits.Classifier

class ConnectingRules extends GenericRules {

    static List<SyntacticalError> check(Classifier self){
        if(self instanceof Class) return check(self as Class)
        if(self instanceof DataType) return check(self as DataType)
        if(self instanceof Relationship) return check(self as Relationship)
        return []
    }

    static List<SyntacticalError> check(Class self){
        def list = []
        list += modeConnectedToCharacterization(self)
        list += qualityConnectedToStructuration(self)
        list += relatorConnectedToMediation(self)
        list += highOrderConnectedToInstanceOf(self)
        list += eventConnectedToParticipation(self)
        list += roleConnectedToMediationOrParticipation(self)
        list += roleMixinConnectedToMediationOrParticipation(self)
        return list - null
    }

    static List<SyntacticalError> check(DataType self){
        def list = []
        list += domainComposedDimensions(self)
        list += domainConnectedToStructuration(self)
        list += enumComposedLiterals(self)
        list += enumConnectedToStructuration(self)
        list += dimensionConnectedToStructuration(self)
        return list - null
    }

    static List<SyntacticalError> check(Relationship self){
        def list = []
        list += mediationSource(self)
        list += structurationSource(self)
        list += characterizationSource(self)
        list += characterizationTarget(self)
        list += componentOfPart(self)
        list += componentOfWhole(self)
        list += memberOfPart(self)
        list += memberOfWhole(self)
        list += subCollectionOfPart(self)
        list += subCollectionOfWhole(self)
        list += subQuantityOfPart(self)
        list += subQuantityOfWhole(self)
        list += consitutionPart(self)
        list += consitutionWhole(self)
        list += quaPartOfPart(self)
        list += quaPartOfWhole(self)
        list += instanceOfSource(self)
        list += instanceOfTarget(self)
        list += participationSourceAndTarget(self)
        list += temporalSourceAndTarget(self)
        list += causationSourceAndTarget(self)
        list += materialSourceAndTarget(self)
        list += subEventOfPartAndWhole(self)
        list += mediationTarget(self)
        list += structurationTarget(self)
        return list - null
    }

    //==========================================
    //Syntactical Rules: Must Be Connected
    //==========================================

    static SyntacticalError modeConnectedToCharacterization(Class self){
        def errormsg = 'A Mode must be connected (directly or indirectly) to a Characterization'
        def itsTrue = typeMustBeConnectedToRelationship(self, "isNonQualitativeIntrinsicMoment", "isCharacterization")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError qualityConnectedToStructuration(Class self){
        def errormsg = 'A Quality must be connected (directly or indirectly) to a Structuration'
        def itsTrue =  typeMustBeConnectedToRelationship(self,"isQualitativeIntrinsicMoment", "isStructuration")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError relatorConnectedToMediation(Class self){
        def errormsg = 'A Relator must be connected (directly or indirectly) to a Mediation'
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
    //Syntactical Rules: Connecting Sides
    //==========================================

    static SyntacticalError mediationSource(Relationship self){
        def errormsg = 'The mediating type (source) of a Mediation must be a Truth Maker (relator descedant)'
        def itsTrue = sourceClassMustBe(self, "isMediation", "isTruthMaker")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError structurationSource(Relationship self){
        def errormsg = 'The structured type (source) of a Structuration must be a Quality'
        def itsTrue = sourceClassMustBe(self, "isStructuration", "isQualitativeIntrinsicMoment")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError characterizationSource(Relationship self){
        def errormsg = 'The characterizing type (source) of a Characterization must be a Mode or Quality'
        def itsTrue = sourceClassMustBe(self, "isCharacterization", "isIntrinsicMoment")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError characterizationTarget(Relationship self){
        def errormsg = 'The characterized type (target) of a Characterization must be an Endurant'
        def itsTrue = targetClassMustBe(self, "isCharacterization", "isEndurantClass")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError componentOfPart(Relationship self){
        def errormsg = 'The part of a ComponentOf must be a Functional Complex'
        def itsTrue = partMustBe(self, "isComponentOf", "isFunctionalComplex")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError componentOfWhole(Relationship self){
        def errormsg = 'The whole of a ComponentOf must be a Functional Complex (have a kind identity)'
        def itsTrue = wholeMustBe(self, "isComponentOf", "isFunctionalComplex")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError memberOfPart(Relationship self){
        def errormsg = 'The part of a MemberOf must be a Functional Complex (have a kind identity'
        def itsTrue = partMustBe(self, "isMemberOf", "isFunctionalComplex")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError memberOfWhole(Relationship self){
        def errormsg = 'The whole of a MemberOf must be a Collection (have a collective identity)'
        def itsTrue = wholeMustBe(self, "isMemberOf", "isCollection")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subCollectionOfPart(Relationship self){
        def errormsg = 'The part of a SubCollectionOf must be a Collection (have a collective identity)'
        def itsTrue = partMustBe(self, "isSubCollectionOf", "isCollection")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subCollectionOfWhole(Relationship self){
        def errormsg = 'The whole of a SubCollectionOf must be a Collection (have a collective identity)'
        def itsTrue = wholeMustBe(self, "isSubCollectionOf", "isCollection")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subQuantityOfPart(Relationship self){
        def errormsg = 'The part of a SubQuantityOf must be an Amount of Matter (have a quantity identity)'
        def itsTrue = partMustBe(self, "isSubQuantityOf", "isAmountOfMatter")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subQuantityOfWhole(Relationship self){
        def errormsg = 'The whole of a SubQuantityOf must be an Amount of Matter (have a quantity identity)'
        def itsTrue = wholeMustBe(self, "isSubQuantityOf", "isAmountOfMatter")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError consitutionPart(Relationship self){
        def errormsg = 'The part of a Consitution must be a Collection or an Amount of Matter (have a quantity identity)'
        def itsTrue = partMustBeEither(self, "isConstitution", "isAmountOfMatter", "isCollection")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError consitutionWhole(Relationship self){
        def errormsg = 'The whole of a Consitution must be a Functional Complex (have a kind identity)'
        def itsTrue = wholeMustBe(self, "isConstitution", "isFunctionalComplex")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError quaPartOfPart(Relationship self){
        def errormsg = 'The part of a QuaPartOf must be a non-measurable Instrinsic Moment (have a mode identity)'
        def itsTrue = partMustBe(self, "isQuaPartOf", "isNonQualitativeIntrinsicMoment")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError quaPartOfWhole(Relationship self){
        def errormsg = 'The whole of a QuaPartOf must be a Truth Maker (have a relator identity)'
        def itsTrue = wholeMustBe(self, "isQuaPartOf", "isTruthMaker")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError instanceOfSource(Relationship self){
        def errormsg = 'The source type of an InstanceOf cannot be a High Order Class'
        def itsTrue = sourceClassMustNotBe(self, "isInstanceOf", "isHighOrder")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError instanceOfTarget(Relationship self){
        def errormsg = 'The target type of an InstanceOf must be a HighOrder Class'
        def itsTrue = targetClassMustBe(self, "isInstanceOf", "isHighOrder")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError participationSourceAndTarget(Relationship self){
        def errormsg = 'A Participation must connect an Event to an Endurant'
        def itsTrue = mustConnect(self, "isParticipation", "isEvent", "isEndurantClass")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError temporalSourceAndTarget(Relationship self){
        def errormsg = 'A Temporal must only connect an Event to another Event'
        def itsTrue = mustConnect(self, "isTemporal", "isEvent", "isEvent")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError causationSourceAndTarget(Relationship self){
        def errormsg = 'A Causation must only connect an Event to another Event'
        def itsTrue = mustConnect(self, "isCausation", "isEvent", "isEvent")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError materialSourceAndTarget(Relationship self){
        def errormsg = 'A Material cannot connect a Mode or a Quality'
        def itsTrue = mustNotConnect(self, "isMaterial", "isIntrinsicMoment", "isIntrinsicMoment")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError subEventOfPartAndWhole(Relationship self){
        def errormsg = 'A SubEventOf must only connect an Event to another Event'
        def itsTrue = mustConnect(self, "isSubEventOf", "isEvent", "isEvent")
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError mediationTarget(Relationship self){
        def errormsg = 'The mediated type (target) of a Mediation must be a Rigid Sortal (Kind, Collective Quantity, or SubKind), Category, Role or RoleMixin'
        def itsTrue = true
        if(self.isMediation()){
            itsTrue = self.targetClass()!=null &&
                    (self.targetClass().isSubstanceSortalClass() || self.targetClass().isSubKind() || self.targetClass().isCategory()
                            || self.targetClass().isRole() || self.targetClass().isRoleMixin())
        }
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

    static SyntacticalError structurationTarget(Relationship self){
        def errormsg = 'The structurating type (target) of a Structuration must be a Domain, Dimension, or Enumeration'
        def itsTrue = true
        if(self.isStructuration()){
            itsTrue = self.targetDataType()!=null && (self.targetDataType().isStructure() || self.targetDataType().isEnumeration())
        }
        if(!itsTrue) return new SyntacticalError(self, errormsg)
        return null
    }

}
