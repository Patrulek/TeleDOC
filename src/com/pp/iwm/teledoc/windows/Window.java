package com.pp.iwm.teledoc.windows;

import com.pp.iwm.teledoc.layouts.WindowLayout;
import com.pp.iwm.teledoc.models.WindowModel;

import javafx.application.Platform;
import javafx.scene.Group;

public abstract class Window {
	
	// ===================================
	// FIELDS
	// ===================================
	
	protected WindowLayout layout;
	protected WindowModel model;
	
	// ===================================
	// METHODS
	// ===================================
	
	public Window() {
		createModel();
		createLayout();
		initEventHandlers();
	}
	
	protected abstract void createModel();
	protected abstract void createLayout();
	protected abstract void initEventHandlers();
	protected abstract void onClose();
	
	public void show() {
		layout.show();
	}
	
	public void hide() {
		layout.hide();
	}
	
	public void relocate(double _x, double _y) {
		layout.stage.setX(_x);
		layout.stage.setY(_y);
	}
	
	protected void openWindow(Window _new_window) {
		_new_window.show();
	}
	
	protected void openWindowAndHideCurrent(Window _new_window) {
		Group root = layout.root;
		_new_window.show();
		root.getChildren().clear();
		hide();
	}
}
