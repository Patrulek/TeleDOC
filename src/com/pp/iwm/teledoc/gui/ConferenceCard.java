package com.pp.iwm.teledoc.gui;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ConferenceCard extends Pane {
	
	public Label lbl_title = null;
	public Label lbl_founder = null;
	private ImageButton btn_action = null;
	private ImageButton btn_information = null;
	private ConferenceTabsPane tab_pane = null;
	
	boolean active_conf = true;
	
	public ConferenceCard(ConferenceTabsPane _tab_pane, boolean _active_conf) {
		super();
		tab_pane = _tab_pane;
		active_conf = _active_conf;
		
		setPrefWidth(220.0); 
		setPrefHeight(50.0);
		
		lbl_title = new Label("Tytu³");
		lbl_title.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_title.setLayoutX(5.0); lbl_title.setLayoutY(7.0);
		lbl_title.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_title.setPrefWidth(120.0);
		
		lbl_founder = new Label("Za³o¿yciel: ");
		lbl_founder.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_founder.setLayoutX(5.0); lbl_founder.setLayoutY(27.0);
		lbl_founder.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_title.setPrefWidth(120.0);
		
		btn_action = active_conf ? new ImageButton(Utils.IMG_NEW_CONF_ICON, Utils.HINT_JOIN_CONF, Utils.ACT_JOIN_CONF) : new ImageButton(Utils.IMG_FOLDER_ICON, Utils.HINT_OPEN_CONF, Utils.ACT_OPEN_CONF);
		btn_action.setLayoutX(160.0); btn_action.setLayoutY(-6.0);
		btn_action.customizeZoomAnimation(0.65, 0.4, 250, 250);
		btn_action.setScaleX(0.4); btn_action.setScaleY(0.4);
		
		btn_information = new ImageButton(Utils.IMG_FOLDER_ICON, Utils.HINT_CONF_DETAILS, Utils.ACT_CONF_DETAILS);
		btn_information.setLayoutX(160.0); btn_information.setLayoutY(14.0);
		btn_information.customizeZoomAnimation(0.65, 0.4, 250, 250);
		btn_information.setScaleX(0.4); btn_information.setScaleY(0.4);

		btn_action.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onButtonMouseEntered(btn_action));
		btn_action.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onButtonMouseExited(btn_action));
		btn_information.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onButtonMouseEntered(btn_information));
		btn_information.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onButtonMouseExited(btn_information));
		
		getChildren().add(lbl_title);
		getChildren().add(lbl_founder);
		getChildren().add(btn_action);
		getChildren().add(btn_information);
		
		setOnMouseClicked(event -> onMouseClicked());
		setOnMouseEntered(event -> onMouseEntered());
		setOnMouseExited(event -> onMouseExited());
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
	
	private void onButtonMouseEntered(ImageButton _btn) {
		String s = _btn.getHint() + tab_pane.hovered_card.lbl_title.getText();
		
		tab_pane.addTextToStatusBar(s);
	}
	
	private void onButtonMouseExited(ImageButton _btn) {
		tab_pane.removeTextFromStatusBar();
	}
}
