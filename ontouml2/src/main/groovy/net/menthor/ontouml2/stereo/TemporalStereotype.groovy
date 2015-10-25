package net.menthor.ontouml2.stereo

enum TemporalStereotype {

	UNSET("Unset"),
	STARTS("Starts"),
	PRECEDES("Precedes"),
	EQUALS("Equals"),
	MEETS("Meets"),
	FINISHES("Finishes"),
	OVERLAPS("Overlaps"),
	DURING("During");

	final String name;
	static final Map map

    TemporalStereotype(String name) {
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
