package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import com.pp.iwm.teledoc.gui.Utils;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.objects.User;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Window {
	
	protected Stage stage = null;
	protected Group root = null;
	protected Scene scene = null;
	protected Point mouse_pos = null;
	protected User user = null;
	
	protected boolean is_dragged = false;
	
	public Window() {
		stage = new Stage();
		stage.getIcons().add(ImageManager.instance().getImage(Utils.IMG_APP_ICON));
		root = new Group();
		mouse_pos = new Point(0, 0);
		createStage();
	}
	
	protected abstract void createStage();
	
	protected void openWindow(Window _new_window, boolean _hide_current) {
		_new_window.show();
		
		if( _hide_current ) {
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
