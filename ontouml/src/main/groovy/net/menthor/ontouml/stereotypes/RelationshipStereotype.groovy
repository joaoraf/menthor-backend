package net.menthor.ontouml.stereotypes

enum RelationshipStereotype {

	UNSET("Unset"),
    COMPONENTOF("ComponentOf"),
    MEMBEROF("MemberOf"),
    SUBCOLLECTIONOF("SubCollectionOf"),
    SUBQUANTITYOF("SubQuantityOf"),
    QUAPARTOF("QuaPartOf"),
	CONSTITUTION("Constitution"),
	CHARACTERIZATION("Characterization"),
	MEDIATION("Mediation"),
	MATERIAL("Material"),
	FORMAL("Formal"),
	DERIVATION("Derivation"),
	STRUCTURATION("Structuration"),
	PARTICIPATION("Participation"),
    SUBEVENTOF("SubEventOf"),
	CAUSATION("Causation"),
	TEMPORAL("Temporal"),
    INSTANCEOF("InstanceOf");

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
