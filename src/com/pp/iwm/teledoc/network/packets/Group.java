package com.pp.iwm.teledoc.network.packets;

public class Group {
	private String name;
	private String ownerEmail;
	private String ownerName;
	private String ownerSurname;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String email) {
		this.ownerEmail = email;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerSurname() {
		return ownerSurname;
	}
	public void setOwnerSurname(String ownerSurname) {
		this.ownerSurname = ownerSurname;
	}
}
