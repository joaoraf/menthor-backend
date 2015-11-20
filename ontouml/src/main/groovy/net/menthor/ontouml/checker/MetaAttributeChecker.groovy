package net.menthor.ontouml.checker

import net.menthor.ontouml.Class
import net.menthor.ontouml.Relationship
import net.menthor.mcore.traits.MClassifier
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.rules.attribute.CausationDependency
import net.menthor.ontouml.rules.attribute.CharacterizationDependency
import net.menthor.ontouml.rules.attribute.CollectionWithEssentialParts
import net.menthor.ontouml.rules.attribute.DerivationDependency
import net.menthor.ontouml.rules.attribute.InstanceOfDependency
import net.menthor.ontouml.rules.attribute.MediationDependency
import net.menthor.ontouml.rules.attribute.MemberOfAndExtensionalWhole
import net.menthor.ontouml.rules.attribute.MixinsAbstract
import net.menthor.ontouml.rules.attribute.ParticipationDependency
import net.menthor.ontouml.rules.attribute.QuaPartOfEssentialPart
import net.menthor.ontouml.rules.attribute.QuaPartOfImmutablePart
import net.menthor.ontouml.rules.attribute.QuaPartOfImmutableWhole
import net.menthor.ontouml.rules.attribute.QuaPartOfInseparablePart
import net.menthor.ontouml.rules.attribute.QuaPartOfNonShareable
import net.menthor.ontouml.rules.attribute.SubEventDependency
import net.menthor.ontouml.rules.attribute.SubEventOfEssentialPart
import net.menthor.ontouml.rules.attribute.SubEventOfImmutablePart
import net.menthor.ontouml.rules.attribute.SubEventOfImmutableWhole
import net.menthor.ontouml.rules.attribute.SubEventOfInseparablePart
import net.menthor.ontouml.rules.attribute.SubQuantityOfEssentialPart
import net.menthor.ontouml.rules.attribute.SubQuantityOfImmutablePart
import net.menthor.ontouml.rules.attribute.SubQuantityOfNonShareable
import net.menthor.ontouml.rules.attribute.TemporalDependency

class MetaAttributeChecker {

    static List<SyntacticalError> check(MClassifier self){
        def rules = []
        def errors = []
        if(self instanceof Class) rules += getRules(self as Class)
        if(self instanceof Relationship) rules += getRules(self as Relationship)
        rules.each{ rule ->
            errors += (rule as SyntacticalRule).check()
        }
        return errors-null
    }

    static List<SyntacticalRule> getRules(Class self){
        def list = []
        list += new MixinsAbstract(self)
        list += new CollectionWithEssentialParts(self)
        return list - null
    }

    static List<SyntacticalRule> getRules(Relationship self){
        def list = []
        list += new MemberOfAndExtensionalWhole(self)
        list += new DerivationDependency(self)
        list += new TemporalDependency(self)
        list += new CausationDependency(self)
        list += new SubEventDependency(self)
        list += new MediationDependency(self)
        list += new CharacterizationDependency(self)
        list += new ParticipationDependency(self)
        list += new InstanceOfDependency(self)
        list += new SubQuantityOfNonShareable(self)
        list += new QuaPartOfNonShareable(self)
        list += new SubQuantityOfEssentialPart(self)
        list += new SubQuantityOfImmutablePart(self)
        list += new SubEventOfEssentialPart(self)
        list += new SubEventOfImmutablePart(self)
        list += new QuaPartOfEssentialPart(self)
        list += new QuaPartOfImmutablePart(self)
        list += new SubEventOfInseparablePart(self)
        list += new SubEventOfImmutableWhole(self)
        list += new QuaPartOfInseparablePart(self)
        list += new QuaPartOfImmutableWhole(self)
        return list - null
    }
}
