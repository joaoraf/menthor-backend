package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Generalization
import net.menthor.ontouml2.GeneralizationSet;

class GeneralizationSetImpl extends NamedElementImpl implements GeneralizationSet {

    boolean isCovering
    boolean isDisjoint
    List<Generalization> generalizations
    Class highorder
}
