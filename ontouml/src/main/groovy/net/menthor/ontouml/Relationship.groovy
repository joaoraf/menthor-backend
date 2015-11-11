package net.menthor.ontouml

import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.stereotypes.ParticipationStereotype
import net.menthor.ontouml.stereotypes.TemporalStereotype
import net.menthor.ontouml.traits.Classifier
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.TransitivityValue
import org.codehaus.jackson.annotate.JsonIgnore
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
class Relationship implements Classifier {

    protected List<EndPoint> endPoints = []

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

    List<EndPoint> getEndPoints() { return endPoints }

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

    void setEndPoint(EndPoint ep){
        if(ep==null) return
        if(!endPoints.contains(ep)){
            endPoints.add(ep)
        }
        //Ensuring opposite end
        ep.setOwner(this)
    }

    void setEndPoints(List<EndPoint> eps){
        if(eps==null || eps==[]){
            this.endPoints.clear()
            return
        }
        eps.each{ ep->
            setEndPoint(ep)
        }
    }

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

    void setDefaultEndPoints(int arity){
        for (int i = 1; i <= arity; i++) {
            EndPoint ep = new EndPoint();
            ep.setCardinalities(1,1)
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
    boolean isBinary() { return endPoints.size()==2 }

    @JsonIgnore
    boolean isTernary() { return endPoints.size()==3 }

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

    //================================
    //Source and Target
    //================================

    /** Returns the source (first end-point) of this relationship */
    EndPoint sourceEndPoint(){
        if(endPoints.size()>0){ return endPoints.get(0) }
        return null;
    }
    /** Returns the target (second end-point) of this relationship */
    EndPoint targetEndPoint(){
        if(endPoints.size()>1){ return endPoints.get(1) }
        return null;
    }
    /** Returns the source (first end-classifier) of this relationship */
    Classifier source(){
        if(sourceEndPoint()!=null){ return sourceEndPoint().getClassifier() }
        return null;
    }
    /** Returns the target (second end-classifier) of this relationship */
    Classifier target(){
        if(targetEndPoint()!=null){ return targetEndPoint().getClassifier() }
        return null;
    }

    @JsonIgnore
    boolean isSourceAClass(){ return (sourceClass()==null) ? false : true }

    /** Returns the source (first end-class) of this relationship */
    java.lang.Class sourceClass() {
        if(source()!=null) return source() as java.lang.Class
        return null;
    }

    @JsonIgnore
    boolean isTargetAClass(){ return (targetClass()==null) ? false : true }

    /** Returns the target (second end-class) of this relationship */
    java.lang.Class targetClass(){
        if(target()!=null) return target() as java.lang.Class
        return null;
    }

    @JsonIgnore
    boolean isSourceADataType(){ return (sourceDataType()==null) ? false : true }

    /** Returns the source (first end-dataType) of this relationship */
    DataType sourceDataType(){
        if(source()!=null) return source() as DataType
        return null;
    }

    @JsonIgnore
    boolean isTargetADataType(){ return (targetDataType()==null) ? false : true }

    /** Returns the target (second end-dataType) of this relationship */
    DataType targetDataType(){
        if(target()!=null) return target() as DataType
        return null;
    }

    @JsonIgnore
    boolean isSourceARelationship(){ return (sourceRelationship()==null) ? false : true }

    /** Returns the source (first end-relationship) of this relationship */
    Relationship sourceRelationship() {
        if(source()!=null) return source() as Relationship
        return null;
    }

    @JsonIgnore
    boolean isTargetARelationship(){ return (targetRelationship()==null) ? false : true }

    /** Returns the target (second end-relationship) of this relationship */
    Relationship targetRelationship(){
        if(target()!=null) return target() as Relationship
        return null;
    }

    @JsonIgnore
    boolean isTargetATruthMaker(){ return isTargetAClass() && ((java.lang.Class)target()).isTruthMaker() }

    @JsonIgnore
    boolean isSourceATruthMaker(){ return isSourceAClass() && ((java.lang.Class)source()).isTruthMaker() }

    @JsonIgnore
    boolean isTargetAnEvent(){ return isTargetAClass() && ((java.lang.Class)target()).isEvent() }

    @JsonIgnore
    boolean isSourceAnEvent(){ return isSourceAClass() && ((java.lang.Class)source()).isEvent() }

    @JsonIgnore
    boolean isTargetAStructure(){ return isTargetADataType() && ((DataType)target()).isStructure() }

    @JsonIgnore
    boolean isSourceAStructure(){ return isSourceADataType() && ((DataType)source()).isStructure() }

    @JsonIgnore
    boolean isSourceAQuality(){ return isSourceAClass() && ((java.lang.Class)source()).isQuality() }

    @JsonIgnore
    boolean isTargetAQuality(){ return isTargetAClass() && ((java.lang.Class)target()).isQuality() }

    @JsonIgnore
    boolean isTargetANonQualitativeIntrinsicMoment(){
        return isTargetAClass() && ((java.lang.Class)target()).isNonQualitativeIntrinsicMoment()
    }

    @JsonIgnore
    boolean isSourceANonQualitativeIntrinsicMoment(){
        return isSourceAClass() && ((java.lang.Class)source()).isNonQualitativeIntrinsicMoment()
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
    java.lang.Class wholeClass(){
        if(isMeronymic()) { return sourceClass(); }
        else return null;
    }
    /** Returns the target (second end-class) of this relationship */
    java.lang.Class partClass(){
        if(isMeronymic()) { return targetClass(); }
        else return null;
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
    boolean isConnecting(Classifier c){
        endPoints.each{ ep ->
            def t = ep.getClassifier();
            if(t!=null){
                if(t.equals(c)) return true;
            }
        }
        return false;
    }

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
}
