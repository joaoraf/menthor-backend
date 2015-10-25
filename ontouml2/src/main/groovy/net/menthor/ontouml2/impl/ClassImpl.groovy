package net.menthor.ontouml2.impl

import net.menthor.ontouml2.stereo.ClassStereotype
import net.menthor.ontouml2.stereo.QualityStereotype
import net.menthor.ontouml2.value.ClassificationValue
import net.menthor.ontouml2.value.ExistenceValue;

class ClassImpl extends TypeImpl implements net.menthor.ontouml2.Class {

    ClassStereotype stereotype
    boolean isAbstract
    boolean isDerived

    //Collective
    boolean isExtensional

    //Quality
    QualityStereotype qualityStereotype

    //Identity Providers
    ExistenceValue existenceValue

    //Anti-Rigid Classes
    ClassificationValue classificationValue
}
