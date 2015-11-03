package net.menthor.ontouml2.traits

import net.menthor.ontouml2.EndPoint
import net.menthor.ontouml2.Generalization
import net.menthor.ontouml2.Relationship
import net.menthor.ontouml2.stereotypes.RelationshipStereotype

trait Classifier implements ContainedElement, NamedElement {

    List<Generalization> isGeneralIn
    List<Generalization> isSpecificIn

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
        c.isSpecificIn().each{ g ->
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
        c.isGeneralIn().each{ g ->
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
    // Related classifiers and end-points
    //=============================

    /** Returns all direct end-points from this classifier (in which we can navigate from it)
     *  In other words, it returns all opposite ends of the relationships connected to this classifier. */
    List<EndPoint> endPoints(){
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
    List<EndPoint> endPoints(RelationshipStereotype stereo){
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
    List<EndPoint> allEndPoints() {
        def result = []
        result.addAll(this.endPoints());
        this.allParents().each{ p ->
            result.addAll(p.endPoints());
        }
        return result;
    }

    /** Returns all direct and indirect end-points from this classifier (in which we can navigate from it)
     *  In other words, it returns all opposite ends of the relationships connected to this classifier, or to a parent of this classifier. */
    List<EndPoint> allEndPoints(RelationshipStereotype stereo) {
        def result = []
        result.addAll(this.endPoints(stereo));
        this.allParents().each{ p ->
            result.addAll(p.endPoints(stereo));
        }
        return result;
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
}