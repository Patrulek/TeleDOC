package com.pp.iwm.teledoc.animations;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class TranslateAnimation {
	
	// ===================================
	// FIELDS
	// ===================================
	
	private double x_transition;
	private double y_transition;
	private long start_to_end_duration;
	private long end_to_start_duration;
	private boolean is_enabled = true;
	
	private TranslateTransition translate_anim;
	
	// ===================================
	// METHODS
	// ===================================
	
	public TranslateAnimation(Node _node_to_animate) {
		translate_anim = new TranslateTransition();
		translate_anim.setNode(_node_to_animate);
	}
	
	public void playForward() {
		play(true);
	}
	
	public void playBackward() {
		play(false);
	}
	
	private void play(boolean _is_playing_forward) {
		if( !is_enabled )
			return;
		
		translate_anim.stop();
			
		if( _is_playing_forward ) {
			translate_anim.setToX(x_transition);
			translate_anim.setToY(y_transition);
			translate_anim.setDuration(Duration.millis(start_to_end_duration));
		} else {
			translate_anim.setToX(0.0);
			translate_anim.setToY(0.0);
			translate_anim.setDuration(Duration.millis(end_to_start_duration));
		}
			
		translate_anim.play();
	}
	
	public void customize(double _x_dist, double _y_dist, long _in_dur, long _out_dur) {
		x_transition = _x_dist;
		y_transition = _y_dist;
		start_to_end_duration = _in_dur;
		end_to_start_duration = _out_dur;
	}
	
	public void enable() {
		is_enabled = true;
	}
	
	public void disable() {
		is_enabled = false;
	}
	
	public void setOnFinished(EventHandler<ActionEvent> _ev_handler) {
		translate_anim.setOnFinished(_ev_handler);
	}
}
