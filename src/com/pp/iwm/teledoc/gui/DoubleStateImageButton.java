package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DoubleStateImageButton extends ImageButton {
	
	// =========================================
	// FIELDS
	// =========================================
	
	private int image_key_second;
	private BooleanProperty is_on;
	private EventHandler<ActionEvent> ev_handler;
	
	// =========================================
	// METHODS
	// =========================================
	
	public DoubleStateImageButton(int _image_key, int _image_key_second, String _hint, int _action) {
		super(_image_key, _hint, _action);
		
		image_key_second = _image_key_second;
		is_on = new SimpleBooleanProperty(true);
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
	
	public void switchOn() {
		is_on.set(true);
		changeGraphic();
	}
	
	public void switchOff() {
		is_on.set(false);
		changeGraphic();
	}
	
	private void onAction() {
		if( is_on.get() )
			switchOff();
		else
			switchOn();
	}
	
	private void changeGraphic() {
		Image img = is_on.get() ? ImageManager.instance().getImage(image_key) : ImageManager.instance().getImage(image_key_second);
		setGraphic(new ImageView(img));
	}
	
	public BooleanProperty isOnProperty() {
		return is_on;
	}
}
