package net.menthor.mcore.test

import net.menthor.mcore.MClass
import net.menthor.mcore.MFactory
import net.menthor.mcore.MPackage
import net.menthor.mcore.MRelationship
import net.menthor.mcore.MSerializer

class CarAccidentExample {

    static void main(String[] args) {
        MPackage m = generate()

        //save as JSON file
        MSerializer s = new MSerializer()
        s.toFormattedJSONFile(m,"mcore/src/test/groovy/net/menthor/mcore/test/","CarAccident")

        println "Car Accident generated"
    }

    static MPackage generate(){

        //create the model
        MPackage m = MFactory.createModel("Car Accident")

        //create kinds
        MClass person = m.createClass("Person")
        MClass vehicle = m.createClass("Vehicle")
        MClass roadway = m.createClass("Roadway")

        //create subkinds
        MClass man = m.createClass("Man")
        MClass woman = m.createClass("Woman")

        //create a partition between man and woman
        List genders = [man, woman]
        m.createPartition(genders, person)

        //create phases
        MClass living = m.createClass("Living")
        MClass deceased = m.createClass("Deceased")

        //create a partition between living and deceased
        List nature = [living, deceased]
        m.createPartition(nature, person)

        //create roles
        MClass traveler = m.createClass("Traveler")
        MClass victim = m.createClass("Victim")
        MClass crashedVehicle = m.createClass("CrashedVehicle")

        //create relators
        MClass accident = m.createClass("Traffic Accident")
        MClass rearEndCollision = m.createClass("Rear End Collision")
        MClass travel = m.createClass("Travel")

        // create generalizations/specializations
        m.createGeneralization(traveler, person)
        m.createGeneralization(victim, person)
        m.createGeneralization(crashedVehicle, vehicle)
        m.createGeneralization(rearEndCollision, accident)

        //create mediations
        m.createRelationship(accident, 1, -1, "occurs", roadway, 1, 1)
        m.createRelationship(accident, 1, 1, "has", victim, 1, -1)
        m.createRelationship(accident, 1, 1, "involves", crashedVehicle, 1, -1)
        m.createRelationship(travel, 1, 1, "has", traveler, 1, -1)
        m.createRelationship(travel, 1, 1, "made by", vehicle, 1, 1)

        //create material relationship
        MRelationship material = m.createRelationship(victim, 1, -1, "has been victim in", roadway, 1, 1)

        //create the derivation
        m.createRelationship(material, accident);

        //print
        m.getElements().each{ e -> println e }

        return m
    }
}
