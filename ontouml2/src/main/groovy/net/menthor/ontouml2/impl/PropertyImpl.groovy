package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Property;

abstract class PropertyImpl extends NamedElementImpl implements Property {

    boolean isOrdered
    boolean isDerived
    int lowerBound
    int upperBound
    boolean isDependency
}
