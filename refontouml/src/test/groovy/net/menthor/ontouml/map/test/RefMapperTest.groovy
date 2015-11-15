package net.menthor.ontouml.map.test

import net.menthor.ontouml.Model
import net.menthor.ontouml.Serializer
import net.menthor.ontouml.map.RefMapper

class RefMapperTest {

    static void main(String[] args){

        //read example in OntoUML
        Serializer s = new Serializer()
        Model m = s.fromJSONFile("refontouml/src/test/groovy/net/menthor/ontouml/map/test/CarAccident.json")
        println m

        def refmapper = new RefMapper()

        //transform to RefOntoUML
        def refmodel = refmapper.toRefOntoUML(m)
        refmapper.serialize(refmodel, "refontouml/src/test/groovy/net/menthor/ontouml/map/test/CarAccident.refontouml")

        //transform back to OntoUML
        Model m2 = refmapper.fromRefOntoUML(refmodel)
        s.toFormattedJSONFile(m2,"refontouml/src/test/groovy/net/menthor/ontouml/map/test/","CarAccident2")

        println "SUCCESS: RefOntoUML Mapper test passed"
    }
}
