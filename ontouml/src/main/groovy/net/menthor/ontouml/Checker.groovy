package net.menthor.ontouml

import net.menthor.ontouml.rules.TypeRules
import net.menthor.ontouml.traits.Type

class Checker {

    static execute(Model model){
        def result = []
        model.getElements().each { elem ->
            if (elem instanceof Type) {
                result += TypeRules.check(elem)
            }
        }
        println result
    }
}
