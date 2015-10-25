package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Classifier
import net.menthor.ontouml2.Generalization;

abstract class ClassifierImpl extends ContainedElementImpl implements Classifier {

    String uniqueName
    List<String> definitions
    List<String> synonyms
    String text
    List<Generalization> isGeneralIn
    List<Generalization> isSpecificIn
}