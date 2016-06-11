package com.pp.iwm.teledoc.drawables;

import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.objects.ObjectId;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

public abstract class DrawableObject {
	
	
	
	//
	// FIELDS
	//
	public static int static_id = 1;
	
	protected BoundingBox box;
	protected double scale = 1.0;
	protected DrawableObjectListener listener;
	protected boolean is_dragged;
	protected boolean is_selected;
	public Point2D max_delta = new Point2D(0.0, 0.0);
	public ObjectId id;
	
	//
	// METHODS
	//
	
	public int nextId() {
		return static_id++;
	}
	
	public abstract void move(Point2D _delta);
	
	public void showIfSameImage() {
		if( id.image_id == User.instance().getCurrentImage() ) {
			show();
			rescale();
		}
	}
	
	public void hideIfOtherImage() {
		if( id.image_id != User.instance().getCurrentImage() )
			hide();
	}
	
	public abstract void hide();
	public abstract void show();
	
	public DrawableObject() {
		id = new ObjectId(nextId(), User.instance().getCurrentImage(), User.instance().getEmail());
	}
	
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
