package net.menthor.ontouml2.impl

import net.menthor.ontouml2.EndPoint
import net.menthor.ontouml2.Relationship
import net.menthor.ontouml2.stereo.ParticipationStereotype
import net.menthor.ontouml2.stereo.RelationshipStereotype
import net.menthor.ontouml2.stereo.TemporalStereotype
import net.menthor.ontouml2.value.CiclicityValue
import net.menthor.ontouml2.value.ReflexivityValue
import net.menthor.ontouml2.value.SymmetryValue
import net.menthor.ontouml2.value.TransitivityValue;

class RelationshipImpl extends ClassifierImpl implements Relationship {

    RelationshipStereotype stereotype
    ReflexivityValue reflexivityValue
    SymmetryValue symmetryValue
    TransitivityValue transitivityValue
    CiclicityValue ciclicityValue
    List<EndPoint> endPoints

    //Temporal
    TemporalStereotype temporalStereotype

    //Participation
    ParticipationStereotype participationStereotype
}
