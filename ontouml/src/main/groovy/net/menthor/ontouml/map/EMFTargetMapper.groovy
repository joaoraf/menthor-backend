package net.menthor.ontouml.map

import net.menthor.ontouml.Attribute
import net.menthor.ontouml.Class
import net.menthor.ontouml.DataType
import net.menthor.ontouml.Package
import net.menthor.ontouml.EndPoint
import net.menthor.ontouml.Generalization
import net.menthor.ontouml.GeneralizationSet
import net.menthor.ontouml.Model
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.traits.Container

/* A Generic trait to map OntoUML to a target language which is based on EMF packaging style.
 * This trait can be used to transform OntoUML into UML, Ecore,  RefOntoUML, etc */

trait EMFTargetMapper {

    Object tgtModel
    Map <Package, Object> tgtPackagesMap = [:]
    Map <Class, Object> tgtClassMap = [:]
    Map <DataType, Object> tgtDataTypeMap = [:]
    Map <Relationship, Object> tgtRelationshipsMap = [:]
    Map <Attribute, Object> tgtAttributesMap = [:]
    Map <EndPoint, Object> tgtEndpointsMap = [:]
    Map <Generalization, Object> tgtGensMap = [:]
    Map <GeneralizationSet, Object> tgtGenSetsMap = [:]

    abstract Object cloneModel(Model ontomodel)
    abstract Object clonePackage(Package ontopackage, Object tgtParentPackage)
    abstract Object cloneClass(Class ontoclass)
    abstract Object cloneDataType(DataType ontodatatype)
    abstract Object cloneRelationship(Relationship ontorel)
    abstract Object cloneAttribute(Attribute attr)
    abstract Object cloneEndPoint(EndPoint ep)
    abstract cloneSubsetsAndRedefines(EndPoint ep)
    abstract Object cloneGeneralization(Generalization g)
    abstract Object cloneGeneralizationSet(GeneralizationSet gs)

    Object from(Model ontomodel){
        runModel(ontomodel)
        runPackages(ontomodel, tgtModel)
        runTypes()
        runRelationships()
        runAttributes()
        runEndPoints()
        runSubsetsAndRedefines()
        runGeneralizations()
        runGeneralizationSets()
        return tgtModel;
    }

    void runModel(Model ontomodel){
        this.tgtModel = cloneModel(ontomodel)
        tgtPackagesMap.put(ontomodel as Package,tgtModel)
    }

    void runPackages(Container ontopackage, Object tgtParentPackage){
        ontopackage.getElements().each{ ontoelem ->
            if(ontoelem instanceof Package){
                def tgtpackage = clonePackage(ontoelem as Package, tgtParentPackage)
                tgtPackagesMap.put(ontoelem as Package, tgtpackage)
                runPackages(ontoelem as Package,tgtpackage)
            }
        }
    }

    void runTypes(){
        tgtPackagesMap.keySet().each{ ontopack ->
            ontopack.types().each{ elem ->
                if(elem instanceof Class) {
                    Class ontoclass = elem as Class
                    Object tgtClass = cloneClass(ontoclass)
                    tgtClassMap.put((ontoclass), tgtClass)
                }
                else if(elem instanceof DataType){
                    DataType ontodatatype = elem as DataType
                    Object tgtDataType = cloneDataType(ontodatatype)
                    tgtDataTypeMap.put(ontodatatype,tgtDataType)
                }
            }
        }
    }

    void runRelationships(){
        tgtPackagesMap.keySet().each{ ontopack ->
            ontopack.relationships().each{ rel ->
                Object tgtRel = cloneRelationship(rel)
                tgtRelationshipsMap.put(rel, tgtRel)
            }
        }
    }

    void runAttributes(){
        tgtPackagesMap.keySet().each{ ontopack ->
            ontopack.attributes().each{ attr ->
                Object tgtAttr = cloneAttribute(attr)
                tgtAttributesMap.put(attr, tgtAttr);
            }
        }
    }

    void runEndPoints(){
        tgtPackagesMap.keySet().each{ ontopack ->
            ontopack.endPoints().each{ ep ->
                Object tgtEndPoint = cloneEndPoint(ep)
                tgtEndpointsMap.put(ep, tgtEndPoint);
            }
        }
    }

    void runSubsetsAndRedefines(){
        tgtPackagesMap.keySet().each { ontopack ->
            ontopack.endPoints().each{ ep ->
                cloneSubsetsAndRedefines(ep)
            }
        }
    }

    void runGeneralizations(){
        tgtPackagesMap.keySet().each { ontopack ->
            ontopack.generalizations().each{ g ->
                Object tgtGen = cloneGeneralization(g)
                tgtGensMap.put(g, tgtGen)
            }
        }
    }

    void runGeneralizationSets(){
        tgtPackagesMap.keySet().each { ontopack ->
            ontopack.generalizationSets().each{ gs ->
                Object tgtGenSet = cloneGeneralizationSet(gs)
                tgtGenSetsMap.put(gs, tgtGenSet)
            }
        }
    }
}
