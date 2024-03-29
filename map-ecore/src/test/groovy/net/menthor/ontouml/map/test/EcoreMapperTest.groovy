package net.menthor.ontouml.map.test

import net.menthor.ontouml.Model
import net.menthor.mcore.MSerializer
import net.menthor.ontouml.map.EcoreMapper

class EcoreMapperTest {

    static void main(String[] args){
        def mapper = new EcoreMapper()
        MSerializer s = new MSerializer()
        Model m = s.fromJSONFile("ecore/src/test/groovy/net/menthor/ontouml/map/test/CarAccident.json")
        m.createPackage("Package1")
        def ecoremodel = mapper.toEcore(m, true)
        mapper.serialize(ecoremodel,"ecore/src/test/groovy/net/menthor/ontouml/map/test/CarAccident.ecore")
        println "Done."
    }
}
