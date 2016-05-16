package com.kafka.util;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.beans.Notification;

public class NotificationEncoder implements Encoder<Notification>{
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	
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

}
