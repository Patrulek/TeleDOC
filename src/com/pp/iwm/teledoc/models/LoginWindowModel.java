package com.pp.iwm.teledoc.models;

import com.pp.iwm.teledoc.windows.LoginWindow;

public class LoginWindowModel extends WindowModel {

	// ==================================
	// FIELDS
	// ==================================
	
	private LoginWindow login_window;
		
	// ==================================
	// METHODS
	// ==================================
		
	public LoginWindowModel(LoginWindow _window) {
		super(_window);
		login_window = (LoginWindow) window;
	}
}
