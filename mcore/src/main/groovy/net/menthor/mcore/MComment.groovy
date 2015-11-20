package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MContainedElement
import net.menthor.mcore.traits.MElement

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MComment implements MElement {

    protected String text
    protected MContainedElement owner

    //=============================
    // Getters
    //=============================

    String getText() { return text }

    @JsonIgnore
    MContainedElement getOwner() { owner }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setOwner(MContainedElement owner){
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

