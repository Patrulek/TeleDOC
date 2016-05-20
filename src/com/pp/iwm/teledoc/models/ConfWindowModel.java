package com.pp.iwm.teledoc.models;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.drawables.DrawableObject;
import com.pp.iwm.teledoc.windows.CameraWindow;
import com.pp.iwm.teledoc.windows.ConfWindow;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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
	
	public Point2D image_mouse_pos;		// mapowanie pozycji kursora na pozycjê piksela w obrazie na canvasie
	public Point2D temp1;
	public Point2D temp2;
	public DrawableLine temp_line;
	public DrawableBrokenLine temp_broken_line;
	public Annotation temp_annotation;
	public Color chosen_color;
	
	public CameraWindow camera_window;
	
	public List<DrawableObject> drawables;
	public List<Node> line_layer;
	public List<Node> marker_layer;
	public List<Node> annotation_layer;
	//public List<DrawableObject> line_layer;
	//public List<DrawableObject> marker_layer;
	//public List<DrawableObject> annotation_layer;
	public DrawableObject selected_drawable;
	public DrawableObject hovered_drawable;
	
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
		line_layer = new ArrayList<>();
		marker_layer = new ArrayList<>();
		annotation_layer = new ArrayList<>();
		drawables = new ArrayList<>();
	}	
}
