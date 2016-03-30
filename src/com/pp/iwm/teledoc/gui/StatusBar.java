package com.pp.iwm.teledoc.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class StatusBar extends Pane {
	
	Label lbl_hint = null;
	
	public StatusBar() {
		this.setPrefWidth(1024.0);
		this.setPrefHeight(20.0);
		this.setStyle("-fx-background-color: rgb(69, 90, 100);");
		
		
		lbl_hint = new Label("");
		lbl_hint.setLayoutX(10.0);
		lbl_hint.setLayoutY(3.0);
		lbl_hint.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_hint.setStyle("-fx-text-fill: rgb(182, 182, 182);");
		
		this.getChildren().add(lbl_hint);
	}
	
	public void setText(String text) {
		lbl_hint.setText(text);
	}
}
