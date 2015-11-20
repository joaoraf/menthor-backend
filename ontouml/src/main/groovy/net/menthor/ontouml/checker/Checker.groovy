package net.menthor.ontouml.checker

import net.menthor.ontouml.Model
import net.menthor.mcore.traits.MClassifier
import net.menthor.mcore.traits.MElement

class Checker {

    static private List<SyntacticalError> result = []

    static private synchronized checkSpecializingRules(MElement elem){ result += SpecializingChecker.check(elem) }
    static private synchronized checkConnectingRules(MElement elem){ result += ConnectingChecker.check(elem) }
    static private synchronized checkValueRules(MElement elem){ result += ValuesChecker.check(elem) }
    static private synchronized checkMetaAttributeRules(MElement elem){ result += MetaAttributeChecker.check(elem) }
    static private synchronized checkCardinalityRules(MElement elem){ result += CardinalityChecker.check(elem) }

    static List<SyntacticalError> execute(Model model){
        result.clear()
        model.getElements().each { elem ->
            if (elem instanceof MClassifier) {
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
