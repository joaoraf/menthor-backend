package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Comment
import net.menthor.ontouml2.ContainedElement
import net.menthor.ontouml2.Container;

abstract class ContainedElementImpl extends ElementImpl implements ContainedElement {

    private Container holder
    private List<Comment> comments
}