package net.menthor.ontouml.rules.connection

import net.menthor.ontouml.Relationship
import net.menthor.ontouml.rules.SyntacticalRule
import net.menthor.ontouml.Class

class MediationTarget implements SyntacticalRule {

    MediationTarget(Relationship self){
        this.errorMsg = 'The mediated type (target) of a Mediation must be a Rigid Sortal (Kind, Collective Quantity, or SubKind), Category, Role or RoleMixin'
        this.self = self
    }

    @Override
    boolean condition() {
        def rel = self as Relationship
        if(rel.isMediation()){
            def tgtClass = (rel.targetClass() as Class)
            return tgtClass!=null && (tgtClass.isSubstanceSortalClass() || tgtClass.isSubKind() ||
                   tgtClass.isCategory() || tgtClass.isRole() || tgtClass.isRoleMixin())
        }
        return true
    }
}