package com.pp.iwm.teledoc.gui;

import java.util.Stack;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class StatusBar extends Pane {
	
	// ===================================
	// FIELDS
	// ===================================
	
	private Label lbl_hint;
	private Stack<String> texts;
	
	// ===================================
	// METHODS
	// ===================================
	
	public StatusBar() {
		setPrefWidth(1024.0); setPrefHeight(20.0);
		setStyle("-fx-background-color: rgb(45, 81, 90);");
		
		texts = new Stack<>();
		
		lbl_hint = new Label("");
		lbl_hint.setLayoutX(10.0); lbl_hint.setLayoutY(3.0);
		lbl_hint.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_hint.setStyle("-fx-text-fill: rgb(180, 180, 240);");
		
		getChildren().add(lbl_hint);
	}
	
	public void addText(String _text) {
		texts.push(_text);
		setText(_text);
	}
	
	public void removeText() {
		if( !texts.isEmpty() ) {
			texts.pop();
			
			if( !texts.isEmpty() )
				setText(texts.peek());
			else
				setText("");
		}
	}
	
	private void setText(String _text) {
		lbl_hint.setText(_text);
	}
}
