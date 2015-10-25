package net.menthor.ontouml2.impl

import net.menthor.ontouml2.Comment
import net.menthor.ontouml2.ContainedElement;

class CommentImpl extends ElementImpl implements Comment {

    String text
    ContainedElement owner
}
