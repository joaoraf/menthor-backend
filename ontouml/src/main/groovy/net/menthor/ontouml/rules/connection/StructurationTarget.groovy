package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.DataType
import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class StructurationTarget implements SyntacticalRule {

    StructurationTarget(Relationship self){
        this.errorMsg = 'The structurating type (target) of a Structuration must be a Domain, Dimension, or Enumeration'
        this.self = self
    }

    @Override
    boolean condition() {
        def rel = self as Relationship
        if(rel.isStructuration()){
            def tgtDataType = rel.targetDataType() as DataType
            return tgtDataType!=null && (tgtDataType.isStructure() || tgtDataType.isEnumeration())
        }
        return true
    }
}
