package net.menthor.mcore.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MConstraint
import net.menthor.mcore.util.NameProcessor

/** An element of the metamodel which has a name */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait MNamedElement implements MElement {

    protected List<MConstraint> isContextIn
    protected String name
    protected String uniqueName
    protected List<String> definitions = []
    protected List<String> synonyms = []
    protected String text

    //=============================
    // Getters
    //=============================

    @JsonIgnore
    List<MConstraint> getIsContextIn(){ return isContextIn }

    String getName() { return name }

    String getUniqueName() { return uniqueName }

    List<String> getDefinitions() { return definitions }

    List<String> getSynonyms(){ return synonyms }

    String getText(){ return text }

    //=============================
    // Setters
    //=============================

    void setName(String name){
        if(name==null || name.isEmpty()){
            if(uniqueName!=null) NameProcessor.remove(uniqueName)
            int idx = getClass().getName().lastIndexOf(".")
            this.uniqueName = NameProcessor.process(getClass().getName().substring(idx))
        }else{
            if(uniqueName!=null) NameProcessor.remove(uniqueName)
            this.uniqueName = NameProcessor.process(name)
            this.name = name
        }
    }

    void setUniqueName(String uniqueName){
        this.uniqueName = uniqueName
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

    void setIsContextIn(MConstraint c){
        if(c==null) return
        if(!isContextIn.contains(c)){
            isContextIn.add(c)
        }
        //Ensuring opposite end
        c.setContext(this)
    }

    void setIsContextIn(List<MConstraint> constraints){
        if(constraints==null || constraints ==[]) {
            isContextIn.clear()
            return
        }
        constraints.each{ c ->
            setIsContextIn(c)
        }
    }
}
