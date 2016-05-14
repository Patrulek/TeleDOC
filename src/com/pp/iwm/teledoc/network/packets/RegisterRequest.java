package com.pp.iwm.teledoc.network.packets;

public class RegisterRequest extends Request {
	private String name;
	private String surname;
	private String email;
	private String password;
	private String position;
	
	public RegisterRequest() {
		
	}

	public RegisterRequest(String name, String surname, String email, String password, String position) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.position = position;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
	
}
