package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Model extends Package {

    static void main(String[] args){
        println "\n================================="
        println "OntoUML 2.0 Metamodel API"
        println "Copyright: MIT License"
        println "Powered by Menthor (www.menthor.net)"
        println "================================="
    }
}
