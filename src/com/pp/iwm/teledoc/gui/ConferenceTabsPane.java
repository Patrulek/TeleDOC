package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.windows.AppWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class ConferenceTabsPane extends TabPane {
	
	Tab active_tab = null;
	Tab last_tab = null;
	
	ScrollPane active_pane = null;
	ScrollPane last_pane = null;
	
	ConferenceCard selected_card = null;
	ConferenceCard hovered_card = null;
	private Window window;
	
	
	public ConferenceTabsPane(Window _window) {
		super();
		window = _window;
		
		setPrefWidth(220.0);
		setPrefHeight(536.0);
		
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		
		for( int i = 0; i < 50; i++ )
			vbox1.getChildren().add(new ConferenceCard(this, true));
		
		for( int i = 0; i < 12; i++ )
			vbox2.getChildren().add(new ConferenceCard(this, false));
		
		active_pane = new ScrollPane(vbox1);
		active_pane.setPrefWidth(220.0); active_pane.setPrefHeight(550.0);
		active_pane.setStyle("-fx-background: rgb(30, 54, 60); -fx-background-color: rgb(30, 54, 60);");
		active_tab = new Tab("Aktywne", active_pane);
		active_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		active_pane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		
		last_pane = new ScrollPane(vbox2);
		last_pane.setPrefWidth(220.0); active_pane.setPrefHeight(550.0);
		last_pane.setStyle("-fx-background: rgb(30, 54, 60); -fx-background-color: rgb(30, 54, 60);");
		last_tab = new Tab("Ostatnie", last_pane);
		last_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		last_pane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		
		
		active_tab.closableProperty().set(false);
		last_tab.closableProperty().set(false);
		
		
		last_tab.setStyle("-fx-background-color: rgb(30, 54, 60);");
		active_tab.setStyle("-fx-background-color: rgb(30, 54, 60);");
		
		getTabs().add(active_tab);
		getTabs().add(last_tab);
	}
	
	public void onCardSelect(ConferenceCard _selected_card) {
		if( selected_card != null ) {
			selected_card.setStyle("-fx-background-color: transparent;");
			selected_card.lbl_title.setStyle("-fx-text-fill: rgb(160, 160, 200);");
			selected_card.lbl_founder.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		}
		
		selected_card = _selected_card;
		selected_card.setStyle("-fx-background-color: rgb(15, 27, 30);");
		selected_card.lbl_title.setStyle("-fx-text-fill: rgb(140, 140, 170);");
		selected_card.lbl_founder.setStyle("-fx-text-fill: rgb(140, 140, 170);");
	}
	
	public void onCardHover(ConferenceCard _hovered_card) {
		hovered_card = _hovered_card;
	}
	
	public void addTextToStatusBar(String _text) {
		((AppWindow)window).addTextToStatusBar(_text);
	}
	
	public void removeTextFromStatusBar() {
		((AppWindow)window).removeTextFromStatusBar();
	}
}
