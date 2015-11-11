package net.menthor.ontouml.map

import net.menthor.ontouml.Model
import net.menthor.ontouml.traits.Container

class RefOntoMapper extends AbstractMapper {

//    RefOntoUML.RefOntoUMLFactory factory = RefOntoUML.RefOntoUMLFactory.eINSTANCE;
//    RefOntoUML.Model refmodel;

    RefOntoMapper(Model ontomodel) {
        super(ontomodel)
    }

    @Override
    Object cloneModel(Model ontomodel) {
//        this.refmodel = factory.createModel();
//        refmodel.setName(ontomodel.getName());
//        return refmodel
    }

    @Override
    Object clonePackage(Container ontopackage) {
//        String name = ontopackage.getName();
//        RefOntoUML.Package refpack = factory.createPackage();
//        refpack.setName(name);
//        refmodel.getPackagedElement().add(refpack);
//        return refpack
    }
}
