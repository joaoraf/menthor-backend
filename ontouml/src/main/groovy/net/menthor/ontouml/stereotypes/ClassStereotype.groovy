package net.menthor.ontouml.stereotypes

enum ClassStereotype {

	UNSET("Unset"),
	KIND("Kind"),
	COLLECTIVE("Collective"),
	QUANTITY("Quantity"),
	RELATOR("Relator"),
	MODE("Mode"),
	QUALITY("Quality"),
	ROLE("Role"),
	PHASE("Phase"),
	SUBKIND("SubKind"),
	CATEGORY("Category"),
	MIXIN("Mixin"),
	ROLEMIXIN("RoleMixin"),
	PHASEMIXIN("PhaseMixin"),
	EVENT("Event"),
	HIGHORDER("HighOrder");

	final String name;
	static final Map map

	ClassStereotype(String name) {
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