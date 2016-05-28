package com.pp.iwm.teledoc.drawables;

import java.util.Random;

import com.pp.iwm.teledoc.gui.DrawablePane;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pointer {
	
	//  =============================
	// FIELDS
	// ==============================
	
	private final double RADIUS = 4.0;
	
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
		pointer.relocate(_new_pos.getX(), _new_pos.getY());
		lbl_member.relocate(_new_pos.getX() - lbl_member.getWidth() / 2.0, _new_pos.getY() - RADIUS - lbl_member.getHeight());
		pane_pos = _new_pos;
	}
	
	public void setMember(String _member_name) {
		lbl_member.setText(_member_name);
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
