package com.pp.iwm.teledoc.drawables;

import com.pp.iwm.teledoc.gui.DrawablePane;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DrawableLine extends DrawableObject {
	
	// =======================================
	// FIELDS
	// =======================================
	
	private Line line;
	private DrawablePane drawable_pane;
	private Point2D from;
	private Point2D to;
	private Color color;
	private double line_width;
	
	// =======================================
	// METHODS
	// =======================================
	
	public DrawableLine(Point2D _from, Point2D _to, Color _color, DrawablePane _drawable_pane) {
		super();
		drawable_pane = _drawable_pane;
		from = _from;
		to = _to;
		color = _color;
		line_width = 2.0; // TODO
		
		init();
	}
	
	private void init() {
		line = new Line();
		line.setStroke(color);
		line.setStrokeWidth(2.0);
		line.setOnMouseEntered(ev -> onMouseEntered());
		line.setOnMouseExited(ev -> onMouseExited());
		rescale();
	}
	
	@Override
	public void rescale() {
		double scale = drawable_pane.getScale();
		line.setStrokeWidth(scale * line_width);
		line.setStartX(scale * from.getX()); line.setStartY(scale * from.getY());
		line.setEndX(scale * to.getX()); line.setEndY(scale * to.getY());
	}
	
	// TODO zaznaczanie, usuwanie, przeci¹ganie
	
	public void onMouseEntered() {
		Color c = color.invert();
		line.setStroke(c);
	}
	
	public void onMouseExited() {
		line.setStroke(color);
	}
	
	private Color getOriginalColorCopy() {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
	}
	
	public Line getLine() {
		return line;
	}
}
