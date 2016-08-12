package com.kafka.producer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.google.common.io.Resources;
import com.kafka.beans.Notification;

public class Producer /*implements Runnable*/{
	//private List<Notification> notifications = new ArrayList<Notification>();
	private KafkaProducer<String, Notification> producer;
	public Producer(){
		
	}
	/*public Producer(List<Notification> notifications) {
		super();
		this.notifications = notifications;
		init();
	}*/
	public void init(){
		try (InputStream props = new FileInputStream("D:\\Kohls Softwares\\producer.props")) {
		Properties properties = new Properties();
		properties.load(props);
		producer = new KafkaProducer<String,Notification>(properties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*@Override
	public void run() {
		for(Notification notification:notifications){
			producer.send(new ProducerRecord<String, Notification>("best", notification));
		}
		
	}*/
	public void sendNotification(Notification notification) throws InterruptedException, ExecutionException{
		RecordMetadata recordMetadata = producer.send(new ProducerRecord<String, Notification>("testNotif", notification)).get();
		 /*System.out.println("topic where message is published : " + recordMetadata.topic());
	      System.out.println("partition where message is published : " + recordMetadata.partition());
	      System.out.println("message offset # : " + recordMetadata.offset());*/
	}
}
