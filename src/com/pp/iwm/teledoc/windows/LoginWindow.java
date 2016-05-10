package com.pp.iwm.teledoc.windows;

import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.Utils;
import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

public class LoginWindow extends Window implements ChangeListener<Boolean> {
	
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
		mouse_pos = new Point2D(_ev.getScreenX(), _ev.getScreenY());
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent _ev) {
		is_dragged = false;
	}
	
	private void onWindowBackgroundMouseDragged(MouseEvent _ev) {
		if( _ev.getSceneY() < 24 || is_dragged) {
			is_dragged = true;
			stage.setX(stage.getX() + _ev.getScreenX() - mouse_pos.getX());
			stage.setY(stage.getY() + _ev.getScreenY() - mouse_pos.getY());
		}
		
		mouse_pos = new Point2D(_ev.getScreenX(), _ev.getScreenY());
	}
	
	// TODO hardcoded
	private void setLblErrColor(int _color_type) {
		switch( _color_type ) {
			case 0:			// czerwony
				lbl_error.setStyle("-fx-text-fill: rgb(205, 100, 100); -fx-alignment: center");
				break;
			case 1:			// zielony
				lbl_error.setStyle("-fx-text-fill: rgb(100, 205, 100); -fx-alignment: center");
				break;
			case 2:			// zolty
				lbl_error.setStyle("-fx-text-fill: rgb(120, 120, 40); -fx-alignment: center;");
				break;
		}
	}

	@Override
	protected void createStage() {
		scene = new Scene(root, 404, 389, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		rect_window_background = new Rectangle(402, 387);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setLayoutX(1.0); rect_window_background.setLayoutY(1.0);
		rect_window_background.setStroke(Color.rgb(45, 81, 90));
		rect_window_background.setStrokeWidth(2.0);
		rect_window_background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		rect_window_background.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		rect_window_background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		// cross btn
		ibtn_exit = new ImageButton(Utils.IMG_EXIT_APP_ICON, Utils.HINT_CLOSE_APP, Utils.ACT_EXIT_APP);
		ibtn_exit.setLayoutX(368.0); ibtn_exit.setLayoutY(7.0);
		ibtn_exit.setOnAction(ev -> hide());
		
		// teledoc logo
		iv_logo = new ImageView(ImageManager.instance().getImage(Utils.IMG_LOGO));
		iv_logo.setLayoutX(67.0); iv_logo.setLayoutY(27.0);
		
		// username icon
		iv_email = new ImageView(ImageManager.instance().getImage(Utils.IMG_EMAIL_ICON));
		iv_email.setLayoutX(35.0); iv_email.setLayoutY(165.0);
		
		// username text field
		tf_email = new TextField();
		tf_email.setLayoutX(69.0); tf_email.setLayoutY(162.0);
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
		iv_password.setLayoutX(35.0); iv_password.setLayoutY(215.0);
		
		// password field
		pf_password = new PasswordField();
		pf_password.setLayoutX(69.0); pf_password.setLayoutY(212.0);
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
		lbl_error.setLayoutX(57.0); lbl_error.setLayoutY(262.0);
		lbl_error.setPrefWidth(300.0);
		lbl_error.setFont(Utils.LBL_FONT);
		lbl_error.setText("");
		lbl_error.setStyle("-fx-text-fill: rgb(205, 100, 100);"
							+ "-fx-alignment: center;");
		
		// image buttons
		ibtn_register = new ImageButton(Utils.IMG_REGISTER_ICON, Utils.HINT_REGISTER, Utils.ACT_REGISTER);
		ibtn_register.setLayoutX(58.0); ibtn_register.setLayoutY(297.0);
		ibtn_register.setPrefWidth(64.0);
		ibtn_register.setOnAction(ev -> openRegisterWindow());
		ibtn_register.addListenerForHoverProperty(this);
		
		ibtn_reset_password = new ImageButton(Utils.IMG_RESET_PASS_ICON, Utils.HINT_RESET_PASS, Utils.ACT_RESET_PASS);
		ibtn_reset_password.setLayoutX(160.0); ibtn_reset_password.setLayoutY(297.0);
		ibtn_reset_password.setPrefWidth(64.0);
		ibtn_reset_password.setOnAction(ev -> resetPassword());
		ibtn_reset_password.addListenerForHoverProperty(this);
		
		ibtn_login = new ImageButton(Utils.IMG_LOGIN_ICON, Utils.HINT_LOGIN, Utils.ACT_LOGIN);
		ibtn_login.setLayoutX(262.0); ibtn_login.setLayoutY(297.0);
		ibtn_login.setPrefWidth(64.0);
		ibtn_login.setOnAction(ev -> loginToApplication());
		ibtn_login.addListenerForHoverProperty(this);

		// add elements
		root.getChildren().addAll(rect_window_background,
									ibtn_exit,
									iv_logo,
									iv_email,
									tf_email,
									iv_password,
									pf_password,
									lbl_error,
									ibtn_register,
									ibtn_reset_password,
									ibtn_login);
		
		tf_email.requestFocus();
		
		stage.setScene(scene);
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> _observable, Boolean _old_value, Boolean _new_value) {
		if( ibtn_login.isHover() ) {
			setLblErrColor(2);
			lbl_error.setText("Zaloguj");
		} else if( ibtn_register.isHover() ) {
			setLblErrColor(2);
			lbl_error.setText("Zarejestruj");
		} else if( ibtn_reset_password.isHover() ) {
			setLblErrColor(2);
			lbl_error.setText("Przypomnij has³o");
		} else
			lbl_error.setText("");
	}
}
