package net.menthor.mcore

import net.menthor.mcore.traits.MContainedElement
import net.menthor.mcore.traits.MElement
import net.menthor.mcore.traits.MNamedElement
import net.menthor.mcore.traits.MProperty

class MPrinter implements MElement {

    protected MPrinter() {}

    static String print(MElement elem){
        if(elem instanceof MGeneralization){
            MGeneralization gen = (elem as MGeneralization)
            return type(elem)+ " {"+gen.getSpecific().getName()+" -> "+gen.getGeneral().getName()+"}"
        }
        if(elem instanceof MRelationship){
            MRelationship rel = elem as MRelationship
            return type(elem)+ " ("+name(elem)+") {"+rel.source().getName()+" -> "+rel.target().getName()+"}"
        }
        return type(elem)+" "+name(elem)
    }

    //======================================
    //Type
    //======================================

    static String type(MElement elem){
        return elem.getClass().getName().replace("net.menthor.mcore.","")
    }

    //======================================
    //Name
    //======================================

    static String name(MElement elem){
        if(elem==null) return "null"
        if(elem instanceof MNamedElement){
            String name = (elem as MNamedElement).getName()
            if(name == null) return ""
            return name
        }
        return " nameless"
    }

    static String name(MElement elem, boolean addSingleQuote, boolean addLowerUpper){
        if(addSingleQuote) return "'"+name(elem)+"'"
        if(addLowerUpper) return "<"+name(elem)+">"
        return name(elem)
    }

    //======================================
    //Multiplicity
    //======================================

    static String multiplicity(MProperty p, boolean alwaysShowLowerAndUpper, String separator){
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
    //Path
    //======================================

    static String path(MContainedElement c){
        if(c == null) return ""
        if (c.getContainer()==null) return ""
        else{
            String containerName = ""
            if(c.getContainer() instanceof MNamedElement) containerName = ((MNamedElement) c.getContainer()).getName()
            else containerName = "unnamed"
            if(!c.getContainer() instanceof MContainedElement) return containerName
            return path(c.getContainer())+"::"+containerName
        }
    }
}
