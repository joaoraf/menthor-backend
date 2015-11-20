### Menthor Backend Infrastructure

- To compile/build the project, on Terminal, type **gradle wrapper** and then **gradlew build**. 
- To generate a Fat Jar for a given sub-project just type **gradle fatJar**

**Projects**:

- /mcore: Menthor core metamodel (MCore)
- /ontouml: OntoUML 2.0 metamodel
- /map-refontouml: Mapping to the Reference OntoUML infrastructure
- /map-ecore: Mapping to the ECore metamodeling langauge

**Examples**: 

- Using the metamodel programatically: CarAccidentExample.groovy
- Serialize/deserialize to JSON: SerializationTest.groovy
- Mapping to RefOntoUML: RefMapperTest.groovy
- Mapping to Ecore: EcoreMapperTest.groovy
- Syntactical Checker: CheckerTest.groovy

