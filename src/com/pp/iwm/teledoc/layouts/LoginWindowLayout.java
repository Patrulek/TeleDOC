package com.pp.iwm.teledoc.layouts;

import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.LoginWindow;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginWindowLayout extends WindowLayout {
	
	// ===========================
	// FIELDS 
	// ===========================
	
	private LoginWindow login_window;
	
	public Rectangle rect_window_background;
	public TextField tf_email;
	public PasswordField pf_password;
	public Label lbl_error;
	public ImageView iv_logo;
	public ImageView iv_email;
	public ImageView iv_password;
	public ImageButton ibtn_exit;
	public ImageButton ibtn_register;
	public ImageButton ibtn_reset_password;
	public ImageButton ibtn_login;
	
	
	// ===========================
	// METHODS
	// ===========================
	
	public LoginWindowLayout(LoginWindow _window) {
		super(_window);
		login_window = (LoginWindow) window;
	}
	
	@Override
	public void create() {
		scene = new Scene(root, 404, 389, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		
		createBackground();
		createExitButton();
		createLogo();
		createUsernameIconAndTextField();
		createPasswordIconAndTextField();
		createErrorLabel();
		createButtons();
		
		addElementsToScene();
		
		stage.setScene(scene);
		
		tf_email.requestFocus();
	}
	
	private void createBackground() {
		rect_window_background = new Rectangle(402, 387);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setLayoutX(1.0); rect_window_background.setLayoutY(1.0);
		rect_window_background.setStroke(Color.rgb(45, 81, 90));
		rect_window_background.setStrokeWidth(2.0);
	}
	
	private void createExitButton() {
		ibtn_exit = new ImageButton(Utils.IMG_EXIT_APP_ICON, Utils.HINT_CLOSE_APP, Utils.ACT_EXIT_APP);
		ibtn_exit.setLayoutX(368.0); ibtn_exit.setLayoutY(7.0);
	}
	
	private void createLogo() {
		iv_logo = new ImageView(ImageManager.instance().getImage(Utils.IMG_LOGO));
		iv_logo.setLayoutX(67.0); iv_logo.setLayoutY(27.0);
	}
	
	private void createUsernameIconAndTextField() {
		iv_email = new ImageView(ImageManager.instance().getImage(Utils.IMG_EMAIL_ICON));
		iv_email.setLayoutX(35.0); iv_email.setLayoutY(165.0);

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
	}
	
	private void createPasswordIconAndTextField() {
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
	}
	
	private void createErrorLabel() {
		lbl_error = new Label();
		lbl_error.setLayoutX(53.0); lbl_error.setLayoutY(262.0);
		lbl_error.setPrefWidth(300.0);
		lbl_error.setFont(Utils.LBL_FONT);
		lbl_error.setText("");
		lbl_error.setStyle("-fx-text-fill: rgb(205, 100, 100);"
							+ "-fx-alignment: center;");
	}
	
	private void createButtons() {
		ibtn_register = new ImageButton(Utils.IMG_REGISTER_ICON, Utils.HINT_REGISTER, Utils.ACT_REGISTER);
		ibtn_register.setLayoutX(58.0); ibtn_register.setLayoutY(297.0);
		ibtn_register.setPrefWidth(64.0);
		
		ibtn_reset_password = new ImageButton(Utils.IMG_RESET_PASS_ICON, Utils.HINT_RESET_PASS, Utils.ACT_RESET_PASS);
		ibtn_reset_password.setLayoutX(160.0); ibtn_reset_password.setLayoutY(297.0);
		ibtn_reset_password.setPrefWidth(64.0);
		
		ibtn_login = new ImageButton(Utils.IMG_LOGIN_ICON, Utils.HINT_LOGIN, Utils.ACT_LOGIN);
		ibtn_login.setLayoutX(262.0); ibtn_login.setLayoutY(297.0);
		ibtn_login.setPrefWidth(64.0);
	}
	
	private void addElementsToScene() {
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
	}
	
	public void clearErrorLabelText() {
		lbl_error.setText("");
	}
	
	public void changeErrorLabelText(String _text) {
		lbl_error.setText(_text);
	}
	
	// TODO hardcoded
	public void setErrorLabelTextColor(int _color_type) {
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
}
