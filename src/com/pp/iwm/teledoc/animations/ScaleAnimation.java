package com.pp.iwm.teledoc.animations;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleAnimation {
	
	// ===================================
	// FIELDS
	// ===================================
	
	private double start_scale = 1.15;
	private double end_scale = 1.0;
	private long start_to_end_duration = 250;
	private long end_to_start_duration = 500;
	private boolean is_enabled = true;
	
	private ScaleTransition scale_anim;
	
	// ===================================
	// METHODS
	// ===================================
	
	public ScaleAnimation(Node _node_to_animate) {
		scale_anim = new ScaleTransition();
		scale_anim.setNode(_node_to_animate);
	}
	
	public void stop() {
		scale_anim.stop();
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
		
		scale_anim.stop();
		
		if( _is_playing_forward ) {
			scale_anim.setToX(start_scale); scale_anim.setToY(start_scale);
			scale_anim.setDuration(Duration.millis(start_to_end_duration));
		} else {
			scale_anim.setToX(end_scale); scale_anim.setToY(end_scale);
			scale_anim.setDuration(Duration.millis(end_to_start_duration));
		}
		
		scale_anim.play();
	}
	
	public void customize(double _zoom_in_scale, double _zoom_out_scale, 
						long _zoom_in_duration, long _zoom_out_duration) {
		start_scale = _zoom_in_scale;
		end_scale = _zoom_out_scale;
		start_to_end_duration = _zoom_in_duration;
		end_to_start_duration = _zoom_out_duration;
	}
	
	public void enable() {
		is_enabled = true;
	}
	
	public void disable() {
		is_enabled = false;
	}
	
	public void setOnFinished(EventHandler<ActionEvent> _ev_handler) {
		scale_anim.setOnFinished(_ev_handler);
	}
}
