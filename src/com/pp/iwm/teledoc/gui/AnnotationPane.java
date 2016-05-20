package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class AnnotationPane extends Pane {
	
	// ====================================
	// FIELDS
	// ====================================
	
	
	private ConfWindowLayout layout;
	private TextArea text_area;
	private Button btn_submit;
	private Button btn_cancel;
	
	private Bounds viewport_bounds;
	private Annotation current_ann;
	
	private FadeAnimation fade_animation;
	
	// ====================================
	// METHODS
	// ====================================
	
	public AnnotationPane(ConfWindowLayout _layout, Bounds _viewport_bounds) {
		super();
		layout = _layout;
		viewport_bounds = _viewport_bounds;
		createLayout();
		addAnimation();
	}
	
	private void createLayout() {
		setPrefSize(300, 90);
		setStyle("-fx-background-color: rgb(30, 54, 60);");
		
		text_area = new TextArea();
		text_area.setPrefSize(290, 50);
		text_area.setLayoutX(5); text_area.setLayoutY(5);
		text_area.setWrapText(true);
		
		btn_submit = new Button("Dodaj");
		btn_submit.setPrefSize(142, 20);
		btn_submit.setLayoutX(5); btn_submit.setLayoutY(60);
		
		btn_cancel = new Button("Anuluj");
		btn_cancel.setPrefSize(143, 20);
		btn_cancel.setLayoutX(152); btn_cancel.setLayoutY(60);
		
		getChildren().add(text_area);
		getChildren().add(btn_submit);
		getChildren().add(btn_cancel);
		
		setOpacity(0.0);
		setVisible(false);
	}
	
	public Annotation getCurrentAnnotation() {
		return current_ann;
	}
	
	private void addAnimation() {
		fade_animation = new FadeAnimation(this);
		fade_animation.customize(1.0, 0.0, 100, 150);
	}
	
	public void showForAnnotation(Annotation _ann) {
		current_ann = _ann;
		text_area.setText(current_ann.getText());
		calculatePosition();
		show();
	}
	
	public void refresh() {
		calculatePosition();
	}
	
	private void calculatePosition() {
		double radius = current_ann.getCircle().getRadius();
		Point2D pos = new Point2D(current_ann.getCircle().getCenterX(), current_ann.getCircle().getCenterY());
		pos = layout.mapImagePosToScreenPos(pos);
		Point2D x_bounds = new Point2D(pos.getX() - getWidth() - 2 * radius, pos.getX() + 2 * radius);
		Point2D y_bounds = new Point2D(pos.getY() - getHeight() / 2.0, pos.getY() + getHeight() / 2.0);

		if( x_bounds.getX() + getWidth() > viewport_bounds.getMaxX())
			setLayoutX(viewport_bounds.getMaxX() - getWidth());
		else if( x_bounds.getY() + getWidth() > viewport_bounds.getMaxX() )
			setLayoutX(x_bounds.getX());
		else if( x_bounds.getY() < viewport_bounds.getMinX())
			setLayoutX(viewport_bounds.getMinX());
		else
			setLayoutX(x_bounds.getY());
		
		if( y_bounds.getY() > viewport_bounds.getMaxY() )
			setLayoutY(viewport_bounds.getMaxY() - getHeight());
		else if( y_bounds.getX() < viewport_bounds.getMinY() )
			setLayoutY(viewport_bounds.getMinY());
		else
			setLayoutY(y_bounds.getX());
	}
	
	private void show() {
		setVisible(true);
		fade_animation.setOnFinished(null);
		fade_animation.playForward();
	}
	
	public void hide() {
		fade_animation.playBackward();
		fade_animation.setOnFinished(ev -> onHideFinished());
	}
	
	private void onHideFinished() {
		setVisible(false);
		fade_animation.setOnFinished(null);
	}
	
	public Button getBtnSubmit() {
		return btn_submit;
	}
	
	public Button getBtnCancel() {
		return btn_cancel;
	}
	
	public TextArea getTextArea() {
		return text_area;
	}
}
