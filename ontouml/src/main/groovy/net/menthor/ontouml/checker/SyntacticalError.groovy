package net.menthor.ontouml.checker

import net.menthor.mcore.traits.MElement

class SyntacticalError {

    String message
    MElement element

    SyntacticalError(MElement element, String msg){
        this.message = msg
        this.element = element
    }

    String toString(){
        "Syntactical Error: "+element.toString() +" - "+message
    }
}
