package com.pp.iwm.teledoc.layouts;

import com.pp.iwm.teledoc.gui.ActionPane;
import com.pp.iwm.teledoc.gui.ConferenceCard;
import com.pp.iwm.teledoc.gui.ConferencePanel;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.FileExplorer;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.AppWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

public class AppWindowLayout extends WindowLayout {

	// =======================================
	// FIELDS 
	// =======================================
	
	private AppWindow app_window;

	public Rectangle rect_window_background; 
	public ImageButton ibtn_exit;
	public Label lbl_user;
	
	public Dockbar dockbar;
	public StatusBar status_bar;
	public ConferencePanel conf_pane;
	public FileExplorer file_pane;
	public ActionPane action_pane;
	
	// =======================================
	// METHODS
	// =======================================
	
	
	public AppWindowLayout(Window _window) {
		super(_window);
		app_window = (AppWindow) window;
	}
	
	private void createBackground() {
		rect_window_background = new Rectangle(1026, 602);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setLayoutX(1.0); rect_window_background.setLayoutY(1.0);
		rect_window_background.setStroke(Color.rgb(45, 81, 90));
		rect_window_background.setStrokeWidth(2.0);
	}
	
	private void createExitIcon() {
		ibtn_exit = new ImageButton(Utils.IMG_EXIT_APP_ICON, Utils.HINT_CLOSE_APP, Utils.ACT_EXIT_APP);
		ibtn_exit.setLayoutX(992.0); ibtn_exit.setLayoutY(7.0);
	}
	
	private void createTitleLabel() {
		String title = User.instance().getName() + " " + User.instance().getSurname() + " (" + User.instance().getEmail() + ")";
		lbl_user = new Label(title);
		lbl_user.setLayoutX(22.0); lbl_user.setLayoutY(14.0);
		lbl_user.setFont(Utils.LBL_FONT);
		lbl_user.setStyle("-fx-text-fill: rgb(140, 140, 170); -fx-font-weight: normal;");
		lbl_user.setMouseTransparent(true);
	}
	
	private void createDockbar() {
		dockbar = new Dockbar(32.0, 8.0);
		dockbar.setLayoutX(252.0); dockbar.setLayoutY(34.0);
		populateDockbar();
	}
	
	private void populateDockbar() {
		// new conference
		ImageButton btn_1 = new ImageButton(Utils.IMG_NEW_CONF_ICON, Utils.HINT_CREATE_NEW_CONF, Utils.ACT_NEW_CONF);
		dockbar.addIconAndFitInBar(btn_1);
				
		// find conference
		ImageButton btn_2 = new ImageButton(Utils.IMG_SEARCH_CONF_ICON, Utils.HINT_SEARCH_CONF, Utils.ACT_FIND_CONF);
		dockbar.addIconAndFitInBar(btn_2);
				
		// upload file
		ImageButton btn_3 = new ImageButton(Utils.IMG_UPLOAD_ICON, Utils.HINT_UPLOAD_FILE, Utils.ACT_UPLOAD_FILE);
		dockbar.addIconAndFitInBar(btn_3);
				
		// download file
		ImageButton btn_4 = new ImageButton(Utils.IMG_DOWNLOAD_ICON, Utils.HINT_DOWNLOAD_FILE, Utils.ACT_DOWNLOAD_FILE);
		dockbar.addIconAndFitInBar(btn_4);
				
		// new conference from file
		ImageButton btn_5 = new ImageButton(Utils.IMG_NEW_CONF_FROM_FILE_ICON, Utils.HINT_CREATE_CONF_FROM_FILE, Utils.ACT_NEW_CONF_FROM_FILE);
		dockbar.addIconAndFitInBar(btn_5);
				
		// find file
		ImageButton btn_6 = new ImageButton(Utils.IMG_SEARCH_FILE_ICON, Utils.HINT_SEARCH_FILE, Utils.ACT_FIND_FILE);
		dockbar.addIconAndFitInBar(btn_6);
				
		// help
		ImageButton btn_7 = new ImageButton(Utils.IMG_HELP_ICON, Utils.HINT_HELP, Utils.ACT_SHOW_HELP);
		dockbar.addIconAndFitInBar(btn_7);
				
		// logout
		ImageButton btn_8 = new ImageButton(Utils.IMG_LOGOUT_ICON, Utils.HINT_LOGOUT, Utils.ACT_LOGOUT);
		dockbar.addIconAndFitInBar(btn_8);
	}
	
	private void createStatusBar() {
		status_bar = new StatusBar();
		status_bar.setLayoutX(2.0); status_bar.setLayoutY(582.0);
	}
	
	private void createFileExplorer() {
		file_pane = new FileExplorer(this);
		file_pane.setLayoutX(252.0); file_pane.setLayoutY(34.0);
	}
	
	private void createActionPane() {
		action_pane = new ActionPane(this);
		action_pane.setLayoutX(252.0); action_pane.setLayoutY(582.0);
	}
	
	private void createConfPane() {
		conf_pane = new ConferencePanel(this);
		conf_pane.setLayoutX(17.0); conf_pane.setLayoutY(56.0);
	}
	
	private void addElementsToScene() {
		root.getChildren().addAll(rect_window_background, 
								file_pane,
								action_pane,
								status_bar,
								dockbar,
								lbl_user,
								ibtn_exit,
								conf_pane);
	}
	
	@Override
	public void create() {
		Scene scene = new Scene(root, 1028, 604, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setOnCloseRequest(ev -> { app_window.hide(); ev.consume(); });		// TODO byæ mo¿e przenieœæ do AppWindow
		
		createBackground();
		createExitIcon();
		createTitleLabel();
		createDockbar();
		createStatusBar();
		createFileExplorer();
		createConfPane();
		createActionPane();
		
		addElementsToScene();
		
		stage.setScene(scene);
	}
	
	public void addTextToStatusBar(String _text) {
		status_bar.addText(_text);
	}
	
	public void removeTextFromStatusBar() {
		status_bar.removeText();
	}
	
	public void addHidePanelIcon() {
		ImageButton ibtn = new ImageButton(Utils.IMG_HIDE_PANEL, Utils.HINT_HIDE_PANEL, Utils.ACT_HIDE_PANEL);
		ibtn.addEventHandler(ActionEvent.ACTION, ev -> action_pane.hide());
		dockbar.addIconAndFitInBar(ibtn);
	}
	
	public void removeHidePanelIcon() {
		dockbar.removeIcon(dockbar.findIcon(Utils.ACT_HIDE_PANEL));
	}
	
	public void changeTitleBarText(String _text) {
		lbl_user.setText(_text);
	}
	
	public void conferenceCardAdded(ConferenceCard _card) {
		app_window.onConferenceCardAdded(_card);
	}
}
