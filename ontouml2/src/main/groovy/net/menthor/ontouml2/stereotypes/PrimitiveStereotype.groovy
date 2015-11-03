package net.menthor.ontouml2.stereotypes

enum PrimitiveStereotype {

	UNSET("Unset"),
	BOOLEAN("Boolean"),
	STRING("String"),
	REAL("Real"),
	INTEGER("Integer"),
	DATE("Date"),
	DATE_TIME("DateTime");

	final String name;
	static final Map map

	PrimitiveStereotype(String name) {
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
