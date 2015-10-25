package net.menthor.ontouml2.value

enum TransitivityValue {

	UNSET(0, "Unset", "Unset"),
	TRANSITIVE(0, "Transitive", "Transitive"),
	INTRANSITIVE(0, "Intransitive", "Intransitive"),
	NON_TRANSITIVE(0, "NonTransitive", "NonTransitive");

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
