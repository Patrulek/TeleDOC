package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.drawables.DrawableObject;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.windows.ConfWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class DrawablePane extends Pane {
	
	// =====================================
	// FIELDS 
	// =====================================

	// layer0 - image | layer1 - (broken) lines | layer2 - marker and distance pointer | layer3 - annotations
	
	public enum LayersToDraw {
		ONLY_IMAGE, LINES, MARKERS, ANNOTATIONS, LINES_AND_MARKERS, LINES_AND_ANNOTATIONS, MARKERS_AND_ANNOTATIONS, DRAW_ALL;
	}
	
	private DrawableCanvas drawable_canvas;
	private double scale;
	private LayersToDraw layers_to_draw;
	private ConfWindowLayout layout;
	private List<Node> line_layer;
	private List<Node> marker_layer;
	private List<Node> annotation_layer;
	private List<DrawableObject> drawables;
	private DrawablePaneListener listener;
	
	private DrawableObject selected_drawable;
	private Rectangle[] temp_line_selectors;
	
	// =====================================
	// METHODS
	// =====================================
	
	public DrawablePane(ConfWindowLayout _layout) {
		super();
		layout = _layout;
		line_layer = new ArrayList<>();
		marker_layer = new ArrayList<>();
		annotation_layer = new ArrayList<>();
		drawables = new ArrayList<>();
		createLayout();
	}
	
	public void setListener(DrawablePaneListener _listener) {
		listener = _listener;
	}
	
	public void refreshSelectors() {
		hideLineSelectors();
		showLineSelectors();
	}
	
	private void showLineSelectors() {
		if( temp_line_selectors != null )
			getChildren().addAll(temp_line_selectors[0], temp_line_selectors[1]);
	}
	
	private void hideLineSelectors() {
		if( temp_line_selectors != null )
			getChildren().removeAll(temp_line_selectors[0], temp_line_selectors[1]);
	}
	
	public void setSelectedDrawable(DrawableObject _drawable) {
		if( selected_drawable == _drawable )
			return;
		
		selected_drawable = _drawable;
		hideLineSelectors();
		
		if( _drawable == null )
			temp_line_selectors = null;
		else if( _drawable instanceof DrawableLine )
			temp_line_selectors = ((DrawableLine)_drawable).getSelectors();
		else if( _drawable instanceof DrawableBrokenLine )
			temp_line_selectors = ((DrawableBrokenLine)_drawable).getSelectors();
		
		showLineSelectors();
	}
	
	private void createLayout() {
		drawable_canvas = new DrawableCanvas();
		getChildren().add(drawable_canvas);
	}
	
	
	public void setImageAndResetCanvas(int _image_key) {
		drawable_canvas.setImageAndResetCanvas(_image_key);
	}
	
	
	public void setViewportBounds(Bounds _viewport_bounds) {
		drawable_canvas.setViewportBounds(_viewport_bounds);
	}
	
	public void rescaleBy(double _rescale_by) {
		drawable_canvas.rescaleBy(_rescale_by);
		scale = drawable_canvas.getScale();
		resize(scale * 2560.0, scale * 1440.0);		// TODO
		listener.onRescalePane();
		
		for( DrawableObject drawable : drawables ) {
			drawable.setScale(scale);
			drawable.rescale();
		}
	}
	
	public void setDrawables(List<DrawableObject> _drawables) {
		drawables = _drawables;
	}
	
	public double getScale() {
		return drawable_canvas.getScale();
	}
	
	public void addDrawable(DrawableObject _drawable) {
		if( _drawable instanceof DrawableLine )
			getChildren().add(((DrawableLine)_drawable).getLine());
		else if( _drawable instanceof DrawableBrokenLine )
			getChildren().addAll(((DrawableBrokenLine)_drawable).getLines());
		else if( _drawable instanceof Annotation )
			getChildren().add(((Annotation)_drawable).getCircle());
		
		annotationLayerToFront();
	}
	
	public void updateBrokenLine(Line _line) {
		getChildren().add(_line);
		annotationLayerToFront();
	}
	
	public void removeDrawable(DrawableObject _drawable) {
		if( _drawable instanceof DrawableLine )
			getChildren().remove(((DrawableLine)_drawable).getLine());
		else if( _drawable instanceof DrawableBrokenLine )
			getChildren().removeAll(((DrawableBrokenLine)_drawable).getLines());
		else if( _drawable instanceof Annotation )
			getChildren().remove(((Annotation)_drawable).getCircle());
	}
	
	private void annotationLayerToFront() {
		for( Node node : annotation_layer )
			if( node instanceof Circle )
				((Circle)node).toFront();
	}
	
	public void removeAnnotation(Annotation _annotation) {
		getChildren().remove(_annotation.getCircle());
		annotation_layer.remove(_annotation);
	}
	
	public void changeLayer(LayersToDraw _layers_to_draw) {
		layers_to_draw = _layers_to_draw;
		redrawLayers();
	}
	
	public void setLineLayer(List<Node> _line_layer) {
		line_layer = _line_layer;
	}
	
	public void setMarkerLayer(List<Node> _marker_layer) {
		marker_layer = _marker_layer;
	}
	
	public void setAnnotationLayer(List<Node> _annotation_layer) {
		annotation_layer = _annotation_layer;
	}
	
	private void redrawLayers() {
		// TODO optymalizacja
		listener.onRedrawLayers();
		
		disableAllLayers();
		switch( layers_to_draw ) {
			case LINES:
				enableLineLayer();
				break;
			case MARKERS:
				enableMarkerLayer();
				break;
			case ANNOTATIONS:
				enableAnnotationLayer();
				break;
			case LINES_AND_MARKERS:
				enableLineLayer();
				enableMarkerLayer();
				break;
			case LINES_AND_ANNOTATIONS:
				enableLineLayer();
				enableAnnotationLayer();
				break;
			case MARKERS_AND_ANNOTATIONS:
				enableMarkerLayer();
				enableAnnotationLayer();
				break;
			case DRAW_ALL:
				enableAllLayers();
				break;
		}
	}
	
	private void disableAllLayers() {
		getChildren().removeAll(line_layer);
		getChildren().removeAll(marker_layer);
		getChildren().removeAll(annotation_layer);
	}
	
	private void enableAllLayers() {
		enableLineLayer();
		enableMarkerLayer();
		enableAnnotationLayer();
	}
	
	private void enableLineLayer() {
		getChildren().addAll(line_layer);
	}
	
	private void enableMarkerLayer() {
		getChildren().addAll(marker_layer);
	}
	
	private void enableAnnotationLayer() {
		getChildren().addAll(annotation_layer);
	}
	
	public interface DrawablePaneListener {
		public void onRedrawLayers();
		public void onRescalePane();
	}
}
