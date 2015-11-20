package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MClassifier
import net.menthor.mcore.traits.MNamedElement
import net.menthor.mcore.MPackage
import net.menthor.ontouml.stereotypes.ClassStereotype
import net.menthor.ontouml.stereotypes.ConstraintStereotype
import net.menthor.ontouml.stereotypes.DataTypeStereotype
import net.menthor.ontouml.stereotypes.RelationshipStereotype

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class Package extends MPackage {

    Package createPackage(String name) {
        return Factory.createPackage(name,this)
    }

    Generalization createGeneralization(MClassifier source, MClassifier target){
        return Factory.createGeneralization(source, target, this)
    }

    GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, List generalizations){
        return Factory.createGeneralizationSet(isCovering,isDisjoint,generalizations,this)
    }

    GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint){
        return Factory.createGeneralizationSet(isCovering,isDisjoint,this)
    }

    GeneralizationSet createPartition(List<MClassifier> specifics, MClassifier general) {
        return Factory.createPartition(specifics,general,this)
    }

    Class createClass(ClassStereotype c, String name){
        return Factory.createClass(c, name,this)
    }

    DataType createDataType(DataTypeStereotype c, String name){
        return Factory.createDataType(c, name,this)
    }

    Class createKind(String name){
        return Factory.createClass(ClassStereotype.KIND,name,this)
    }

    Class createCollective(String name){
        return Factory.createClass(ClassStereotype.COLLECTIVE,name,this)
    }

    Class createQuantity(String name){
        return Factory.createClass(ClassStereotype.QUANTITY,name,this)
    }

    Class createSubKind(String name){
        return Factory.createClass(ClassStereotype.SUBKIND,name,this)
    }

    Class createPhase(String name){
        return Factory.createClass(ClassStereotype.PHASE,name,this)
    }

    Class createRole(String name){
        return Factory.createClass(ClassStereotype.ROLE,name,this)
    }

    Class createMixin(String name){
        return Factory.createClass(ClassStereotype.MIXIN,name,this)
    }

    Class createCategory(String name){
        return Factory.createClass(ClassStereotype.CATEGORY,name,this)
    }

    Class createRoleMixin(String name){
        return Factory.createClass(ClassStereotype.ROLEMIXIN,name,this)
    }

    Class createPhaseMixin(String name){
        return Factory.createClass(ClassStereotype.PHASEMIXIN,name,this)
    }

    Class createMode(String name){
        return Factory.createClass(ClassStereotype.MODE,name,this)
    }

    Class createRelator(String name){
        return Factory.createClass(ClassStereotype.RELATOR,name,this)
    }

    Class createQuality(String name){
        return Factory.createClass(ClassStereotype.QUALITY,name,this)
    }

    Class createEvent(String name){
        return Factory.createClass(ClassStereotype.EVENT,name,this)
    }

    Class createHighorder(String name){
        return Factory.createClass(ClassStereotype.HIGHORDER,name,this)
    }

    Relationship createRelationship(RelationshipStereotype stereo, MClassifier source, MClassifier target){
        return Factory.createRelationship(stereo, source, target, this)
    }
    Relationship createDerivation(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.DERIVATION, source, target, this)
    }

    Relationship createTemporal(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.TEMPORAL, source, target, this)
    }

    Relationship createStructuration(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.STRUCTURATION, source, target, this)
    }

    Relationship createCausation(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.CAUSATION, source, target, this)
    }

    Relationship createCharacterization(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.CHARACTERIZATION, source, target, this)
    }

    Relationship createComponentOf(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.COMPONENTOF, source, target, this)
    }

    Relationship createConsitution(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.CONSTITUTION, source, target, this)
    }

    Relationship createFormal(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.FORMAL, source, target, this)
    }

    Relationship createInstanceOf(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.INSTANCEOF, source, target, this)
    }

    Relationship createMaterial(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.MATERIAL, source, target, this)
    }

    Relationship createMediation(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.MEDIATION, source, target, this)
    }

    Relationship createMemberOf(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.MEMBEROF, source, target, this)
    }

    Relationship createParticipation(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.PARTICIPATION, source, target, this)
    }

    Relationship createQuaPartOf(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.QUAPARTOF, source, target, this)
    }

    Relationship createSubCollectionOf(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.SUBCOLLECTIONOF, source, target, this)
    }

    Relationship createSubEventOf(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.SUBEVENTOF, source, target, this)
    }

    Relationship createSubQuantityOf(MClassifier source, MClassifier target){
        return Factory.createRelationship(RelationshipStereotype.SUBQUANTITYOF, source, target, this)
    }

    Relationship createRelationship(RelationshipStereotype stereo, MClassifier source, int sourceLower, int sourceUpper, String name,
                                    MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(stereo, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createMediation(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.MEDIATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createMaterial(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.MATERIAL, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createFormal(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.FORMAL, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createCharacterization(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.CHARACTERIZATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createCausation(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.CAUSATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createComponentOf(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.COMPONENTOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createConstitution(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.CONSTITUTION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createDerivation(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.DERIVATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createInstanceOf(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.INSTANCEOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createMemberOf(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.MEMBEROF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createParticipation(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.PARTICIPATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createQuaPartOf(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.QUAPARTOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createStructuration(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.STRUCTURATION, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createSubCollectionOf(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.SUBCOLLECTIONOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createSubEventOf(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.SUBEVENTOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createSubQuantityOf(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.SUBQUANTITYOF, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Relationship createTemporal(MClassifier source, int sourceLower, int sourceUpper, String name, MClassifier target, int targetLower, int targetUpper){
        return Factory.createRelationship(RelationshipStereotype.TEMPORAL, source, sourceLower, sourceUpper, name, target, targetLower, targetUpper, this)
    }

    Constraint createConstraint(MNamedElement context, ConstraintStereotype stereo, String identifier, String expression){
        return Factory.createConstraint(context, stereo, identifier, expression, this)
    }

    String toString() { Printer.print(this) }
}
