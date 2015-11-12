package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.ontouml.traits.ContainedElement
import net.menthor.ontouml.traits.Element

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Comment implements Element {

    protected String text
    protected ContainedElement owner

    //=============================
    // Getters
    //=============================

    String getText() { return text }

    @JsonIgnore
    ContainedElement getOwner() { owner }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setOwner(ContainedElement owner){
        owner = owner
        if(owner==null) return
        //Ensuring opposite end
        if(!owner.comments.contains(this)){
            owner.comments.add(this)
        }
    }

    void setText(String text){
        this.text = text
    }
}
