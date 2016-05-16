package com.kafka.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import com.google.common.io.Resources;
import com.kafka.beans.Notification;

public class ConsumerLoop implements Runnable {

	private final KafkaConsumer<String, Notification> consumer;
	  private final List<String> topics;
	  private final int id;
	  PrintWriter out;
	  public ConsumerLoop(int id,
	                      String groupId, 
	                      List<String> topics/*,PrintWriter out*/) {
	//	this.out = out;
	    this.id = id;
	    this.topics = topics;
	    Properties properties = new Properties();
	    try (InputStream props = Resources.getResource("consumer.props").openStream()) {
	         properties.load(props);
	         if (properties.getProperty("group.id") == null) {
	             properties.setProperty("group.id", groupId);
	         }
	     } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    this.consumer = new KafkaConsumer<String, Notification>(properties);
	  }
	 
	  @Override
	  public void run() {
	    try {
	      consumer.subscribe(topics);

	      while (true) {
	        ConsumerRecords<String, Notification> records = consumer.poll(Long.MAX_VALUE);
	        for (ConsumerRecord<String, Notification> record : records) {
	          /*Map<String, Object> data = new HashMap<>();
	          data.put("partition", record.partition());
	          data.put("offset", record.offset());
	          data.put("value", record.value());
	         out.println(this.id + ": " + data.toString());*/
	         //out.println(record.partition() + ": " + new Gson().toJson(record.value(),Notification.class));
	        }
	        System.out.println(new Date() + " : "+ (records.count()));
	      }
	    } catch (WakeupException e) {
	      // ignore for shutdown 
	    } finally {
	      consumer.close();
	    }
	  }

	  public void shutdown() {
	    consumer.wakeup();
	  }

}
