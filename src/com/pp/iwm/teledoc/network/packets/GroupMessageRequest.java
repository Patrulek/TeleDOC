package com.pp.iwm.teledoc.network.packets;

public class GroupMessageRequest extends Request {
	private String message;

	public GroupMessageRequest() {
		super();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
