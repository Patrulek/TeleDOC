package com.pp.iwm.teledoc.drawables;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class DrawableBrokenLine extends DrawableObject {
	
	// ==================================
	// FIELDS
	// ==================================
	
	private List<Line> lines;
	private DrawablePane drawable_pane;
	private List<Point2D> end_points;
	private Color original_color;
	private double line_width;
	private boolean is_selected;

	private Rectangle [] line_selectors;
	private Point2D first_selector;
	private Point2D second_selector;
	private Line selected_line;
	private int selected_line_index;
	
	// ==================================
	// METHODS
	// ==================================
	
	public static final double SELECTOR_SIZE = 8.0;
	
	private void changeOriginalColor(Color _color) {
		original_color = _color;
		changeLinesColor(_color);
		line_selectors[0].setFill(original_color.invert());
		line_selectors[1].setFill(original_color.invert());
	}
	
	private void changeLinesColor(Color _color) {
		for( Line line : lines )
			line.setStroke(_color);
	}
	
	public DrawableBrokenLine(Point2D _from, Point2D _to, Color _color, DrawablePane _drawable_pane) {
		super();
		drawable_pane = _drawable_pane;
		scale = drawable_pane.getScale();
		end_points = new ArrayList<>();
		line_width = 2.0; // TODO
		lines = new ArrayList<>();
		selected_line_index = -1;
		
		Line line = new Line(_from.getX(), _from.getY(), _to.getX(), _to.getY());
		addLine(line);
		
		line_selectors = new Rectangle[2];
		line_selectors[0] = new Rectangle(SELECTOR_SIZE * scale, SELECTOR_SIZE * scale);
		line_selectors[1] = new Rectangle(SELECTOR_SIZE * scale, SELECTOR_SIZE * scale);
		line_selectors[0].setOnMouseDragged(ev -> onSelectorDragged(ev, 0));
		line_selectors[1].setOnMouseDragged(ev -> onSelectorDragged(ev, 1));
		
		changeOriginalColor(_color);
	}
	
	private void onSelectorDragged(MouseEvent _ev, int _index) {
		Point2D delta = listener.onDragged(this);
		
		changeEndPoint(selected_line_index + _index, end_points.get(selected_line_index + _index).add(delta));
		
		rescaleLines();
		calculateSelectorsPosition();
		onChanged();
	}
	
	private void changeEndPoint(int _index, Point2D _end_point) {
		end_points.set(_index, _end_point);
	}
	
	private void onLineDragReleased() {
		if( is_dragged )
			is_dragged = false;
	}
	
	private void onLineDragged() {
		if( !is_dragged )
			is_dragged = true;

		Point2D delta = listener.onDragged(this);
		
		for( int i = 0; i < end_points.size(); i++ )
			end_points.set(i, end_points.get(i).add(delta));
		
		rescale();
		
		onChanged();
	}
	
	public void addLine(Line _line) {
		double scale = drawable_pane.getScale();
		Point2D from = new Point2D(_line.getStartX(), _line.getStartY());
		Point2D to = new Point2D(_line.getEndX(), _line.getEndY());
		
		_line.setStartX(scale * from.getX()); _line.setStartY(scale * from.getY());
		_line.setEndX(scale * to.getX()); _line.setEndY(scale * to.getY());
		_line.setStroke(original_color);
		_line.setStrokeWidth(line_width * scale);
		_line.setOnMouseEntered(ev -> onMouseEntered(_line));
		_line.setOnMouseExited(ev -> onMouseExited());
		_line.setOnMouseClicked(ev -> onLineSelected(_line));
		_line.setOnMouseDragged(ev -> onLineDragged());
		_line.setOnMouseReleased(ev -> onLineDragReleased());
		lines.add(_line);
		
		if( end_points.isEmpty() ) {
			end_points.add(from);
			end_points.add(to);
		} else
			end_points.add(to);
	}
	
	private void onLineSelected(Line _line) {
		selected_line = _line;
		findSelectedLineIndex();
		onSelected();
	}
	
	private void findSelectedLineIndex() {
		for( int i = 0; i < lines.size(); i++ )
			if( selected_line == lines.get(i) ) {
				selected_line_index = i;
				return;
			}
	}
	
	@Override
	public void rescale() {
		rescaleLines();
		rescaleSelectors();
		
		if( is_selected ) {
			calculateSelectorsPosition();
			showSelectorsAtPane();
		}
	}
	
	public Rectangle[] getSelectors() {
		return line_selectors;
	}
	
	private void calculateSelectorsPosition() {
		Point2D from = end_points.get(selected_line_index);
		Point2D to = end_points.get(selected_line_index + 1);
		
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
	
	private void rescaleSelectors() {
		line_selectors[0].setWidth(scale * SELECTOR_SIZE); line_selectors[0].setHeight(scale * SELECTOR_SIZE);
		line_selectors[1].setWidth(scale * SELECTOR_SIZE); line_selectors[1].setHeight(scale * SELECTOR_SIZE);
	}
	
	private void rescaleLines() {
		int i = 0;
		
		for( Line line : lines ) {
			line.setStrokeWidth(scale * line_width);
			line.setStartX(scale * end_points.get(i).getX()); line.setStartY(scale * end_points.get(i).getY());
			line.setEndX(scale * end_points.get(i + 1).getX()); line.setEndY(scale * end_points.get(i + 1).getY());
			i++;
		}
	}
	
	
	public List<Line> getLines() {
		return lines;
	}

	@Override
	public void onSelected() {
		is_selected = true;
		drawable_pane.setSelectedDrawable(this);
		calculateSelectorsPosition();
		showSelectorsAtPane();
	}

	@Override
	public void onChanged() {
		// TODO Auto-generated method stub
		
	}
	
	public void onMouseEntered(Line _line) {
		onMouseEntered();
		_line.setStroke(Utils.mixColors(original_color.invert(), Color.BLACK));
	}
	
	@Override
	public void onMouseEntered() {
		changeLinesColor(original_color.invert());
	}
	
	@Override
	public void onMouseExited() {
		changeLinesColor(original_color);
	}

	@Override
	public void onDeselected() {
		is_selected = false;
	}
}
