package net.menthor.ontouml2

import net.menthor.ontouml2.traits.Classifier
import net.menthor.ontouml2.traits.ContainedElement;

class GeneralizationSet implements ContainedElement {

    boolean isCovering
    boolean isDisjoint
    List<Generalization> generalizations
    Class highorder

    Classifier general(){
        if(generalizations.size()>0) { return generalizations.get(0).getGeneral(); }
        else { return null; }
    }

    List<Classifier> specifics(){
        def result = []
        generalizations.each{ g ->
            result.add(g.specific);
        }
        return result;
    }
}
