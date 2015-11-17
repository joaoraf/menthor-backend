- To compile/build the project, on Terminal, type **gradle wrapper** and then **gradlew build**. 
- To generate a Fat Jar for a given sub-project just type **gradle fatJar**

**Examples**: 

- Using the metamodel programatically 
  - _CarAccidentExample.groovy_ (ontouml/src/test/groovy)
- Serialize/deserialize to JSON 
  - _JSONSerializationTest.groovy_ (ontouml/src/test/groovy)
- Compatibility with RefOntoUML 
  - _RefMapperTest.groovy_ (refontouml/src/test/groovy)
- Compatibility to Ecore 
  - _EcoreMapperTest.groovy_ (ecore/src/test/groovy)
- Syntactical Checker
  - _CheckerTest.groovy_ (ontouml/src/test/groovy) 

**Wiki**: 

- [Metamodel] (https://github.com/johnguerson/groovy-ontouml2/wiki/Metamodel)
- [Syntactical Rules] (https://github.com/johnguerson/groovy-ontouml2/wiki/Rules) 

**Release Notes**:

- Version 1.0
  - Serialization in JSON, Car Accident example, and mappings to Ecore and RefOntoUML.
