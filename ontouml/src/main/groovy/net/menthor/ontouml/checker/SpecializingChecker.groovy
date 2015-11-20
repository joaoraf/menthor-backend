package net.menthor.ontouml.checker

import net.menthor.ontouml.Class
import net.menthor.ontouml.DataType
import net.menthor.mcore.traits.MClassifier
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.rules.specialization.AntiRigidProvider
import net.menthor.ontouml.rules.specialization.DataTypeAncestors
import net.menthor.ontouml.rules.specialization.DimensionAncestors
import net.menthor.ontouml.rules.specialization.DomainAncestors
import net.menthor.ontouml.rules.specialization.EndurantAncestors
import net.menthor.ontouml.rules.specialization.EnumAncestors
import net.menthor.ontouml.rules.specialization.EventAncestors
import net.menthor.ontouml.rules.specialization.HighOrderAncestors
import net.menthor.ontouml.rules.specialization.IdentityProviders
import net.menthor.ontouml.rules.specialization.MixinAncestors
import net.menthor.ontouml.rules.specialization.PhaseMustNotSpecialize
import net.menthor.ontouml.rules.specialization.PhasePartition
import net.menthor.ontouml.rules.specialization.RigidityAncestors
import net.menthor.ontouml.rules.specialization.RoleMixinMustNotSpecialize
import net.menthor.ontouml.rules.specialization.RoleMustNotSpecialize
import net.menthor.ontouml.rules.specialization.SubKindProvider

class SpecializingChecker {

    static List<SyntacticalError> check(MClassifier self){
        def rules = []
        def errors = []
        if(self instanceof Class) rules += getRules(self as Class)
        if(self instanceof DataType) rules += getRules(self as DataType)
        rules.each{ rule ->
            errors += (rule as SyntacticalRule).check()
        }
        return errors - null
    }

    static List<SyntacticalRule> getRules(Class self){
        def list = []
        list += new RigidityAncestors(self)
        list += new MixinAncestors(self)
        list += new RoleMixinMustNotSpecialize(self)
        list += new EventAncestors(self)
        list += new HighOrderAncestors(self)
        list += new EndurantAncestors(self)
        list += new SubKindProvider(self)
        list += new AntiRigidProvider(self)
        list += new RoleMustNotSpecialize(self)
        list += new PhaseMustNotSpecialize(self)
        list += new IdentityProviders(self)
        list += new PhasePartition(self)
        return list - null
    }

    static List<SyntacticalRule> getRules(DataType self){
        def list = []
        list += new DataTypeAncestors(self)
        list += new DimensionAncestors(self)
        list += new DomainAncestors(self)
        list += new EnumAncestors(self)
        return list - null
    }
}
