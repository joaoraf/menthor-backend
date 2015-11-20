package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MContainedElement
import net.menthor.mcore.traits.MNamedElement

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MConstraint implements MContainedElement {

    protected MNamedElement context
    protected String condition
    protected String name

    //=============================
    // Getters
    //=============================

    MNamedElement getContext() { return context }

    String getCondition() { return condition }

    String getName() { return name }

    //=============================
    // Setters
    //=============================

    void setContext(MNamedElement context) {
        this.context = context
        if(context==null) return
        //Ensuring the opposite end
        if(!context.getIsContextIn().contains(this)){
            context.getIsContextIn().add(this)
        }
    }

    void setCondition(String condition) { this.condition = condition }

    void setName(String identifier) { this.name = identifier }
}
