package com.pp.iwm.teledoc.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class ConferenceTabsPane extends TabPane {
	
	Tab active_tab = null;
	Tab last_tab = null;
	
	Pane active_pane = null;
	Pane last_pane = null;
	
	public ConferenceTabsPane() {
		super();
		
		this.setPrefWidth(220.0);
		
		active_pane = new Pane();
		active_pane.setPrefWidth(220.0); active_pane.setPrefHeight(600.0);
		active_pane.setStyle("-fx-background-color: rgb(207, 216, 220);");
		this.active_tab = new Tab("Aktywne", active_pane);
		
		last_pane = new Pane();
		last_pane.setPrefWidth(220.0); active_pane.setPrefHeight(600.0);
		last_pane.setStyle("-fx-background-color: rgb(207, 216, 220);");
		this.last_tab = new Tab("Ostatnie", last_pane);
		
		
		
		this.active_tab.closableProperty().set(false);
		this.last_tab.closableProperty().set(false);
		
		this.last_tab.setStyle("-fx-background-color: rgb(69, 90, 100, 1);");
		this.active_tab.setStyle("-fx-background-color: rgb(69, 90, 100, 1);");
		
		this.getTabs().add(active_tab);
		this.getTabs().add(last_tab);
	}
}
