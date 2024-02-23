package org.medical.hub.mail;

import javax.persistence.AttributeConverter;
import java.util.Objects;
import java.util.stream.Stream;

public class MailStatusConverter implements AttributeConverter<MailStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MailStatus attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getValue();
    }

    @Override
    public MailStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(MailStatus.values())
                .filter(c -> Objects.equals(c.getValue(), dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
