package net.menthor.mcore

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import net.menthor.mcore.traits.MType

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
class MDataType implements MType {

    protected List<MLiteral> literals = []

    //=============================
    // Getters
    //=============================

    List<MLiteral> getLiterals() { return literals }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setLiteral(MLiteral literal){
        if(literal==null) return
        if(!literals.contains(literal)){
            literals.add(literal)
        }
        //Ensuring opposite end
        literal.setOwner(this)
    }

    void setLiterals(List<MLiteral> literals){
        if(literals==null || literals==[]){
            this.literals.clear()
            return
        }
        literals.each{ l ->
            setLiteral(l)
        }
    }


}