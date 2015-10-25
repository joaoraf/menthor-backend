package net.menthor.ontouml2.impl

import net.menthor.ontouml2.DataType
import net.menthor.ontouml2.Literal
import net.menthor.ontouml2.stereo.DataTypeStereotype
import net.menthor.ontouml2.value.MeasurementValue
import net.menthor.ontouml2.value.ScaleValue;

class DataTypeImpl extends TypeImpl implements DataType {

    DataTypeStereotype stereotype

    //Domain
    List<DataType> dimensions

    //Dimension
    ScaleValue scale
    MeasurementValue measurement
    String unitOfMeasure
    float lowerBoundRegion
    float upperBoundRegion
    DataType ownerDomain

    //Enumeration
    List<Literal> literals
    DataType structure
}
