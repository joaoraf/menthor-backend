package net.menthor.ontouml

import net.menthor.mcore.MPrinter
import net.menthor.ontouml.stereotypes.ClassStereotype
import net.menthor.ontouml.stereotypes.ConstraintStereotype
import net.menthor.ontouml.stereotypes.DataTypeStereotype
import net.menthor.ontouml.stereotypes.PrimitiveStereotype
import net.menthor.ontouml.stereotypes.RelationshipStereotype
import net.menthor.mcore.traits.MClassifier
import net.menthor.mcore.traits.MElement
import net.menthor.mcore.traits.MNamedElement
import net.menthor.mcore.traits.MProperty

class Printer extends MPrinter {

    protected Printer() {}

    @Override
    static String print(MElement elem){
        if (elem instanceof Model) return stereotypeAndName(elem, false, false);
        if (elem instanceof Package) return stereotypeAndName(elem, false, false);
        if (elem instanceof Class || elem instanceof DataType)return stereotypeAndName(elem, true, false);
        if(elem instanceof Constraint) return stereotypeAndName(elem,true,false)
        if (elem instanceof Relationship) return print(elem as Relationship)
        if (elem instanceof Generalization) return print(elem as Generalization)
        if (elem instanceof GeneralizationSet) return print(elem as GeneralizationSet)
        if (elem instanceof Attribute) return print(elem as Attribute)
        if (elem instanceof EndPoint) return print(elem as EndPoint)
        if (elem instanceof Literal) return print(elem as Literal)
        if(elem instanceof Comment) return print(elem as Comment)
        return stereotypeAndName(elem, true, false);
    }

    static String print(Relationship a){
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

    static String print(Generalization g){
        def result = new String()
        result += stereotype(g)+" {"+name(g.getSpecific())+" -> "+name(g.getGeneral())+"}";
        return result;
    }

    static String print(GeneralizationSet genset){
        def result = new String()
        MClassifier general = genset.general();
        result += stereotypeAndName(genset, false, false) + " / "+name(general)+" { ";
        int i=1;
        for(MClassifier specific: genset.specifics()){
            if(i < genset.specifics().size())
                result += name(specific)+", ";
            else
                result += name(specific) + " } ";
            i++;
        }
        return result;
    }

    static String print(Attribute p){
        return stereotypeAndName(p,true,false)+ " ["+multiplicity(p,true,"..")+"]";
    }

    static String print(EndPoint p){
        return stereotype(p)+" ("+name(p)+") "+name(p.getClassifier()) +" ["+multiplicity(p,true,"..")+"]";
    }

    static String print(Literal p){
        return stereotype(p)+" ("+p.getText()+")";
    }

    static String print(Comment p){
        return stereotype(p);
    }

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

    static String stereotype(MElement elem){
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

    static boolean hasStereotype(MElement elem){
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

    static String stereotype(MElement elem, boolean addGuillemets) {
        if(hasStereotype(elem)) {
            //Changed to unicode because on mac this character appear like "?"
            if(addGuillemets) return "\u00AB"+stereotype(elem)+"\u00BB"
            return stereotype(elem)
        }else{
            return stereotype(elem)
        }
    }

    //======================================
    //Name + Stereotype
    //======================================

    static String stereotypeAndName(MElement elem, boolean addGuillemets, boolean addSingleQuotes) {
        String n = ""
        if(elem instanceof MNamedElement) n = " "+name(elem,addSingleQuotes,false)
        return stereotype(elem,addGuillemets)+n
    }

    static String nameAndStereotype(MElement elem, boolean addGuillemets, boolean addSingleQuotes){
        String n = ""
        if(elem instanceof MNamedElement) n = name(elem,addSingleQuotes,false)
        return n+" ("+stereotype(elem,addGuillemets)+")"
    }

    static String nameAndStereotype(MProperty p, boolean addTypeStereotype){
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

    static String nameAndStereotype(MProperty p){
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

    static String nameStereotypeAndMultiplicity(MProperty p, boolean quotePropertyName, boolean quoteTypeName, boolean alwaysShowLowerAndUpper, boolean addTypeStereotype, boolean guillemetTypeStereotype){
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
    //All details
    //======================================

    static String allDetails(Relationship a){
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
}
