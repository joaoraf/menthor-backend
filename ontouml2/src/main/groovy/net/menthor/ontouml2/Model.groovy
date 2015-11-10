package net.menthor.ontouml2

import net.menthor.ontouml2.traits.Container
import org.codehaus.jackson.annotate.JsonTypeInfo

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
class Model extends Package {

}
