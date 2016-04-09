package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.Conference;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ConferenceCard extends Pane {
	
	// ======================================
	// FIELDS
	// ======================================
	
	private Label lbl_title;
	private Label lbl_founder;
	private ImageButton ibtn_action;
	private ImageButton ibtn_information;
	private ConferencePanel conf_pane = null;
	private Conference conf = null;
	
	// =========================================
	// METHODS
	// =========================================
	
	public ConferenceCard(ConferencePanel _conf_pane, Conference _conf) {
		super();
		conf_pane = _conf_pane;
		conf = _conf;
		
		setPrefWidth(220.0); setPrefHeight(50.0);
		
		lbl_title = new Label(conf.getTitle());
		lbl_title.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_title.setLayoutX(5.0); lbl_title.setLayoutY(7.0);
		lbl_title.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_title.setPrefWidth(120.0);
		
		lbl_founder = new Label(conf.getChairman().getName() + " " + conf.getChairman().getSurname());
		lbl_founder.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_founder.setLayoutX(5.0); lbl_founder.setLayoutY(27.0);
		lbl_founder.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_title.setPrefWidth(120.0);
		
		ibtn_action = conf.isOpen() ? new ImageButton(Utils.IMG_NEW_CONF_ICON, Utils.HINT_JOIN_CONF, Utils.ACT_JOIN_CONF) : new ImageButton(Utils.IMG_FOLDER_ICON, Utils.HINT_OPEN_CONF, Utils.ACT_OPEN_CONF);
		ibtn_action.setLayoutX(160.0); ibtn_action.setLayoutY(-6.0);
		ibtn_action.customizeZoomAnimation(0.65, 0.4, 250, 250);
		ibtn_action.setScaleX(0.4); ibtn_action.setScaleY(0.4);
		
		ibtn_information = new ImageButton(Utils.IMG_FOLDER_ICON, Utils.HINT_CONF_DETAILS, Utils.ACT_CONF_DETAILS);
		ibtn_information.setLayoutX(160.0); ibtn_information.setLayoutY(14.0);
		ibtn_information.customizeZoomAnimation(0.65, 0.4, 250, 250);
		ibtn_information.setScaleX(0.4); ibtn_information.setScaleY(0.4);

		ibtn_action.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onButtonMouseEntered(ibtn_action));
		ibtn_action.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onButtonMouseExited(ibtn_action));
		ibtn_action.addEventHandler(ActionEvent.ACTION, ev -> onButtonAction(ibtn_action));
		ibtn_information.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onButtonMouseEntered(ibtn_information));
		ibtn_information.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onButtonMouseExited(ibtn_information));
		ibtn_information.addEventHandler(ActionEvent.ACTION, ev -> onButtonAction(ibtn_information));
		
		getChildren().add(lbl_title);
		getChildren().add(lbl_founder);
		getChildren().add(ibtn_action);
		getChildren().add(ibtn_information);
		
		setOnMouseClicked(event -> onMouseClicked());
		setOnMouseEntered(event -> onMouseEntered());
		setOnMouseExited(event -> onMouseExited());
	}
	
	public void setNormalStyle() {
		setStyle("-fx-background-color: transparent;");
		lbl_title.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_founder.setStyle("-fx-text-fill: rgb(160, 160, 200);");
	}
	
	public void setSelectionStyle() {
		setStyle("-fx-background-color: rgb(15, 27, 30);");
		lbl_title.setStyle("-fx-text-fill: rgb(140, 140, 170);");
		lbl_founder.setStyle("-fx-text-fill: rgb(140, 140, 170);");
	}
	
	private void onMouseClicked() {
		conf_pane.onCardSelect(this);
	}
	
	private void onMouseEntered() {
		conf_pane.onCardHover(this);
	}
	
	private void onMouseExited() {
		conf_pane.onCardHover(null);
	}
	
	private void onButtonAction(ImageButton _ibtn) {
		;
	}
	
	private void onButtonMouseEntered(ImageButton _ibtn) {
		String s = _ibtn.getHint() + conf_pane.getHoveredCard().lbl_title.getText();
		
		conf_pane.addTextToStatusBar(s);
	}
	
	private void onButtonMouseExited(ImageButton _ibtn) {
		conf_pane.removeTextFromStatusBar();
	}
	
	public Conference getConference() {
		return conf;
	}
}
