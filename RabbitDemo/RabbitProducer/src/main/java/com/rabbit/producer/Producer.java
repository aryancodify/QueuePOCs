package com.rabbit.producer;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbit.worker.CompositeMessageWorker;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



public class Producer {
	private static final String QUEUE_NAME = "notification_offer";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("RABBIT01");
		connectionFactory.setUsername("aryan");
		connectionFactory.setPassword("kohls1234");
		    Connection connection = connectionFactory.newConnection();
		
		Channel channel = connection.createChannel();
		
		ExecutorService producerExecutorService = Executors.newFixedThreadPool(10);
		/*channel.addReturnListener(new ReturnListener() {
			
			public void handleReturn(int replyCode,
                    String replyText,
                    String exchange,
                    String routingKey,
                    BasicProperties properties,
                    byte[] body) throws IOException {
				// TODO Auto-generated method stub
				System.out.println("Message Failed" + replyText);
			}
		});*/
		
		String message = "{'emailId':'user9535252','itemId':'item9535252','notificationType':'silent',"
				+ "'dummy':[['hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb0','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb0',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb0','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb0',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb0'],['hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb1',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb1','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb1',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb1','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb1'],"
				+ "['hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb2','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb2',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb2','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb2',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb2'],['hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb3',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb3','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb3',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb3','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb3'],"
				+ "['hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb4','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb4',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb4','hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb4',"
				+ "'hcghjcbhjcbhjbcwhjbcwehjcbwehjbcwehjb4']]}";
		System.out.println("Transfer Started : " + new Date(System.currentTimeMillis()));
		Queue<String> queue = new LinkedList<>();
		for(int i=1;i<=5000000;i++){
			queue.add(message);
			if(i%10000==0){
				CompositeMessageWorker<String> compositeMessageWorker = new CompositeMessageWorker<String>(connection, QUEUE_NAME);
				compositeMessageWorker.setQueue(queue);
				producerExecutorService.execute(compositeMessageWorker);
				queue = new LinkedList<String>();
			}
		}
		 // This will make the executor accept no new threads
	    // and finish all existing threads in the queue
		producerExecutorService.shutdown();
		// Wait until all threads are finish
		producerExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		System.out.println("Transfer Ended : " + new Date(System.currentTimeMillis()));
		channel.close();
		connection.close();
	}
}
