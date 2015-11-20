package net.menthor.ontouml.checker

import net.menthor.ontouml.Class
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.rules.cardinality.CharacterizationSourceCardinality
import net.menthor.ontouml.rules.cardinality.CharacterizationTargetCardinality
import net.menthor.ontouml.rules.cardinality.DerivationSourceCardinality
import net.menthor.ontouml.rules.cardinality.DerivationTargetCardinality
import net.menthor.ontouml.rules.cardinality.InstanceOfTargetCardinality
import net.menthor.ontouml.rules.cardinality.MediationTargetCardinality
import net.menthor.ontouml.rules.cardinality.QuaPartOfWholeCardinality
import net.menthor.ontouml.rules.cardinality.StructurationTargetCardinality
import net.menthor.ontouml.rules.cardinality.SubCollectionPartCardinality
import net.menthor.ontouml.rules.cardinality.SubQuantityPartCardinality
import net.menthor.ontouml.rules.cardinality.SubQuantityWholeCardinality
import net.menthor.ontouml.rules.cardinality.TruthMakerCardinalityEnds
import net.menthor.ontouml.rules.cardinality.WeakSupplementation
import net.menthor.mcore.traits.MClassifier

class CardinalityChecker {

    static List<SyntacticalError> check(MClassifier self){
        def errors = []
        def rules = []
        if(self instanceof Class) rules += getRules(self as Class)
        if(self instanceof Relationship) rules += getRules(self as Relationship)
        rules.each{ rule ->
            errors += (rule as SyntacticalRule).check()
        }
        return errors-null
    }

    static List<SyntacticalRule> getRules(Class self) {
        def list = []
        list += new TruthMakerCardinalityEnds(self)
        list += new WeakSupplementation(self)
        return list - null
    }

    static List<SyntacticalRule> getRules(Relationship self){
        def list = []
        list += new MediationTargetCardinality(self)
        list += new CharacterizationSourceCardinality(self)
        list += new CharacterizationTargetCardinality(self)
        list += new InstanceOfTargetCardinality(self)
        list += new SubCollectionPartCardinality(self)
        list += new SubQuantityWholeCardinality(self)
        list += new SubQuantityPartCardinality(self)
        list += new StructurationTargetCardinality(self)
        list += new DerivationSourceCardinality(self)
        list += new DerivationTargetCardinality(self)
        list += new QuaPartOfWholeCardinality(self)
        return list - null
    }
}
