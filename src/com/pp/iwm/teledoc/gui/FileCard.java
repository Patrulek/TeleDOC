package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.File;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class FileCard extends VBox {

	// ======================================
	// FIELDS
	// ======================================
	
	private ImageView iv_icon;
	private Label lbl_name;
	private FileExplorer file_explorer;
	private File file;
	
	// ==========================================
	// METHODS
	// ==========================================
	
	public FileCard(FileExplorer _file_explorer, File _file) {
		file_explorer = _file_explorer;
		file = _file;
		
		createLayout();
		setHandlers();
	}
	
	private void createLayout() {
		iv_icon = file.isFolder() ? new ImageView("/assets/folder_icon.png") : new ImageView("/assets/image_icon.png");
		
		lbl_name = new Label(file.getName());
		lbl_name.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_name.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_name.setMaxWidth(32.0); lbl_name.setMaxHeight(40.0);
		lbl_name.setWrapText(true);
		lbl_name.setAlignment(Pos.TOP_CENTER);
		
		getChildren().add(iv_icon);
		getChildren().add(lbl_name);
	}
	
	private void setHandlers() {
		setOnMouseClicked(ev -> onMouseClicked(ev));
		setOnMouseEntered(ev -> onMouseEntered());
		setOnMouseExited(ev -> onMouseExited());
	}
	
	public void setSelectionStyle() {
		lbl_name.setStyle("-fx-background-color: rgb(15, 27, 30); -fx-text-fill: rgb(140, 140, 170);");
		file_explorer.addTextToStatusBar(lbl_name.getText());
	}
	
	public void setNormalStyle() {
		lbl_name.setStyle("-fx-background-color: rgb(0, 0, 0, 0); -fx-text-fill: rgb(160, 160, 200);");
		file_explorer.removeTextFromStatusBar();
	}
	
	private void onMouseClicked(MouseEvent _ev) {
		if( _ev.getClickCount() == 1 )
			file_explorer.onCardSelect(this);
		else if( _ev.getClickCount() == 2 )
			file_explorer.onCardChoose(this);
	}
	
	private void onMouseEntered() {
		file_explorer.onCardHover(this);
	}
	
	private void onMouseExited() {
		file_explorer.onCardHover(null);
	}
	
	public File getFile() {
		return file;
	}
}
