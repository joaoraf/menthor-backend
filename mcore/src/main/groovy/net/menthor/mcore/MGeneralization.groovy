package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MClassifier
import net.menthor.mcore.traits.MContainedElement

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MGeneralization implements MContainedElement {

    protected MClassifier general
    protected MClassifier specific
    protected MGeneralizationSet generalizationSet

    //=============================
    // Getters
    //=============================

    MClassifier getGeneral() { return general }

    MClassifier getSpecific() { return specific }

    @JsonIgnore
    MGeneralizationSet getGeneralizationSet() { return generalizationSet }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setGeneralizationSet(MGeneralizationSet gs) {
        generalizationSet = gs
        if(gs==null) return
        //Ensuring the opposite end
        if(!gs.generalizations.contains(this)){
            gs.generalizations.add(this)
        }
    }

    void setGeneral(MClassifier general){
        this.general = general
        if(general==null) return
        //Ensuring the opposite end
        if(!general.getIsGeneralIn().contains(this)){
            general.getIsGeneralIn().add(this)
        }
    }

    void setSpecific(MClassifier specific){
        this.specific = specific
        if(specific == null) return
        //Ensuring the opposite end
        if(!specific.getIsSpecificIn().contains(this)){
            specific.getIsSpecificIn().add(this)
        }
    }
}
