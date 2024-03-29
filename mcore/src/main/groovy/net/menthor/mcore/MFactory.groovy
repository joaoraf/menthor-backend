package net.menthor.mcore

import net.menthor.mcore.traits.MClassifier
import net.menthor.mcore.traits.MContainer
import net.menthor.mcore.traits.MElement
import net.menthor.mcore.traits.MNamedElement
import net.menthor.mcore.traits.MType

class MFactory implements MElement {

    private MFactory(){}

    static MPackage createPackage(String name, MContainer container){
        MPackage p = new MPackage()
        p.setName(name)
        p.setContainer(container)
        return p
    }

    static MPackage createModel(String name){
        MPackage p = new MPackage()
        p.setName(name)
        return p
    }

    static MGeneralization createGeneralization (MClassifier specific, MClassifier general, MContainer container){
        MGeneralization g = new MGeneralization()
        g.setGeneral(general)
        g.setSpecific(specific)
        g.setContainer(container)
        return g;
    }

    static MGeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, MContainer container){
        MGeneralizationSet gs = new MGeneralizationSet()
        gs.setIsCovering(isCovering)
        gs.setIsDisjoint(isDisjoint)
        gs.setContainer(container)
        return gs
    }

    static MGeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, List generalizations, MContainer container) {
        MGeneralizationSet gs = createGeneralizationSet(isCovering,isDisjoint,container);
        gs.setGeneralizations(generalizations)
        return gs;
    }

    static MGeneralizationSet createPartition(List<MClassifier> specifics, MClassifier general, MContainer container){
        MGeneralizationSet gs = createGeneralizationSet(true,true,container)
        specifics.each{ spec ->
            MGeneralization g = createGeneralization(spec, general, container)
            g.setGeneralizationSet(gs)
        }
        return gs;
    }

    static MRelationship createRelationship(String name, MContainer container) {
        MRelationship rel = new MRelationship()
        rel.setDefaultEndPoints(2)
        rel.setName(name)
        rel.setContainer(container)
        return rel
    }

    static MRelationship createRelationship(MClassifier source, MClassifier target, MContainer container){
        MRelationship rel = createRelationship("",container)
        rel.getEndPoints().get(0).setClassifier(source)
        rel.getEndPoints().get(1).setClassifier(target)
        return rel;
    }

    static MRelationship createRelationship(MClassifier source, int srcLower, int srcUpper, String name,
        MClassifier target, int tgtLower, int tgtUpper, MContainer container) {
        MRelationship rel = createRelationship(source, target, container)
        rel.setName(name)
        rel.sourceEndPoint().setCardinalities(srcLower, srcUpper)
        rel.targetEndPoint().setCardinalities(tgtLower, tgtUpper)
        return rel
    }

    static MClass createClass(String name, MContainer container){
        MClass c = new MClass()
        c.setName(name)
        c.setContainer(container)
        return c;
    }

    static MDataType createDataType(String name, MContainer container){
        MDataType dt = new MDataType()
        dt.setName(name)
        dt.setContainer(container)
        return dt;
    }

    static MLiteral createLiteral(String text){
        MLiteral lit = new MLiteral()
        lit.setText(text)
        return lit
    }

    static List<MLiteral> createLiterals(Collection<String> textValues){
        List<MLiteral> result = new ArrayList<MLiteral>()
        textValues.each{ v ->
            result.add(createLiteral(v))
        }
        return result;
    }

    static MDataType createDataType(String name, Collection<String> literalValues, MContainer container){
        MDataType enu = createDataType(name,container);
        List<MLiteral> literals = createLiterals(literalValues)
        enu.setLiterals(literals)
        enu.setContainer(container)
        return enu;
    }

    MAttribute createAttribute (MType owner){
        MAttribute attribute = createAttribute(owner, 1, 1, "", false, false)
        return attribute
    }

    static MAttribute createAttribute (MType owner, int lower, int upper){
        MAttribute attribute = new MAttribute()
        attribute.setOwner(owner)
        attribute.setLowerBound(lower)
        attribute.setUpperBound(upper)
        attribute.setName("")
        owner.setAttribute(attribute)
        return attribute
    }

    static MAttribute createAttribute (MType owner, int lower, int upper, String name, boolean isDerived, boolean isDependency){
        MAttribute attribute = createAttribute(owner, lower, upper)
        attribute.setName("")
        attribute.setDerived(isDerived)
        attribute.setDependency(isDependency)
        return attribute;
    }

    static MEndPoint createEndPoint(MRelationship rel, MClassifier classifier, int lower, int upper){
      return createEndPoint(rel, classifier, lower, upper, "");
    }

    static MEndPoint createEndPoint(MRelationship rel, MClassifier classifier, int lower, int upper, String name){
        MEndPoint ep = new MEndPoint()
        ep.setClassifier(classifier)
        ep.setLowerBound(lower)
        ep.setUpperBound(upper)
        ep.setName(name)
        rel.setEndPoint(ep)
        return ep;
    }

    static MEndPoint createEndPoint (MRelationship rel, MClassifier classifier, int lower, int upper, String name, boolean isDerived, boolean isDependency){
        MEndPoint endpoint = createEndPoint(rel, classifier, lower, upper, name)
        endpoint.setDerived(isDerived)
        endpoint.setDependency(isDependency)
        return endpoint;
    }

    static MConstraint createConstraint(MNamedElement context, String name, String condition, MContainer container){
        MConstraint c = new MConstraint()
        c.setContext(context)
        c.setName(name)
        c.setCondition(condition)
        c.setContainer(container)
        return c
    }
}
