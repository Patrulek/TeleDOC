package com.pp.iwm.teledoc.windows;

import java.awt.Point;
import java.util.List;
import java.util.Set;

import com.pp.iwm.teledoc.gui.ActionPane;
import com.pp.iwm.teledoc.gui.ActionPane.PaneState;
import com.pp.iwm.teledoc.gui.ConferencePanel;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.FileExplorer;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.gui.Utils;
import com.pp.iwm.teledoc.objects.Conference;
import com.pp.iwm.teledoc.objects.FileTree;
import com.pp.iwm.teledoc.objects.User;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

public class AppWindow extends Window {
	
	// ===========================================
	// FIELDS
	// ===========================================
	
	private Rectangle rect_window_background; 
	private ImageButton ibtn_exit;
	private Label lbl_user;
	
	private Dockbar dockbar;
	private StatusBar status_bar;
	private ConferencePanel conf_pane;
	private FileExplorer file_pane;
	private ActionPane action_pane;
	
	// ===========================================
	// METHODS
	// ===========================================
	
	public AppWindow(/*user data? */) {
		user = new User("Patryk", "Lewandowski", "mój.mail@gmail.com", this);
		user.loadDataFromDB();
		
		setTitleBar();
	}
	
	public void addConf(Conference _conf) {
		conf_pane.addConf(_conf);
	}
	
	public void removeConf(Conference _conf) {
		conf_pane.removeConf(_conf);
	}
	
	public void addTextToStatusBar(String _text) {
		status_bar.addText(_text);
	}
	
	public void removeTextFromStatusBar() {
		status_bar.removeText();
	}
	
	public void setFileExplorerRoot(FileTree _file_tree) {
		file_pane.setFileTree(_file_tree);
	}
	
	public void refreshFileExplorerView() {
		file_pane.refreshView();
	}
	
	public void addHidePanelIcon() {
		ImageButton ibtn = new ImageButton(Utils.IMG_HIDE_PANEL, Utils.HINT_HIDE_PANEL, Utils.ACT_HIDE_PANEL);
		ibtn.addEventHandler(ActionEvent.ACTION, ev -> action_pane.hide());
		dockbar.addIcon(ibtn);
	}
	
	public void removeHidePanelIcon() {
		dockbar.removeIcon(dockbar.findIcon(Utils.ACT_HIDE_PANEL));
	}
	
	private void onCreateNewConference(ImageButton _ibtn, boolean _from_file) {
		action_pane.changePaneState(PaneState.NEW_CONF);
	}
	
	private void onSearchConference(ImageButton _ibtn) {
		action_pane.changePaneState(PaneState.SEARCH_CONF);
	}
	
	private void onUploadFile(ImageButton _ibtn) {
		// open system explorer
	}
	
	private void onDownloadFile(ImageButton _ibtn) {
		// download file
	}
	
	private void onSearchFile(ImageButton _ibtn) {
		action_pane.changePaneState(PaneState.SEARCH_FILE);
	}
	
	private void onShowHelp(ImageButton _ibtn) {
		// show help popup window
	}
	
	private void onLogout() {
		openWindow(new LoginWindow(), true);
	}
	
	private void setTitleBar() {
		String str = user.getName() + " " + user.getSurname() + " (" + user.getEmail() + ") ";
		lbl_user.setText(str);
	}
	
	private void onWindowBackgroundMousePressed(MouseEvent ev) {
		mouse_pos = new Point((int)ev.getScreenX(), (int)ev.getScreenY());
	}
	
	private void onWindowBackgroundMouseDragged(MouseEvent ev) {
		if( (ev.getSceneY() < 24 && !is_dragged) || is_dragged  ) {
			is_dragged = true;
			stage.setX(stage.getX() + ev.getScreenX() - mouse_pos.x);
			stage.setY(stage.getY() + ev.getScreenY() - mouse_pos.y);
			mouse_pos = new Point((int)ev.getScreenX(), (int)ev.getScreenY());
		}
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent ev) {
		is_dragged = false;
	}
	
	private void onDockbarMouseMoved(MouseEvent _ev) {
		int selected_icon = dockbar.getSelectedIconIndex();
		int old_selected_icon = dockbar.getOldSelectedIconIndex();
		List<ImageButton> all_icons = dockbar.getIcons();
		
		if( old_selected_icon != selected_icon ) {
			removeTextFromStatusBar();
			
			if( selected_icon >= 0 ) {
				if( all_icons.get(selected_icon).getHint().equals(Utils.HINT_CREATE_CONF_FROM_FILE)
					&& file_pane.getSelectedCard() != null && !file_pane.getSelectedCard().getFile().isFolder() )
					addTextToStatusBar(all_icons.get(selected_icon).getHint() + file_pane.getSelectedCard().getFile().getName());
				else
					addTextToStatusBar(all_icons.get(selected_icon).getHint());
			}
		}
	}
	
	private void onDockbarMouseEntered(MouseEvent _ev) {
		addTextToStatusBar("");
	}
	
	private void onDockbarMouseExited(MouseEvent _ev) {
		dockbar.resetSelection();
		removeTextFromStatusBar();
	}
	
	private void populateDockbar() {
		// new conference
		ImageButton btn_1 = new ImageButton(Utils.IMG_NEW_CONF_ICON, Utils.HINT_CREATE_NEW_CONF, Utils.ACT_NEW_CONF);
		dockbar.addIcon(btn_1);
		btn_1.setOnAction(ev -> onCreateNewConference(btn_1, false));
		
		// find conference
		ImageButton btn_2 = new ImageButton(Utils.IMG_SEARCH_CONF_ICON, Utils.HINT_SEARCH_CONF, Utils.ACT_FIND_CONF);
		dockbar.addIcon(btn_2);
		btn_2.setOnAction(ev -> onSearchConference(btn_2));
		
		// upload file
		ImageButton btn_3 = new ImageButton(Utils.IMG_UPLOAD_ICON, Utils.HINT_UPLOAD_FILE, Utils.ACT_UPLOAD_FILE);
		dockbar.addIcon(btn_3);
		btn_3.setOnAction(ev -> onUploadFile(btn_3));
		
		// download file
		ImageButton btn_4 = new ImageButton(Utils.IMG_DOWNLOAD_ICON, Utils.HINT_DOWNLOAD_FILE, Utils.ACT_DOWNLOAD_FILE);
		dockbar.addIcon(btn_4);
		btn_4.setOnAction(ev -> onDownloadFile(btn_4));
		
		// new conference from file
		ImageButton btn_5 = new ImageButton(Utils.IMG_NEW_CONF_FROM_FILE_ICON, Utils.HINT_CREATE_CONF_FROM_FILE, Utils.ACT_NEW_CONF_FROM_FILE);
		dockbar.addIcon(btn_5);
		btn_5.setOnAction(ev -> onCreateNewConference(btn_5, true));
		
		// find file
		ImageButton btn_6 = new ImageButton(Utils.IMG_SEARCH_FILE_ICON, Utils.HINT_SEARCH_FILE, Utils.ACT_FIND_FILE);
		dockbar.addIcon(btn_6);
		btn_6.setOnAction(ev -> onSearchFile(btn_6));
		
		// help
		ImageButton btn_7 = new ImageButton(Utils.IMG_HELP_ICON, Utils.HINT_HELP, Utils.ACT_SHOW_HELP);
		dockbar.addIcon(btn_7);
		btn_7.setOnAction(ev -> onShowHelp(btn_7));
		
		// logout
		ImageButton btn_8 = new ImageButton(Utils.IMG_LOGOUT_ICON, Utils.HINT_LOGOUT, Utils.ACT_LOGOUT);
		dockbar.addIcon(btn_8);
		btn_8.setOnAction(ev -> onLogout());
	}

	@Override
	protected void createStage() {
		Scene scene = new Scene(root, 1028, 604, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		rect_window_background = new Rectangle(1024, 600);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setLayoutX(2.0); rect_window_background.setLayoutY(2.0);
		rect_window_background.setStroke(Color.rgb(45, 81, 90));
		rect_window_background.setStrokeWidth(2.0);
		rect_window_background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		rect_window_background.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		rect_window_background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		// cross btn
		ibtn_exit = new ImageButton(Utils.IMG_EXIT_APP_ICON, Utils.HINT_CLOSE_APP, Utils.ACT_EXIT_APP);
		ibtn_exit.setLayoutX(992.0); ibtn_exit.setLayoutY(7.0);
		ibtn_exit.setOnAction(ev -> hide());
		ibtn_exit.addEventFilter(MouseEvent.MOUSE_ENTERED, ev -> status_bar.addText(ibtn_exit.getHint()));
		ibtn_exit.addEventFilter(MouseEvent.MOUSE_EXITED, ev -> status_bar.removeText());
		
		// user label
		lbl_user = new Label("Patryk Lewandowski (moj.mail@gmail.com)");
		lbl_user.setLayoutX(22.0); lbl_user.setLayoutY(14.0);
		lbl_user.setFont(Utils.LBL_FONT);
		lbl_user.setStyle("-fx-text-fill: rgb(140, 140, 170); -fx-font-weight: normal;");
		lbl_user.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		lbl_user.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		lbl_user.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		// dockbar
		dockbar = new Dockbar(32.0, 8.0);
		dockbar.setLayoutX(252.0); dockbar.setLayoutY(34.0);
		dockbar.addEventFilter(MouseEvent.MOUSE_MOVED, ev -> onDockbarMouseMoved(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onDockbarMouseEntered(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onDockbarMouseExited(ev));
		populateDockbar();
		
		// status bar
		status_bar = new StatusBar();
		status_bar.setLayoutX(2.0); status_bar.setLayoutY(582.0);
		
		// file explorer panel
		file_pane = new FileExplorer(this);
		file_pane.setLayoutX(252.0); file_pane.setLayoutY(34.0);
		
		// action panel
		action_pane = new ActionPane(this);
		action_pane.setLayoutX(252.0); action_pane.setLayoutY(582.0);
		
		// add elements
		root.getChildren().add(rect_window_background);
		root.getChildren().add(file_pane);
		root.getChildren().add(action_pane);
		root.getChildren().add(status_bar);
		root.getChildren().add(dockbar);

		root.getChildren().add(lbl_user);
		root.getChildren().add(ibtn_exit);
		
		conf_pane = new ConferencePanel(this);
		conf_pane.setLayoutX(17.0); conf_pane.setLayoutY(56.0);
		root.getChildren().add(conf_pane);
		
		stage.setScene(scene);
	}
	
}
