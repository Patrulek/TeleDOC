package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import javafx.stage.Stage;

public abstract class Window {
	
	protected Stage stage;
	protected Point mouse_pos;
	
	public Window() {
		stage = new Stage();
		mouse_pos = new Point(0, 0);
		createStage();
		show();
	}
	
	protected abstract void createStage();
	
	protected void openWindow(Window new_window, boolean hide_current) {
		new_window.show();
		this.hide();
	}
	
	protected void show() {
		stage.show();
	}
	
	protected void hide() {
		stage.hide();
	}
}
