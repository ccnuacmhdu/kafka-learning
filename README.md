# kafka-learning

## Chapter1 kafka概述、安装及命令行操作
https://blog.csdn.net/ccnuacmhdu/article/details/125394957

## Chapter2 kafka生产者
#### 异步发送
org.example.kafka.producer.CustomProducer
#### 回调异步发送
org.example.kafka.producer.CustomProducerCallback
#### 同步发送
org.example.kafka.producer.CustomProducerSync
#### 自定义分区器
org.example.kafka.producer.MyPartitioner
org.example.kafka.producer.CustomProducerCallbackPartition
#### 提高生产者的吞吐量
org.example.kafka.producer.CustomProducerConfiguration
#### 生产者事务
org.example.kafka.producer.CustomProducerTransaction

## Chapter3 kafka消费者
#### 独立消费者订阅主题
org.example.kafka.consumer.CustomConsumer
#### 独立消费者订阅分区
org.example.kafka.consumer.CustomConsumerPartition
#### 消费者组
org.example.kafka.consumer.CustomConsumer2
#### 指定offset消费
org.example.kafka.consumer.CustomConsumerSeek
#### 指定时间消费
org.example.kafka.consumer.CustomConsumerForTime

## Chapter4 对接外部系统
#### 对接flink
org.example.kafka.flink.FlinkKafkaProducerDemo
org.example.kafka.flink.FlinkKafkaConsumerDemo
