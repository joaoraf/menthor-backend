package net.menthor.ontouml.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.ontouml.Comment
import net.menthor.ontouml.Model

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait ContainedElement extends Element {

    protected Container container
    protected List<Comment> comments = []

    //=============================
    // Getters
    //=============================

    @JsonIgnore
    Container getContainer() { return container }

    List<Comment> getComments() { return comments }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setContainer(Container c){
        container = c
        if(c==null) return
        //Ensure the opposite end
        if(!c.elements.contains(this)) {
            c.elements.add(this)
        }
    }

    void setComment(Comment c){
        if(c==null) return
        if(!comments.contains(c)){
            comments.add(c)
        }
        //Ensuring opposite end
        c.setOwner(this)
    }

    void setComments(List<Comment> comments){
        if(comments==null || comments == []){
            this.comments.clear()
            return
        }
        comments.each{ c ->
            setComment(c)
        }
    }

    //=============================
    // Model
    //=============================

    /** The model */
    Model model(){
        return model(container)
    }

    /** The model searching it from a given container */
    Model model(Container c){
        if(c instanceof Model) {
            return c
        }else{
            if(c instanceof ContainedElement) {
                return model(c.container)
            }
        }
    }
}