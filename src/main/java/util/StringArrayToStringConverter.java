package util;

import javax.persistence.AttributeConverter;

public class StringArrayToStringConverter implements AttributeConverter<String[], String> {

    //http://hantsy.blogspot.com/2013/12/jpa-21-attribute-converter.html
    //https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/entity-attribute-type-converter.html

    @Override
    public String convertToDatabaseColumn(String[] strings) {
        return String.join(",", strings);
    }

    @Override
    public String[] convertToEntityAttribute(String s) {
        return s.split(",");
    }
}
