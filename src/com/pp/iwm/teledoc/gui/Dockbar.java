package com.pp.iwm.teledoc.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Dockbar extends Pane {
	
	List<ImageButton> all_icons = null;
	List<ImageButton> viewed_icons = null;
	int max_icons = 15;
	int icon_spacing = 4; // pixels
	int icon_pref_size = 32;
	boolean horizontal_bar = true;
	double iconBaseScale = 0.75;
	double iconBaseOpacity = 0.5;
	boolean animOnlyHoveredIcon = true;
	
	Point mouse_pos = null;
	Rectangle r_for_moving = null;
	
	public Dockbar() {
		this.all_icons = new ArrayList<>();
		this.viewed_icons = new ArrayList<>();
		
		r_for_moving = new Rectangle(10, 32);
		r_for_moving.setFill(Utils.ICONS_COLOR);
		
		r_for_moving.setOnMousePressed(event -> {
											mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY());
											
											//double diff = event.getSceneX() - this.getLayoutX();
											//System.out.println(event.getSceneX() + " | " + this.getLayoutX());
											//int selected_icon = (int) (diff / (icon_pref_size + icon_spacing));
										});
		r_for_moving.setOnMouseDragged(event -> {
											this.setLayoutX(this.getLayoutX() + event.getScreenX() - mouse_pos.x);
											this.setLayoutY(this.getLayoutY() + event.getScreenY() - mouse_pos.y);
											mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY());
										}); 
		
		this.getChildren().add(r_for_moving);
	}
	
	public void addIcon(ImageButton btn) {
		all_icons.add(btn);
		fitIcon(btn);
		relocateNavigationRectangle();
		
		
		if( viewed_icons.size() < max_icons )
			viewed_icons.add(btn);
		else
			btn.setVisible(false);
		
		this.getChildren().add(btn);
	}
	
	public void removeIcon(ImageButton btn) {
		all_icons.remove(btn);
		viewed_icons.remove(btn);
		this.getChildren().remove(btn);
		
		btn.setVisible(false);
		
		relocateNavigationRectangle();
		
		// recompute dockbar
	}
	
	private void fitIcon(ImageButton btn) {
		btn.setScaleX(iconBaseScale);
		btn.setScaleY(iconBaseScale);
		btn.setOpacity(iconBaseOpacity);
		btn.customizeZoomAnimation(1.15, iconBaseScale, 250, 500);
		btn.customizeFadeAnimation(1.0, iconBaseOpacity, 250, 500);

		int x = (all_icons.size() - 1) * (icon_pref_size + icon_spacing);
		btn.setLayoutX(x);
	}
	
	private void relocateNavigationRectangle() {
		int x = (int) (all_icons.size() * (icon_pref_size + icon_spacing) + 0.25 * icon_pref_size);
		r_for_moving.setLayoutX(x);
	}
}
