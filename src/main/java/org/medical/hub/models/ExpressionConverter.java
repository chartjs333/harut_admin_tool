package org.medical.hub.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ExpressionConverter implements AttributeConverter<Expression, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Expression attribute) {
        if(attribute == null){
            return null;
        }

        return attribute.getValue();
    }

    @Override
    public Expression convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }
        return Stream.of(Expression.values())
                .filter(c -> Objects.equals(c.getValue(), dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
