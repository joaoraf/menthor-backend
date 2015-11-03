package net.menthor.ontouml2

import net.menthor.ontouml2.stereotypes.PrimitiveStereotype
import net.menthor.ontouml2.traits.Property
import net.menthor.ontouml2.traits.Type;

class Attribute implements Property {

    PrimitiveStereotype stereotype
    Type owner

    boolean isInteger() {
        return stereotype==PrimitiveStereotype.INTEGER;
    }
    boolean isBoolean() {
        return stereotype==PrimitiveStereotype.BOOLEAN;
    }
    boolean isReal() {
        return stereotype==PrimitiveStereotype.REAL;
    }
    boolean isDate() {
        return stereotype==PrimitiveStereotype.DATE;
    }
    boolean isDateTime() {
        return stereotype==PrimitiveStereotype.DATE_TIME;
    }
    boolean isString() {
        return stereotype==PrimitiveStereotype.STRING;
    }
}