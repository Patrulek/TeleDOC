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
	
	// BTN_ACTIONS
	public static final String ACT_EXIT_APP = "EXIT_APP";
	public static final String ACT_NEW_CONF = "NEW_CONF";
	public static final String ACT_FIND_CONF = "FIND_CONF";
	public static final String ACT_UPLOAD_FILE = "UPLOAD_FILE";
	public static final String ACT_DOWNLOAD_FILE = "DOWNLOAD_FILE";
	public static final String ACT_NEW_CONF_FROM_FILE = "NEW_CONF_FROM_FILE";
	public static final String ACT_FIND_FILE = "FIND_FILE";
	public static final String ACT_SHOW_HELP = "SHOW_HELP";
	public static final String ACT_LOGOUT = "LOGOUT";
	
	private Dockbar dockbar;
	public StatusBar status_bar;
	public ConferenceTabsPane tab_pane;
	public FileExplorer file_pane;
	public ActionPane action_pane;
	
	private boolean dragging = false;
	
	public AppWindow() {
		
	}
	
	private void populateDockbar() {
		// new conference
		DockImageButton btn_1 = new DockImageButton("/assets/add_new_conf.png", "Stwórz now¹ konferencjê", ACT_NEW_CONF, dockbar);
		dockbar.addIcon(btn_1);
		btn_1.setOnAction(event -> onCreateNewConference(btn_1, false));
		
		// find conference
		DockImageButton btn_2 = new DockImageButton("/assets/search_conf.png", "Wyszukaj konferencjê", ACT_FIND_CONF, dockbar);
		dockbar.addIcon(btn_2);
		btn_2.setOnAction(event -> onFindConference(btn_2));
		
		// upload file
		DockImageButton btn_3 = new DockImageButton("/assets/upload_file.png", "Wgraj plik", ACT_UPLOAD_FILE, dockbar);
		dockbar.addIcon(btn_3);
		btn_3.setOnAction(event -> onUploadFile(btn_3));
		
		// download file
		DockImageButton btn_4 = new DockImageButton("/assets/download_file.png", "Pobierz plik", ACT_DOWNLOAD_FILE, dockbar);
		dockbar.addIcon(btn_4);
		btn_4.setOnAction(event -> onDownloadFile(btn_4));
		
		// new conference from file
		DockImageButton btn_5 = new DockImageButton("/assets/new_conf_from_file.png", "Stwórz now¹ konferencjê z aktywnego pliku: ", ACT_NEW_CONF_FROM_FILE, dockbar);
		dockbar.addIcon(btn_5);
		btn_5.setOnAction(event -> onCreateNewConference(btn_5, true));
		
		// find file
		DockImageButton btn_6 = new DockImageButton("/assets/find_file.png", "Wyszukaj plik", ACT_FIND_FILE, dockbar);
		dockbar.addIcon(btn_6);
		btn_6.setOnAction(event -> onFindFile(btn_6));
		
		// help
		DockImageButton btn_7 = new DockImageButton("/assets/help_icon.png", "Pomoc", ACT_SHOW_HELP, dockbar);
		dockbar.addIcon(btn_7);
		btn_7.setOnAction(event -> onShowHelp(btn_7));
		
		// logout
		DockImageButton btn_8 = new DockImageButton("/assets/logout.png", "Wyloguj", ACT_LOGOUT, dockbar);
		dockbar.addIcon(btn_8);
		btn_8.setOnAction(event -> onLogout());
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
			tab.setStyle("-fx-text-fill: rgb(182, 182, 182); -fx-font-weight: normal;");
		
		tabs = null;
		tabs = tab_pane.lookupAll(".tab-header-area > .headers-region > .tab:selected > .tab-container > .tab-label");
		for(Node tab : tabs)
			tab.setStyle("-fx-text-fill: rgb(182, 182, 182); -fx-font-weight: bold;");
		
		//TODO: do poprawy
	}
	
	private void onFilePaneMousePressed(MouseEvent event) {
		mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY());
	}
	
	private void onFilePaneMouseDragged(MouseEvent event) {
		if( (event.getSceneY() < 32 && !dragging) || dragging  ) {
			dragging = true;
			stage.setX(stage.getX() + event.getScreenX() - mouse_pos.x);
			stage.setY(stage.getY() + event.getScreenY() - mouse_pos.y);
			mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY());
		}
	}
	
	private void onFilePaneMouseReleased(MouseEvent event) {
		dragging = false;
	}

	@Override
	protected void createStage() {
		Group root = new Group();
		// hide window content
		Scene scene = new Scene(root, 1024, 600, Color.rgb(0, 0, 0, 0));
		scene.setCursor(new ImageCursor(new Image("/assets/cursor.png")));
		
		
		// hide title bar
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		Rectangle r_window_content = new Rectangle(1024, 600);
		r_window_content.setFill(Utils.PRIMARY_COLOR);
		
		// cross btn
		ImageButton btn_exit = new ImageButton("/assets/exit_icon.png", "Zamknij program", ACT_EXIT_APP);
		btn_exit.setLayoutX(989.0); btn_exit.setLayoutY(5.0);
		btn_exit.setOnAction(event -> this.hide());
		
		// dockbar
		dockbar = new Dockbar();
		dockbar.app_window = this;
		populateDockbar();
		
		// status bar
		status_bar = new StatusBar();
		status_bar.setLayoutY(580.0);
		
		// conference panel
		tab_pane = new ConferenceTabsPane(status_bar);
		
		// file explorer panel
		file_pane = new FileExplorer(this);
		file_pane.setOnMousePressed(event -> onFilePaneMousePressed(event));
		file_pane.setOnMouseDragged(event -> onFilePaneMouseDragged(event));
		file_pane.setOnMouseReleased(event -> onFilePaneMouseReleased(event));
		
		// action panel
		action_pane = new ActionPane(status_bar);
		
		// add elements
		root.getChildren().add(r_window_content);
		root.getChildren().add(tab_pane);
		root.getChildren().add(file_pane);
		root.getChildren().add(action_pane);
		root.getChildren().add(status_bar);
		root.getChildren().add(dockbar);
		
		root.getChildren().add(btn_exit);
		
		stage.setScene(scene);
		
		modifyTabPane();
	}
	
}
