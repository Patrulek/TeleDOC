package com.pp.iwm.teledoc.gui;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ConferenceTabsPane extends TabPane {
	
	Tab active_tab = null;
	Tab last_tab = null;
	
	ScrollPane active_pane = null;
	ScrollPane last_pane = null;
	
	ConferenceCard selected_card = null;
	ConferenceCard hovered_card = null;
	StatusBar status_bar = null;
	
	public ConferenceTabsPane(StatusBar status_bar) {
		super();
		this.status_bar = status_bar;
		
		this.setPrefWidth(220.0);
		this.setPrefHeight(580.0);
		
		VBox vbox1 = new VBox();
		vbox1.setAlignment(Pos.TOP_CENTER);
		VBox vbox2 = new VBox();
		vbox2.setAlignment(Pos.TOP_CENTER);
		
		for( int i = 0; i < 50; i++ )
			vbox1.getChildren().add(new ConferenceCard(this, true));
		
		for( int i = 0; i < 12; i++ )
			vbox2.getChildren().add(new ConferenceCard(this, false));
		
		active_pane = new ScrollPane(vbox1);
		active_pane.setPrefWidth(220.0); active_pane.setPrefHeight(550.0);
		active_pane.setStyle("-fx-background-color: rgb(207, 216, 220);");
		this.active_tab = new Tab("Aktywne", active_pane);
		active_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		active_pane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		
		last_pane = new ScrollPane(vbox2);
		last_pane.setPrefWidth(220.0); active_pane.setPrefHeight(550.0);
		last_pane.setStyle("-fx-background-color: rgb(207, 216, 220);");
		this.last_tab = new Tab("Ostatnie", last_pane);
		last_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		last_pane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		
		
		this.active_tab.closableProperty().set(false);
		this.last_tab.closableProperty().set(false);
		
		
		this.last_tab.setStyle("-fx-background-color: rgb(69, 90, 100, 1);");
		this.active_tab.setStyle("-fx-background-color: rgb(69, 90, 100, 1);");
		
		this.getTabs().add(active_tab);
		this.getTabs().add(last_tab);
	}
	
	public void onCardSelect(ConferenceCard selected_card) {
		if( this.selected_card != null ) {
			this.selected_card.setStyle("-fx-background-color: rgb(0, 0, 0, 0);");
			this.selected_card.lbl_title.setStyle("-fx-text-fill: rgb(114, 114, 114);");
			this.selected_card.lbl_founder.setStyle("-fx-text-fill: rgb(114, 114, 114);");
		}
		
		this.selected_card = selected_card;
		this.selected_card.setStyle("-fx-background-color: rgb(69, 90, 100);");
		this.selected_card.lbl_title.setStyle("-fx-text-fill: rgb(182, 182, 182);");
		this.selected_card.lbl_founder.setStyle("-fx-text-fill: rgb(182, 182, 182);");
	}
	
	public void onCardHover(ConferenceCard hovered_card) {
		this.hovered_card = hovered_card;
	}
}
