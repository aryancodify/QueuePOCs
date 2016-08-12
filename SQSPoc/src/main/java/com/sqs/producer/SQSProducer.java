package com.sqs.producer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.autoscaling.AmazonAutoScalingAsyncClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.BatchResultErrorEntry;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageBatchResult;
import com.sqs.util.AWSSimpleQueueServiceUtil;

public class SQSProducer {
	
 private AWSSimpleQueueServiceUtil awsSimpleQueueServiceUtil = AWSSimpleQueueServiceUtil.getInstance();
 private String queueName = awsSimpleQueueServiceUtil.getQueueName();
 private String queueUrl = awsSimpleQueueServiceUtil.getQueueUrl(queueName);
 private AmazonSQS amazonSQS = awsSimpleQueueServiceUtil.getAWSSQSClient();
 public void produce(){
	// amazonSQS = new AmazonAutoScalingAsyncClient(awsCredentials)
	 SendMessageBatchResult sendMessageBatchResult = new SendMessageBatchResult();
	 SendMessageBatchRequest sendMessageBatchRequest = new SendMessageBatchRequest(queueUrl);
	 List<SendMessageBatchRequestEntry> entries = new ArrayList<SendMessageBatchRequestEntry>();
	 for(int i=1;i<=1;i++){
		 SendMessageBatchRequestEntry messageBatchRequestEntry = new SendMessageBatchRequestEntry(""+i, "Hi this is aryan" + i + " from Noida");
		 entries.add(messageBatchRequestEntry);
	 }
	 sendMessageBatchRequest.setEntries(entries);
	 sendMessageBatchResult = amazonSQS.sendMessageBatch(sendMessageBatchRequest);
	 System.out.println("Failed entries : ");
	 for(BatchResultErrorEntry errorEntry :sendMessageBatchResult.getFailed()){
		 System.out.println(errorEntry.getId());
	 }
	// awsSimpleQueueServiceUtil.sendMessageToQueue(queueUrl, "Hi this is aryan" + i + " from Noida");
 }
 public static void main(String[] args) {
	 
	 SQSProducer sqsProducer = new SQSProducer();
	 sqsProducer.produce();
	 
}
}
