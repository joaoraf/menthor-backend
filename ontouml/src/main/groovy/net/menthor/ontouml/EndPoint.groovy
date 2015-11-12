package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.ontouml.traits.Classifier
import net.menthor.ontouml.traits.Property

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class EndPoint implements Property {

    protected Relationship owner
    protected Classifier classifier
    protected List<EndPoint> subsets = []
    protected List<EndPoint> redefines = []
    protected List<EndPoint> subsettedBy = []
    protected List<EndPoint> redefinedBy = []

    //=============================
    // Getters
    //=============================

    @JsonIgnore
    Relationship getOwner() { return owner }

    Classifier getClassifier() { return classifier }

    List<EndPoint> getSubsets() { return subsets }

    List<EndPoint> getRedefines() { return redefines }

    @JsonIgnore
    List<EndPoint> getIsSubsettedBy() { return subsettedBy }

    @JsonIgnore
    List<EndPoint> getIsRedefinedBy() { return redefinedBy }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setOwner(Relationship owner){
        this.owner = owner
        if(owner==null) return
        //Ensuring opposite end
        if(!owner.endPoints.contains(this)){
            owner.endPoints.add(this)
        }
    }

    void setClassifier(Classifier c){
        classifier = c
        if(c == null) return
        //Ensuring opposite end
        if(!c.getIsClassifierIn().contains(this)){
            c.setIsClassifierIn(this)
        }
    }

    void setSubset(EndPoint superEp){
        if(superEp==null) return
        if(!subsets.contains(superEp)){
            subsets.add(superEp)
        }
        if(!superEp.subsettedBy.contains(this)){
            superEp.subsettedBy.add(this)
        }
    }

    void setSubsets(List<EndPoint> superEps){
        if(superEps==null || superEps==[]){
            this.subsets.clear()
            return
        }
        superEps.each{ ep ->
            setSubset(ep)
        }
    }

    void setSubsetted(EndPoint subEp){
        if(subEp==null) return
        if(!subsettedBy.contains(subEp)){
            subsettedBy.add(subEp)
        }
        if(!subEp.subsets.contains(this)){
            subEp.subsets.add(this)
        }
    }

    void setSubsettedBy(List<EndPoint> subEps){
        if(subEps==null || subEps==[]){
            this.subsets.clear()
            return
        }
        subEps.each{ ep ->
            setSubsetted(ep)
        }
    }

    void setRedefine(EndPoint superEp){
        if(superEp==null) return
        if(!redefines.contains(superEp)){
            redefines.add(superEp)
        }
        if(!superEp.redefinedBy.contains(this)){
            superEp.redefinedBy.add(this)
        }
    }

    void setRedefines(List<EndPoint> superEps){
        if(superEps==null || superEps==[]){
            this.redefines.clear()
            return
        }
        superEps.each{ ep ->
            setRedefine(ep)
        }
    }

    void setRedefined(EndPoint subEp){
        if(subEp==null) return
        if(!redefinedBy.contains(subEp)){
            redefinedBy.add(subEp)
        }
        if(!subEp.redefines.contains(this)){
            subEp.redefines.add(this)
        }
    }

    void setRedefinedBy(List<EndPoint> subEps){
        if(subEps==null || subEps==[]){
            this.redefinedBy.clear()
            return
        }
        subEps.each{ ep ->
            setRedefinedBy(ep)
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
