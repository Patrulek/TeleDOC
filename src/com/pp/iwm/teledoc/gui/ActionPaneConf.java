package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.animations.TranslateAnimation;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.objects.ObjectId;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

// TODO zmergowac z ActionPane
public class ActionPaneConf extends Pane implements ChangeListener<Boolean> {
	
	// =========================================
	// FIELDS
	// =========================================
	
	private ConfWindowLayout layout;
	private Pane content_pane;
	private ImageButton btn_hide;
	
	private TranslateAnimation translate_animation;
	private FadeAnimation fade_animation;
	private FadeAnimation fade_animation_content_pane;
	
	private ThumbnailPanel thumbnail_panel;
	private Thread opacity_thread;
	
	private boolean is_visible;
	private PaneState pane_state;
	private double last_active;
	
	private Pane color_preview[];
	
	public enum PaneState {
		UNDEFINED, DRAW_LINE, LOADED_IMAGES
	}
	
	// ===========================================
	// METHODS
	// ===========================================
	
	public ActionPaneConf(ConfWindowLayout _layout) {
		is_visible = false;
		pane_state = PaneState.UNDEFINED;
		layout = _layout;
		color_preview = new Pane[5];
			
		createLayout();
		addAnimations();
		launchOpacityThread();
	}
	
	private void createLayout() {
		setStyle("-fx-background-color: rgb(15, 27, 30);");
		setPrefSize(1362.0, 60.0);
		setOpacity(0.0);
		hoverProperty().addListener(this);
		
		content_pane = new Pane();
		content_pane.setStyle("-fx-background-color: transparent;");
		content_pane.setPrefSize(1362.0, 60.0);
		
		btn_hide = new ImageButton(Utils.IMG_HIDE_PANEL_SMALL, Utils.HINT_HIDE_PANEL, Utils.ACT_HIDE_PANEL);
		btn_hide.setLayoutX(1323.0); btn_hide.setLayoutY(5.0);
		btn_hide.disableFadeAnimation();
		btn_hide.customizeZoomAnimation(1.15, 1.0, 250, 400);
		btn_hide.addEventHandler(ActionEvent.ACTION, ev -> onHideBtnAction(ev));
		btn_hide.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onHideBtnMouseEntered(ev));
		btn_hide.addEventHandler(MouseEvent.MOUSE_EXITED, ev-> onHideBtnMouseExited(ev));
		
		thumbnail_panel = new ThumbnailPanel(layout);

		getChildren().add(content_pane);
		getChildren().add(btn_hide);
	}
	
	private void addAnimations() {
		translate_animation = new TranslateAnimation(this);
		translate_animation.customize(0, -60, 300, 450);
		
		fade_animation = new FadeAnimation(this);
		fade_animation.customize(1.0, 0.0, 550, 150);
		
		fade_animation_content_pane = new FadeAnimation(content_pane);
		fade_animation_content_pane.customize(1.0, 0.0, 200, 150);
	}
	
	public void changePaneStateAndRefresh(PaneState _pane_state) {
		if( pane_state != _pane_state ) {
			pane_state = _pane_state;
			recreate();
		}
		
		show();
	}
	
	public void addThumbnailForImage(int _image_id) {
		thumbnail_panel.addThumbnailForImage(_image_id);
	}
	
	public void show() {
		fade_animation.customize(1.0, 0.0, 550, 150);
		fade_animation.playForward();
		translate_animation.playForward();
		last_active = System.currentTimeMillis();
		
		if( !is_visible ) {
			is_visible = true;
			//((ConfWindow)window).addHidePanelIcon();
		}
	}
	
	public void hide() {
		fade_animation.customize(1.0, 0.0, 550, 150);
		fade_animation.playBackward();
		translate_animation.playBackward();
		
		if( is_visible ) {
			is_visible = false;
			//((AppWindow)window).removeHidePanelIcon();
		}
	}
	
	private void recreate() {
		fade_animation_content_pane.playBackward();
		fade_animation_content_pane.setOnFinished(ev -> changePanel());
	}
	
	private void changePanel() {
		content_pane.getChildren().clear();
		
		switch( pane_state ) {
			case DRAW_LINE:
				createNewDrawLinePanel();
				break;
			case LOADED_IMAGES:
				createLoadedImagesPanel();
				break;
				
			default:
		}
		
		fade_animation_content_pane.setOnFinished(null);
		fade_animation_content_pane.playForward();
	}
	
	private void createLoadedImagesPanel() {
		thumbnail_panel.loadThumbnails();
		content_pane.getChildren().add(thumbnail_panel);
	}
	
	private void createNewDrawLinePanel() {
		if( color_preview[0] != null ) {
			for( int i = 0; i < 5; i++ )
				content_pane.getChildren().add(color_preview[i]);
			
			return;
		}
		
		for( int i = 0; i < 5; i++ )  {
			color_preview[i] = new Pane();
			color_preview[i].setLayoutX(100.0 + i * 32.0); color_preview[i].setLayoutY(14.0);
			color_preview[i].setPrefSize(32.0, 32.0);
			color_preview[i].setOnMouseClicked(ev -> onColorChanged(ev));
			content_pane.getChildren().add(color_preview[i]);
		}
		
		color_preview[0].setStyle("-fx-background-color: red;");
		color_preview[1].setStyle("-fx-background-color: green;");
		color_preview[2].setStyle("-fx-background-color: blue;");
		color_preview[3].setStyle("-fx-background-color: yellow;");
		color_preview[4].setStyle("-fx-background-color: orange;");
	}
	
	private void onBtnAction(Button _btn) {
		if( _btn.getText().equals("Stwórz") ) 
			onCreateConfAction();
	}
	
	private void onCreateConfAction() {
		// TODO: zmieniæ 
					// wys³aæ zapytanie do servera
					// odebraæ wiadomoœæ od servera
					// zmieniæ okno
					// wyœwietliæ error jeœli nie uda³o siê nawi¹zaæ po³¹czenia
	}
	
	private void onBtnEntered(ImageButton _ibtn) {
		//((ConfWindow)window).addTextToStatusBar(_ibtn.getHint());
	}
	
	private void onBtnExited(ImageButton _ibtn) {
		//((ConfWindow)window).removeTextFromStatusBar();
	}
	
	private void onHideBtnMouseEntered(MouseEvent _ev) {
		//((ConfWindow)window).addTextToStatusBar(btn_hide.getHint());
	}
	
	private void onHideBtnMouseExited(MouseEvent _ev) {
		//((ConfWindow)window).removeTextFromStatusBar();
	}
	
	private void onHideBtnAction(ActionEvent _ev) {
		hide();
	}
	
	private void onColorChanged(MouseEvent _ev) {
		int i = (int) ((_ev.getSceneX() - 100.0) / 32.0);
		
		Color c = i == 0 ? Color.RED : i == 1 ? Color.GREEN : i == 2 ? Color.BLUE : i == 3 ? Color.YELLOW : Color.ORANGE;
		//((ConfWindow)window).setCurrentColor(c);
	}

	@Override	// TODO potrzeba dostroiæ
	public void changed(ObservableValue<? extends Boolean> _observable, Boolean _old, Boolean _new) {
		if( !_old && _new ) {
			fade_animation.customize(1.0, getOpacity(), 400, 400);
			fade_animation.playForward();
		} else if( _old && !_new ) {
			last_active = System.currentTimeMillis();
		}
	}
	
	public void stopOpacityThread() {
		opacity_thread.interrupt();
	}
	
	private void launchOpacityThread() {
		if( opacity_thread == null )
			opacity_thread = new Thread(() -> {
				while( !opacity_thread.isInterrupted() ) {
					//System.out.println(last_active);
					double time = last_active + 1;
						
					while( System.currentTimeMillis() - time < 2500.0 ) {
						try { Thread.sleep(50); } 
						catch (InterruptedException e) { return; }
					}
						
					if( last_active > time || isHover() )
						continue;
					
					if( is_visible ) {
						fade_animation.stop();
						fade_animation.customize(0.33, getOpacity(), 700, 700);
						fade_animation.playForward();
					}
					
					while( time == last_active + 1 ) {
						try { Thread.sleep(50); }
						catch (InterruptedException e) { return; }
					}
				}
			});
			
		opacity_thread.start();
	}

	public void notifyThumbnailPanel(ObjectId _id) {
		thumbnail_panel.imageChanged(_id);
	}
}
