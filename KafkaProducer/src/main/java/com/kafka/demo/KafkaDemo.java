package com.kafka.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.kafka.beans.Notification;
import com.kafka.producer.Producer;

public class KafkaDemo {
public static void main(String[] args) throws InterruptedException, ExecutionException {
	/*ExecutorService executorService = Executors.newFixedThreadPool(10);
	List<Notification> notifications = new  ArrayList<Notification>();*/
	System.setProperty("java.security.auth.login.config", "D:\\Kohls Softwares" + File.separator + "jaas.conf");
	List<String[]> dummyList = new ArrayList<String[]>(0);
	
	for(int i=0;i<5;i++){
		String[] abc = new String[5];
		for(int j=0;j<5;j++)
		abc[j] = "hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb"+i;
		dummyList.add(abc);
	}
	Notification notification;
	Producer producer = new Producer();
	producer.init();
	System.out.println("start: "+new Date());
	for(int i=1;i<=1;i++){
		notification = new Notification("user"+i, "item"+i, "silent");
		notification.setDummy(dummyList);
		producer.sendNotification(notification);
		//notifications.add(notification);
		
		/*if(i%1000==0){
			Producer producer = new Producer(notifications);
			executorService.submit(producer);
			notifications.clear();
		}*/
	}
	System.out.println("end: "+new Date());
}
}
