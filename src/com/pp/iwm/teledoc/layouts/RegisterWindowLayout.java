package com.pp.iwm.teledoc.layouts;

import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.RegisterWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

public class RegisterWindowLayout extends WindowLayout {
	
	// ===================================
	// FIELDS 
	// ===================================
	
	private RegisterWindow register_window;
	
	// UI ELEMENTS
	public Rectangle rect_window_background;
	public ImageButton ibtn_exit;
	public ImageView iv_logo;
	public ImageView iv_name;
	public ImageView iv_surname;
	public ImageView iv_email;
	public ImageView iv_password;
	public TextField tf_name;
	public TextField tf_surname;
	public TextField tf_email;
	public PasswordField pf_password;
	public Label lbl_error;
	public ImageButton ibtn_register;
	public ImageButton ibtn_back;
	
	// ===================================
	// METHODS 
	// ===================================
	
	public RegisterWindowLayout(Window _window) {
		super(_window);
	}
	
	private void createBackground() {
		rect_window_background = new Rectangle(402, 487);
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
	
	private void createUserNameIconAndTextField() {
		iv_name = new ImageView(ImageManager.instance().getImage(Utils.IMG_NAME_ICON));
		iv_name.setLayoutX(35.0); iv_name.setLayoutY(165.0);
		
		tf_name = new TextField();
		tf_name.setLayoutX(69.0); tf_name.setLayoutY(162.0);
		tf_name.setPrefWidth(300.0);
		tf_name.setPromptText("Imiê");
		tf_name.setFont(Utils.TF_FONT);
		tf_name.setStyle("-fx-text-fill: rgb(222, 135, 205); "
						+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
						+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
						+ "-fx-highlight-fill: rgb(15, 27, 30); "
						+ "-fx-background-color: rgb(30, 54, 60); ");
	}
	
	private void createUserSurnameIconAndTextField() {
		iv_surname = new ImageView(ImageManager.instance().getImage(Utils.IMG_SURNAME_ICON));
		iv_surname.setLayoutX(35.0); iv_surname.setLayoutY(215.0);
				
		tf_surname = new TextField();
		tf_surname.setLayoutX(69.0); tf_surname.setLayoutY(212.0);
		tf_surname.setPrefWidth(300.0);
		tf_surname.setPromptText("Nazwisko");
		tf_surname.setFont(Utils.TF_FONT);
		tf_surname.setStyle("-fx-text-fill: rgb(222, 135, 205); "
							+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-fill: rgb(15, 27, 30); "
							+ "-fx-background-color: rgb(30, 54, 60); ");
	}
	
	private void createUserMailIconAndTextField() {
		iv_email = new ImageView(ImageManager.instance().getImage(Utils.IMG_EMAIL_ICON));
		iv_email.setLayoutX(35.0); iv_email.setLayoutY(265.0);
				
		tf_email = new TextField();
		tf_email.setLayoutX(69.0); tf_email.setLayoutY(262.0);
		tf_email.setPrefWidth(300.0);
		tf_email.setPromptText("Email");
		tf_email.setFont(Utils.TF_FONT);
		tf_email.setStyle("-fx-text-fill: rgb(222, 135, 205); "
						+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
						+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
						+ "-fx-highlight-fill: rgb(15, 27, 30); "
						+ "-fx-background-color: rgb(30, 54, 60); ");	
	}
	
	private void createUserPasswordIconAndTextField() {
		iv_password = new ImageView(ImageManager.instance().getImage(Utils.IMG_PASSWORD_ICON));
		iv_password.setLayoutX(35.0); iv_password.setLayoutY(315.0);
		
		pf_password = new PasswordField();
		pf_password.setLayoutX(69.0); pf_password.setLayoutY(312.0);
		pf_password.setPrefWidth(300.0);
		pf_password.setPromptText("Has³o");
		pf_password.setFont(Utils.TF_FONT);
		pf_password.setStyle("-fx-text-fill: rgb(222, 135, 205); "
							+ "-fx-prompt-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-text-fill: rgb(140, 90, 135); "
							+ "-fx-highlight-fill: rgb(15, 27, 30); "
							+ "-fx-background-color: rgb(30, 54, 60); ");
	}
	
	private void createErrorLabel() {
		lbl_error = new Label();
		lbl_error.setLayoutX(40.0); lbl_error.setLayoutY(362.0);
		lbl_error.setPrefWidth(326.0);
		lbl_error.setFont(Utils.LBL_FONT);
		lbl_error.setText("");
		lbl_error.setStyle("-fx-text-fill: rgb(205, 100, 100);"
							+ "-fx-alignment: center;");
	}
	
	private void createButtons() {
		ibtn_back = new ImageButton(Utils.IMG_BACK_ICON, Utils.HINT_BACK_TO_LOGIN, Utils.ACT_BACK_TO_LOGIN);
		ibtn_back.setLayoutX(99.0); ibtn_back.setLayoutY(397.0);
		ibtn_back.setPrefWidth(64.0);
		
		ibtn_register = new ImageButton(Utils.IMG_REGISTER_ICON, Utils.HINT_REGISTER, Utils.ACT_REGISTER);
		ibtn_register.setLayoutX(221.0); ibtn_register.setLayoutY(397.0);
		ibtn_register.setPrefWidth(64.0);
	}
	
	private void addElementsToScene() {
		root.getChildren().addAll(rect_window_background,
								ibtn_exit,
								iv_logo,
								iv_name,
								tf_name,
								iv_surname,
								tf_surname,
								iv_email,
								tf_email,
								iv_password,
								pf_password,
								lbl_error,
								ibtn_register,
								ibtn_back);
	}
	
	@Override
	public void create() {
		Scene scene = new Scene(root, 404, 489, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		
		createBackground();
		createExitButton();
		createLogo();
		createUserNameIconAndTextField();
		createUserSurnameIconAndTextField();
		createUserMailIconAndTextField();
		createUserPasswordIconAndTextField();
		createErrorLabel();
		createButtons();
		
		addElementsToScene();
		stage.setScene(scene);
		
		tf_name.requestFocus();
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
