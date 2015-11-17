package net.menthor.ontouml.test

import net.menthor.ontouml.Checker
import net.menthor.ontouml.Model

class CheckerTest {

    static void main(String[] args){
        Model m = CarAccidentExample.generate()
        m.createMode("Mode1")

        def checker = new Checker()
        checker.execute(m).each{ error ->
            println error
        }
    }
}
