package com.pp.iwm.teledoc.windows;

import java.awt.Point;
import java.util.List;
import java.util.Set;

import com.pp.iwm.teledoc.gui.ActionPane;
import com.pp.iwm.teledoc.gui.ActionPane.PaneState;
import com.pp.iwm.teledoc.layouts.AppWindowLayout;
import com.pp.iwm.teledoc.gui.ConferencePanel;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.FileExplorer;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.models.AppWindowModel;
import com.pp.iwm.teledoc.objects.Conference;
import com.pp.iwm.teledoc.objects.FileTree;
import com.pp.iwm.teledoc.objects.User;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppWindow extends Window {
	
	// ===========================================
	// FIELDS
	// ===========================================
	
	private AppWindowModel window_model;
	private AppWindowLayout window_layout;
	
	// ===========================================
	// METHODS
	// ===========================================
	
	public AppWindow() {
		super();
	}
	
	public void addConf(Conference _conf) {
		window_layout.conf_pane.addConf(_conf);
	}
	
	public void removeConf(Conference _conf) {
		window_layout.conf_pane.removeConf(_conf);
	}
	
	public void setFileExplorerRoot(FileTree _file_tree) {
		window_layout.file_pane.setFileTree(_file_tree);
	}
	
	public void refreshFileExplorerView() {
		window_layout.file_pane.refreshView();
	}
	
	private void onCreateNewConference(boolean _from_file) {
		window_layout.action_pane.changeStateAndRefresh(PaneState.NEW_CONF);
	}
	
	private void onSearchConference() {
		window_layout.action_pane.changeStateAndRefresh(PaneState.SEARCH_CONF);
	}
	
	private void onUploadFile() {
		// open system explorer
	}
	
	private void onDownloadFile() {
		// download file
	}
	
	private void onSearchFile() {
		window_layout.action_pane.changeStateAndRefresh(PaneState.SEARCH_FILE);
	}
	
	private void onShowHelp() {
		// show help popup window
	}
	
	private void onLogout() {
		openWindowAndHideCurrent(new LoginWindow());
	}
	
	private void onWindowBackgroundMousePressed(MouseEvent _ev) {
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}
	
	private void onWindowBackgroundMouseDragged(MouseEvent _ev) {
		Stage stage = window_layout.stage;
		Point2D mouse_pos = window_model.getMousePos();
		
		if( _ev.getSceneY() < 24  || window_model.isDragged() ) {
			window_model.setDragged(true);
			stage.setX(stage.getX() + _ev.getScreenX() - mouse_pos.getX());
			stage.setY(stage.getY() + _ev.getScreenY() - mouse_pos.getY());
		}
		
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent ev) {
		window_model.setDragged(false);
	}
	
	private void onDockbarMouseMoved(MouseEvent _ev) {
		Dockbar dockbar = window_layout.dockbar;
		FileExplorer file_pane = window_layout.file_pane;
		
		int selected_icon = dockbar.getHoveredIconIndex();
		int old_selected_icon = dockbar.getOldHoveredIconIndex();
		List<ImageButton> all_icons = dockbar.getIcons();
		
		if( old_selected_icon != selected_icon ) {
			window_layout.removeTextFromStatusBar();
			
			if( selected_icon >= 0 ) {
				if( all_icons.get(selected_icon).getHint().equals(Utils.HINT_CREATE_CONF_FROM_FILE)
					&& file_pane.getSelectedCard() != null && !file_pane.getSelectedCard().getFile().isFolder() )
					window_layout.addTextToStatusBar(all_icons.get(selected_icon).getHint() + file_pane.getSelectedCard().getFile().getName());
				else
					window_layout.addTextToStatusBar(all_icons.get(selected_icon).getHint());
			}
		}
	}
	
	private void onDockbarMouseEntered(MouseEvent _ev) {
		window_layout.addTextToStatusBar("");
	}
	
	private void onDockbarMouseExited(MouseEvent _ev) {
		window_layout.dockbar.resetHoveredIndex();
		window_layout.removeTextFromStatusBar();
	}

	@Override
	protected void createLayout() {
		window_layout = new AppWindowLayout(this);
		layout = window_layout;
	}


	@Override
	protected void createModel() {
		window_model = new AppWindowModel(this);
		model = window_model;
	}

	@Override
	protected void initEventHandlers() {
		Rectangle background = window_layout.rect_window_background;
		ImageButton ibtn_exit = window_layout.ibtn_exit;
		Label lbl_user = window_layout.lbl_user;
		Dockbar dockbar = window_layout.dockbar;
		StatusBar status_bar = window_layout.status_bar;
		
		background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		background.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));

		ibtn_exit.setOnAction(ev -> hide());
		ibtn_exit.addEventFilter(MouseEvent.MOUSE_ENTERED, ev -> status_bar.addText(ibtn_exit.getHint()));
		ibtn_exit.addEventFilter(MouseEvent.MOUSE_EXITED, ev -> status_bar.removeText());
		
		lbl_user.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		lbl_user.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		lbl_user.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		dockbar.addEventFilter(MouseEvent.MOUSE_MOVED, ev -> onDockbarMouseMoved(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onDockbarMouseEntered(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onDockbarMouseExited(ev));
		dockbar.getIcons().get(0).setOnAction(ev -> onCreateNewConference(false));
		dockbar.getIcons().get(1).setOnAction(ev -> onSearchConference());
		dockbar.getIcons().get(2).setOnAction(ev -> onUploadFile());
		dockbar.getIcons().get(3).setOnAction(ev -> onDownloadFile());
		dockbar.getIcons().get(4).setOnAction(ev -> onCreateNewConference(true));
		dockbar.getIcons().get(5).setOnAction(ev -> onSearchFile());
		dockbar.getIcons().get(6).setOnAction(ev -> onShowHelp());
		dockbar.getIcons().get(7).setOnAction(ev -> onLogout());
	}
	
}
