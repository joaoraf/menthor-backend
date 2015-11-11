package net.menthor.ontouml.map

import net.menthor.ontouml.Attribute
import net.menthor.ontouml.EndPoint
import net.menthor.ontouml.Generalization
import net.menthor.ontouml.GeneralizationSet
import net.menthor.ontouml.Model
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.traits.Container
import net.menthor.ontouml.traits.Type

abstract class AbstractMapper extends AbstractCloner {

    Model ontomodel
    Object targetModel

    Map <Container, Object> packagesMap = [:]
    Map <Type, Object> typesMap = [:]
    Map <Relationship, Object> relationshipsMap= [:]
    Map <Attribute, Object> attributesMap= [:]
    Map <EndPoint, Object> endpointsMap= [:]
    Map <Generalization, Object> gensMap= [:]
    Map <GeneralizationSet, Object> genSetsMap= [:]

    AbstractMapper(Model ontomodel){
        this.ontomodel = ontomodel
        this.targetModel = cloneModel(ontomodel)
        packagesMap.put(ontomodel,targetModel)
    }

    Object run(){
        runPackages(ontomodel, targetModel);
        return targetModel;
    }

    private void runPackages (Container ontopackage){
        ontopackage.getElements().each{ o ->
            if(o instanceof Container){
                def targetPackage = clonePackage(o)
                packagesMap.put(o as Container, targetPackage)
                runPackages(o as Container,targetPackage)
            }
        }
    }
}
