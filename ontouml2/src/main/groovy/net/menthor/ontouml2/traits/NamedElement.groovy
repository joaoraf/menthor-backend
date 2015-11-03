package net.menthor.ontouml2.traits;

/** An element of the metamodel which has a name */
trait NamedElement implements Element {

    String name
    String uniqueName
    List<String> definitions
    List<String> synonyms
    String text
}
