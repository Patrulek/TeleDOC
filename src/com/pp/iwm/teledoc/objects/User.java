package com.pp.iwm.teledoc.objects;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.windows.Window;
import com.pp.iwm.teledoc.windows.AppWindow;

public class User {
	
	// =======================================
	// FIELDS
	// =======================================

	private String name;
	private String surname;
	private String email;
	private Window window;
	
	private List<Conference> closed_conferences;
	private List<Conference> open_conferences;
	private FileTree file_tree;
	// private List<Image> used_images;
	
	// =======================================
	// METHODS
	// =======================================
	
	public User(String _name, String _surname, String _email, Window _window) {
		name = _name;
		surname = _surname;
		email = _email;
		window = _window;
		
		closed_conferences = new ArrayList<>();
		open_conferences = new ArrayList<>();
		file_tree = new FileTree();
		/*
		 *  
		 */
	}
	
	public User() {
		
	}
	
	public void setWindow(Window _window) {
		window = _window;
	}
	
	public void loadDataFromDB() {
		loadConferencesFromDB();
		loadFileTreeFromDB();
	}
	
	private void loadConferencesFromDB() {
		for( int i = 0; i < 30; i++ ) {
			Conference c = null;
			
			if( i % 3 != 0 ) {
				c = new Conference("Konferencja nr " + (i + 1), "desc" + i, null, this, true);
				open_conferences.add(c);
			} else {
				c = new Conference("Konferencja nr " + (i + 1), "desc" + i, null, this, false);
				closed_conferences.add(c);
			}
			
			((AppWindow)window).addConf(c);
		}
	}
	
	// updateConferences()
	// removeNotExistingConferences()
	// addNewConferences()
	
	private void loadFileTreeFromDB() {
		for( int i = 0; i < 30; i++ ) {
			file_tree.addFile("root/folder" + i + "/");
			file_tree.addFile("root/image" + i + ".png");
			file_tree.addFile("root/folder/image" + i + ".png");
			file_tree.addFile("root/arrow.hdtv.720p/s04e17" + i + ".avi");
			file_tree.addFile("root/_d_u_p_a_" + i + "/png" + i + ".png");
		}
		
		((AppWindow)window).setFileExplorerRoot(file_tree);
		((AppWindow)window).refreshFileExplorerView();
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
