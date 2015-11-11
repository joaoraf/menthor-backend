package net.menthor.ontouml.stereotypes

enum ParticipationStereotype {

	UNSET("Unset"),
	CREATION("Creation"),
	CHANGE("Change"),
	DESTRUCTION("Destruction");

	final String name;
	static final Map map

    ParticipationStereotype(String name) {
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
