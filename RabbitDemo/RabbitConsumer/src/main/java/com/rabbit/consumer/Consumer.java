package com.rabbit.consumer;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class Consumer {
	private final static String QUEUE_NAME = "notification_offer";
	public static void main(String[] args) throws Exception {
		
		   ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("RABBIT01");
		    factory.setUsername("aryan");
		    factory.setPassword("kohls1234");
		    Connection connection = factory.newConnection();
		   final Channel channel = connection.createChannel();
		   
		    System.out.println(" [*] Waiting for messages.");
		    
		    channel.exchangeDeclare("offerExchange", "direct", true);
		    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		    channel.queueBind(QUEUE_NAME, "offerExchange", "notification_offer");
		    int prefetchCount=5000;
		    ExecutorService threadExecutor = Executors.newFixedThreadPool(10);
		    Worker consumerWorker = new Worker(prefetchCount, threadExecutor, 1,
		    		connection.createChannel(), QUEUE_NAME);
		    System.out.println("Number OF records processed : " + consumerWorker.processed + " : " +new Date());
		    /*channel.basicConsume(QUEUE_NAME, autoAck, "myConsumerTag",
		         new DefaultConsumer(channel) {
		             @Override
		             public void handleDelivery(String consumerTag,
		                                        Envelope envelope,
		                                        AMQP.BasicProperties properties,
		                                        byte[] body)
		                 throws IOException
		             {
		                 String routingKey = envelope.getRoutingKey();
		                 String contentType = properties.getContentType();
		                 long deliveryTag = envelope.getDeliveryTag();
		                 // (process the message components here ...)
		                 String message = new String(body, "UTF-8");
				         System.out.println(" [x] Received '" + message + "'" + "Routing Key : "+ routingKey);
		                 channel.basicAck(deliveryTag, false);
		             }
		             @Override
		            public void handleShutdownSignal(String consumerTag,
		            		ShutdownSignalException sig) {
		            	// TODO Auto-generated method stub
		            	 System.out.println("Recieving Ended");
		            	System.out.println("Channel Closed : " + sig.getMessage()+"   "+ new Date(System.currentTimeMillis())); 
		            }
		         });*/
	}
	
	static class Worker extends DefaultConsumer {

	    String name;
	    long sleep;
	    Channel channel;
	    String queue;
	    long processed;
	    ExecutorService executorService;

	    public Worker(int prefetch, ExecutorService threadExecutor,
	                  long s, Channel c, String q) throws Exception {
	        super(c);
	        sleep = s;
	        channel = c;
	        queue = q;
	        channel.basicQos(prefetch);
	        channel.basicConsume(queue, false, this);
	        executorService = threadExecutor;
	    }

	    @Override
	    public void handleDelivery(String consumerTag,
	                               Envelope envelope,
	                               AMQP.BasicProperties properties,
	                               byte[] body) throws IOException {
	        Runnable task = new VariableLengthTask(this,
	                                               envelope.getDeliveryTag(),
	                                               channel, sleep);
	        executorService.submit(task);
	    }
	    
	    @Override
        public void handleShutdownSignal(String consumerTag,
        		ShutdownSignalException sig) {
        	// TODO Auto-generated method stub
        	 System.out.println("Recieving Ended for thread");
        	System.out.println("Channel Closed : " + sig.getMessage()+"   "+ new Date(System.currentTimeMillis())); 
        }
	}
	
	static class VariableLengthTask implements Runnable {

	    long tag;
	    long sleep;
	    Channel chan;
	    Worker worker;

	    VariableLengthTask(Worker w, long t, Channel c, long s) {
	        worker = w;
	        tag = t;
	        chan = c;
	        sleep = s;
	    }

	    public void run() {
	        try {
	            Thread.sleep(sleep);
	        } catch (InterruptedException e) {
	            throw new RuntimeException(e);
	        }

	        if (chan.isOpen()) {
	            try {
	                chan.basicAck(tag, false);
	                worker.processed++;
	            } catch (IOException e) {}
	        }
	    }
	}
}
