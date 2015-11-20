package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MClassifier

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MRelationship implements MClassifier {

    protected List<MEndPoint> endPoints = []

    //=============================
    // Getters
    //=============================

    List<MEndPoint> getEndPoints() { return endPoints }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setEndPoint(MEndPoint ep){
        if(ep==null) return
        if(!endPoints.contains(ep)){
            endPoints.add(ep)
        }
        //Ensuring opposite end
        ep.setOwner(this)
    }

    void setEndPoints(List<MEndPoint> eps){
        if(eps==null || eps==[]){
            this.endPoints.clear()
            return
        }
        eps.each{ ep->
            setEndPoint(ep)
        }
    }

    //================================
    //Source and Target
    //================================

    @JsonIgnore
    boolean isBinary() { return endPoints.size()==2 }

    @JsonIgnore
    boolean isTernary() { return endPoints.size()==3 }

    /** Returns the source (first end-point) of this relationship */
    MEndPoint sourceEndPoint(){
        if(endPoints.size()>0){ return endPoints.get(0) }
        return null;
    }
    /** Returns the target (second end-point) of this relationship */
    MEndPoint targetEndPoint(){
        if(endPoints.size()>1){ return endPoints.get(1) }
        return null;
    }
    /** Returns the source (first end-classifier) of this relationship */
    MClassifier source(){
        if(sourceEndPoint()!=null){ return sourceEndPoint().getClassifier() }
        return null;
    }
    /** Returns the target (second end-classifier) of this relationship */
    MClassifier target(){
        if(targetEndPoint()!=null){ return targetEndPoint().getClassifier() }
        return null;
    }

    @JsonIgnore
    boolean isSourceAClass(){ return (sourceClass()==null) ? false : true }

    /** Returns the source (first end-class) of this relationship */
    MClass sourceClass() {
        if(source()!=null) return source() as MClass
        return null;
    }

    @JsonIgnore
    boolean isTargetAClass(){ return (targetClass()==null) ? false : true }

    /** Returns the target (second end-class) of this relationship */
    MClass targetClass(){
        if(target()!=null) return target() as MClass
        return null;
    }

    @JsonIgnore
    boolean isSourceADataType(){ return (sourceDataType()==null) ? false : true }

    /** Returns the source (first end-dataType) of this relationship */
    MDataType sourceDataType(){
        if(source()!=null) return source() as MDataType
        return null;
    }

    @JsonIgnore
    boolean isTargetADataType(){ return (targetDataType()==null) ? false : true }

    /** Returns the target (second end-dataType) of this relationship */
    MDataType targetDataType(){
        if(target()!=null) return target() as MDataType
        return null;
    }

    @JsonIgnore
    boolean isSourceARelationship(){ return (sourceRelationship()==null) ? false : true }

    /** Returns the source (first end-relationship) of this relationship */
    MRelationship sourceRelationship() {
        if(source()!=null) return source() as MRelationship
        return null;
    }

    @JsonIgnore
    boolean isTargetARelationship(){ return (targetRelationship()==null) ? false : true }

    /** Returns the target (second end-relationship) of this relationship */
    MRelationship targetRelationship(){
        if(target()!=null) return target() as MRelationship
        return null;
    }

    //================================
    //Meta-Attributes Checking
    //================================

    /** Checks if this relationship is derived i.e. checking if there is at least one end-point which is derived */
    @JsonIgnore
    boolean isDerived(){
       endPoints.each{ ep ->
            if (ep.isDerived()) return true;
        }
        return false;
    }

    /** Checks if there is at least one end-point in this relationship of classifier c. */
    @JsonIgnore
    boolean isConnecting(MClassifier c){
        return endPoints.any{ ep ->
            ep.getClassifier().equals(c)
        }
    }

    void setDefaultEndPoints(int arity) {
        for (int i = 1; i <= arity; i++) {
            MEndPoint ep = new MEndPoint();
            ep.setCardinalities(1, 1)
            setEndPoint(ep)
        }
    }
}
