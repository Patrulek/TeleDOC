package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class AnnotationTextPane extends ScrollPane {
	
	// =============================
	// FIELDS
	// =============================
	
	private ConfWindowLayout layout;
	private Bounds viewport_bounds;
	private Label lbl_text;
	private Annotation annotation_to_display;
	
	// =============================
	// METHODS
	// =============================
	
	public AnnotationTextPane(ConfWindowLayout _layout, Bounds _viewport_bounds) {
		layout = _layout;
		viewport_bounds = _viewport_bounds;
		
		createLayout();
	}
	
	private void createLayout() {
		lbl_text = new Label();
		lbl_text.setWrapText(true);
		lbl_text.setAlignment(Pos.TOP_LEFT);
		lbl_text.setStyle("-fx-text-fill: rgb(140, 140, 170);");
		lbl_text.setMaxWidth(240.0);
		
		setContent(lbl_text);
		setVisible(false);
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		setPrefWidth(260.0);
		setMaxSize(260.0, 240.0);
		setStyle("-fx-background-color: rgb(30, 54, 60, 0.85); -fx-background: rgb(30, 54, 60, 0.85);");
	}
	
	public void showForAnnotation(Annotation _ann) {
		if( annotation_to_display != _ann ) {
			annotation_to_display = _ann;
			setText(_ann.getText());
			recalcBounds();
			recalcPosition();
		} 
		
		setVisible(true);
	}
	
	private void recalcPosition() {
		if( annotation_to_display != null ) {
			Annotation _ann = annotation_to_display;
			double radius = _ann.getCircle().getRadius();
			Point2D pos = new Point2D(_ann.getCircle().getCenterX(), _ann.getCircle().getCenterY());
			pos = layout.mapImagePosToScreenPos(pos);
			Point2D x_bounds = new Point2D(pos.getX() - getWidth() / 2.0, pos.getX() + getWidth() / 2.0);
			Point2D y_bounds = new Point2D(pos.getY() - 2 * radius - getHeight(), pos.getY() + 2 * radius);
			
			if( x_bounds.getX() < viewport_bounds.getMinX() )
				setLayoutX(viewport_bounds.getMinX());
			else if( x_bounds.getY() > viewport_bounds.getMaxX() )
				setLayoutX(viewport_bounds.getMaxX() - getWidth());
			else
				setLayoutX(x_bounds.getX());
			
			if( y_bounds.getX() < viewport_bounds.getMinY() )
				setLayoutY(y_bounds.getY());
			else
				setLayoutY(y_bounds.getX());
		}
	}
	
	public void hide() {
		setVisible(false);
		annotation_to_display = null;
	}
	
	private void setText(String _text) {
		lbl_text.setText(_text);
		
	}
	
	private void recalcBounds() {
		Bounds layout_bounds = lbl_text.getLayoutBounds();
		lbl_text.autosize();
		
		if( !layout_bounds.equals(lbl_text.getLayoutBounds()) ) {
			double height = lbl_text.getLayoutBounds().getHeight();
			setPrefHeight(height);
			autosize();
		}
	}
}
