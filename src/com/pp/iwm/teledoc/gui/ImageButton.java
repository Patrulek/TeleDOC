package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.util.Duration;

public class ImageButton extends Button {
	
	public Integer image_key;
	public ScaleTransition zoom_anim;
	double zoom_in_scale = 1.15;
	double zoom_out_scale = 1.0;
	long zoom_in_duration = 250;
	long zoom_out_duration = 500;
	
	public FadeTransition fade_anim;
	double fade_in_opacity = 1.0;
	double fade_out_opacity = 1.0;
	long fade_in_duration = 250;
	long fade_out_duration = 500;
	
	boolean zoom_anim_enabled = true;
	boolean fade_anim_enabled = true;
	
	public String hint = "";
	public Integer action = -1;
	
	public ImageButton(Integer image_key, String hint, Integer action) {
		this.hint = hint;
		this.action = action;
		this.image_key = image_key;
		this.setGraphic(new ImageView(ImageManager.instance().getImage(image_key)));
		this.setStyle("-fx-background-color: transparent;");
		
		this.zoom_anim = new ScaleTransition(Duration.millis(zoom_in_duration), this);
		this.zoom_anim.setToX(zoom_in_scale); this.zoom_anim.setToY(zoom_in_scale);
		
		this.fade_anim = new FadeTransition(Duration.millis(fade_in_duration), this);
		this.fade_anim.setToValue(fade_in_opacity);
		
		this.setHoverAnimation();
	}
	
	private void setHoverAnimation() {
		this.setOnMouseEntered(event -> onMouseEntered());
		this.setOnMouseExited(event -> onMouseExited());
	}
	
	public void enableZoomAnimation(boolean enabled) {
		zoom_anim_enabled = enabled;
	}
	
	public void enableFadeAnimation(boolean enabled) {
		fade_anim_enabled = enabled;
	}
	
	public void enableAnimations(boolean enabled) {
		enableFadeAnimation(enabled);
		enableZoomAnimation(enabled);
	}
	
	public void customizeZoomAnimation(double set_zoom_in_scale, double set_zoom_out_scale, 
										long set_zoom_in_duration, long set_zoom_out_duration) {
		this.zoom_in_scale = set_zoom_in_scale;
		this.zoom_out_scale = set_zoom_out_scale;
		this.zoom_in_duration = set_zoom_in_duration;
		this.zoom_out_duration = set_zoom_out_duration;
	}
	
	public void customizeFadeAnimation(double set_fade_in_opacity, double set_fade_out_opacity,
										long set_fade_in_duration, long set_fade_out_duration) {
		this.fade_in_opacity = set_fade_in_opacity;
		this.fade_out_opacity = set_fade_out_opacity;
		this.fade_in_duration = set_fade_in_duration;
		this.fade_out_duration = set_fade_out_duration;
	}
	
	protected void onMouseEntered() {
		if( zoom_anim_enabled ) {
			zoom_anim.stop(); 
			zoom_anim.setToX(zoom_in_scale);
			zoom_anim.setToY(zoom_in_scale);
			zoom_anim.setDuration(Duration.millis(zoom_in_duration));
			zoom_anim.play();
		}
		
		if( fade_anim_enabled ) {
			fade_anim.stop();
			fade_anim.setToValue(fade_in_opacity);
			fade_anim.setDuration(Duration.millis(fade_in_duration));
			fade_anim.play();
		}
	}
	
	protected void onMouseExited() {
		if( zoom_anim_enabled ) {
			zoom_anim.stop(); 
			zoom_anim.setToX(zoom_out_scale);
			zoom_anim.setToY(zoom_out_scale);
			zoom_anim.setDuration(Duration.millis(zoom_out_duration));
			zoom_anim.play();
		}
		
		if( fade_anim_enabled ) {
			fade_anim.stop();
			fade_anim.setToValue(fade_out_opacity);
			fade_anim.setDuration(Duration.millis(fade_out_duration));
			fade_anim.play();
		}
	}
}
