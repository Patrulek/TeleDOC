package com.pp.iwm.teledoc.drawables;

import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Annotation	extends DrawableObject {
	
	// ======================================
	// FIELDS
	// ======================================
	
	public enum State {
		DRAWING, DRAWN, SELECTED;
	}
	
	private final double RADIUS = 8.0;
	private Color color;
	private String text;
	private Point2D position;
	private Circle circle;
	private Bounds viewport_bounds;
	private State state;
	private DrawablePane drawable_pane;
	private boolean was_dragged;
	
	// ======================================
	// METHODS
	// ======================================
	
	public void show() {
		circle.setVisible(true);
	}
	
	public void hide() {
		circle.setVisible(false);
	}
	
	public Annotation(String _text, Color _color, Bounds _viewport_bounds, Point2D _position, DrawablePane _drawable_pane) {
		state = State.DRAWING;
		color = _color;
		text = _text;
		viewport_bounds = _viewport_bounds;
		position = _position;
		drawable_pane = _drawable_pane;
		is_selected = false;
		
		double scale = drawable_pane.getScale();
		circle = new Circle(scale * position.getX(), scale * position.getY(), RADIUS, color);
		circle.setOnMouseEntered(ev -> onMouseEntered(ev));
		circle.setOnMouseExited(ev -> onMouseExited());
		circle.setOnMouseClicked(ev -> onSelected());
		circle.setOnMouseDragged(ev -> onCircleDragged());
		circle.setOnMouseReleased(ev -> onCircleDragReleased());
		
		Platform.runLater(() -> relocate());
	}
	
	public String getText() {
		return text;
	}
	
	public State getState() {
		return state;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public Color getOriginalColor() {
		return color;
	}
	
	private void onCircleDragReleased() {
		if( state == State.DRAWING )
			return;
		
		if( is_dragged ) {
			was_dragged = true;
			is_dragged = false;
			onChanged();
			
			String params = Utils.objectsToString(max_delta, id);
			System.out.println("Przesunieto o: " + max_delta);
			User.instance().sendMoveObjectAction(params);
			max_delta = new Point2D(0.0, 0.0);
		}
	}
	
	public void setOriginalColor() {
		circle.setFill(color);
	}
	
	private void onCircleDragged() {
		if( state == State.DRAWING )
			return;
		
		if( !is_dragged )
			is_dragged = true;

		Point2D delta = listener.onDragged(this);
		max_delta = max_delta.add(delta);
		changePosition(position.add(delta));
		
		onChanged();
	}
	
	public void changePosition(Point2D _new_position) {
		position = _new_position;
		relocate();
	}
	
	public boolean isDragged() {
		return is_dragged;
	}
	
	private void relocate() {
		scale = drawable_pane.getScale();
		double x = position.getX();
		double y = position.getY();
		
		//if( x * scale + RADIUS > viewport_bounds.getMaxX() )
		//	x = viewport_bounds.getMaxX() - RADIUS;
		
		//if( y * scale + RADIUS > viewport_bounds.getMaxY() )
		//	y = viewport_bounds.getMaxY() - RADIUS;
		
		position = new Point2D(x, y);
		circle.setCenterX(position.getX() * scale);
		circle.setCenterY(position.getY() * scale);
	}
	
	@Override
	public void rescale() {
		//circle.setCenterX(scale * position.getX());
		//circle.setCenterY(scale * position.getY());
		relocate();
	}
	
	public void setText(String _text) {
		text = _text;
	}
	
	public void changeState(State _new_state) {
		state = _new_state;
	}
	
	public void onMouseEntered(MouseEvent _ev) {
		onMouseEntered();
		
		if( state == State.DRAWN && !is_dragged ) {
			Color c = color.invert();
			circle.setFill(c);
		} 
	}
	
	public Circle getCircle() {
		return circle;
	}

	@Override
	public void onSelected() {
		if( is_selected )
			return;
		
		if( !is_dragged && !was_dragged ) {
			is_selected = true;
			listener.onSelected(this);
		} 
		
		was_dragged = false;
	}
	
	@Override
	public void onMouseEntered() {
		listener.onMouseEntered(this);
	}
	
	@Override
	public void onMouseExited() {
		listener.onMouseExited(this);
		
		if( state == State.DRAWN && !is_dragged )
			setOriginalColor();
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj == null || !(obj instanceof Annotation) )
			return false;
		
		Annotation ann = (Annotation)obj;
		
		if( ann.position.equals(position) && ann.text.equals(text) )
			return true;
		
		return false;
	}

	@Override
	public void onChanged() {
		listener.onChanged(this);
	}

	@Override
	public void onDeselected() {
		is_selected = false;
		drawable_pane.setSelectedDrawable(null);
	}

	@Override
	public void move(Point2D _delta) {
		System.out.println("Moved by: " + _delta);
		changePosition(position.add(_delta));
		
		rescale();
	}
}
