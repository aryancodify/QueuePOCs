package com.kafka.util;

import java.io.IOException;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import kafka.serializer.Decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.beans.Notification;

public class NotificationDecoder implements Decoder<Notification>,Deserializer<Notification>{
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public Notification fromBytes(byte[] bytes) {
		// TODO Auto-generated method stub
	
			try {
				return objectMapper.readValue(bytes, Notification.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Notification deserialize(String topic, byte[] bytes) {
		// TODO Auto-generated method stub
		try {
			return objectMapper.readValue(bytes, Notification.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
