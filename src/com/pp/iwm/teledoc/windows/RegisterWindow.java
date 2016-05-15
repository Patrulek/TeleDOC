package com.pp.iwm.teledoc.windows;

import com.esotericsoftware.kryonet.Connection;
import com.pp.iwm.teledoc.layouts.RegisterWindowLayout;
import com.pp.iwm.teledoc.models.RegisterWindowModel;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.network.User.NetworkListener;
import com.pp.iwm.teledoc.network.User.State;
import com.pp.iwm.teledoc.network.packets.LoginResponse;
import com.pp.iwm.teledoc.network.packets.RegisterResponse;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegisterWindow extends Window implements ChangeListener<Boolean>, NetworkListener {
	
	// ================================================================
	// FIELDS
	// ================================================================
	
	private RegisterWindowModel window_model;
	private RegisterWindowLayout window_layout;
	
	
	// =================================================================
	// METHODS
	// =================================================================
	
	public RegisterWindow() {
		super();
		User.instance().setListener(this);
	}
	
	private void openLoginWindow(boolean _register_success) {
		openWindowAndHideCurrent(new LoginWindow());
	}
	
	private boolean validateTextFields() {
		if( Utils.isTextFieldEmpty(window_layout.tf_email) || Utils.isTextFieldEmpty(window_layout.tf_name) 
			|| Utils.isTextFieldEmpty(window_layout.tf_surname) || Utils.isTextFieldEmpty(window_layout.pf_password) ){
			window_layout.setErrorLabelTextColor(0);
			window_layout.changeErrorLabelText(Utils.MSG_FILL_ALL_FIELDS);
			return false;
		}
		
		return true;
	}
	
	private boolean validateEmail() {
		if( !Utils.isTextFieldAnEmail(window_layout.tf_email) ) {
			window_layout.setErrorLabelTextColor(0);
			window_layout.changeErrorLabelText(Utils.MSG_INCORRECT_MAIL);
			return false;
		}
		
		return true;
	}
	
	private boolean validatePassword() {
		if( !Utils.isTextFieldLongerThan(window_layout.pf_password, 6) ) {
			window_layout.setErrorLabelTextColor(0);
			window_layout.changeErrorLabelText(Utils.MSG_PASS_TOO_SHORT);
			return false;
		}
		
		return true;
	}
	
	private void registerAccount() {
		window_layout.clearErrorLabelText();
		
		if( validateTextFields() && validateEmail() && validatePassword() ) {
			if( User.instance().getState() != State.CONNECTED && User.instance().getState() != State.RECONNECTED )
				tryToConnect();
			else
				tryToRegister();	// TODO hack
		} else if( Utils.isTextFieldEqual(window_layout.tf_email, "dev") && Utils.isTextFieldEqual(window_layout.pf_password, "dev")
					&& Utils.isTextFieldEqual(window_layout.tf_name, "dev") && Utils.isTextFieldEqual(window_layout.tf_surname, "dev") )
			registerSuccess();
	}
	
	private void tryToConnect() {
		User.instance().connectToServer();
	}
	
	private void tryToRegister() {
		String name = window_layout.tf_name.getText().trim();
		String surname = window_layout.tf_surname.getText().trim();
		String email = window_layout.tf_email.getText().trim();
		String password = window_layout.pf_password.getText().trim();
		
		User.instance().register(name, surname, email, password);
	}
	
	private void onWindowBackgroundMousePressed(MouseEvent _ev) {
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent _ev) {
		window_model.setDragged(false);
	}
	
	private void onWindowBackgroundMoseDragged(MouseEvent _ev) {
		Stage stage = window_layout.stage;
		Point2D mouse_pos = window_model.getMousePos();
		
		if( _ev.getSceneY() < 24 || window_model.isDragged() ) {
			window_model.setDragged(true);
			stage.setX(stage.getX() + _ev.getScreenX() - mouse_pos.getX());
			stage.setY(stage.getY() + _ev.getScreenY() - mouse_pos.getY());
		}
		
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}

	@Override
	protected void createLayout() {
		window_layout = new RegisterWindowLayout(this);
		layout = window_layout;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> _observable, Boolean _old_value, Boolean _new_value) {
		if( window_layout.ibtn_back.isHover() ) {
			window_layout.setErrorLabelTextColor(2);
			window_layout.changeErrorLabelText("Powrót");
		} else if( window_layout.ibtn_register.isHover() ) {
			window_layout.setErrorLabelTextColor(2);
			window_layout.changeErrorLabelText("Zarejestruj");
		} else
			window_layout.clearErrorLabelText();
	}

	@Override
	protected void createModel() {
		window_model = new RegisterWindowModel(this);
		model = window_model;
	}

	@Override
	protected void initEventHandlers() {
		window_layout.rect_window_background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		window_layout.rect_window_background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		window_layout.rect_window_background.setOnMouseDragged(ev -> onWindowBackgroundMoseDragged(ev));

		window_layout.pf_password.setOnAction(ev -> registerAccount());
		
		window_layout.ibtn_exit.setOnAction(ev -> hide());
		
		window_layout.ibtn_back.setOnAction(ev -> openLoginWindow(false));
		window_layout.ibtn_back.addListenerForHoverProperty(this);
		
		window_layout.ibtn_register.setOnAction(ev -> registerAccount());
		window_layout.ibtn_register.addListenerForHoverProperty(this);
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
				tryToRegister();
				window_layout.changeErrorLabelText("Trwa rejestracja");
				window_layout.setErrorLabelTextColor(2);
			} else if( _state == State.DISCONNECTED ) {
				window_layout.changeErrorLabelText("Utracono po³¹czenie");
				window_layout.setErrorLabelTextColor(0);
			}
		});
	}
	
	private void registerSuccess() {
		Platform.runLater(() -> {
			window_layout.setErrorLabelTextColor(1);
			window_layout.changeErrorLabelText("Rejestracja zakoñczona powodzeniem");
		});
	}
	
	private void registerFailed() {
		Platform.runLater(() -> {
			window_layout.setErrorLabelTextColor(0);
			window_layout.changeErrorLabelText("Istnieje ju¿ u¿ytkownik o takim adresie email");
		});
	}

	@Override
	public void onReceive(Connection _connection, Object _message) {
		if( _message instanceof RegisterResponse )
			onRegisterResponseReceive((RegisterResponse)_message);
	}
	
	private void onRegisterResponseReceive(RegisterResponse _response) {
		if( _response.getAnswer() )  // zarejestrowano pomyœlnie
			registerSuccess();
		else
			registerFailed();
	}
}
