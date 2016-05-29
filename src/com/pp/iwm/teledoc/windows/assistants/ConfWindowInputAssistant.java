package com.pp.iwm.teledoc.windows.assistants;

import java.util.List;

import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.gui.ChatPane;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.models.ConfWindowModel;
import com.pp.iwm.teledoc.models.ConfWindowModel.UserContext;
import com.pp.iwm.teledoc.utils.InputUtils;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.ConfWindow;

import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ConfWindowInputAssistant {
	
	// ====================================
	// FIELDS
	// ====================================
	
	private ConfWindow window;
	private ConfWindowModel model;
	private ConfWindowLayout layout;
	
	private double last_mouse_time;
	
	// ====================================
	// METHODS
	// ====================================
	
	public ConfWindowInputAssistant(ConfWindow _window) {
		window = _window;
		model = window.getWindowModel();
		layout = window.getWindowLayout();
	}
	
	public void onMouseMoved(MouseEvent _ev) {
		model.setMousePos(new Point2D(_ev.getSceneX(), _ev.getSceneY()));
		window.mapMousePosToImageMousePos();
		
		
		
		if( model.is_sending_pointer.get() && System.currentTimeMillis() > last_mouse_time + 50) {
			window.getNetworkAssistant().sendMousePos();
			last_mouse_time = System.currentTimeMillis();
		}
	}
	
	public void onScrollPanePressed(MouseEvent _ev) {
		DrawablePane drawable_pane = layout.drawable_pane;
		ConfWindowDrawableAssistant drawable_assistant = window.getDrawableAssistant();
		
		Point2D mouse_pos = new Point2D(_ev.getSceneX(), _ev.getSceneY());
		model.setMousePos(mouse_pos);
		
		window.mapMousePosToImageMousePos();
		Point2D image_mouse_pos = model.image_mouse_pos;
		
		switch( model.user_context ) {
			case DRAWING_LINE:
				if( InputUtils.onLeftBtn(_ev) ) 
					drawLine(image_mouse_pos, drawable_assistant, drawable_pane);
				else if( InputUtils.onRightBtn(_ev) )
					cancelDrawingLine();
				
				break;
			case DRAWING_BROKEN_LINE:
				if( InputUtils.onLeftBtn(_ev) )
					drawBrokenLine(image_mouse_pos, drawable_assistant, drawable_pane);
				else if( InputUtils.onRightBtn(_ev) ) 
					cancelDrawingBrokenLine();
				
				break;
			case GETTING_DISTANCE:
				if( InputUtils.onLeftBtn(_ev) ) 
					calculateDistanceBetweenPoints(image_mouse_pos);
				else if( InputUtils.onRightBtn(_ev) )
					cancelCalculationDistance();
				
				break;
			case ADDING_ANNOTATION:
				if( InputUtils.onLeftBtn(_ev) )
					drawAnnotation(mouse_pos, image_mouse_pos, drawable_assistant);
				else if( InputUtils.onRightBtn(_ev) )
					cancelDrawingAnnotation();
				
				break;
			case DOING_NOTHING:
				if( InputUtils.onRightBtn(_ev) )
					drawable_assistant.deselectObject();
				
				break;
		}
	}
	
	private void drawLine(Point2D _image_mouse_pos, ConfWindowDrawableAssistant _drawable_assistant, DrawablePane _drawable_pane) {
		if( model.temp1 == null)
			model.temp1 = _image_mouse_pos;
		else if( model.temp2 == null ) {
			model.temp2 = _image_mouse_pos;
			model.temp_line = new DrawableLine(model.temp1, model.temp2, Color.RED, _drawable_pane);
			_drawable_assistant.addDrawable(model.temp_line);
			model.temp1 = model.temp2 = null;
			window.changeUserContext(UserContext.DOING_NOTHING);
		}
	}
	
	private void cancelDrawingLine() {
		model.temp1 = model.temp2 = null;
		model.temp_line = null;
		window.changeUserContext(UserContext.DOING_NOTHING);
	}
	
	private void drawBrokenLine(Point2D _image_mouse_pos, ConfWindowDrawableAssistant _drawable_assistant, DrawablePane _drawable_pane) {
		if( model.temp1 == null )
			model.temp1 = _image_mouse_pos;
		else if( model.temp2 == null ) {
			model.temp2 = _image_mouse_pos;
			 
			if( model.temp_broken_line == null ) {
				model.temp_broken_line = new DrawableBrokenLine(model.temp1, model.temp2, Color.BLUE, _drawable_pane);
				_drawable_assistant.addDrawable(model.temp_broken_line);
			} else {
				Point2D p1 = model.temp1; Point2D p2 = model.temp2;
				Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
				_drawable_assistant.updateBrokenLine(line);
			}
			 
			model.temp1 = model.temp2;
			model.temp2 = null;
		}
	}
	
	private void cancelDrawingBrokenLine() {
		model.temp1 = model.temp2 = null;
		model.temp_broken_line = null;
		window.changeUserContext(UserContext.DOING_NOTHING);
	}
	
	private void calculateDistanceBetweenPoints(Point2D _image_mouse_pos) {
		if( model.temp1 == null )
			model.temp1 = _image_mouse_pos;
		else if( model.temp2 == null ) {
			model.temp2 = _image_mouse_pos;
			layout.removeTextFromStatusBar();
			layout.addTextToStatusBar("Odleg³oœæ pomiêdzy " + model.temp1 + " oraz " + model.temp2 + " = " + model.temp1.distance(model.temp2));
			model.temp1 = model.temp2 = null;
		}
	}
	
	private void cancelCalculationDistance() {
		model.temp1 = model.temp2 = null;
	}
	
	private void drawAnnotation(Point2D _mouse_pos, Point2D _image_mouse_pos, ConfWindowDrawableAssistant _drawable_assistant) {
		model.temp1 = _image_mouse_pos;
		if( model.temp_annotation == null )
			_drawable_assistant.createTempAnnotation();
		else
			model.temp_annotation.changePosition(model.temp1);
		
		layout.annotation_pane.showForAnnotation(model.temp_annotation);
	}
	
	private void cancelDrawingAnnotation() {
		window.onAnnotationCancel();
		window.changeUserContext(UserContext.DOING_NOTHING);
	}

	public void onScrollPaneScroll(ScrollEvent _ev) {
		double rescale_by = _ev.getDeltaY() / 750.0;
		layout.drawable_pane.rescaleBy(rescale_by);
		
		_ev.consume();
	}
	
	public void onScrollPaneDragged(MouseEvent _ev) {
		Point2D mouse_pos = model.getMousePos();
		DrawablePane drawable_pane = layout.drawable_pane;
		ScrollPane scroll_pane = layout.scroll_pane;
		Point2D new_pos = new Point2D(_ev.getSceneX(), _ev.getSceneY());
		
		if( InputUtils.onMiddleBtn(_ev) ) {
			Point2D diff = mouse_pos.subtract(new_pos);
			double canvas_w = drawable_pane.getWidth();
			double canvas_h = drawable_pane.getHeight();
			
			double x_translation = scroll_pane.getHvalue();
			x_translation = _ev.isControlDown() ? x_translation + diff.getX() / canvas_w * 2 : x_translation + diff.getX() / canvas_w;
			
			double y_translation = scroll_pane.getVvalue();
			y_translation = _ev.isControlDown() ? y_translation + diff.getY() / canvas_h * 2 : y_translation + diff.getY() / canvas_h;
			
			scroll_pane.setHvalue(x_translation);
			scroll_pane.setVvalue(y_translation);
			
			layout.annotation_pane.refresh();
			layout.minimap_pane.refresh();
		}
		
		model.setMousePos(new_pos);
	}
	
	public void onAnnotationPaneTextAreaKeyPressed(KeyEvent _ev) {
		if( InputUtils.onEnter(_ev) ) {
			if( InputUtils.withShift(_ev) )
				layout.annotation_pane.getTextArea().appendText("\n");
			else {
				window.onAnnotationSubmit();
				_ev.consume();
			}
		}
	}
	
	public void onChatTextAreaKeyPressed(KeyEvent _ev) {
		ChatPane chat_pane = layout.chat_pane;
		String message = chat_pane.getTextFromTextArea();
		
		if( InputUtils.onEnter(_ev) ) {
			if( InputUtils.withShift(_ev) )
				chat_pane.appendLine();
			else if( !Utils.isStringEmpty(message) ) {
				window.getNetworkAssistant().sendNewMessage(message);
				_ev.consume();
			}
		}
	}
	
	public void onDockbarMouseMoved(MouseEvent _ev) {
		Dockbar dockbar = layout.dockbar;
		int selected_icon = dockbar.getHoveredIconIndex();
		int old_selected_icon = dockbar.getOldHoveredIconIndex();
		List<ImageButton> all_icons = dockbar.getIcons();
		
		if( old_selected_icon != selected_icon ) {
			layout.removeTextFromStatusBar();
			
			if( selected_icon >= 0 )
				layout.addTextToStatusBar(all_icons.get(selected_icon).getHint());
		}
	}
	
	public void onDockbarMouseEntered(MouseEvent _ev) {
		layout.addTextToStatusBar("");
	}
	
	public void onDockbarMouseExited(MouseEvent _ev) {
		layout.dockbar.resetHoveredIndex();
		layout.removeTextFromStatusBar();
	}
	
	
	public void onHotkeyAction(KeyEvent _ev) {
		if( InputUtils.withControl(_ev) )
			onHotkeyWithControlDown(_ev);
		else if( InputUtils.withAlt(_ev) )
			onHotkeyWithAltDown(_ev);
		else if( InputUtils.withoutModifiers(_ev) )
			onHotkeyWithoutModifiers(_ev);
	}
	
	private void onHotkeyWithoutModifiers(KeyEvent _ev) {
		ConfWindowDrawableAssistant drawable_assistant = window.getDrawableAssistant();
		
		if( InputUtils.onAnyArrow(_ev) ) {
			navigateCanvas(_ev);
			_ev.consume();
		}
	}

	
	private void navigateCanvas(KeyEvent _ev) {
		double x_offset = InputUtils.onArrowLeft(_ev) ? -0.1 : InputUtils.onArrowRight(_ev) ? 0.1 : 0.0;
		double y_offset = InputUtils.onArrowUp(_ev) ? -0.1 : InputUtils.onArrowDown(_ev) ? 0.1 : 0.0;
		Point2D offset = new Point2D(x_offset, y_offset);
		navigateCanvas(offset);
	}
	
	private void navigateCanvas(Point2D _offset) {
		ScrollPane scroll_pane = layout.scroll_pane;
		
		if( layout.chat_pane.isTextAreaFocused() )
			return;
		
		scroll_pane.setHvalue(scroll_pane.getHvalue() + _offset.getX());
		scroll_pane.setVvalue(scroll_pane.getVvalue() + _offset.getY());
	}
	
	
	private void onHotkeyWithAltDown(KeyEvent _ev) {
		if( InputUtils.onF4(_ev) )
			window.onLeaveConference();
	}
	
	private void onHotkeyWithControlDown(KeyEvent _ev) {
		if( InputUtils.onLetterC(_ev) )
			window.onChatBtnAction();
		else if( InputUtils.onLetterH(_ev) )
			window.onHelpAction();
		else if( InputUtils.onLetterM(_ev) )
			window.onMembersBtnAction();
		else if( InputUtils.onArrowUp(_ev) )
			goToTopChatPane();
		else if( InputUtils.onArrowDown(_ev) )
			goToBottomChatPane();
		else if( InputUtils.onSpace(_ev) )
			changeFocus();
	}
	
	private void changeFocus() {
		ChatPane chat_pane = layout.chat_pane;
		
		if( chat_pane.isTextAreaFocused() )
			layout.root.requestFocus();
		else
			chat_pane.requestFocusForTextArea();
	}
	
	private void goToTopChatPane() {
		ChatPane chat_pane = layout.chat_pane;
		
		if( chat_pane.isVisible() )
			chat_pane.goToTop();
	}
	
	private void goToBottomChatPane() {
		ChatPane chat_pane = layout.chat_pane;
		
		if( chat_pane.isVisible() )
			chat_pane.goToBottom();
	}
}
