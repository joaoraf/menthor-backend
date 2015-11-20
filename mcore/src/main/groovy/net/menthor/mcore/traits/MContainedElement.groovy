package net.menthor.mcore.traits

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MComment
import net.menthor.mcore.MPackage

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
trait MContainedElement extends MElement {

    protected MContainer container
    protected List<MComment> comments = []

    //=============================
    // Getters
    //=============================

    @JsonIgnore
    MContainer getContainer() { return container }

    List<MComment> getComments() { return comments }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setContainer(MContainer c){
        container = c
        if(c==null) return
        //Ensure the opposite end
        if(!c.elements.contains(this)) {
            c.elements.add(this)
        }
    }

    void setComment(MComment c){
        if(c==null) return
        if(!comments.contains(c)){
            comments.add(c)
        }
        //Ensuring opposite end
        c.setOwner(this)
    }

    void setComments(List<MComment> comments){
        if(comments==null || comments == []){
            this.comments.clear()
            return
        }
        comments.each{ c ->
            setComment(c)
        }
    }

    //=============================
    // Root
    //=============================

    /** The model container */
    MPackage model(){
        return model(container)
    }

    /** The roo container searching from a given container */
    MPackage model(MPackage c){
        if(c.getContainer()==null) {
            return c
        }else{
            return model(c.container)
        }
    }
}