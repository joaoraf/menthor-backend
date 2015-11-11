package net.menthor.ontouml

import net.menthor.ontouml.stereotypes.ClassStereotype
import net.menthor.ontouml.stereotypes.ConstraintStereotype
import net.menthor.ontouml.stereotypes.DataTypeStereotype
import net.menthor.ontouml.stereotypes.PrimitiveStereotype
import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.ontouml.traits.Classifier
import net.menthor.ontouml.traits.ContainedElement
import net.menthor.ontouml.traits.Element
import net.menthor.ontouml.traits.NamedElement
import net.menthor.ontouml.traits.Property

class Printer {

    //======================================
    //Stereotype
    //======================================

    static String stereotype(Class c){
        if(c.getStereotype()!=null && c.getStereotype()!=ClassStereotype.UNSET) {
            return c.getStereotype().getName()
        } else return "Class"
    }

    static String stereotype(Relationship r){
        if(r.getStereotype()!=null && r.getStereotype()!=RelationshipStereotype.UNSET) {
            return r.getStereotype().getName()
        } else return "Relationship"
    }

    static String stereotype(DataType d){
        if(d.getStereotype()!=null && d.getStereotype()!=DataTypeStereotype.UNSET) {
            return d.getStereotype().getName()
        } else return "DataType"
    }

    static String stereotype(Attribute a){
        if(a.getStereotype()!=null && a.getStereotype()!=PrimitiveStereotype.UNSET) {
            return a.getStereotype().getName()
        } else return "Attribute"
    }

    static String stereotype(Constraint c){
        if(c.getStereotype()!=null && c.getStereotype()!= ConstraintStereotype.UNSET) {
            return c.getStereotype().getName()
        } else return "Constraint"
    }

    static String stereotype(Element elem){
        if(elem==null) return "Null"
        if(elem instanceof Class) return stereotype(elem as Class)
        if(elem instanceof Relationship) return stereotype(elem as Relationship)
        if(elem instanceof DataType) return stereotype(elem as DataType)
        if(elem instanceof Attribute) return stereotype(elem as Attribute)
        if(elem instanceof Constraint) return stereotype(elem as Constraint)
        if(elem instanceof EndPoint) return "EndPoint"
        if(elem instanceof Model) return "Model"
        if(elem instanceof Package) return "Package"
        if(elem instanceof GeneralizationSet) return "GeneralizationSet"
        if(elem instanceof Generalization) return "Generalization"
        if(elem instanceof Comment) return "Comment"
        if(elem instanceof Literal) return "Literal"
        return "UnknownType"
    }

    static boolean hasStereotype(Element elem){
        boolean hasStereotypeDefined=false
        if(elem instanceof Class && (elem as Class).getStereotype()!=null && (elem as Class).getStereotype()!=ClassStereotype.UNSET){
            hasStereotypeDefined=true
        } else if(elem instanceof Relationship && (elem as Relationship).getStereotype()!=null && (elem as Relationship).getStereotype()!=RelationshipStereotype.UNSET){
            hasStereotypeDefined=true
        } else if(elem instanceof DataType && (elem as DataType).getStereotype()!=null && (elem as DataType).getStereotype()!=DataTypeStereotype.UNSET){
            hasStereotypeDefined=true
        } else if(elem instanceof Constraint && (elem as Constraint).getStereotype()!=null && (elem as Constraint).getStereotype()!=ConstraintStereotype.UNSET){
            hasStereotypeDefined=true
        } else if(elem instanceof Attribute && (elem as Attribute).getStereotype()!=null && (elem as Attribute).getStereotype()!=PrimitiveStereotype.UNSET){
            hasStereotypeDefined=true
        }
        return hasStereotypeDefined
    }

    static String stereotype(Element elem, boolean addGuillemets) {
        if(hasStereotype(elem)) {
            //Changed to unicode because on mac this character appear like "?"
            if(addGuillemets) return "\u00AB"+stereotype(elem)+"\u00BB"
            return stereotype(elem)
        }else{
            return stereotype(elem)
        }
    }

    //======================================
    //Name
    //======================================

    static String name(Element elem){
        if(elem==null) return "null"
        if(elem instanceof NamedElement){
            String name = (elem as NamedElement).getName()
            if(name == null) return ""
            return name
        }
        return "nameless"
    }

    static String name(Element elem, boolean addSingleQuote, boolean addLowerUpper){
        if(addSingleQuote) return "'"+name(elem)+"'"
        if(addLowerUpper) return "<"+name(elem)+">"
        return name(elem)
    }

    //======================================
    //Multiplicity
    //======================================

    static String multiplicity(Property p, boolean alwaysShowLowerAndUpper, String separator){
        if(p==null) return "null"
        Integer lower = p.getLowerBound()
        Integer upper = p.getUpperBound()
        return multiplicity(lower, upper, alwaysShowLowerAndUpper, separator)
    }

    static String multiplicity(Integer lower, Integer upper, boolean alwaysShowLowerAndUpper, String separator) {
        String lowerString = lower.toString()
        String upperString = upper.toString()
        if (lower == -1) lowerString = "*"
        if (upper == -1) upperString = "*"
        if(!alwaysShowLowerAndUpper && lower==upper) return lowerString
        return lowerString+separator+upperString
    }

    //======================================
    //Name + Stereotype
    //======================================

    static String stereotypeAndName(Element elem, boolean addGuillemets, boolean addSingleQuotes) {
        String n = ""
        if(elem instanceof NamedElement) n = " "+name(elem,addSingleQuotes,false)
        return stereotype(elem,addGuillemets)+n
    }

    static String nameAndStereotype(Element elem, boolean addGuillemets, boolean addSingleQuotes){
        String n = ""
        if(elem instanceof NamedElement) n = name(elem,addSingleQuotes,false)
        return n+" ("+stereotype(elem,addGuillemets)+")"
    }

    static String nameAndStereotype(Property p, boolean addTypeStereotype){
        if(p instanceof Attribute){
            if(addTypeStereotype) return name(p, true, false)+" ("+stereotypeAndName(p, true, false)+")"
            else return name(p, true, false)+" (PrimitiveType)"
        }
        if(p instanceof EndPoint){
            if(addTypeStereotype) return name(p, true, false)+" ("+stereotypeAndName(((EndPoint)p).getClassifier(), true, false)+")"
            else return name(p, true, false)+" ("+name(((EndPoint)p).getClassifier())+")"
        }
        return name(p, true, false)+" (typeless)"
    }

    static String nameAndStereotype(Property p){
        if(p instanceof Attribute){
            return name(p, true, false)+" ("+stereotype(p)+")"
        }
        if(p instanceof EndPoint){
            return name(p, true, false)+" ("+name(((EndPoint)p).getClassifier())+")"
        }
        return name(p, true, false)+" (typeless)"

    }

    //======================================
    //Name + Stereotype + Multiplicity
    //======================================

    static String nameStereotypeAndMultiplicity(Property p, boolean quotePropertyName, boolean quoteTypeName, boolean alwaysShowLowerAndUpper, boolean addTypeStereotype, boolean guillemetTypeStereotype){
        if(p instanceof EndPoint){
            String typeDesc = new String()
            if(addTypeStereotype) typeDesc = stereotypeAndName(((EndPoint)p).getClassifier(), guillemetTypeStereotype, quoteTypeName)
            else typeDesc = name(((EndPoint)p).getClassifier(),quoteTypeName,false)
            return name(p, quotePropertyName, false)+" ["+multiplicity(p, alwaysShowLowerAndUpper, "..")+"] ("+typeDesc+")"
        }
        if(p instanceof Attribute){
            String typeDesc = new String();
            if(addTypeStereotype) typeDesc = stereotypeAndName(((Attribute)p), guillemetTypeStereotype, quoteTypeName)
            else typeDesc = name(((Attribute)p),quoteTypeName,false)
            return name(p, quotePropertyName, false)+" ["+multiplicity(p, alwaysShowLowerAndUpper, "..")+"] ("+typeDesc+")"
        }
        return name(p, quotePropertyName, false)+" ["+multiplicity(p, alwaysShowLowerAndUpper, "..")+"] (typeless)"
    }

    //======================================
    //Path
    //======================================

    static String path(ContainedElement c){
        if(c == null) return ""
        if (c.getContainer()==null) return ""
        else{
            String containerName = ""
            if(c.getContainer() instanceof NamedElement) containerName = ((NamedElement) c.getContainer()).getName()
            else containerName = "unnamed"
            if(!c.getContainer() instanceof ContainedElement) return containerName
            return path(c.getContainer())+"::"+containerName
        }
    }

    //======================================
    //All details
    //======================================

    static String AllDetails(Relationship a){
        String result = stereotypeAndName(a,true, true)+" {"
        int i=1
        for(EndPoint ep: a.getEndPoints()){
            if(i<a.getEndPoints().size())
                result += name(ep.getClassifier(),true,false)+"("+multiplicity(ep, true, "..")+")"+" -> "
            else
                result += name(ep.getClassifier(),true,false)+"("+multiplicity(ep, true, "..")+")"+"}"
            i++
        }
        return result
    }

    //======================================
    //Common String Representation
    //======================================

    static String commonRepresentation(Relationship a){
        String result = stereotypeAndName(a,true, false)+" {"
        int i=1
        for(EndPoint ep: a.getEndPoints()){
            if(i<a.getEndPoints().size())
                result += name(ep.getClassifier())+" -> "
            else
                result += name(ep.getClassifier())+"}"
            i++;
        }
        return result;
    }

    static String commonRepresentation(Generalization g){
        def result = new String()
        result += stereotype(g)+" {"+name(g.getSpecific())+" -> "+name(g.getGeneral())+"}";
        return result;
    }

    static String commonRepresentation(GeneralizationSet genset){
        def result = new String()
        Classifier general = genset.general();
        result += stereotypeAndName(genset, false, false) + " / "+name(general)+" { ";
        int i=1;
        for(Classifier specific: genset.specifics()){
            if(i < genset.specifics().size())
                result += name(specific)+", ";
            else
                result += name(specific) + " } ";
            i++;
        }
        return result;
    }

    static String commonRepresentation(Attribute p){
        return stereotypeAndName(p,true,false)+ " ["+multiplicity(p,true,"..")+"]";
    }

    static String commonRepresentation(EndPoint p){
        return stereotype(p)+" ("+name(p)+") "+name(p.getClassifier()) +" ["+multiplicity(p,true,"..")+"]";
    }

    static String commonRepresentation(Literal p){
        return stereotype(p)+" ("+p.getText()+")";
    }

    static String commonRepresentation(Comment p){
        return stereotype(p);
    }

    static String commonRepresentation(Element elem){
        if (elem instanceof Model) return stereotypeAndName(elem, false, false);
        if (elem instanceof Package) return stereotypeAndName(elem, false, false);
        if (elem instanceof Class || elem instanceof DataType)return stereotypeAndName(elem, true, false);
        if(elem instanceof Constraint) return stereotypeAndName(elem,true,false)
        if (elem instanceof Relationship) return commonRepresentation(elem as Relationship)
        if (elem instanceof Generalization) return commonRepresentation(elem as Generalization)
        if (elem instanceof GeneralizationSet) return commonRepresentation(elem as GeneralizationSet)
        if (elem instanceof Attribute) return commonRepresentation(elem as Attribute)
        if (elem instanceof EndPoint) return commonRepresentation(elem as EndPoint)
        if (elem instanceof Literal) return commonRepresentation(elem as Literal)
        if(elem instanceof Comment) return commonRepresentation(elem as Comment)
        return stereotypeAndName(elem, true, false);
    }
}
