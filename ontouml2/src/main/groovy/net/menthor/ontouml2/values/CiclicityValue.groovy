package net.menthor.ontouml2.values;

enum CiclicityValue {

	UNSET("Unset"),
	CYCLIC("Cyclic"),
	ACYCLIC("Acyclic"),
	NON_CYCLIC("NonCyclic");

	final String name;
    static final Map map

    CiclicityValue(String name) {
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