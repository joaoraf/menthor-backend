package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper

import groovy.json.JsonOutput
import net.menthor.mcore.traits.MElement

class MSerializer implements MElement {

    ObjectMapper mapper = new ObjectMapper()

    MSerializer() {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    MPackage fromJSONFile(String path){
        def jsonStr = readToString(path)
        return fromJSONString(jsonStr)
    }

    MPackage fromJSONString(String json) {
        MPackage actual = (MPackage) mapper.readValue(json, MPackage.class);
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