package com.pp.iwm.teledoc.gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.windows.AppWindow;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Dockbar extends Pane {
	
	public AppWindow app_window;
	List<DockImageButton> all_icons = null;
	int max_icons = 15;
	int icon_spacing = 4; // pixels
	int icon_pref_size = 32;
	boolean horizontal_bar = true;
	double iconBaseScale = 0.75;
	double iconBaseOpacity = 0.5;
	boolean animOnlyHoveredIcon = true;
	int selected_icon = -1;
	
	
	Point mouse_pos = null;
	Rectangle r_for_moving = null;
	
	Point lu_bound = null;
	Point rd_bound = null;
	
	public Dockbar() {
		all_icons = new ArrayList<>();
		
		lu_bound = new Point(0, 32);
		rd_bound = new Point(1024, 580);

		
		r_for_moving = new Rectangle(10, 36);
		r_for_moving.setFill(Utils.PRIMARY_VERY_LIGHT_COLOR);
		r_for_moving.setOpacity(0.5);
		
		r_for_moving.setOnMousePressed(event -> {
											mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY());
											
											//double diff = event.getSceneX() - getLayoutX();
											//System.out.println(event.getSceneX() + " | " + getLayoutX());
											//int selected_icon = (int) (diff / (icon_pref_size + icon_spacing));
										});
		r_for_moving.setOnMouseDragged(event -> {
											double x = getLayoutX() + event.getScreenX() - mouse_pos.x;
											x = x + getWidth() > rd_bound.x ? rd_bound.x - getWidth() : x;
											x = x < lu_bound.x ? lu_bound.x : x;
											
											double y = getLayoutY() + event.getScreenY() - mouse_pos.y;
											y = y + getHeight() > rd_bound.y ? rd_bound.y - getHeight() : y;
											y = y < lu_bound.y ? lu_bound.y : y;
											
											setLayoutX(x);
											setLayoutY(y);
											mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY());
										}); 
		
		//getChildren().add(r_for_moving);
		

	}
	
	public void addIcon(DockImageButton _btn) {
		all_icons.add(_btn);
		fitIcon(_btn);
		relocateNavigationRectangle();
		
		getChildren().add(_btn);
	}
	
	public void removeIcon(DockImageButton _btn) {
		all_icons.remove(_btn);
		getChildren().remove(_btn);
		
		_btn.setVisible(false);
		
		relocateNavigationRectangle();
		
		// recompute dockbar
	}
	
	private void fitIcon(ImageButton _btn) {
		_btn.setScaleX(iconBaseScale);
		_btn.setScaleY(iconBaseScale);
		_btn.setOpacity(iconBaseOpacity);
		_btn.customizeZoomAnimation(1.15, iconBaseScale, 250, 500);
		_btn.customizeFadeAnimation(1.0, iconBaseOpacity, 250, 500);

		int x = (all_icons.size() - 1) * (icon_pref_size + icon_spacing);
		_btn.setLayoutX(x);
	}
	
	private void relocateNavigationRectangle() {
		int x = (int) (all_icons.size() * (icon_pref_size + icon_spacing) + 0.6 * icon_pref_size);
		r_for_moving.setLayoutX(x);
	}
	
	public void onIconMouseEntered(double _x) {
		selected_icon = (int)(_x / (icon_pref_size + icon_spacing));
		
		if( selected_icon == 4 && app_window.file_pane.selected_card != null && !app_window.file_pane.selected_card.file.is_folder ) // hardcoded
			app_window.status_bar.addText(all_icons.get(selected_icon).getHint() + app_window.file_pane.selected_card.file.name);
		else
			app_window.status_bar.addText(all_icons.get(selected_icon).getHint());
	
	}
	
	public void onIconMouseExited() {
		selected_icon = -1;
		app_window.status_bar.removeText();
	}
}
