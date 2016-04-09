package com.pp.iwm.teledoc.animations;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;

public class ScaleTransitionInfo {
	
	// ===================================
	// FIELDS
	// ===================================
	
	private double in_sc = 1.15;
	private double out_sc = 1.0;
	private long in_dur = 250;
	private long out_dur = 500;
	
	private ScaleTransition zoom_anim;
	
	// ===================================
	// METHODS
	// ===================================
	
	public ScaleTransitionInfo(ScaleTransition _zoom_anim) {
		zoom_anim = _zoom_anim;
	}
	
	public void play(boolean _in) {
		if( zoom_anim != null ) {
			zoom_anim.stop(); 
			
			if( _in ) {
				zoom_anim.setToX(in_sc); zoom_anim.setToY(in_sc);
				zoom_anim.setDuration(Duration.millis(in_dur)); 
			} else {
				zoom_anim.setToX(out_sc); zoom_anim.setToY(out_sc);
				zoom_anim.setDuration(Duration.millis(out_dur)); 
			}
			
			zoom_anim.play();
		}
	}
	
	public void customize(double _zoom_in_scale, double _zoom_out_scale, 
						long _zoom_in_duration, long _zoom_out_duration) {
		in_sc = _zoom_in_scale;
		out_sc = _zoom_out_scale;
		in_dur = _zoom_in_duration;
		out_dur = _zoom_out_duration;
	}
}
