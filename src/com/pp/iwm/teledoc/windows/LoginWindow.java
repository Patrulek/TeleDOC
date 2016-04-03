package com.pp.iwm.teledoc.windows;

import java.awt.Point;

import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.Utils;
import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.scene.Group;
import javafx.scene.ImageCursor;
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

public class LoginWindow extends Window {
	
	// =====================================================
	// FIELDS
	// =====================================================
	
	
	// UI ELEMENTS
	private Rectangle rect_window_background;
	private TextField tf_email;
	private PasswordField pf_password;
	private Label lbl_error;
	private ImageView iv_logo;
	private ImageView iv_email;
	private ImageView iv_password;
	private ImageButton ibtn_exit;
	private ImageButton ibtn_register;
	private ImageButton ibtn_reset_password;
	private ImageButton ibtn_login;
	
	// ========================================================
	// METHODS
	// ========================================================
	
	public LoginWindow() {
		super();
	}

	private void openRegisterWindow() {
		openWindow(new RegisterWindow(), true);
	}

	private void openAppWindow() {
		openWindow(new AppWindow(), true);
	}
	
	private void loginToApplication() {
		lbl_error.setText("");
		
		if( validateTextFields() )
			sendDataToServer();
	}
	
	private void sendDataToServer() {
		/*
		 * if( !canConnect() )
		 * 		show_error();
		 * else
		 * 		send_data();
		 * 
		 */
		
		// hack alert
		if( tf_email.getText().equals("dev") && pf_password.getText().equals("dev") )
			openAppWindow();
	}
	
	private void readDataFromServer() {
		/*
		 * if( incorrect email/password )
		 * 		show_error();
		 * else
		 * 		openAppWindow(user_email);
		 */
	}
	
	private void resetPassword() {
		if( tf_email.getText().equals("") ) {
			setLblErrColor(0);
			lbl_error.setText(Utils.MSG_INPUT_EMAIL);
		}
		else {
			setLblErrColor(1);
			lbl_error.setText(Utils.MSG_YOUR_PASSWORD);
			// sendEmailToServer()
			// waitForMessage()
			// showPasswordInLabel()
		}
	}
	
	private boolean validateTextFields() {
		if( tf_email.getText().equals("") || pf_password.getText().equals("") ) {
			setLblErrColor(0);
			lbl_error.setText(Utils.MSG_FILL_ALL_FIELDS);
			return false;
		}
		
		return true;
	}
	
	private boolean canConnect() {
		return false;
	}
	
	private void onWindowBackgroundMousePressed(MouseEvent _ev) {
		mouse_pos = new Point((int)_ev.getScreenX(), (int)_ev.getScreenY());
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent _ev) {
		is_dragged = false;
	}
	
	private void onWindowBackgroundMoseDragged(MouseEvent _ev) {
		if( (!is_dragged && _ev.getSceneY() < 24) || is_dragged ) {
			is_dragged = true;
			stage.setX(stage.getX() + _ev.getScreenX() - mouse_pos.x);
			stage.setY(stage.getY() + _ev.getScreenY() - mouse_pos.y);
			mouse_pos = new Point((int)_ev.getScreenX(), (int)_ev.getScreenY());
		}
	}
	
	private void setLblErrColor(int _color_type) {
		switch( _color_type ) {
			case 0:			// czerwony
				lbl_error.setStyle("-fx-text-fill: rgb(205, 100, 100); -fx-alignment: center");
				break;
			case 1:			// zielony
				lbl_error.setStyle("-fx-text-fill: rgb(100, 205, 100); -fx-alignment: center");
				break;
		}
	}

	@Override
	protected void createStage() {
		scene = new Scene(root, 400, 385, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		rect_window_background = new Rectangle(400, 385);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		rect_window_background.setOnMouseDragged(ev -> onWindowBackgroundMoseDragged(ev));
		rect_window_background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		// cross btn
		ibtn_exit = new ImageButton(Utils.IMG_EXIT_APP_ICON, Utils.HINT_CLOSE_APP, Utils.ACT_EXIT_APP);
		ibtn_exit.setLayoutX(366.0); ibtn_exit.setLayoutY(5.0);
		ibtn_exit.setOnAction(ev -> hide());
		
		// teledoc logo
		iv_logo = new ImageView(ImageManager.instance().getImage(Utils.IMG_LOGO));
		iv_logo.setLayoutX(65.0); iv_logo.setLayoutY(25.0);
		
		// username icon
		iv_email = new ImageView(ImageManager.instance().getImage(Utils.IMG_EMAIL_ICON));
		iv_email.setLayoutX(33.0); iv_email.setLayoutY(163.0);
		
		// username text field
		tf_email = new TextField();
		tf_email.setLayoutX(67.0); tf_email.setLayoutY(160.0);
		tf_email.setPrefWidth(300.0);
		tf_email.setPromptText(Utils.PROMPT_EMAIL);
		tf_email.setFont(Utils.TF_FONT);
		tf_email.setStyle("-fx-text-fill: rgb(222, 135, 205); "
							+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-fill: rgb(15, 27, 30); "
							+ "-fx-background-color: rgb(30, 54, 60); ");
		
		// password icon
		iv_password = new ImageView(ImageManager.instance().getImage(Utils.IMG_PASSWORD_ICON));
		iv_password.setLayoutX(33.0); iv_password.setLayoutY(213.0);
		
		// password field
		pf_password = new PasswordField();
		pf_password.setLayoutX(67.0); pf_password.setLayoutY(210.0);
		pf_password.setPrefWidth(300.0);
		pf_password.setPromptText(Utils.PROMPT_PASS);
		pf_password.setFont(Utils.TF_FONT);
		pf_password.setStyle("-fx-text-fill: rgb(222, 135, 205); "
							+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-fill: rgb(15, 27, 30); "
							+ "-fx-background-color: rgb(30, 54, 60); ");
		pf_password.setOnAction(ev -> loginToApplication());
		
		// error label
		lbl_error = new Label();
		lbl_error.setLayoutX(55.0); lbl_error.setLayoutY(260.0);
		lbl_error.setPrefWidth(300.0);
		lbl_error.setFont(Utils.LBL_FONT);
		lbl_error.setText("");
		lbl_error.setStyle("-fx-text-fill: rgb(205, 100, 100);"
							+ "-fx-alignment: center;");
		
		// image buttons
		ibtn_register = new ImageButton(Utils.IMG_REGISTER_ICON, Utils.HINT_REGISTER, Utils.ACT_REGISTER);
		ibtn_register.setLayoutX(56.0); ibtn_register.setLayoutY(295.0);
		ibtn_register.setPrefWidth(64.0);
		ibtn_register.setOnAction(ev -> openRegisterWindow());
		
		ibtn_reset_password = new ImageButton(Utils.IMG_RESET_PASS_ICON, Utils.HINT_RESET_PASS, Utils.ACT_RESET_PASS);
		ibtn_reset_password.setLayoutX(158.0); ibtn_reset_password.setLayoutY(295.0);
		ibtn_reset_password.setPrefWidth(64.0);
		ibtn_reset_password.setOnAction(ev -> resetPassword());
		
		ibtn_login = new ImageButton(Utils.IMG_LOGIN_ICON, Utils.HINT_LOGIN, Utils.ACT_LOGIN);
		ibtn_login.setLayoutX(260.0); ibtn_login.setLayoutY(295.0);
		ibtn_login.setPrefWidth(64.0);
		ibtn_login.setOnAction(ev -> loginToApplication());

		// add elements
		root.getChildren().add(rect_window_background);
		root.getChildren().add(ibtn_exit);
		root.getChildren().add(iv_logo);
		root.getChildren().add(iv_email);
		root.getChildren().add(tf_email);
		root.getChildren().add(iv_password);
		root.getChildren().add(pf_password);
		root.getChildren().add(lbl_error);
		root.getChildren().add(ibtn_register);
		root.getChildren().add(ibtn_reset_password);
		root.getChildren().add(ibtn_login);
		
		tf_email.requestFocus();
		
		stage.setScene(scene);
	}
}
