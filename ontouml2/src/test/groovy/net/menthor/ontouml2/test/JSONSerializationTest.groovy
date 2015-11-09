package net.menthor.ontouml2.test

import net.menthor.ontouml2.Factory
import net.menthor.ontouml2.Class
import net.menthor.ontouml2.Model
import net.menthor.ontouml2.Relationship
import net.menthor.ontouml2.Serializer
import net.menthor.ontouml2.examples.CarAccidentExample
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
        Model m = CarAccidentExample.generate()
        s.saveFormattedJSON(m,directory,jsonGen)

        //read the example from file and write it again
        Model m2 = s.fromJSONFile(directory+jsonGen+".json")
        s.saveFormattedJSON(m2,directory, jsonRead)

        //compare the two files
        String genContent = s.readToString(directory+jsonGen+".json")
        String readContent = s.readToString(directory+jsonRead+".json")
        if(genContent.equals(readContent)){
            println "SUCCESS: Serialization Test passed"
        }else{
            println "ERROR: Serialization Test failed. The two files are not identical"
        }
    }
}