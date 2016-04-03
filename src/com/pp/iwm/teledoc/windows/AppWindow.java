package com.pp.iwm.teledoc.windows;

import java.awt.Point;
import java.util.Set;

import com.pp.iwm.teledoc.gui.ActionPane;
import com.pp.iwm.teledoc.gui.ConferenceTabsPane;
import com.pp.iwm.teledoc.gui.DockImageButton;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.FileCard;
import com.pp.iwm.teledoc.gui.FileExplorer;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.gui.Utils;
import com.pp.iwm.teledoc.objects.FileTree;
import com.sun.org.apache.bcel.internal.generic.LLOAD;

import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppWindow extends Window {
	
	// UI ELEMENTS
	private Rectangle rect_window_background; 
	private ImageButton ibtn_exit;
	private Label lbl_user;
	
	private Dockbar dockbar;
	public StatusBar status_bar;
	public ConferenceTabsPane tab_pane;
	public FileExplorer file_pane;
	public ActionPane action_pane;
	
	
	public AppWindow() {
		
	}
	
	private void populateDockbar() {
		// new conference
		DockImageButton btn_1 = new DockImageButton(Utils.IMG_NEW_CONF_ICON, "Stwórz now¹ konferencjê", Utils.ACT_NEW_CONF, dockbar);
		dockbar.addIcon(btn_1);
		btn_1.setOnAction(ev -> onCreateNewConference(btn_1, false));
		
		// find conference
		DockImageButton btn_2 = new DockImageButton(Utils.IMG_SEARCH_CONF_ICON, "Wyszukaj konferencjê", Utils.ACT_FIND_CONF, dockbar);
		dockbar.addIcon(btn_2);
		btn_2.setOnAction(ev -> onFindConference(btn_2));
		
		// upload file
		DockImageButton btn_3 = new DockImageButton(Utils.IMG_UPLOAD_ICON, "Wgraj plik", Utils.ACT_UPLOAD_FILE, dockbar);
		dockbar.addIcon(btn_3);
		btn_3.setOnAction(ev -> onUploadFile(btn_3));
		
		// download file
		DockImageButton btn_4 = new DockImageButton(Utils.IMG_DOWNLOAD_ICON, "Pobierz plik", Utils.ACT_DOWNLOAD_FILE, dockbar);
		dockbar.addIcon(btn_4);
		btn_4.setOnAction(ev -> onDownloadFile(btn_4));
		
		// new conference from file
		DockImageButton btn_5 = new DockImageButton(Utils.IMG_NEW_CONF_FROM_FILE_ICON, "Stwórz now¹ konferencjê z aktywnego pliku: ", Utils.ACT_NEW_CONF_FROM_FILE, dockbar);
		dockbar.addIcon(btn_5);
		btn_5.setOnAction(ev -> onCreateNewConference(btn_5, true));
		
		// find file
		DockImageButton btn_6 = new DockImageButton(Utils.IMG_SEARCH_FILE_ICON, "Wyszukaj plik", Utils.ACT_FIND_FILE, dockbar);
		dockbar.addIcon(btn_6);
		btn_6.setOnAction(ev -> onFindFile(btn_6));
		
		// help
		DockImageButton btn_7 = new DockImageButton(Utils.IMG_HELP_ICON, "Pomoc", Utils.ACT_SHOW_HELP, dockbar);
		dockbar.addIcon(btn_7);
		btn_7.setOnAction(ev -> onShowHelp(btn_7));
		
		// logout
		DockImageButton btn_8 = new DockImageButton(Utils.IMG_LOGOUT_ICON, "Wyloguj", Utils.ACT_LOGOUT, dockbar);
		dockbar.addIcon(btn_8);
		btn_8.setOnAction(ev -> onLogout());
	}
	
	private void onCreateNewConference(ImageButton btn, boolean from_file) {
		action_pane.create(btn);
	}
	
	private void onFindConference(ImageButton btn) {
		action_pane.create(btn);
	}
	
	private void onUploadFile(ImageButton btn) {
		// open system explorer
	}
	
	private void onDownloadFile(ImageButton btn) {
		// download file
	}
	
	private void onFindFile(ImageButton btn) {
		action_pane.create(btn);
	}
	
	private void onShowHelp(ImageButton btn) {
		// show help popup window
	}
	
	private void onLogout() {
		openWindow(new LoginWindow(), true);
	}

	private void modifyTabPane() {
		tab_pane.getStyleClass().add("floating");
		tab_pane.setStyle("-fx-tab-min-width: 90; -fx-tab-max-width: 90;");
		
		Set<Node> tabs = tab_pane.lookupAll(".tab-header-area > .headers-region > .tab:top > .tab-container > .tab-label");
		for(Node tab : tabs)
			tab.setStyle("-fx-text-fill: rgb(210, 210, 140); -fx-font-weight: normal;");
		
		tabs = null;
		tabs = tab_pane.lookupAll(".tab-header-area > .headers-region > .tab:selected > .tab-container > .tab-label");
		for(Node tab : tabs)
			tab.setStyle("-fx-text-fill: rgb(210, 210, 140); -fx-font-weight: bold;");
		
		//TODO: do poprawy
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

	@Override
	protected void createStage() {
		Scene scene = new Scene(root, 1024, 600, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		rect_window_background = new Rectangle(1024, 600);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		rect_window_background.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		rect_window_background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		// cross btn
		ibtn_exit = new ImageButton(Utils.IMG_EXIT_APP_ICON, Utils.HINT_CLOSE_APP, Utils.ACT_EXIT_APP);
		ibtn_exit.setLayoutX(990.0); ibtn_exit.setLayoutY(5.0);
		ibtn_exit.setOnAction(ev -> hide());
		
		// user label
		lbl_user = new Label("Patryk Lewandowski (patryk.jan.lewandowski@gmail.com)");
		lbl_user.setLayoutX(20.0); lbl_user.setLayoutY(12.0);
		lbl_user.setFont(Utils.LBL_FONT);
		lbl_user.setStyle("-fx-text-fill: rgb(140, 140, 170); -fx-font-weight: normal;");
		lbl_user.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		lbl_user.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		lbl_user.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		// dockbar
		dockbar = new Dockbar();
		dockbar.app_window = this;
		dockbar.setLayoutX(250.0); dockbar.setLayoutY(32.0);
		populateDockbar();
		
		// status bar
		status_bar = new StatusBar();
		status_bar.setLayoutY(580.0);
		
		// conference panel
		tab_pane = new ConferenceTabsPane(status_bar);
		tab_pane.setLayoutX(15.0); tab_pane.setLayoutY(32.0);
		
		// file explorer panel
		file_pane = new FileExplorer(this);
		file_pane.setLayoutX(250.0); file_pane.setLayoutY(32.0);
		
		// action panel
		action_pane = new ActionPane(status_bar);
		
		// add elements
		root.getChildren().add(rect_window_background);
		root.getChildren().add(tab_pane);
		root.getChildren().add(file_pane);
		root.getChildren().add(action_pane);
		root.getChildren().add(status_bar);
		root.getChildren().add(dockbar);

		root.getChildren().add(lbl_user);
		root.getChildren().add(ibtn_exit);
		
		stage.setScene(scene);
		
		modifyTabPane();
	}
	
}
