package com.pp.iwm.teledoc.windows;

import com.pp.iwm.teledoc.layouts.CameraWindowLayout;
import com.pp.iwm.teledoc.models.CameraWindowModel;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CameraWindow extends Window {
	
	// =================================
	// FIELDS
	// =================================
	
	private CameraWindowLayout window_layout;
	private CameraWindowModel window_model;
	
	
	// =================================
	// METHODS
	// =================================
	
	public CameraWindow() {
		super();
		window_layout.setImageViewGraphic(ImageManager.instance().getImage(Utils.IMG_EMPTY_CAMERA));
	}
	
	/* public void setMember(Member _member) {
		window_model.member = _member;
	} */
	
	public void setParentWindow(ConfWindow _window) {
		window_model.parent_window = _window;
	}
	
	private void onWindowBackgroundMousePressed(MouseEvent _ev) {
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent _ev) {
		window_model.setDragged(false);
	}
	
	private void onWindowBackgroundMouseDragged(MouseEvent _ev) {
		Stage stage = window_layout.stage;
		Point2D mouse_pos = window_model.getMousePos();
		
		if( _ev.getSceneY() < 24 || window_model.isDragged()) {
			window_model.setDragged(true);
			stage.setX(stage.getX() + _ev.getScreenX() - mouse_pos.getX());
			stage.setY(stage.getY() + _ev.getScreenY() - mouse_pos.getY());
		}
		
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}
	
	@Override
	protected void createModel() {
		window_model = new CameraWindowModel(this);
		model = window_model;
	}

	@Override
	protected void createLayout() {
		window_layout = new CameraWindowLayout(this);
		layout = window_layout;
	}

	@Override
	protected void initEventHandlers() {
		Rectangle background = window_layout.rect_window_background;
		background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		background.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		window_layout.ibtn_exit.addEventHandler(ActionEvent.ACTION, ev -> hide());
		window_layout.ibtn_minimize.addEventHandler(ActionEvent.ACTION, ev -> onMinimizeButton());
	}
	
	public void onMinimizeButton() {
		if( !window_layout.ibtn_minimize.isOnProperty().get() )
			fold();
		else 
			unfold();
	}
	
	
	private void fold() {
		window_layout.stage.setHeight(32.0);
	}
	
	private void unfold() {
		window_layout.stage.setHeight(514.0);
	}
	@Override
	public void hide() {
		window_model.parent_window.closeCameraWindow();
		super.hide();
	}
}
