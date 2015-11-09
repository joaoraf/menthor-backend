package net.menthor.ontouml2.test

import net.menthor.ontouml2.Factory
import net.menthor.ontouml2.Model
import net.menthor.ontouml2.Class
import net.menthor.ontouml2.Relationship
import net.menthor.ontouml2.Serializer
import net.menthor.ontouml2.stereotypes.ClassStereotype
import net.menthor.ontouml2.stereotypes.RelationshipStereotype

/**
 *  Generates a model example and serialize it to a JSON file.
 *  Then, it reads that example generated as a JSON file and serialize it again with another file name.
 *  It then checks if the two files are the same to make sure the serialization is correct.
 */

class JSONSerializationTest {

    static void main(String[] args){

        def directory = "src/test/groovy/net/menthor/ontouml2/test/"
        def jsonGen = "CarAccident@Gen"
        def jsonRead = "CarAccident@Read"

        Serializer s = new Serializer()

        //carAccidentExample example
        carAccidentExample(s, directory,jsonGen)

        //read the example from file and write it again
        readInButWriteTo(s, directory,jsonGen, jsonRead)

        //compare the two files
        String genContent = s.readToString(directory+jsonGen+".json")
        String readContent = s.readToString(directory+jsonRead+".json")
        if(genContent.equals(readContent)){
            println "SUCCESS: Serialization Test passed"
        }else{
            println "ERROR: Serialization Test failed. The two files are not identical"
        }
    }

    static void carAccidentExample(Serializer s, String directory, String jsonFileName){

        //create the model
        Model m = Factory.createModel("Car Accident")

        //create kinds
        Class person = m.createClass(ClassStereotype.KIND, "Person")
        Class vehicle = m.createClass(ClassStereotype.KIND, "Vehicle")
        Class roadway = m.createClass(ClassStereotype.KIND, "Roadway")

        //create subkinds
        Class man = m.createClass(ClassStereotype.SUBKIND,"Man")
        Class woman = m.createClass(ClassStereotype.SUBKIND,"Woman")

        //create a partition between man and woman
        List genders = [man, woman]
        m.createPartition(genders, person)

        //create phases
        Class living = m.createClass(ClassStereotype.PHASE,"Living")
        Class deceased = m.createClass(ClassStereotype.PHASE,"Deceased")

        //create a partition between living and deceased
        List nature = [living, deceased]
        m.createPartition(nature, person)

        //create roles
        Class traveler = m.createClass(ClassStereotype.ROLE, "Traveler")
        Class victim = m.createClass(ClassStereotype.ROLE, "Victim")
        Class crashedVehicle = m.createClass(ClassStereotype.ROLE, "CrashedVehicle")

        //create relators
        Class accident = m.createClass(ClassStereotype.RELATOR,"Traffic Accident")
        Class rearEndCollision = m.createClass(ClassStereotype.RELATOR, "Rear End Collision")
        Class travel = m.createClass(ClassStereotype.RELATOR, "Travel")

        // create generalizations/specializations
        m.createGeneralization(traveler, person)
        m.createGeneralization(victim, person)
        m.createGeneralization(crashedVehicle, vehicle)
        m.createGeneralization(rearEndCollision, accident)

        //create mediations
        m.createRelationship(RelationshipStereotype.MEDIATION, accident, 1, -1, "occurs", roadway, 1, 1)
        m.createRelationship(RelationshipStereotype.MEDIATION,accident, 1, 1, "has", victim, 1, -1)
        m.createRelationship(RelationshipStereotype.MEDIATION,accident, 1, 1, "involves", crashedVehicle, 1, -1)
        m.createRelationship(RelationshipStereotype.MEDIATION,travel, 1, 1, "has", traveler, 1, -1)
        m.createRelationship(RelationshipStereotype.MEDIATION,travel, 1, 1, "made by", vehicle, 1, 1)

        //create material relationship
        Relationship material = m.createRelationship(RelationshipStereotype.MATERIAL, victim, 1, -1, "has been victim in", roadway, 1, 1)

        //create the derivation
        m.createRelationship(RelationshipStereotype.DERIVATION, material, accident);

        //save as JSON
        s.saveFormattedJSON(m,directory,jsonFileName)
    }

    static void readInButWriteTo(Serializer s, String directory, String jsonInputFileName, String jsonOutFileName){
        Model m = s.fromJSONFile(directory+jsonInputFileName+".json")
        s.saveFormattedJSON(m,directory, jsonOutFileName)
    }
}