package net.menthor.ontouml

import net.menthor.ontouml.stereotypes.ClassStereotype
import net.menthor.ontouml.stereotypes.ConstraintStereotype
import net.menthor.ontouml.stereotypes.DataTypeStereotype
import net.menthor.ontouml.stereotypes.PrimitiveStereotype
import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.mcore.traits.MContainer
import net.menthor.mcore.traits.MNamedElement
import net.menthor.mcore.traits.MType
import net.menthor.mcore.traits.MClassifier

class Factory {

    private Factory(){}

    static Package createModel(String name) {
        Model model = new Model()
        model.setName(name)
        return model
    }

    static Package createPackage(String name, MContainer container){
        Package p = new Package()
        p.setName(name)
        p.setContainer(container)
        return p
    }

    static Generalization createGeneralization (MClassifier specific, MClassifier general, MContainer container){
        Generalization g = new Generalization()
        g.setGeneral(general)
        g.setSpecific(specific)
        g.setContainer(container)
        return g;
    }

    static GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, MContainer container){
        GeneralizationSet gs = new GeneralizationSet()
        gs.setIsCovering(isCovering)
        gs.setIsDisjoint(isDisjoint)
        gs.setContainer(container)
        return gs
    }

    static GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, List generalizations, MContainer container) {
        GeneralizationSet gs = createGeneralizationSet(isCovering,isDisjoint,container);
        gs.setGeneralizations(generalizations)
        return gs;
    }

    static GeneralizationSet createPartition(List<MClassifier> specifics, MClassifier general, MContainer container){
        GeneralizationSet gs = createGeneralizationSet(true,true,container)
        specifics.each{ spec ->
            Generalization g = createGeneralization(spec, general, container)
            g.setGeneralizationSet(gs)
        }
        return gs;
    }

    static Relationship createRelationship(RelationshipStereotype stereotype, String name, MContainer container) {
        Relationship rel = new Relationship()
        rel.setStereotype(stereotype)
        rel.setDefaultEndPoints(2)
        rel.setName(name)
        rel.setContainer(container)
        return rel
    }

    static Relationship createRelationship(RelationshipStereotype stereotype, MClassifier source, MClassifier target, MContainer container){
        Relationship rel = createRelationship(stereotype,"",container)
        rel.setDefaultReflexivityValue()
        rel.setDefaultSymmetryValue()
        rel.setDefaultTransitivityValue()
        rel.setDefaultCyclicityValue()
        rel.getEndPoints().get(0).setClassifier(source)
        rel.getEndPoints().get(1).setClassifier(target)
        return rel
    }

    static Relationship createRelationship(RelationshipStereotype stereotype, MClassifier source, int srcLower, int srcUpper, String name,
        MClassifier target, int tgtLower, int tgtUpper, MContainer container) {
        Relationship rel = createRelationship(stereotype, source, target, container)
        rel.setName(name)
        rel.sourceEndPoint().setCardinalities(srcLower, srcUpper)
        rel.targetEndPoint().setCardinalities(tgtLower, tgtUpper)
        return rel
    }

    static Class createClass(ClassStereotype stereotype, String name, MContainer container){
        Class c = new Class()
        c.setName(name)
        c.setContainer(container)
        c.setStereotype(stereotype)
        if(c.isMixinClass()) {
            c.setIsAbstract(true)
        }
        return c
    }

    static DataType createDataType(DataTypeStereotype stereotype, String name, MContainer container){
        DataType dt = new DataType()
        dt.setName(name)
        dt.setContainer(container)
        dt.setStereotype(stereotype)
        return dt;
    }

    static Literal createLiteral(String text){
        Literal lit = new Literal()
        lit.setText(text)
        return lit
    }

    static List<Literal> createLiterals(Collection<String> textValues){
        List<Literal> result = []
        textValues.each{ v ->
            result.add(createLiteral(v))
        }
        return result;
    }

    static DataType createEnumeration(String name, Collection<String> textValues, MContainer container){
        DataType enu = createDataType(DataTypeStereotype.ENUMERATION,name,container);
        List<Literal> literals = createLiterals(textValues)
        enu.setLiterals(literals)
        return enu;
    }

    static Attribute createAttribute (MType owner, PrimitiveStereotype primitive, int lower, int upper){
        Attribute attribute = new Attribute()
        attribute.setStereotype(primitive)
        attribute.setOwner(owner)
        attribute.setLowerBound(lower)
        attribute.setUpperBound(upper)
        attribute.setName("")
        owner.setAttribute(attribute)
        return attribute
    }

    static Attribute createAttribute (MType owner, PrimitiveStereotype primitive, int lower, int upper, String name, boolean isDerived, boolean isDependency){
        Attribute attribute = createAttribute(owner, primitive, lower, upper)
        attribute.setName("")
        attribute.setDerived(isDerived)
        attribute.setDependency(isDependency)
        return attribute;
    }

    static Attribute createAttribute (MType owner, PrimitiveStereotype primitive){
        Attribute attribute = createAttribute(owner, primitive, 1, 1, "", false, false)
        return attribute
    }

    static EndPoint createEndPoint(Relationship rel, MClassifier classifier, int lower, int upper, String name){
        EndPoint ep = new EndPoint()
        ep.setClassifier(classifier)
        ep.setLowerBound(lower)
        ep.setUpperBound(upper)
        ep.setName(name)
        rel.setEndPoint(ep)
        return ep;
    }

    static EndPoint createEndPoint(Relationship rel, MClassifier classifier, int lower, int upper){
        return createEndPoint(rel, classifier, lower, upper, "");
    }

    static EndPoint createEndPoint (Relationship rel, MClassifier classifier, int lower, int upper, String name, boolean isDerived, boolean isDependency){
        EndPoint endpoint = createEndPoint(rel, classifier, lower, upper, name)
        endpoint.setDerived(isDerived)
        endpoint.setDependency(isDependency)
        return endpoint;
    }

    static Constraint createConstraint(MNamedElement context, ConstraintStereotype stereotype, String name, String condition, MContainer container){
        Constraint c =  Constraint()
        c.setContext(context)
        c.setName(name)
        c.setCondition(condition)
        c.setContainer(container)
        c.setStereotype(stereotype)
        return c
    }
}
