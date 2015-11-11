package net.menthor.ontouml

import groovy.json.JsonOutput
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.map.annotate.JsonSerialize

class Serializer {

    ObjectMapper mapper = new ObjectMapper()

    Serializer() {
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    Model fromJSONFile(String path){
        def jsonStr = readToString(path)
        return fromJSON(jsonStr)
    }

    Model fromJSON(String json) {
        Model actual = (Model) mapper.readValue(json, Model.class);
        return actual
    }

    String toJSON(GroovyObject obj) {
        return mapper.writeValueAsString(obj);
    }

    String toFormattedJSON(GroovyObject obj){
        def jsonStr = toJSON(obj)
        return JsonOutput.prettyPrint(jsonStr)
    }

    File saveJSON (GroovyObject obj, String directory, String fileName) {
        def content = toJSON(obj)
        writeToFile(content, directory, fileName, ".json")
    }

    File saveFormattedJSON(GroovyObject obj, String directory, String fileName) {
        def content = toFormattedJSON(obj)
        writeToFile(content, directory, fileName, ".json")
    }

    File writeToFile(String content, String directory, String fileName, String extension) {
        return new File("$directory/$fileName$extension").withWriter { out ->
            out.print content
        }
    }

    String readToString(String abspath){
        return new File(abspath).getText('UTF-8')
    }
}