package com.sqs.consumer;

import java.util.List;

import com.amazonaws.services.sqs.model.Message;
import com.sqs.util.AWSSimpleQueueServiceUtil;

public class SQSConsumer {
	private AWSSimpleQueueServiceUtil awsSimpleQueueServiceUtil = AWSSimpleQueueServiceUtil.getInstance();
	 private String queueName = awsSimpleQueueServiceUtil.getQueueName();
	 private String queueUrl = awsSimpleQueueServiceUtil.getQueueUrl(queueName);
	 public void consume(){
		 List<Message> messages = awsSimpleQueueServiceUtil.getMessagesFromQueue(queueUrl);
		 for(Message message : messages){
			 System.out.println(message.getBody());
		 }
	 }
	 public static void main(String[] args) {
		SQSConsumer sqsConsumer = new SQSConsumer();
		while(true)
			sqsConsumer.consume();
	}
}
