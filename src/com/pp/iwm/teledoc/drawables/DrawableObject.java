package com.pp.iwm.teledoc.drawables;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;

public abstract class DrawableObject {
	
	//
	// FIELDS
	//
	
	protected BoundingBox box;
	protected double scale = 1.0;
	
	//
	// METHODS
	//
	
	public DrawableObject() {}
	
	public abstract void rescale();
	public abstract void onSelected();
	public abstract void onDeselected();
	public abstract void onMouseEntered();
	public abstract void onMouseExited();
	public abstract void onChanged();
}
