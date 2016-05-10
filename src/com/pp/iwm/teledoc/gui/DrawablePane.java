package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.drawables.DrawableObject;
import com.pp.iwm.teledoc.windows.ConfWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

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
	private Window window;
	
	private List<DrawableObject> line_layer;
	private List<DrawableObject> marker_layer;
	private List<DrawableObject> annotation_layer;
	
	// =====================================
	// METHODS
	// =====================================
	
	public DrawablePane(Window _window) {
		super();
		window = _window;
		line_layer = new ArrayList<>();
		marker_layer = new ArrayList<>();
		annotation_layer = new ArrayList<>();
		createLayout();
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
		resize(scale * 2560.0, scale * 1440.0);
		
		for( DrawableObject object : line_layer ) 
			object.rescale();
		
		for( DrawableObject object : marker_layer )
			object.rescale();
		
		for( DrawableObject object : annotation_layer )
			object.rescale();
	}
	
	public double getScale() {
		return drawable_canvas.getScale();
	}
	
	public void addLine(DrawableLine _line) {
		line_layer.add(_line);
		getChildren().add(_line.getLine());
	}
	
	public void addBrokenLine(DrawableBrokenLine _broken_line) {
		line_layer.add(_broken_line);
	}
	
	public void updateBrokenLine(Line _line) {
		getChildren().add(_line);
	}
	
	public void addAnnotation(Annotation _annotation) {
		annotation_layer.add(_annotation);
		getChildren().add(_annotation.getCircle());
	}
	
	public void removeAnnotation(Annotation _annotation) {
		getChildren().remove(_annotation.getCircle());
		annotation_layer.remove(_annotation);
	}
	
	public void changeLayer(LayersToDraw _layers_to_draw) {
		layers_to_draw = _layers_to_draw;
		redrawLayers();
	}
	
	private void redrawLayers() {
		// TODO optymalizacja
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
	
	//TODO
	private List<Node> lineLayer() {
		List<Node> layer = new ArrayList<>();
		
		for( DrawableObject drawable : line_layer ) {
			if( drawable instanceof DrawableLine ) {
				DrawableLine line = (DrawableLine) drawable;
				layer.add(line.getLine());
			} else {
				DrawableBrokenLine broken_line = (DrawableBrokenLine)drawable;
				layer.addAll(broken_line.getLines());
			}
		}
			
		return layer;
	}
	
	private List<Node> markerLayer() {
		List<Node> layer = new ArrayList<>();
		
		return layer;
	}
	
	private List<Node> annotationLayer() {
		List<Node> layer = new ArrayList<>();
		
		for( DrawableObject drawable : annotation_layer ) {
			Annotation annotation = (Annotation)drawable;
			layer.add(annotation.getCircle());
		}
		
		return layer;
	}
	
	private void disableAllLayers() {
		getChildren().removeAll(lineLayer());
		getChildren().removeAll(markerLayer());
		getChildren().removeAll(annotationLayer());
	}
	
	private void enableAllLayers() {
		enableLineLayer();
		enableMarkerLayer();
		enableAnnotationLayer();
	}
	
	private void enableLineLayer() {
		List<Node> lines = lineLayer();
		getChildren().addAll(lines);
	}
	
	private void enableMarkerLayer() {
		List<Node> markers = markerLayer();
		getChildren().addAll(markers);
	}
	
	private void enableAnnotationLayer() {
		List<Node> annotations = annotationLayer();
		getChildren().addAll(annotations);
	}
	
	public ConfWindow getConfWindow() {
		return (ConfWindow)window;
	}
}
