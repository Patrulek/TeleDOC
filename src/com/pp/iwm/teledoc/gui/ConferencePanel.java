package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.objects.Conference;
import com.pp.iwm.teledoc.windows.AppWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ConferencePanel extends Pane {
	
	// =========================================
	// FIELDS
	// =========================================
	
	private boolean is_open_tab_active = true;
	
	private HBox header;
	private Pane open_conf_tab;
	private Label lbl_open_conf;
	private Pane closed_conf_tab;
	private Label lbl_closed_conf;
	
	private ScrollPane open_conf_scrollp;
	private VBox open_conf_contentp;
	
	private ScrollPane closed_conf_scrollp;
	private VBox closed_conf_contentp;

	private ConferenceCard selected_card = null;
	private ConferenceCard hovered_card = null;
	
	private List<ConferenceCard> open_cards;
	private List<ConferenceCard> closed_cards;
	
	private Window window;
	
	// ===========================================
	// METHODS 
	// ===========================================
	
	public ConferencePanel(Window _window) {
		super();
		window = _window;
		open_cards = new ArrayList<>();
		closed_cards = new ArrayList<>();
		
		setPrefSize(222.0, 493.0);
		setStyle("-fx-background-color: transparent;");
		
		open_conf_contentp = new VBox();
		closed_conf_contentp = new VBox();
		
		open_conf_scrollp = new ScrollPane(open_conf_contentp);
		open_conf_scrollp.setPrefSize(222.0, 493.0);
		open_conf_scrollp.setLayoutY(20.0);
		open_conf_scrollp.getStylesheets().add("/styles/conf_pane.css");
		open_conf_scrollp.setHbarPolicy(ScrollBarPolicy.NEVER);
		open_conf_scrollp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		
		closed_conf_scrollp = new ScrollPane(closed_conf_contentp);
		closed_conf_scrollp.setLayoutY(20.0); closed_conf_scrollp.setVisible(false);
		closed_conf_scrollp.setPrefSize(222.0, 493.0);
		closed_conf_scrollp.getStylesheets().add("/styles/conf_pane.css");
		closed_conf_scrollp.setHbarPolicy(ScrollBarPolicy.NEVER);
		closed_conf_scrollp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		header = new HBox();
		header.setStyle("-fx-background: transparent;");
		
		open_conf_tab = new Pane();
		open_conf_tab.setPrefSize(103.0, 20.0);
		open_conf_tab.setStyle("-fx-background-color: rgb(30, 54, 60);");
		open_conf_tab.setOnMouseEntered(ev -> onTabMouseEntered(open_conf_tab));
		open_conf_tab.setOnMouseExited(ev -> onTabMouseExited(open_conf_tab));
		open_conf_tab.setOnMouseClicked(ev -> onTabMouseClicked(open_conf_tab));
		
		lbl_open_conf = new Label("Otwarte");
		lbl_open_conf.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_open_conf.setPrefSize(103.0, 20.0);
		lbl_open_conf.setStyle("-fx-alignment: center; -fx-text-fill: rgb(160, 160, 200);");
		
		closed_conf_tab = new Pane();
		closed_conf_tab.setPrefSize(103.0, 20.0);
		closed_conf_tab.setStyle("-fx-background-color: rgb(15, 27, 30);");
		closed_conf_tab.setOnMouseEntered(ev -> onTabMouseEntered(closed_conf_tab));
		closed_conf_tab.setOnMouseExited(ev -> onTabMouseExited(closed_conf_tab));
		closed_conf_tab.setOnMouseClicked(ev -> onTabMouseClicked(closed_conf_tab));
		
		lbl_closed_conf = new Label("Zamkniête");
		lbl_closed_conf.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_closed_conf.setPrefSize(103.0, 20.0);
		lbl_closed_conf.setStyle("-fx-alignment: center; -fx-text-fill: rgb(160, 160, 200);");
		
		getChildren().add(open_conf_scrollp);
		getChildren().add(closed_conf_scrollp);
		getChildren().add(header);
		
		header.getChildren().add(open_conf_tab);
		header.getChildren().add(closed_conf_tab);
		
		open_conf_tab.getChildren().add(lbl_open_conf);
		closed_conf_tab.getChildren().add(lbl_closed_conf);
	}
	

	public void addConf(Conference _conf) {
		if( _conf == null )
			return;
		
		ConferenceCard conf_card = new ConferenceCard(this, _conf);
		
		if( _conf.isOpen() ) {
			open_cards.add(conf_card);
			open_conf_contentp.getChildren().add(conf_card);
		} else {
			closed_cards.add(conf_card);
			closed_conf_contentp.getChildren().add(conf_card);
		}
	}
	
	public void clearConf() {
		open_cards.clear();
		open_conf_contentp.getChildren().clear();
		
		closed_cards.clear();
		closed_conf_contentp.getChildren().clear();
	}
	
	public void removeConf(Conference _conf) {
		if( _conf == null )
			return;
		
		for( ConferenceCard conf_card : open_cards ) {
			if( conf_card.getConference().equals(_conf) ) {
				open_conf_contentp.getChildren().remove(conf_card);
				open_cards.remove(conf_card);
				return;
			}
		}
		
		for( ConferenceCard conf_card : closed_cards ) {
			if( conf_card.getConference().equals(_conf) ) {
				closed_conf_contentp.getChildren().remove(conf_card);
				closed_cards.remove(conf_card);
				return;
			}
		}
	}
	
	public ConferenceCard getHoveredCard() {
		return hovered_card;
	}
	
	public ConferenceCard getSelectedCard() {
		return selected_card;
	}
	
	public void onCardSelect(ConferenceCard _selected_card) {
		if( selected_card != null ) 
			selected_card.setNormalStyle();
		
		selected_card = _selected_card;
		selected_card.setSelectionStyle();
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
	
	private void onTabMouseEntered(Pane _tab) {
		setTabHoverStyle(_tab);
	}
	
	private void onTabMouseExited(Pane _tab) {
		setTabDeselectionStyle(_tab);
	}
	
	private void setTabHoverStyle(Pane _tab) {
		if( _tab == closed_conf_tab ) {
			if( is_open_tab_active ) {
				_tab.setStyle("-fx-background-color: rgb(45, 81, 90);");
				lbl_closed_conf.setStyle("-fx-alignment: center; -fx-text-fill: rgb(180, 180, 240);");
			}
		} else {
			if( !is_open_tab_active ) {
				_tab.setStyle("-fx-background-color: rgb(45, 81, 90);");
				lbl_open_conf.setStyle("-fx-alignment: center; -fx-text-fill: rgb(180, 180, 240);");
			}
		}
	}
	
	private void setTabSelectionStyle(Pane _tab) {
		if( _tab == closed_conf_tab ) {
			if( is_open_tab_active ) {
				_tab.setStyle("-fx-background-color: rgb(30, 54, 60);");
				lbl_closed_conf.setStyle("-fx-alignment: center; -fx-text-fill: rgb(160, 160, 200);");
			}
		} else {
			if( !is_open_tab_active ) {
				_tab.setStyle("-fx-background-color: rgb(30, 54, 60);");
				lbl_open_conf.setStyle("-fx-alignment: center; -fx-text-fill: rgb(160, 160, 200);");
			}
		}
	}
	
	private void setTabDeselectionStyle(Pane _tab) {
		if( _tab == closed_conf_tab ) {
			if( is_open_tab_active ) {
				_tab.setStyle("-fx-background-color: rgb(15, 27, 30);");
				lbl_closed_conf.setStyle("-fx-alignment: center; -fx-text-fill: rgb(160, 160, 200);");
			}
		} else {
			if( !is_open_tab_active ) {
				_tab.setStyle("-fx-background-color: rgb(15, 27, 30);");
				lbl_open_conf.setStyle("-fx-alignment: center; -fx-text-fill: rgb(160, 160, 200);");
			}
		}
	}
	
	private void onTabMouseClicked(Pane _tab) {
		if( _tab == open_conf_tab && !is_open_tab_active ) {
			setTabSelectionStyle(open_conf_tab);
			is_open_tab_active = true;
			setTabDeselectionStyle(closed_conf_tab);
			closed_conf_scrollp.setVisible(false);
			open_conf_scrollp.setVisible(true);
		} else if( _tab == closed_conf_tab && is_open_tab_active ) {
			setTabSelectionStyle(closed_conf_tab);
			is_open_tab_active = false;
			setTabDeselectionStyle(open_conf_tab);
			open_conf_scrollp.setVisible(false);
			closed_conf_scrollp.setVisible(true);
		}
	}
}
