package org.medical.hub.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RuleTypeConverter implements AttributeConverter<RuleType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RuleType attribute) {
        if(attribute == null){
            return null;
        }

        return attribute.getValue();
    }

    @Override
    public RuleType convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }

        return Stream.of(RuleType.values())
                .filter(c -> Objects.equals(c.getValue(), dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
