package org.example.kafka.flink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * flink生产者
 */
public class FlinkKafkaProducerDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        List<String> wordsList = Arrays.asList("hello", "flink", "kafka");
        DataStream<String> stream = env.fromCollection(wordsList);

        FlinkKafkaProducer<String> kafkaProducer = new FlinkKafkaProducer<>("first", new SimpleStringSchema(), properties);

        stream.addSink(kafkaProducer);

        stream.print();

        env.execute();
    }
}
