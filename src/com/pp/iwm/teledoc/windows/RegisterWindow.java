package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.Utils;

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

public class RegisterWindow {
	public Stage stage;
	public Point mouse_pos;
	
	private Label lbl_error = null;
	private TextField tf_name = null;
	private TextField tf_surname = null;
	private TextField tf_email = null;
	private PasswordField pf_password = null;
	
	public RegisterWindow() {
		stage = new Stage();
		mouse_pos = new Point(0, 0);
		
		Group root = new Group();
		// hide window content
		Scene scene = new Scene(root, 400, 600, Color.rgb(0, 0, 0, 0));
		// hide title bar
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		Rectangle r_window_content = new Rectangle(400, 600);
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
		btn_exit.setOnAction(event -> this.hide());
		
		// teledoc logo
		ImageView iv_logo = new ImageView(new Image("/assets/teledoc_logo.png"));
		iv_logo.setLayoutX(50.0); iv_logo.setLayoutY(50.0);
		
		// name icon
		ImageView iv_name = new ImageView(new Image("/assets/name_icon.png"));
		iv_name.setLayoutX(20.0); iv_name.setLayoutY(167.0);
		
		// name text field
		tf_name = new TextField();
		tf_name.setLayoutX(55.0); tf_name.setLayoutY(160.0);
		tf_name.setPrefWidth(300.0);
		tf_name.setPromptText("name");
		tf_name.setFont(Utils.TF_FONT);
		tf_name.setStyle("-fx-text-fill: rgb(114, 114, 114); "
							+ "-fx-prompt-text-fill: rgb(182, 182, 182); "
							+ "-fx-highlight-text-fill:black; "
							+ "-fx-highlight-fill: gray; "
							+ "-fx-background-color: rgb(207, 216, 220); ");
		
		// surname icon
		ImageView iv_surname = new ImageView(new Image("/assets/name_icon.png")); // TODO:  surname icon
		iv_surname.setLayoutX(20.0); iv_surname.setLayoutY(237.0);
				
		// surname text field
		tf_surname = new TextField();
		tf_surname.setLayoutX(55.0); tf_surname.setLayoutY(230.0);
		tf_surname.setPrefWidth(300.0);
		tf_surname.setPromptText("surname");
		tf_surname.setFont(Utils.TF_FONT);
		tf_surname.setStyle("-fx-text-fill: rgb(114, 114, 114); "
							+ "-fx-prompt-text-fill: rgb(182, 182, 182); "
							+ "-fx-highlight-text-fill:black; "
							+ "-fx-highlight-fill: gray; "
							+ "-fx-background-color: rgb(207, 216, 220); ");
		

		// email icon
		ImageView iv_email = new ImageView(new Image("/assets/name_icon.png")); // TODO:  email icon
		iv_email.setLayoutX(20.0); iv_email.setLayoutY(307.0);
				
		// email text field
		tf_email = new TextField();
		tf_email.setLayoutX(55.0); tf_email.setLayoutY(300.0);
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
		iv_password.setLayoutX(24.0); iv_password.setLayoutY(377.0);
		
		// password field
		pf_password = new PasswordField();
		pf_password.setLayoutX(55.0); pf_password.setLayoutY(370.0);
		pf_password.setPrefWidth(300.0);
		pf_password.setPromptText("password");
		pf_password.setFont(Utils.TF_FONT);
		pf_password.setStyle("-fx-text-fill: rgb(114, 114, 114); "
							+ "-fx-prompt-text-fill: rgb(182, 182, 182); "
							+ "-fx-highlight-text-fill:black; "
							+ "-fx-highlight-fill: gray; "
							+ "-fx-background-color: rgb(207, 216, 220); ");
		
		
		// password error label
		lbl_error = new Label();
		lbl_error.setLayoutX(55.0); lbl_error.setLayoutY(435.0);
		lbl_error.setPrefWidth(300.0);
		lbl_error.setFont(Utils.LBL_FONT);
		lbl_error.setText("");
		lbl_error.setStyle("-fx-text-fill: rgb(255, 64, 129);"
							+ "-fx-alignment: center;");
		
		
		// image buttons
		ImageButton btn_register = new ImageButton("/assets/register_icon.png");
		btn_register.setLayoutX(220.0); btn_register.setLayoutY(470.0);
		btn_register.setPrefWidth(64.0);
		btn_register.setOnAction(event -> registerAccount());
		
		ImageButton btn_back = new ImageButton("/assets/back_icon.png");
		btn_back.setLayoutX(90.0); btn_back.setLayoutY(470.0);
		btn_back.setPrefWidth(64.0);
		btn_back.setOnAction(event -> openLoginWindow(false));
		
		
		// add elements
		root.getChildren().add(r_window_content);
		root.getChildren().add(btn_exit);
		root.getChildren().add(iv_logo);
		root.getChildren().add(iv_name);
		root.getChildren().add(tf_name);
		root.getChildren().add(iv_surname);
		root.getChildren().add(tf_surname);
		root.getChildren().add(iv_email);
		root.getChildren().add(tf_email);
		root.getChildren().add(iv_password);
		root.getChildren().add(pf_password);
		root.getChildren().add(lbl_error);
		root.getChildren().add(btn_register);
		root.getChildren().add(btn_back);
		stage.setScene(scene);
		stage.show();
	}
	
	public void show() {
		stage.show();
	}
	
	public void hide() {
		stage.hide();
	}
	
	private void openLoginWindow(boolean register_success) {
		LoginWindow login = new LoginWindow();
		this.hide();
	}
	
	private boolean validateTextFields() {
		if( tf_email.getText().equals("") || tf_name.equals("") || tf_surname.equals("") || pf_password.getText().equals("")) {
			lbl_error.setText("Proszê wype³niæ wszystkie pola");
			return false;
		}
		
		return true;
	}
	
	private boolean validateEmail() {
		String email = tf_email.getText();
		
		if( !email.contains("@") || !email.contains(".") ) {
			lbl_error.setText("Niepoprawny email");
			return false;
		}
		
		return true;
	}
	
	private boolean validatePassword() {
		if( pf_password.getText().length() < 6 ) {
			lbl_error.setText("Podane has³o jest za krótkie");
			return false;
		}
		
		return true;
	}
	
	private boolean canConnect() {
		return false;
	}
	
	private void sendDataToServer() {
		/*
		 * if( !canConnect() )
		 * 		show_error();
		 * else
		 * 		send_data();
		 * 
		 */
	}
	
	private void registerAccount() {
		lbl_error.setText("");
		
		if( validateTextFields() && validateEmail() && validatePassword() )
			sendDataToServer();
	}
	
	private void readDataFromServer() {
		/*
		 * if( email_already_exists )
		 * 		show_error();
		 * else
		 * 		openLoginWindow(register_success);
		 */
	}
}
