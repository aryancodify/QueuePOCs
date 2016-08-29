This repository contains the demos i constructed for various queueing mechanisms mainly in multi threaded mode. To see the demo for Apache Kafka
go to 

  https://www.youtube.com/watch?v=TumTnYFcXro
  
  Kafka Commands
  
	1. Start Kafka : 
.\bin\windows\kafka-server-start.bat .\config\server.properties

	2. Create Topic :
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

	3. Create Producer :
kafka-console-producer.bat --broker-list localhost:9092 --topic test

	4. Create Condsumer :
kafka-console-consumer.bat --zookeeper localhost:2181 --topic test

		1. List Topics: kafka-topics.bat --list --zookeeper localhost:2181
		2. Describe Topic: kafka-topics.bat --describe --zookeeper localhost:2181 --topic [Topic Name]
		3. Read messages from beginning: kafka-console-consumer.bat --zookeeper localhost:2181 --topic [Topic Name] --from-beginning
		4. Delete Topic: kafka-run-class.bat kafka.admin.TopicCommand --delete --topic [topic_to_delete] --zookeeper localhost:2181
		5. .\bin\windows\kafka-server-start.bat .\config\server-1.properties
		6. .\bin\windows\kafka-server-start.bat .\config\server-2.properties



