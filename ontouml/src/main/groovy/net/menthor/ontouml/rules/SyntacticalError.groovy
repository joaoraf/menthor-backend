package net.menthor.ontouml.rules

import net.menthor.ontouml.traits.Element

class SyntacticalError {

    String message
    Element element

    SyntacticalError(Element element, String msg){
        this.message = msg
        this.element = element
    }

    String toString(){
        "Syntactical Error: "+element.toString() +" - "+message
    }
}
