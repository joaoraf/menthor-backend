package net.menthor.ontouml.checker

import net.menthor.ontouml.Relationship
import net.menthor.mcore.traits.MClassifier
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.checker.SyntacticalError
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.rules.value.CausationValues
import net.menthor.ontouml.rules.value.CharacterizationValues
import net.menthor.ontouml.rules.value.ComponentOfValues
import net.menthor.ontouml.rules.value.ConstitutionValues
import net.menthor.ontouml.rules.value.DuringValues
import net.menthor.ontouml.rules.value.EqualsValues
import net.menthor.ontouml.rules.value.FinishesValues
import net.menthor.ontouml.rules.value.MediationValues
import net.menthor.ontouml.rules.value.MeetsValues
import net.menthor.ontouml.rules.value.MemberOfValues
import net.menthor.ontouml.rules.value.MeronymicValues
import net.menthor.ontouml.rules.value.OverlapsValues
import net.menthor.ontouml.rules.value.PrecedesValues
import net.menthor.ontouml.rules.value.StartsValues
import net.menthor.ontouml.rules.value.SubCollectionOfValues
import net.menthor.ontouml.rules.value.SubEventValues
import net.menthor.ontouml.rules.value.SubQuantityOfValues

class ValuesChecker extends GenericCondition {

    static List<SyntacticalError> check(MClassifier self){
        def rules = []
        def errors = []
        if(self instanceof Relationship) rules += getRules(self as Relationship)
        rules.each { rule ->
            errors += (rule as SyntacticalRule).check()
        }
        return errors - null
    }

    static List<SyntacticalRule> getRules(Relationship self){
        def list = []
        list += new MediationValues(self)
        list += new CharacterizationValues(self)
        list += new CausationValues(self)
        list += new MeronymicValues(self)
        list += new MemberOfValues(self)
        list += new ComponentOfValues(self)
        list += new SubCollectionOfValues(self)
        list += new SubQuantityOfValues(self)
        list += new SubEventValues(self)
        list += new ConstitutionValues(self)
        list += new PrecedesValues(self)
        list += new DuringValues(self)
        list += new MeetsValues(self)
        list += new FinishesValues(self)
        list += new StartsValues(self)
        list += new EqualsValues(self)
        list += new OverlapsValues(self)
        return list - null
    }
}
