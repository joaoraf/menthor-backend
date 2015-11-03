package net.menthor.ontouml2.stereotypes

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
	SUB_KIND("SubKind"),
	CATEGORY("Category"),
	MIXIN("Mixin"),
	ROLE_MIXIN("RoleMixin"),
	PHASE_MIXIN("PhaseMixin"),
	EVENT("Event"),
	HIGH_ORDER("HighOrder");

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