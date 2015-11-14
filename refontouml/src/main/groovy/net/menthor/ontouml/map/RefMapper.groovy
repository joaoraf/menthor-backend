package net.menthor.ontouml.map

import net.menthor.ontouml.Model

/** A Mapping to the Reference OntoUML Infrastructure */

class RefMapper {

    RefSourceMapper sourceMapper = new RefSourceMapper()
    RefTargetMapper targetMapper = new RefTargetMapper()

    Model fromRefOntoUML(RefOntoUML.Package refmodel){
        sourceMapper.fromRefOntoUML(refmodel)
    }

    RefOntoUML.Package toRefOntoUML(Model m){
        targetMapper.toRefOntoUML(m)
    }

    void serialize(RefOntoUML.Package model, String path){
        targetMapper.serialize(model,path)
    }

    RefOntoUML.Package deserialize(String path){
        sourceMapper.deserialize(path)
    }
}
