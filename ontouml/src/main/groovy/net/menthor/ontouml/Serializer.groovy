package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper

import groovy.json.JsonOutput

class Serializer {

    ObjectMapper mapper = new ObjectMapper()

    Serializer() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    Model fromJSONFile(String path){
        def jsonStr = readToString(path)
        return fromJSONString(jsonStr)
    }

    Model fromJSONString(String json) {
        Model actual = (Model) mapper.readValue(json, Model.class);
        return actual
    }

    String toJSONString(GroovyObject obj) {
        return mapper.writeValueAsString(obj);
    }

    String toFormattedJSONString(GroovyObject obj){
        def jsonStr = toJSONString(obj)
        return JsonOutput.prettyPrint(jsonStr)
    }

    File toJSONFile(GroovyObject obj, String directory, String fileName) {
        def content = toJSONString(obj)
        writeToFile(content, directory, fileName, ".json")
    }

    File toFormattedJSONFile(GroovyObject obj, String directory, String fileName) {
        def content = toFormattedJSONString(obj)
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