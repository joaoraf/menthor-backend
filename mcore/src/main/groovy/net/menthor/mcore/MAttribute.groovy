package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MProperty
import net.menthor.mcore.traits.MType

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MAttribute implements MProperty {

    protected MType owner

    //=============================
    // Getters
    //=============================

    @JsonIgnore
    MType getOwner() { return owner }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setOwner(MType owner){
        this.owner = owner
        if(owner==null) return
        //Ensuring opposite end
        if(!owner.attributes.contains(this)){
            owner.attributes.add(this)
        }
    }
}
