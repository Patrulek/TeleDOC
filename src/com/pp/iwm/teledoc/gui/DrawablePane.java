package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.drawables.DrawableObject;
import com.pp.iwm.teledoc.drawables.Pointer;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.objects.Member;
import com.pp.iwm.teledoc.windows.ConfWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class DrawablePane extends Pane implements ChangeListener<Boolean> {
	
	// =====================================
	// FIELDS 
	// =====================================

	// layer0 - image | layer1 - (broken) lines | layer2 - marker and distance pointer | layer3 - annotations
	
	public enum Layers {
		LINES, MARKERS, ANNOTATIONS;
	}
	
	private DrawableCanvas drawable_canvas;
	private double scale;
	private ConfWindowLayout layout;
	private List<Node> line_layer;
	private List<Node> marker_layer;
	private List<Node> annotation_layer;
	private List<Pointer> pointers;
	private List<DrawableObject> drawables;
	private DrawablePaneListener listener;
	
	private BooleanProperty lineLayerVisibilityProperty;
	private BooleanProperty markerLayerVisibilityProperty;
	private BooleanProperty annotationLayerVisibilityProperty;
	
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
		pointers = new ArrayList<>();
		
		lineLayerVisibilityProperty = new SimpleBooleanProperty(true);
		markerLayerVisibilityProperty = new SimpleBooleanProperty(true);
		annotationLayerVisibilityProperty = new SimpleBooleanProperty(true);
		
		lineLayerVisibilityProperty.addListener(this);
		markerLayerVisibilityProperty.addListener(this);
		annotationLayerVisibilityProperty.addListener(this);
		
		layoutBoundsProperty().addListener((observable, old_v, new_v) -> {
			if(old_v.getWidth() == 0.0 || old_v.getHeight() == 0.0)
				listener.onBoundsChanged();
		});
		
		createLayout();
	}
	
	public BooleanProperty getLineLayerVisibilityProperty() {
		return lineLayerVisibilityProperty;
	}
	
	public BooleanProperty getMarkerLayerVisibilityProperty() {
		return markerLayerVisibilityProperty;
	}
	
	public BooleanProperty getAnnotationLayerVisibilityProperty() {
		return annotationLayerVisibilityProperty;
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
	
	
	public void setImageAndResetCanvas(Image _img) {
		drawable_canvas.setImageAndResetCanvas(_img);
		scale = drawable_canvas.getScale();
	}
	
	public Point2D getImageSize() {
		return drawable_canvas.getImageSize();
	}
	
	
	public void setViewportBounds(Bounds _viewport_bounds) {
		drawable_canvas.setViewportBounds(_viewport_bounds);
	}
	
	public void rescaleBy(double _rescale_by) {
		drawable_canvas.rescaleBy(_rescale_by);
		double new_scale = drawable_canvas.getScale();
		
		if( new_scale == scale )
			return;

		
		double prev_h = layout.scroll_pane.getHvalue();
		double prev_v = layout.scroll_pane.getVvalue();
		
		scale = new_scale;
		resize(drawable_canvas.getWidth(), drawable_canvas.getHeight());
		
		// TODO temp solution
		layout.scroll_pane.setHvalue((7 * prev_h + layout.scroll_pane.getHvalue()) / 8.0);
		layout.scroll_pane.setVvalue((7 * prev_v + layout.scroll_pane.getVvalue()) / 8.0);

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
		
		if( temp_line_selectors != null )
			removeLineSelectors();
	}
	
	private void removeLineSelectors() {
		getChildren().removeAll(temp_line_selectors[0], temp_line_selectors[1]);
		temp_line_selectors = null;
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
	
	public void setLineLayer(List<Node> _line_layer) {
		line_layer = _line_layer;
	}
	
	public void setMarkerLayer(List<Node> _marker_layer) {
		marker_layer = _marker_layer;
	}
	
	public void setAnnotationLayer(List<Node> _annotation_layer) {
		annotation_layer = _annotation_layer;
	}
	
	private void redrawLayers(Layers _changed_layer) {
		// TODO optymalizacja
		listener.onRedrawLayers();
		
		switch( _changed_layer ) {
			case LINES:
				disableLineLayer();
				hideLineSelectors();
				
				if( lineLayerVisibilityProperty.get() ) {
					enableLineLayer();
					showLineSelectors();
					annotationLayerToFront();
				}
				
				break;
			case MARKERS:
				disableMarkerLayer();
				
				if( markerLayerVisibilityProperty.get() ) {
					enableMarkerLayer();
					annotationLayerToFront();
				}
				
				break;
			case ANNOTATIONS:
				disableAnnotationLayer();
				
				if( annotationLayerVisibilityProperty.get() ) {
					enableAnnotationLayer();
					annotationLayerToFront();
				}
				
				break;
		}
		
		selectorsToFront();
	}
	
	private void selectorsToFront() {
		if( temp_line_selectors != null ) {
			temp_line_selectors[0].toFront();
			temp_line_selectors[1].toFront();
		}
	}
	
	private void enableLineLayer() {
		getChildren().addAll(line_layer);
	}
	
	private void disableLineLayer() {
		getChildren().removeAll(line_layer);
	}
	
	private void enableMarkerLayer() {
		getChildren().addAll(marker_layer);
	}
	
	private void disableMarkerLayer() {
		getChildren().removeAll(marker_layer);
	}
	
	private void enableAnnotationLayer() {
		getChildren().addAll(annotation_layer);
	}
	
	private void disableAnnotationLayer() {
		getChildren().removeAll(annotation_layer);
	}
	
	private int findPointerIdx(String _member_mail) {
		for( int i = 0; i < pointers.size(); i++ )
			if( pointers.get(i).getMemberEmail().equals(_member_mail) )
				return i;
		
		return -1;
	}
	
	public void showPointerFor(String _member_mail) {
		int pointer_idx = findPointerIdx(_member_mail);
		
		if( pointer_idx == -1 ) {
			pointers.add(new Pointer(this, _member_mail));
			pointer_idx = pointers.size() - 1;
			
			Member m = User.instance().findMemberInCurrentConference(_member_mail);
			
			if( m != null ) {
				String member_name = m.name + " " + m.surname;
				pointers.get(pointer_idx).setMember(member_name);
			}
			
			Platform.runLater(() -> {
				int pointer_idx2 = pointers.size() - 1;
				pointers.get(pointer_idx2).addToPane();
			});
		}
		
		pointers.get(pointer_idx).show();
	}
	
	public void hidePointerFor(String _member_mail) {
		int pointer_idx = findPointerIdx(_member_mail);
		
		if( pointer_idx != -1 )
			pointers.get(pointer_idx).hide();
	}
	
	public void relocatePointerFor(String _member_mail, Point2D _new_pos) {
		Platform.runLater(() -> {
			int pointer_idx = findPointerIdx(_member_mail);
			
			if( pointer_idx != -1 )
				pointers.get(pointer_idx).relocate(_new_pos);
		});
	}
	
	public void refreshPointers() {
		for( Pointer p : pointers )
			p.refresh();
	}
	
	public interface DrawablePaneListener {
		public void onRedrawLayers();
		public void onRescalePane();
		public void onBoundsChanged();
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> _observable, Boolean _old, Boolean _new) {
		if( lineLayerVisibilityProperty == _observable )
			redrawLayers(Layers.LINES);
		else if( markerLayerVisibilityProperty == _observable )
			redrawLayers(Layers.MARKERS);
		else if( annotationLayerVisibilityProperty == _observable )
			redrawLayers(Layers.ANNOTATIONS);
	}

	public void setImageIdForPointer(String _email, int _img_id) {
		int pointer_idx = findPointerIdx(_email);
		
		if( pointer_idx != -1 )
			pointers.get(pointer_idx).setImgId(_img_id);
	}

	public void refreshObjects() {
		for( DrawableObject drawable : drawables ) {
			drawable.hideIfOtherImage();
			drawable.showIfSameImage();
		}
	}
}
