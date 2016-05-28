package com.pp.iwm.teledoc.objects;

public class Member {
	
	// ==============================
	// FIELDS
	// ==============================
	
	public String name;
	public String surname;
	public String email;
	public boolean is_sending_pointer;
	public boolean is_sending_camera;
	public boolean is_sending_voice;
	
	// ==============================
	// METHODS
	// ==============================
	
	public Member(String _name, String _surname, String _email) {
		name = _name;
		surname = _surname;
		email = _email;
		is_sending_pointer = is_sending_camera = is_sending_voice = false;
	}
}
