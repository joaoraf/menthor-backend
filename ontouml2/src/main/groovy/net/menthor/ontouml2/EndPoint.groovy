package net.menthor.ontouml2

import net.menthor.ontouml2.traits.Classifier
import net.menthor.ontouml2.traits.Property;

class EndPoint implements Property {

    Relationship owner
    Classifier classifier
    List<EndPoint> subsets
    List<EndPoint> redefines
    List<EndPoint> isSubsettedBy
    List<EndPoint> isRedefinedBy
}
