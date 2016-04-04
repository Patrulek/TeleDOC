package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import com.pp.iwm.teledoc.gui.Utils;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.objects.User;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Window {
	
	// ===================================
	// FIELDS
	// ===================================
	
	protected Stage stage;
	protected Group root;
	protected Scene scene;
	protected Point mouse_pos;
	protected User user = null;
	
	protected boolean is_dragged = false;
	
	// ===================================
	// METHODS
	// ===================================
	
	public Window() {
		stage = new Stage();
		stage.getIcons().add(ImageManager.instance().getImage(Utils.IMG_APP_ICON));
		root = new Group();
		mouse_pos = new Point(0, 0);
		createStage();
	}
	
	public void show() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
	
	protected void openWindow(Window _new_window, boolean _hide_current) {
		_new_window.show();
		
		if( _hide_current ) {
			root.getChildren().clear();
			hide();
		}
	}
	
	protected abstract void createStage();
}
