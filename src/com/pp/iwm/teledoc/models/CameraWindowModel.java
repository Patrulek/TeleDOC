package com.pp.iwm.teledoc.models;

import com.pp.iwm.teledoc.windows.CameraWindow;
import com.pp.iwm.teledoc.windows.ConfWindow;
import com.pp.iwm.teledoc.windows.Window;

public class CameraWindowModel extends WindowModel {
	
	// ==============================
	// FIELDS
	// ==============================
	
	private CameraWindow camera_window;

	public ConfWindow parent_window;
	// public Member member;
	
	// ==============================
	// METHODS
	// ==============================
	
	public CameraWindowModel(Window _window) {
		super(_window);
		camera_window = (CameraWindow)_window;
	}
}
