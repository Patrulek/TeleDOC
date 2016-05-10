package com.pp.iwm.teledoc.animations;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public class FadeAnimation {
	
	// ===================================
	// FIELDS
	// ===================================
	
	private double start_opacity = 1.0;
	private double end_opacity = 1.0;
	private long start_to_end_duration = 250;
	private long end_to_start_duration = 500;
	private boolean is_enabled = true;
	
	private FadeTransition fade_anim;
	
	// ====================================
	// METHODS
	// ====================================
	
	public FadeAnimation(Node _node_to_animate) {
		fade_anim = new FadeTransition();
		fade_anim.setNode(_node_to_animate);
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
		
		fade_anim.stop();
		
		if( _is_playing_forward ) {
			fade_anim.setToValue(start_opacity);
			fade_anim.setDuration(Duration.millis(start_to_end_duration));
		} else {
			fade_anim.setToValue(end_opacity);
			fade_anim.setDuration(Duration.millis(end_to_start_duration));
		}
		
		fade_anim.play();
	}
	
	public void customize(double _start_opacity, double _end_opacity,
						long _start_to_end_duration, long _end_to_start_duration) {
		start_opacity = _start_opacity;
		end_opacity = _end_opacity;
		start_to_end_duration = _start_to_end_duration;
		end_to_start_duration = _end_to_start_duration;
	}

	public void enable() {
		is_enabled = true;
	}
	
	public void disable() {
		is_enabled = false;
	}
	
	public void setOnFinished(EventHandler<ActionEvent> _ev_handler) {
		fade_anim.setOnFinished(_ev_handler);
	}
}
