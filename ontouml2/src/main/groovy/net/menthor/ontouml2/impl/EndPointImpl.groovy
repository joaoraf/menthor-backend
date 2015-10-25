package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Classifier
import net.menthor.ontouml2.EndPoint
import net.menthor.ontouml2.Relationship;

class EndPointImpl extends PropertyImpl implements EndPoint {

    Relationship owner
    Classifier classifier
    List<EndPoint> subsets
    List<EndPoint> redefines
    List<EndPoint> isSubsettedBy
    List<EndPoint> isRedefinedBy
}
