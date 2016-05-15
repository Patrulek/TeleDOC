package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.animations.ScaleAnimation;
import com.pp.iwm.teledoc.animations.TranslateAnimation;
import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.util.Duration;

public class ImageButton extends Button {
	
	// =============================================== 
	// FIELDS
	// ===============================================
	
	protected ScaleAnimation scale_animation;
	protected FadeAnimation fade_animation;
	protected TranslateAnimation translate_animation;
	
	protected String hint;
	protected int action;
	protected int image_key;
	
	// ===============================================
	// METHODS
	// ===============================================
	
	public ImageButton(int _image_key, String _hint, int _action) {
		hint = _hint;
		action = _action;
		image_key = _image_key;
		
		setGraphic(new ImageView(ImageManager.instance().getImage(image_key)));
		setStyle("-fx-background-color: transparent;");								// TODO: css
		
		scale_animation = new ScaleAnimation(this);
		fade_animation = new FadeAnimation(this);
		translate_animation = new TranslateAnimation(this);
		
		setHoverAnimation(true);
	}
	
	public void changeButton(int _image_key, String _hint, int _action) {
		image_key = _image_key;
		hint = _hint;
		action = _action;
		
		setGraphic(new ImageView(ImageManager.instance().getImage(image_key)));
	}
	
	public void addListenerForHoverProperty(ChangeListener<? super Boolean> _listener) {
		hoverProperty().addListener(_listener);
	}
	
	public void removeListenerForHoverProperty(ChangeListener<? super Boolean> _listener) {
		hoverProperty().removeListener(_listener);
	}
	
	public void setHint(String _hint) {
		hint = _hint;
	}
	
	public void setHoverAnimation(boolean _enable) {
		if( _enable ) {
			setOnMouseEntered(ev -> onMouseEntered());
			setOnMouseExited(ev -> onMouseExited());
		} else {
			setOnMouseEntered(null);
			setOnMouseExited(null);
		}
	}
	
	public void enableTranslateAnimation() {
		translate_animation.enable();
	}
	
	public void disableTranslateAnimation() {
		translate_animation.disable();
	}
	
	public void enableScaleAnimation() {
		scale_animation.enable();
	}
	
	public void disableScaleAnimation() {
		scale_animation.disable();
	}
	
	public void enableFadeAnimation() {
		fade_animation.enable();
	}
	
	public void disableFadeAnimation() {
		fade_animation.disable();
	}
	
	public void enableAnimations() {
		enableFadeAnimation();
		enableScaleAnimation();
		enableTranslateAnimation();
	}
	
	public void disableAnimations() {
		disableFadeAnimation();
		disableScaleAnimation();
		disableTranslateAnimation();
	}
	
	public void customizeTranslateAnimation(double _x_dist, double _y_dist, long _in_dur, long _out_dur) {
		translate_animation.customize(_x_dist, _y_dist, _in_dur, _out_dur);
	}
	
	public void customizeZoomAnimation(double _zoom_in_scale, double _zoom_out_scale, 
										long _zoom_in_duration, long _zoom_out_duration) {
		scale_animation.customize(_zoom_in_scale, _zoom_out_scale, _zoom_in_duration, _zoom_out_duration);
	}
	
	public void customizeFadeAnimation(double _fade_in_opacity, double _fade_out_opacity,
										long _fade_in_duration, long _fade_out_duration) {
		fade_animation.customize(_fade_in_opacity, _fade_out_opacity, _fade_in_duration, _fade_out_duration);
	}
	
	public void playForwardAnimations() {
		scale_animation.playForward();
		fade_animation.playForward();
		translate_animation.playForward();
	}
	
	public void playBackwardAnimations() {
		scale_animation.playBackward();
		fade_animation.playBackward();
		translate_animation.playBackward();
	}
	
	private void onMouseEntered() {
		playForwardAnimations();
	}
	
	private void onMouseExited() {
		playBackwardAnimations();
	}
	
	public int getAction() {
		return action;
	}
	
	public String getHint() {
		return hint;
	}
	
	public FadeAnimation getFadeAnimation() {
		return fade_animation;
	}
}
