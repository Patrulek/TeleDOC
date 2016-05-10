package com.pp.iwm.teledoc.objects;

import java.util.Date;

public class ChatMessage {			// TODO struktura a nie obiekt
	
	// ==============================================
	// FIELDS
	// ==============================================
	
	private Date time;
	private String username;
	private String message;
	
	
	// ==============================================
	// METHODS
	// ==============================================
	
	public ChatMessage(Date _time, String _username, String _message) {
		time = _time;
		username = _username;
		message = _message;
	}
	
	public Date getTime() {
		return time;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getMessage() {
		return message;
	}
}
