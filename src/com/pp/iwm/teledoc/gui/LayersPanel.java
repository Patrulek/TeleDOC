package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.animations.TranslateAnimation;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class LayersPanel extends Pane implements ChangeListener<Boolean> {
	
	// ================================
	// FIELDS
	// ================================
	
	private ConfWindowLayout layout;
	
	private TranslateAnimation translate_animation;
	private FadeAnimation fade_animation;
	
	private Thread visibility_thread;
	
	private BooleanProperty is_locked;
	private double last_active;
	
	private DoubleStateImageButton ibtn_lock;
	private DoubleStateImageButton ibtn_line_layer;
	private DoubleStateImageButton ibtn_marker_layer;
	private DoubleStateImageButton ibtn_annotation_layer;
	
	private Label lbl_lines;
	private Label lbl_markers;
	private Label lbl_annotations;
	
	// ================================
	// METHODS
	// ================================
	
	public LayersPanel(ConfWindowLayout _layout) {
		layout = _layout;
		last_active = System.currentTimeMillis();
		
		createLayout();
		addAnimations();
		launchVisibilityThread();
	}
	
	private void createLayout() {
		setStyle("-fx-background-color: rgb(15, 27, 30);");
		setPrefSize(160.0, 120.0);
		setOpacity(0.0);
		setVisible(false);
		hoverProperty().addListener(this);
		
		ibtn_lock = new DoubleStateImageButton(Utils.IMG_LOCK, Utils.IMG_UNLOCK, Utils.HINT_LOCK, Utils.ACT_UNLOCK);
		ibtn_lock.switchOff();
		ibtn_lock.relocate(132.0, 96.0);
		ibtn_lock.disableAnimations();
		ibtn_lock.enableScaleAnimation();
		ibtn_lock.customizeZoomAnimation(1.15, 1.0, 400, 400);
		ibtn_lock.addEventHandler(ActionEvent.ACTION, ev -> last_active = System.currentTimeMillis());
		
		lbl_lines = new Label("Linie");
		lbl_lines.setFont(Utils.LBL_FONT);
		lbl_lines.setStyle("-fx-text-fill: rgb(140, 140, 170);");
		lbl_lines.relocate(8.0, 8.0);
		lbl_lines.setPrefHeight(24.0);
		
		ibtn_line_layer = new DoubleStateImageButton(Utils.IMG_EYE, Utils.IMG_DISABLED, Utils.HINT_EYE, Utils.ACT_EYE);
		ibtn_line_layer.relocate(132.0, 8.0);
		ibtn_line_layer.disableAnimations();
		ibtn_line_layer.enableScaleAnimation();
		ibtn_line_layer.customizeZoomAnimation(1.15, 1.0, 400, 400);
		
		lbl_markers = new Label("Znaczniki");
		lbl_markers.setFont(Utils.LBL_FONT);
		lbl_markers.setStyle("-fx-text-fill: rgb(140, 140, 170);");
		lbl_markers.relocate(8.0, 37.0);
		lbl_markers.setPrefHeight(24.0);

		ibtn_marker_layer = new DoubleStateImageButton(Utils.IMG_EYE, Utils.IMG_DISABLED, Utils.HINT_EYE, Utils.ACT_EYE);
		ibtn_marker_layer.relocate(132.0, 37.0);
		ibtn_marker_layer.disableAnimations();
		ibtn_marker_layer.enableScaleAnimation();
		ibtn_marker_layer.customizeZoomAnimation(1.15, 1.0, 400, 400);
		
		lbl_annotations = new Label("Adnotacje");
		lbl_annotations.setFont(Utils.LBL_FONT);
		lbl_annotations.setStyle("-fx-text-fill: rgb(140, 140, 170);");
		lbl_annotations.relocate(8.0, 66.0);
		lbl_annotations.setPrefHeight(24.0);
		
		ibtn_annotation_layer = new DoubleStateImageButton(Utils.IMG_EYE, Utils.IMG_DISABLED, Utils.HINT_EYE, Utils.ACT_EYE);
		ibtn_annotation_layer.relocate(132.0, 67.0);
		ibtn_annotation_layer.disableAnimations();
		ibtn_annotation_layer.enableScaleAnimation();
		ibtn_annotation_layer.customizeZoomAnimation(1.15, 1.0, 400, 400);
		
		is_locked = new SimpleBooleanProperty();
		is_locked.bind(ibtn_lock.isOnProperty());
		
		getChildren().add(ibtn_lock);
		getChildren().add(lbl_lines);
		getChildren().add(ibtn_line_layer);
		getChildren().add(lbl_markers);
		getChildren().add(ibtn_marker_layer);
		getChildren().add(lbl_annotations);
		getChildren().add(ibtn_annotation_layer);
	}
	
	private void addAnimations() {
		fade_animation = new FadeAnimation(this);
		fade_animation.customize(1.0, 0.0, 400, 400);
		
		translate_animation = new TranslateAnimation(this);
		translate_animation.customize(160.0, 0.0, 400, 400);
	}
	

	public void stopVisibilityThread() {
		visibility_thread.interrupt();
	}
	
	private void launchVisibilityThread() {
		visibility_thread = new Thread(() -> {
			while( !visibility_thread.isInterrupted() ) {
				double time = last_active + 1;
				boolean locked = is_locked.get();
				
				while( System.currentTimeMillis() - time < 1750.0 || isHover() ) {
					try { Thread.sleep(50); } 
					catch (InterruptedException e) { return; }
				}
				
				if( last_active > time )
					continue;
				
				if( isVisible() ) {
					if( is_locked.get() ) {
						fade_animation.customize(0.33, getOpacity(), 700, 700);
						fade_animation.playForward();
					} else 
						hide();
				}
				
				while( time == last_active + 1 && locked == is_locked.get() ) {
					try { Thread.sleep(50); }
					catch (InterruptedException e) { return; }
				}
			}
		});
		visibility_thread.start();
	}
	
	public void show() {
		fade_animation.customize(1.0, 0.0, 400, 400);
		fade_animation.playForward();
		translate_animation.playForward();
		last_active = System.currentTimeMillis();
		setVisible(true);
	}
	
	public void hide() {
		fade_animation.customize(1.0, 0.0, 400, 400);
		fade_animation.setOnFinished(ev -> { setVisible(false); fade_animation.setOnFinished(null); });
		fade_animation.playBackward();
		translate_animation.playBackward();
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> _observable, Boolean _old, Boolean _new) {
		if( !_old && _new ) {
			fade_animation.customize(1.0, getOpacity(), 400, 400);
			fade_animation.playForward();
		} else if( _old && !_new )
			last_active = System.currentTimeMillis();
	}
	
	public DoubleStateImageButton getLineLayerBtn() {
		return ibtn_line_layer;
	}
	
	public DoubleStateImageButton getMarkerLayerBtn() {
		return ibtn_marker_layer;
	}
	
	public DoubleStateImageButton getAnnotationLayerBtn() {
		return ibtn_annotation_layer;
	}
}
