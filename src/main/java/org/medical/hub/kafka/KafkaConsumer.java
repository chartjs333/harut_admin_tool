package org.medical.hub.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "incoming-mail", groupId = "group_id")
    void consume(ConsumerRecord<String, String> record) {
//        System.out.println("Email id: " + mailEvent.getUserEmailId());
        System.out.println("Email "+record.value());
    }
}
