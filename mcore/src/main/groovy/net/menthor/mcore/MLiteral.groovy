package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MElement

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MLiteral implements MElement {

    protected String text
    protected MDataType owner

    //=============================
    //Getters
    //=============================

    String getText() { return text }

    @JsonIgnore
    MDataType getOwner() { return owner }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setText(String text) { this.text = text }

    void setOwner(MDataType owner){
        this.owner = owner
        if(owner==null) return
        //Ensuring opposite end
        if(!owner.getLiterals().contains(this)){
            owner.literals.add(this)
        }
    }
}
