package com.pp.iwm.teledoc.objects;

import java.util.Date;

public class ChatMessage {			// TODO struktura a nie obiekt
	
	// ==============================================
	// FIELDS
	// ==============================================
	
	private Date time;
	private String username;
	private String message;
	private boolean is_my_message;
	
	// ==============================================
	// METHODS
	// ==============================================
	
	public ChatMessage(Date _time, String _username, String _message, boolean _is_my_message) {
		time = _time;
		username = _username;
		message = _message;
		is_my_message = _is_my_message;
	}
	
	public boolean isMyMessage() {
		return is_my_message;
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
