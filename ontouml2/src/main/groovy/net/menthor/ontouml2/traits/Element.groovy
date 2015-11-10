package net.menthor.ontouml2.traits

import net.menthor.ontouml2.Printer
import org.codehaus.jackson.annotate.JsonTypeInfo

/** The most general element in the metamodel */

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
trait Element {

    String toString(){
        return Printer.commonRepresentation(this)
    }
}
