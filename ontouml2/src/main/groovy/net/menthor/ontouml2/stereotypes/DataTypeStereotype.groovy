package net.menthor.ontouml2.stereotypes

enum DataTypeStereotype {

	UNSET( "Unset"),
	DOMAIN("Domain"),
	DIMENSION("Dimension"),
	ENUMERATION("Enumeration");

    final String name;
    static final Map map

    DataTypeStereotype(String name) {
        this.name = name;
    }

    @Override
    String toString() {
        return name;
    }

    static getEnum(name) {
        map[name]
    }

    static {
        map = [:] as TreeMap
        values().each{ e ->
            map.put(e.name, e)
        }
    }
}
