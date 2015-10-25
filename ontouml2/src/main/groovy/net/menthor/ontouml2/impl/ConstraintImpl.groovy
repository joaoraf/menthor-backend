package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Constraint
import net.menthor.ontouml2.stereo.ConstraintStereotype;

class ConstraintImpl extends ContainedElementImpl implements Constraint {

    ConstraintStereotype stereotype
    String context
    String expression
}
