package com.pp.iwm.teledoc.layouts;

import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.Window;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class WindowLayout {
	
	// ================================
	// FIELDS 
	// ================================
	
	public Stage stage;
	public Group root;
	public Scene scene;
	protected Window window;
	
	// ================================
	// METHODS
	// ================================
	
	public WindowLayout(Window _window) {
		stage = new Stage();
		stage.getIcons().add(ImageManager.instance().getImage(Utils.IMG_APP_ICON));
		root = new Group();
		window = _window;
		create();
	}
	
	public abstract void create();
	
	public void show() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
}
