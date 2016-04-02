package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.windows.AppWindow;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ActionPane extends Pane {
	
	// BTN_ACTIONS
	public static String ACT_HIDE_PANEL = "HIDE_PANEL";
	
	public StatusBar status_bar = null;
	public Pane content_pane = null;
	public TranslateTransition translate_anim = null;
	public FadeTransition fade_anim = null;
	double translate_anim_distance = 60.0;
	public FadeTransition fade_anim_content_pane = null;
	
	ImageButton btn_hide = null;
	
	public ActionPane(StatusBar status_bar) {
		this.status_bar = status_bar;
		this.setStyle("-fx-background-color: rgb(69, 90, 100, 0.8);");
		this.setPrefWidth(767.0); this.setPrefHeight(60.0);
		this.setLayoutX(235.0); this.setLayoutY(580.0);
		this.setOpacity(0.0);
		
		content_pane = new Pane();
		content_pane.setStyle("-fx-background-color: transparent;");
		content_pane.setPrefWidth(767.0); content_pane.setPrefHeight(60.0);
		
		this.translate_anim = new TranslateTransition(Duration.millis(300.0), this);
		this.fade_anim = new FadeTransition(Duration.millis(300.0), this);
		
		fade_anim_content_pane = new FadeTransition(Duration.millis(150.0), content_pane);
		
		btn_hide = new ImageButton("/assets/logout.png", "Ukryj panel", ACT_HIDE_PANEL);
		btn_hide.setLayoutX(725.0);
		btn_hide.setScaleX(0.5); btn_hide.setScaleY(0.5);
		btn_hide.setOpacity(0.5);
		btn_hide.customizeFadeAnimation(1.0, 0.5, 250, 400);
		btn_hide.customizeZoomAnimation(0.65, 0.5, 250, 400);
		btn_hide.setOnMouseClicked(event -> onHideBtnMouseClicked());
		btn_hide.setOnMouseEntered(event -> onHideBtnMouseEntered());
		btn_hide.setOnMouseExited(event-> onHideBtnMouseExited());

		this.getChildren().add(content_pane);
		this.getChildren().add(btn_hide);
	}
	
	public void create(ImageButton btn) {
		recreate(btn);
		show();
	}
	
	private void recreate(ImageButton btn) {
		fade_anim_content_pane.stop();
		fade_anim_content_pane.setToValue(0.0);
		fade_anim_content_pane.setDuration(Duration.millis(150.0));
		fade_anim_content_pane.play();
		fade_anim_content_pane.setOnFinished(event -> changePanel(btn));
	}
	
	private void changePanel(ImageButton btn) {
		content_pane.getChildren().clear();
		
		switch( btn.action ) {
			case AppWindow.ACT_NEW_CONF:
				createNewConfPanel();
				
				break;
			case AppWindow.ACT_FIND_CONF:
				createFindConfPanel();
				
				break;
			case AppWindow.ACT_NEW_CONF_FROM_FILE:
				createNewConfPanel(); // tymczasowo
				
				break;
			case AppWindow.ACT_FIND_FILE:
				createFindConfPanel(); // tymczasowo
				
				break;
		}
		
		fade_anim_content_pane.setOnFinished(null);
		fade_anim_content_pane.setDuration(Duration.millis(200.0));
		fade_anim_content_pane.setToValue(1.0);
		fade_anim_content_pane.play();
	}
	
	public void show() {
		translate_anim.stop(); 
		fade_anim.stop();
		
		translate_anim.setToY(-translate_anim_distance);
		translate_anim.setDuration(Duration.millis(300.0));
		fade_anim.setToValue(1.0);
		fade_anim.setDuration(Duration.millis(550.0));
		
		translate_anim.play(); 
		fade_anim.play();
	}
	
	private void createNewConfPanel() {
		TextField tf_conf_title = new TextField();
		PasswordField pf_password = new PasswordField();
		Button btn_create = new Button("Stwórz");
		
		tf_conf_title.setPromptText("Nazwa konferencji");
		tf_conf_title.setLayoutX(50.0); tf_conf_title.setLayoutY(20.0);
		tf_conf_title.setPrefWidth(200.0);
		tf_conf_title.setStyle("-fx-text-fill: rgb(114, 114, 114); "
				+ "-fx-prompt-text-fill: rgb(182, 182, 182); "
				+ "-fx-highlight-text-fill:black; "
				+ "-fx-highlight-fill: gray; "
				+ "-fx-background-color: rgb(207, 216, 220); ");
		tf_conf_title.setFont(Utils.TF_FONT_SMALL);
		
		
		pf_password.setPromptText("Has³o (opcjonalne)");
		pf_password.setLayoutX(300.0); pf_password.setLayoutY(20.0);
		pf_password.setPrefWidth(200.0);
		pf_password.setStyle("-fx-text-fill: rgb(114, 114, 114); "
				+ "-fx-prompt-text-fill: rgb(182, 182, 182); "
				+ "-fx-highlight-text-fill:black; "
				+ "-fx-highlight-fill: gray; "
				+ "-fx-background-color: rgb(207, 216, 220); ");
		pf_password.setFont(Utils.TF_FONT_SMALL);
		
		btn_create.setLayoutX(550.0); btn_create.setLayoutY(20.0);
		btn_create.setOnAction(event -> onBtnAction(btn_create));
		
		content_pane.getChildren().add(tf_conf_title);
		content_pane.getChildren().add(pf_password);
		content_pane.getChildren().add(btn_create);
	}
	
	private void createFindConfPanel() {
		TextField tf_conf_title = new TextField();
		PasswordField pf_password = new PasswordField();
		Button btn_create = new Button("Szukaj");
		
		tf_conf_title.setPromptText("Nazwa konferencji");
		tf_conf_title.setLayoutX(50.0); tf_conf_title.setLayoutY(20.0);
		tf_conf_title.setPrefWidth(200.0);
		tf_conf_title.setStyle("-fx-text-fill: rgb(114, 114, 114); "
				+ "-fx-prompt-text-fill: rgb(182, 182, 182); "
				+ "-fx-highlight-text-fill:black; "
				+ "-fx-highlight-fill: gray; "
				+ "-fx-background-color: rgb(207, 216, 220); ");
		tf_conf_title.setFont(Utils.TF_FONT_SMALL);
		
		btn_create.setLayoutX(300.0); btn_create.setLayoutY(20.0);
		btn_create.setOnAction(event -> onBtnAction(btn_create));
		
		content_pane.getChildren().add(tf_conf_title);
		content_pane.getChildren().add(btn_create);
	}
	
	public void onBtnAction(Button btn) {
		if( btn.getText().equals("Stwórz") ) {	// TODO: zmieniæ 
			// wys³aæ zapytanie do servera
			// odebraæ wiadomoœæ od servera
			// zmieniæ okno
			// wyœwietliæ error jeœli nie uda³o siê nawi¹zaæ po³¹czenia
		}
	}
	
	public void hide() {
		translate_anim.stop();
		fade_anim.stop();
		
		translate_anim.setToY(translate_anim_distance);
		translate_anim.setDuration(Duration.millis(450.0));
		fade_anim.setToValue(0.0);
		fade_anim.setDuration(Duration.millis(150.0));
		
		translate_anim.play();
		fade_anim.play();
	}
	
	private void onHideBtnMouseEntered() {
		status_bar.addText(btn_hide.hint);
		btn_hide.onMouseEntered();
	}
	
	private void onHideBtnMouseExited() {
		status_bar.removeText();
		btn_hide.onMouseExited();
	}
	
	private void onHideBtnMouseClicked() {
		this.hide();
	}
}
