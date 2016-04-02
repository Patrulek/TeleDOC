package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Window {
	
	protected Stage stage = null;
	protected Group root = null;
	protected Scene scene = null;
	protected Point mouse_pos = null;
	
	protected boolean is_dragged = false;
	
	public Window() {
		stage = new Stage();
		root = new Group();
		mouse_pos = new Point(0, 0);
		createStage();
	}
	
	protected abstract void createStage();
	
	protected void openWindow(Window new_window, boolean hide_current) {
		new_window.show();
		
		if( hide_current ) {
			root.getChildren().clear();
			hide();
		}
	}
	
	public void show() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
}
