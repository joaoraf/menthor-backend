package net.menthor.ontouml2

import net.menthor.ontouml2.traits.Classifier
import net.menthor.ontouml2.traits.ContainedElement
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
class GeneralizationSet implements ContainedElement {

    protected boolean covering
    protected boolean disjoint
    protected List<Generalization> generalizations = []
    protected Class highorder

    //=============================
    //Getters
    //=============================

    boolean isCovering() { return covering }

    boolean isDisjoint() { return disjoint }

    List<Generalization> getGeneralizations() { return generalizations }

    Class getHighorder() { return highorder }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setIsCovering(boolean isCovering) { this.covering = isCovering }

    void setIsDisjoint(boolean isDisjoint) { this.disjoint = isDisjoint }

    void setGeneralization(Generalization g){
        if(g==null) return
        if(!generalizations.contains(g)){
            generalizations.add(g)
        }
        //Ensure the opposite end
        g.setGeneralizationSet(this)
    }

    void setGeneralizations(List<Generalization> gens){
        if(gens==null||gens==[]){
            this.generalizations.clear()
            return
        }
        gens.each { g ->
            setGeneralization(g)
        }
    }

    void setHighorder(Class highorder){
        this.highorder = highorder
        if(highorder==null) return
        //Ensuring opposite end
        highorder.generalizationSet = this
    }

    //=============================
    // General and Specifics
    //=============================

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
