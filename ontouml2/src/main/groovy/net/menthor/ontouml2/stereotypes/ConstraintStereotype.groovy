package net.menthor.ontouml2.stereotypes

enum ConstraintStereotype {

	UNSET("Unset"),
	INVARIANT("Invariant"),
	DERIVATION("Derivation"),
	TEMPORAL("Temporal"),
	HISTORICAL("Historical");

	final String name;
	static final Map map

	ConstraintStereotype(String name) {
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
