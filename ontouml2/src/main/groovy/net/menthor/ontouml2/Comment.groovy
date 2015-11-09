package net.menthor.ontouml2

import net.menthor.ontouml2.traits.ContainedElement
import net.menthor.ontouml2.traits.Element
import org.codehaus.jackson.annotate.JsonIgnore
import org.codehaus.jackson.annotate.JsonTypeInfo

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
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
