package com.pp.iwm.teledoc.windows;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.Annotation.State;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.gui.ActionPaneConf.PaneState;
import com.pp.iwm.teledoc.gui.AnnotationPane;
import com.pp.iwm.teledoc.gui.ChatPane;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.DrawablePane.LayersToDraw;
import com.pp.iwm.teledoc.gui.MemberPane;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.models.ConfWindowModel;
import com.pp.iwm.teledoc.models.ConfWindowModel.UserContext;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.network.User.NetworkListener;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.InputUtils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

public class ConfWindow extends Window implements ChangeListener<Number>, NetworkListener {

	
	// =========================================
	// FIELDS
	// =========================================
	
	private ConfWindowModel window_model;
	private ConfWindowLayout window_layout;
	
	// =========================================
	// METHODS
	// =========================================
	
	public ConfWindow() {
		ImageManager.instance().loadImage(2099, "/assets/big_image.jpg");
		User.instance().setListener(this);
	}
	
	public void changeCanvasLayerVisibility(LayersToDraw _layers_to_draw) {
		window_layout.drawable_pane.changeLayer(_layers_to_draw);
	}
	
	public void navigateCanvas(Point2D _offset) {
		ScrollPane scroll_pane = window_layout.scroll_pane;
		
		if( window_layout.chat_pane.isTextAreaFocused() )
			return;
		
		scroll_pane.setHvalue(scroll_pane.getHvalue() + _offset.getX());
		scroll_pane.setVvalue(scroll_pane.getVvalue() + _offset.getY());
	}
	
	
	public void createTempAnnotation() {
		DrawablePane drawable_pane = window_layout.drawable_pane;
		window_model.temp_annotation = new Annotation("Tutaj wpisz tekst", Color.GREEN, drawable_pane.getBoundsInParent(), window_model.temp1, drawable_pane);
		drawable_pane.addAnnotation(window_model.temp_annotation);
	}
	
	private void enableMicrophone() {
		
	}
	
	private void enableCamera() {
		
	}
	
	
	public void changeUserContext(UserContext _context) {
		window_model.user_context = _context;
	}
	
	public void onAnnotationSubmit(String _text) {
		Annotation tmp_ann = window_model.temp_annotation;
		
		if( !_text.equals("") )
			tmp_ann.setText(_text);
		
		window_layout.annotation_pane.hide();
		tmp_ann.changeState(State.DRAWN);
		tmp_ann = null;
		window_model.temp1 = window_model.temp2 = null;
		changeUserContext(UserContext.DOING_NOTHING);
	}
	
	public void onAnnotationCancel() {
		Annotation tmp_ann = window_model.temp_annotation;
		
		window_layout.annotation_pane.hide();
		window_model.temp1 = window_model.temp2 = null;
		 
		if( tmp_ann != null ) {
			window_layout.drawable_pane.removeAnnotation(tmp_ann);
			tmp_ann = null;
		}
	}
	
	@Override
	protected void createLayout() {
		window_layout = new ConfWindowLayout(this);
		layout = window_layout;
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

	@Override
	protected void createModel() {
		window_model = new ConfWindowModel(this);
		model = window_model;
	}

	@Override
	protected void initEventHandlers() {
		ScrollPane scroll_pane = window_layout.scroll_pane;
		scroll_pane.addEventFilter(ScrollEvent.SCROLL, ev -> onCanvasScroll(ev));
		scroll_pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, ev -> onCanvasDragged(ev));
		scroll_pane.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> onMousePressed(ev));
		
		Dockbar dockbar = window_layout.dockbar;
		dockbar.addEventFilter(MouseEvent.MOUSE_MOVED, ev -> onDockbarMouseMoved(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onDockbarMouseEntered(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onDockbarMouseExited(ev));
		dockbar.getIcons().get(0).setOnAction(ev -> onDrawLine());
		dockbar.getIcons().get(1).setOnAction(ev -> onDrawMultipleLines());
		dockbar.getIcons().get(2).setOnAction(ev -> onInteractiveCursorAction());
		dockbar.getIcons().get(3).setOnAction(ev -> onAddAnnotation());
		dockbar.getIcons().get(4).setOnAction(ev -> onImagePanelAction());
		dockbar.getIcons().get(5).setOnAction(ev -> onCalculateDistance());
		dockbar.getIcons().get(6).setOnAction(ev -> onUploadImage());
		dockbar.getIcons().get(7).setOnAction(ev -> onHelpAction());
		dockbar.getIcons().get(8).setOnAction(ev -> onLeaveConference());

		window_layout.ibtn_chat.addEventHandler(ActionEvent.ACTION, ev -> onChatBtnAction());
		window_layout.ibtn_members.addEventHandler(ActionEvent.ACTION, ev -> onMembersBtnAction());
		
		window_layout.root.addEventFilter(KeyEvent.KEY_RELEASED, ev -> onHotkeyAction(ev));
		window_layout.chat_pane.opacityProperty().addListener(this);
	}
	
	private void onCanvasScroll(ScrollEvent _ev) {
		double rescale_by = _ev.getDeltaY() / 750.0;
		window_layout.drawable_pane.rescaleBy(rescale_by);
		
		_ev.consume();
	}
	
	private void onCanvasDragged(MouseEvent _ev) {
		Point2D mouse_pos = window_model.getMousePos();
		DrawablePane drawable_pane = window_layout.drawable_pane;
		ScrollPane scroll_pane = window_layout.scroll_pane;
		
		if( _ev.getButton() == MouseButton.MIDDLE ) {
			Point2D new_pos = new Point2D(_ev.getSceneX(), _ev.getSceneY());
			Point2D diff = mouse_pos.subtract(new_pos);
			double canvas_w = drawable_pane.getWidth();
			double canvas_h = drawable_pane.getHeight();
			
			double x_translation = scroll_pane.getHvalue();
			x_translation = _ev.isControlDown() ? x_translation + diff.getX() / canvas_w * 2 : x_translation + diff.getX() / canvas_w;
			
			double y_translation = scroll_pane.getVvalue();
			y_translation = _ev.isControlDown() ? y_translation + diff.getY() / canvas_h * 2 : y_translation + diff.getY() / canvas_h;
			
			scroll_pane.setHvalue(x_translation);
			scroll_pane.setVvalue(y_translation);
			mouse_pos = new_pos;
		}
	}
	
	private void onMousePressed(MouseEvent _ev) {
		Point2D mouse_pos = window_model.getMousePos();
		DrawablePane drawable_pane = window_layout.drawable_pane;
		ScrollPane scroll_pane = window_layout.scroll_pane;
		StatusBar status_bar = window_layout.status_bar;
		AnnotationPane annotation_pane = window_layout.annotation_pane;
		UserContext user_context = window_model.user_context;
		Point2D temp1 = window_model.temp1;
		Point2D temp2 = window_model.temp2;
		DrawableBrokenLine temp_broken_line = window_model.temp_broken_line;
		Annotation temp_annotation = window_model.temp_annotation;
		
		mouse_pos = new Point2D(_ev.getSceneX(), _ev.getSceneY());
		double canvas_w = drawable_pane.getWidth();
		double canvas_h = drawable_pane.getHeight();
		double x_pos = mouse_pos.getX() + scroll_pane.getHvalue() * (canvas_w - scroll_pane.getViewportBounds().getWidth()) - 4; // 2 - ramka okna która ma 2 pigzy
		double y_pos = mouse_pos.getY() + scroll_pane.getVvalue() * (canvas_h - scroll_pane.getViewportBounds().getHeight()) - 3; //
		x_pos /= drawable_pane.getScale();
		y_pos /= drawable_pane.getScale();
		 
		if( user_context == UserContext.DRAWING_LINE && _ev.getButton() == MouseButton.PRIMARY ) {	// TODO dodaæ anulowanie rysowania
			if( temp1 == null)
				temp1 = new Point2D(x_pos, y_pos);
			else if( temp2 == null ) {
				temp2 = new Point2D(x_pos, y_pos);
				DrawableLine line = new DrawableLine(temp1, temp2, Color.RED, drawable_pane);
				drawable_pane.addLine(line);
				temp1 = temp2 = null;
				changeUserContext(UserContext.DOING_NOTHING);
			}
		} else if( user_context == UserContext.DRAWING_BROKEN_LINE ) {
			if( _ev.getButton() == MouseButton.PRIMARY ) {
				if( temp1 == null )
					temp1 = new Point2D(x_pos, y_pos);
				else if( temp2 == null ) {
					temp2 = new Point2D(x_pos, y_pos);
					 
					if( temp_broken_line == null ) {
						temp_broken_line = new DrawableBrokenLine(temp1, temp2, Color.BLUE, drawable_pane);
						drawable_pane.addBrokenLine(temp_broken_line);
					} else
						temp_broken_line.addLine(temp1, temp2);
					 
					temp1 = temp2;
					temp2 = null;
				}
			} else if( _ev.getButton() == MouseButton.SECONDARY ) {
				temp1 = temp2 = null;
				temp_broken_line = null;
				changeUserContext(UserContext.DOING_NOTHING);
			}
		} else if( user_context == UserContext.GETTING_DISTANCE ) { // dodaæ anulowanie obliczania
			if( _ev.getButton() == MouseButton.PRIMARY ) {
				if( temp1 == null )
					temp1 = new Point2D(x_pos, y_pos);
				else if( temp2 == null ) {
					temp2 = new Point2D(x_pos, y_pos);
					status_bar.removeText();
					status_bar.addText("Odleg³oœæ pomiêdzy " + temp1 + " oraz " + temp2 + " = " + temp1.distance(temp2));
					temp1 = temp2 = null;
				}
			}
		} else if( user_context == UserContext.ADDING_ANNOTATION ) {
			if( _ev.getButton() == MouseButton.PRIMARY ) {
				annotation_pane.showAtLocation(mouse_pos, -1.0, false);
				window_model.temp1 = new Point2D(x_pos, y_pos);
				if( temp_annotation == null ) {
					createTempAnnotation();
				} else {
					temp_annotation.changePosition(temp1);
				}
				 
				 
			} else if( _ev.getButton() == MouseButton.SECONDARY ) {
				onAnnotationCancel();
				changeUserContext(UserContext.DOING_NOTHING);
			}
		}
	}
	
	private void onDockbarMouseMoved(MouseEvent _ev) {
		Dockbar dockbar = window_layout.dockbar;
		int selected_icon = dockbar.getHoveredIconIndex();
		int old_selected_icon = dockbar.getOldHoveredIconIndex();
		List<ImageButton> all_icons = dockbar.getIcons();
		
		if( old_selected_icon != selected_icon ) {
			window_layout.removeTextFromStatusBar();
			
			if( selected_icon >= 0 )
				window_layout.addTextToStatusBar(all_icons.get(selected_icon).getHint());
		}
	}
	
	private void onDockbarMouseEntered(MouseEvent _ev) {
		window_layout.addTextToStatusBar("");
	}
	
	private void onDockbarMouseExited(MouseEvent _ev) {
		window_layout.dockbar.resetHoveredIndex();
		window_layout.removeTextFromStatusBar();
	}
	
	public void showAnnotationPane(ScrollPane _annotation_text_pane) {
		window_layout.root.getChildren().add(_annotation_text_pane);
	}
	
	public void hideAnnotationPane(ScrollPane _annotation_text_pane) {
		window_layout.root.getChildren().add(_annotation_text_pane);
	}
	
	private void onHotkeyAction(KeyEvent _ev) {
		if( InputUtils.withControl(_ev) )
			onHotkeyWithControlDown(_ev);
		else if( InputUtils.withAlt(_ev) )
			onHotkeyWithAltDown(_ev);
		else if( InputUtils.withoutModifiers(_ev) )
			onHotkeyWithoutModifiers(_ev);
	}
	
	private void onHotkeyWithAltDown(KeyEvent _ev) {
		if( InputUtils.onF4(_ev) )
			onLeaveConference();
	}
	
	private void onHotkeyWithControlDown(KeyEvent _ev) {
		if( InputUtils.onLetterC(_ev) )
			onChatBtnAction();
		else if( InputUtils.onLetterH(_ev) )
			onHelpAction();
		else if( InputUtils.onLetterM(_ev) )
			onMembersBtnAction();
		else if( InputUtils.onArrowUp(_ev) )
			goToTopChatPane();
		else if( InputUtils.onArrowDown(_ev) )
			goToBottomChatPane();
		else if( InputUtils.onSpace(_ev) )
			changeFocus();
	}
	
	private void changeFocus() {
		ChatPane chat_pane = window_layout.chat_pane;
		
		if( chat_pane.isTextAreaFocused() )
			window_layout.root.requestFocus();
		else
			chat_pane.requestFocusForTextArea();
	}
	
	private void goToTopChatPane() {
		ChatPane chat_pane = window_layout.chat_pane;
		
		if( chat_pane.isVisible() )
			chat_pane.goToTop();
	}
	
	private void goToBottomChatPane() {
		ChatPane chat_pane = window_layout.chat_pane;
		
		if( chat_pane.isVisible() )
			chat_pane.goToBottom();
	}
	
	private void onHotkeyWithoutModifiers(KeyEvent _ev) {
		if( InputUtils.onAnyArrow(_ev) ) {
			navigateCanvas(_ev);
			_ev.consume();
		} else if( InputUtils.onDigit0(_ev) )
			changeCanvasLayerVisibility(LayersToDraw.ONLY_IMAGE);
		else if( InputUtils.onDigit1(_ev) )
			changeCanvasLayerVisibility(LayersToDraw.LINES);
		else if( InputUtils.onDigit2(_ev) )
			changeCanvasLayerVisibility(LayersToDraw.ANNOTATIONS);
		else if( InputUtils.onDigit3(_ev) )
			changeCanvasLayerVisibility(LayersToDraw.DRAW_ALL);
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
		Platform.runLater(() -> openWindowAndHideCurrent(new AppWindow()));
	}
	
	public void onImagePanelAction() {
		
	}
	
	public void onAddAnnotation() {
		changeUserContext(UserContext.ADDING_ANNOTATION);
	}
	
	public void onHelpAction() {
		
	}
	
	private void navigateCanvas(KeyEvent _ev) {
		double x_offset = InputUtils.onArrowLeft(_ev) ? -0.1 : InputUtils.onArrowRight(_ev) ? 0.1 : 0.0;
		double y_offset = InputUtils.onArrowUp(_ev) ? -0.1 : InputUtils.onArrowDown(_ev) ? 0.1 : 0.0;
		Point2D offset = new Point2D(x_offset, y_offset);
		navigateCanvas(offset);
	}

	@Override
	public void onStateChanged(com.pp.iwm.teledoc.network.User.State _state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceive(Connection _connection, Object _message) {
		// TODO Auto-generated method stub
		
	}
}
