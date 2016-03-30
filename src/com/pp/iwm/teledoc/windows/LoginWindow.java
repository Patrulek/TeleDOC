package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.Utils;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginWindow {
	
	public Stage stage;
	public Point mouse_pos;
	
	public LoginWindow() {
		stage = new Stage();
		mouse_pos = new Point(0, 0);
		
		Group root = new Group();
		// hide window content
		Scene scene = new Scene(root, 400, 400, Color.rgb(0, 0, 0, 0));
		// hide title bar
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		Rectangle r_window_content = new Rectangle(400, 400);
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
		btn_exit.setLayoutX(365.0); btn_exit.setLayoutY(5.0);
		btn_exit.setOnAction(event -> Platform.exit());
		
		// teledoc logo
		ImageView iv_logo = new ImageView(new Image("/assets/teledoc_logo.png"));
		iv_logo.setLayoutX(50.0); iv_logo.setLayoutY(50.0);
		
		// username icon
		ImageView iv_email = new ImageView(new Image("/assets/name_icon.png")); // TODO:  email icon
		iv_email.setLayoutX(20.0); iv_email.setLayoutY(157.0);
		
		// username text field
		TextField tf_email = new TextField();
		tf_email.setLayoutX(55.0); tf_email.setLayoutY(150.0);
		tf_email.setPrefWidth(300.0);
		tf_email.setPromptText("email");
		tf_email.setFont(Utils.TF_FONT);
		tf_email.setStyle("-fx-text-fill: rgb(114, 114, 114); "
							+ "-fx-prompt-text-fill: rgb(182, 182, 182); "
							+ "-fx-highlight-text-fill:black; "
							+ "-fx-highlight-fill: gray; "
							+ "-fx-background-color: rgb(207, 216, 220); ");
		
		// password icon
		ImageView iv_password = new ImageView(new Image("/assets/pass_icon.png"));
		iv_password.setLayoutX(24.0); iv_password.setLayoutY(207.0);
		
		// password field
		PasswordField pf_password = new PasswordField();
		pf_password.setLayoutX(55.0); pf_password.setLayoutY(200.0);
		pf_password.setPrefWidth(300.0);
		pf_password.setPromptText("password");
		pf_password.setFont(Utils.TF_FONT);
		pf_password.setStyle("-fx-text-fill: rgb(114, 114, 114); "
							+ "-fx-prompt-text-fill: rgb(182, 182, 182); "
							+ "-fx-highlight-text-fill:black; "
							+ "-fx-highlight-fill: gray; "
							+ "-fx-background-color: rgb(207, 216, 220); ");
		
		// error label
		Label lbl_error = new Label();
		lbl_error.setLayoutX(55.0); lbl_error.setLayoutY(250.0);
		lbl_error.setPrefWidth(300.0);
		lbl_error.setFont(Utils.LBL_FONT);
		lbl_error.setText("Nieprawid³owy email/has³o");
		lbl_error.setStyle("-fx-text-fill: rgb(255, 64, 129);"
							+ "-fx-alignment: center;");
		//lbl_error.setVisible(false);
		
		// image buttons
		ImageButton btn_register = new ImageButton("/assets/register_icon.png");
		btn_register.setLayoutX(65.0); btn_register.setLayoutY(300.0);
		btn_register.setPrefWidth(64.0);
		
		ImageButton btn_remind = new ImageButton("/assets/remind_password.png");
		btn_remind.setLayoutX(155.0); btn_remind.setLayoutY(300.0);
		btn_remind.setPrefWidth(64.0);
		
		ImageButton btn_login = new ImageButton("/assets/login_icon.png");
		btn_login.setLayoutX(245.0); btn_login.setLayoutY(300.0);
		btn_login.setPrefWidth(64.0);
		
		// add elements
		root.getChildren().add(r_window_content);
		root.getChildren().add(btn_exit);
		root.getChildren().add(iv_logo);
		root.getChildren().add(iv_email);
		root.getChildren().add(tf_email);
		root.getChildren().add(iv_password);
		root.getChildren().add(pf_password);
		root.getChildren().add(lbl_error);
		root.getChildren().add(btn_register);
		root.getChildren().add(btn_remind);
		root.getChildren().add(btn_login);
		stage.setScene(scene);
		stage.show();
	}
	
	public void show() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
}
