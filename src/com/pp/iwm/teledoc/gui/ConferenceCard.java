package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.Conference;
import com.pp.iwm.teledoc.utils.Utils;

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
	private ImageButton ibtn_join;
	private ImageButton ibtn_details;
	private ConferencePanel conf_pane;
	private Conference conf;
	
	// =========================================
	// METHODS
	// =========================================
	
	public ImageButton getJoinBtn() {
		return ibtn_join;
	}
	
	public ImageButton getDetailsBtn() {
		return ibtn_details;
	}
	
	public ConferenceCard(ConferencePanel _conf_pane, Conference _conf) {
		super();
		conf_pane = _conf_pane;
		conf = _conf;
		
		createLayout();
		setHandlers();
	}
	
	private void createLayout() {
		setPrefWidth(220.0); setPrefHeight(50.0);
		
		lbl_title = new Label(conf.getTitle());
		lbl_title.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_title.setLayoutX(5.0); lbl_title.setLayoutY(7.0);
		lbl_title.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_title.setPrefWidth(160.0);
		
		// TODO temp
		lbl_founder = new Label(conf.getOwner());
		lbl_founder.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_founder.setLayoutX(5.0); lbl_founder.setLayoutY(27.0);
		lbl_founder.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_title.setPrefWidth(160.0);
		
		ibtn_join = conf.isOpen() ? new ImageButton(Utils.IMG_NEW_CONF_ICON, Utils.HINT_JOIN_CONF, Utils.ACT_JOIN_CONF) : new ImageButton(Utils.IMG_FOLDER_ICON, Utils.HINT_OPEN_CONF, Utils.ACT_OPEN_CONF);
		ibtn_join.setLayoutX(160.0); ibtn_join.setLayoutY(-6.0);
		ibtn_join.customizeZoomAnimation(0.65, 0.4, 250, 250);
		ibtn_join.setScaleX(0.4); ibtn_join.setScaleY(0.4);
		
		ibtn_details = new ImageButton(Utils.IMG_FOLDER_ICON, Utils.HINT_CONF_DETAILS, Utils.ACT_CONF_DETAILS);
		ibtn_details.setLayoutX(160.0); ibtn_details.setLayoutY(14.0);
		ibtn_details.customizeZoomAnimation(0.65, 0.4, 250, 250);
		ibtn_details.setScaleX(0.4); ibtn_details.setScaleY(0.4);
		
		getChildren().add(lbl_title);
		getChildren().add(lbl_founder);
		getChildren().add(ibtn_join);
		getChildren().add(ibtn_details);
	}
	
	private void setHandlers() {
		setOnMouseClicked(ev -> onMouseClicked());
		setOnMouseEntered(ev -> onMouseEntered());
		setOnMouseExited(ev -> onMouseExited());
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
	
	public Conference getConference() {
		return conf;
	}
	
	public Label getTitleLabel() {
		return lbl_title;
	}
}
