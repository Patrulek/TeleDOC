package com.pp.iwm.teledoc.models;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.windows.ConfWindow;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class ConfWindowModel extends WindowModel {
	
	// ==================================
	// FIELDS
	// ==================================
	
	private ConfWindow conf_window;
	
	public enum UserContext {
		DRAWING_LINE, DRAWING_BROKEN_LINE, GETTING_DISTANCE, ADDING_ANNOTATION, DOING_NOTHING 
	}
	
	public UserContext user_context;
	
	public double chat_max_opacity;
	public boolean is_chat_hiding;
	
	public Point2D temp1;
	public Point2D temp2;
	public DrawableBrokenLine temp_broken_line;
	public Annotation temp_annotation;
	public Color chosen_color;
	
	// ==================================
	// METHODS
	// ==================================
	
	public ConfWindowModel(ConfWindow _window) {
		super(_window);
		conf_window = (ConfWindow)_window;
		
		chat_max_opacity = 1.0;
		is_chat_hiding = false;
		user_context = UserContext.DOING_NOTHING;
		chosen_color = Color.RED;
	}
	
	
}
