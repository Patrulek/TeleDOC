package com.pp.iwm.teledoc.network.packets;

public class Response {
	
	private boolean answer;
	private String message;
	
	public Response() {
		super();
	}

	public Response(boolean answer, String message) {
		super();
		this.answer = answer;
		this.message = message;
	}
	
	public Response(boolean answer) {
		this.answer = answer;
		this.message = null;
	}

	public boolean getAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
