package com.pp.iwm.teledoc.layouts;

import com.pp.iwm.teledoc.gui.ActionPaneConf;
import com.pp.iwm.teledoc.gui.AnnotationPane;
import com.pp.iwm.teledoc.gui.AnnotationTextPane;
import com.pp.iwm.teledoc.gui.ChatPane;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.DoubleStateImageButton;
import com.pp.iwm.teledoc.gui.DrawablePane;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.LayersPanel;
import com.pp.iwm.teledoc.gui.MemberCard;
import com.pp.iwm.teledoc.gui.MemberPane;
import com.pp.iwm.teledoc.gui.MinimapPane;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.ConfWindow;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;

public class ConfWindowLayout extends WindowLayout {
	
	// ============================
	// FIELDS
	// ============================
	
	private ConfWindow conf_window;
	
	public Rectangle rect_window_background; 
	public StatusBar status_bar;
	public ChatPane chat_pane;
	public MemberPane member_pane;
	public Dockbar dockbar;
	public ScrollPane scroll_pane;
	public DrawablePane drawable_pane;
	public AnnotationPane annotation_pane;
	public AnnotationTextPane annotation_text_pane;
	public ActionPaneConf action_pane;
	public MinimapPane minimap_pane;
	public LayersPanel layers_pane;
	public DoubleStateImageButton ibtn_camera;
	public DoubleStateImageButton ibtn_microphone;
	public DoubleStateImageButton ibtn_chat;
	public DoubleStateImageButton ibtn_members;
	
	// =============================
	// METHODS
	// =============================
	
	public ConfWindowLayout(ConfWindow _window) {
		super(_window);
		conf_window = (ConfWindow) window;
	}
	
	public void create() {
		scene = new Scene(root, 1366, 768, Color.rgb(0, 0, 0, 0));
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setOnCloseRequest(ev -> ev.consume());
		
		createBackground();
		createStatusBar();
		createChatPane();
		createMemberPane();
		createDockbar();

		createDrawablePane();
		createScrollPane();
		createAnnotationPane();
		createAnnotationTextPane();
		createActionPane();
		createMinimapPane();
		createButtons();
		createLayersPane();
		
		addElementsToScene();
		initializeLater();
		
		stage.setScene(scene);
	}
	
	private void createBackground() {
		rect_window_background = new Rectangle(1364, 766);
		rect_window_background.setFill(Utils.PRIMARY_DARK_COLOR);
		rect_window_background.setLayoutX(1.0); rect_window_background.setLayoutY(1.0);
		rect_window_background.setStroke(Color.rgb(45, 81, 90));
		rect_window_background.setStrokeWidth(2.0);
	}
	
	private void createStatusBar() {
		status_bar = new StatusBar();
		status_bar.setLayoutX(2.0); status_bar.setLayoutY(746.0);
		status_bar.setPrefWidth(1362.0);
	}
	
	private void createChatPane() {
		chat_pane = new ChatPane(window);
		chat_pane.setLayoutX(1065.0); chat_pane.setLayoutY(60.0);
	}
	
	private void createMemberPane() {
		member_pane = new MemberPane(this);
		member_pane.setLayoutX(1065.0); member_pane.setLayoutY(60.0);
	}
	
	private void createDockbar() {
		dockbar = new Dockbar(32.0, 8.0);
		dockbar.setLayoutX(2.0); dockbar.setLayoutY(2.0);
		dockbar.setStyle("-fx-background-color: rgb(30, 54, 60);");
		dockbar.setVertical();
		populateDockbar();
	}
	
	private void populateDockbar() {
		ImageButton btn_1 = new ImageButton(Utils.IMG_LINE, Utils.HINT_LINE, Utils.ACT_LINE);
		dockbar.addIconAndFitInBar(btn_1);
		
		// broken line
		ImageButton btn_2 = new ImageButton(Utils.IMG_LINE2, Utils.HINT_LINE2, Utils.ACT_LINE2);
		dockbar.addIconAndFitInBar(btn_2);
		
		// pointer
		DoubleStateImageButton btn_3 = new DoubleStateImageButton(Utils.IMG_POINTER_OFF, Utils.IMG_POINTER_ON, Utils.HINT_POINTER_ON, Utils.ACT_POINTER);
		btn_3.switchOff();
		dockbar.addIconAndFitInBar(btn_3);
		
		// add annotation
		ImageButton btn_4 = new ImageButton(Utils.IMG_ANNOTATION, Utils.HINT_ANNOTATION, Utils.ACT_ANNOTATION);
		dockbar.addIconAndFitInBar(btn_4);
		
		// image panel
		ImageButton btn_5 = new ImageButton(Utils.IMG_IMAGE_PANEL, Utils.HINT_IMAGE_PANEL, Utils.ACT_IMAGE_PANEL);
		dockbar.addIconAndFitInBar(btn_5);
		
		// distance
		ImageButton btn_6 = new ImageButton(Utils.IMG_DISTANCE, Utils.HINT_DISTANCE, Utils.ACT_DISTANCE);
		dockbar.addIconAndFitInBar(btn_6);
		
		// delete object
		ImageButton btn_7 = new ImageButton(Utils.IMG_DELETE, Utils.HINT_DELETE, Utils.ACT_DELETE);
		dockbar.addIconAndFitInBar(btn_7);
		
		// layers
		ImageButton btn_8 = new ImageButton(Utils.IMG_LAYERS, Utils.HINT_LAYERS, Utils.ACT_LAYERS);
		dockbar.addIconAndFitInBar(btn_8);
		
		// upload
		ImageButton btn_9 = new ImageButton(Utils.IMG_UPLOAD_ICON, Utils.HINT_UPLOAD_FILE, Utils.ACT_UPLOAD_FILE);
		dockbar.addIconAndFitInBar(btn_9);
		
		// help
		ImageButton btn_10 = new ImageButton(Utils.IMG_HELP_ICON, Utils.HINT_HELP, Utils.ACT_SHOW_HELP);
		dockbar.addIconAndFitInBar(btn_10);
		
		// new conference from file
		ImageButton btn_11 = new ImageButton(Utils.IMG_LOGOUT_ICON, Utils.HINT_LEAVE_CONF, Utils.ACT_LOGOUT);
		dockbar.addIconAndFitInBar(btn_11);
	}
	
	private void createLayersPane() {
		layers_pane = new LayersPanel(this);
		drawable_pane.getLineLayerVisibilityProperty().bind(layers_pane.getLineLayerBtn().isOnProperty());
		drawable_pane.getMarkerLayerVisibilityProperty().bind(layers_pane.getMarkerLayerBtn().isOnProperty());
		drawable_pane.getAnnotationLayerVisibilityProperty().bind(layers_pane.getAnnotationLayerBtn().isOnProperty());
		
		Platform.runLater(() -> {
			double x_pos = dockbar.getLayoutX() + dockbar.getWidth() - layers_pane.getWidth();
			double y_pos = dockbar.getIcons().get(6).getLayoutY() + dockbar.getLayoutY();
			layers_pane.relocate(x_pos, y_pos);
		});
	}
	
	private void createScrollPane() {
		scroll_pane = new ScrollPane(drawable_pane);
		scroll_pane.setPrefSize(1362, 744);
		scroll_pane.setLayoutX(2.0); scroll_pane.setLayoutY(2.0);
		//scroll_pane.setFitToHeight(true); scroll_pane.setFitToWidth(true);
		scroll_pane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.getStylesheets().add("/styles/canvas_pane.css");
	}
	
	private void createDrawablePane() {
		drawable_pane = new DrawablePane(this);
	}
	
	private void createAnnotationPane() {
		annotation_pane = new AnnotationPane(this, rect_window_background.getLayoutBounds());
	}
	
	private void createAnnotationTextPane() {
		annotation_text_pane = new AnnotationTextPane(this, rect_window_background.getLayoutBounds());
	}
	
	private void createActionPane() {
		action_pane = new ActionPaneConf(this);
		action_pane.setLayoutX(2.0); action_pane.setLayoutY(746.0);
	}
	
	private void createMinimapPane() {
		minimap_pane = new MinimapPane(this);
		minimap_pane.relocate(1105.0, 564.0);
	}
	
	private void createButtons() {
		ibtn_microphone = new DoubleStateImageButton(Utils.IMG_MICROPHONE_ON, Utils.IMG_MICROPHONE_OFF, "mic", Utils.ACT_MICROPHONE);
		ibtn_microphone.setLayoutX(1300.0); ibtn_microphone.setLayoutY(100.0);

		ibtn_camera = new DoubleStateImageButton(Utils.IMG_CAMERA_ON, Utils.IMG_CAMERA_OFF, "cam", Utils.ACT_CAMERA);
		ibtn_camera.setLayoutX(1300.0); ibtn_camera.setLayoutY(160.0);
		

		ibtn_chat = new DoubleStateImageButton(Utils.IMG_CHAT, Utils.IMG_CHAT_NEW, "chat", Utils.ACT_CHAT);
		ibtn_chat.setLayoutX(1300.0); ibtn_chat.setLayoutY(220.0);
		ibtn_chat.removeAction();
		

		ibtn_members = new DoubleStateImageButton(Utils.IMG_MEMBERS, Utils.IMG_MEMBERS_NEW, "members", Utils.ACT_MICROPHONE);
		ibtn_members.setLayoutX(1300.0); ibtn_members.setLayoutY(280.0);
		ibtn_members.removeAction();
	}
	
	private void addElementsToScene() {
		root.getChildren().addAll(rect_window_background,
								scroll_pane,
								member_pane,
								chat_pane,
								layers_pane,
								dockbar,
								minimap_pane,
								action_pane,
								status_bar,
								annotation_pane,
								annotation_text_pane,
								ibtn_microphone,
								ibtn_camera,
								ibtn_chat,
								ibtn_members);
	}
	
	private void initializeLater() {
		Platform.runLater(() -> {
			drawable_pane.setViewportBounds(scroll_pane.getViewportBounds());
			drawable_pane.setImageAndResetCanvas(2099);
		});
	}

	
	public void addTextToStatusBar(String _text) {
		status_bar.addText(_text);
	}
	
	public void removeTextFromStatusBar() {
		status_bar.removeText();
	}
	
	public Point2D mapImagePosToScreenPos(Point2D _image_pos) {
		return conf_window.mapImagePosToScreenPos(_image_pos);
	}
	
	public void openCameraWindow(MemberCard _card) {
		conf_window.openCameraWindow(_card);
	}
}
