package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.animations.TranslateAnimation;
import com.pp.iwm.teledoc.layouts.AppWindowLayout;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.AppWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ActionPane extends Pane {	// TODO osobne funkcje dla ka¿dego buttona
	
	// ========================================
	// FIELDS
	// ========================================
	
	private AppWindowLayout layout;
	private Pane content_pane;
	private ImageButton ibtn_hide;
	private ImageButton ibtn_action;
	private StringProperty conf_title;
	private StringProperty folder_name;
	
	private TranslateAnimation translate_animation;
	private FadeAnimation fade_animation;
	private FadeAnimation fade_animation_content_pane;
	
	private boolean is_visible;
	private PaneState state;
	
	public enum PaneState {
		UNDEFINED, NEW_CONF, SEARCH_CONF, SEARCH_FILE, ADD_FOLDER;
	}
	
	// ============================================
	// METHODS
	// ============================================
	
	public ActionPane(AppWindowLayout _layout) {
		is_visible = false;
		state = PaneState.UNDEFINED;
		layout = _layout;
		conf_title = new SimpleStringProperty();
		folder_name = new SimpleStringProperty();
			
		createLayout();
		addAnimations();
	}
	
	private void createLayout() {
		setStyle("-fx-background-color: rgb(15, 27, 30);");
		setPrefSize(759.0, 60.0);
		setOpacity(0.0);
		
		content_pane = new Pane();
		content_pane.setStyle("-fx-background-color: transparent;");
		content_pane.setPrefSize(759.0, 60.0);
		
		ibtn_hide = new ImageButton(Utils.IMG_HIDE_PANEL_SMALL, Utils.HINT_HIDE_PANEL, Utils.ACT_HIDE_PANEL);
		ibtn_hide.setLayoutX(720.0); ibtn_hide.setLayoutY(5.0);
		ibtn_hide.disableFadeAnimation();
		ibtn_hide.customizeZoomAnimation(1.15, 1.0, 250, 400);
		
		ibtn_action = new ImageButton(Utils.IMG_NEW_CONF_ICON, Utils.HINT_CREATE, Utils.ACT_CREATE);

		getChildren().add(content_pane);
		getChildren().add(ibtn_hide);
	}
	
	private void addAnimations() {
		translate_animation = new TranslateAnimation(this);
		translate_animation.customize(0, -60, 300, 450);
		
		fade_animation = new FadeAnimation(this);
		fade_animation.customize(1.0, 0.0, 550, 150);
		
		fade_animation_content_pane = new FadeAnimation(content_pane);
		fade_animation_content_pane.customize(1.0, 0.0, 200, 150);
	}
	
	public void changeStateAndRefresh(PaneState _pane_state) {
		if( state != _pane_state ) {
			state = _pane_state;
			recreate();
		}
		
		show();
	}
	
	public void show() {
		fade_animation.playForward();
		translate_animation.playForward();
		
		if( !is_visible ) {
			is_visible = true;
			layout.addHidePanelIcon();
		}
	}
	
	public void hide() {
		fade_animation.playBackward();
		translate_animation.playBackward();
		
		if( is_visible ) {
			is_visible = false;
			layout.removeHidePanelIcon();
		}
	}
	
	private void recreate() {
		fade_animation_content_pane.playBackward();
		fade_animation_content_pane.setOnFinished(ev -> changePanel());
	}
	
	private void changePanel() {
		content_pane.getChildren().clear();
		
		switch( state ) {
			case NEW_CONF:
				createNewConfPanel();
				
				break;
			case SEARCH_CONF:
			case SEARCH_FILE:
				createSearchConfPanel();
				
				break;
			case ADD_FOLDER:
				createAddFolderPanel();
				
				break;
			default:
		}
		
		fade_animation_content_pane.setOnFinished(null);
		fade_animation_content_pane.playForward();
	}
	
	private void createAddFolderPanel() {
		TextField tf_conf_title = new TextField();
		tf_conf_title.requestFocus();
		ibtn_action.changeButton(Utils.IMG_ADD_FOLDER_BIG, Utils.HINT_ADD_FOLDER, Utils.ACT_ADD_FOLDER);
		
		tf_conf_title.setPromptText("Nazwa folderu");
		tf_conf_title.setLayoutX(50.0); tf_conf_title.setLayoutY(20.0);
		tf_conf_title.setPrefWidth(200.0);
		tf_conf_title.setStyle("-fx-text-fill: rgb(222, 135, 205); "
				+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-fill: rgb(15, 27, 30); "
				+ "-fx-background-color: rgb(30, 54, 60); ");
		tf_conf_title.setFont(Utils.TF_FONT_SMALL);
		folder_name.bind(tf_conf_title.textProperty());
		
		ibtn_action.setLayoutX(280.0); ibtn_action.setLayoutY(11.0);
		
		content_pane.getChildren().add(tf_conf_title);
		content_pane.getChildren().add(ibtn_action);
	}
	
	private void createNewConfPanel() {
		TextField tf_conf_title = new TextField();
		PasswordField pf_password = new PasswordField();
		ibtn_action.changeButton(Utils.IMG_NEW_CONF_ICON, Utils.HINT_CREATE, Utils.ACT_CREATE);
		
		tf_conf_title.setPromptText("Nazwa konferencji");
		tf_conf_title.setLayoutX(50.0); tf_conf_title.setLayoutY(20.0);
		tf_conf_title.setPrefWidth(200.0);
		tf_conf_title.setStyle("-fx-text-fill: rgb(222, 135, 205); "
				+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-fill: rgb(15, 27, 30); "
				+ "-fx-background-color: rgb(30, 54, 60); ");
		tf_conf_title.setFont(Utils.TF_FONT_SMALL);
		conf_title.bind(tf_conf_title.textProperty());
		
		pf_password.setPromptText("Has³o (opcjonalne)");
		pf_password.setLayoutX(300.0); pf_password.setLayoutY(20.0);
		pf_password.setPrefWidth(200.0);
		pf_password.setStyle("-fx-text-fill: rgb(222, 135, 205); "
				+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-fill: rgb(15, 27, 30); "
				+ "-fx-background-color: rgb(30, 54, 60); ");
		pf_password.setFont(Utils.TF_FONT_SMALL);
		
		ibtn_action.setLayoutX(530.0); ibtn_action.setLayoutY(11.0);
		
		content_pane.getChildren().add(tf_conf_title);
		content_pane.getChildren().add(pf_password);
		content_pane.getChildren().add(ibtn_action);
	}
	
	private void createSearchConfPanel() {
		TextField tf_conf_title = new TextField();
		//
		ibtn_action.changeButton(Utils.IMG_SEARCH_CONF_ICON, Utils.HINT_SEARCH, Utils.ACT_SEARCH);
		
		if( state == PaneState.SEARCH_CONF)
			tf_conf_title.setPromptText("Nazwa konferencji");
		else
			tf_conf_title.setPromptText("Nazwa pliku");
		
		tf_conf_title.setLayoutX(50.0); tf_conf_title.setLayoutY(20.0);
		tf_conf_title.setPrefWidth(200.0);
		tf_conf_title.setStyle("-fx-text-fill: rgb(222, 135, 205); "
				+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-fill: rgb(15, 27, 30); "
				+ "-fx-background-color: rgb(30, 54, 60); ");
		tf_conf_title.setFont(Utils.TF_FONT_SMALL);
		
		ibtn_action.setLayoutX(280.0); ibtn_action.setLayoutY(11.0);
		
		content_pane.getChildren().add(tf_conf_title);
		content_pane.getChildren().add(ibtn_action);
	}
	
	public ImageButton getHideBtn() {
		return ibtn_hide;
	}
	
	public ImageButton getActionBtn() {
		return ibtn_action;
	}
	
	public PaneState getState() {
		return state;
	}
	
	public String getConfTitle() {
		return conf_title.get();
	}
	
	public String getFolderName() {
		return folder_name.get();
	}
}
