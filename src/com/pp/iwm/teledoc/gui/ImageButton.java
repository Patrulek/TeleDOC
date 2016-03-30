package com.pp.iwm.teledoc.gui;

import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ImageButton extends Button {
	
	public SimpleStringProperty image_url;
	public ScaleTransition zoom_in;
	public ScaleTransition zoom_out;
	
	public ImageButton(String image_url) {
		this.image_url = new SimpleStringProperty();
		this.image_url.set(image_url);
		this.setGraphic(new ImageView(new Image(image_url)));
		this.setBackground(null);
		
		this.zoom_in = new ScaleTransition(Duration.millis(250), this);
		this.zoom_in.setToX(1.15); this.zoom_in.setToY(1.15);
		this.zoom_out = new ScaleTransition(Duration.millis(500), this);
		this.zoom_out.setToX(1.0); this.zoom_out.setToY(1.0);
		
		this.setHoverAnimation();
	}
	
	public void setHoverAnimation() {
		this.setOnMouseEntered(event -> {zoom_out.stop(); zoom_in.play();});
		this.setOnMouseExited(event -> {zoom_in.stop(); zoom_out.play();});
	}
	
	public void removeHoverAnimation() {
		this.setOnMouseEntered(event -> {;});
		this.setOnMouseExited(event -> {;});
	}
}
