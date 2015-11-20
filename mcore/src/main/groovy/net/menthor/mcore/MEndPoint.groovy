package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MClassifier
import net.menthor.mcore.traits.MProperty

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MEndPoint implements MProperty {

    protected MRelationship owner
    protected MClassifier classifier

    //=============================
    // Getters
    //=============================

    @JsonIgnore
    MRelationship getOwner() { return owner }

    MClassifier getClassifier() { return classifier }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setOwner(MRelationship owner){
        this.owner = owner
        if(owner==null) return
        //Ensuring opposite end
        if(!owner.endPoints.contains(this)){
            owner.endPoints.add(this)
        }
    }

    void setClassifier(MClassifier c){
        classifier = c
        if(c == null) return
        //Ensuring opposite end
        if(!c.getIsClassifierIn().contains(this)){
            c.setIsClassifierIn(this)
        }
    }

    //=============================
    // Default values
    //=============================

    private String formatName(String name){
        return name.trim().toLowerCase().replace(" ","_").replace("-","_")
    }

    void setDefaultName(){
        def name = new String()
        if (classifier != null){
            name = classifier.getName()
            if (name == null || name.trim().isEmpty()) {
                name = owner.getName()
                if(name == null || name.trim().isEmpty()){
                    name = "endpoint"
                }else{
                    name = formatName(name)
                }
            } else {
                name = formatName(name)
            }
        }
        setName(name)
    }
}
