package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import com.pp.iwm.teledoc.objects.File;
import com.pp.iwm.teledoc.objects.FileTree;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;

public class FileExplorer extends Pane {
	
	FileTree file_tree = null;
	Label lbl_user = null;
	StatusBar status_bar = null;
	ScrollPane scroll_pane = null;
	Pane simple_pane = null;
	FileCard selected_card = null;
	FileCard hovered_card = null;
	ImageButton btn_back = null;
	Label lbl_path = null;
	List<FileCard> files = null;
	int max_cards_in_row = 0;
	double cards_gap = 20.0;
	
	public FileExplorer(StatusBar status_bar) {
		this.setPrefWidth(804.0);
		this.setPrefHeight(580.0);
		this.setLayoutX(220.0);
		this.status_bar = status_bar;
		
		
		files = new ArrayList<>();
		calcMaxCardsInRow();
		
		lbl_user = new Label("Patryk Lewandowski (patryk.jan.lewandowski@gmail.com)");
		lbl_user.setStyle("-fx-text-fill: rgb(182, 182, 182); -fx-font-weight: normal;");
		lbl_user.setFont(Utils.LBL_FONT);
		lbl_user.setLayoutX(20.0); lbl_user.setLayoutY(12.0);
		
		
		simple_pane = new Pane();
		simple_pane.setStyle("-fx-background-color: rgb(96, 125, 139, 1.0);");
		simple_pane.setPrefWidth(767.0);
		
		btn_back = new ImageButton("/assets/logout.png");
		btn_back.setScaleX(0.4); btn_back.setScaleY(0.4);
		btn_back.setOpacity(0.5);
		btn_back.setLayoutX(-5.0);
		btn_back.customizeZoomAnimation(0.5, 0.4, 200, 200);
		btn_back.customizeFadeAnimation(1.0, 0.5, 200, 200);
		btn_back.setOnMouseEntered(event -> onBtnMouseEntered(btn_back));
		btn_back.setOnMouseExited(event -> onBtnMouseExited(btn_back));
		btn_back.setOnMouseClicked(event -> onBtnMouseClicked(btn_back));
		
		lbl_path = new Label("root/");
		lbl_path.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_path.setStyle("-fx-text-fill: rgb(182, 182, 182);");
		lbl_path.setMaxWidth(600.0);
		lbl_path.setLayoutX(52.0); lbl_path.setLayoutY(13.0);
		
		simple_pane.getChildren().add(btn_back);
		simple_pane.getChildren().add(lbl_path);
		
		
	
		scroll_pane = new ScrollPane(simple_pane);
		scroll_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll_pane.setLayoutX(15.0); scroll_pane.setLayoutY(42.0);
		scroll_pane.setPrefWidth(767.0); scroll_pane.setPrefHeight(493.0);
		scroll_pane.setStyle("-fx-background: rgb(96, 125, 139, 1.0); -fx-background-color: rgb(69, 90, 100, 1.0);");
		scroll_pane.setOnMouseClicked(event -> onScrollPaneMouseClicked());
		
		getChildren().add(lbl_user);
		getChildren().add(scroll_pane);
		
		file_tree = new FileTree();
		refreshView();
	}
	
	public void onCardSelect(FileCard selected_card) {
		if( this.selected_card != null ) 
			this.selected_card.deselectCard();
		
		this.selected_card = selected_card;
		this.selected_card.selectCard();
	}
	
	
	public void refreshView() {
		files.clear();
		simple_pane.getChildren().clear();
		simple_pane.getChildren().add(btn_back);
		simple_pane.getChildren().add(lbl_path);
		int j = 0;
		
		for( Entry<String, File> entry : file_tree.current_root.children.entrySet() ) {
			FileCard fc1 = new FileCard(this, entry.getValue());
			files.add(fc1);
		}
		
		java.util.Collections.sort(files, new Comparator<FileCard>() {
			@Override
			public int compare(FileCard fc1, FileCard fc2) {
				if( fc1.file.is_folder == fc2.file.is_folder ) 
					return fc1.file.name.compareTo(fc2.file.name);
				else {
					if( fc1.file.is_folder && !fc2.file.is_folder )
						return -1;
					else if( !fc1.file.is_folder && fc2.file.is_folder )
						return 1;
					return 0;
				}
			}
		});
		
		for( int i = 0; i < files.size(); i++ ) {
			double x = (i % max_cards_in_row) * 52.0 + 50.0;
			double y = (i / max_cards_in_row) * 80.0 + 40.0;
			
			simple_pane.getChildren().add(files.get(i));
			files.get(i).setLayoutX(x); files.get(i).setLayoutY(y);
		}
	}
	
	public void onCardChoose(FileCard choosed_card) {
		String str = choosed_card.lbl_name.getText();
		
		if( choosed_card.file.is_folder )
			if( file_tree.current_root.children != null && !file_tree.current_root.children.isEmpty() ) {
				file_tree.current_root = file_tree.current_root.children.get(str);
				updateLabelPath();
				refreshView();
			}
		else
			; // wyœwietl plik
	}
	
	public void onCardHover(FileCard hovered_card) {
		this.hovered_card = hovered_card;
	}
	
	private void calcMaxCardsInRow() {
		max_cards_in_row = (int)(719.0 / (32.0 + cards_gap));
	}
	
	private void onScrollPaneMouseClicked() {
		if( selected_card != null && hovered_card == null) 
			this.selected_card.deselectCard();
	}
	
	private void onBtnMouseClicked(ImageButton btn) {
		if( btn == btn_back ) {
			file_tree.current_root = file_tree.current_root.parent != null ? file_tree.current_root.parent : file_tree.current_root;
			updateLabelPath();
			refreshView();
		}
	}
	
	private void updateLabelPath() {
		lbl_path.setText(file_tree.current_root.path);
	}
	
	private void onBtnMouseEntered(ImageButton btn) {
		btn.onMouseEntered();
		
		if( btn == btn_back )
			status_bar.addText("W górê");
	}
	
	private void onBtnMouseExited(ImageButton btn) {
		btn.onMouseExited();
		status_bar.removeText();
	}
}
