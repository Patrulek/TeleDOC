package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.utils.InputUtils;
import com.pp.iwm.teledoc.windows.ConfWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class AnnotationPane extends Pane {
	
	// ====================================
	// FIELDS
	// ====================================
	
	
	private Window window;
	private TextArea text_area;
	private Button btn_submit;
	private Button btn_cancel;
	
	private final double HORIZONTAL_GAP = 16.0;
	private final double VERTICAL_GAP = 45.0;
	private Bounds viewport_bounds;
	private Point2D last_location;
	private double scale;
	
	private FadeAnimation fade_animation;
	
	// ====================================
	// METHODS
	// ====================================
	
	public AnnotationPane(Window _window, Bounds _viewport_bounds) {
		super();
		window = _window;
		scale = 1.0;
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
	
	private void addAnimation() {
		fade_animation = new FadeAnimation(this);
		fade_animation.customize(1.0, 0.0, 100, 150);
	}
	
	// TODO hack
	public void showAtLocation(Point2D _location, double _new_scale, boolean _is_moving) {
		if( (last_location != _location && _location != null) /* || (scale != _new_scale && _new_scale != -1.0) || _is_moving */)
			calculatePosition(_location, _new_scale);
		
		show();
	}
	
	private void calculatePosition(Point2D _location, double _new_scale) {
		last_location = _location;
		scale = _new_scale;
		
		calculateHorizontalPosition(_location, _new_scale);
		calculateVerticalPosition(_location, _new_scale);
	}
	
	private void calculateHorizontalPosition(Point2D _location, double _new_scale) {
			if( last_location.getX() + getWidth() > viewport_bounds.getMaxX() )
				setLayoutX(last_location.getX() - getWidth() - HORIZONTAL_GAP);
			else
				setLayoutX(last_location.getX() + HORIZONTAL_GAP);
	}
	
	private void calculateVerticalPosition(Point2D _location, double _new_scale) {
			if( last_location.getY() + getHeight() / 2 > viewport_bounds.getMaxY() )
				setLayoutY(viewport_bounds.getMaxY() - getHeight());
			else if( last_location.getY() - getHeight() / 2 < 0.0 )
				setLayoutY(0.0);
			else
				setLayoutY(last_location.getY() - VERTICAL_GAP);
	}
	
	private void show() {
		setVisible(true);
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
