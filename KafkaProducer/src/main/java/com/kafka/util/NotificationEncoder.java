package com.kafka.util;


import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.beans.Notification;

public class NotificationEncoder implements Encoder<Notification>,Serializer<Notification>{
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public NotificationEncoder(){
		
	}
	
	public NotificationEncoder(VerifiableProperties verifiableProperties){
		
	}

	public byte[] toBytes(Notification notification) {
		try {
            return objectMapper.writeValueAsString(notification).getBytes();
        } catch (JsonProcessingException e) {
        	e.printStackTrace();
            //logger.error(String.format("Json processing failed for object: %s", object.getClass().getName()), e);
        }
        return "".getBytes();
	}

	@Override
	public void configure(Map configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] serialize(String topic, Notification data) {
		// TODO Auto-generated method stub
		try {
            return objectMapper.writeValueAsString(data).getBytes();
        } catch (JsonProcessingException e) {
        	e.printStackTrace();
            //logger.error(String.format("Json processing failed for object: %s", object.getClass().getName()), e);
        }
        return "".getBytes();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
