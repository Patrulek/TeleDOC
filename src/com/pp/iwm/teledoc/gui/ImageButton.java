package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeTransitionInfo;
import com.pp.iwm.teledoc.animations.ScaleTransitionInfo;
import com.pp.iwm.teledoc.animations.TranslateTransitionInfo;
import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.util.Duration;

public class ImageButton extends Button {
	
	// =============================================== 
	// FIELDS
	// ===============================================
	
	private ScaleTransition zoom_anim;
	private ScaleTransitionInfo zoom_info;
	
	private FadeTransition fade_anim;
	private FadeTransitionInfo fade_info;
	
	private TranslateTransition translate_anim;
	private TranslateTransitionInfo translate_info;
	
	private boolean zoom_anim_enabled = true;
	private boolean fade_anim_enabled = true;
	private boolean translate_anim_enabled = true;
	
	private String hint = "";
	private int action = -1;
	private int image_key = -1;
	
	// ===============================================
	// METHODS
	// ===============================================
	
	public ImageButton(int _image_key, String _hint, int _action) {
		hint = _hint;
		action = _action;
		image_key = _image_key;
		setGraphic(new ImageView(ImageManager.instance().getImage(image_key)));
		setStyle("-fx-background-color: transparent;");								// TODO: css
		
		zoom_anim = new ScaleTransition(Duration.millis(0), this);
		zoom_info = new ScaleTransitionInfo(zoom_anim);
		
		fade_anim = new FadeTransition(Duration.millis(0), this);
		fade_info = new FadeTransitionInfo(fade_anim);
		
		translate_anim = new TranslateTransition(Duration.millis(0), this);
		translate_info = new TranslateTransitionInfo(translate_anim);
		
		setHoverAnimation(true);
	}
	
	public void setHint(String _hint) {
		hint = _hint;
	}
	
	public void setHoverAnimation(boolean _enable) {
		if( _enable ) {
			setOnMouseEntered(ev -> onMouseEntered(true));
			setOnMouseExited(ev -> onMouseExited(false));
		} else {
			setOnMouseEntered(null);
			setOnMouseExited(null);
		}
	}
	
	public void enableTranslateAnimation(boolean _enabled) {
		translate_anim_enabled = _enabled;
	}
	
	public void enableZoomAnimation(boolean _enabled) {
		zoom_anim_enabled = _enabled;
	}
	
	public void enableFadeAnimation(boolean _enabled) {
		fade_anim_enabled = _enabled;
	}
	
	public void enableAnimations(boolean _enabled) {
		enableFadeAnimation(_enabled);
		enableZoomAnimation(_enabled);
		enableTranslateAnimation(_enabled);
	}
	
	public void customizeTranslateAnimation(double _x_dist, double _y_dist, long _in_dur, long _out_dur) {
		translate_info.customize(_x_dist, _y_dist, _in_dur, _out_dur);
	}
	
	public void customizeZoomAnimation(double _zoom_in_scale, double _zoom_out_scale, 
										long _zoom_in_duration, long _zoom_out_duration) {
		zoom_info.customize(_zoom_in_scale, _zoom_out_scale, _zoom_in_duration, _zoom_out_duration);
	}
	
	public void customizeFadeAnimation(double _fade_in_opacity, double _fade_out_opacity,
										long _fade_in_duration, long _fade_out_duration) {
		fade_info.customize(_fade_in_opacity, _fade_out_opacity, _fade_in_duration, _fade_out_duration);
	}
	
	public void playAnimations(boolean _in) {
		if( zoom_anim_enabled ) 
			zoom_info.play(_in);
		
		if( fade_anim_enabled )
			fade_info.play(_in);
		
		if( translate_anim_enabled )
			translate_info.play(_in);
	}
	
	private void onMouseEntered(boolean _in) {
		playAnimations(_in);
	}
	
	private void onMouseExited(boolean _in) {
		playAnimations(_in);
	}
	
	public int getAction() {
		return action;
	}
	
	public String getHint() {
		return hint;
	}
	
	public String Hint() {
		return hint;
	}
	
	public FadeTransition getFadeAnim() {
		return fade_anim;
	}
}
