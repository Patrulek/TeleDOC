package com.pp.iwm.teledoc.drawables;

import java.util.Random;

import com.pp.iwm.teledoc.gui.DrawablePane;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pointer {
	
	//  =============================
	// FIELDS
	// ==============================
	
	private final double RADIUS = 8.0;
	private final double HALF_RADIUS = RADIUS / 2.0;
	
	private DrawablePane drawable_pane;
	private Point2D pane_pos;
	private Label lbl_member;
	private Circle pointer;
	private Color color;
	private String email;
	
	
	// ==============================
	// METHODS
	// ==============================
	
	public Pointer(DrawablePane _drawable_pane, String _email) {
		drawable_pane = _drawable_pane;
		email = _email;
		pane_pos = new Point2D(0.0, 0.0);
		color = new Color(new Random().nextDouble(), new Random().nextDouble(), new Random().nextDouble(), 1.0);
		pointer = new Circle(RADIUS, color);
		
		lbl_member = new Label("");
		lbl_member.setStyle("-fx-background-color: rgb(" + color.getRed() * 255 + ", " + color.getGreen() * 255 + ", " +
							color.getBlue() * 255 + ");");
	}
	
	public void relocate(Point2D _new_pos) {
		pane_pos = _new_pos;
		Platform.runLater(() -> refresh());
	}
	
	public void refresh() {
		double scale = drawable_pane.getScale();
		double pointer_x = pane_pos.getX() - HALF_RADIUS;
		double pointer_y = pane_pos.getY() - HALF_RADIUS;
		
		pointer.relocate(scale * pointer_x, scale * pointer_y);
		lbl_member.relocate(scale * pointer_x - lbl_member.getWidth() / 2.0 + RADIUS, scale * pointer_y - HALF_RADIUS - lbl_member.getHeight());
	}
	
	public void setMember(String _member_name) {
		lbl_member.setText(_member_name);
	}
	
	public void addToPane() {
		drawable_pane.getChildren().add(lbl_member);
		drawable_pane.getChildren().add(pointer);
	}
	
	public void show() {
		pointer.setVisible(true);
		lbl_member.setVisible(true);
	}
	
	public void hide() {
		pointer.setVisible(false);
		lbl_member.setVisible(false);
	}
	
	public String getMemberEmail() {
		return email;
	}
}
