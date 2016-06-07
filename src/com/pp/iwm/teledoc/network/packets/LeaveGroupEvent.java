package com.pp.iwm.teledoc.network.packets;

public class LeaveGroupEvent extends Response {
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
