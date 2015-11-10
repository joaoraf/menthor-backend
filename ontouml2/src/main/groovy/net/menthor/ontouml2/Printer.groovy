package net.menthor.ontouml2

import net.menthor.ontouml2.stereotypes.ClassStereotype
import net.menthor.ontouml2.stereotypes.ConstraintStereotype
import net.menthor.ontouml2.stereotypes.DataTypeStereotype
import net.menthor.ontouml2.stereotypes.PrimitiveStereotype
import net.menthor.ontouml2.stereotypes.RelationshipStereotype
import net.menthor.ontouml2.traits.Classifier
import net.menthor.ontouml2.traits.ContainedElement
import net.menthor.ontouml2.traits.Element
import net.menthor.ontouml2.traits.NamedElement
import net.menthor.ontouml2.traits.Property

class Printer {

    static String stereotype(Element elem){
        if(elem==null) return "Null";
        if(elem instanceof Class){
            Class c = elem as Class
            if(c.getStereotype()!=null && c.getStereotype()!=ClassStereotype.UNSET) {
                return c.getStereotype().getName();
            } else return "Class";
        }
        if(elem instanceof Relationship){
            Relationship r = elem as Relationship;
            if(r.getStereotype()!=null && r.getStereotype()!=RelationshipStereotype.UNSET) {
                return r.getStereotype().getName();
            } else return "Relationship";
        }
        if(elem instanceof DataType){
            DataType d = (DataType)elem;
            if(d.getStereotype()!=null && d.getStereotype()!=DataTypeStereotype.UNSET) {
                return d.getStereotype().getName();
            } else return "DataType";
        }
        if(elem instanceof Attribute){
            Attribute a = elem as Attribute;
            if(a.getStereotype()!=null && a.getStereotype()!=PrimitiveStereotype.UNSET) {
                return a.getStereotype().getName();
            } else return "Attribute";
        }
        if(elem instanceof Constraint) {
            Constraint c = elem Constraint;
            if(c.getStereotype()!=null && c.getStereotype()!= ConstraintStereotype.UNSET) {
                return c.getStereotype().getName();
            } else return "Constraint";
        }
        if(elem instanceof EndPoint) return "EndPoint";
        if(elem instanceof Model) return "Model";
        if(elem instanceof Package) return "Package";
        if(elem instanceof GeneralizationSet) return "GeneralizationSet";
        if(elem instanceof Generalization) return "Generalization";
        if(elem instanceof Comment) return "Comment";
        if(elem instanceof Literal) return "Literal";
        return "UnknownType";
    }

    static String stereotype(Element elem, boolean addGuillemets) {
        boolean hasStereotypeDefined=false;
        if(elem instanceof Class && (elem as Class).getStereotype()!=null && (elem as Class).getStereotype()!=ClassStereotype.UNSET){
            hasStereotypeDefined=true;
        }
        else if(elem instanceof Relationship && (elem as Relationship).getStereotype()!=null && (elem as Relationship).getStereotype()!=RelationshipStereotype.UNSET){
            hasStereotypeDefined=true;
        }
        else if(elem instanceof DataType && (elem as DataType).getStereotype()!=null && (elem as DataType).getStereotype()!=DataTypeStereotype.UNSET){
            hasStereotypeDefined=true;
        }
        else if(elem instanceof Constraint && (elem as Constraint).getStereotype()!=null && (elem as Constraint).getStereotype()!=ConstraintStereotype.UNSET){
            hasStereotypeDefined=true;
        }
        else if(elem instanceof Attribute && (elem as Attribute).getStereotype()!=null && (elem as Attribute).getStereotype()!=PrimitiveStereotype.UNSET){
            hasStereotypeDefined=true;
        }
        if(hasStereotypeDefined) {
            //Changed to unicode because on mac this character appear like "?"
            if(addGuillemets) return "\u00AB"+stereotype(elem)+"\u00BB";
            return stereotype(elem);
        }else{
            return stereotype(elem);
        }
    }

    static String name(Element elem){
        if(elem==null) return "null";
        if(elem instanceof NamedElement){
            String name = (elem as NamedElement).getName();
            if(name == null) return "";
            return name;
        }
        return "nameless";
    }

    static String name(Element elem, boolean addSingleQuote, boolean addLowerUpper){
        if(addSingleQuote) return "'"+name(elem)+"'";
        if(addLowerUpper) return "<"+name(elem)+">";
        return name(elem);
    }

    static String stereotypeAndName(Element elem, boolean addGuillemets, boolean addSingleQuotes) {
        String n = "";
        if(elem instanceof NamedElement) n = " "+name(elem,addSingleQuotes,false);
        return stereotype(elem,addGuillemets)+n;
    }

    static String nameAndStereotype(Element elem, boolean addGuillemets, boolean addSingleQuotes){
        String n = "";
        if(elem instanceof NamedElement) n = name(elem,addSingleQuotes,false);
        return n+" ("+stereotype(elem,addGuillemets)+")";
    }

    static String multiplicity(Property p, boolean alwaysShowLowerAndUpper, String separator){
        if(p==null) return "null";
        Integer lower = p.getLowerBound();
        Integer upper = p.getUpperBound();
        return multiplicity(lower, upper, alwaysShowLowerAndUpper, separator);
    }

    static String multiplicity(Integer lower, Integer upper, boolean alwaysShowLowerAndUpper, String separator) {
        String lowerString = lower.toString();
        String upperString = upper.toString();
        if (lower == -1) lowerString = "*";
        if (upper == -1) upperString = "*";
        if(!alwaysShowLowerAndUpper && lower==upper) return lowerString;
        return lowerString+separator+upperString;
    }

    static String nameAndType(Property p){
        if(p instanceof Attribute){
            return name(p, true, false)+" ("+stereotype(p)+")";
        }
        if(p instanceof EndPoint){
            return name(p, true, false)+" ("+name(((EndPoint)p).getClassifier())+")";
        }
        return name(p, true, false)+" (typeless)";

    }

    static String nameAndStereotype(Property p, boolean addTypeStereotype){
        if(p instanceof Attribute){
            if(addTypeStereotype) return name(p, true, false)+" ("+stereotypeAndName(p, true, false)+")";
            else return name(p, true, false)+" (PrimitiveType)";
        }
        if(p instanceof EndPoint){
            if(addTypeStereotype) return name(p, true, false)+" ("+stereotypeAndName(((EndPoint)p).getClassifier(), true, false)+")";
            else return name(p, true, false)+" ("+name(((EndPoint)p).getClassifier())+")";
        }
        return name(p, true, false)+" (typeless)";
    }

    static String nameStereotypeAndMultiplicity(Property p, boolean quotePropertyName, boolean quoteTypeName, boolean alwaysShowLowerAndUpper, boolean addTypeStereotype, boolean guillemetTypeStereotype){
        if(p instanceof EndPoint){
            String typeDesc = new String();
            if(addTypeStereotype) typeDesc = stereotypeAndName(((EndPoint)p).getClassifier(), guillemetTypeStereotype, quoteTypeName);
            else typeDesc = name(((EndPoint)p).getClassifier(),quoteTypeName,false);
            return name(p, quotePropertyName, false)+" ["+multiplicity(p, alwaysShowLowerAndUpper, "..")+"] ("+typeDesc+")";
        }
        if(p instanceof Attribute){
            String typeDesc = new String();
            if(addTypeStereotype) typeDesc = stereotypeAndName(((Attribute)p), guillemetTypeStereotype, quoteTypeName);
            else typeDesc = name(((Attribute)p),quoteTypeName,false);
            return name(p, quotePropertyName, false)+" ["+multiplicity(p, alwaysShowLowerAndUpper, "..")+"] ("+typeDesc+")";
        }
        return name(p, quotePropertyName, false)+" ["+multiplicity(p, alwaysShowLowerAndUpper, "..")+"] (typeless)";
    }

    static String path(ContainedElement c){
        if(c == null) return "";
        if (c.getContainer()==null) return "";
        else{
            String containerName = "";
            if(c.getContainer() instanceof NamedElement) containerName = ((NamedElement) c.getContainer()).getName();
            else containerName = "unnamed";
            if(!c.getContainer() instanceof ContainedElement) return containerName;
            return path(c.getContainer())+"::"+containerName;
        }
    }

    static String AllDetails(Relationship a){
        String result = stereotypeAndName(a,true, true)+" {";
        int i=1;
        for(EndPoint ep: a.getEndPoints()){
            if(i<a.getEndPoints().size())
                result += name(ep.getClassifier(),true,false)+"("+multiplicity(ep, true, "..")+")"+" -> ";
            else
                result += name(ep.getClassifier(),true,false)+"("+multiplicity(ep, true, "..")+")"+"}";
            i++;
        }
        return result;
    }

    static String commonRepresentation(Element elem){
        if (elem instanceof Model) return stereotypeAndName(elem, false, false);
        if (elem instanceof Package) return stereotypeAndName(elem, false, false);
        if (elem instanceof Class || elem instanceof DataType)return stereotypeAndName(elem, true, false);
        if (elem instanceof Relationship){
            Relationship a = elem as Relationship;
            String result = stereotypeAndName(elem,true, false)+" {";
            int i=1;
            for(EndPoint ep: a.getEndPoints()){
                if(i<a.getEndPoints().size())
                    result += name(ep.getClassifier())+" -> ";
                else
                    result += name(ep.getClassifier())+"}";
                i++;
            }
            return result;
        }
        if (elem instanceof Generalization){
            String result = new String();
            Generalization g = elem as Generalization;
            result += stereotype(elem)+" {"+name(g.getSpecific())+" -> "+name(g.getGeneral())+"}";
            return result;
        }
        if (elem instanceof GeneralizationSet) {
            String result = new String();
            GeneralizationSet genset = elem as GeneralizationSet;
            Classifier general = genset.general();
            result += stereotypeAndName(elem, false, false) + " / "+name(general)+" { ";
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
        if (elem instanceof Attribute){
            Attribute p = elem as Attribute;
            return stereotypeAndName(p,true,false)+ " ["+multiplicity(p,true,"..")+"]";
        }
        if (elem instanceof EndPoint){
            EndPoint p = elem as EndPoint;
            return stereotype(p)+" ("+name(p)+") "+name(p.getClassifier()) +" ["+multiplicity(p,true,"..")+"]";
        }
        if (elem instanceof Literal){
            Literal p = elem as Literal;
            return stereotype(p)+" ("+p.getText()+")";
        }
        if(elem instanceof Comment){
            Comment p = elem as Comment;
            return stereotype(p);
        }
        if(elem instanceof Constraint){
            Constraint p = elem as Constraint;
            return stereotypeAndName(p,true,false);
        }
        return stereotypeAndName(elem, true, false);
    }
}
