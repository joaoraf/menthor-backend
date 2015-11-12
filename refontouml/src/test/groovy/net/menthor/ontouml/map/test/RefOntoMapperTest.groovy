package net.menthor.ontouml.map.test

import net.menthor.ontouml.Model
import net.menthor.ontouml.Serializer
import net.menthor.ontouml.map.RefOntoMapper

class RefOntoMapperTest {

    static void main(String[] args){
        Serializer s = new Serializer()
        Model m = s.fromJSONFile("map-refonto/src/test/groovy/net/menthor/ontouml/map/test/CarAccident.json")
        println m
        def mapper = new RefOntoMapper()
        mapper.toRefOntoUML(m)
        mapper.serialize("map-refonto/src/test/groovy/net/menthor/ontouml/map/test/CarAccident.refontouml")
    }
}
