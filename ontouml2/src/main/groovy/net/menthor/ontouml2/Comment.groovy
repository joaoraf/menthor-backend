package net.menthor.ontouml2

import net.menthor.ontouml2.traits.ContainedElement
import net.menthor.ontouml2.traits.Element

class Comment implements Element {

    String text
    ContainedElement owner
}
