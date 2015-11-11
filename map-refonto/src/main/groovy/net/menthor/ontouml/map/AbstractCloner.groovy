package net.menthor.ontouml.map

import net.menthor.ontouml.Model
import net.menthor.ontouml.traits.Container

abstract class AbstractCloner {

    abstract Object cloneModel(Model ontomodel)
    abstract Object clonePackage(Container ontopackage)
}
