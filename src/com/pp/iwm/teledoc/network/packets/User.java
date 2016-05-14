package com.pp.iwm.teledoc.network.packets;

public class User {
	private String name;
	private String surname;
	private String email;
	private String password;
	
	public User() {
		super();
	}

	public User(String login, String password) {
		super();
		this.email = login;
		this.password = password;
	}
	
	public User(String name, String surname, String login, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = login;
		this.password = password;
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
	public void setEmail(String login) {
		this.email = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
