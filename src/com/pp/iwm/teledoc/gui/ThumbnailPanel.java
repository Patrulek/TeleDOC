package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.objects.ObjectId;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ThumbnailPanel extends Pane {
	
	// =====================================
	// FIELDS 
	// =====================================
	
	private ConfWindowLayout layout;
	
	private ScrollPane scroll_pane;
	private HBox content_pane;
	private ThumbnailPanelCard hovered_card;
	private ThumbnailPanelCard selected_card;
	
	private List<ThumbnailPanelCard> thumbnails;
	
	// =====================================
	// METHODS
	// =====================================
	
	public ThumbnailPanel(ConfWindowLayout _layout) {
		super();
		layout = _layout;
		thumbnails = new ArrayList<>();
		
		createLayout();
	}
	
	private void createLayout() {
		setPrefSize(1300.0, 60.0);
		setStyle("-fx-background-color: transparent;");
		
		content_pane = new HBox();
		content_pane.setSpacing(20.0);
		content_pane.setPrefHeight(60.0);
		content_pane.setPadding(new Insets(2.0, 0.0, 2.0, 20.0));
		
		scroll_pane = new ScrollPane(content_pane);
		scroll_pane.setPrefSize(1300.0, 60.0);
		scroll_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.setVbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
		
		getChildren().add(scroll_pane);
	}
	
	public void loadThumbnails() {
		Map<Integer, Integer> images = User.instance().getUsedImages();
		content_pane.getChildren().clear();
		int i = 0;
		
		for( Entry<Integer, Integer> entry : images.entrySet() ) {
			int image_id = entry.getValue();
			
			if( findCardForImageId(image_id) == null )
				addThumbnailForImage(image_id, i);
			else
				content_pane.getChildren().add(findCardForImageId(image_id));
			
			i++;
		}
	}
	
	private ThumbnailPanelCard findCardForImageId(int _image_id) {
		for( ThumbnailPanelCard card : thumbnails )
			if( card.getImageKey() == _image_id )
				return card;
		
		return null;
	}

	public void addThumbnailForImage(int _image_id, int _i) {
		for( ThumbnailPanelCard card : thumbnails )
			if( card.getImageKey() == _image_id )
				return;
		
		double x_pos = 32.0 * _i;
		
		ThumbnailPanelCard card = new ThumbnailPanelCard(this, _image_id);
		boolean is_active = User.instance().getCurrentImage() == _image_id;
		card.setActiveAndUpdateView(is_active);
		thumbnails.add(card);
		card.setLayoutX(x_pos);
		content_pane.getChildren().add(card);
		
		if( is_active )
			selected_card = card;
	}

	public void addThumbnailForImage(int _image_id) {
		addThumbnailForImage(_image_id, thumbnails.size());
	}
	
	public void onCardSelect(ThumbnailPanelCard _card) {
		if( selected_card != null )
			selected_card.setActiveAndUpdateView(false);
		
		User.instance().setCurrentImage(_card.getImageKey());
		selected_card = _card;
		selected_card.setActiveAndUpdateView(true);
	}

	public void imageChanged(ObjectId _id) {
		if( _id.image_id != User.instance().getCurrentImage() ) {
			ThumbnailPanelCard card = findCardForImageId(_id.image_id);
			
			if( card != null ) {
				System.out.println(_id.image_id + " | " + User.instance().getCurrentImage() + " : <-- image changed");
				
				card.setActiveAndUpdateView(false);
				card.setChangedAndUpdateView(true);
			}
				//Platform.runLater(() -> card.setChangedAndUpdateView(true));
		}
	}
}
