package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Dockbar extends Pane {
	
	// =========================================
	// FIELDS
	// =========================================
	
	private List<ImageButton> all_icons = null;
	private double icon_spacing;
	private double icon_size;
	
	// private boolean horizontal_bar = true;
	private double icon_base_scale = 0.75;
	private double icon_base_opacity = 0.5;
	private double dist_y = -2.0;
	// private boolean animOnlyHoveredIcon = true;
	private int old_selected_icon = -1;
	private int selected_icon = -1;
	
	
	// ==========================================
	// METHODS
	// ==========================================
	
	public Dockbar(double _icon_size, double _icon_spacing) {
		icon_size = _icon_size;
		icon_spacing = _icon_spacing;
		all_icons = new ArrayList<>();

		setStyle("-fx-background-color: transparent;");

		addEventFilter(MouseEvent.MOUSE_MOVED, ev -> onMouseMoved(ev));
	}
	
	public void addIcon(ImageButton _ibtn) {
		all_icons.add(_ibtn);
		fitIcon(_ibtn);
		
		getChildren().add(_ibtn);
		animateAddition(_ibtn, true);
	}
	
	private void animateAddition(ImageButton _ibtn, boolean _in) {
		_ibtn.enableTranslateAnimation(false);
		_ibtn.enableZoomAnimation(false);
		_ibtn.customizeFadeAnimation(0.5, 0.0, 200, 200);
		
		if( _in ) {
			_ibtn.setOpacity(0.0);
			_ibtn.playAnimations(true);
		} else {
			_ibtn.getFadeAnim().setOnFinished(ev -> { getChildren().remove(_ibtn); relocateIcons();} );
			_ibtn.playAnimations(false);
		}
		
		_ibtn.customizeFadeAnimation(1.0, icon_base_opacity, 250, 500);
		_ibtn.enableAnimations(true);
	}
	
	public void removeIcon(int _icon_index) {
		ImageButton ibtn = all_icons.get(_icon_index);
		removeIcon(ibtn);
	}
	
	public void removeIcon(ImageButton _ibtn) {
		all_icons.remove(_ibtn);
		animateAddition(_ibtn, false);
	}
	
	private void fitIcon(ImageButton _btn) {
		_btn.setScaleX(icon_base_scale);
		_btn.setScaleY(icon_base_scale);
		_btn.setOpacity(icon_base_opacity);
		_btn.customizeZoomAnimation(0.85, icon_base_scale, 250, 500);
		_btn.customizeFadeAnimation(1.0, icon_base_opacity, 250, 500);
		_btn.customizeTranslateAnimation(0.0, dist_y, 250, 500);

		double x = (all_icons.size() - 1) * (icon_size + icon_spacing);
		_btn.setLayoutX(x);
	}
	
	private void relocateIcons() {
		int i = 0;
		
		for( ImageButton ibtn : all_icons ) {
			double x = i * (icon_size + icon_spacing);
			ibtn.setLayoutX(x);
			i++;
		}
	}
	
	private void onMouseMoved(MouseEvent _ev) {
		old_selected_icon = selected_icon;
		
		if( selected_icon == -1 ) {
			if( _ev.getX() >= 6 && _ev.getX() < (6 + all_icons.size() * (icon_size + icon_spacing)) )
				selected_icon = (int)((_ev.getX() - 6) / (icon_size + icon_spacing ));
		} else {
			if( _ev.getX() < 26 + selected_icon * (icon_size + icon_spacing) ) { 
				if( _ev.getX() < 4 )
					selected_icon = -1;
				else
					selected_icon = (int)((_ev.getX() - 4) / (icon_size + icon_spacing));
			} else {
				if( _ev.getX() >= 6 + all_icons.size() * (icon_size + icon_spacing) )
					selected_icon = -1;
				else
					selected_icon = (int)((_ev.getX() - 6) / (icon_size + icon_spacing));
			}
		}
	}
	
	public int getSelectedIconIndex() {
		return selected_icon;
	}
	
	public int getOldSelectedIconIndex() {
		return old_selected_icon;
	}
	
	public ImageButton getSelectedIcon() {
		if( selected_icon >= 0 )
			return all_icons.get(selected_icon);
		
		return null;
	}
	
	public void resetSelection() {
		old_selected_icon = selected_icon = -1;
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
}
