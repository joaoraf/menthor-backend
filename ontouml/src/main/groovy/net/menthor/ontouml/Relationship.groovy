package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MRelationship
import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.stereotypes.ParticipationStereotype
import net.menthor.ontouml.stereotypes.TemporalStereotype
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.TransitivityValue

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Relationship extends MRelationship {

    protected RelationshipStereotype stereotype
    protected TemporalStereotype temporalStereotype
    protected ParticipationStereotype participationStereotype

    protected ReflexivityValue reflexivityValue
    protected SymmetryValue symmetryValue
    protected TransitivityValue transitivityValue
    protected CiclicityValue ciclicityValue

    //=============================
    // Getters
    //=============================

    RelationshipStereotype getStereotype() { return stereotype }

    TemporalStereotype getTemporalStereotype() { return temporalStereotype }

    ParticipationStereotype getParticipationStereotype() { return participationStereotype }

    ReflexivityValue getReflexivityValue() { return reflexivityValue }

    SymmetryValue getSymmetryValue() { return symmetryValue }

    TransitivityValue getTransitivityValue() { return transitivityValue }

    CiclicityValue getCiclicityValue() { return ciclicityValue }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setStereotype(RelationshipStereotype stereotype) { this.stereotype = stereotype }

    void setTemporalStereotype(TemporalStereotype temporalStereotype) { this.temporalStereotype = temporalStereotype }

    void setParticipationStereotype(ParticipationStereotype participationStereotype) { this.participationStereotype = participationStereotype }

    void setReflexivityValue(ReflexivityValue reflexivityValue) { this.reflexivityValue = reflexivityValue }

    void setSymmetryValue(SymmetryValue symmetryValue) { this.symmetryValue = symmetryValue }

    void setTransitivityValue(TransitivityValue transitivityValue) { this.transitivityValue = transitivityValue }

    void setCiclicityValue(CiclicityValue ciclicityValue) { this.ciclicityValue = ciclicityValue }

    //=============================
    // Default values
    //=============================

    void setDefaultCyclicityValue(){
        if(isMeronymic()||isCharacterization()||isCausation() || isPrecedes()||isMeets()||isFinishes()||isStarts()||isDuring()) {
            setCiclicityValue(CiclicityValue.ACYCLIC)
        } else if(isOverlaps()) {
            setCiclicityValue(CiclicityValue.NON_CYCLIC)
        } else if(isEquals()) {
            setCiclicityValue(CiclicityValue.CYCLIC)
        }
    }

    void setDefaultTransitivityValue(){
        if(isMemberOf()||isMeets()) {
            setTransitivityValue(TransitivityValue.INTRANSITIVE)
        } else if(isComponentOf()||isOverlaps()) {
            setTransitivityValue(TransitivityValue.NON_TRANSITIVE)
        } else if(isSubCollectionOf()||isSubQuantityOf()||isSubEventOf()||isConstitution() ||isCharacterization()||
            isCausation()||isPrecedes()||isFinishes()||isStarts()||isDuring()||isEquals()){
            setTransitivityValue(TransitivityValue.TRANSITIVE)
        }
    }

    void setDefaultReflexivityValue(){
        if(isMemberOf()||isComponentOf()||isSubCollectionOf()||isSubQuantityOf()) {
            setReflexivityValue(ReflexivityValue.NON_REFLEXIVE)
        } else if(isFinishes()||isStarts()||isDuring()||isEquals()||isOverlaps()) {
            setReflexivityValue(ReflexivityValue.REFLEXIVE)
        } else if(isSubEventOf()||isConstitution()||isMediation()||isCharacterization()||isCausation()) {
            setReflexivityValue(ReflexivityValue.IRREFLEXIVE)
        }
    }

    void setDefaultSymmetryValue(){
        if(isMeronymic()||isCharacterization()||isCausation()|| isPrecedes()||isMeets()||isFinishes()||isStarts()) {
            setSymmetryValue(SymmetryValue.ANTI_SYMMETRIC)
        } else if(isDuring()) {
            setSymmetryValue(SymmetryValue.ASSYMETRIC)
        } else if(isEquals()||isOverlaps()) {
            setSymmetryValue(SymmetryValue.SYMMETRIC)
        }
    }

    void setDefaultDependencyValue(){
        if(isCausation() || isSubEventOf() || isTemporal() || isDerivation() || isQuaPartOf()){
            sourceEndPoint().setDependency(true);
        }
        if (isCausation() || isMediation() || isSubEventOf() || isCharacterization() ||
            isParticipation() || isTemporal() || isDerivation() || isQuaPartOf()){
            targetEndPoint().setDependency(true);
        }
    }

    void setDefaultCardinalityValues(){
        EndPoint ep1 = sourceEndPoint()
        if(ep1!=null) {
            if (isCharacterization() || isStructuration() || isQuaPartOf()) {
                ep1.setCardinalities(1, 1)
            } else if (isSubQuantityOf()) {
                ep1.setCardinalities(0, 1)
            } else if (isDerivation()) {
                ep1.setCardinalities(1, -1)
            } else {
                ep1.setCardinalities(0, -1)
            }
        }
        EndPoint ep2 = targetEndPoint()
        if(ep2!=null) {
            if (isSubQuantityOf() || isSubCollectionOf()) {
                ep2.setCardinalities(0, 1)
            } else if (isMediation() || isCharacterization() || isInstanceOf() || isDerivation()) {
                ep2.setCardinalities(1, -1)
            } else {
                ep2.setCardinalities(0, -1)
            }
        }
    }

    @Override
    void setDefaultEndPoints(int arity){
        for (int i = 1; i <= arity; i++) {
            EndPoint ep = new EndPoint();
            ep.setCardinalities(1, 1)
            setEndPoint(ep)
        }
        setDefaultDependencyValue()
        setDefaultCardinalityValues()
    }

    //================================
    //Stereotype Checking
    //================================

    @JsonIgnore
    boolean isComponentOf(){ stereotype==RelationshipStereotype.COMPONENTOF }

    @JsonIgnore
    boolean isMemberOf(){ stereotype==RelationshipStereotype.MEMBEROF }

    @JsonIgnore
    boolean isSubCollectionOf(){ stereotype==RelationshipStereotype.SUBCOLLECTIONOF }

    @JsonIgnore
    boolean isSubQuantityOf(){ stereotype==RelationshipStereotype.SUBQUANTITYOF }

    @JsonIgnore
    boolean isQuaPartOf(){ stereotype==RelationshipStereotype.QUAPARTOF }

    @JsonIgnore
    boolean isConstitution(){ stereotype==RelationshipStereotype.CONSTITUTION }

    @JsonIgnore
    boolean isCharacterization(){ stereotype==RelationshipStereotype.CHARACTERIZATION }

    @JsonIgnore
    boolean isMediation(){ stereotype==RelationshipStereotype.MEDIATION }

    @JsonIgnore
    boolean isMaterial(){ stereotype==RelationshipStereotype.MATERIAL }

    @JsonIgnore
    boolean isFormal(){ stereotype==RelationshipStereotype.FORMAL }

    @JsonIgnore
    boolean isStructuration(){ stereotype==RelationshipStereotype.STRUCTURATION }

    @JsonIgnore
    boolean isParticipation(){ stereotype==RelationshipStereotype.PARTICIPATION }

    @JsonIgnore
    boolean isSubEventOf(){ stereotype==RelationshipStereotype.SUBEVENTOF }

    @JsonIgnore
    boolean isCausation(){ stereotype==RelationshipStereotype.CAUSATION }

    @JsonIgnore
    boolean isTemporal(){ stereotype==RelationshipStereotype.TEMPORAL }

    @JsonIgnore
    boolean isInstanceOf(){ stereotype==RelationshipStereotype.INSTANCEOF }

    @JsonIgnore
    boolean isDerivation(){ stereotype==RelationshipStereotype.DERIVATION }

    @JsonIgnore
    boolean isMeronymic() {
        isComponentOf() || isMemberOf() || isSubQuantityOf() || isSubCollectionOf() || isConstitution() || isSubEventOf()
    }

    @JsonIgnore
    boolean isStarts(){ isTemporal() && temporalStereotype==TemporalStereotype.STARTS }

    @JsonIgnore
    boolean isPrecedes(){ isTemporal() && temporalStereotype==TemporalStereotype.PRECEDES }

    @JsonIgnore
    boolean isEquals(){ isTemporal() && temporalStereotype==TemporalStereotype.EQUALS }

    @JsonIgnore
    boolean isMeets(){ isTemporal() && temporalStereotype==TemporalStereotype.MEETS }

    @JsonIgnore
    boolean isFinishes(){ isTemporal() && temporalStereotype==TemporalStereotype.FINISHES }

    @JsonIgnore
    boolean isOverlaps(){ isTemporal() && temporalStereotype==TemporalStereotype.OVERLAPS }

    @JsonIgnore
    boolean isDuring(){ isTemporal() && temporalStereotype==TemporalStereotype.DURING }

    @JsonIgnore
    boolean isCreation() { return isParticipation() && participationStereotype == ParticipationStereotype.CREATION }

    @JsonIgnore
    boolean isDestruction() { return isParticipation() && participationStereotype == ParticipationStereotype.DESTRUCTION }

    @JsonIgnore
    boolean isChange() { return isParticipation() && participationStereotype == ParticipationStereotype.CHANGE }

    @JsonIgnore
    boolean isTargetATruthMaker(){ return isTargetAClass() && ((Class)target()).isTruthMaker() }

    @JsonIgnore
    boolean isSourceATruthMaker(){ return isSourceAClass() && ((Class)source()).isTruthMaker() }

    @JsonIgnore
    boolean isTargetAnEvent(){ return isTargetAClass() && ((Class)target()).isEvent() }

    @JsonIgnore
    boolean isSourceAnEvent(){ return isSourceAClass() && ((Class)source()).isEvent() }

    @JsonIgnore
    boolean isTargetAStructure(){ return isTargetADataType() && ((DataType)target()).isStructure() }

    @JsonIgnore
    boolean isSourceAStructure(){ return isSourceADataType() && ((DataType)source()).isStructure() }

    @JsonIgnore
    boolean isSourceAQuality(){ return isSourceAClass() && ((Class)source()).isQuality() }

    @JsonIgnore
    boolean isTargetAQuality(){ return isTargetAClass() && ((Class)target()).isQuality() }

    @JsonIgnore
    boolean isTargetANonQualitativeIntrinsicMoment(){
        return isTargetAClass() && ((Class)target()).isNonQualitativeIntrinsicMoment()
    }

    @JsonIgnore
    boolean isSourceANonQualitativeIntrinsicMoment(){
        return isSourceAClass() && ((Class)source()).isNonQualitativeIntrinsicMoment()
    }

    @JsonIgnore
    boolean isTargetAMaterialRelationship(){
        return isTargetARelationship() && ((Relationship)target()).isMaterial()
    }

    @JsonIgnore
    boolean isSourceAMaterialRelationship(){
        return isSourceARelationship() && ((Relationship)source()).isMaterial()
    }

    //================================
    //Part and Whole
    //================================

    EndPoint partEndPoint(){
        if(isMeronymic()) { return targetEndPoint(); }
        else return null;
    }
    EndPoint wholeEndPoint(){
        if(isMeronymic()) { return sourceEndPoint(); }
        else return null;
    }
    /** Returns the source (first end-class) of this relationship */
    Class wholeClass(){
        if(isMeronymic()) { return sourceClass(); }
        else return null;
    }
    /** Returns the target (second end-class) of this relationship */
    Class partClass(){
        if(isMeronymic()) { return targetClass(); }
        else return null;
    }

    //================================
    //Meta-Attributes Checking
    //================================

    /** A part is essential if the target end of a meronymic relationship is dependent on the rigid source type */
    @JsonIgnore
    boolean isPartEssential() {
        targetEndPoint().isDependency() && sourceClass().isRigid() && isMeronymic()
    }

    /** A part is inseparable if the source end of a meronymic relationship is dependent on the rigid target type */
    @JsonIgnore
    boolean isPartInseparable() {
        sourceEndPoint().isDependency() && targetClass().isRigid() && isMeronymic()
    }

    /** A part is immutable if the source end of a meronymic relationship is dependent on the anti-rigid target type */
    @JsonIgnore
    boolean isPartImmutable() {
        sourceEndPoint().isDependency() && targetClass().isAntiRigid() && isMeronymic()
    }

    /** A whole is immutable if the target end of a meronymic relationship is dependent on the anti-rigid source type */
    @JsonIgnore
    boolean isWholeImmutable() {
        targetEndPoint().isDependency() && sourceClass().isAntiRigid() && isMeronymic()
    }

    /** A part is mandatory if the target end of a meronymic relationship has a lower bound of at least 1 */
    @JsonIgnore
    boolean isPartMandatory() {
        targetEndPoint().lowerBound>=1 && isMeronymic()
    }

    /** A whole is mandatory if the source end of a meronymic relationship has a lower bound of at least 1 */
    @JsonIgnore
    boolean isWholeMandatory() {
        sourceEndPoint().lowerBound>=1 && isMeronymic()
    }

    /** A part is shareable if the source end of a meronymic relationship has a upper bound greater than 1 */
    @JsonIgnore
    boolean isPartShareable() {
        sourceEndPoint().upperBound > 1 && isMeronymic()
    }

    //================================
    //End Point
    //================================

    @JsonIgnore
    boolean shouldInvertEndPoints(){
        if(isMediation() && !isSourceATruthMaker() && isTargetATruthMaker()) {
            return true;
        } else if(isCharacterization() && isTargetANonQualitativeIntrinsicMoment() && !isSourceANonQualitativeIntrinsicMoment()) {
            return true;
        } else if(isStructuration() && isTargetAStructure() && !isSourceAQuality()) {
            return true;
        } else if(isParticipation() && !isSourceAnEvent() && isTargetAnEvent()) {
            return true;
        } else if(isDerivation() && isSourceATruthMaker() && isTargetAMaterialRelationship()) {
            return true;
        } else if(isQuaPartOf() && isTargetATruthMaker() && !isSourceANonQualitativeIntrinsicMoment()) {
            return true;
        } else {
            return false
        }
    }

    String toString() { Printer.print(this) }
}
