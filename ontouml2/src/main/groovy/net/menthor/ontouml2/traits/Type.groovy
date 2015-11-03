package net.menthor.ontouml2.traits

import net.menthor.ontouml2.Attribute
import net.menthor.ontouml2.EndPoint

trait Type implements Classifier {

    List<Attribute> attributes

    /** Returns all types directly connected to this type through a relationship. */
    List<Type> relatedTypes(){
        def result = []
        endPoints().each{ ep ->
            def t = ep.getClassifier();
            if(t!=null){
                result.add(t as Type)
            }
        }
        return result;
    }

    /**Returns all types directly and indirectly connected to this type through a relationship. */
    List<Type> allRelatedTypes(){
        def result = []
        allEndPoints().each { ep ->
            def t = ep.getClassifier();
            if(t!=null){
                result.add(t as Type)
            }
        }
        return result;
    }
}