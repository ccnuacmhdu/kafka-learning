package org.example.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 自定义分区器测试
 */
public class CustomProducerCallbackPartition {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"org.example.kafka.producer.MyPartitioner");

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0; i < 5; i++) {
            // 异步发送，带回调函数，使用自定义分区器
            final int finalI = i;
            kafkaProducer.send(new ProducerRecord<>("first", "kafkaProducer " + i), (recordMetadata, e) -> {
                if (e == null) {
                    System.out.println("topic: " + recordMetadata.topic() + ", value: kafkaProducer " + finalI +
                            ", partition：" + recordMetadata.partition() + ", offset: " + recordMetadata.offset());
                } else {
                    e.printStackTrace();
                }
            });
        }

        kafkaProducer.close();
    }
}
