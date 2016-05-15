package com.pp.iwm.teledoc.windows;

import com.esotericsoftware.kryonet.Connection;
import com.pp.iwm.teledoc.layouts.LoginWindowLayout;
import com.pp.iwm.teledoc.models.LoginWindowModel;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.network.User.NetworkListener;
import com.pp.iwm.teledoc.network.User.State;
import com.pp.iwm.teledoc.network.packets.LoginResponse;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginWindow extends Window implements ChangeListener<Boolean>, NetworkListener {
	
	// =====================================================
	// FIELDS
	// =====================================================

	private LoginWindowModel window_model;
	private LoginWindowLayout window_layout;
	
	// ========================================================
	// METHODS
	// ========================================================
	
	public LoginWindow() {
		super();
		User.instance().setListener(this);
	}

	private void openRegisterWindow() {
		if( User.instance().getState() != State.CONNECTING )
			openWindowAndHideCurrent(new RegisterWindow());
	}

	private void openAppWindow() {
		openWindowAndHideCurrent(new AppWindow());
	}
	
	private void loginToApplication() {
		window_layout.clearErrorLabelText();
		
		if( validateTextFields() ) {
			if( User.instance().getState() != State.CONNECTED && User.instance().getState() != State.RECONNECTED )
				tryToConnect();
			else
				tryToLogIn();
		}
	}
	
	private void tryToLogIn() {
		String email = window_layout.tf_email.getText().trim();
		String password = window_layout.pf_password.getText().trim();
		
		User.instance().logIn(email, password);
	}
	
	private void tryToConnect() {
		User.instance().connectToServer();
	}
	
	private void resetPassword() {
		if( Utils.isTextFieldEmpty(window_layout.tf_email) ) {
			window_layout.setErrorLabelTextColor(0);
			window_layout.changeErrorLabelText(Utils.MSG_INPUT_EMAIL);
		} else {
			window_layout.setErrorLabelTextColor(1);
			window_layout.changeErrorLabelText(Utils.MSG_YOUR_PASSWORD);
			// sendEmailToServer()
			// waitForMessage()
			// showPasswordInLabel()
		}
	}
	
	private boolean validateTextFields() {
		if( Utils.isTextFieldEmpty(window_layout.tf_email) || Utils.isTextFieldEmpty(window_layout.pf_password) ) {
			window_layout.setErrorLabelTextColor(0);
			window_layout.changeErrorLabelText(Utils.MSG_FILL_ALL_FIELDS);
			return false;
		}
		
		return true;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> _observable, Boolean _old_value, Boolean _new_value) {
		if( window_layout.ibtn_login.isHover() ) {
			window_layout.setErrorLabelTextColor(2);
			window_layout.changeErrorLabelText("Zaloguj");
		} else if( window_layout.ibtn_register.isHover() ) {
			window_layout.setErrorLabelTextColor(2);
			window_layout.changeErrorLabelText("Zarejestruj");
		} else if( window_layout.ibtn_reset_password.isHover() ) {
			window_layout.setErrorLabelTextColor(2);
			window_layout.changeErrorLabelText("Przypomnij has³o");
		} else
			window_layout.clearErrorLabelText();
	}
	
	@Override
	protected void initEventHandlers() {
		window_layout.rect_window_background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		window_layout.rect_window_background.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		window_layout.rect_window_background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		window_layout.ibtn_exit.setOnAction(ev -> hide());
		window_layout.pf_password.setOnAction(ev -> loginToApplication());
		
		window_layout.ibtn_register.setOnAction(ev -> openRegisterWindow());
		window_layout.ibtn_register.addListenerForHoverProperty(this);
		
		window_layout.ibtn_reset_password.setOnAction(ev -> resetPassword());
		window_layout.ibtn_reset_password.addListenerForHoverProperty(this);
		
		window_layout.ibtn_login.setOnAction(ev -> loginToApplication());
		window_layout.ibtn_login.addListenerForHoverProperty(this);
	}
	
	
	private void onWindowBackgroundMousePressed(MouseEvent _ev) {
		model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent _ev) {
		model.setDragged(false);
	}
	
	private void onWindowBackgroundMouseDragged(MouseEvent _ev) {
		Stage stage = window_layout.stage;
		Point2D mouse_pos = window_model.getMousePos();
		
		if( _ev.getSceneY() < 24 || model.isDragged()) {
			window_model.setDragged(true);
			stage.setX(stage.getX() + _ev.getScreenX() - mouse_pos.getX());
			stage.setY(stage.getY() + _ev.getScreenY() - mouse_pos.getY());
		}
		
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}

	@Override
	protected void createModel() {
		window_model = new LoginWindowModel(this);
		model = window_model;
	}
	
	@Override
	protected void createLayout() {
		window_layout = new LoginWindowLayout(this);
		layout = window_layout;
	}

	@Override
	public void onStateChanged(State _state) {
		Platform.runLater(() -> {
			if( _state == State.CONNECTION_FAILURE ) {
				window_layout.changeErrorLabelText("Server offline");
				window_layout.setErrorLabelTextColor(0);
			} else if( _state == State.CONNECTING ) {
				window_layout.changeErrorLabelText("£¹czenie z serverem");
				window_layout.setErrorLabelTextColor(2);
			} else if( _state == State.CONNECTED ) {
				tryToLogIn();
				window_layout.changeErrorLabelText("£¹czenie z baz¹ danych");
				window_layout.setErrorLabelTextColor(2);
			} else if( _state == State.DISCONNECTED ) {
				window_layout.changeErrorLabelText("Utracono po³¹czenie");
				window_layout.setErrorLabelTextColor(0);
			}
		});
	}

	@Override
	public void onReceive(Connection _connection, Object _message) {
		if( _message instanceof LoginResponse && User.instance().getState() == State.CONNECTED )
			onLoginResponseReceive((LoginResponse)_message);
	}
	
	private void onLoginResponseReceive(LoginResponse _response) {
		if( _response.getId() != -1 ) // -1 nie znaleziono usera w bazie
			loginSuccess(_response);
		else
			loginFailed();
	}

	private void loginSuccess(LoginResponse _response) {
		User.instance().setName(_response.getName());
		User.instance().setSurname(_response.getSurname());
		User.instance().setEmail(_response.getEmail());
		
		Platform.runLater(() -> {
			window_layout.changeErrorLabelText("Uda³o siê zalogowaæ");
			window_layout.setErrorLabelTextColor(1);
			openAppWindow();
		});
	}
	
	private void loginFailed() {
		Platform.runLater(() -> {
			window_layout.changeErrorLabelText("Niepoprawid³owe dane");
			window_layout.setErrorLabelTextColor(0);
		});
	}
}
