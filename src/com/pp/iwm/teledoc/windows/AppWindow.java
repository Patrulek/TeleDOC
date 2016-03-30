package com.pp.iwm.teledoc.windows;

import java.awt.Point;
import java.util.Set;

import com.pp.iwm.teledoc.gui.ConferenceTabsPane;
import com.pp.iwm.teledoc.gui.DockImageButton;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.gui.Utils;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppWindow {
	public Stage stage;
	public Point mouse_pos;
	
	private TextField tf_email;
	private PasswordField pf_password;
	private Label lbl_error;
	private Dockbar dockbar;
	public StatusBar status_bar;
	public ConferenceTabsPane tab_pane;
	
	private boolean dragging = false;
	
	public AppWindow() {
		stage = new Stage();
		mouse_pos = new Point(0, 0);
		
		Group root = new Group();
		// hide window content
		Scene scene = new Scene(root, 1024, 600, Color.rgb(0, 0, 0, 0));
		// hide title bar
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		Rectangle r_window_content = new Rectangle(1024, 600);
		r_window_content.setFill(Utils.PRIMARY_COLOR);
		r_window_content.setArcHeight(10.0);
		r_window_content.setArcWidth(10.0);
		r_window_content.setOnMousePressed(event -> mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY()));
		r_window_content.setOnMouseDragged(event -> {
														if( (event.getSceneY() < 32 && !dragging) || dragging  ) {
															dragging = true;
															stage.setX(stage.getX() + event.getScreenX() - mouse_pos.x);
															stage.setY(stage.getY() + event.getScreenY() - mouse_pos.y);
															mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY());
														}
													});
		r_window_content.setOnMouseReleased(event -> dragging = false);
		
		// cross btn
		ImageButton btn_exit = new ImageButton("/assets/exit_icon.png");
		btn_exit.setLayoutX(989.0); btn_exit.setLayoutY(5.0);
		btn_exit.setOnAction(event -> this.hide());
		
		// dockbar
		dockbar = new Dockbar();
		dockbar.app_window = this;
		dockbar.setLayoutX(312); dockbar.setLayoutY(520);
		populateDockbar();
		
		// status bar
		status_bar = new StatusBar();
		status_bar.setLayoutY(580.0);
		
		// conference panel
		tab_pane = new ConferenceTabsPane();
		

		
		// add elements
		root.getChildren().add(r_window_content);
		root.getChildren().add(btn_exit);
		root.getChildren().add(tab_pane);
		root.getChildren().add(status_bar);
		root.getChildren().add(dockbar);
		
		stage.setScene(scene);
		stage.show();
		
		modifyTabPane();
	}
	
	public void show() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
	
	private void populateDockbar() {
		// new conference
		DockImageButton btn_1 = new DockImageButton("/assets/add_new_conf.png", "Stwórz now¹ konferencjê", dockbar);
		dockbar.addIcon(btn_1);
		
		// find conference
		DockImageButton btn_2 = new DockImageButton("/assets/search_conf.png", "Wyszukaj konferencjê", dockbar);
		dockbar.addIcon(btn_2);
		
		// upload file
		DockImageButton btn_3 = new DockImageButton("/assets/upload_file.png", "Wgraj plik", dockbar);
		dockbar.addIcon(btn_3);
		
		// downlaod file
		DockImageButton btn_4 = new DockImageButton("/assets/download_file.png", "Pobierz plik", dockbar);
		dockbar.addIcon(btn_4);
		
		// new conference from file
		DockImageButton btn_5 = new DockImageButton("/assets/new_conf_from_file.png", "Stwórz now¹ konferencjê z aktywnego pliku", dockbar);
		dockbar.addIcon(btn_5);
		
		// find file
		DockImageButton btn_6 = new DockImageButton("/assets/find_file.png", "Wyszukaj plik", dockbar);
		dockbar.addIcon(btn_6);

		// options
		DockImageButton btn_8 = new DockImageButton("/assets/options.png", "Opcje", dockbar);
		dockbar.addIcon(btn_8);
		
		// help
		DockImageButton btn_7 = new DockImageButton("/assets/help_icon.png", "Pomoc", dockbar);
		dockbar.addIcon(btn_7);
		
		// logout
		DockImageButton btn_9 = new DockImageButton("/assets/logout.png", "Wyloguj", dockbar);
		dockbar.addIcon(btn_9);
		btn_9.setOnAction(event -> logout());
	}
	
	private void logout() {
		new LoginWindow();
		this.hide();
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
	
}
