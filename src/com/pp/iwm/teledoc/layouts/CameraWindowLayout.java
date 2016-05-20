package com.pp.iwm.teledoc.layouts;

import com.pp.iwm.teledoc.gui.DoubleStateImageButton;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.CameraWindow;
import com.pp.iwm.teledoc.windows.ConfWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

public class CameraWindowLayout extends WindowLayout {
	
	// ================================
	// FIELDS
	// ================================
	
	private CameraWindow camera_window;
	
	public Rectangle rect_window_background; 
	public DoubleStateImageButton ibtn_minimize;
	public ImageButton ibtn_exit;
	public ImageView image_view;
	
	
	// ================================
	// METHODS
	// ================================
	
	public CameraWindowLayout(Window _window) {
		super(_window);
		camera_window = (CameraWindow)_window;
	}

	@Override
	public void create() {
		scene = new Scene(root, 642, 514, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setOnCloseRequest(ev -> { hide(); ev.consume(); });
		stage.setAlwaysOnTop(true);
		
		createBackground();
		createMinimizeButton();
		createExitButton();
		createImageView();
		
		addElementsToScene();
		
		stage.setScene(scene);
	}
	
	private void createBackground() {
		rect_window_background = new Rectangle(640, 512);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setLayoutX(1.0); rect_window_background.setLayoutY(1.0);
		rect_window_background.setStroke(Color.rgb(17, 27, 30));
		rect_window_background.setStrokeWidth(2.0);
	}
	
	private void createMinimizeButton() {
		ibtn_minimize = new DoubleStateImageButton(Utils.IMG_MAXIMIZE, Utils.IMG_MINIMIZE, Utils.HINT_MINIMIZE, Utils.ACT_MINIMIZE);
		ibtn_minimize.relocate(576.0, 6.0);
	}
	
	private void createExitButton() {
		ibtn_exit = new ImageButton(Utils.IMG_EXIT_APP_ICON, Utils.HINT_CLOSE_APP, Utils.ACT_EXIT_APP);
		ibtn_exit.setLayoutX(606.0); ibtn_exit.setLayoutY(6.0);
	}
	
	private void createImageView() {
		image_view = new ImageView();
		image_view.relocate(1.0, 33.0);
		image_view.setFitWidth(640.0);
		image_view.setFitHeight(480.0);
	}
	
	public void setImageViewGraphic(Image _image) {
		image_view.setImage(_image);
	}
	
	private void addElementsToScene() {
		root.getChildren().addAll(rect_window_background,
								ibtn_minimize,
								ibtn_exit,
								image_view);
	}

}
