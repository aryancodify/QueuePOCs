package com.kafka.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.kafka.consumer.ConsumerLoop;

public class KafkaDemo {
	public static void main(String[] args) { 
		System.setProperty("java.security.auth.login.config", "D:\\Kohls Softwares" + File.separator + "jaas.conf");
		  int numConsumers = 2;
		  String groupId = "test";
		  List<String> topics = Arrays.asList("testNotif");
		  final ExecutorService executor = Executors.newFixedThreadPool(numConsumers);
		  /*			  PrintWriter out = null;
	try {
			out = new PrintWriter("D:/kafkaout.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		  final List<ConsumerLoop> consumers = new ArrayList<>();
		  for (int i = 0; i < numConsumers; i++) {
		    ConsumerLoop consumer = new ConsumerLoop(i, groupId, topics/*,out*/);
		    consumers.add(consumer);
		    executor.submit(consumer);
		  }

		  Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		      for (ConsumerLoop consumer : consumers) {
		        consumer.shutdown();
		      } 
		      executor.shutdown();
		      try {
		        executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
		    }
		  });
		  //out.close();
		}
}
