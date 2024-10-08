# Apache Kafka in spring boot ang golang

### Quick start

Start the kafka cluster
```shell
docker compose down -v
cd kafka
docker compose up -d
```

Start the producers and consumers by
```shell
./gradlew bootrun # for spring boot
go run . # for go
```


### Note to self
kafka-topics --create --topic message --bootstrap-server localhost:29092 --partitions 1 --replication-factor 1

- If we set concurrency to x, it is similar to x instance with concurrency 1. ie x threads will listen to x partition. If there are not enough partition then some will just sit idly

- Using json serializer and deserializer, the pojo should have the same class and package. So, keep them in a common package in both producer and consumer. else can be solved with [type mapping](https://docs.spring.io/spring-kafka/reference/kafka/serdes.html#serdes-mapping-types)

- To handle the class mismatch which is checked during deserialization use an wrapper of error handling deserializer

- If a field is absent in consumer then it is set to null as we are using json serializer and deserializer

- Even if a project doesn't use any producer it is better to define the serializer. This is necessary when a consumer fails and have to write to dead letter topic thus becoming a producer underneath

- Utilize key-based partitioning to ensure that related messages are consistently routed to the same partition, preserving order and enabling efficient data processing.



