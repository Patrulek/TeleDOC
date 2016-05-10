package com.pp.iwm.teledoc.windows;

import java.util.List;

import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.Annotation.State;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.gui.AnnotationPane;
import com.pp.iwm.teledoc.gui.ChatPane;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.DoubleStateImageButton;
import com.pp.iwm.teledoc.gui.DrawableCanvas;
import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.gui.DrawablePane.LayersToDraw;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.MemberPane;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.gui.Utils;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.objects.InputUtils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

public class ConfWindow extends Window implements ChangeListener<Number> {

	
	// =========================================
	// FIELDS
	// =========================================
	
	private enum UserContext {
		DRAWING_LINE, DRAWING_BROKEN_LINE, GETTING_DISTANCE, ADDING_ANNOTATION, DOING_NOTHING 
	}
	
	private UserContext user_context;
	private Rectangle rect_window_background; 
	private StatusBar status_bar;
	private ChatPane chat_pane;
	private MemberPane member_pane;
	private Dockbar dockbar;
	private ScrollPane scroll_pane;
	private DrawablePane drawable_pane;
	private AnnotationPane annotation_pane;
	
	private double chat_max_opacity = 1.0;
	private boolean is_chat_hiding = false;
	
	
	private Point2D temp1;
	private Point2D temp2;
	private DrawableBrokenLine temp_broken_line;
	private Annotation temp_annotation;
	
	// =========================================
	// METHODS
	// =========================================
	
	
	public ConfWindow() {
		root.addEventFilter(KeyEvent.KEY_RELEASED, ev -> onHotkeyAction(ev));
		user_context = UserContext.DOING_NOTHING;
	}
	
	private void onHotkeyAction(KeyEvent _ev) {
		if( InputUtils.withControl(_ev) )
			onHotkeyWithControlDown(_ev);
		else if( InputUtils.withoutModifiers(_ev) )
			onHotkeyWithoutModifiers(_ev);
	}
	
	private void onHotkeyWithControlDown(KeyEvent _ev) {
		if( InputUtils.onKeyC(_ev) )
			onChatBtnAction();
		else if( InputUtils.onKeyH(_ev) )
			onHelpAction();
		else if( InputUtils.onKeyM(_ev) )
			onMembersBtnAction();
		else if( InputUtils.onKeyUp(_ev) )
			goToTopChatPane();
		else if( InputUtils.onKeyDown(_ev) )
			goToBottomChatPane();
		else if( InputUtils.onKeySpace(_ev) )
			changeFocus();
	}
	
	private void onHotkeyWithoutModifiers(KeyEvent _ev) {
		if( InputUtils.onAnyArrow(_ev) ) {
			navigateCanvas(_ev);
			_ev.consume();
		} else if( InputUtils.onKey0(_ev) )
			changeCanvasLayerVisibility(LayersToDraw.ONLY_IMAGE);
		else if( InputUtils.onKey1(_ev) )
			changeCanvasLayerVisibility(LayersToDraw.LINES);
		else if( InputUtils.onKey2(_ev) )
			changeCanvasLayerVisibility(LayersToDraw.ANNOTATIONS);
		else if( InputUtils.onKey3(_ev) )
			changeCanvasLayerVisibility(LayersToDraw.DRAW_ALL);
	}

	private void changeCanvasLayerVisibility(LayersToDraw _layers_to_draw) {
		drawable_pane.changeLayer(_layers_to_draw);
	}
	
	private void navigateCanvas(KeyEvent _ev) {
		if( chat_pane.isTextAreaFocused() )
			return;
		
		if( InputUtils.onKeyUp(_ev) )
			scroll_pane.setVvalue(scroll_pane.getVvalue() - 0.1);
		else if( InputUtils.onKeyDown(_ev) )
			scroll_pane.setVvalue(scroll_pane.getVvalue() + 0.1);
		
		if( InputUtils.onKeyLeft(_ev) )
			scroll_pane.setHvalue(scroll_pane.getHvalue() - 0.1);
		else if( InputUtils.onKeyRight(_ev) )
			scroll_pane.setHvalue(scroll_pane.getHvalue() + 0.1);
	}
	
	private void changeFocus() {
		if( chat_pane.isTextAreaFocused() )
			root.requestFocus();
		else
			chat_pane.requestFocusForTextArea();
	}
	
	private void goToTopChatPane() {
		if( chat_pane.isVisible() )
			chat_pane.goToTop();
	}
	
	private void goToBottomChatPane() {
		if( chat_pane.isVisible() )
			chat_pane.goToBottom();
	}
	
	public void addTextToStatusBar(String _text) {
		status_bar.addText(_text);
	}
	
	public void removeTextFromStatusBar() {
		status_bar.removeText();
	}
	
	private void enableMicrophone() {
		
	}
	
	private void enableCamera() {
		
	}
	
	private void onChatBtnAction() {
		if( chat_pane.getOpacity() != 0.0 ) {
			
			chat_pane.hide();
			is_chat_hiding = true;
		} else {
			chat_pane.show();
			is_chat_hiding = false;
		}
	}
	
	private void onMembersBtnAction() {
		if( member_pane.getOpacity() != 0.0 )
			member_pane.hide();
		else
			member_pane.show();
	}
	
	private void changeContext(UserContext _context) {
		if( user_context == _context )
			return;
		
		user_context = _context;
	}
	
	private void onDrawLine() {
		changeContext(UserContext.DRAWING_LINE);
	}
	
	private void onDrawMultipleLines() {
		changeContext(UserContext.DRAWING_BROKEN_LINE);
	}
	
	private void onInteractiveCursorAction() {
		
	}
	
	private void onUploadImage() {
		
	}
	
	private void onCalculateDistance() {
		changeContext(UserContext.GETTING_DISTANCE);
	}
	
	private void onLeaveConference() {
		Platform.exit();
	}
	
	private void onImagePanelAction() {
		
	}
	
	private void onAddAnnotation() {
		changeContext(UserContext.ADDING_ANNOTATION);
	}
	
	private void onHelpAction() {
		
	}
	
	private void populateDockbar() {
		// line
				ImageButton btn_1 = new ImageButton(Utils.IMG_LINE, Utils.HINT_LINE, Utils.ACT_LINE);
				dockbar.addIconAndFitInBar(btn_1);
				btn_1.setOnAction(ev -> onDrawLine());
				
				// broken line
				ImageButton btn_2 = new ImageButton(Utils.IMG_LINE2, Utils.HINT_LINE2, Utils.ACT_LINE2);
				dockbar.addIconAndFitInBar(btn_2);
				btn_2.setOnAction(ev -> onDrawMultipleLines());
				
				// pointer
				DoubleStateImageButton btn_3 = new DoubleStateImageButton(Utils.IMG_POINTER_ON, Utils.IMG_POINTER_OFF, Utils.HINT_POINTER_ON, Utils.ACT_POINTER);
				dockbar.addIconAndFitInBar(btn_3);
				btn_3.setOnAction(ev -> onInteractiveCursorAction());
				
				// add annotation
				ImageButton btn_4 = new ImageButton(Utils.IMG_ANNOTATION, Utils.HINT_ANNOTATION, Utils.ACT_ANNOTATION);
				dockbar.addIconAndFitInBar(btn_4);
				btn_4.setOnAction(ev -> onAddAnnotation());
				
				// image panel
				ImageButton btn_5 = new ImageButton(Utils.IMG_IMAGE_PANEL, Utils.HINT_IMAGE_PANEL, Utils.ACT_IMAGE_PANEL);
				dockbar.addIconAndFitInBar(btn_5);
				btn_5.setOnAction(ev -> onImagePanelAction());
				
				// distance
				ImageButton btn_6 = new ImageButton(Utils.IMG_DISTANCE, Utils.HINT_DISTANCE, Utils.ACT_DISTANCE);
				dockbar.addIconAndFitInBar(btn_6);
				btn_6.setOnAction(ev -> onCalculateDistance());
				
				// upload
				ImageButton btn_7 = new ImageButton(Utils.IMG_UPLOAD_ICON, Utils.HINT_UPLOAD_FILE, Utils.ACT_UPLOAD_FILE);
				dockbar.addIconAndFitInBar(btn_7);
				btn_7.setOnAction(ev -> onUploadImage());
				
				// help
				ImageButton btn_8 = new ImageButton(Utils.IMG_HELP_ICON, Utils.HINT_HELP, Utils.ACT_SHOW_HELP);
				dockbar.addIconAndFitInBar(btn_8);
				btn_8.setOnAction(ev -> onHelpAction());
				
				// new conference from file
				ImageButton btn_9 = new ImageButton(Utils.IMG_LOGOUT_ICON, Utils.HINT_LEAVE_CONF, Utils.ACT_LOGOUT);
				dockbar.addIconAndFitInBar(btn_9);
				btn_9.setOnAction(ev -> onLeaveConference());
	}
	
	private void onDockbarMouseMoved(MouseEvent _ev) {
		int selected_icon = dockbar.getHoveredIconIndex();
		int old_selected_icon = dockbar.getOldHoveredIconIndex();
		List<ImageButton> all_icons = dockbar.getIcons();
		
		if( old_selected_icon != selected_icon ) {
			removeTextFromStatusBar();
			
			if( selected_icon >= 0 )
				addTextToStatusBar(all_icons.get(selected_icon).getHint());
		}
	}
	
	private void onDockbarMouseEntered(MouseEvent _ev) {
		addTextToStatusBar("");
	}
	
	private void onDockbarMouseExited(MouseEvent _ev) {
		dockbar.resetHoveredIndex();
		removeTextFromStatusBar();
	}
	
	
	private void createButtons() {
		DoubleStateImageButton dibtn_1 = new DoubleStateImageButton(Utils.IMG_MICROPHONE_ON, Utils.IMG_MICROPHONE_OFF, "mic", Utils.ACT_MICROPHONE);
		dibtn_1.setLayoutX(1300.0); dibtn_1.setLayoutY(100.0);

		DoubleStateImageButton dibtn_2 = new DoubleStateImageButton(Utils.IMG_CAMERA_ON, Utils.IMG_CAMERA_OFF, "cam", Utils.ACT_CAMERA);
		dibtn_2.setLayoutX(1300.0); dibtn_2.setLayoutY(160.0);
		

		DoubleStateImageButton dibtn_3 = new DoubleStateImageButton(Utils.IMG_CHAT, Utils.IMG_CHAT_NEW, "chat", Utils.ACT_CHAT);
		dibtn_3.setLayoutX(1300.0); dibtn_3.setLayoutY(220.0);
		dibtn_3.removeAction();
		dibtn_3.addEventHandler(ActionEvent.ACTION, ev -> onChatBtnAction());
		

		DoubleStateImageButton dibtn_4 = new DoubleStateImageButton(Utils.IMG_MEMBERS, Utils.IMG_MEMBERS_NEW, "members", Utils.ACT_MICROPHONE);
		dibtn_4.setLayoutX(1300.0); dibtn_4.setLayoutY(280.0);
		dibtn_4.removeAction();
		dibtn_4.addEventHandler(ActionEvent.ACTION, ev ->onMembersBtnAction());
		
		root.getChildren().add(dibtn_1);
		root.getChildren().add(dibtn_2);
		root.getChildren().add(dibtn_3);
		root.getChildren().add(dibtn_4);
	}
	
	private void onCanvasScroll(ScrollEvent _ev) {
		double rescale_by = _ev.getDeltaY() / 750.0;
		drawable_pane.rescaleBy(rescale_by);
		
		_ev.consume();
	}
	
	private void onCanvasDragged(MouseEvent _ev) {
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
		 mouse_pos = new Point2D(_ev.getSceneX(), _ev.getSceneY());
		 double canvas_w = drawable_pane.getWidth();
		 double canvas_h = drawable_pane.getHeight();
		 double x_pos = mouse_pos.getX() + scroll_pane.getHvalue() * (canvas_w - scroll_pane.getViewportBounds().getWidth()) - 4; // 2 - ramka okna która ma 2 pigzy
		 double y_pos = mouse_pos.getY() + scroll_pane.getVvalue() * (canvas_h - scroll_pane.getViewportBounds().getHeight()) - 3; //
		 x_pos /= drawable_pane.getScale();
		 y_pos /= drawable_pane.getScale();
		 
		 if( user_context == UserContext.DRAWING_LINE && _ev.getButton() == MouseButton.PRIMARY ) {	// dodaæ anulowanie rysowania
			 if( temp1 == null)
				 temp1 = new Point2D(x_pos, y_pos);
			 else if( temp2 == null ) {
				 temp2 = new Point2D(x_pos, y_pos);
				 DrawableLine line = new DrawableLine(temp1, temp2, Color.GREEN, drawable_pane);
				 drawable_pane.addLine(line);
				 temp1 = temp2 = null;
				 changeContext(UserContext.DOING_NOTHING);
			 }
		 } else if( user_context == UserContext.DRAWING_BROKEN_LINE ) {
			 if( _ev.getButton() == MouseButton.PRIMARY ) {
				 if( temp1 == null )
					 temp1 = new Point2D(x_pos, y_pos);
				 else if( temp2 == null ) {
					 temp2 = new Point2D(x_pos, y_pos);
					 
					 if( temp_broken_line == null ) {
						 temp_broken_line = new DrawableBrokenLine(temp1, temp2, Color.RED, drawable_pane);
						 drawable_pane.addBrokenLine(temp_broken_line);
					 } else
						 temp_broken_line.addLine(temp1, temp2);
					 
					 temp1 = temp2;
					 temp2 = null;
				 }
			 } else if( _ev.getButton() == MouseButton.SECONDARY ) {
				 temp1 = temp2 = null;
				 temp_broken_line = null;
				 changeContext(UserContext.DOING_NOTHING);
			 }
		 } else if( user_context == UserContext.GETTING_DISTANCE ) { // dodaæ anulowanie obliczania
			 if( _ev.getButton() == MouseButton.PRIMARY ) {
				 if( temp1 == null )
					 temp1 = new Point2D(x_pos, y_pos);
				 else if( temp2 == null ) {
					 temp2 = new Point2D(x_pos, y_pos);
					// DrawableLine line = new DrawableLine(temp1, temp2, Color.YELLOW);
					// Line l = new Line(temp1.getX(), temp1.getY(), temp2.getX(), temp2.getY());
					 //drawable_pane.addLine(l);
					 status_bar.removeText();
					 status_bar.addText("Odleg³oœæ pomiêdzy " + temp1 + " oraz " + temp2 + " = " + temp1.distance(temp2));
					 temp1 = temp2 = null;
				 }
			 }
		 } else if( user_context == UserContext.ADDING_ANNOTATION ) {
			 if( _ev.getButton() == MouseButton.PRIMARY ) {
				 annotation_pane.showAtLocation(mouse_pos, -1.0, false);
				 temp1 = new Point2D(x_pos, y_pos);
				 
				 if( temp_annotation == null ) {
					 temp_annotation = new Annotation("Tutaj wpisz tekst", Color.YELLOWGREEN, drawable_pane.getBoundsInParent(), temp1, drawable_pane);
					 drawable_pane.addAnnotation(temp_annotation);
				 } else {
					 temp_annotation.changePosition(temp1);
				 }
				 
				 
			 } else if( _ev.getButton() == MouseButton.SECONDARY ) {
				 onAnnotationCancel();
				 changeContext(UserContext.DOING_NOTHING);
			 }
		 }
	}
	
	public void onAnnotationSubmit(String _text) {
		if( !_text.equals("") )
			temp_annotation.setText(_text);
		
		annotation_pane.hide();
		temp_annotation.changeState(State.DRAWN);
		temp_annotation = null;
		temp1 = temp2 = null;
		changeContext(UserContext.DOING_NOTHING);
	}
	
	public void onAnnotationCancel() {
		annotation_pane.hide();
		temp1 = temp2 = null;
		 
		if( temp_annotation != null ) {
			drawable_pane.removeAnnotation(temp_annotation);
			temp_annotation = null;
		}
	}
	
	@Override
	protected void createStage() {
		ImageManager.instance().loadImage(2099, "/assets/big_image.jpg");
		
		Scene scene = new Scene(root, 1366, 768, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		
		// window background
		rect_window_background = new Rectangle(1364, 766);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setLayoutX(1.0); rect_window_background.setLayoutY(1.0);
		rect_window_background.setStroke(Color.rgb(45, 81, 90));
		rect_window_background.setStrokeWidth(2.0);

		// drawable canvas
		drawable_pane = new DrawablePane(this);
		
		scroll_pane = new ScrollPane(drawable_pane);
		scroll_pane.setPrefSize(1362, 744);
		scroll_pane.setLayoutX(2.0); scroll_pane.setLayoutY(2.0);
		//scroll_pane.setFitToHeight(true); scroll_pane.setFitToWidth(true);
		scroll_pane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.getStylesheets().add("/styles/canvas_pane.css");
		scroll_pane.addEventFilter(ScrollEvent.SCROLL, ev -> onCanvasScroll(ev));
		scroll_pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, ev -> onCanvasDragged(ev));
		scroll_pane.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> onMousePressed(ev));
		scroll_pane.addEventFilter(ScrollEvent.SCROLL_FINISHED, ev -> System.out.println("Finished"));
		
	/*	Platform.runLater(() -> {
								System.out.println(scroll_pane.getWidth() + " | " + scroll_pane.getHeight());
								System.out.println(scroll_pane.getViewportBounds());
								System.out.println(scroll_pane.getBoundsInLocal());
								System.out.println(scroll_pane.getBoundsInParent());
								System.out.println(scroll_pane.getVmax() + " | " + scroll_pane.getVmin());
								System.out.println(scroll_pane.getHmax() + " | " + scroll_pane.getHmin());
								System.out.println(scroll_pane.contentProperty().get().getBoundsInLocal());
								});  */
		Platform.runLater(() -> { drawable_pane.setViewportBounds(scroll_pane.getViewportBounds()); drawable_pane.setImageAndResetCanvas(2099);});
		
		// chat pane
		chat_pane = new ChatPane(this);
		chat_pane.setLayoutX(1065.0); chat_pane.setLayoutY(60.0);
		chat_pane.opacityProperty().addListener(this);
		
		// member pane
		member_pane = new MemberPane(this);
		member_pane.setLayoutX(1065.0); member_pane.setLayoutY(60.0);
		
		// dockbar
		dockbar = new Dockbar(32.0, 8.0);
		dockbar.setLayoutX(2.0); dockbar.setLayoutY(2.0);
		dockbar.addEventFilter(MouseEvent.MOUSE_MOVED, ev -> onDockbarMouseMoved(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onDockbarMouseEntered(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onDockbarMouseExited(ev));
		dockbar.setStyle("-fx-background-color: rgb(30, 54, 60);");
		dockbar.setVertical();
		populateDockbar();

		// status bar
		status_bar = new StatusBar();
		status_bar.setLayoutX(2.0); status_bar.setLayoutY(746.0);
		status_bar.setPrefWidth(1362);
		
		// annotation pane
		annotation_pane = new AnnotationPane(this, rect_window_background.getLayoutBounds());
		
		// add elements
		root.getChildren().add(rect_window_background);
		root.getChildren().add(scroll_pane);
		root.getChildren().add(member_pane);
		root.getChildren().add(chat_pane);
		root.getChildren().add(dockbar);
		root.getChildren().add(status_bar);
		root.getChildren().add(annotation_pane);
		
		createButtons();
		
		stage.setScene(scene);
	}

	@Override
	public void changed(ObservableValue<? extends Number> _observable, Number _old_value, Number _new_value) {
		if( !is_chat_hiding )
			chat_max_opacity = _new_value.doubleValue();
		
		if( _old_value.doubleValue() == 0.0 && _new_value.doubleValue() > 0.0 ) {
			member_pane.animateToLeft();
		} else if( (is_chat_hiding && chat_max_opacity < 0.7) || 
				(_old_value.doubleValue() >= 0.7 && _new_value.doubleValue() < 0.7) ) {
			member_pane.animateToRight(); 
		}
	}
}
