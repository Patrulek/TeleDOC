package com.pp.iwm.teledoc.drawables;

import com.pp.iwm.teledoc.gui.DrawablePane;
import com.sun.javafx.css.CalculatedValue;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class DrawableLine extends DrawableObject {
	
	// =======================================
	// FIELDS
	// =======================================
	
	public static final double SELECTOR_SIZE = 8.0;
	
	private Line line;
	private DrawablePane drawable_pane;
	private Point2D from;
	private Point2D to;
	private Color original_color;
	private double line_width;
	private boolean is_selected;

	private Rectangle [] line_selectors;
	private Point2D first_selector;
	private Point2D second_selector;
	
	// =======================================
	// METHODS
	// =======================================
	
	public DrawableLine(Point2D _from, Point2D _to, Color _color, DrawablePane _drawable_pane) {
		super();
		drawable_pane = _drawable_pane;
		from = _from;
		to = _to;
		line_width = 2.0; // TODO
		first_selector = new Point2D(from.getX(), from.getY());
		second_selector = new Point2D(to.getX(), to.getY());
		line = new Line();

		line_selectors = new Rectangle[2];
		line_selectors[0] = new Rectangle(SELECTOR_SIZE, SELECTOR_SIZE);
		line_selectors[1] = new Rectangle(SELECTOR_SIZE, SELECTOR_SIZE);
		line_selectors[0].setOnMouseDragged(ev -> onSelectorDragged(ev, 0));
		line_selectors[1].setOnMouseDragged(ev -> onSelectorDragged(ev, 1));

		changeOriginalColor(_color);
		init();
	}
	
	private void init() {
		line.setStrokeWidth(2.0);
		line.setOnMouseEntered(ev -> onMouseEntered());
		line.setOnMouseExited(ev -> onMouseExited());
		line.setOnMouseClicked(ev -> onSelected());
		rescale();
	}
	
	@Override
	public void rescale() {
		scale = drawable_pane.getScale();
		rescaleLine();
		rescaleSelectors();
		
		if( is_selected ) {
			calculateSelectorsPosition();
			showSelectorsAtPane();
		}
	}
	
	private void rescaleLine() {
		line.setStrokeWidth(scale * line_width);
		line.setStartX(scale * from.getX()); line.setStartY(scale * from.getY());
		line.setEndX(scale * to.getX()); line.setEndY(scale * to.getY());
	}
	
	private void rescaleSelectors() {
		line_selectors[0].setWidth(scale * SELECTOR_SIZE); line_selectors[0].setHeight(scale * SELECTOR_SIZE);
		line_selectors[1].setWidth(scale * SELECTOR_SIZE); line_selectors[1].setHeight(scale * SELECTOR_SIZE);
	}
	
	private void calculateSelectorsPosition() {
		Point2D dist = to.subtract(from).normalize().multiply(SELECTOR_SIZE * 1.5 * scale);
		Point2D offset = new Point2D(SELECTOR_SIZE * scale / 2, SELECTOR_SIZE * scale / 2);
		
		first_selector = from.multiply(scale).subtract(dist).subtract(offset);
		second_selector = to.multiply(scale).add(dist).subtract(offset);
		
		line_selectors[0].setLayoutX(first_selector.getX()); line_selectors[0].setLayoutY(first_selector.getY());
		line_selectors[1].setLayoutX(second_selector.getX()); line_selectors[1].setLayoutY(second_selector.getY());
	}
	
	private void showSelectorsAtPane() {
		drawable_pane.refreshSelectors();
	}
	
	// TODO zaznaczanie, usuwanie, przeci¹ganie
	
	public Line getLine() {
		return line;
	}

	@Override
	public void onSelected() {
		is_selected = true;
		drawable_pane.setSelectedDrawable(this);
		calculateSelectorsPosition();
		showSelectorsAtPane();
	}
	

	@Override
	public void onDeselected() {
		is_selected = false;
		
	}

	@Override
	public void onMouseEntered() {
		changeLineColor(original_color.invert());
	}
	
	@Override
	public void onMouseExited() {
		restoreColor();
	}

	@Override
	public void onChanged() {
		
	}
	
	private void onSelectorDragged(MouseEvent _ev, int _index) {
		Point2D delta = drawable_pane.getPaneMouseDelta();
		
		if( _index == 0 )
			changeFrom(from.add(delta));
		else
			changeTo(to.add(delta));	// mouse_pos.add(dist)
		
		rescaleLine();
		calculateSelectorsPosition();
		onChanged();
	}
	
	private void changeFrom(Point2D _new_from) {
		from = _new_from;
	}
	
	private void changeTo(Point2D _new_to) {
		to = _new_to;
	}
	
	public void changeLineColor(Color _new_color) {
		line.setStroke(_new_color);
	}
	
	public void changeOriginalColor(Color _new_color) {
		original_color = _new_color;
		changeLineColor(_new_color);
		line_selectors[0].setFill(original_color.invert());
		line_selectors[1].setFill(original_color.invert());
	}
	
	public void restoreColor() {
		line.setStroke(original_color);
	}
	
	public Rectangle[] getSelectors() {
		return line_selectors;
	}
}
