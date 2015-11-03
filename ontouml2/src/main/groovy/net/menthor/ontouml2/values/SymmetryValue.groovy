package net.menthor.ontouml2.values

enum SymmetryValue {

	UNSET("Unset"),
	SYMMETRIC("Symmetric"),
	ASSYMETRIC("Assymetric"),
	ANTI_SYMMETRIC("AntiSymmetric"),
	NON_SYMMETRIC("NonSymmetric");

	final String name;
	static final Map map

    SymmetryValue(String name) {
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
