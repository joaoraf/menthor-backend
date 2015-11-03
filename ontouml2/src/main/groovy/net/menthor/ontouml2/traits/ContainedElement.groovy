package net.menthor.ontouml2.traits

import net.menthor.ontouml2.Comment
import net.menthor.ontouml2.Model

trait ContainedElement {

    Container holder
    List<Comment> comments

    /** The model */
    Model model(){
        return model(holder)
    }

    /** The model searching it from a given container */
    Model model(Container c){
        if(c instanceof Model) {
            return c
        }else{
            if(c instanceof ContainedElement) {
                return model(c.getHolder())
            }
        }
    }
}