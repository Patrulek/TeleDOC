package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class DrawableCanvas extends Canvas {
	
	//
	// FIELDS
	//
	
	
	private Image image;
	private GraphicsContext gc;
	private double scale;
	private double min_scale;
	private double max_scale;
	private Bounds viewport_bounds;
	
	//
	// METHODS
	//
	public DrawableCanvas(double _width, double _height) {
		super(_width, _height);
		scale = 1.0;
	}
	
	public DrawableCanvas() {
		super();
		gc = getGraphicsContext2D();
		scale = 1.0;
	}
	
	public void setImageAndResetCanvas(Image _img) {
		Image new_image = _img;
		
		if( image == new_image )
			return;
		
		image = new_image;
		resetCanvas();
	}
	
	public void setViewportBounds(Bounds _bounds) {
		viewport_bounds = _bounds;
	}
	
	// TODO: temp
	public double getScale() {
		return scale; 
	}
	
	private void resetCanvas() {
		resetMinAndMaxCanvasScale();
		rescale();
	}
	
	private void resetMinAndMaxCanvasScale() {
		double min_width = viewport_bounds.getWidth();
		double min_height = viewport_bounds.getHeight();
		
		min_scale = Math.max(min_height / (double)image.getHeight(), min_width / (double)image.getWidth());
		max_scale = 2.0;
	}
	
	private void drawImageBuffer() {
		gc.drawImage(image, 0.0, 0.0, getWidth(), getHeight());
	}
	
	private void clearScreen() {
		gc.clearRect(0.0, 0.0, getWidth(), getHeight());
	}
	
	private void draw() {
		clearScreen();
		drawImageBuffer();
    }
	
	private void rescale() {
		scale = 1.0;
		resizeAndDraw();
	}
	
	@Override
	public void resize(double _width, double _height) {
		setWidth(_width);
		setHeight(_height);
	}
	
	private void rescale(double _new_scale) {
		scale = _new_scale < min_scale ? min_scale : _new_scale > max_scale ? max_scale : _new_scale;
		resizeAndDraw();
	}
	
	private void resizeAndDraw() {
		if( image != null ) {
			resize(image.getWidth() * scale, image.getHeight() * scale);
			draw();
		}
	}
	
	public void rescaleBy(double _rescale_by) {
		if( _rescale_by == 0.0 )
			return;
		
		double new_scale = scale + _rescale_by;
		new_scale = Math.max(new_scale, min_scale);
		new_scale = Math.min(new_scale, max_scale);
		
		if( new_scale == scale )
			return;
		
		rescale(new_scale);
	}
	
	@Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
    
    public Point2D getImageSize() {
    	return new Point2D(image.getWidth(), image.getHeight());
    }
    
    public Bounds getViewportBounds() {
    	return viewport_bounds;
    }
}

