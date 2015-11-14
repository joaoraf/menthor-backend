package net.menthor.ontouml.test

import net.menthor.ontouml.Serializer
import net.menthor.ontouml.Model

/**
 *  Generates a model example and serialize it to a JSON file.
 *  Then, it reads that example generated as a JSON file and serialize it again with another file name.
 *  It then checks if the two files are the same to make sure the serialization is correct.
 */

class JSONSerializationTest {

    static void main(String[] args){

        def directory = "ontouml/src/test/groovy/net/menthor/ontouml/test/"
        def jsonGen = "CarAccident@Gen"
        def jsonRead = "CarAccident@Read"

        Serializer s = new Serializer()

        //carAccidentExample example
        Model m = CarAccidentExample.generate()
        s.toFormattedJSONFile(m,directory,jsonGen)

        //read the example from file and write it again
        Model m2 = s.fromJSONFile(directory+jsonGen+".json")
        s.toFormattedJSONFile(m2,directory, jsonRead)

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