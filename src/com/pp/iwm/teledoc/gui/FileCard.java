package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.File;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class FileCard extends VBox {
	
	public FileExplorer file_explorer = null;
	public File file = null;
	public ImageView iv_icon = null;
	public Label lbl_name = null;
	
	public FileCard(FileExplorer file_explorer, File file) {
		this.file_explorer = file_explorer;
		this.file = file;
		
		iv_icon = file.is_folder ? new ImageView("/assets/folder_icon.png") : new ImageView("/assets/image_icon.png");
		
		lbl_name = new Label(file.name);
		lbl_name.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_name.setStyle("-fx-text-fill: rgb(182, 182, 182);");
		lbl_name.setMaxWidth(32.0); lbl_name.setMaxHeight(40.0);
		lbl_name.setWrapText(true);
		lbl_name.setAlignment(Pos.TOP_CENTER);
		
		getChildren().add(iv_icon);
		getChildren().add(lbl_name);
		
		this.setOnMouseClicked(event -> onMouseClicked(event));
		this.setOnMouseEntered(event -> onMouseEntered());
		this.setOnMouseExited(event -> onMouseExited());
	}
	
	public void onMouseClicked(MouseEvent event) {
		if( event.getClickCount() == 1 )
			file_explorer.onCardSelect(this);
		else if( event.getClickCount() == 2 )
			file_explorer.onCardChoose(this);
	}
	
	public void onMouseEntered() {
		file_explorer.onCardHover(this);
	}
	
	public void onMouseExited() {
		file_explorer.onCardHover(null);
	}
	
	public void selectCard() {
		lbl_name.setStyle("-fx-background-color: rgb(69, 90, 100); -fx-text-fill: rgb(182, 182, 182);");
		file_explorer.status_bar.addText(lbl_name.getText());
	}
	
	public void deselectCard() {
		lbl_name.setStyle("-fx-background-color: rgb(0, 0, 0, 0); -fx-text-fill: rgb(182, 182, 182);");
		file_explorer.status_bar.removeText();
	}
}
