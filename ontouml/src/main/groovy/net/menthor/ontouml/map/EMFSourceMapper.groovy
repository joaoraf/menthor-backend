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

/* A Generic trait to map a source language based on EMF packaging style into OntoUML.
 * This trait can be used to transform UML, Ecore, RefOntoUML, etc into OntoUML */

trait EMFSourceMapper {

    Model ontomodel
    Map <Object, Container> srcPackagesMap = [:]
    Map <Object, Class> srcClassMap = [:]
    Map <Object, DataType> srcDataTypeMap = [:]
    Map <Object, Relationship> srcRelationshipsMap= [:]
    Map <Object, Attribute> srcAttributesMap= [:]
    Map <Object, EndPoint> srcEndpointsMap= [:]
    Map <Object, Generalization> srcGensMap= [:]
    Map <Object, GeneralizationSet> srcGenSetsMap= [:]

    String accessMethod = "eContents"

    java.lang.Class packageClass
    java.lang.Class typeClass
    java.lang.Class classClass
    java.lang.Class dataTypeClass
    java.lang.Class relationshipClass
    java.lang.Class genClass
    java.lang.Class propertyClass
    java.lang.Class genSetClass

    void setup(java.lang.Class packageClass, java.lang.Class typeClass, java.lang.Class classClass, java.lang.Class dataTypeClass,
    java.lang.Class relationshipClass, java.lang.Class genClass, java.lang.Class propertyClass, java.lang.Class genSetClass){
        this.packageClass=packageClass
        this.typeClass=typeClass
        this.classClass=classClass
        this.dataTypeClass=dataTypeClass
        this.relationshipClass=relationshipClass
        this.genClass=genClass
        this.propertyClass=propertyClass
        this.genSetClass=genSetClass
    }

    abstract Model cloneModel(Object sourceModel)
    abstract Package clonePackage(Object srcPackage, Package container)
    abstract Class cloneClass(Object srcClass)
    abstract DataType cloneDataType(Object srcDataType)
    abstract Relationship cloneRelationship(Object srcRel)
    abstract Attribute cloneAttribute(Object srcAttr)
    abstract EndPoint cloneEndPoint(Object srcEp)
    abstract cloneSubsetsAndRedefines(Object srcEp)
    abstract Generalization cloneGeneralization(Object srcGen)
    abstract GeneralizationSet cloneGeneralizationSet(Object srcGs)

    Model from(Object srcModel){
        runModel(srcModel)
        runPackages(srcModel, ontomodel)
        runTypes()
        runRelationships()
        runAttributes()
        runEndPoints()
        runSubsetsAndRedefines()
        runGeneralizations()
        runGeneralizationSets()
        return ontomodel;
    }

    void runModel(Object srcModel) {
        this.ontomodel = cloneModel(srcModel)
        srcPackagesMap.put(srcModel, ontomodel)
    }

    void runPackages (Object srcPack, Container ontoParentPackage){
        srcPack."${accessMethod}"().each{ srcElem ->
            if(packageClass.isInstance(srcElem)){
                Package ontopackage = clonePackage(srcElem, ontoParentPackage)
                srcPackagesMap.put(srcElem, ontopackage)
                runPackages(srcElem, ontopackage)
            }
        }
    }

    void runTypes(){
        srcPackagesMap.keySet().each{ srcpack ->
            srcpack."${accessMethod}"().each{ srcelem ->
                if(classClass.isInstance(srcelem)){
                    Class ontoclass = cloneClass(srcelem)
                    srcClassMap.put(srcelem,ontoclass)
                }
                else if(dataTypeClass.isInstance(srcelem)){
                    DataType ontoDataType = cloneDataType(srcelem)
                    srcDataTypeMap.put(srcelem,ontoDataType)
                }
            }
        }
    }

    void runRelationships(){
        srcPackagesMap.keySet().each{ srcpack ->
            srcpack."${accessMethod}"().each { srcelem ->
                if (relationshipClass.isInstance(srcelem)){
                    Relationship ontorel = cloneRelationship(srcelem)
                    srcRelationshipsMap.put(srcelem, ontorel)
                }
            }
        }
    }

    void runAttributes(){
        srcPackagesMap.keySet().each{ srcpack ->
            srcpack."${accessMethod}"().each { srcelem ->
                if(classClass.isInstance(srcelem) || dataTypeClass.isInstance(srcelem)){
                    srcelem."${accessMethod}"().each { attr ->
                        if(propertyClass.isInstance(attr)){
                            Attribute ontoattr = cloneAttribute(attr)
                            srcAttributesMap.put(attr, ontoattr);
                        }
                    }
                }
            }
        }
    }

    void runEndPoints(){
        srcPackagesMap.keySet().each{ srcpack ->
            srcpack."${accessMethod}"().each { srcelem ->
                if(relationshipClass.isInstance(srcelem)){
                    srcelem."${accessMethod}"().each { ep ->
                        EndPoint ontoendpoint = cloneEndPoint(ep)
                        srcEndpointsMap.put(ep, ontoendpoint);
                    }
                }
            }
        }
    }

    void runSubsetsAndRedefines(){
        srcEndpointsMap.keySet().each { srcep ->
           cloneSubsetsAndRedefines(srcep)
        }
    }

   void runGeneralizations(){
        srcPackagesMap.keySet().each {  srcpack ->
            srcpack."${accessMethod}"().each { srcelem ->
                if(typeClass.isInstance(srcelem) || relationshipClass.isInstance(srcelem)) {
                    srcelem."${accessMethod}"().each { srcgen ->
                        if (genClass.isInstance(srcgen)) {
                            Generalization ontogen = cloneGeneralization(srcgen)
                            srcGensMap.put(srcgen, ontogen)
                        }
                    }
                }
            }
        }
    }

    void runGeneralizationSets(){
        srcPackagesMap.keySet().each { srcpack ->
            srcpack."${accessMethod}"().each { srcelem ->
                if(genSetClass.isInstance(srcelem)) {
                    GeneralizationSet ontogs = cloneGeneralizationSet(srcelem)
                    srcGenSetsMap.put(srcelem, ontogs)
                }
            }
        }
    }
}
