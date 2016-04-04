package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeTransitionInfo;
import com.pp.iwm.teledoc.animations.TranslateTransitionInfo;
import com.pp.iwm.teledoc.windows.AppWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ActionPane extends Pane {
	
	private Window window;
	public Pane content_pane = null;
	public TranslateTransitionInfo translate_info;
	public TranslateTransition translate_anim = null;
	public FadeTransition fade_anim = null;
	public FadeTransitionInfo fade_info;
	public FadeTransition fade_anim_content_pane = null;
	public FadeTransitionInfo fade_info_content_pane;
	
	public boolean is_visible = false;
	
	ImageButton btn_hide = null;
	
	public ActionPane(Window _window) {
		window = _window;
		setStyle("-fx-background-color: rgb(15, 27, 30);");
		setPrefWidth(759.0); setPrefHeight(60.0);
		setLayoutX(250.0); setLayoutY(580.0);
		setOpacity(0.0);
		
		content_pane = new Pane();
		content_pane.setStyle("-fx-background-color: transparent;");
		content_pane.setPrefWidth(759.0); content_pane.setPrefHeight(60.0);
		
		translate_anim = new TranslateTransition(Duration.millis(300.0), this);
		translate_info = new TranslateTransitionInfo(translate_anim);
		translate_info.customize(0, -60, 300, 450);
		
		fade_anim = new FadeTransition(Duration.millis(300.0), this);
		fade_info = new FadeTransitionInfo(fade_anim);
		fade_info.customize(1.0, 0.0, 550, 150);
		
		fade_anim_content_pane = new FadeTransition(Duration.millis(150.0), content_pane);
		fade_info_content_pane = new FadeTransitionInfo(fade_anim_content_pane);
		fade_info_content_pane.customize(1.0, 0.0, 200, 150);
		
		btn_hide = new ImageButton(Utils.IMG_HIDE_PANEL_SMALL, Utils.HINT_HIDE_PANEL, Utils.ACT_HIDE_PANEL);
		btn_hide.setLayoutX(720.0); btn_hide.setLayoutY(5.0);
		btn_hide.enableFadeAnimation(false);
		btn_hide.customizeZoomAnimation(1.15, 1.0, 250, 400);
		btn_hide.addEventHandler(ActionEvent.ACTION, ev -> onHideBtnAction(ev));
		btn_hide.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onHideBtnMouseEntered(ev));
		btn_hide.addEventHandler(MouseEvent.MOUSE_EXITED, ev-> onHideBtnMouseExited(ev));

		getChildren().add(content_pane);
		getChildren().add(btn_hide);
	}
	
	public void create(ImageButton _btn) {
		recreate(_btn);
		show();
	}
	
	private void recreate(ImageButton _btn) {
		fade_info_content_pane.play(false);
		fade_anim_content_pane.setOnFinished(event -> changePanel(_btn));
	}
	
	private void changePanel(ImageButton _btn) {
		content_pane.getChildren().clear();
		
		switch( _btn.getAction() ) {
			case Utils.ACT_NEW_CONF:
				createNewConfPanel();
				
				break;
			case Utils.ACT_FIND_CONF:
				createFindConfPanel();
				
				break;
			case Utils.ACT_NEW_CONF_FROM_FILE:
				createNewConfPanel(); // tymczasowo
				
				break;
			case Utils.ACT_FIND_FILE:
				createFindConfPanel(); // tymczasowo
				
				break;
		}
		
		fade_anim_content_pane.setOnFinished(null);
		fade_info_content_pane.play(true);
	}
	
	public void show() {
		fade_info.play(true);
		translate_info.play(true);
		
		if( !is_visible ) {
			is_visible = true;
			((AppWindow)window).addHidePanelIcon();
		}
	}
	
	private void createNewConfPanel() {
		TextField tf_conf_title = new TextField();
		PasswordField pf_password = new PasswordField();
		ImageButton ibtn_create = new ImageButton(Utils.IMG_NEW_CONF_ICON, Utils.HINT_CREATE, Utils.ACT_CREATE);
		
		tf_conf_title.setPromptText("Nazwa konferencji");
		tf_conf_title.setLayoutX(50.0); tf_conf_title.setLayoutY(20.0);
		tf_conf_title.setPrefWidth(200.0);
		tf_conf_title.setStyle("-fx-text-fill: rgb(222, 135, 205); "
				+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-fill: rgb(15, 27, 30); "
				+ "-fx-background-color: rgb(30, 54, 60); ");
		tf_conf_title.setFont(Utils.TF_FONT_SMALL);
		
		
		pf_password.setPromptText("Has³o (opcjonalne)");
		pf_password.setLayoutX(300.0); pf_password.setLayoutY(20.0);
		pf_password.setPrefWidth(200.0);
		pf_password.setStyle("-fx-text-fill: rgb(222, 135, 205); "
				+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-fill: rgb(15, 27, 30); "
				+ "-fx-background-color: rgb(30, 54, 60); ");
		pf_password.setFont(Utils.TF_FONT_SMALL);
		
		ibtn_create.setLayoutX(530.0); ibtn_create.setLayoutY(11.0);
		ibtn_create.setOnAction(event -> onBtnAction(ibtn_create));
		ibtn_create.addEventFilter(MouseEvent.MOUSE_ENTERED, ev -> onBtnEntered(ibtn_create));
		ibtn_create.addEventFilter(MouseEvent.MOUSE_EXITED, ev -> onBtnExited(ibtn_create));
		
		content_pane.getChildren().add(tf_conf_title);
		content_pane.getChildren().add(pf_password);
		content_pane.getChildren().add(ibtn_create);
	}
	
	private void createFindConfPanel() {
		TextField tf_conf_title = new TextField();
		//
		ImageButton ibtn_search = new ImageButton(Utils.IMG_SEARCH_CONF_ICON, Utils.HINT_SEARCH, Utils.ACT_SEARCH);
		
		tf_conf_title.setPromptText("Nazwa konferencji");
		tf_conf_title.setLayoutX(50.0); tf_conf_title.setLayoutY(20.0);
		tf_conf_title.setPrefWidth(200.0);
		tf_conf_title.setStyle("-fx-text-fill: rgb(222, 135, 205); "
				+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
				+ "-fx-highlight-fill: rgb(15, 27, 30); "
				+ "-fx-background-color: rgb(30, 54, 60); ");
		tf_conf_title.setFont(Utils.TF_FONT_SMALL);
		
		ibtn_search.setLayoutX(280.0); ibtn_search.setLayoutY(11.0);
		ibtn_search.setOnAction(event -> onBtnAction(ibtn_search));
		ibtn_search.addEventFilter(MouseEvent.MOUSE_ENTERED, ev -> onBtnEntered(ibtn_search));
		ibtn_search.addEventFilter(MouseEvent.MOUSE_EXITED, ev -> onBtnExited(ibtn_search));
		
		content_pane.getChildren().add(tf_conf_title);
		content_pane.getChildren().add(ibtn_search);
	}
	
	public void onBtnAction(Button _btn) {
		if( _btn.getText().equals("Stwórz") ) {	// TODO: zmieniæ 
			// wys³aæ zapytanie do servera
			// odebraæ wiadomoœæ od servera
			// zmieniæ okno
			// wyœwietliæ error jeœli nie uda³o siê nawi¹zaæ po³¹czenia
		}
	}
	
	private void onBtnEntered(ImageButton _ibtn) {
		((AppWindow)window).addTextToStatusBar(_ibtn.getHint());
	}
	
	private void onBtnExited(ImageButton _ibtn) {
		((AppWindow)window).removeTextFromStatusBar();
	}
	
	public void hide() {
		fade_info.play(false);
		translate_info.play(false);
		
		if( is_visible ) {
			is_visible = false;
			((AppWindow)window).removeHidePanelIcon();
		}
	}
	
	private void onHideBtnMouseEntered(MouseEvent _ev) {
		((AppWindow)window).addTextToStatusBar(btn_hide.getHint());
	}
	
	private void onHideBtnMouseExited(MouseEvent _ev) {
		((AppWindow)window).removeTextFromStatusBar();
	}
	
	private void onHideBtnAction(ActionEvent _ev) {
		hide();
	}
}
