package net.menthor.mcore.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MEndPoint
import net.menthor.mcore.MGeneralization
import net.menthor.mcore.MRelationship

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait MClassifier implements MContainedElement, MNamedElement {

    protected List<MGeneralization> isGeneralIn = []
    protected List<MGeneralization> isSpecificIn = []
    protected List<MEndPoint> isClassifierIn = []

    //=============================
    // Getters
    //=============================

    @JsonIgnore
    List<MGeneralization> getIsGeneralIn(){ return isGeneralIn }

    @JsonIgnore
    List<MGeneralization> getIsSpecificIn(){ return isSpecificIn }

    @JsonIgnore
    List<MEndPoint> getIsClassifierIn() { return isClassifierIn }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setIsGeneralIn(MGeneralization g){
        if(g==null) return
        if(!isGeneralIn.contains(g)){
            isGeneralIn.add(g)
        }
        //Ensuring opposite end
        g.setGeneral(this)
    }

    void setIsGeneralIn(List<MGeneralization> gens){
        if(gens==null || gens ==[]) {
            isGeneralIn.clear()
            return
        }
        gens.each{ g ->
            setIsGeneralIn(g)
        }
    }

    void setIsSpecificIn(MGeneralization g){
        if(g==null) return
        if(!isSpecificIn.contains(g)){
            isSpecificIn.add(g)
        }
        //Ensuring opposite end
        g.setSpecific(this)
    }

    void setIsSpecificIn(List<MGeneralization> gens){
        if(gens==null || gens==[]){
            isSpecificIn.clear()
            return
        }
        gens.each{ g ->
            setIsSpecificIn(g)
        }
    }

    void setIsClassifierIn(MEndPoint p){
        if(p==null) return
        if(!isClassifierIn.contains(p)){
            isClassifierIn.add(p)
        }
        //Ensuring opposite end
        p.setClassifier(this)
    }

    void setIsClassifierIn(List<MEndPoint> eps){
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
    List<MClassifier> children(){
        def list = []
        isGeneralIn.each{ g ->
            list.add(g.getSpecific());
        }
        return list;
    }

    /** Direct parents (the ancestors) */
    List<MClassifier> parents(){
        def list = []
        isSpecificIn.each{g ->
            list.add(g.getGeneral());
        }
        return list;
    }

    /** All (direct and indirect) parents */
    void allParents(MClassifier c, List result){
        c.getIsSpecificIn().each{ g ->
            def MClassifier parent = g.getGeneral();
            result.add(parent);
            allParents(parent,result);
        }
    }

    /** All (direct and indirect) parents */
    List<MClassifier> allParents(){
        def list = []
        allParents(this, list)
        return list;
    }

    /* All (direct and indirect) children */
    void allChildren(MClassifier c, List result){
        c.getIsGeneralIn().each{ g ->
            def child = g.getSpecific();
            result.add(child);
            allChildren(child, result);
        }
    }

    /* All (direct and indirect) children */
    List<MClassifier> allChildren(){
        def list = []
        allChildren(this, list)
        return list;
    }

    /** Checks if this classifier is disjoint of the parents of a given classifier, i.e., checks if they don't have overlapping parents */
    boolean isDisjointFromParentsOf(MClassifier c){
        c.allParents().each{ o ->
            if(this.allParents().contains(o)) return false;
            if(this.equals(o)) return false;
        }
        return true;
    }

    /** Checks if this classifier is disjoint of the children of a given classifier, i.e., checks if they don't have overlapping siblings */
    boolean isDisjointFromChildrenOf(MClassifier c){
        c.allChildren().each{ o ->
            if(this.allParents().contains(o)) return false;
            if(this.equals(o)) return false;
        }
        return true;
    }

    boolean isDisjointOf(MClassifier c){
        if(this.isDisjointFromParentsOf(c) && this.isDisjointFromChildrenOf(c)){
            return true;
        }
        return false
    }

    //=============================
    // Siblings
    //=============================

    /** Returns direct siblings i.e. classifiers which specialize the same classifier as this classifier */
    List<MClassifier> siblings(){
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
    List<MEndPoint> oppositeEndPoints(){
        def result = []
        model().allRelationships().each{ rel ->
            rel = rel as MRelationship
            if(rel.isConnecting(this)){
                rel.getEndPoints().each{ ep ->
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
    List<MEndPoint> allOppositeEndPoints() {
        def result = []
        result.addAll(this.oppositeEndPoints());
        this.allParents().each{ p ->
            result.addAll(p.oppositeEndPoints());
        }
        return result;
    }

    /** Returns all direct relationships this classifier is connected to */
    List<MRelationship> relationships(){
        def result = []
        model().allRelationships().each{ rel ->
            if(rel.isConnecting(this)){
                result.add(rel)
            }
        }
        return result
    }

    /** Returns all direct and indirect relationships this classifier is connected to */
    List<MRelationship> allRelationships(){
        def result = []
        result.addAll(this.relationships())
        this.allParents().each{ p ->
            result.addAll(p.relationships())
        }
        return result
    }
}