package net.menthor.ontouml2.impl

import net.menthor.ontouml2.DataType
import net.menthor.ontouml2.Literal;

class LiteralImpl extends ElementImpl implements Literal {

    String text
    DataType owner
    float upperBoundRegion
    float lowerBoundRegion
}
