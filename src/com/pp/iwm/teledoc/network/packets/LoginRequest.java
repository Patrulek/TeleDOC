package com.pp.iwm.teledoc.network.packets;

// TODO Napisa� szyforwanie has�a aby nie lecia�o przez sie� jako czysty tekst
// TODO Usuna� login zostaje tylko email z Request

public class LoginRequest extends Request {
	private String password;

	public LoginRequest() {}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
