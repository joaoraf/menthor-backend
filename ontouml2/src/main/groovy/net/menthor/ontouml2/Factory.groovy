package net.menthor.ontouml2

import net.menthor.ontouml2.stereotypes.ClassStereotype
import net.menthor.ontouml2.stereotypes.DataTypeStereotype
import net.menthor.ontouml2.stereotypes.PrimitiveStereotype
import net.menthor.ontouml2.stereotypes.QualityStereotype
import net.menthor.ontouml2.stereotypes.RelationshipStereotype
import net.menthor.ontouml2.traits.Classifier
import net.menthor.ontouml2.traits.ContainedElement
import net.menthor.ontouml2.traits.Container
import net.menthor.ontouml2.traits.NamedElement
import net.menthor.ontouml2.traits.Type
import net.menthor.ontouml2.values.CiclicityValue
import net.menthor.ontouml2.values.ReflexivityValue
import net.menthor.ontouml2.values.SymmetryValue
import net.menthor.ontouml2.values.TransitivityValue

class Factory {

    static void attachName(NamedElement element, String name){
        if(name!=null)element.setName(name)
        else element.setName("")
    }

    static void attachDefaultEndPointName(EndPoint ep){
        String name = new String();
        if (ep.getClassifier() != null){
            name = ep.getClassifier().getName();
            if (name == null || name.trim().isEmpty()) name = "source";
            else name = name.trim().toLowerCase();
        }
        ep.setName(name);
    }

    static void attachContainer(ContainedElement ce, Container container){
        if(container!=null) {
            container.getElements().add(ce)
            ce.setHolder(container)
        }
    }

    static void attachGeneralizationSet(List<Generalization> generalizations, GeneralizationSet gs) {
        gs.getGeneralizations().addAll(generalizations);
        generalizations.each { g ->
            g.setGeneralizationSet(gs)
        }
    }

    static void attachGeneralizationSet(Generalization gen, GeneralizationSet gs) {
        gen.setGeneralizationSet(gs);
        gs.getGeneralizations().add(gen)
    }

    static void attachLiterals(DataType enu, List<Literal> literals){
        enu.getLiterals().addAll(literals);
        for(Literal lit: literals) lit.setOwner(enu);
    }

    static void attachAttribute(Type owner, Attribute atrr){
        if(owner!=null)owner.getAttributes().add(atrr);
        atrr.setOwner(owner);
    }

    static void attachEndPoint(Relationship owner, EndPoint ep){
        if(owner!=null){
            owner.getEndPoints().add(ep);
            ep.setOwner(owner);
        }
    }

    static void attachEndPoints(Relationship owner, EndPoint epSource, EndPoint epTarget){
        attachEndPoint(owner,epSource)
        attachEndPoint(owner,epTarget)
    }

    static void attachDefaultEndPoints(Relationship rel, int arity){
        for (int i = 1; i <= arity; i++) {
            EndPoint ep = new EndPoint();
            ep.setCardinalities(1,1)
            rel.getEndPoints().add(ep)
        }
        attachDefaultDependencyValue(rel)
        attachDefaultCardinalityValues(rel)
    }

    static Package createPackage(String name, Container container){
        Package p = new Package()
        attachName(p,name)
        attachContainer(p,container)
        return p
    }

    static Model createModel(String name){
        Model p = new Model()
        attachName(p,name)
        return p
    }

    static Generalization createGeneralization (Classifier specific, Classifier general, Container container){
        Generalization g = new Generalization()
        g.setGeneral(general)
        g.setSpecific(specific)
        attachContainer(g, container)
        return g;
    }

    static GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, Container container){
        GeneralizationSet gs = new GeneralizationSet()
        gs.setIsCovering(isCovering)
        gs.setIsDisjoint(isDisjoint)
        attachContainer(gs, container)
        return gs
    }

    static GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, List generalizations, Container container) {
        GeneralizationSet gs = createGeneralizationSet(isCovering,isDisjoint,container);
        attachGeneralizationSet(generalizations,gs)
        return gs;
    }

    static GeneralizationSet createPartition(List<Classifier> specifics, Classifier general, Container container){
        GeneralizationSet gs = createGeneralizationSet(true,true,container)
        specifics.each{ spec ->
            Generalization g = createGeneralization(spec, general, container)
            attachGeneralizationSet(g)
        }
        return gs;
    }

    static void attachDefaultCyclicityValue(Relationship rel){
         if(rel.isMeronymic()||rel.isCharacterization()||rel.isCausation() || rel.isPrecedes()||rel.isMeets()||rel.isFinishes()||rel.isStarts()||rel.isDuring()) {
             rel.setCiclicityValue(CiclicityValue.ACYCLIC)
         } else if(rel.isOverlaps()) {
             rel.setCiclicityValue(CiclicityValue.NON_CYCLIC)
         } else if(rel.isEquals()) {
             rel.setCiclicityValue(CiclicityValue.CYCLIC)
         }
    }

    static void attachDefaultTransitivityValue(Relationship rel){
        if(rel.isMemberOf()||rel.isMeets()) {
            rel.setTransitivityValue(TransitivityValue.INTRANSITIVE)
        } else if(rel.isComponentOf()||rel.isOverlaps()) {
            rel.setTransitivityValue(TransitivityValue.NON_TRANSITIVE)
        } else if(rel.isSubCollectionOf()||rel.isSubQuantityOf()||rel.isSubEventOf()||rel.isConstitution() ||rel.isCharacterization()||rel.isCausation()||rel.isPrecedes()||rel.isFinishes()||rel.isStarts()||rel.isDuring()||rel.isEquals()){
            rel.setTransitivityValue(TransitivityValue.TRANSITIVE)
        }
    }

    static void attachDefaultReflexivityValue(Relationship rel){
        if(rel.isMemberOf()||rel.isComponentOf()||rel.isSubCollectionOf()||rel.isSubQuantityOf()) {
            rel.setReflexivityValue(ReflexivityValue.NON_REFLEXIVE)
        } else if(rel.isFinishes()||rel.isStarts()||rel.isDuring()||rel.isEquals()||rel.isOverlaps()) {
            rel.setReflexivityValue(ReflexivityValue.REFLEXIVE)
        } else if(rel.isSubEventOf()||rel.isConstitution()||rel.isMediation()||rel.isCharacterization()||rel.isCausation()) {
            rel.setReflexivityValue(ReflexivityValue.IRREFLEXIVE)
        }
    }

    static void attachDefaultSymmetryValue(Relationship rel){
        if(rel.isMeronymic()||rel.isCharacterization()||rel.isCausation()|| rel.isPrecedes()||rel.isMeets()||rel.isFinishes()||rel.isStarts()) {
            rel.setSymmetryValue(SymmetryValue.ANTI_SYMMETRIC)
        } else if(rel.isDuring()) {
            rel.setSymmetryValue(SymmetryValue.ASSYMETRIC)
        } else if(rel.isEquals()||rel.isOverlaps()) {
            rel.setSymmetryValue(SymmetryValue.SYMMETRIC)
        }
    }

    static void attachDefaultDependencyValue(Relationship rel){
        if(rel.isCausation() || rel.isSubEventOf() || rel.isTemporal() || rel.isDerivation() || rel.isQuaPartOf()){
            rel.sourceEndPoint().setIsDependency(true);
        }
        if (rel.isCausation() || rel.isMediation() || rel.isSubEventOf() || rel.isCharacterization() ||
        rel.isParticipation() || rel.isTemporal() || rel.isDerivation() || rel.isQuaPartOf()){
            rel.targetEndPoint().setIsDependency(true);
        }
    }

    static void attachDefaultCardinalityValues(Relationship rel){
        EndPoint ep1 = rel.sourceEndPoint()
        EndPoint ep2 = rel.targetEndPoint()
        if(rel.isCharacterization() || rel.isStructuration() || rel.isQuaPartOf()) {
            ep1.setCardinalities(1,1)
        } else if (rel.isSubQuantityOf()) {
            ep1.setCardinalities(0,1)
        } else if (rel.isDerivation()) {
            ep1.setCardinalities(1,-1)
        } else {
            ep1.setCardinalities(0,-1)
        }
        if (rel.isSubQuantityOf() || rel.isSubCollectionOf()) {
            ep2.setCardinalities(0,1)
        } else if(rel.isMediation() || rel.isCharacterization() || rel.isInstanceOf() || rel.isDerivation()) {
            ep2.setCardinalities(1,-1)
        } else {
            ep2.setCardinalities(0,-1)
        }
    }

    static Relationship createRelationship(RelationshipStereotype stereotype, Classifier source, Classifier target, Container container){
        Relationship rel = new Relationship()
        rel.setStereotype(stereotype)
        attachName("")
        attachDefaultEndPoints(rel,2)
        attachDefaultReflexivityValue(rel)
        attachDefaultSymmetryValue(rel)
        attachDefaultTransitivityValue(rel)
        attachDefaultCyclicityValue(rel)
        attachContainer(rel,container)
        return rel;
    }

    static boolean shouldInvertEndPoints(Relationship rel){
        if(rel.isMediation() && !rel.isSourceATruthMaker() && rel.isTargetATruthMaker()) {
            return true;
        } else if(rel.isCharacterization() && rel.isTargetANonQualitativeIntrinsicMoment() && !rel.isSourceANonQualitativeIntrinsicMoment()) {
            return true;
        } else if(rel.isStructuration() && rel.isTargetAStructure() && !rel.isSourceAQuality()) {
            return true;
        } else if(rel.isParticipation() && !rel.isSourceAnEvent() && rel.isTargetAnEvent()) {
            return true;
        } else if(rel.isDerivation() && rel.isSourceATruthMaker() && rel.isTargetAMaterialRelationship()) {
            return true;
        } else if(rel.isQuaPartOf() && rel.isTargetATruthMaker() && !rel.isSourceANonQualitativeIntrinsicMoment()) {
            return true;
        } else {
            return false
        }
    }

    static Class createClass(ClassStereotype stereotype, String name, Container container){
        Class c = new Class()
        c.setStereotype(stereotype)
        if(c.isMixinClass()) {
            c.setIsAbstract(true)
        }
        attachName(c,name)
        attachContainer(c, container)
        return c;
    }

    static Class createPerceivableQuality(String name, Container container){
        Class quality = createClass(ClassStereotype.QUALITY,name,container)
        quality.setQualityStereotype(QualityStereotype.PERCEIVABLE)
        return quality;
    }

    static Class createNonPerceivableQuality(String name, Container container){
        Class quality = createClass(ClassStereotype.QUALITY,name,container)
        quality.setQualityStereotype(QualityStereotype.NON_PERCEIVABLE)
        return quality;
    }

    static Class createNominalQuality(String name, Container container){
        Class quality = createClass(ClassStereotype.QUALITY,name,container)
        quality.setQualityStereotype(QualityStereotype.NOMINAL)
        return quality;
    }

    static DataType createDataType(DataTypeStereotype stereotype, String name, Container container){
        DataType dt = new DataType()
        dt.setStereotype(stereotype)
        attachName(name)
        attachContainer(dt, container)
        return dt;
    }

    static Literal createLiteral(String text){
        Literal lit = new Literal()
        lit.setText(text)
        return lit
    }

    static List<Literal> createLiterals(Collection<String> textValues){
        List<Literal> result = new ArrayList<Literal>()
        textValues.each{ v ->
            result.add(createLiteral(v))
        }
        return result;
    }

    static DataType createEnumeration(String name, Collection<String> textValues, Container container){
        DataType enu = createDataType(DataTypeStereotype.ENUMERATION,name,container);
        List<Literal> literals = createLiterals(textValues)
        attachLiterals(enu,literals)
        attachContainer(enu,container)
        return enu;
    }

    static Attribute createAttribute (Type owner, PrimitiveStereotype primitive){
        Attribute attribute = createAttribute(owner, primitive, 1, 1, "", false, false)
        return attribute
    }

    static Attribute createAttribute (Type owner, PrimitiveStereotype primitive, int lower, int upper){
        Attribute attribute = new Attribute()
        attribute.setStereotype(primitive)
        attribute.setOwner(owner)
        attribute.setLowerBound(lower)
        attribute.setUpperBound(upper);
        attachName("")
        attachAttribute(owner,attribute)
        return attribute
    }

    static Attribute createAttribute (Type owner, PrimitiveStereotype primitive, int lower, int upper, String name, boolean isDerived, boolean isDependency){
        Attribute attribute = createAttribute(owner, primitive, lower, upper)
        attachName("")
        attribute.setIsDerived(isDerived)
        attribute.setIsDependency(isDependency)
        return attribute;
    }

    static EndPoint createEndPoint(Relationship rel, Classifier classifier, int lower, int upper){
      return createEndPoint(rel, classifier, lower, upper, "");
    }

    static EndPoint createEndPoint(Relationship rel, Classifier classifier, int lower, int upper, String name){
        EndPoint ep = new EndPoint()
        ep.setClassifier(classifier)
        ep.setLowerBound(lower)
        ep.setUpperBound(upper)
        attachName(name)
        attachEndPoint(rel, ep)
        return ep;
    }

}
