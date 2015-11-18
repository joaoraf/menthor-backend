package net.menthor.ontouml.map

import net.menthor.ontouml.*
import net.menthor.ontouml.stereotypes.PrimitiveStereotype
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.*
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.util.BasicExtendedMetaData
import org.eclipse.emf.ecore.util.ExtendedMetaData
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl
import sun.security.krb5.internal.crypto.EType

class EcoreMapper {

    static void main(String[] args){
        println "\n================================================"
        println "Compatibility with the Ecore Metamodeling Language"
        println "Copyright: MIT License"
        println "Powered by Menthor (www.menthor.net)"
        println "=================================================="
    }

    private EcoreTargetMapper tgtMapper = new EcoreTargetMapper()

    EPackage toEcore(Model m) {
        return tgtMapper.toEcore(m)
    }

    EPackage toEcore(Model m, boolean ignorePackages) {
        return tgtMapper.toEcore(m, ignorePackages)
    }

    void serialize(EPackage ecoremodel, String path){
        tgtMapper.serialize(ecoremodel,path)
    }
}
