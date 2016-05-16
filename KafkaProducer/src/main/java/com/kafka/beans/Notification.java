package com.kafka.beans;

import java.io.Serializable;
import java.util.List;

public class Notification implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7754581920736969488L;
	private String emailId;
	private String itemId;
	private String notificationType;
	private List<String[]> dummy; 
	public Notification(){
		
	}
	public Notification(String emailId, String itemId, String notificationType) {
		super();
		this.emailId = emailId;
		this.itemId = itemId;
		this.notificationType = notificationType;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public List<String[]> getDummy() {
		return dummy;
	}
	public void setDummy(List<String[]> dummy) {
		this.dummy = dummy;
	}
	
	
}
