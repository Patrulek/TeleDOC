package com.pp.iwm.teledoc.drawables;

import com.pp.iwm.teledoc.gui.DrawablePane;

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
	
	// TODO temp
	private ScrollPane annotation_text_pane;
	
	// ======================================
	// METHODS
	// ======================================
	
	public Annotation(String _text, Color _color, Bounds _viewport_bounds, Point2D _position, DrawablePane _drawable_pane) {
		state = State.DRAWING;
		color = _color;
		text = _text;
		viewport_bounds = _viewport_bounds;
		position = _position;
		drawable_pane = _drawable_pane;
		
		double scale = drawable_pane.getScale();
		circle = new Circle(scale * position.getX(), scale * position.getY(), RADIUS, color);
		circle.setOnMouseEntered(ev -> onMouseEntered(ev));
		circle.setOnMouseExited(ev -> onMouseExited());
		circle.setOnMouseDragged(ev -> onCircleDragged());
		circle.setOnMouseReleased(ev -> onCircleDragReleased());
		
		Platform.runLater(() -> relocate());
	}
	
	private void onCircleDragReleased() {
		if( is_dragged ) {
			is_dragged = false;
			createTextPane(position);
		}
	}
	
	private void onCircleDragged() {
		if( !is_dragged ) {
			is_dragged = true;
			destroyTextPane();
		}

		Point2D delta = listener.onDragged(this);
		changePosition(position.add(delta));
		
		
		onChanged();
	}

	// TODO temp
	private void createTextPane(Point2D _pos) {
		if( annotation_text_pane == null ) {
		// TODO mo¿liwoœæ nie wyœwietlania siê ca³ego tekstu / zmiast pane scroll pane
		Label l = new Label(text);
		l.setWrapText(true);
		l.setAlignment(Pos.TOP_LEFT);
		l.setPrefSize(240.0, 60.0);
		l.setMaxSize(240.0, 240.0);
		l.setStyle("-fx-text-fill: rgb(140, 140, 120);");
		annotation_text_pane = new ScrollPane(l);
		annotation_text_pane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		annotation_text_pane.setStyle("-fx-background-color: rgb(30, 54, 60, 0.85); -fx-background: rgb(30, 54, 60, 0.85);");
	
		if( _pos.getY() - RADIUS * 2 - 60.0 < 0 ) {
			if( _pos.getY() + RADIUS * 2 + 60.0 < 744.0 )
				annotation_text_pane.setLayoutY(_pos.getY() + RADIUS * 2);
			else
				annotation_text_pane.setLayoutY(0.0);
		} else
			annotation_text_pane.setLayoutY(_pos.getY() - RADIUS * 2 - 60.0 );
		
		if( _pos.getX() - 120.0 < 32.0 )
			annotation_text_pane.setLayoutX(60.0);
		else if( _pos.getX() + 120.0 > 1360.0 )
			annotation_text_pane.setLayoutX(1108.0);
		else
			annotation_text_pane.setLayoutX(_pos.getX() - 120.0);
		
		//drawable_pane.getConfWindow().showAnnotationPane(annotation_text_pane);
		}
	}
	
	private void destroyTextPane() {
		if( annotation_text_pane != null ) {
			//drawable_pane.getConfWindow().hideAnnotationPane(annotation_text_pane);
			annotation_text_pane = null;
		}
	}
	
	public void changePosition(Point2D _new_position) {
		position = _new_position;
		relocate();
	}
	
	private void relocate() {
		double scale = drawable_pane.getScale();
		double x = position.getX();
		double y = position.getY();
		
		if( x * scale + RADIUS > viewport_bounds.getMaxX() )
			x = viewport_bounds.getMaxX() - RADIUS;
		
		if( y * scale + RADIUS > viewport_bounds.getMaxY() )
			y = viewport_bounds.getMaxY() - RADIUS;
		
		position = new Point2D(x, y);
		circle.setCenterX(position.getX() * scale);
		circle.setCenterY(position.getY() * scale);
	}
	
	@Override
	public void rescale() {
		circle.setCenterX(scale * position.getX());
		circle.setCenterY(scale * position.getY());
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
			Point2D mouse_pos = new Point2D(_ev.getSceneX(), _ev.getSceneY());
			createTextPane(mouse_pos);
		} 
	}
	
	public Circle getCircle() {
		return circle;
	}

	@Override
	public void onSelected() {
		listener.onSelected(this);
	}
	
	@Override
	public void onMouseEntered() {
		listener.onMouseEntered(this);
	}
	
	@Override
	public void onMouseExited() {
		listener.onMouseExited(this);
		
		if( state == State.DRAWN && !is_dragged ) {
			circle.setFill(color);
			destroyTextPane();
		}
	}

	@Override
	public void onChanged() {
		listener.onChanged(this);
	}

	@Override
	public void onDeselected() {
	}
}
