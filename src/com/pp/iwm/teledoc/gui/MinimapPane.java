package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.animations.TranslateAnimation;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MinimapPane extends Pane {
	
	// ==============================
	// FIELDS
	// ==============================
	
	private ConfWindowLayout layout;
	
	private FadeAnimation fade_animation;
	private TranslateAnimation translate_animation;
	
	private ImageView mini_image;
	private DoubleStateImageButton ibtn_hide;
	private Rectangle view_border;
	private Bounds viewport_bounds;
	private Bounds drawable_bounds;
	
	// =================================
	// METHODS
	// =================================
	
	public MinimapPane(ConfWindowLayout _layout) {
		layout = _layout;
		createLayout();
		addAnimations();
		Platform.runLater(() -> {
			viewport_bounds = layout.scroll_pane.getViewportBounds();
			drawable_bounds = layout.drawable_pane.getLayoutBounds();
			refresh();
		});
	}
	
	private void createLayout() {
		setStyle("-fx-background-color: rgb(15, 27, 30);");
		setPrefSize(260.0, 180.0);
		setVisible(false);
		setOpacity(1.0);
		
		ibtn_hide = new DoubleStateImageButton(Utils.IMG_PARENT_FOLDER_SMALL, Utils.IMG_PARENT_FOLDER_SMALL, "Ukryj", -1);
		ibtn_hide.setRotate(90.0);
		ibtn_hide.disableFadeAnimation();
		ibtn_hide.disableTranslateAnimation();
		ibtn_hide.addEventHandler(ActionEvent.ACTION, ev -> onHideBtn());
		ibtn_hide.relocate(-6.0, 2.0);
		
		mini_image = new ImageView();
		mini_image.setLayoutX(20.0);
		mini_image.setFitHeight(180.0);
		mini_image.setFitWidth(240.0);
		mini_image.setOnMouseClicked(ev -> moveToPosition(ev));
		mini_image.setOnMouseDragged(ev -> moveToPosition(ev));
		mini_image.setFocusTraversable(false);
		
		view_border = new Rectangle();
		view_border.setStroke(Color.rgb(205, 100, 100));
		view_border.setFill(Color.rgb(100, 100, 205, 0.2));
		view_border.setStrokeWidth(2.0);
		view_border.setMouseTransparent(true);
		view_border.setFocusTraversable(false);
		
		
		setFocusTraversable(false);
		getChildren().addAll(mini_image, view_border, ibtn_hide);
	}
	
	private void onHideBtn() {
		if( ibtn_hide.isOnProperty().get() ) {
			hide();
			ibtn_hide.setRotate(90.0);
		} else {
			show();
			ibtn_hide.setRotate(270.0);
		}
	}
	
	private void addAnimations() {
		fade_animation = new FadeAnimation(this);
		fade_animation.customize(1.0, 0.0, 400, 400);
		
		translate_animation = new TranslateAnimation(this);
		translate_animation.customize(240.0, 0.0, 300, 300);
	}
	
	public void setImage(Image _img) {
		Platform.runLater(() -> {
			mini_image.setImage(_img);
		});
		
		if( !isVisible() )
			setVisible(true);
	}
	
	public void refresh() {
		drawable_bounds = layout.drawable_pane.getLayoutBounds();
		Platform.runLater(() -> calculateBorder());
	}
	
	public void show() {
		translate_animation.playForward();
	}
	
	public void hide() {
		translate_animation.playBackward();
	}
	
	private void moveToPosition(MouseEvent _ev) {
		Point2D pos = mini_image.sceneToLocal(new Point2D(_ev.getSceneX(), _ev.getSceneY()));
		
		double new_x = pos.getX() + 20.0;
		new_x = new_x - view_border.getWidth() / 2.0 < 20.0 ? 20.0 : new_x + view_border.getWidth() / 2.0 > 260.0 ? 260.0 - view_border.getWidth() : new_x - view_border.getWidth() / 2.0;
		double new_y = pos.getY();
		new_y = new_y - view_border.getHeight() / 2.0 < 0.0 ? 0.0 : new_y + view_border.getHeight() / 2.0 > 180.0 ? 180.0 - view_border.getHeight() : new_y - view_border.getHeight() / 2.0;
		
		view_border.setX(new_x); view_border.setY(new_y);
		view_border.autosize();
		
		double h_value = (new_x - 20.0) / (260.0 - 20.0 - view_border.getWidth());
		double v_value = new_y / (180.0 - view_border.getHeight());
		
		h_value = Double.isNaN(h_value) ? 0.0 : h_value;
		v_value = Double.isNaN(v_value) ? 0.0 : v_value;
		
		layout.scroll_pane.setHvalue(h_value);
		layout.scroll_pane.setVvalue(v_value);
	}
	
	// TODO przenieœæ wy¿ej
	private void calculateBorder() {
		layout.scroll_pane.autosize();
		double h_value = layout.scroll_pane.getHvalue();
		double v_value = layout.scroll_pane.getVvalue();
		
		double w = 240.0 * viewport_bounds.getWidth() / drawable_bounds.getWidth();
		double h = 180.0 * viewport_bounds.getHeight() / drawable_bounds.getHeight();
		
		double x = h_value * (260.0 - 20.0 - w) + 20.0;
		double y = v_value * (180 - h);
		
		view_border.setX(x); view_border.setY(y);
		view_border.setWidth(w); view_border.setHeight(h);
		view_border.autosize();
	}
} 
