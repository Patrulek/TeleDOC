package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.ImageManager;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ThumbnailPanelCard extends Pane {
	
	// ==============================
	// FIELDS
	// ==============================
	
	private ImageView image_view;
	private Integer image_key;
	private ThumbnailPanel thumbnail_pane;
	private boolean is_selected;
	private boolean is_active;
	private boolean is_changed;
	
	// ==============================
	// METHODS
	// ==============================
	
	public ThumbnailPanelCard(ThumbnailPanel _pane, Integer _image_key) {
		super();
		thumbnail_pane = _pane;
		image_key = _image_key;
		is_selected = is_active = is_changed = false;
		
		createLayout();
		setHandlers();
	}
	
	private void createLayout() {
		setPrefSize(54.0, 54.0);
		setMaxSize(54.0, 54.0);
		setStyle("-fx-background-color: transparent;");
		updateView();
		
		image_view = new ImageView();
		image_view.setPreserveRatio(false);
		image_view.setLayoutX(2.0); image_view.setLayoutY(2.0);
		image_view.setFitHeight(50.0); image_view.setFitWidth(50.0);
		image_view.setImage(ImageManager.instance().getImage(image_key));
		
		getChildren().add(image_view);
	}
	
	private void setHandlers() {
		setOnMouseClicked(ev -> thumbnail_pane.onCardSelect(this));
	}
	
	public void setSelectedAndUpdateView(boolean _is_selected) {
		is_selected = _is_selected;
		updateView();
	}
	
	public void setActiveAndUpdateView(boolean _is_active) {
		is_active = _is_active;
		updateView();
	}
	
	public void setChangedAndUpdateView(boolean _is_changed) {
		is_changed = _is_changed;
		updateView();
	}
	
	private void updateView() {
		if( is_active )
			setStyle("-fx-background-color: rgb(100, 205, 100);");
		else if( is_selected )
			setStyle("-fx-background-color: rgb(100, 100, 205);");
		else if( is_changed )
			setStyle("-fx-background-color: rgb(205, 100, 100);");
		else
			setStyle("-fx-background-color: transparent;");
	}
}
