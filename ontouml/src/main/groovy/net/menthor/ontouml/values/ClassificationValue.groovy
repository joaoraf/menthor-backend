package net.menthor.ontouml.values;

enum ClassificationValue {

	UNSET("Unset"),
	INITIAL("Initial"),
	FINAL("Final");

	final String name;
	static final Map map

    ClassificationValue(String name) {
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