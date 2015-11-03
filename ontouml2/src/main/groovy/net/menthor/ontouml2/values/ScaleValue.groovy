package net.menthor.ontouml2.values

enum ScaleValue {

	UNSET("Unset"),
	INTERVAL("Interval"),
	RATIONAL("Rational"),
	ORDINAL("Ordinal"),
	NOMINAL("Nominal");

	final String name;
	static final Map map

    ScaleValue(String name) {
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
