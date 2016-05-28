package com.pp.iwm.teledoc.network.packets;

// TODO Napisaæ szyforwanie has³a aby nie lecia³o przez sieæ jako czysty tekst
// TODO Usunaæ login zostaje tylko email z Request

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
