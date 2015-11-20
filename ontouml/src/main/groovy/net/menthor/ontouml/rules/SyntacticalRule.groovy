package net.menthor.ontouml.rules

import net.menthor.mcore.traits.MClassifier
import net.menthor.ontouml.checker.SyntacticalError

trait SyntacticalRule {

    MClassifier self
    boolean ruleActived=true
    String errorMsg

    abstract boolean condition()

    SyntacticalError check() {
        def itsTrue = condition()
        if(!itsTrue) return new SyntacticalError(self, errorMsg)
        return null
    }
}
