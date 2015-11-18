package net.menthor.ontouml.names

class NameProcessor {

    private static Map<String, Counter > freqMap = [:]

    private static def reservedAlsKeys = [
        "World","abstract","all","and","as","assert","but","check","disj","else","exactly","extends","fact",
        "for","fun","iden","iff","implies","in","Int","let","lone","module","no","none","not","one","open",
        "or","pred","run","set","sig","some","sum","univ","int","Int", "Property", "Object", "Datatype"
     ] as String[]

    private static def invalidChars = [
        '[^\\p{ASCII}]',' ',',','!','@','#','\\$','%','\"','\'','&','\\*','-','=','\\+',';',':','-','<','>',
        '\\?','\\.','\\{','\\}','\\(','\\)','\\[','\\]','\\\\','/','\\|'
    ] as String []

    private static String removeInvalidChars(String name){
        def result = name
        invalidChars.each{ regex ->
            result = result.replaceAll(regex,"")
        }
        return result
    }

    private static def reservedOclKeys = [
        "and","body","context","def","derive","else","init","inv","invalid","let","not","null","endif","endpackage",
        "false","if","implies","in","or","package","post","static","true","then","xor","Bag","Boolean","Collection" ,
        "Integer","OclAny","OclInvalid","OclMessage","OclVoid","OrderedSet","Real","Sequence","Set","String","Tuple",
        "UnlimitedNatural"
    ] as String []

    private static def add(String name){
        Counter c = freqMap.get(name);
        if (c == null) {
            c = new Counter()
            freqMap.put(name, c)
        } else {
            c.increment();
        }
        return c.count()
    }

    static def remove(String name){
        Counter c = freqMap.get(name)
        if(c!=null){
            c.decrease()
            if(c.count()<=0) freqMap.remove(name)
        }
    }

    static def process(String name){
        name = removeInvalidChars(name)
        def count = add(name)
        return name+count
    }
}

