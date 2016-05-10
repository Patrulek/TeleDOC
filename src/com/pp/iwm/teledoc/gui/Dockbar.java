package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Dockbar extends Pane {
	
	// =========================================
	// FIELDS
	// =========================================
	
	private List<ImageButton> all_icons;
	private double icon_spacing;
	private double icon_size;
	
	private boolean is_horizontal;
	private double icon_base_scale;
	private double icon_base_opacity;
	private double dist_y;
	// private boolean animOnlyHoveredIcon = true;
	private int old_hovered_icon;
	private int hovered_icon;
	
	
	// ==========================================
	// METHODS
	// ==========================================
	
	public Dockbar(double _icon_size, double _icon_spacing) {
		is_horizontal = true;
		icon_base_scale = 0.75;
		icon_base_opacity = 0.66;
		dist_y = -2.0;
		old_hovered_icon = hovered_icon = -1;
		all_icons = new ArrayList<>();
		
		icon_size = _icon_size;
		icon_spacing = _icon_spacing;

		setStyle("-fx-background-color: transparent;");
		addEventFilter(MouseEvent.MOUSE_MOVED, ev -> onMouseMoved(ev));
	}
	
	public void addIconAndFitInBar(ImageButton _ibtn) {
		all_icons.add(_ibtn);
		fitIcon(_ibtn);
		
		getChildren().add(_ibtn);
		prepareIconForAdditionOrRemoving(_ibtn);
		animateAddition(_ibtn);
	}
	
	private void animateAddition(ImageButton _ibtn) {
		_ibtn.setOpacity(0.0);
		_ibtn.playForwardAnimations();
		_ibtn.customizeFadeAnimation(1.0, icon_base_opacity, 250, 500);
		_ibtn.enableAnimations();
	}
	
	private void animateRemoving(ImageButton _ibtn) {
		_ibtn.getFadeAnimation().setOnFinished(ev -> { getChildren().remove(_ibtn); relocateIcons();} );
		_ibtn.playBackwardAnimations();
		_ibtn.disableAnimations();
	}
	
	private void prepareIconForAdditionOrRemoving(ImageButton _ibtn) {
		_ibtn.disableTranslateAnimation();
		_ibtn.disableScaleAnimation();
		_ibtn.customizeFadeAnimation(0.66, 0.0, 200, 200);
	}
	
	public void removeIcon(int _icon_index) {
		ImageButton ibtn = all_icons.get(_icon_index);
		removeIcon(ibtn);
	}
	
	public void removeIcon(ImageButton _ibtn) {
		all_icons.remove(_ibtn);
		prepareIconForAdditionOrRemoving(_ibtn);
		animateRemoving(_ibtn);
	}
	
	private void fitIcon(ImageButton _ibtn) {
		_ibtn.setScaleX(icon_base_scale);
		_ibtn.setScaleY(icon_base_scale);
		_ibtn.setOpacity(icon_base_opacity);
		_ibtn.customizeZoomAnimation(0.85, icon_base_scale, 250, 500);
		_ibtn.customizeFadeAnimation(1.0, icon_base_opacity, 250, 500);

		double pos = (all_icons.size() - 1) * (icon_size + icon_spacing);
		
		if( is_horizontal ) {
			_ibtn.customizeTranslateAnimation(0.0, dist_y, 250, 500);
			_ibtn.setLayoutX(pos);
		} else {
			_ibtn.customizeTranslateAnimation(-dist_y, 0.0, 250, 500);
			_ibtn.setLayoutY(pos);
		}
	}
	
	private void relocateIcons() {
		for( int i = 0; i < all_icons.size(); i++ ) {
			double pos = i * (icon_size + icon_spacing);
			ImageButton ibtn = all_icons.get(i);
			
			if( is_horizontal )
				ibtn.setLayoutX(pos);
			else
				ibtn.setLayoutY(pos);
		}
	}
	
	private void onMouseMoved(MouseEvent _ev) {
		findHoveredIconIndex(_ev);
	}
	
	private void findHoveredIconIndex(MouseEvent _ev) {
		old_hovered_icon = hovered_icon;
		
		if( is_horizontal )
			findHoveredIconIndexIfHorizontal(_ev);
		else
			findHoveredIconIndexIfVertical(_ev);
	}
	
	private void findHoveredIconIndexIfHorizontal(MouseEvent _ev) {
		if( hovered_icon == -1 ) {
			if( _ev.getX() >= 6 && _ev.getX() < (6 + all_icons.size() * (icon_size + icon_spacing)) )
				hovered_icon = (int)((_ev.getX() - 6) / (icon_size + icon_spacing ));
		} else {
			if( _ev.getX() < 26 + hovered_icon * (icon_size + icon_spacing) ) { 
				if( _ev.getX() < 4 )
					hovered_icon = -1;
				else
					hovered_icon = (int)((_ev.getX() - 4) / (icon_size + icon_spacing));
			} else {
				if( _ev.getX() >= 6 + all_icons.size() * (icon_size + icon_spacing) )
					hovered_icon = -1;
				else
					hovered_icon = (int)((_ev.getX() - 6) / (icon_size + icon_spacing));
			}
		}
	}
	
	private void findHoveredIconIndexIfVertical(MouseEvent _ev) {
		if( hovered_icon == -1 ) {
			if( _ev.getY() >= 6 && _ev.getY() < (6 + all_icons.size() * (icon_size + icon_spacing)) )
				hovered_icon = (int)((_ev.getY() - 6) / (icon_size + icon_spacing ));
		} else {
			if( _ev.getY() < 26 + hovered_icon * (icon_size + icon_spacing) ) { 
				if( _ev.getY() < 4 )
					hovered_icon = -1;
				else
					hovered_icon = (int)((_ev.getY() - 4) / (icon_size + icon_spacing));
			} else {
				if( _ev.getY() >= 6 + all_icons.size() * (icon_size + icon_spacing) )
					hovered_icon = -1;
				else
					hovered_icon = (int)((_ev.getY() - 6) / (icon_size + icon_spacing));
			}
		}
	}
	
	public int getHoveredIconIndex() {
		return hovered_icon;
	}
	
	public int getOldHoveredIconIndex() {
		return old_hovered_icon;
	}
	
	public ImageButton getHoveredIcon() {
		if( hovered_icon >= 0 )
			return all_icons.get(hovered_icon);
		
		return null;
	}
	
	public void resetHoveredIndex() {
		old_hovered_icon = hovered_icon = -1;
	}
	
	public List<ImageButton> getIcons() {
		return all_icons;
	}
	
	public ImageButton findIcon(int action) {
		for( ImageButton ibtn : all_icons )
			if( ibtn.getAction() == action )
				return ibtn;
		
		return null;
	}
	
	public void setVertical() {
		if( !is_horizontal )
			return;
		
		is_horizontal = false;
		relocateIcons();
	}
	
	public void setHorizontal() {
		if( is_horizontal )
			return;
		
		is_horizontal = true;
		relocateIcons();
	}
	
	/*public interface onOrientationChanged {
		public void orientationChanged(boolean _orientation, double _width, double _height);
	}*/
}
