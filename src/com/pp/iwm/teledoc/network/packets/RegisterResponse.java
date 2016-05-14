package com.pp.iwm.teledoc.network.packets;

public class RegisterResponse extends Response {
	
	public RegisterResponse() {
		super();
	}
	
	public RegisterResponse(boolean answer, String message) {
		super();
		setAnswer(answer);
		setMessage(message);
	}
	
}
