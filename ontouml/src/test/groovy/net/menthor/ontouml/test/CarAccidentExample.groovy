package net.menthor.ontouml.test

import net.menthor.ontouml.Class
import net.menthor.ontouml.Factory
import net.menthor.ontouml.Model
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.Serializer

class CarAccidentExample {

    static void main(String[] args) {
        Model m = generate()

        //save as JSON file
        Serializer s = new Serializer()
        s.saveFormattedJSON(m,"ontouml/src/test/groovy/net/menthor/ontouml/test/","CarAccident")

        println "Car Accident generated"
    }

    static Model generate(){

        //create the model
        Model m = Factory.createModel("Car Accident")

        //create kinds
        Class person = m.createKind("Person")
        Class vehicle = m.createKind("Vehicle")
        Class roadway = m.createKind("Roadway")

        //create subkinds
        Class man = m.createSubKind("Man")
        Class woman = m.createSubKind("Woman")

        //create a partition between man and woman
        List genders = [man, woman]
        m.createPartition(genders, person)

        //create phases
        Class living = m.createPhase("Living")
        Class deceased = m.createPhase("Deceased")

        //create a partition between living and deceased
        List nature = [living, deceased]
        m.createPartition(nature, person)

        //create roles
        Class traveler = m.createRole("Traveler")
        Class victim = m.createRole("Victim")
        Class crashedVehicle = m.createRole("CrashedVehicle")

        //create relators
        Class accident = m.createRelator("Traffic Accident")
        Class rearEndCollision = m.createRelator("Rear End Collision")
        Class travel = m.createRelator("Travel")

        // create generalizations/specializations
        m.createGeneralization(traveler, person)
        m.createGeneralization(victim, person)
        m.createGeneralization(crashedVehicle, vehicle)
        m.createGeneralization(rearEndCollision, accident)

        //create mediations
        m.createMediation(accident, 1, -1, "occurs", roadway, 1, 1)
        m.createMediation(accident, 1, 1, "has", victim, 1, -1)
        m.createMediation(accident, 1, 1, "involves", crashedVehicle, 1, -1)
        m.createMediation(travel, 1, 1, "has", traveler, 1, -1)
        m.createMediation(travel, 1, 1, "made by", vehicle, 1, 1)

        //create material relationship
        Relationship material = m.createMaterial(victim, 1, -1, "has been victim in", roadway, 1, 1)

        //create the derivation
        m.createDerivation(material, accident);

        //print
        m.getElements().each{ e -> println e }

        return m
    }
}
