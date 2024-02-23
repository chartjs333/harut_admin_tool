package org.medical.hub.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KafkaMessageDeserializer implements Deserializer<Message<UserEmailEvent>> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {}

    @Override
    public Message<UserEmailEvent> deserialize(String s, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new String(bytes,
                    StandardCharsets.UTF_8), Message.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public void close() {}
}
