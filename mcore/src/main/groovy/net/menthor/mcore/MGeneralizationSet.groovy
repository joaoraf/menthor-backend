package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MClassifier
import net.menthor.mcore.traits.MContainedElement

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MGeneralizationSet implements MContainedElement {

    protected boolean covering
    protected boolean disjoint
    protected List<MGeneralization> generalizations = []

    //=============================
    //Getters
    //=============================

    boolean isCovering() { return covering }

    boolean isDisjoint() { return disjoint }

    List<MGeneralization> getGeneralizations() { return generalizations }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setIsCovering(boolean isCovering) { this.covering = isCovering }

    void setIsDisjoint(boolean isDisjoint) { this.disjoint = isDisjoint }

    void setGeneralization(MGeneralization g){
        if(g==null) return
        if(!generalizations.contains(g)){
            generalizations.add(g)
        }
        //Ensure the opposite end
        g.setGeneralizationSet(this)
    }

    void setGeneralizations(List<MGeneralization> gens){
        if(gens==null||gens==[]){
            this.generalizations.clear()
            return
        }
        gens.each { g ->
            setGeneralization(g)
        }
    }

    //=============================
    // General and Specifics
    //=============================

    MClassifier general(){
        if(generalizations.size()>0) { return generalizations.get(0).getGeneral(); }
        else { return null; }
    }

    List<MClassifier> specifics(){
        def result = []
        generalizations.each{ g ->
            result.add(g.specific);
        }
        return result;
    }
}
