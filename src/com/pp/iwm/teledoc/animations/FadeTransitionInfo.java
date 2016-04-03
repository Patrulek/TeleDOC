package com.pp.iwm.teledoc.animations;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class FadeTransitionInfo {
	private double in_op = 1.0;
	private double out_op = 1.0;
	private long in_dur = 250;
	private long out_dur = 500;
	
	private FadeTransition fade_anim;
	
	public FadeTransitionInfo(FadeTransition _fade_anim) {
		fade_anim = _fade_anim;
	}
	
	public void play(boolean _in) {
		if( fade_anim != null ) {
			fade_anim.stop();
			
			if( _in ) {
				fade_anim.setToValue(in_op);
				fade_anim.setDuration(Duration.millis(in_dur));
			} else {
				fade_anim.setToValue(out_op);
				fade_anim.setDuration(Duration.millis(out_dur));
			}
			
			fade_anim.play();
		}
	}
	
	public void customize(double _fade_in_opacity, double _fade_out_opacity,
						long _fade_in_duration, long _fade_out_duration) {
		in_op = _fade_in_opacity;
		out_op = _fade_out_opacity;
		in_dur = _fade_in_duration;
		out_dur = _fade_out_duration;
	}
}
