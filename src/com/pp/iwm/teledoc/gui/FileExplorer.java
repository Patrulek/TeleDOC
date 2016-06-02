package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.pp.iwm.teledoc.gui.ActionPane.PaneState;
import com.pp.iwm.teledoc.layouts.AppWindowLayout;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.layouts.WindowLayout;
import com.pp.iwm.teledoc.network.NetworkClient;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.network.User.DownloadListener;
import com.pp.iwm.teledoc.network.User.FileTreeListener;
import com.pp.iwm.teledoc.objects.File;
import com.pp.iwm.teledoc.objects.FileTree;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileExplorer extends Pane implements FileTreeListener, DownloadListener {
	
	// =========================================
	// FIELDS
	// =========================================

	private WindowLayout layout;
	private Pane header_pane;
	private ImageButton btn_back;
	private ImageButton btn_add_folder;
	private DoubleStateImageButton btn_my_files;
	private ImageButton btn_add_to_conf;
	private Label lbl_path;
	private Pane simple_pane;
	private ScrollPane scroll_pane;
	private ImageView image_preview;
	private String previewed_file_path;
	private File last_current_folder;
	private boolean is_returning_to_all_files;
	private boolean is_dragged;
	
	private FileTree file_tree;
	private FileCard selected_card;
	private FileCard hovered_card;
	private List<FileCard> files;
	
	private int max_cards_in_row;
	private double cards_gap;
	private double icon_size;
	
	// ==========================================
	// METHODS
	// ==========================================
	
	public FileExplorer(WindowLayout _layout) {
		max_cards_in_row = 0;
		cards_gap = 20.0;
		icon_size = 32.0;
		files = new ArrayList<>();
		layout = _layout;
		previewed_file_path = "";
		is_returning_to_all_files = true;
		is_dragged = false;
		
		createLayout();
		recalcMaxCardsInRow();
	}
	
	public void setDragged(boolean _is_dragged) {
		is_dragged = _is_dragged;
	}
	
	public boolean isDragged() {
		return is_dragged;
	}
	
	private void createLayout() {
		setPrefSize(759.0, 548.0);
		
		header_pane = new Pane();
		header_pane.setStyle("-fx-background-color: rgb(45, 81, 90);");
		header_pane.setPrefSize(759.0, 24.0);
		header_pane.setLayoutY(42.0);
		
		image_preview = new ImageView();
		image_preview.setVisible(false);
		image_preview.setFitWidth(759.0);
		image_preview.setFitHeight(420.0);
		
		simple_pane = new Pane();
		simple_pane.setStyle("-fx-background-color: rgb(30, 54, 60);");
		simple_pane.setPrefWidth(759.0);
		
		btn_back = new ImageButton(Utils.IMG_PARENT_FOLDER_SMALL, Utils.HINT_PARENT_FOLDER, Utils.ACT_PARENT_FOLDER);
		btn_back.customizeZoomAnimation(1.15, 1.0, 200, 200);
		btn_back.disableFadeAnimation();
		btn_back.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onBtnMouseEntered(btn_back));
		btn_back.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onBtnMouseExited(btn_back));
		btn_back.addEventHandler(ActionEvent.ACTION, ev -> onBtnMouseClicked(btn_back));
		
		if( layout instanceof AppWindowLayout )
			btn_add_folder = new ImageButton(Utils.IMG_ADD_FOLDER, Utils.HINT_ADD_FOLDER, Utils.ACT_ADD_FOLDER);
		else
			btn_add_folder = new ImageButton(Utils.IMG_UPLOAD_ICON_SMALL, Utils.HINT_UPLOAD_FILE, Utils.ACT_UPLOAD_FILE);
			
		btn_add_folder.customizeZoomAnimation(1.15, 1.0, 200, 200);
		btn_add_folder.disableFadeAnimation();
		btn_add_folder.addEventHandler(ActionEvent.ACTION, ev -> onBtnMouseClicked(btn_add_folder));
		btn_add_folder.addEventHandler(MouseEvent.MOUSE_ENTERED,  ev -> onBtnMouseEntered(btn_add_folder));
		btn_add_folder.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onBtnMouseExited(btn_add_folder));
		btn_add_folder.setLayoutX(64.0);
		
		if( layout instanceof ConfWindowLayout ) {
			btn_add_to_conf = new ImageButton(Utils.IMG_DOWNLOAD_ICON_SMALL, Utils.HINT_ADD_FILE_TO_CONF, Utils.ACT_ADD_FILE_TO_CONF);
			btn_add_to_conf.customizeZoomAnimation(1.15, 1.0, 200, 200);
			btn_add_to_conf.disableFadeAnimation();
			btn_add_to_conf.addEventHandler(ActionEvent.ACTION, ev -> onBtnMouseClicked(btn_add_to_conf));
			btn_add_to_conf.addEventHandler(MouseEvent.MOUSE_ENTERED,  ev -> onBtnMouseEntered(btn_add_to_conf));
			btn_add_to_conf.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onBtnMouseExited(btn_add_to_conf));
			btn_add_to_conf.setLayoutX(96.0);
		}
		
		btn_my_files = new DoubleStateImageButton(Utils.IMG_MY_FILES, Utils.IMG_ALL_FILES, Utils.HINT_MY_FILES, Utils.ACT_MY_FILES);
		btn_my_files.customizeZoomAnimation(1.15, 1.0, 200, 200);
		btn_my_files.disableFadeAnimation();
		btn_my_files.addEventHandler(ActionEvent.ACTION, ev -> onBtnMouseClicked(btn_my_files));
		btn_my_files.addEventHandler(MouseEvent.MOUSE_ENTERED,  ev -> onBtnMouseEntered(btn_my_files));
		btn_my_files.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onBtnMouseExited(btn_my_files));
		btn_my_files.setLayoutX(32.0);
		
		lbl_path = new Label("root/");
		lbl_path.setFont(Utils.LBL_STATUSBAR_FONT);
		lbl_path.setStyle("-fx-text-fill: rgb(180, 180, 240); -fx-font-weight: bold;");
		lbl_path.setMaxWidth(600.0); lbl_path.setPrefHeight(24.0);
		
		if( layout instanceof ConfWindowLayout )
			lbl_path.setLayoutX(138.0);
		else
			lbl_path.setLayoutX(106.0);
		
		header_pane.getChildren().add(btn_back);
		header_pane.getChildren().add(btn_my_files);
		header_pane.getChildren().add(btn_add_folder);
		
		if( layout instanceof ConfWindowLayout )
			header_pane.getChildren().add(btn_add_to_conf);
		
		header_pane.getChildren().add(lbl_path);
		
		scroll_pane = new ScrollPane(simple_pane);
		scroll_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll_pane.setLayoutY(66.0);
		scroll_pane.setPrefSize(759.0, 469.0);
		scroll_pane.getStylesheets().add("/styles/file_pane.css");
		scroll_pane.setOnMouseClicked(event -> onScrollPaneMouseClicked());
		
		getChildren().add(header_pane);
		getChildren().add(scroll_pane);
	}
	
	public void show() {
		setVisible(true);
	}
	
	public void hide() {
		setVisible(false);
	}
	
	public void showImagePreview() {
		System.out.println("Wyœwietl obraz");
		image_preview.toFront();
		image_preview.setVisible(true);
		Platform.runLater(() -> updateLabelPath());
	}
	
	public void hideImagePreview() {
		image_preview.setVisible(false);
	}
	
	// TODO hardcoded numbers
	private void recalcMaxCardsInRow() {
		max_cards_in_row = (int)(719.0 / (icon_size + cards_gap));
	}
	
	// TODO do optymalizacji
	public void refreshView() {
		clearView();
		refreshCurrentFolder();
		sortFiles();
		relocateFilesInExplorer();
		
		updateLabelPath();
	}
	
	private void refreshCurrentFolder() {
		System.out.println("FILE_TREE: "  + file_tree.getName() + " | CURRENT FOLDER: " + file_tree.getCurrentFolder().getPath());
		for( Entry<String, File> entry : file_tree.getFilesForCurrentFolder().entrySet() ) {
			FileCard fc1 = new FileCard(this, entry.getValue());
			files.add(fc1);
		}
	}
	
	private void clearView() {
		files.clear();
		simple_pane.getChildren().clear();
	}
	
	private void sortFiles() {
		java.util.Collections.sort(files, new Comparator<FileCard>() {
			@Override
			public int compare(FileCard fc1, FileCard fc2) {
				if( fc1.getFile().isFolder() == fc2.getFile().isFolder() ) 
					return fc1.getFile().getName().compareTo(fc2.getFile().getName());
				else {
					if( fc1.getFile().isFolder() && !fc2.getFile().isFolder() )
						return -1;
					else if( !fc1.getFile().isFolder() && fc2.getFile().isFolder() )
						return 1;
					return 0;
				}
			}
		});
	}
	
	private void relocateFilesInExplorer() {
		for( int i = 0; i < files.size(); i++ ) {
			double x = (i % max_cards_in_row) * (icon_size + cards_gap) + 40.0 ;
			double y = (i / max_cards_in_row) * (icon_size + cards_gap * 2);
			
			simple_pane.getChildren().add(files.get(i));
			files.get(i).setLayoutX(x); files.get(i).setLayoutY(y);
		}
		
		simple_pane.getChildren().add(image_preview);
	}
	
	public void onCardSelect(FileCard _selected_card) {
		if( selected_card != null ) 
			selected_card.setNormalStyle();
		
		selected_card = _selected_card;
		selected_card.setSelectionStyle();
	}
	
	public void onCardChoose(FileCard _choosed_card) {
		File choosed_file = _choosed_card.getFile();
		is_returning_to_all_files = true;
		
		if( choosed_file.isFolder() ) {
			file_tree.goIntoFolder(choosed_file);
			refreshView();
		} else {
			previewed_file_path = _choosed_card.getFile().getPath();
			User.instance().downloadFile(_choosed_card.getFile().getPath());
		}
	}
	
	public void onCardHover(FileCard _hovered_card) {
		hovered_card = _hovered_card;
	}

	public void addTextToStatusBar(String _text) {
		if( layout instanceof ConfWindowLayout )
			((ConfWindowLayout)layout).addTextToStatusBar(_text);
		else
			((AppWindowLayout)layout).addTextToStatusBar(_text);
	}
	
	public void removeTextFromStatusBar() {
		if( layout instanceof ConfWindowLayout )
			((ConfWindowLayout)layout).removeTextFromStatusBar();
		else
			((AppWindowLayout)layout).removeTextFromStatusBar();
	}
	
	private void onScrollPaneMouseClicked() {
		if( selected_card != null && hovered_card == null) {
			selected_card.setNormalStyle();
			selected_card = null;
		}
	}
	
	private void onBtnMouseClicked(ImageButton _ibtn) {
		switch( _ibtn.getAction() ) {
			case Utils.ACT_ADD_FOLDER:
				onAddFolderBtnAction();
				break;
			case Utils.ACT_MY_FILES:
				onMyFilesBtnAction();
				break;
			case Utils.ACT_PARENT_FOLDER:
				onBackBtnAction();
				break;
			case Utils.ACT_UPLOAD_FILE:
				onUploadFile();
				break;
			case Utils.ACT_ADD_FILE_TO_CONF:
				onAddToConf();
				break;
		}
	}
	
	private void onAddToConf() {
		if( selected_card != null ) {
			;//User.in
		}
	}
	
	private void onUploadFile() {
		openFileChooser();
	}
	
	private void openFileChooser() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Wybierz plik, który chcesz wgraæ na server");
		fc.getExtensionFilters().add(new ExtensionFilter("Pliki obrazów", "*.png", "*.jpg"));
		java.io.File selected_file = fc.showOpenDialog(layout.stage);
		
		if( selected_file != null ) {
			if( Utils.isFileSizeGreaterThan(selected_file, NetworkClient.FILESIZE_LIMIT) )
				JOptionPane.showMessageDialog(null, "Maksymalny rozmiar pliku wynosi " + NetworkClient.FILESIZE_LIMIT + " MB"); // TODO messagebox przystosowany do appki
			else
				startUploading(selected_file);
		}
	}
	
	private void startUploading(java.io.File _file) {
		double filesize = _file.length() / Utils.BYTES_PER_MEGABYTE;
		filesize = Math.round(filesize * 10.0) / 10.0;
		System.out.println("Przesy³anie pliku: " + _file.getName() + " o rozmiarze " + filesize + " MB"); // TODO uzupelnic
		User.instance().sendImage(_file);
	}
	
	private void onAddFolderBtnAction() {
		if( image_preview.isVisible() || file_tree.getName().equals("group_files") || file_tree.getName().equals("my_files") )
			return;
		
		if( layout instanceof AppWindowLayout )
			((AppWindowLayout)layout).action_pane.changeStateAndRefresh(PaneState.ADD_FOLDER);
	}
	
	private void onMyFilesBtnAction() {
		if( image_preview.isVisible() ) {
			hideImagePreview();
			previewed_file_path = "";
			lbl_path.setText("~/my_files/");
		} 
		
		if( btn_my_files.isOnProperty().get() ) {
			User.instance().showAllFiles();
		} else
			User.instance().showMyFiles();
		
		Platform.runLater(() -> refreshView());
	}
	
	private void onBackBtnAction() {
		if( image_preview.isVisible() ) {
			hideImagePreview();
			previewed_file_path = "";
		} else if( file_tree.getName().equals("group_files") ) {
			is_returning_to_all_files = true;
			onMyFilesBtnAction();
		} else if( file_tree.hasCurrentFolderAParent() ) {
			if( selected_card != null ) {
				selected_card = null;
				removeTextFromStatusBar();
			}

			file_tree.goParentFolderIfExist();
			refreshView();
		}
		
		if( file_tree.getName().equals("my_files") ) {
			if( file_tree.hasCurrentFolderAParent() ) {
				if( selected_card != null ) {
					selected_card = null;
					removeTextFromStatusBar();
				}

				file_tree.goParentFolderIfExist();
				refreshView();
			}
		}
	}
	
	private void updateLabelPath() {
		String s = file_tree.getName().equals("my_files") ? "~/my_files/" : file_tree.getName().equals("group_files") ? "~/group_files/" : "";
		lbl_path.setText(s + file_tree.getCurrentFolder().getPath());
	}
	
	private void onBtnMouseEntered(ImageButton _ibtn) {
		addTextToStatusBar(_ibtn.getHint());
	}
	
	private void onBtnMouseExited(ImageButton _ibtn) {
		removeTextFromStatusBar();
	}
	
	public void setFileTree(FileTree _file_tree) {
		file_tree = _file_tree;
	}
	
	public FileCard getSelectedCard() {
		return selected_card;
	}

	@Override
	public void onFileTreeChanged(FileTree _file_tree) {
		file_tree = _file_tree;
		Platform.runLater(() -> refreshView());
	}

	@Override
	public void onFileTreePreviewChanged(FileTree _file_tree) {
		if( _file_tree == file_tree )
			return;
		
		System.out.println("STARY: " + file_tree.getName() + " | NOWY: " + _file_tree.getName());
		
		if( file_tree.getName().equals("all_files") )
			last_current_folder = file_tree.getCurrentFolder();
		
		file_tree = _file_tree;
		if( file_tree.getName().equals("group_files") ) {
			file_tree.setCurrentAsRoot();
		} else if( file_tree.getName().equals("my_files") ) {
			file_tree.setCurrentAsRoot();
		} else {
			if( is_returning_to_all_files ) {
				file_tree.setCurrentFolder(last_current_folder);
				is_returning_to_all_files = false;
			}
		}
		
		Platform.runLater(() -> {
			if( file_tree.getName().equals("group_files") ) {
				if( image_preview.isVisible() )
					hideImagePreview();
				
			} else if( file_tree.getName().equals("my_files") ) {
				;
			}
			
			refreshView();
		});
	}
	

	@Override
	public void onDownloadBegin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDownloadProgress() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDownloadFinish() {
		Platform.runLater(() -> lbl_path.setText(previewed_file_path));
		setLastDownloadedFileInPreview();
		showImagePreview();
	}
	
	private void setLastDownloadedFileInPreview() {
		image_preview.setImage(ImageManager.instance().getLastLoadedImage());
	}
}
