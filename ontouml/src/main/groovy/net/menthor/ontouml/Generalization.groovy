package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.ontouml.traits.ContainedElement
import net.menthor.ontouml.traits.Classifier

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Generalization implements ContainedElement {

    protected Classifier general
    protected Classifier specific
    protected GeneralizationSet generalizationSet

    //=============================
    // Getters
    //=============================

    Classifier getGeneral() { return general }

    Classifier getSpecific() { return specific }

    @JsonIgnore
    GeneralizationSet getGeneralizationSet() { return generalizationSet }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setGeneralizationSet(GeneralizationSet gs) {
        generalizationSet = gs
        if(gs==null) return
        //Ensuring the opposite end
        if(!gs.generalizations.contains(this)){
            gs.generalizations.add(this)
        }
    }

    void setGeneral(Classifier general){
        this.general = general
        if(general==null) return
        //Ensuring the opposite end
        if(!general.isGeneralIn.contains(this)){
            general.isGeneralIn.add(this)
        }
    }

    void setSpecific(Classifier specific){
        this.specific = specific
        if(specific == null) return
        //Ensuring the opposite end
        if(!specific.isSpecificIn.contains(this)){
            specific.isSpecificIn.add(this)
        }
    }
}
