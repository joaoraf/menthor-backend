package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Classifier
import net.menthor.ontouml2.Generalization
import net.menthor.ontouml2.GeneralizationSet;

class GeneralizationImpl extends ContainedElementImpl implements Generalization {

    Classifier general
    Classifier specific
    GeneralizationSet generalizationSet
}
