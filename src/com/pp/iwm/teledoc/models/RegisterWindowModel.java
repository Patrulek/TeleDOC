package com.pp.iwm.teledoc.models;

import com.pp.iwm.teledoc.windows.RegisterWindow;
import com.pp.iwm.teledoc.windows.Window;

public class RegisterWindowModel extends WindowModel {
	
	// ==========================================
	// FIELDS
	// ==========================================
	
	private RegisterWindow register_window;
	
	// ==========================================
	// METHODS
	// ==========================================
	
	
	public RegisterWindowModel(Window _window) {
		super(_window);
		register_window = (RegisterWindow) window;
	}
}
