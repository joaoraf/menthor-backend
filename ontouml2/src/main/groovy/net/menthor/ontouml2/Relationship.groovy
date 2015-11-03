package net.menthor.ontouml2

import net.menthor.ontouml2.stereotypes.ParticipationStereotype
import net.menthor.ontouml2.stereotypes.RelationshipStereotype
import net.menthor.ontouml2.stereotypes.TemporalStereotype
import net.menthor.ontouml2.traits.Classifier
import net.menthor.ontouml2.values.CiclicityValue
import net.menthor.ontouml2.values.ReflexivityValue
import net.menthor.ontouml2.values.SymmetryValue
import net.menthor.ontouml2.values.TransitivityValue;

class Relationship implements Classifier {

    RelationshipStereotype stereotype
    ReflexivityValue reflexivityValue
    SymmetryValue symmetryValue
    TransitivityValue transitivityValue
    CiclicityValue ciclicityValue
    List<EndPoint> endPoints
    TemporalStereotype temporalStereotype
    ParticipationStereotype participationStereotype

    boolean isComponentOf(){
        stereotype==RelationshipStereotype.COMPONENT_OF
    }
    boolean isMemberOf(){
        stereotype==RelationshipStereotype.MEMBER_OF
    }
    boolean isSubCollectionOf(){
        stereotype==RelationshipStereotype.SUB_COLLECTION_OF
    }
    boolean isSubQuantityOf(){
        stereotype==RelationshipStereotype.SUB_QUANTITY_OF
    }
    boolean isQuaPartOf(){
        stereotype==RelationshipStereotype.QUA_PART_OF
    }
    boolean isConstitution(){
        stereotype==RelationshipStereotype.CONSTITUTION
    }
    boolean isCharacterization(){
        stereotype==RelationshipStereotype.CHARACTERIZATION
    }
    boolean isMediation(){
        stereotype==RelationshipStereotype.MEDIATION
    }
    boolean isMaterial(){
        stereotype==RelationshipStereotype.MATERIAL
    }
    boolean isFormal(){
        stereotype==RelationshipStereotype.FORMAL
    }
    boolean isStructuration(){
        stereotype==RelationshipStereotype.STRUCTURATION
    }
    boolean isParticipation(){
        stereotype==RelationshipStereotype.PARTICIPATION
    }
    boolean isSubEventOf(){
        stereotype==RelationshipStereotype.SUB_EVENT_OF
    }
    boolean isCausation(){
        stereotype==RelationshipStereotype.CAUSATION
    }
    boolean isTemporal(){
        stereotype==RelationshipStereotype.TEMPORAL
    }
    boolean isInstanceOf(){
        stereotype==RelationshipStereotype.INSTANCE_OF
    }
    boolean isDerivation(){
        stereotype==RelationshipStereotype.DERIVATION
    }
    boolean isMeronymic() {
        isComponentOf() || isMemberOf() || isSubQuantityOf() || isSubCollectionOf() || isConstitution() || isSubEventOf()
    }
    boolean isBinary() {
        return endPoints.size()==2
    }
    boolean isTernary() {
        return endPoints.size()==3
    }
    boolean isStarts(){
        isTemporal() && temporalStereotype==TemporalStereotype.STARTS
    }
    boolean isPrecedes(){
        isTemporal() && temporalStereotype==TemporalStereotype.PRECEDES
    }
    boolean isEquals(){
        isTemporal() && temporalStereotype==TemporalStereotype.EQUALS
    }
    boolean isMeets(){
        isTemporal() && temporalStereotype==TemporalStereotype.MEETS
    }
    boolean isFinishes(){
        isTemporal() && temporalStereotype==TemporalStereotype.FINISHES
    }
    boolean isOverlaps(){
        isTemporal() && temporalStereotype==TemporalStereotype.OVERLAPS
    }
    boolean isDuring(){
        isTemporal() && temporalStereotype==TemporalStereotype.DURING
    }
    boolean isCreation() {
        return isParticipation() && participationStereotype == ParticipationStereotype.CREATION
    }
    boolean isDestruction() {
        return isParticipation() && participationStereotype == ParticipationStereotype.DESTRUCTION
    }
    boolean isChange() {
        return isParticipation() && participationStereotype == ParticipationStereotype.CHANGE
    }
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
    EndPoint partEndPoint(){
        if(isMeronymic()) { return targetEndPoint(); }
        else return null;
    }
    EndPoint wholeEndPoint(){
        if(isMeronymic()) { return sourceEndPoint(); }
        else return null;
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
    boolean isSourceAClass(){
        return (sourceClass()==null) ? false : true
    }
    /** Returns the source (first end-class) of this relationship */
    Class sourceClass() {
        if(source()!=null) return source() as Class
        return null;
    }
    boolean isTargetAClass(){
        return (targetClass()==null) ? false : true
    }
    /** Returns the target (second end-class) of this relationship */
    Class targetClass(){
        if(target()!=null) return target() as Class
        return null;
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
    boolean isSourceADataType(){
        return (sourceDataType()==null) ? false : true
    }
    /** Returns the source (first end-dataType) of this relationship */
    DataType sourceDataType(){
        if(source()!=null) return source() as DataType
        return null;
    }
    boolean isTargetADataType(){
        return (targetDataType()==null) ? false : true
    }
    /** Returns the target (second end-dataType) of this relationship */
    DataType targetDataType(){
        if(target()!=null) return target() as DataType
        return null;
    }
    boolean isSourceARelationship(){
        return (sourceRelationship()==null) ? false : true
    }
    /** Returns the source (first end-relationship) of this relationship */
    Relationship sourceRelationship() {
        if(source()!=null) return source() as Relationship
        return null;
    }
    boolean isTargetARelationship(){
        return (targetRelationship()==null) ? false : true
    }
    /** Returns the target (second end-relationship) of this relationship */
    Relationship targetRelationship(){
        if(target()!=null) return target() as Relationship
        return null;
    }
    boolean isTargetATruthMaker(){
        return isTargetAClass() && ((Class)target()).isTruthMaker()
    }
    boolean isSourceATruthMaker(){
        return isSourceAClass() && ((Class)source()).isTruthMaker()
    }
    boolean isTargetAnEvent(){
        return isTargetAClass() && ((Class)target()).isEvent()
    }
    boolean isSourceAnEvent(){
        return isSourceAClass() && ((Class)source()).isEvent()
    }
    boolean isTargetAStructure(){
        return isTargetADataType() && ((DataType)target()).isStructure()
    }
    boolean isSourceAStructure(){
        return isSourceADataType() && ((DataType)source()).isStructure()
    }
    boolean isSourceAQuality(){
        return isSourceAClass() && ((Class)source()).isQuality()
    }
    boolean isTargetAQuality(){
        return isTargetAClass() && ((Class)target()).isQuality()
    }
    boolean isTargetANonQualitativeIntrinsicMoment(){
        return isTargetAClass() && ((Class)target()).isNonQualitativeIntrinsicMoment()
    }
    boolean isSourceANonQualitativeIntrinsicMoment(){
        return isSourceAClass() && ((Class)source()).isNonQualitativeIntrinsicMoment()
    }
    boolean isTargetAMaterialRelationship(){
        return isTargetARelationship() && ((Relationship)target()).isMaterial()
    }
    boolean isSourceAMaterialRelationship(){
        return isSourceARelationship() && ((Relationship)source()).isMaterial()
    }
    /** Checks if this relationship is derived i.e. checking if there is at least one end-point which is derived */
    boolean isDerived(){
       endPoints.each{ ep ->
            if (ep.isDerived()) return true;
        }
        return false;
    }
    /** Checks if there is at least one end-point in this relationship of classifier c. */
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
    boolean isPartEssential() {
        targetEndPoint().isDependency && sourceClass().isRigid() && isMeronymic()
    }
    /** A part is inseparable if the source end of a meronymic relationship is dependent on the rigid target type */
    boolean isPartInseparable() {
        sourceEndPoint().isDependency && targetClass().isRigid() && isMeronymic()
    }
    /** A part is immutable if the source end of a meronymic relationship is dependent on the anti-rigid target type */
    boolean isPartImmutable() {
        sourceEndPoint().isDependency && targetClass().isAntiRigid() && isMeronymic()
    }
    /** A whole is immutable if the target end of a meronymic relationship is dependent on the anti-rigid source type */
    boolean isWholeImmutable() {
        targetEndPoint().isDependency && sourceClass().isAntiRigid() && isMeronymic()
    }
    /** A part is mandatory if the target end of a meronymic relationship has a lower bound of at least 1 */
    boolean isPartMandatory() {
        targetEndPoint().lowerBound>=1 && isMeronymic()
    }
    /** A whole is mandatory if the source end of a meronymic relationship has a lower bound of at least 1 */
    boolean isWholeMandatory() {
        sourceEndPoint().lowerBound>=1 && isMeronymic()
    }
    /** A part is shareable if the source end of a meronymic relationship has a upper bound greater than 1 */
    boolean isPartShareable() {
        sourceEndPoint().upperBound > 1 && isMeronymic()
    }
}
