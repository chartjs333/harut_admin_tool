package org.medical.hub.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CompareBetweenConverter implements AttributeConverter<CompareBetween, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CompareBetween attribute) {
        if(attribute == null){
            return null;
        }

        return attribute.getValue();
    }

    @Override
    public CompareBetween convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }
        return Stream.of(CompareBetween.values())
                .filter(c -> Objects.equals(c.getValue(), dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
