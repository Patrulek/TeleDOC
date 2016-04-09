package com.pp.iwm.teledoc.animations;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class TranslateTransitionInfo {
	
	// ===================================
	// FIELDS
	// ===================================
	
	private double x_dist;
	private double y_dist;
	private long in_dur;
	private long out_dur;
	
	private TranslateTransition translate_anim;
	
	// ===================================
	// METHODS
	// ===================================
	
	public TranslateTransitionInfo(TranslateTransition _translate_anim) {
		translate_anim = _translate_anim;
	}
	
	public void play(boolean _in) {
		if( translate_anim != null ) {
			translate_anim.stop();
			
			if( _in ) {
				translate_anim.setToX(x_dist);
				translate_anim.setToY(y_dist);
				translate_anim.setDuration(Duration.millis(in_dur));
			} else {
				translate_anim.setToX(-x_dist);
				translate_anim.setToY(-y_dist);
				translate_anim.setDuration(Duration.millis(out_dur));
			}
			
			translate_anim.play();
		}
	}
	
	public void customize(double _x_dist, double _y_dist, long _in_dur, long _out_dur) {
		x_dist = _x_dist;
		y_dist = _y_dist;
		in_dur = _in_dur;
		out_dur = _out_dur;
	}
}
