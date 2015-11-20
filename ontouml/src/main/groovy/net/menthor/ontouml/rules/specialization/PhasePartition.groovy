package net.menthor.ontouml.rules.specialization

import net.menthor.ontouml.Class
import net.menthor.ontouml.rules.SyntacticalRule

class PhasePartition implements SyntacticalRule {

    PhasePartition(Class self){
        this.errorMsg = 'A Phase must be grouped in exactly one {disjoint, complete} Generalization Set with other Phases (i.e. every phase must be in a partition with at least one more phase).'
        this.self = self
    }

    @Override
    boolean condition() {
        if(self.isPhase()){
            def gensets = self.getIsSpecificIn().collect{ g -> g.getGeneralizationSet() }
            def value = gensets.size()==1 && gensets.get(0).isCovering() && gensets.get(0).isDisjoint() && gensets.get(0).specifics().every{ s -> (s instanceof Class) && (s as Class).isPhase() }
            return value
        }
        return true
    }
}