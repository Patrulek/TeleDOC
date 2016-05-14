package com.pp.iwm.teledoc.models;

import com.pp.iwm.teledoc.windows.Window;

import javafx.geometry.Point2D;

public class WindowModel {
	
	// ===========================
	// FIELDS
	// ===========================
	
	protected Point2D mouse_pos;
	protected boolean is_dragged;
	protected Window window;
	
	// =============================
	// METHODS 
	// =============================
	
	public WindowModel(Window _window) {
		mouse_pos = new Point2D(0.0, 0.0);
		is_dragged = false;
		window = _window;
	}
	
	public Point2D getMousePos() {
		return mouse_pos;
	}
	
	public void setMousePos(Point2D _mouse_pos) {
		mouse_pos = _mouse_pos;
	}
	
	public boolean isDragged() {
		return is_dragged;
	}
	
	public void setDragged(boolean _is_dragged) {
		is_dragged = _is_dragged;
	}
}
