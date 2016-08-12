package com.rabbit.worker;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ReturnListener;

public class CompositeMessageWorker<T> implements Runnable{

	private Connection connection;
	private String rabbitQueueName;
	public CompositeMessageWorker(Connection connection, String rabbitQueueName) {
		this.connection = connection;
		this.rabbitQueueName = rabbitQueueName;
	}
	private Queue<T> queue = new LinkedList<>();
	
	public Queue<T> getQueue() {
		return queue;
	}
	public void setQueue(Queue<T> queue) {
		this.queue = queue;
	}
	public void add(T message){
		queue.add(message);
	}
	public void run() {
		try {
			Channel channel = connection.createChannel();
			channel.addReturnListener(new ReturnListener() {
				
				public void handleReturn(int replyCode,
	                    String replyText,
	                    String exchange,
	                    String routingKey,
	                    BasicProperties properties,
	                    byte[] body) throws IOException {
					// TODO Auto-generated method stub
					System.out.println("Message Failed" + replyText);
				}
			});
			for(T message: queue){
				channel.basicPublish("offerExchange", this.rabbitQueueName, MessageProperties.PERSISTENT_TEXT_PLAIN, ((String) message).getBytes());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
