package net.menthor.ontouml

import net.menthor.ontouml.stereotypes.DataTypeStereotype
import net.menthor.ontouml.values.MeasurementValue
import net.menthor.ontouml.values.ScaleValue
import net.menthor.ontouml.traits.Type
import org.codehaus.jackson.annotate.JsonIgnore
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
class DataType implements Type {

    protected DataTypeStereotype stereotype

    //in case of domains
    protected List<DataType> dimensions = []

    //in case of dimensions
    protected ScaleValue scale
    protected MeasurementValue measurement
    protected String unitOfMeasure
    protected float lowerBoundRegion
    protected float upperBoundRegion
    protected DataType ownerDomain

    //in case of enumerations
    protected List<Literal> literals = []
    protected DataType ownerStructure

    //=============================
    //Getters
    //=============================

    DataTypeStereotype getStereotype() { return stereotype }

    List<DataType> getDimensions() { return dimensions }

    ScaleValue getScale() { return scale }

    MeasurementValue getMeasurement() { return measurement }

    String getUnitOfMeasure() { return unitOfMeasure }

    float getLowerBoundRegion() { return lowerBoundRegion }

    float getUpperBoundRegion() { return upperBoundRegion }

    @JsonIgnore
    DataType getOwnerDomain() { return ownerDomain }

    List<Literal> getLiterals() { return literals }

    DataType getOwnerStructure() { return ownerStructure }

    //=============================
    // Setters were overwritten to ensure
    // opposite ends in the metamodel
    //=============================

    void setStereotype(DataTypeStereotype stereotype) { this.stereotype = stereotype }

    void setScale(ScaleValue scale) { this.scale = scale }

    void setMeasurement(MeasurementValue measurement) { this.measurement = measurement }

    void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure }

    void setLowerBoundRegion(float lowerBoundRegion) { this.lowerBoundRegion = lowerBoundRegion }

    void setUpperBoundRegion(float upperBoundRegion) { this.upperBoundRegion = upperBoundRegion }

    void setOwnerStructure(DataType ownerStructure) { this.ownerStructure = ownerStructure }

    void setDimension(DataType dimension){
        if(dimension==null) return
        if(!dimensions.contains(dimension)){
            dimensions.add(dimension)
        }
        dimension.ownerDomain = this
    }

    void setDimensions(List<DataType> dimensions){
        if(dimensions==null || dimensions==[]){
            this.dimensions.clear()
            return
        }
        dimensions.each{ d ->
            setDimension(d)
        }
    }

    void setOwnerDomain(DataType domain){
        ownerDomain = domain
        if(domain==null) return
        if(!domain.dimensions.contains(this)){
            domain.dimensions.add(this)
        }
    }

    void setLiteral(Literal literal){
        if(literal==null) return
        if(!literals.contains(literal)){
            literals.add(literal)
        }
        //Ensuring opposite end
        literal.setOwner(this)
    }

    void setLiterals(List<Literal> literals){
        if(literals==null || literals==[]){
            this.literals.clear()
            return
        }
        literals.each{ l ->
            setLiteral(l)
        }
    }

    //=============================
    // Stereotype Checking
    //=============================

    @JsonIgnore
    boolean isEnumeration(){ stereotype==DataTypeStereotype.ENUMERATION }

    @JsonIgnore
    boolean isDomain(){ stereotype==DataTypeStereotype.DOMAIN }

    @JsonIgnore
    boolean isDimension(){ stereotype==DataTypeStereotype.DIMENSION }

    @JsonIgnore
    boolean isDataType(){ stereotype==DataTypeStereotype.UNSET || stereotype==null }

    @JsonIgnore
    boolean isNominal() { scale!=null && scale==ScaleValue.NOMINAL }

    @JsonIgnore
    boolean isInterval() { scale!=null && scale==ScaleValue.INTERVAL }

    @JsonIgnore
    boolean isOrdinal() { scale!=null && scale==ScaleValue.ORDINAL }

    @JsonIgnore
    boolean isRational() { scale!=null && scale==ScaleValue.RATIONAL }

    @JsonIgnore
    boolean isString() { measurement!=null && measurement==MeasurementValue.STRING }

    @JsonIgnore
    boolean isInteger() { measurement!=null && measurement==MeasurementValue.INTEGER }

    @JsonIgnore
    boolean isDecimal() { measurement!=null && measurement==MeasurementValue.DECIMAL }

    @JsonIgnore
    boolean isReal() { measurement!=null && measurement==MeasurementValue.REAL }

    @JsonIgnore
    boolean isNominalString() { isNominal() && isString() }

    @JsonIgnore
    boolean isIntervalInteger() { isInterval() && isInteger() }

    @JsonIgnore
    boolean isIntervalDecimal() { isInterval() && isDecimal() }

    @JsonIgnore
    boolean isOrdinalInteger() { isOrdinal() && isInteger() }

    @JsonIgnore
    boolean isOrdinalDecimal() { isOrdinal() && isDecimal() }

    @JsonIgnore
    boolean isRationalInteger() { isRational() && isInteger() }

    @JsonIgnore
    boolean isRationalDecimal() { isRational() && isDecimal() }

    @JsonIgnore
    boolean isIntervalReal() { isInterval() && isReal() }

    @JsonIgnore
    boolean isOrdinalReal() { isOrdinal() && isReal() }

    @JsonIgnore
    boolean isRationalReal() { isRational() && isReal() }

    @JsonIgnore
    boolean isStructure() { isDimension() || isDomain() }
}
