package com.pp.iwm.teledoc.windows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;
import com.pp.iwm.teledoc.gui.ActionPane.PaneState;
import com.pp.iwm.teledoc.gui.ConferenceCard;
import com.pp.iwm.teledoc.gui.Dockbar;
import com.pp.iwm.teledoc.gui.FileExplorer;
import com.pp.iwm.teledoc.gui.ImageButton;
import com.pp.iwm.teledoc.gui.StatusBar;
import com.pp.iwm.teledoc.layouts.AppWindowLayout;
import com.pp.iwm.teledoc.models.AppWindowModel;
import com.pp.iwm.teledoc.network.NetworkClient;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.network.User.NetworkListener;
import com.pp.iwm.teledoc.network.User.State;
import com.pp.iwm.teledoc.network.packets.AllGroupsResponse;
import com.pp.iwm.teledoc.network.packets.CreateGroupResponse;
import com.pp.iwm.teledoc.network.packets.Group;
import com.pp.iwm.teledoc.network.packets.JoinToGroupResponse;
import com.pp.iwm.teledoc.network.packets.images.ConfirmSendImageResponse;
import com.pp.iwm.teledoc.network.packets.images.GetAllImagesDescriptionResponse;
import com.pp.iwm.teledoc.network.packets.images.ImageDescription;
import com.pp.iwm.teledoc.objects.Conference;
import com.pp.iwm.teledoc.objects.FileTree;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AppWindow extends Window implements NetworkListener {
	
	// ===========================================
	// FIELDS
	// ===========================================
	
	private AppWindowModel window_model;
	private AppWindowLayout window_layout;
	
	// ===========================================
	// METHODS
	// ===========================================
	
	public AppWindow() {
		super();
		User.instance().setListener(this);
		User.instance().setFileTreeListener(window_layout.file_pane);
		User.instance().loadDataFromDB();
	}
	
	public void addConf(Conference _conf) {
		window_layout.conf_pane.addConf(_conf);
	}
	
	public void removeConf(Conference _conf) {
		window_layout.conf_pane.removeConf(_conf);
	}
	
	public void setFileExplorerRoot(FileTree _file_tree) {
		window_layout.file_pane.setFileTree(_file_tree);
	}
	
	public void refreshFileExplorerView() {
		window_layout.file_pane.refreshView();
	}
	
	private void onCreateNewConference(boolean _from_file) {
		window_layout.action_pane.changeStateAndRefresh(PaneState.NEW_CONF);
	}
	
	private void onSearchConference() {
		window_layout.action_pane.changeStateAndRefresh(PaneState.SEARCH_CONF);
	}
	
	private void onUploadFile() {
		openFileChooser();
	}
	
	private void openFileChooser() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Wybierz plik, który chcesz wgraæ na server");
		fc.getExtensionFilters().add(new ExtensionFilter("Pliki obrazów", "*.png", "*.jpg"));
		File selected_file = fc.showOpenDialog(window_layout.stage);
		
		if( selected_file != null ) {
			if( Utils.isFileSizeGreaterThan(selected_file, NetworkClient.FILESIZE_LIMIT) )
				JOptionPane.showMessageDialog(null, "Maksymalny rozmiar pliku wynosi " + NetworkClient.FILESIZE_LIMIT + " MB"); // TODO messagebox przystosowany do appki
			else
				startUploading(selected_file);
		}
	}
	
	private void startUploading(File _file) {
		double filesize = _file.length() / Utils.BYTES_PER_MEGABYTE;
		filesize = Math.round(filesize * 10.0) / 10.0;
		System.out.println("Przesy³anie pliku: " + _file.getName() + " o rozmiarze " + filesize + " MB"); // TODO uzupelnic
		User.instance().sendImage(_file);
	}
	
	private void onDownloadFile() {
		// download file
	}
	
	private void onSearchFile() {
		window_layout.action_pane.changeStateAndRefresh(PaneState.SEARCH_FILE);
	}
	
	private void onShowHelp() {
		// show help popup window
	}
	
	private void onLogout() {
		User.instance().logOut();
		Platform.runLater(() -> openWindowAndHideCurrent(new LoginWindow()));
	}
	
	private void onWindowBackgroundMousePressed(MouseEvent _ev) {
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}
	
	private void onWindowBackgroundMouseDragged(MouseEvent _ev) {
		Stage stage = window_layout.stage;
		Point2D mouse_pos = window_model.getMousePos();
		
		if( _ev.getSceneY() < 24  || window_model.isDragged() ) {
			window_model.setDragged(true);
			stage.setX(stage.getX() + _ev.getScreenX() - mouse_pos.getX());
			stage.setY(stage.getY() + _ev.getScreenY() - mouse_pos.getY());
		}
		
		window_model.setMousePos(new Point2D(_ev.getScreenX(), _ev.getScreenY()));
	}
	
	private void onWindowBackgroundMouseReleased(MouseEvent ev) {
		window_model.setDragged(false);
	}
	
	private void onDockbarMouseMoved(MouseEvent _ev) {
		Dockbar dockbar = window_layout.dockbar;
		FileExplorer file_pane = window_layout.file_pane;
		
		int selected_icon = dockbar.getHoveredIconIndex();
		int old_selected_icon = dockbar.getOldHoveredIconIndex();
		List<ImageButton> all_icons = dockbar.getIcons();
		
		if( old_selected_icon != selected_icon ) {
			window_layout.removeTextFromStatusBar();
			
			if( selected_icon >= 0 ) {
				if( all_icons.get(selected_icon).getHint().equals(Utils.HINT_CREATE_CONF_FROM_FILE)
					&& file_pane.getSelectedCard() != null && !file_pane.getSelectedCard().getFile().isFolder() )
					window_layout.addTextToStatusBar(all_icons.get(selected_icon).getHint() + file_pane.getSelectedCard().getFile().getName());
				else
					window_layout.addTextToStatusBar(all_icons.get(selected_icon).getHint());
			}
		}
	}
	
	private void onDockbarMouseEntered(MouseEvent _ev) {
		window_layout.addTextToStatusBar("");
	}
	
	private void onDockbarMouseExited(MouseEvent _ev) {
		window_layout.dockbar.resetHoveredIndex();
		window_layout.removeTextFromStatusBar();
	}

	@Override
	protected void createLayout() {
		window_layout = new AppWindowLayout(this);
		layout = window_layout;
	}


	@Override
	protected void createModel() {
		window_model = new AppWindowModel(this);
		model = window_model;
	}
	
	private void onActionPaneHideBtnMouseEntered(MouseEvent _ev) {
		window_layout.addTextToStatusBar(window_layout.action_pane.getHideBtn().getHint());
	}
	
	private void onActionPaneHideBtnMouseExited(MouseEvent _ev) {
		window_layout.removeTextFromStatusBar();
	}
	
	private void onActionPaneHideBtnAction(ActionEvent _ev) {
		window_layout.action_pane.hide();
	}
	
	private void onActionPaneActionBtnAction(ImageButton _ibtn) {
		PaneState state = window_layout.action_pane.getState();
		
		if( state == PaneState.NEW_CONF )
			User.instance().createNewConference(window_layout.action_pane.getConfTitle());
	}
	
	private void onActionPaneActionBtnEntered(ImageButton _ibtn) {
		window_layout.addTextToStatusBar(_ibtn.getHint());
	}
	
	private void onActionPaneActionBtnExited(ImageButton _ibtn) {
		window_layout.removeTextFromStatusBar();
	}
	
	@Override
	public void hide() {
		if( !window_model.is_opening_conf_window )
			User.instance().logOut();
		
		super.hide();
	}

	@Override
	protected void initEventHandlers() {
		Rectangle background = window_layout.rect_window_background;
		ImageButton ibtn_exit = window_layout.ibtn_exit;
		Label lbl_user = window_layout.lbl_user;
		Dockbar dockbar = window_layout.dockbar;
		StatusBar status_bar = window_layout.status_bar;
		ImageButton ibtn_hide = window_layout.action_pane.getHideBtn();
		ImageButton ibtn_action = window_layout.action_pane.getActionBtn();

		ibtn_hide.addEventHandler(ActionEvent.ACTION, ev -> onActionPaneHideBtnAction(ev));
		ibtn_hide.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onActionPaneHideBtnMouseEntered(ev));
		ibtn_hide.addEventHandler(MouseEvent.MOUSE_EXITED, ev-> onActionPaneHideBtnMouseExited(ev));
		
		ibtn_action.setOnAction(event -> onActionPaneActionBtnAction(ibtn_action));
		ibtn_action.addEventFilter(MouseEvent.MOUSE_ENTERED, ev -> onActionPaneActionBtnEntered(ibtn_action));
		ibtn_action.addEventFilter(MouseEvent.MOUSE_EXITED, ev -> onActionPaneActionBtnExited(ibtn_action));
		
		background.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		background.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		background.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));

		ibtn_exit.setOnAction(ev -> hide());
		ibtn_exit.addEventFilter(MouseEvent.MOUSE_ENTERED, ev -> status_bar.addText(ibtn_exit.getHint()));
		ibtn_exit.addEventFilter(MouseEvent.MOUSE_EXITED, ev -> status_bar.removeText());
		
		lbl_user.setOnMousePressed(ev -> onWindowBackgroundMousePressed(ev));
		lbl_user.setOnMouseDragged(ev -> onWindowBackgroundMouseDragged(ev));
		lbl_user.setOnMouseReleased(ev -> onWindowBackgroundMouseReleased(ev));
		
		dockbar.addEventFilter(MouseEvent.MOUSE_MOVED, ev -> onDockbarMouseMoved(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onDockbarMouseEntered(ev));
		dockbar.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onDockbarMouseExited(ev));
		dockbar.getIcons().get(0).setOnAction(ev -> onCreateNewConference(false));
		dockbar.getIcons().get(1).setOnAction(ev -> onSearchConference());
		dockbar.getIcons().get(2).setOnAction(ev -> onUploadFile());
		dockbar.getIcons().get(3).setOnAction(ev -> onDownloadFile());
		dockbar.getIcons().get(4).setOnAction(ev -> onCreateNewConference(true));
		dockbar.getIcons().get(5).setOnAction(ev -> onSearchFile());
		dockbar.getIcons().get(6).setOnAction(ev -> onShowHelp());
		dockbar.getIcons().get(7).setOnAction(ev -> onLogout());
	}

	@Override
	public void onStateChanged(State _state) {
		if( _state == State.DISCONNECTED ) {
			JOptionPane.showMessageDialog(null, "Brak po³¹czenia z serverem");
			onLogout();
		}
	}

	@Override
	public void onReceive(Connection _connection, Object _message) {
		if( User.instance().isConnected() ) {
			if( _message instanceof AllGroupsResponse )
				onAllGroupsResponseReceive((AllGroupsResponse)_message);
			else if( _message instanceof CreateGroupResponse )
				onCreateGroupResponseReceive((CreateGroupResponse)_message);
			else if( _message instanceof JoinToGroupResponse )
				onJoinToGroupResponseReceive((JoinToGroupResponse)_message);
			else if( _message instanceof ConfirmSendImageResponse )
				onConfirmSendImageResponseReceive((ConfirmSendImageResponse)_message);
			else if( _message instanceof GetAllImagesDescriptionResponse )
				onGetAllImagesDescriptionResponseReceive((GetAllImagesDescriptionResponse)_message);
		}
	}
	
	private void onAllGroupsResponseReceive(AllGroupsResponse _response) {
		List<Group> conferences = _response.getGroups();
		Platform.runLater(() -> addLoadedConferences(conferences));
	}
	
	private void onCreateGroupResponseReceive(CreateGroupResponse _response) {
		if( !_response.getAnswer() ) // nie uda³o siê utworzyæ grupy
			JOptionPane.showMessageDialog(null, "Nie uda³o siê utworzyæ grupy");	// TODO zmieniæ message box'a na aplikacyjny komunikat
		else {
			User.instance().joinToConference(_response.getGroupName());
			
			// TODO przenieœæ <tam gdzie siê da> ranlejtery do najbardziej wewnêtrznych klas
			Platform.runLater(() -> {
				Conference c = new Conference(_response.getGroupName(), "temp_desc", null, User.instance().getName(), true);
				window_layout.conf_pane.addConf(c);
			});
		}
	}
	
	private void onJoinToGroupResponseReceive(JoinToGroupResponse _response) {
		if( !_response.getAnswer() ) // nie uda³o siê do³¹czyæ
			JOptionPane.showMessageDialog(null, "Nie uda³o siê do³¹czyæ do grupy");  // TODO zmieniæ
		else {
			window_model.is_opening_conf_window = true;
			Platform.runLater(() -> openWindowAndHideCurrent(new ConfWindow()));
		}
	}
	
	private void addLoadedConferences(List<Group> _conferences) {
		for( Group conf : _conferences ) {
			Conference c = new Conference(conf.getName(), "temp_desc", null, conf.getOwner(), /* TODO temp */ true);
			window_layout.conf_pane.addConf(c);
		}
	}
	
	private void onConfirmSendImageResponseReceive(ConfirmSendImageResponse _response) {
		if( _response.getAnswer() ) {
			User.instance().addUploadedFileToTree();
			// czy chcesz utworzyc z wczytanego obrazka konferencje?
		} else
			JOptionPane.showMessageDialog(null, "Nie uda³o siê wys³aæ obrazu na server!");
	}
	
	private void onGetAllImagesDescriptionResponseReceive(GetAllImagesDescriptionResponse _response) {
		List<ImageDescription> list_of_images = _response.getAllImagesDescrition();
		List<String> list_of_filepaths = new ArrayList<>();
		
		for( ImageDescription desc : list_of_images ) {
			String filepath = desc.getPath() + desc.getName();
			list_of_filepaths.add(filepath);
		}

		User.instance().addFilesToTree(list_of_filepaths);
	}
	
	public void onConferenceCardAdded(ConferenceCard _card) {
		ImageButton ibtn_join = _card.getJoinBtn();
		ImageButton ibtn_details = _card.getDetailsBtn();
		
		ibtn_join.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onButtonMouseEntered(ibtn_join));
		ibtn_join.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onButtonMouseExited(ibtn_join));
		ibtn_join.addEventHandler(ActionEvent.ACTION, ev -> onButtonAction(ibtn_join));
		
		ibtn_details.addEventHandler(MouseEvent.MOUSE_ENTERED, ev -> onButtonMouseEntered(ibtn_details));
		ibtn_details.addEventHandler(MouseEvent.MOUSE_EXITED, ev -> onButtonMouseExited(ibtn_details));
		ibtn_details.addEventHandler(ActionEvent.ACTION, ev -> onButtonAction(ibtn_details));
	}
	
	private void onButtonAction(ImageButton _ibtn) {
		if( _ibtn.getAction() == Utils.ACT_JOIN_CONF )
			onJoinConference();
		else if( _ibtn.getAction() == Utils.ACT_OPEN_CONF )
			onOpenConference();
		else if( _ibtn.getAction() == Utils.ACT_CONF_DETAILS )
			onConferenceDetails();
	}
	
	private void onJoinConference() {
		String conf_title = window_layout.conf_pane.getHoveredCard().getConference().getTitle();
		User.instance().joinToConference(conf_title);
	}

	// TODO podmieniæ te 2 funkcje
	private void onOpenConference() {
		System.out.println("Otwórz ponownie konferencjê");
	}
	
	private void onConferenceDetails() {
		System.out.println("Wyœwietl szczegó³y konferencji");
	}
	
	private void onButtonMouseEntered(ImageButton _ibtn) {
		window_layout.conf_pane.addTextToStatusBarFromBtn(_ibtn);
	}
	
	private void onButtonMouseExited(ImageButton _ibtn) {
		window_layout.conf_pane.removeTextFromStatusBar();
	}
}
