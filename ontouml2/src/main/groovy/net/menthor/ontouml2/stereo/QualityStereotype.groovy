package net.menthor.ontouml2.stereo

enum QualityStereotype {

	UNSET("Unset"),
	NOMINAL("Nominal"),
	PERCEIVABLE("Perceivable"),
	NON_PERCEIVABLE("NonPerceivable");

	final String name;
	static final Map map

    QualityStereotype(String name) {
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
