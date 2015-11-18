package net.menthor.ontouml.rules

import net.menthor.ontouml.Model
import net.menthor.ontouml.rules.CardinalityRules
import net.menthor.ontouml.rules.MetaAttributeRules
import net.menthor.ontouml.rules.SpecializingRules
import net.menthor.ontouml.rules.ConnectingRules
import net.menthor.ontouml.rules.SyntacticalError
import net.menthor.ontouml.rules.ValueRules
import net.menthor.ontouml.traits.Classifier
import net.menthor.ontouml.traits.Element

class Checker {

    static private List<SyntacticalError> result = []

    static private checkSpecializingRules(Element elem){ result += SpecializingRules.check(elem) }
    static private checkConnectingRules(Element elem){ result += ConnectingRules.check(elem) }
    static private checkValueRules(Element elem){ result += ValueRules.check(elem) }
    static private checkMetaAttributeRules(Element elem){ result += MetaAttributeRules.check(elem) }
    static private checkCardinalityRules(Element elem){ result += CardinalityRules.check(elem) }

    static List<SyntacticalError> execute(Model model){
        result.clear()
        model.getElements().each { elem ->
            if (elem instanceof Classifier) {
                def thread1 = Thread.start { checkSpecializingRules(elem) }
                def thread2 = Thread.start { checkConnectingRules(elem)}
                def thread3 = Thread.start { checkValueRules(elem)}
                def thread4 = Thread.start { checkMetaAttributeRules(elem)}
                def thread5 = Thread.start { checkCardinalityRules(elem)}
                thread1.join()
                thread2.join()
                thread3.join()
                thread4.join()
                thread5.join()
            }
        }
        return result
    }
}
