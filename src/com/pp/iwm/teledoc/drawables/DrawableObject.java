package com.pp.iwm.teledoc.drawables;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

public abstract class DrawableObject {
	
	//
	// FIELDS
	//
	
	protected BoundingBox box;
	protected double scale = 1.0;
	protected DrawableObjectListener listener;
	protected boolean is_dragged;
	protected boolean is_selected;
	
	//
	// METHODS
	//
	
	public DrawableObject() {}
	
	public void setScale(double _scale) {
		scale = _scale;
	}
	
	public void setListener(DrawableObjectListener _listener) {
		listener = _listener;
	}
	
	public boolean isDragged() {
		return is_dragged;
	}
	
	public boolean isSelected() {
		return is_selected;
	}
	
	public abstract void rescale();
	public abstract void onSelected();
	public abstract void onDeselected();
	public abstract void onMouseEntered();
	public abstract void onMouseExited();
	public abstract void onChanged();
	
	public interface DrawableObjectListener {
		public void onMouseEntered(DrawableObject _obj);
		public void onMouseExited(DrawableObject _obj);
		public void onSelected(DrawableObject _obj);
		public void onChanged(DrawableObject _obj);
		public Point2D onDragged(DrawableObject _obj);
	}
}
