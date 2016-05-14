package com.pp.iwm.teledoc.network.packets;

public class LoginResponse extends Response {

	// Jeœli id = -1 to nie ma takiego u¿ytkownika w bazie w przeciwnym wypadku zwróci jego id w bazie
	
	private int id;
	private String name;
	private String surname;
	private String email;
	private String position;
		
	public LoginResponse() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}	
}
