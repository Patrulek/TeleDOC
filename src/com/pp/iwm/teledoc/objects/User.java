package com.pp.iwm.teledoc.objects;

public class User {

	private String name;
	private String surname;
	private String email;
	
	// private List<Conference> last_conferences;
	// private List<Conference> current_conferences;
	// private FileTree file_tree;
	// private List<Image> used_images;
	
	public User(String _name, String _surname, String _email) {
		name = _name;
		surname = _surname;
		email = _email;
		
		/*
		 *  
		 */
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getEmail() {
		return email;
	}
}
