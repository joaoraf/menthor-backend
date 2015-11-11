package net.menthor.ontouml.values

enum ExistenceValue {

	UNSET("Unset"),
	PERMANENT("Permanent"),
	TRANSIENT("Transient"),
	ETERNAL("Eternal");

	final String name;
	static final Map map

    ExistenceValue(String name) {
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
