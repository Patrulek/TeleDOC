package com.pp.iwm.teledoc.network.packets;

// Zostanie wys�ana u�ytkownik�w gdy podczas trwania konferencji zostanie dodany nowy obrazek
public class NewGroupImageEvent extends Response {
	private int id;
	private String name;
	private String path;
	private String ownerName;
	private String ownerSurname;
	private String ownerEmail;
	
	public NewGroupImageEvent() { }
	
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
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
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
	public String getOwnerEmail() {
		return ownerEmail;
	}
	
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
}
