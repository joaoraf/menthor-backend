package net.menthor.ontouml2.value

enum MeasurementValue {

	UNSET("Unset"),
	INTEGER("Integer"),
	REAL("Real"),
	DECIMAL("Decimal"),
	STRING("String");

	final String name;
	static final Map map

    MeasurementValue(String name) {
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