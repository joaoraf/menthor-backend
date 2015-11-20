package net.menthor.ontouml

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.MGeneralizationSet

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class GeneralizationSet extends MGeneralizationSet {

    protected Class highorder

    //=============================
    //Getters
    //=============================

    Class getHighorder() { return highorder }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setHighorder(Class highorder){
        this.highorder = highorder
        if(highorder==null) return
        //Ensuring opposite end
        highorder.generalizationSet = this
    }

    String toString() { Printer.print(this) }
}
