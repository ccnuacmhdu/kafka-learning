package org.example.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义分区器，可以参考DefaultPartitioner
 */
public class MyPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        String msgValue = value.toString();
        int partitionIdx;
        if("kafkaProducer 4".equals(msgValue)) {
            partitionIdx = 0;
        } else {
            partitionIdx = 1;
        }
        return partitionIdx;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
