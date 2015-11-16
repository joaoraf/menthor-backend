package net.menthor.ontouml.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.ontouml.EndPoint
import net.menthor.ontouml.Generalization
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.stereotypes.RelationshipStereotype

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait Classifier implements ContainedElement, NamedElement {

    protected List<Generalization> isGeneralIn = []
    protected List<Generalization> isSpecificIn = []
    protected List<EndPoint> isClassifierIn = []

    //=============================
    // Getters
    //=============================

    @JsonIgnore
    List<Generalization> getIsGeneralIn(){ return isGeneralIn }

    @JsonIgnore
    List<Generalization> getIsSpecificIn(){ return isSpecificIn }

    @JsonIgnore
    List<EndPoint> getIsClassifierIn() { return isClassifierIn }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setIsGeneralIn(Generalization g){
        if(g==null) return
        if(!isGeneralIn.contains(g)){
            isGeneralIn.add(g)
        }
        //Ensuring opposite end
        g.setGeneral(this)
    }

    void setIsGeneralIn(List<Generalization> gens){
        if(gens==null || gens ==[]) {
            isGeneralIn.clear()
            return
        }
        gens.each{ g ->
            setIsGeneralIn(g)
        }
    }

    void setIsSpecificIn(Generalization g){
        if(g==null) return
        if(!isSpecificIn.contains(g)){
            isSpecificIn.add(g)
        }
        //Ensuring opposite end
        g.setSpecific(this)
    }

    void setIsSpecificIn(List<Generalization> gens){
        if(gens==null || gens==[]){
            isSpecificIn.clear()
            return
        }
        gens.each{ g ->
            setIsSpecificIn(g)
        }
    }

    void setIsClassifierIn(EndPoint p){
        if(p==null) return
        if(!isClassifierIn.contains(p)){
            isClassifierIn.add(p)
        }
        //Ensuring opposite end
        p.setClassifier(this)
    }

    void setIsClassifierIn(List<EndPoint> eps){
        if(eps==null || eps == []){
            isClassifierIn.clear()
            return
        }
        eps.each{ p ->
            setIsClassifierIn(p)
        }
    }

    //=============================
    // Children and Parents
    //=============================

    /* Direct children (the descendants) */
    List<Classifier> children(){
        def list = []
        isGeneralIn.each{ g ->
            list.add(g.getSpecific());
        }
        return list;
    }

    /** Direct parents (the ancestors) */
    List<Classifier> parents(){
        def list = []
        isSpecificIn.each{g ->
            list.add(g.getGeneral());
        }
        return list;
    }

    /** All (direct and indirect) parents */
    void allParents(Classifier c, List result){
        c.getIsSpecificIn().each{ g ->
            def Classifier parent = g.getGeneral();
            result.add(parent);
            allParents(parent,result);
        }
    }

    /** All (direct and indirect) parents */
    List<Classifier> allParents(){
        def list = []
        allParents(this, list)
        return list;
    }

    /* All (direct and indirect) children */
    void allChildren(Classifier c, List result){
        c.getIsGeneralIn().each{ g ->
            def child = g.getSpecific();
            result.add(child);
            allChildren(child, result);
        }
    }

    /* All (direct and indirect) children */
    List<Classifier> allChildren(){
        def list = []
        allChildren(this, list)
        return list;
    }

    /** Checks if this classifier is disjoint of the parents of a given classifier, i.e., checks if they don't have overlapping parents */
    boolean isDisjointFromParentsOf(Classifier c){
        c.allParents().each{ o ->
            if(this.allParents().contains(o)) return false;
            if(this.equals(o)) return false;
        }
        return true;
    }

    /** Checks if this classifier is disjoint of the children of a given classifier, i.e., checks if they don't have overlapping siblings */
    boolean isDisjointFromChildrenOf(Classifier c){
        c.allChildren().each{ o ->
            if(this.allParents().contains(o)) return false;
            if(this.equals(o)) return false;
        }
        return true;
    }

    boolean isDisjointOf(Classifier c){
        if(this.isDisjointFromParentsOf(c) && this.isDisjointFromChildrenOf(c)){
            return true;
        }
        return false
    }

    //=============================
    // Siblings
    //=============================

    /** Returns direct siblings i.e. classifiers which specialize the same classifier as this classifier */
    List<Classifier> siblings(){
        def result = []
        parents().each{ p ->
            p.children().each{ sibling ->
                if(!sibling.equals(this)) {
                    result.add(sibling)
                }
            }
        }
        return result;
    }

    //=============================
    // Direct and Opposite Ends
    //=============================

    /** Returns all direct end-points from this classifier (in which we can navigate from it)
     *  In other words, it returns all opposite ends of the relationships connected to this classifier. */
    List<EndPoint> oppositeEndPoints(){
        def result = []
        model().allRelationships().each{ rel ->
            rel = rel as Relationship
            if(rel.isConnecting(this)){
                rel.endPoints().each{ ep ->
                    def t = ep.getClassifier();
                    if(t!=null){
                        if(!t.equals(this)){
                            result.add(ep);
                        }
                    }
                }
            }
        }
        return result;
    }

    /** Returns all direct end-points of a given relationship stereotype from this classifier (in which we can navigate from it)
     *  In other words, it returns all opposite ends of the relationships connected to this classifier. */
    List<EndPoint> oppositeEndPoints(RelationshipStereotype stereo){
        def result = []
        model().allRelationships().each{ rel ->
            if(rel.isConnecting(this) && rel.stereotype.equals(stereo)){
               rel.endPoints().each{ ep ->
                    def t = ep.getClassifier();
                    if(t!=null){
                        if(!t.equals(this)){
                            result.add(ep);
                        }
                    }
                }
            }
        }
        return result;
    }

    /** Returns all direct and indirect end-points from this classifier (in which we can navigate from it)
     *  In other words, it returns all opposite ends of the relationships connected to this classifier, or to a parent of this classifier. */
    List<EndPoint> allOppositeEndPoints() {
        def result = []
        result.addAll(this.oppositeEndPoints());
        this.allParents().each{ p ->
            result.addAll(p.oppositeEndPoints());
        }
        return result;
    }

    /** Returns all direct and indirect end-points from this classifier (in which we can navigate from it)
     *  In other words, it returns all opposite ends of the relationships connected to this classifier, or to a parent of this classifier. */
    List<EndPoint> allOppositeEndPoints(RelationshipStereotype stereo) {
        def result = []
        result.addAll(this.oppositeEndPoints(stereo));
        this.allParents().each{ p ->
            result.addAll(p.oppositeEndPoints(stereo));
        }
        return result;
    }

    /** Returns all direct relationships this classifier is connected to */
    List<Relationship> relationships(){
        def result = []
        model().allRelationships().each{ rel ->
            if(rel.isConnecting(this)){
                result.add(rel)
            }
        }
        return result
    }

    /** Returns all direct and indirect relationships this classifier is connected to */
    List<Relationship> allRelationships(){
        def result = []
        result.addAll(this.relationships())
        this.allParents().each{ p ->
            result.addAll(p.relationships())
        }
        return result
    }
}