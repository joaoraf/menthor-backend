package net.menthor.ontouml2

import net.menthor.ontouml2.traits.Classifier
import net.menthor.ontouml2.traits.ContainedElement;

class Generalization implements ContainedElement {

    Classifier general
    Classifier specific
    GeneralizationSet generalizationSet
}
