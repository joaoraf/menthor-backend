package net.menthor.ontouml2

import net.menthor.ontouml2.stereotypes.DataTypeStereotype
import net.menthor.ontouml2.traits.Type
import net.menthor.ontouml2.values.MeasurementValue
import net.menthor.ontouml2.values.ScaleValue;

class DataType implements Type {

    DataTypeStereotype stereotype
    //in case of domains
    List<DataType> dimensions
    //in case of dimensions
    ScaleValue scale
    MeasurementValue measurement
    String unitOfMeasure
    float lowerBoundRegion
    float upperBoundRegion
    DataType ownerDomain
    //in case of enumerations
    List<Literal> literals
    DataType ownerStructure

    boolean isEnumeration(){
        stereotype==DataTypeStereotype.ENUMERATION
    }
    boolean isDomain(){
        stereotype==DataTypeStereotype.DOMAIN
    }
    boolean isDimension(){
        stereotype==DataTypeStereotype.DIMENSION
    }
    boolean isDataType(){
        stereotype==DataTypeStereotype.UNSET || stereotype==null
    }
    boolean isNominal() {
        scale!=null && scale==ScaleValue.NOMINAL
    }
    boolean isInterval() {
        scale!=null && scale==ScaleValue.INTERVAL
    }
    boolean isOrdinal() {
        scale!=null && scale==ScaleValue.ORDINAL
    }
    boolean isRational() {
        scale!=null && scale==ScaleValue.RATIONAL
    }
    boolean isString() {
        measurement!=null && measurement==MeasurementValue.STRING
    }
    boolean isInteger() {
        measurement!=null && measurement==MeasurementValue.INTEGER
    }
    boolean isDecimal() {
        measurement!=null && measurement==MeasurementValue.DECIMAL
    }
    boolean isReal() {
        measurement!=null && measurement==MeasurementValue.REAL
    }
    boolean isNominalString() {
        isNominal() && isString()
    }
    boolean isIntervalInteger() {
        isInterval() && isInteger()
    }
    boolean isIntervalDecimal() {
        isInterval() && isDecimal()
    }
    boolean isOrdinalInteger() {
        isOrdinal() && isInteger()
    }
    boolean isOrdinalDecimal() {
        isOrdinal() && isDecimal()
    }
    boolean isRationalInteger() {
        isRational() && isInteger()
    }
    boolean isRationalDecimal() {
        isRational() && isDecimal()
    }
    boolean isIntervalReal() {
        isInterval() && isReal()
    }
    boolean isOrdinalReal() {
        isOrdinal() && isReal()
    }
    boolean isRationalReal() {
        isRational() && isReal()
    }
    boolean isStructure() {
        isDimension() || isDomain()
    }
}
