package net.menthor.ontouml.rules

import net.menthor.ontouml.Relationship
import net.menthor.mcore.traits.MType
import net.menthor.ontouml.values.CiclicityValue
import net.menthor.ontouml.values.ReflexivityValue
import net.menthor.ontouml.values.SymmetryValue
import net.menthor.ontouml.values.TransitivityValue

class GenericCondition {

    static boolean exactOne(MType self, String stereotypeCheckMethod, String listElemsMethod){
        if(self."${stereotypeCheckMethod}"()){
            def value = self."${listElemsMethod}"().size()==1
            return value
        }
        return true
    }

    static boolean excludesAsAncestor(MType self, String stereotypeCheckMethod, String ancestorCheckMethod, String ancestorCheckMethod2){
        if(self."${stereotypeCheckMethod}"()) {
            def value = self.allParents().every { p ->
                self.class.isInstance(p) && !p."${ancestorCheckMethod}"() && !p."${ancestorCheckMethod2}"()
            }
            return value
        }
        return true
    }

    static boolean excludesAsAncestor(MType self, String stereotypeCheckMethod, String ancestorCheckMethod){
        if(self."${stereotypeCheckMethod}"()) {
            def value = self.allParents().every { p ->
                self.class.isInstance(p) && !p."${ancestorCheckMethod}"()
            }
            return value
        }
        return true
    }

    static boolean excludesAsParent(MType self, String stereotypeCheckMethod, String ancestorCheckMethod, String ancestorCheckMethod2){
        if(self."${stereotypeCheckMethod}"()) {
            def value = self.parents().every { p ->
                self.class.isInstance(p) && !p."${ancestorCheckMethod}"() && !p."${ancestorCheckMethod2}"()
            }
            return value
        }
        return true
    }

    static boolean excludesAsParent(MType self, String stereotypeCheckMethod, String ancestorCheckMethod){
        if(self."${stereotypeCheckMethod}"()) {
            def value = self.parents().every { p ->
                self.class.isInstance(p) && !p."${ancestorCheckMethod}"()
            }
            return value
        }
        return true
    }

    static boolean includesAsAncestorsOnly(MType self, String stereotypeCheckMethod){
        if(self."${stereotypeCheckMethod}"()) {
            def value = self.allParents().every { p ->
                self.class.isInstance(p) && p."${stereotypeCheckMethod}"()
            }
            return value
        }
        return true
    }

    static boolean typeMustBeConnectedToRelationship(MType self, String typeStereoCheckMethod, String relStereoCheckMethod){
        if(self."${typeStereoCheckMethod}"()){
            def value = self.allRelationships().any { r ->
                r."${relStereoCheckMethod}"()
            }
            return value
        }
        return true
    }

    static boolean typeComposedByAtLeastTwoElements(MType self, String typeStereoCheckMethod, String listElemMethod, Integer number){
        if(self."${typeStereoCheckMethod}"()) {
            return self."${listElemMethod}"().size() >= number
        }
        return true
    }


    static boolean sourceClassMustBe(Relationship self, String relStereotypeMethod, String sourceStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.sourceClass()!=null && self.sourceClass()."${sourceStereotypeMethod}"()
            return value
        }
        return true
    }

    static boolean mustConnect(Relationship self, String relStereotypeMethod, String sourceStereotypeMethod, String targetStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def valueSrc = self.sourceClass()!=null && self.sourceClass()."${sourceStereotypeMethod}"()
            def valueTgt = self.targetClass()!=null && self.targetClass()."${targetStereotypeMethod}"()
            return valueSrc && valueTgt
        }
        return true
    }

    static boolean mustNotConnect(Relationship self, String relStereotypeMethod, String sourceStereotypeMethod, String targetStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def valueSrc = self.sourceClass()!=null && !self.sourceClass()."${sourceStereotypeMethod}"()
            def valueTgt = self.targetClass()!=null && !self.targetClass()."${targetStereotypeMethod}"()
            return valueSrc && valueTgt
        }
        return true
    }

    static boolean sourceClassMustNotBe(Relationship self, String relStereotypeMethod, String sourceStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.sourceClass()!=null && !self.sourceClass()."${sourceStereotypeMethod}"()
            return value
        }
        return true
    }

    static boolean wholeMustBe(Relationship self, String relStereotypeMethod, String wholeStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.wholeClass()!=null && self.wholeClass()."${wholeStereotypeMethod}"()
            return value
        }
        return true
    }

    static boolean targetClassMustBe(Relationship self, String relStereotypeMethod, String sourceStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.targetClass()!=null && self.targetClass()."${sourceStereotypeMethod}"()
            return value
        }
        return true
    }

    static boolean partMustBe(Relationship self, String relStereotypeMethod, String partStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.partClass()!=null && self.partClass()."${partStereotypeMethod}"()
            return value
        }
        return true
    }

    static boolean partMustBeEither(Relationship self, String relStereotypeMethod, String partStereotypeMethod, String partStereotypeMethod2){
        if(self."${relStereotypeMethod}"()){
            def value = self.partClass()!=null && (self.partClass()."${partStereotypeMethod}"() || self.partClass()."${partStereotypeMethod2}"())
            return value
        }
        return true
    }

    static boolean sourceAndTargetDependents(Relationship self, String relStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.sourceEndPoint()!=null && self.sourceEndPoint().isDependency() &&
                    self.targetEndPoint()!=null && self.targetEndPoint().isDependency()
            return value
        }
        return true
    }

    static boolean sourceDependent(Relationship self, String relStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.sourceEndPoint()!=null && self.sourceEndPoint().isDependency()
            return value
        }
        return true
    }

    static boolean targetDependent(Relationship self, String relStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.targetEndPoint()!=null && self.targetEndPoint().isDependency()
            return value
        }
        return true
    }

    static boolean partAndWholeDependents(Relationship self, String relStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.partEndPoint()!=null && self.partEndPoint().isDependency() &&
                    self.wholeEndPoint()!=null && self.wholeEndPoint().isDependency()
            return value
        }
        return true
    }

    static boolean partDependent(Relationship self, String relStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.partEndPoint()!=null && self.partEndPoint().isDependency()
            return value
        }
        return true
    }

    static boolean wholeDependent(Relationship self, String relStereotypeMethod){
        if(self."${relStereotypeMethod}"()){
            def value = self.wholeEndPoint()!=null && self.wholeEndPoint().isDependency()
            return value
        }
        return true
    }

    static boolean isMetaAttribute(Relationship self, String relStereotypeMethod, String metaAttrMethod, boolean value){
        if(self."${relStereotypeMethod}"()){
            def result = self."${metaAttrMethod}"()==value
            return result
        }
        return true
    }

    static boolean isMetaAttributeIfWholeIs(Relationship self, String relStereotypeMethod, String metaAttrMethod, boolean value, String wholeStereotypeMethod){
        if(self."${relStereotypeMethod}"() && self.wholeClass()!=null && self.wholeClass()."${wholeStereotypeMethod}"){
            def result = self."${metaAttrMethod}"()==value
            return result
        }
        return true
    }

    static boolean isMetaAttributeIfPartIs(Relationship self, String relStereotypeMethod, String metaAttrMethod, boolean value, String partStereotypeMethod){
        if(self."${relStereotypeMethod}"() && self.partClass()!=null && self.partClass()."${partStereotypeMethod}"){
            def result = self."${metaAttrMethod}"()==value
            return result
        }
        return true
    }

    static boolean isValue(Relationship self, String relStereotypeMethod, ReflexivityValue rValue, SymmetryValue sValue, TransitivityValue tValue, CiclicityValue cValue){
        if(self."${relStereotypeMethod}"()){
            def result = true
            if(rValue!=null) result = result && self.reflexivityValue == rValue
            if(sValue!=null) result = result && self.symmetryValue == sValue
            if(tValue!=null) result = result && self.transitivityValue == tValue
            if(cValue!=null) result = result && self.ciclicityValue == cValue
            return result
        }
        return true
    }

    static boolean targetMultiplicity(Relationship self, String relStereotypeMethod, Integer lowerBound, Integer upperBound){
        if(self."${relStereotypeMethod}"()){
            def value = true
            if(lowerBound!=null) value = value && self.targetEndPoint()!=null && self.targetEndPoint().getLowerBound() == lowerBound
            if(upperBound!=null) value = value && self.targetEndPoint()!=null && self.targetEndPoint().getUpperBound() == upperBound
            return value
        }
        return true
    }

    static boolean partMultiplicity(Relationship self, String relStereotypeMethod, Integer lowerBound, Integer upperBound){
        if(self."${relStereotypeMethod}"()){
            def value = true
            if(lowerBound!=null) value = value && self.partEndPoint()!=null && self.partEndPoint().getLowerBound() == lowerBound
            if(upperBound!=null) value = value && self.partEndPoint()!=null && self.partEndPoint().getUpperBound() == upperBound
            return value
        }
        return true
    }

    static boolean wholeMultiplicity(Relationship self, String relStereotypeMethod, Integer lowerBound, Integer upperBound){
        if(self."${relStereotypeMethod}"()){
            def value = true
            if(lowerBound!=null) value = value && self.wholeEndPoint()!=null && self.wholeEndPoint().getLowerBound() == lowerBound
            if(upperBound!=null) value = value && self.wholeEndPoint()!=null && self.wholeEndPoint().getUpperBound() == upperBound
            return value
        }
        return true
    }

    static boolean sourceMultiplicity(Relationship self, String relStereotypeMethod, Integer lowerBound, Integer upperBound){
        if(self."${relStereotypeMethod}"()){
            def value = true
            if(lowerBound!=null) value = value && self.sourceEndPoint()!=null && self.sourceEndPoint().getLowerBound() == lowerBound
            if(upperBound!=null) value = value && self.sourceEndPoint()!=null && self.sourceEndPoint().getUpperBound() == upperBound
            return value
        }
        return true
    }

}

