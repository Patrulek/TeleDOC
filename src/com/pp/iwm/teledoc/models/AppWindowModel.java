package com.pp.iwm.teledoc.models;

import com.pp.iwm.teledoc.windows.AppWindow;
import com.pp.iwm.teledoc.windows.Window;

public class AppWindowModel extends WindowModel {
	
	// ======================================
	// FIELDS
	// ======================================
	
	private AppWindow app_window;
	public boolean is_opening_conf_window;
	
	// ======================================
	// METHODS
	// ======================================
	
	
	public AppWindowModel(Window _window) {
		super(_window);
		app_window = (AppWindow) window;
		is_opening_conf_window = false;
	}
}
