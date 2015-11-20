package net.menthor.mcore.test

import net.menthor.mcore.MPackage
import net.menthor.mcore.MSerializer

/**
 *  Generates a model example and serialize it to a JSON file.
 *  Then, it reads that example generated as a JSON file and serialize it again with another file name.
 *  It then checks if the two files are the same to make sure the serialization is correct.
 */

class SerializationTest {

    static void main(String[] args){

        def directory = "mcore/src/test/groovy/net/menthor/mcore/test/"
        def jsonGen = "SerializationGen"
        def jsonRead = "SerializationRead"

        MSerializer s = new MSerializer()

        //carAccidentExample example
        MPackage m = CarAccidentExample.generate()
        s.toFormattedJSONFile(m,directory,jsonGen)

        //read the example from file and write it again
        MPackage m2 = s.fromJSONFile(directory+jsonGen+".json")
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