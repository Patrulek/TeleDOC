package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.gui.Utils;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
	private StatusBar status_bar;
	
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
		r_window_content.setOnMouseDragged(event -> {stage.setX(stage.getX() + event.getScreenX() - mouse_pos.x);
													 stage.setY(stage.getY() + event.getScreenY() - mouse_pos.y);
													 mouse_pos = new Point((int)event.getScreenX(), (int)event.getScreenY());
													});
		
		// cross btn
		ImageButton btn_exit = new ImageButton("/assets/exit_icon.png");
		btn_exit.setLayoutX(989.0); btn_exit.setLayoutY(5.0);
		btn_exit.setOnAction(event -> this.hide());
		
		// dockbar
		dockbar = new Dockbar();
		dockbar.setLayoutX(312); dockbar.setLayoutY(520);
		populateDockbar();
		
		// status bar
		status_bar = new StatusBar();
		status_bar.setLayoutY(580.0);
		
		// add elements
		root.getChildren().add(r_window_content);
		root.getChildren().add(btn_exit);
		root.getChildren().add(dockbar);
		root.getChildren().add(status_bar);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void show() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
	
	private void populateDockbar() {
		// new conference
		ImageButton btn_1 = new ImageButton("/assets/add_new_conf.png");
		dockbar.addIcon(btn_1);
		
		// find conference
		ImageButton btn_2 = new ImageButton("/assets/search_conf.png");
		dockbar.addIcon(btn_2);
		
		// upload file
		ImageButton btn_3 = new ImageButton("/assets/upload_file.png");
		dockbar.addIcon(btn_3);
		
		// downlaod file
		ImageButton btn_4 = new ImageButton("/assets/download_file.png");
		dockbar.addIcon(btn_4);
		
		// new conference from file
		ImageButton btn_5 = new ImageButton("/assets/exit_icon.png");
		dockbar.addIcon(btn_5);
		
		// find file
		ImageButton btn_6 = new ImageButton("/assets/exit_icon.png");
		dockbar.addIcon(btn_6);
		
		// help
		ImageButton btn_7 = new ImageButton("/assets/exit_icon.png");
		dockbar.addIcon(btn_7);
		
		// cancel conference filter
		ImageButton btn_8 = new ImageButton("/assets/exit_icon.png");
		dockbar.addIcon(btn_8);
		
		// logout
		ImageButton btn_9 = new ImageButton("/assets/exit_icon.png");
		dockbar.addIcon(btn_9);
	}
}
