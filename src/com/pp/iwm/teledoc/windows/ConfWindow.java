package com.pp.iwm.teledoc.windows;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.Annotation.State;
import com.pp.iwm.teledoc.gui.ActionPaneConf.PaneState;
import com.pp.iwm.teledoc.gui.AnnotationPane;
import com.pp.iwm.teledoc.gui.ChatPane;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.MemberPane;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.models.ConfWindowModel;
import com.pp.iwm.teledoc.models.ConfWindowModel.UserContext;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.windows.assistants.ConfWindowDrawableAssistant;
import com.pp.iwm.teledoc.windows.assistants.ConfWindowInputAssistant;
import com.pp.iwm.teledoc.windows.assistants.ConfWindowNetworkAssistant;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class ConfWindow extends Window implements ChangeListener<Number> {

	
	// =========================================
	// FIELDS
	// =========================================
	
	private ConfWindowModel window_model;
	private ConfWindowLayout window_layout;
	private ConfWindowDrawableAssistant drawable_assistant;
	private ConfWindowNetworkAssistant network_assistant;
	private ConfWindowInputAssistant input_assistant;
	
	// =========================================
	// METHODS
	// =========================================
	
	public ConfWindow() {
		super();
		drawable_assistant = new ConfWindowDrawableAssistant(this);
		window_layout.drawable_pane.setListener(drawable_assistant);
		
		network_assistant = new ConfWindowNetworkAssistant(this);
		User.instance().setListener(network_assistant);
		
		input_assistant = new ConfWindowInputAssistant(this);
		
		ImageManager.instance().loadImageForUser(2099, "/assets/big_image.jpg");
		User.instance().setCurrentImage(2099);
	}
	
	public void mapMousePosToImageMousePos() {
		DrawablePane drawable_pane = window_layout.drawable_pane;
		ScrollPane scroll_pane = window_layout.scroll_pane;
		Point2D mouse_pos = window_model.getMousePos();
		
		double canvas_w = drawable_pane.getWidth();
		double canvas_h = drawable_pane.getHeight();
		double x_pos = mouse_pos.getX() + scroll_pane.getHvalue() * (canvas_w - scroll_pane.getViewportBounds().getWidth()) - 4; // 2 - ramka okna która ma 2 pigzy
		double y_pos = mouse_pos.getY() + scroll_pane.getVvalue() * (canvas_h - scroll_pane.getViewportBounds().getHeight()) - 3; //
		x_pos /= drawable_pane.getScale();
		y_pos /= drawable_pane.getScale();
		
		window_model.image_mouse_pos = new Point2D(x_pos, y_pos);
	}

	private void enableMicrophone() {
		
	}
	
	private void enableCamera() {
		
	}
	
	public void changeUserContext(UserContext _context) {
		window_model.user_context = _context;
	}
	
	public void onAnnotationSubmit() {
		String text = window_layout.annotation_pane.getTextArea().getText().trim();
		Annotation tmp_ann = window_model.temp_annotation;
		
		if( !text.equals("") )
			tmp_ann.setText(text);
		
		window_layout.annotation_pane.hide();
		tmp_ann.changeState(State.DRAWN);
		tmp_ann = null;
		window_model.temp1 = window_model.temp2 = null;
		changeUserContext(UserContext.DOING_NOTHING);
		window_layout.annotation_pane.getTextArea().setText("");
	}
	
	public void onAnnotationCancel() {
		Annotation tmp_ann = window_model.temp_annotation;
		
		window_layout.annotation_pane.hide();
		window_model.temp1 = window_model.temp2 = null;
		 
		if( tmp_ann != null ) {
			window_layout.drawable_pane.removeAnnotation(tmp_ann);
			window_model.temp_annotation = null;
		}
	}
	
	public void showAnnotationPane(ScrollPane _annotation_text_pane) {
		window_layout.root.getChildren().add(_annotation_text_pane);
	}
	
	public void hideAnnotationPane(ScrollPane _annotation_text_pane) {
		window_layout.root.getChildren().remove(_annotation_text_pane);
	}
	
	public void onChatBtnAction() {
		ChatPane chat_pane = window_layout.chat_pane;
		
		if( chat_pane.getOpacity() != 0.0 ) {
			chat_pane.hide();
			window_model.is_chat_hiding = true;
		} else {
			chat_pane.show();
			window_model.is_chat_hiding = false;
		}
	}
	
	public void onMembersBtnAction() {
		MemberPane member_pane = window_layout.member_pane;
		
		if( member_pane.getOpacity() != 0.0 )
			member_pane.hide();
		else
			member_pane.show();
	}
	
	public void onDrawLine() {
		changeUserContext(UserContext.DRAWING_LINE);
		window_layout.action_pane.changePaneStateAndRefresh(PaneState.DRAW_LINE);
	}
	
	public void onDrawMultipleLines() {
		changeUserContext(UserContext.DRAWING_BROKEN_LINE);
		window_layout.action_pane.changePaneStateAndRefresh(PaneState.DRAW_LINE);
	}
	
	public void onInteractiveCursorAction() {
		
	}
	
	public void onUploadImage() {
		
	}
	
	public void onCalculateDistance() {
		changeUserContext(UserContext.GETTING_DISTANCE);
	}
	
	public void onLeaveConference() {
		User.instance().leaveConference();
		window_layout.action_pane.stopOpacityThread();
		Platform.runLater(() -> openWindowAndHideCurrent(new AppWindow()));
	}
	
	public void onImagePanelAction() {
		window_layout.action_pane.changePaneStateAndRefresh(PaneState.LOADED_IMAGES);
	}
	
	public void onAddAnnotation() {
		changeUserContext(UserContext.ADDING_ANNOTATION);
	}
	
	public void onHelpAction() {
		
	}
	
	@Override
	protected void createModel() {
		window_model = new ConfWindowModel(this);
		model = window_model;
	}
	
	@Override
	protected void createLayout() {
		window_layout = new ConfWindowLayout(this);
		layout = window_layout;
	}
	
	@Override
	protected void initEventHandlers() {
		ScrollPane scroll_pane = window_layout.scroll_pane;
		scroll_pane.addEventFilter(ScrollEvent.SCROLL, ev -> input_assistant.onScrollPaneScroll(ev));
		scroll_pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, ev -> input_assistant.onScrollPaneDragged(ev));
		scroll_pane.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> input_assistant.onScrollPanePressed(ev));
		
		Dockbar dockbar = window_layout.dockbar;
		dockbar.addEventFilter(MouseEvent.MOUSE_MOVED, ev -> input_assistant.onDockbarMouseMoved(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> input_assistant.onDockbarMouseEntered(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> input_assistant.onDockbarMouseExited(ev));
		dockbar.getIcons().get(0).setOnAction(ev -> onDrawLine());
		dockbar.getIcons().get(1).setOnAction(ev -> onDrawMultipleLines());
		dockbar.getIcons().get(2).setOnAction(ev -> onInteractiveCursorAction());
		dockbar.getIcons().get(3).setOnAction(ev -> onAddAnnotation());
		dockbar.getIcons().get(4).setOnAction(ev -> onImagePanelAction());
		dockbar.getIcons().get(5).setOnAction(ev -> onCalculateDistance());
		dockbar.getIcons().get(6).setOnAction(ev -> onUploadImage());
		dockbar.getIcons().get(7).setOnAction(ev -> onHelpAction());
		dockbar.getIcons().get(8).setOnAction(ev -> onLeaveConference());

		ImageButton ibtn_chat = window_layout.ibtn_chat;
		ibtn_chat.addEventHandler(ActionEvent.ACTION, ev -> onChatBtnAction());
		
		ImageButton ibtn_members = window_layout.ibtn_members;
		ibtn_members.addEventHandler(ActionEvent.ACTION, ev -> onMembersBtnAction());
		
		Group root = window_layout.root;
		root.addEventFilter(KeyEvent.KEY_RELEASED, ev -> input_assistant.onHotkeyAction(ev));
		
		ChatPane chat_pane = window_layout.chat_pane;
		chat_pane.opacityProperty().addListener(this);
		chat_pane.getTextArea().addEventFilter(KeyEvent.KEY_PRESSED, ev -> input_assistant.onChatTextAreaKeyPressed(ev));
		
		AnnotationPane annotation_pane = window_layout.annotation_pane;
		annotation_pane.getTextArea().addEventFilter(KeyEvent.KEY_PRESSED, ev -> input_assistant.onAnnotationPaneTextAreaKeyPressed(ev));
		annotation_pane.getBtnSubmit().addEventFilter(ActionEvent.ACTION, ev -> onAnnotationSubmit());
		annotation_pane.getBtnCancel().addEventFilter(ActionEvent.ACTION, ev -> onAnnotationCancel());
	}
	
	@Override
	public void changed(ObservableValue<? extends Number> _observable, Number _old_value, Number _new_value) {
		MemberPane member_pane = window_layout.member_pane;
		
		if( !window_model.is_chat_hiding )
			window_model.chat_max_opacity = _new_value.doubleValue();
		
		if( _old_value.doubleValue() == 0.0 && _new_value.doubleValue() > 0.0 ) {
			member_pane.animateToLeft();
		} else if( (window_model.is_chat_hiding && window_model.chat_max_opacity < 0.7) || 
				(_old_value.doubleValue() >= 0.7 && _new_value.doubleValue() < 0.7) ) {
			member_pane.animateToRight(); 
		}
	}

	public ConfWindowModel getWindowModel() {
		return window_model;
	}
	
	public ConfWindowLayout getWindowLayout() {
		return window_layout;
	}
	
	public ConfWindowDrawableAssistant getDrawableAssistant() {
		return drawable_assistant;
	}
	
	public ConfWindowNetworkAssistant getNetworkAssistant() {
		return network_assistant;
	}
	
	public ConfWindowInputAssistant getInputAssistant() {
		return input_assistant;
	}
}
