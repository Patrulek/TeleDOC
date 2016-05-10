package com.pp.iwm.teledoc.drawables;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.gui.DrawablePane;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DrawableBrokenLine extends DrawableObject {
	
	// ==================================
	// FIELDS
	// ==================================
	
	private List<Line> lines;
	private DrawablePane drawable_pane;
	private List<Point2D> end_points;
	private Color color;
	private double line_width;
	
	
	// ==================================
	// METHODS
	// ==================================
	
	public DrawableBrokenLine(Point2D _from, Point2D _to, Color _color, DrawablePane _drawable_pane) {
		super();
		drawable_pane = _drawable_pane;
		end_points = new ArrayList<>();
		color = _color;
		line_width = 2.0; // TODO
		lines = new ArrayList<>();
		addLine(_from, _to);
	}
	
	public void addLine(Point2D _from, Point2D _to) {
		double scale = drawable_pane.getScale();
		
		Line line = new Line(scale * _from.getX(), scale * _from.getY(), scale * _to.getX(), scale * _to.getY());
		line.setStroke(color);
		line.setStrokeWidth(line_width * scale);
		line.setOnMouseEntered(ev -> onMouseEntered());
		line.setOnMouseExited(ev -> onMouseExited());
		lines.add(line);
		
		if( end_points.isEmpty() ) {
			end_points.add(_from);
			end_points.add(_to);
		} else
			end_points.add(_to);
		
		drawable_pane.updateBrokenLine(line);
	}
	
	@Override
	public void rescale() {
		double scale = drawable_pane.getScale();
		int i = 0;
		
		for( Line line : lines ) {
			line.setStrokeWidth(scale * line_width);
			line.setStartX(scale * end_points.get(i).getX()); line.setStartY(scale * end_points.get(i).getY());
			line.setEndX(scale * end_points.get(i + 1).getX()); line.setEndY(scale * end_points.get(i + 1).getY());
			i++;
		}
	}
	
	public void onMouseEntered() {
		Color c = color.invert();
		
		for( Line line : lines )
			line.setStroke(c);
	}
	
	public void onMouseExited() {
		for( Line line : lines )
			line.setStroke(color);
	}
	
	public List<Line> getLines() {
		return lines;
	}
}
