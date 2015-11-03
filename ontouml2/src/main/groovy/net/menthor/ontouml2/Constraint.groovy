package net.menthor.ontouml2

import net.menthor.ontouml2.stereotypes.ConstraintStereotype
import net.menthor.ontouml2.traits.ContainedElement;

class Constraint implements ContainedElement {

    ConstraintStereotype stereotype
    String context
    String expression
    String identifier
}
