package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Attribute
import net.menthor.ontouml2.Type
import net.menthor.ontouml2.stereo.PrimitiveStereotype;

class AttributeImpl extends PropertyImpl implements Attribute {

    PrimitiveStereotype stereotype
    Type owner
    List<String> definitions
    List<String> synonyms
    String text
}