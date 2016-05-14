package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.animations.TranslateAnimation;
import com.pp.iwm.teledoc.layouts.AppWindowLayout;
import com.pp.iwm.teledoc.utils.Utils;
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

public class ActionPane extends Pane {	// TODO osobne funkcje dla ka¿dego buttona
	
	// ========================================
	// FIELDS
	// ========================================
	
	private AppWindowLayout layout;
	private Pane content_pane;
	private ImageButton btn_hide;
	
	private TranslateAnimation translate_animation;
	private FadeAnimation fade_animation;
	private FadeAnimation fade_animation_content_pane;
	
	private boolean is_visible;
	private PaneState pane_state;
	
	public enum PaneState {
		UNDEFINED, NEW_CONF, SEARCH_CONF, SEARCH_FILE;
	}
	
	// ============================================
	// METHODS
	// ============================================
	
	public ActionPane(AppWindowLayout _layout) {
		is_visible = false;
		pane_state = PaneState.UNDEFINED;
		layout = _layout;
			
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
		
		btn_hide = new ImageButton(Utils.IMG_HIDE_PANEL_SMALL, Utils.HINT_HIDE_PANEL, Utils.ACT_HIDE_PANEL);
		btn_hide.setLayoutX(720.0); btn_hide.setLayoutY(5.0);
		btn_hide.disableFadeAnimation();
		btn_hide.customizeZoomAnimation(1.15, 1.0, 250, 400);
		btn_hide.addEventHandler(ActionEvent.ACTION, ev -> onHideBtnAction(ev));
		btn_hide.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onHideBtnMouseEntered(ev));
		btn_hide.addEventHandler(MouseEvent.MOUSE_EXITED, ev-> onHideBtnMouseExited(ev));

		getChildren().add(content_pane);
		getChildren().add(btn_hide);
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
		if( pane_state != _pane_state ) {
			pane_state = _pane_state;
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
		
		switch( pane_state ) {
			case NEW_CONF:
				createNewConfPanel();
				
				break;
			case SEARCH_CONF:
			case SEARCH_FILE:
				createSearchConfPanel();
				
				break;
			default:
		}
		
		fade_animation_content_pane.setOnFinished(null);
		fade_animation_content_pane.playForward();
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
	
	private void createSearchConfPanel() {
		TextField tf_conf_title = new TextField();
		//
		ImageButton ibtn_search = new ImageButton(Utils.IMG_SEARCH_CONF_ICON, Utils.HINT_SEARCH, Utils.ACT_SEARCH);
		
		if( pane_state == PaneState.SEARCH_CONF)
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
		
		ibtn_search.setLayoutX(280.0); ibtn_search.setLayoutY(11.0);
		ibtn_search.setOnAction(event -> onBtnAction(ibtn_search));
		ibtn_search.addEventFilter(MouseEvent.MOUSE_ENTERED, ev -> onBtnEntered(ibtn_search));
		ibtn_search.addEventFilter(MouseEvent.MOUSE_EXITED, ev -> onBtnExited(ibtn_search));
		
		content_pane.getChildren().add(tf_conf_title);
		content_pane.getChildren().add(ibtn_search);
	}
	
	private void onBtnAction(Button _btn) {
		if( _btn.getText().equals("Stwórz") ) 
			onCreateConfAction();
	}
	
	private void onCreateConfAction() {
		// TODO: zmieniæ 
					// wys³aæ zapytanie do servera
					// odebraæ wiadomoœæ od servera
					// zmieniæ okno
					// wyœwietliæ error jeœli nie uda³o siê nawi¹zaæ po³¹czenia
	}
	
	private void onBtnEntered(ImageButton _ibtn) {
		layout.addTextToStatusBar(_ibtn.getHint());
	}
	
	private void onBtnExited(ImageButton _ibtn) {
		layout.removeTextFromStatusBar();
	}
	
	private void onHideBtnMouseEntered(MouseEvent _ev) {
		layout.addTextToStatusBar(btn_hide.getHint());
	}
	
	private void onHideBtnMouseExited(MouseEvent _ev) {
		layout.removeTextFromStatusBar();
	}
	
	private void onHideBtnAction(ActionEvent _ev) {
		hide();
	}
}
