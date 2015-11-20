package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class QuaPartOfPart implements SyntacticalRule {

    QuaPartOfPart(Relationship self){
        this.errorMsg = 'The part of a QuaPartOf must be a non-measurable Instrinsic Moment (have a mode identity)'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.partMustBe(self, "isQuaPartOf", "isNonQualitativeIntrinsicMoment")
    }
}