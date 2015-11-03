package net.menthor.ontouml2

import net.menthor.ontouml2.stereotypes.ClassStereotype
import net.menthor.ontouml2.stereotypes.QualityStereotype
import net.menthor.ontouml2.traits.Type
import net.menthor.ontouml2.values.ClassificationValue
import net.menthor.ontouml2.values.ExistenceValue;

class Class implements Type {

    ClassStereotype stereotype
    QualityStereotype qualityStereotype //in case of qualities
    boolean isAbstract
    boolean isDerived
    boolean isExtensional //collective
    ExistenceValue existenceValue //identity providers
    ClassificationValue classificationValue //anti-rigid classes

    //================================
    //Stereotype Checking
    //================================

    boolean isKind(){
        stereotype==ClassStereotype.KIND
    }
    boolean isSubKind(){
        stereotype==ClassStereotype.SUB_KIND
    }
    boolean isCollective(){
        stereotype==ClassStereotype.COLLECTIVE
    }
    boolean isQuantity(){
        stereotype==ClassStereotype.QUANTITY
    }
    boolean isRelator(){
        stereotype==ClassStereotype.RELATOR
    }
    boolean isMode(){
        stereotype==ClassStereotype.MODE
    }
    boolean isQuality(){
        stereotype==ClassStereotype.QUALITY
    }
    boolean isRole(){
        stereotype==ClassStereotype.ROLE
    }
    boolean isRoleMixin(){
        stereotype==ClassStereotype.ROLE_MIXIN
    }
    boolean isPhaseMixin(){
        stereotype==ClassStereotype.PHASE_MIXIN
    }
    boolean isPhase(){
        stereotype==ClassStereotype.PHASE
    }
    boolean isCategory(){
        stereotype==ClassStereotype.CATEGORY
    }
    boolean isMixin(){
        stereotype==ClassStereotype.MIXIN
    }
    boolean isEvent(){
        stereotype==ClassStereotype.EVENT
    }
    boolean isHighOrder(){
        stereotype==ClassStereotype.HIGH_ORDER
    }
    boolean isRigid() {
        isKind() || isCollective() || isQuantity() ||isRelator() || isMode() || isQuality() ||isSubKind() || isCategory()
    }
    boolean isNonRigid() {
        isRole()|| isPhase() || isRoleMixin() || isPhaseMixin() || isMixin()
    }
    boolean isAntiRigid() {
        isRole() || isPhase() || isRoleMixin() || isPhaseMixin()
    }
    boolean isSemiRigid() {
        isMixin()
    }
    boolean isPerceivableQuality() {
        isQuality() && qualityStereotype!=null && qualityStereotype==QualityStereotype.PERCEIVABLE
    }
    boolean isNonPerceivableQuality() {
        isQuality() && qualityStereotype!=null && qualityStereotype==QualityStereotype.NON_PERCEIVABLE
    }
    boolean isNominalQuality() {
        isQuality() && qualityStereotype!=null && qualityStereotype==QualityStereotype.NOMINAL
    }
    boolean isEndurantClass() {
        !(isEvent() || isHighOrder())
    }
    boolean isIntrinsicMoment() {
        isNonQualitativeIntrinsicMoment() || isQualitativeIntrinsicMoment()
    }
    boolean isSubstanceSortalClass() {
        isKind() || isCollective() || isQuantity()
    }
    boolean isMomentClass() {
        isRelator() || isMode() || isQuality()
    }
    boolean isIdentityProviderClass() {
        isKind() || isQuantity() || isCollective() || isRelator() || isMode() || isQuality()
    }
    boolean isMixinClass() {
        isMixin() || isRoleMixin() || isPhaseMixin() || isCategory()
    }
    boolean isAntiRigidMixinClass() {
        isRoleMixin() || isPhaseMixin()
    }

    //================================
    //Identity Checking
    //================================

    /**
     * Checks if this element is an amount of matter i.e.
     * 1) if it is a quantity element, or,
     * 2) if it is a subKind or role/phase with exactly one identity provider of the type Quantity, or,
     * 3) if it is a mixin class in which all their children are quantities.
     */
    boolean isAmountOfMatter() {
        if(isQuantity()) return true;
        if(isRole() || isPhase() || isSubKind()){
            def providers = []
            providers.addAll(identityProviders());
            providers.each { c ->
                if (c.isQuantity()) {
                    return true
                }
            }
        }
        if(isMixinClass()) {
            if(children().size()==0) {
                return false
            }
            children().each{ child ->
                if(child instanceof Class) {
                    if(!child.isQuantity()) return false
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if this element is a functional complex i.e.
     * 1) If it is a kind, or
     * 2) if it is a subKind or role/phase with exactly one identity provider of the type kind, or,
     * 3) if it is a mixin class in which all their children are functional complexes.
     */
    boolean isFunctionalComplex(){
        if(isKind()) {
            return true
        }
        if(isRole() || isPhase() || isSubKind()){
            def providers = []
            providers.addAll(identityProviders());
            providers.each { c ->
                if (c.isKind()) {
                    return true
                }
            }
        }
        if(isMixinClass()){
            if(children().size()==0) {
                return false
            }
            children().each{ child ->
                if(child instanceof Class) {
                    if(!child.isKind()) {
                        return false
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if this element is a collective i.e.
     * 1) if it is a collective element, or,
     * 2) if it is a subKind or role/phase with exactly one identity provider of the type Collective, or,
     * 3) if it is a mixin class in which all their children are collectives.
     */
    boolean isCollection(){
        if(isCollective()) {
            return true
        }
        if(isRole() || isPhase() || isSubKind()){
            def providers = []
            providers.addAll(identityProviders());
            providers.each { c ->
                if (c.isCollective()) {
                    return true
                }
            }
        }
        if(isMixinClass()) {
            if(children().size()==0) {
                return false
            }
            children().each{ child ->
                if(child instanceof Class) {
                    if(!child.isCollective()) {
                        return false
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if this element is a moment  i.e.
     * 1) if it is a relator, mode or quality element, or,
     * 2) if it is a subKind or role/phase with exactly one identity provider being a relator, mode or quality, or,
     * 3) if it is a mixin class in which all their children are relators, qualities or modes.
     */
    boolean isMoment(){
        if(isMomentClass()) {
            return true
        }
        if(isRole() || isPhase() || isSubKind()){
            def providers = []
            providers.addAll(identityProviders());
            providers.each { c ->
                if (c.isMomentClass()) {
                    return true
                }
            }
        }
        if(isMixinClass()){
            if(children().size()==0) {
                return false
            }
           children().each{ child ->
                if(child instanceof Class) {
                    if(!child.isMomentClass()) {
                        return false
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if this element is a truth maker (relator) i.e.
     * 1) if it is a truth maker element, or,
     * 2) if it is a subKind or role/phase with exactly one identity provider being a truth maker, or,
     * 3) if it is a mixin class in which all their children are truth makers.
     */
    boolean isTruthMaker(){
        if(isRelator()) {
            return true
        }
        if(isRole() || isPhase() || isSubKind()){
            def providers = []
            providers.addAll(identityProviders());
            providers.each { c ->
                if (c.isRelator()) {
                    return true
                }
            }
        }
        if(isMixinClass()){
            if(children().size()==0) {
                return false
            }
            children().each{ child ->
                if(child instanceof Class) {
                    if(!child.isRelator()) {
                        return false
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if this element is a non qualitative instrinsic moment (a mode) i.e.
     * 1) if it is a instrinsic moment which is a non qualitative element, or,
     * 2) if it is a subKind or role/phase with exactly one identity provider being a non qualitative intrinsic moment, or,
     * 3) if it is a mixin class in which all their children are non qualitative intrinsic moment.
     */
    boolean isNonQualitativeIntrinsicMoment() {
        if(isMode()) {
            return true
        }
        if(isRole() || isPhase() || isSubKind()){
            def providers = []
            providers.addAll(identityProviders());
            providers.each { c ->
                if (c.isMode()) {
                    return true
                }
            }
        }
        if(isMixinClass()){
            if(children().size()==0) {
                return false
            }
            children().each{ child ->
                if(child instanceof Class) {
                    if(!child.isMode()) {
                        return false
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if this element is a qualitative instrinsic moment (quality) i.e.
     * 1) if it is a instrinsic moment which is a qualitative element, or,
     * 2) if it is a subKind or role/phase with exactly one identity provider being a qualitative intrinsic moment, or,
     * 3) if it is a mixin class in which all their children are qualitative intrinsic moment.
     */
    boolean isQualitativeIntrinsicMoment(){
        if(isQuality()) {
            return true
        }
        if(isRole() || isPhase() || isSubKind()){
            def providers = []
            providers.addAll(identityProviders());
            providers.each { c ->
                if (c.isQuality()) {
                    return true
                }
            }
        }
        if(isMixinClass()){
            if(children().size()==0) {
                return false
            }
            children().each{ child ->
                if(child instanceof Class) {
                    if(!child.isQuality()) {
                        return false
                    }
                }
            }
            return true;
        }
        return false;
    }

    /** Returns the identity providers amongst all parents of a class (more than one may be found) */
    List<Class> identityProvidersAtAllParents(){
        def result = []
        allParents().each{ p ->
            if(p instanceof Class){
                if(p.isIdentityProviderClass()) {
                    result.add(p)
                }
            }
        }
        return result;
    }

    /** Returns the identity providers amongst all children of a class (more than one may be found) */
    List<Class> identityProvidersAtAllChildren(){
        def result = []
        allChildren().each{ p ->
            if(p instanceof Class){
                if(p.isIdentityProviderClass()) {
                    result.add(p)
                }
                if(p.isRole() || isPhase() || p.isSubKind()) {
                    result.addAll(p.identityProvidersAtAllParents())
                }
            }
        }
        return result;
    }

    /** Returns the identity providers of a class (more than one may be found) */
    List<Class> identityProviders() {
        def result = []
        if (isIdentityProviderClass()) {
            result.add(this);
        }
        if (isRole() || isPhase() || isSubKind()) {
            result.addAll(identityProvidersAtAllParents());
        }
        if (isMixinClass()){
            result.addAll(identityProvidersAtAllChildren());
            allParents().each{ p ->
                if(p instanceof Class){
                    result.addAll(p.identityProvidersAtAllChildren());
                }
            }
        }
        return result;
    }
}
