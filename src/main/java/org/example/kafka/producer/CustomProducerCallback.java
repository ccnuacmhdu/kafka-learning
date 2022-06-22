package org.example.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class CustomProducerCallback {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0; i < 5; i++) {
            // 异步发送，带回调函数
            kafkaProducer.send(new ProducerRecord<>("first", "kafkaProducer " + i), (recordMetadata, e) -> {
                if (e == null) {
                    System.out.println("topic: " + recordMetadata.topic() + ", partition：" + recordMetadata.partition() +
                            ", offset: " + recordMetadata.offset());
                } else {
                    e.printStackTrace();
                }
            });
            // 延迟一会儿，会看到数据发往不同分区
            Thread.sleep(2);
        }

        kafkaProducer.close();
    }
}
