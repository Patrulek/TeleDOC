package com.pp.iwm.teledoc.windows;

import com.pp.iwm.teledoc.layouts.RegisterWindowLayout;
import com.pp.iwm.teledoc.models.RegisterWindowModel;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegisterWindow extends Window implements ChangeListener<Boolean> {
	
	// ================================================================
	// FIELDS
	// ================================================================
	
	private RegisterWindowModel window_model;
	private RegisterWindowLayout window_layout;
	
	
	// =================================================================
	// METHODS
	// =================================================================
	
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
		window_layout.clearErrorLabelText();
		
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
}
