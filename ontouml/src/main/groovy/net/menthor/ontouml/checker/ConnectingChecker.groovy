package net.menthor.ontouml.checker

import net.menthor.ontouml.Class
import net.menthor.ontouml.DataType
import net.menthor.ontouml.Relationship
import net.menthor.mcore.traits.MClassifier
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.rules.connection.CausationSourceAndTarget
import net.menthor.ontouml.rules.connection.CharacterizationSource
import net.menthor.ontouml.rules.connection.CharacterizationTarget
import net.menthor.ontouml.rules.connection.ComponentOfPart
import net.menthor.ontouml.rules.connection.ComponentOfWhole
import net.menthor.ontouml.rules.connection.ConsitutionPart
import net.menthor.ontouml.rules.connection.ConsitutionWhole
import net.menthor.ontouml.rules.connection.DimensionConnectedToStructuration
import net.menthor.ontouml.rules.connection.DomainComposedDimensions
import net.menthor.ontouml.rules.connection.DomainConnectedToStructuration
import net.menthor.ontouml.rules.connection.EnumComposedLiterals
import net.menthor.ontouml.rules.connection.EnumConnectedToStructuration
import net.menthor.ontouml.rules.connection.EventConnectedToParticipation
import net.menthor.ontouml.rules.connection.HighOrderConnectedToInstanceOf
import net.menthor.ontouml.rules.connection.InstanceOfSource
import net.menthor.ontouml.rules.connection.InstanceOfTarget
import net.menthor.ontouml.rules.connection.MaterialSourceAndTarget
import net.menthor.ontouml.rules.connection.MediationSource
import net.menthor.ontouml.rules.connection.MediationTarget
import net.menthor.ontouml.rules.connection.MemberOfPart
import net.menthor.ontouml.rules.connection.MemberOfWhole
import net.menthor.ontouml.rules.connection.ModeConnectedToCharacterization
import net.menthor.ontouml.rules.connection.ParticipationSourceAndTarget
import net.menthor.ontouml.rules.connection.QuaPartOfPart
import net.menthor.ontouml.rules.connection.QuaPartOfWhole
import net.menthor.ontouml.rules.connection.QualityConnectedToStructuration
import net.menthor.ontouml.rules.connection.RelatorConnectedToMediation
import net.menthor.ontouml.rules.connection.RoleConnectedToMediationOrParticipation
import net.menthor.ontouml.rules.connection.RoleMixinConnectedToMediationOrParticipation
import net.menthor.ontouml.rules.connection.StructurationSource
import net.menthor.ontouml.rules.connection.StructurationTarget
import net.menthor.ontouml.rules.connection.SubCollectionOfPart
import net.menthor.ontouml.rules.connection.SubCollectionOfWhole
import net.menthor.ontouml.rules.connection.SubEventOfPartAndWhole
import net.menthor.ontouml.rules.connection.SubQuantityOfPart
import net.menthor.ontouml.rules.connection.SubQuantityOfWhole
import net.menthor.ontouml.rules.connection.TemporalSourceAndTarget

class ConnectingChecker {

    static List<SyntacticalError> check(MClassifier self){
        def rules = []
        def errors = []
        if(self instanceof Class) rules += getRules(self as Class)
        if(self instanceof DataType) rules += getRules(self as DataType)
        if(self instanceof Relationship) rules += getRules(self as Relationship)
        rules.each{ rule ->
            errors += (rule as SyntacticalRule).check()
        }
        return errors - null
    }

    static List<SyntacticalRule> getRules(Class self){
        def list = []
        list += new ModeConnectedToCharacterization(self)
        list += new QualityConnectedToStructuration(self)
        list += new RelatorConnectedToMediation(self)
        list += new HighOrderConnectedToInstanceOf(self)
        list += new EventConnectedToParticipation(self)
        list += new RoleConnectedToMediationOrParticipation(self)
        list += new RoleMixinConnectedToMediationOrParticipation(self)
        return list - null
    }

    static List<SyntacticalRule> getRules(DataType self){
        def list = []
        list += new DomainComposedDimensions(self)
        list += new DomainConnectedToStructuration(self)
        list += new EnumComposedLiterals(self)
        list += new EnumConnectedToStructuration(self)
        list += new DimensionConnectedToStructuration(self)
        return list - null
    }

    static List<SyntacticalRule> getRules(Relationship self){
        def list = []
        list += new MediationSource(self)
        list += new StructurationSource(self)
        list += new CharacterizationSource(self)
        list += new CharacterizationTarget(self)
        list += new ComponentOfPart(self)
        list += new ComponentOfWhole(self)
        list += new MemberOfPart(self)
        list += new MemberOfWhole(self)
        list += new SubCollectionOfPart(self)
        list += new SubCollectionOfWhole(self)
        list += new SubQuantityOfPart(self)
        list += new SubQuantityOfWhole(self)
        list += new ConsitutionPart(self)
        list += new ConsitutionWhole(self)
        list += new QuaPartOfPart(self)
        list += new QuaPartOfWhole(self)
        list += new InstanceOfSource(self)
        list += new InstanceOfTarget(self)
        list += new ParticipationSourceAndTarget(self)
        list += new TemporalSourceAndTarget(self)
        list += new CausationSourceAndTarget(self)
        list += new MaterialSourceAndTarget(self)
        list += new SubEventOfPartAndWhole(self)
        list += new MediationTarget(self)
        list += new StructurationTarget(self)
        return list - null
    }
}
