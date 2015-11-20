package net.menthor.mcore.util

class NameProcessor {

    private static Map<String, Counter > freqMap = [:]

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
        if(count==1) return name
        else return name+count
    }
}

