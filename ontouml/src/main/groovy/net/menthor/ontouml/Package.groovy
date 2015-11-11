package net.menthor.ontouml

import net.menthor.ontouml.traits.ContainedElement
import net.menthor.ontouml.traits.Container
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
class Package implements Container, ContainedElement {

}
