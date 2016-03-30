package com.pp.iwm.teledoc.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ConferenceCard extends Pane {
	
	Label lbl_title = null;
	Label lbl_founder = null;
	ImageButton btn_action = null;
	ImageButton btn_information = null;
	ConferenceTabsPane tab_pane = null;
	
	boolean active_conf = true;
	
	public ConferenceCard(ConferenceTabsPane tab_pane, boolean active_conf) {
		super();
		this.tab_pane = tab_pane;
		this.active_conf = active_conf;
		
		setPrefWidth(220.0); 
		setPrefHeight(50.0);
		
		lbl_title = new Label("Tytu³");
		lbl_title.setStyle("-fx-text-fill: rgb(114, 114, 114);");
		lbl_title.setLayoutX(5.0); lbl_title.setLayoutY(7.0);
		lbl_title.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_title.setPrefWidth(120.0);
		
		lbl_founder = new Label("Za³o¿yciel: ");
		lbl_founder.setStyle("-fx-text-fill: rgb(114, 114, 114);");
		lbl_founder.setLayoutX(5.0); lbl_founder.setLayoutY(27.0);
		lbl_founder.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_title.setPrefWidth(120.0);
		
		btn_action = active_conf ? new ImageButton("/assets/add_new_conf.png") : new ImageButton("/assets/folder_icon.png");
		btn_action.setLayoutX(160.0); btn_action.setLayoutY(-6.0);
		btn_action.customizeZoomAnimation(0.65, 0.4, 250, 250);
		btn_action.setScaleX(0.4); btn_action.setScaleY(0.4);
		
		btn_information = new ImageButton("/assets/image_icon.png");
		btn_information.setLayoutX(160.0); btn_information.setLayoutY(14.0);
		btn_information.customizeZoomAnimation(0.65, 0.4, 250, 250);
		btn_information.setScaleX(0.4); btn_information.setScaleY(0.4);

		btn_action.setOnMouseEntered(event -> onButtonMouseEntered(btn_action));
		btn_action.setOnMouseExited(event -> onButtonMouseExited(btn_action));
		btn_information.setOnMouseEntered(event -> onButtonMouseEntered(btn_information));
		btn_information.setOnMouseExited(event -> onButtonMouseExited(btn_information));
		
		getChildren().add(lbl_title);
		getChildren().add(lbl_founder);
		getChildren().add(btn_action);
		getChildren().add(btn_information);
		
		this.setOnMouseClicked(event -> onMouseClicked());
		this.setOnMouseEntered(event -> onMouseEntered());
		this.setOnMouseExited(event -> onMouseExited());
	}
	
	private void onMouseClicked() {
		tab_pane.onCardSelect(this);
	}
	
	private void onMouseEntered() {
		tab_pane.onCardHover(this);
	}
	
	private void onMouseExited() {
		tab_pane.onCardHover(null);
	}
	
	private void onButtonMouseEntered(ImageButton btn) {
		String s = "";
		
		if( btn == btn_action ) {
			if( active_conf )
				s = "Do³¹cz do konferencji: " + tab_pane.hovered_card.lbl_title.getText();
			else
				s = "Otwórz ponownie konferencjê: " + tab_pane.hovered_card.lbl_title.getText();
		} else
			s = "Wyœwietl szczegó³owe informacje o konferencji: " + tab_pane.hovered_card.lbl_title.getText();
		
		tab_pane.status_bar.setText(s);
		btn.onMouseEntered();
	}
	
	private void onButtonMouseExited(ImageButton btn) {
		tab_pane.status_bar.setText("");
		btn.onMouseExited();
	}
}
