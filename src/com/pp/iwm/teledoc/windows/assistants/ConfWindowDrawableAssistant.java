package com.pp.iwm.teledoc.windows.assistants;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.drawables.DrawableObject;
import com.pp.iwm.teledoc.drawables.Annotation.State;
import com.pp.iwm.teledoc.drawables.DrawableObject.DrawableObjectListener;
import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.gui.DrawablePane.DrawablePaneListener;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.models.ConfWindowModel;
import com.pp.iwm.teledoc.models.ConfWindowModel.UserContext;
import com.pp.iwm.teledoc.windows.ConfWindow;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ConfWindowDrawableAssistant implements DrawablePaneListener, DrawableObjectListener  {
	
	// ==================================
	// FIELDS
	// ==================================

	private ConfWindow window;
	private ConfWindowModel model;
	private ConfWindowLayout layout;
	
	// ==================================
	// METHODS
	// ==================================
	
	public ConfWindowDrawableAssistant(ConfWindow _window) {
		window = _window;
		model = window.getWindowModel();
		layout = window.getWindowLayout();
	}
	
	public void createTempAnnotation() {
		DrawablePane drawable_pane = layout.drawable_pane;
		model.temp_annotation = new Annotation("Tutaj wpisz tekst", Color.GREEN, drawable_pane.getBoundsInParent(), model.temp1, drawable_pane);
		addDrawable(model.temp_annotation);
	}
	
	public void addDrawable(DrawableObject _obj) {
		model.drawables.add(_obj);
		_obj.setListener(this);
		
		if( _obj instanceof DrawableLine )
			addLine((DrawableLine)_obj);
		else if( _obj instanceof DrawableBrokenLine )
			addBrokenLine((DrawableBrokenLine)_obj);
		else if( _obj instanceof Annotation )
			addAnnotation((Annotation)_obj);
		
		layout.drawable_pane.addDrawable(_obj);
	}
	
	public void removeDrawable(DrawableObject _obj) {
		model.drawables.remove(_obj);
		
		if( _obj instanceof DrawableLine )
			removeLine((DrawableLine)_obj);
		else if( _obj instanceof DrawableBrokenLine )
			removeBrokenLine((DrawableBrokenLine)_obj);
		else if( _obj instanceof Annotation )
			removeAnnotation((Annotation)_obj);
		
		layout.drawable_pane.removeDrawable(_obj);
	}
	
	private void addLine(DrawableLine _line) {
		model.line_layer.add(_line.getLine());
	}
	
	private void removeLine(DrawableLine _line) {
		model.line_layer.remove(_line.getLine());
	}
	
	private void addBrokenLine(DrawableBrokenLine _line) {
		model.line_layer.addAll(_line.getLines());
	}
	
	private void removeBrokenLine(DrawableBrokenLine _line) {
		model.line_layer.removeAll(_line.getLines());
	}
	
	private void addAnnotation(Annotation _annotation) {
		model.annotation_layer.add(_annotation.getCircle());
	}
	
	private void removeAnnotation(Annotation _annotation) {
		model.annotation_layer.remove(_annotation.getCircle());
		layout.annotation_pane.hide();
	}
	
	public void updateBrokenLine(Line _line) {
		model.line_layer.add(_line);
		model.temp_broken_line.addLine(_line);
		layout.drawable_pane.updateBrokenLine(_line);
	}
	
	@Override
	public void onRedrawLayers() {
		layout.drawable_pane.setLineLayer(model.line_layer);
		layout.drawable_pane.setMarkerLayer(model.marker_layer);
		layout.drawable_pane.setAnnotationLayer(model.annotation_layer);
	}

	@Override
	public void onRescalePane() {
		layout.drawable_pane.setDrawables(model.drawables);
		Platform.runLater(() -> layout.annotation_pane.refresh());
	}

	@Override
	public void onMouseEntered(DrawableObject _obj) {
		model.hovered_drawable = _obj; 
		
		if( _obj instanceof Annotation ) {
			Annotation ann = (Annotation)_obj;
			
			if( ann.getState() == State.DRAWN && !ann.isDragged() && !ann.isSelected() )
				layout.annotation_text_pane.showForAnnotation(ann);
		}
	}

	@Override
	public void onMouseExited(DrawableObject _obj) {
		model.hovered_drawable = null;
		
		if( _obj instanceof Annotation ) {
			Annotation ann = (Annotation)_obj;
			
			if( ann.getState() == State.DRAWN && !ann.isDragged() && !ann.isSelected() )
				layout.annotation_text_pane.hide();
		}
	}

	@Override
	public void onSelected(DrawableObject _obj) {
		deselectObject();
		selectObject(_obj);
	}
	
	public void selectObject(DrawableObject _obj) {
		model.selected_drawable = _obj;
		layout.drawable_pane.setSelectedDrawable(_obj);
		
		if( _obj instanceof Annotation ) {
			Annotation ann = (Annotation)_obj;
			layout.annotation_pane.showForAnnotation(ann);
			layout.annotation_text_pane.hide();
		}
	}
	
	public void deselectObject() {
		if( model.selected_drawable != null ) {
			model.selected_drawable.onDeselected();
			
			if( model.selected_drawable instanceof Annotation )
				layout.annotation_pane.hide();
		}
		
		model.selected_drawable = null;
	}

	@Override
	public void onChanged(DrawableObject _obj) {
		if( _obj instanceof Annotation ) {
			Annotation ann = (Annotation)_obj;
			
			if( ann.isDragged() ) {
				layout.annotation_text_pane.hide();
				
				if( ann.isSelected() ) 
					layout.annotation_pane.showForAnnotation(ann);
			}
			else if( ann.getCircle().isHover() ) {
				if( !ann.isSelected() )
					layout.annotation_text_pane.showForAnnotation(ann);
			} else
				ann.setOriginalColor();
		}
	}
	
	@Override
	public Point2D onDragged(DrawableObject _obj) {
		Point2D image_mouse_pos_delta = model.getMouseDelta();
		image_mouse_pos_delta = image_mouse_pos_delta.multiply(1.0 / layout.drawable_pane.getScale());
		return image_mouse_pos_delta;
	}
}
