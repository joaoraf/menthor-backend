package net.menthor.ontouml.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators

/** An element of the metamodel which has a name */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait NamedElement implements Element {

    protected String name
    protected String uniqueName
    protected List<String> definitions = []
    protected List<String> synonyms = []
    protected String text

    //=============================
    // Getters
    //=============================

    String getName() { return name }

    String getUniqueName() { return uniqueName }

    List<String> getDefinitions() { return definitions }

    List<String> getSynonyms(){ return synonyms }

    String getText(){ return text }

    //=============================
    // Setters
    //=============================

    void setName(String name){
        if(name!=null) this.name = name
        else this.name = ""
    }

    void setUniqueName(String uniqueName){
        if(uniqueName!=null) this.uniqueName = uniqueName
        else this.uniqueName = ""
    }

    void setText(String text){
        if(text!=null) this.text = text
        else this.text = ""
    }

    void setDefinitions(List<String> definitions){
        this.definitions = definitions
    }

    void setSynonyms(List<String> synonyms){
        this.synonyms = synonyms
    }
}
