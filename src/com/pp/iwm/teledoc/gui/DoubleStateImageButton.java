package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DoubleStateImageButton extends ImageButton {
	
	// =========================================
	// FIELDS
	// =========================================
	
	private int image_key_second;
	private boolean is_on;
	private EventHandler<ActionEvent> ev_handler;
	
	// =========================================
	// METHODS
	// =========================================
	
	public DoubleStateImageButton(int _image_key, int _image_key_second, String _hint, int _action) {
		super(_image_key, _hint, _action);
		
		image_key_second = _image_key_second;
		is_on = true;
		ev_handler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent _ev) {
				onAction();
			}
		};
		
		addAction();
	}
	

	public void removeAction() {
		removeEventHandler(ActionEvent.ACTION, ev_handler);
	}
	
	public void addAction() {
		addEventHandler(ActionEvent.ACTION, ev_handler);
	}
	
	private void onAction() {
		is_on = !is_on;
		changeGraphic();
	}
	
	private void changeGraphic() {
		Image img = is_on ? ImageManager.instance().getImage(image_key) : ImageManager.instance().getImage(image_key_second);
		setGraphic(new ImageView(img));
	}
}
