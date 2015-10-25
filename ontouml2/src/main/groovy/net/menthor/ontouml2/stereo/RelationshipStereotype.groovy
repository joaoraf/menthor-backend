package net.menthor.ontouml2.stereo

enum RelationshipStereotype {

	UNSET("Unset"),
	COMPONENT_OF("ComponentOf"),
	MEMBER_OF("MemberOf"),
	SUB_COLLECTION_OF("SubCollectionOf"),
	SUB_QUANTITY_OF("SubQuantityOf"),
	QUA_PART_OF("QuaPartOf"),
	CONSTITUTION("Constitution"),
	CHARACTERIZATION("Characterization"),
	MEDIATION("Mediation"),
	MATERIAL("Material"),
	FORMAL("Formal"),
	DERIVATION("Derivation"),
	STRUCTURATION("Structuration"),
	PARTICIPATION("Participation"),
	SUB_EVENT_OF("SubEventOf"),
	CAUSATION("Causation"),
	TEMPORAL("Temporal"),
	INSTANCE_OF("InstanceOf");

    final String name;
    static final Map map

    RelationshipStereotype(String name) {
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
