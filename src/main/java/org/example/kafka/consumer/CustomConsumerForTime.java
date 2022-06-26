package org.example.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * 指定时间消费
 */
public class CustomConsumerForTime {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 配置消费者组（必须配置）
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer_group_id_test");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        List<String> topics = Arrays.asList("first");
        kafkaConsumer.subscribe(topics);

        Set<TopicPartition> assignment = new HashSet<>();
        // 要拿到分区分配方案后，才能消费到数据，否则消费不到数据
        while (assignment.size() == 0) {
            kafkaConsumer.poll(Duration.ofSeconds(1));
            assignment = kafkaConsumer.assignment();
        }
        Map<TopicPartition, Long> timestampToSearch = new HashMap<>();
        // 封装集合存储，每个分区对应一天前的数据
        for (TopicPartition topicPartition : assignment) {
            timestampToSearch.put(topicPartition, System.currentTimeMillis() - 1 * 24 * 3600 * 1000);
        }
        // 获取从 1 天前开始消费的每个分区的 offset
        Map<TopicPartition, OffsetAndTimestamp> offsets = kafkaConsumer.offsetsForTimes(timestampToSearch);

        for (TopicPartition topicPartition : assignment) {
            OffsetAndTimestamp offsetAndTimestamp = offsets.get(topicPartition);
            // 根据时间指定开始消费的位置
            if (offsetAndTimestamp != null){
                kafkaConsumer.seek(topicPartition, offsetAndTimestamp.offset());
            }
        }

        while (true) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println(consumerRecord);
            }
        }
    }

}
