package com.pp.iwm.teledoc.windows.assistants;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.drawables.DrawableObject;
import com.pp.iwm.teledoc.drawables.Annotation.State;
import com.pp.iwm.teledoc.drawables.DrawableObject.DrawableObjectListener;
import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.gui.DrawablePane.DrawablePaneListener;
import com.pp.iwm.teledoc.gui.DrawablePane.LayersToDraw;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.models.ConfWindowModel;
import com.pp.iwm.teledoc.models.ConfWindowModel.UserContext;
import com.pp.iwm.teledoc.windows.ConfWindow;

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
	
	public void changeCanvasLayerVisibility(LayersToDraw _layers_to_draw) {
		layout.drawable_pane.changeLayer(_layers_to_draw);
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
	
	private void addLine(DrawableLine _line) {
		model.line_layer.add(_line.getLine());
	}
	
	private void addBrokenLine(DrawableBrokenLine _line) {
		model.line_layer.addAll(_line.getLines());
	}
	
	private void addAnnotation(Annotation _annotation) {
		model.annotation_layer.add(_annotation.getCircle());
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
	}

	@Override
	public void onMouseEntered(DrawableObject _obj) {
		model.hovered_drawable = _obj; 
	}

	@Override
	public void onMouseExited(DrawableObject _obj) {
		model.hovered_drawable = null;
	}

	@Override
	public void onSelected(DrawableObject _obj) {
		deselectObject();
		selectObject(_obj);
	}
	
	public void selectObject(DrawableObject _obj) {
		model.selected_drawable = _obj;
	}
	
	public void deselectObject() {
		if( model.selected_drawable != null )
			model.selected_drawable.onDeselected();
		
		model.selected_drawable = null;
	}

	@Override
	public void onChanged(DrawableObject _obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Point2D onDragged(DrawableObject _obj) {
		Point2D image_mouse_pos_delta = model.getMouseDelta();
		image_mouse_pos_delta = image_mouse_pos_delta.multiply(1.0 / layout.drawable_pane.getScale());
		return image_mouse_pos_delta;
	}
}
