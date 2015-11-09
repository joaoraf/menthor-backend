package net.menthor.ontouml2.values

enum TransitivityValue {

	UNSET("Unset"),
	TRANSITIVE("Transitive"),
	INTRANSITIVE("Intransitive"),
	NON_TRANSITIVE("NonTransitive");

	final String name;
	static final Map map

    TransitivityValue(String name) {
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
