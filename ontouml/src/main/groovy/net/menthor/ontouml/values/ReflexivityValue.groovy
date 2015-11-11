package net.menthor.ontouml.values

enum ReflexivityValue {

	UNSET("Unset"),
	REFLEXIVE("Reflexive"),
	IRREFLEXIVE("Irreflexive"),
	NON_REFLEXIVE("NonReflexive");

	final String name;
	static final Map map

    ReflexivityValue(String name) {
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
