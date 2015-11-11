package net.menthor.ontouml

import net.menthor.ontouml.stereotypes.ClassStereotype
import net.menthor.ontouml.stereotypes.ConstraintStereotype
import net.menthor.ontouml.stereotypes.DataTypeStereotype
import net.menthor.ontouml.stereotypes.PrimitiveStereotype
import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.ontouml.traits.Container
import net.menthor.ontouml.traits.Type
import net.menthor.ontouml.traits.Classifier

class Factory {

    static java.lang.Package createPackage(String name, Container container){
        java.lang.Package p = new java.lang.Package()
        p.setName(name)
        p.setContainer(container)
        return p
    }

    static Model createModel(String name){
        Model p = new Model()
        p.setName(name)
        return p
    }

    static Generalization createGeneralization (Classifier specific, Classifier general, Container container){
        Generalization g = new Generalization()
        g.setGeneral(general)
        g.setSpecific(specific)
        g.setContainer(container)
        return g;
    }

    static GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, Container container){
        GeneralizationSet gs = new GeneralizationSet()
        gs.setIsCovering(isCovering)
        gs.setIsDisjoint(isDisjoint)
        gs.setContainer(container)
        return gs
    }

    static GeneralizationSet createGeneralizationSet(boolean isCovering, boolean isDisjoint, List generalizations, Container container) {
        GeneralizationSet gs = createGeneralizationSet(isCovering,isDisjoint,container);
        gs.setGeneralizations(generalizations)
        return gs;
    }

    static GeneralizationSet createPartition(List<Classifier> specifics, Classifier general, Container container){
        GeneralizationSet gs = createGeneralizationSet(true,true,container)
        specifics.each{ spec ->
            Generalization g = createGeneralization(spec, general, container)
            g.setGeneralizationSet(gs)
        }
        return gs;
    }

    static Relationship createRelationship(RelationshipStereotype stereotype, Classifier source, Classifier target, Container container){
        Relationship rel = new Relationship()
        rel.setStereotype(stereotype)
        rel.setName("")
        rel.setDefaultEndPoints(2)
        rel.getEndPoints().get(0).setClassifier(source)
        rel.getEndPoints().get(1).setClassifier(target)
        rel.setDefaultReflexivityValue()
        rel.setDefaultSymmetryValue()
        rel.setDefaultTransitivityValue()
        rel.setDefaultCyclicityValue()
        rel.setContainer(container)
        return rel;
    }

    static Relationship createRelationship(RelationshipStereotype stereotype, Classifier source, int srcLower, int srcUpper, String name,
        Classifier target, int tgtLower, int tgtUpper, Container container) {
        Relationship rel = createRelationship(stereotype, source, target, container)
        rel.setName(name)
        rel.sourceEndPoint().setCardinalities(srcLower, srcUpper)
        rel.targetEndPoint().setCardinalities(tgtLower, tgtUpper)
        return rel
    }

    static java.lang.Class createClass(ClassStereotype stereotype, String name, Container container){
        java.lang.Class c = new java.lang.Class()
        c.setStereotype(stereotype)
        if(c.isMixinClass()) {
            c.setIsAbstract(true)
        }
        c.setName(name)
        c.setContainer(container)
        return c;
    }

    static DataType createDataType(DataTypeStereotype stereotype, String name, Container container){
        DataType dt = new DataType()
        dt.setStereotype(stereotype)
        dt.setName(name)
        dt.setContainer(container)
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
        enu.setLiterals(literals)
        enu.setContainer(container)
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
        attribute.setUpperBound(upper)
        attribute.setName("")
        owner.setAttribute(attribute)
        return attribute
    }

    static Attribute createAttribute (Type owner, PrimitiveStereotype primitive, int lower, int upper, String name, boolean isDerived, boolean isDependency){
        Attribute attribute = createAttribute(owner, primitive, lower, upper)
        attribute.setName("")
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
        ep.setName(name)
        rel.setEndPoint(ep)
        return ep;
    }

    static Constraint createConstraint(String context, ConstraintStereotype stereotype, String identifier, String expression, Container container){
        Constraint c = new Constraint()
        c.setStereotype(stereotype)
        c.setContext(context)
        c.setIdentifier(identifier)
        c.setExpression(expression)
        c.setContainer(container)
        return c
    }
}
