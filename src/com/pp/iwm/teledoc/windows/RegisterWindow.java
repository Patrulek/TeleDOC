package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.Utils;
import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterWindow extends Window {
	
	// ================================================================
	// FIELDS
	// ================================================================
	
	
	// UI ELEMENTS
	private Rectangle rect_window_background;
	private ImageButton ibtn_exit;
	private ImageView iv_logo;
	private ImageView iv_name;
	private ImageView iv_surname;
	private ImageView iv_email;
	private ImageView iv_password;
	private TextField tf_name;
	private TextField tf_surname;
	private TextField tf_email;
	private PasswordField pf_password;
	private Label lbl_error;
	private ImageButton ibtn_register;
	private ImageButton ibtn_back;
	
	
	// =================================================================
	// METHODS
	// =================================================================
	
	private void openLoginWindow(boolean register_success) {
		openWindow(new LoginWindow(), true);
	}
	
	private boolean validateTextFields() {
		if( tf_email.getText().equals("") || tf_name.equals("") || tf_surname.equals("") || pf_password.getText().equals("")) {
			lbl_error.setText(Utils.MSG_FILL_ALL_FIELDS);
			return false;
		}
		
		return true;
	}
	
	private boolean validateEmail() {
		String email = tf_email.getText();
		
		if( !email.contains("@") || !email.contains(".") ) {
			lbl_error.setText(Utils.MSG_INCORRECT_MAIL);
			return false;
		}
		
		return true;
	}
	
	private boolean validatePassword() {
		if( pf_password.getText().length() < 6 ) {
			lbl_error.setText(Utils.MSG_PASS_TOO_SHORT);
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
	
	private void onWindowBackgroundMousePressed(MouseEvent ev) {
		mouse_pos = new Point((int)ev.getScreenX(), (int)ev.getScreenY());
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent ev) {
		is_dragged = false;
	}
	
	private void onWindowBackgroundMoseDragged(MouseEvent ev) {
		if( (!is_dragged && ev.getSceneY() < 24) || is_dragged ) {
			is_dragged = true;
			stage.setX(stage.getX() + ev.getScreenX() - mouse_pos.x);
			stage.setY(stage.getY() + ev.getScreenY() - mouse_pos.y);
			mouse_pos = new Point((int)ev.getScreenX(), (int)ev.getScreenY());
		}
	}

	@Override
	protected void createStage() {
		Scene scene = new Scene(root, 400, 600, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		rect_window_background = new Rectangle(400, 600);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setArcHeight(10.0);
		rect_window_background.setArcWidth(10.0);
		rect_window_background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		rect_window_background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		rect_window_background.setOnMouseDragged(ev -> onWindowBackgroundMoseDragged(ev));
		
		// cross btn
		ibtn_exit = new ImageButton(Utils.IMG_EXIT_APP_ICON, Utils.HINT_CLOSE_APP, Utils.ACT_EXIT_APP);
		ibtn_exit.setLayoutX(365.0); ibtn_exit.setLayoutY(5.0);
		ibtn_exit.setOnAction(ev -> hide());
		
		// teledoc logo
		iv_logo = new ImageView(ImageManager.instance().getImage(Utils.IMG_LOGO));
		iv_logo.setLayoutX(50.0); iv_logo.setLayoutY(50.0);
		
		// name icon
		iv_name = new ImageView(ImageManager.instance().getImage(Utils.IMG_NAME_ICON));
		iv_name.setLayoutX(20.0); iv_name.setLayoutY(167.0);
		
		// name text field
		tf_name = new TextField();
		tf_name.setLayoutX(55.0); tf_name.setLayoutY(160.0);
		tf_name.setPrefWidth(300.0);
		tf_name.setPromptText("Imiê");
		tf_name.setFont(Utils.TF_FONT);
		tf_name.setStyle("-fx-text-fill: rgb(222, 135, 205); "
						+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
						+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
						+ "-fx-highlight-fill: rgb(15, 27, 30); "
						+ "-fx-background-color: rgb(30, 54, 60); ");
		
		// surname icon
		iv_surname = new ImageView(ImageManager.instance().getImage(Utils.IMG_NAME_ICON)); // TODO:  surname icon
		iv_surname.setLayoutX(20.0); iv_surname.setLayoutY(237.0);
				
		// surname text field
		tf_surname = new TextField();
		tf_surname.setLayoutX(55.0); tf_surname.setLayoutY(230.0);
		tf_surname.setPrefWidth(300.0);
		tf_surname.setPromptText("Nazwisko");
		tf_surname.setFont(Utils.TF_FONT);
		tf_surname.setStyle("-fx-text-fill: rgb(222, 135, 205); "
							+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-fill: rgb(15, 27, 30); "
							+ "-fx-background-color: rgb(30, 54, 60); ");
		

		// email icon
		iv_email = new ImageView(ImageManager.instance().getImage(Utils.IMG_NAME_ICON)); // TODO:  email icon
		iv_email.setLayoutX(20.0); iv_email.setLayoutY(307.0);
				
		// email text field
		tf_email = new TextField();
		tf_email.setLayoutX(55.0); tf_email.setLayoutY(300.0);
		tf_email.setPrefWidth(300.0);
		tf_email.setPromptText("Email");
		tf_email.setFont(Utils.TF_FONT);
		tf_email.setStyle("-fx-text-fill: rgb(222, 135, 205); "
						+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
						+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
						+ "-fx-highlight-fill: rgb(15, 27, 30); "
						+ "-fx-background-color: rgb(30, 54, 60); ");	
		
		// password icon
		iv_password = new ImageView(ImageManager.instance().getImage(Utils.IMG_PASSWORD_ICON));
		iv_password.setLayoutX(24.0); iv_password.setLayoutY(377.0);
		
		// password field
		pf_password = new PasswordField();
		pf_password.setLayoutX(55.0); pf_password.setLayoutY(370.0);
		pf_password.setPrefWidth(300.0);
		pf_password.setPromptText("Has³o");
		pf_password.setFont(Utils.TF_FONT);
		pf_password.setStyle("-fx-text-fill: rgb(222, 135, 205); "
							+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-fill: rgb(15, 27, 30); "
							+ "-fx-background-color: rgb(30, 54, 60); ");
		
		
		// password error label
		lbl_error = new Label();
		lbl_error.setLayoutX(55.0); lbl_error.setLayoutY(435.0);
		lbl_error.setPrefWidth(300.0);
		lbl_error.setFont(Utils.LBL_FONT);
		lbl_error.setText("");
		lbl_error.setStyle("-fx-text-fill: rgb(205, 100, 100);"
							+ "-fx-alignment: center;");
		
		
		// image buttons
		ibtn_register = new ImageButton(Utils.IMG_REGISTER_ICON, Utils.HINT_REGISTER, Utils.ACT_REGISTER);
		ibtn_register.setLayoutX(220.0); ibtn_register.setLayoutY(470.0);
		ibtn_register.setPrefWidth(64.0);
		ibtn_register.setOnAction(ev -> registerAccount());
		
		ibtn_back = new ImageButton(Utils.IMG_BACK_ICON, Utils.HINT_BACK_TO_LOGIN, Utils.ACT_BACK_TO_LOGIN);
		ibtn_back.setLayoutX(90.0); ibtn_back.setLayoutY(470.0);
		ibtn_back.setPrefWidth(64.0);
		ibtn_back.setOnAction(ev -> openLoginWindow(false));
		
		
		// add elements
		root.getChildren().add(rect_window_background);
		root.getChildren().add(ibtn_exit);
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
		root.getChildren().add(ibtn_register);
		root.getChildren().add(ibtn_back);
		stage.setScene(scene);
	}
}
