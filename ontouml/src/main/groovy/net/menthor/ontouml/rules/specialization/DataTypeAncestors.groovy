package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.DataType
import net.menthor.ontouml.rules.GenericCondition
import net.menthor.ontouml.rules.SyntacticalRule

class DataTypeAncestors implements SyntacticalRule {

    DataTypeAncestors(DataType self){
        this.errorMsg = 'A DataType can only have a DataType ancestor'
        this.self = self
    }

    @Override
    boolean condition() {
        return GenericCondition.includesAsAncestorsOnly(self, "isDataType")
    }
}