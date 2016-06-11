package com.pp.iwm.teledoc.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.pp.iwm.teledoc.network.packets.images.GetAllGroupImagesRequest;
import com.pp.iwm.teledoc.objects.FileTree;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.objects.Member;
import com.pp.iwm.teledoc.objects.TempImage;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Point2D;

public class User extends Listener {
	
	// =======================================
	// FIELDS
	// =======================================

	private State state;
	
	public enum State {
		CONNECTING, RECONNECTING, CONNECTED, RECONNECTED, DISCONNECTED, CONNECTION_FAILURE
	}
	
	public enum ActionType {
		ADD_LINE, ADD_BROKEN_LINE, ADD_ANNOTATION, UPDATE_ANNOTATION, MOVE_OBJECT, DELETE_OBJECT
	}

	private String name;
	private String surname;
	private String email;
	private String group_name;
	
	private NetworkClient client;
	
	private List<Member> members;
	private MembersListListener members_listener;
	private DownloadListener download_listener;
	private ImageListener image_listener;
	private FileTree file_tree;
	private FileTree my_files_tree;
	private FileTree selected_group_file_tree;
	private FileTreeListener file_tree_listener;
	private String uploading_file_path;
	private TempImage downloading_file;
	private String downloading_file_path;
	private Map<Integer, Integer> used_images;		// file ids > image manager ids
	
	private List<String> download_list;
	
	// TODO aktywna konferencja
	
	private static User user;
	private NetworkListener listener;
	private int current_image;
	private boolean is_conf_owner;
	
	
	// =======================================
	// METHODS
	// =======================================
	
	@Override
	public void connected(Connection _connection) {
		changeState(State.CONNECTED);
		System.out.println(_connection + " - connected");
		//super.connected(_connection);
	}
	
	@Override
	public void disconnected(Connection _connection) {
		changeState(State.DISCONNECTED);
		System.out.println(_connection + " - disconnected");
	}
	
	@Override
	public void received(Connection _connection, Object _message) {
		if( listener != null )
			listener.onReceive(_connection, _message);
		//super.received(_connection, _message);
	}
	
	public void changeState(State _new_state) {
		if( state == _new_state )
			return;
		
		state = _new_state;
		
		if( listener != null )
			listener.onStateChanged(state);
	}
	
	public State getState() {
		return state;
	}
	
	public static User instance() {
		if( user == null )
			user = new User();
		
		return user;
	}
	
	public void setGroupName(String _group_name) {
		group_name = _group_name;
	}
	
	public String getGroupName() {
		return group_name;
	}
	
	private User() {
		state = State.DISCONNECTED;
		file_tree = new FileTree("all_files");
		my_files_tree = new FileTree("my_files");
		selected_group_file_tree = new FileTree("group_files");
		client = new NetworkClient();
		downloading_file = null;
		uploading_file_path = downloading_file_path = null;
		members = new ArrayList<>();
		used_images = new HashMap<>();
		is_conf_owner = false;
		current_image = -1;
		download_list = new ArrayList<>();
	}
	
	public void downloadNextFileFromList() {
		if( download_list.isEmpty() )
			return;
		
		String path = download_list.get(0);
		download_list.remove(0);
		
		downloadFile(path);
	}
	
	public void addFileToDownloadList(String _path) {
		if( !download_list.contains(_path) )
			download_list.add(_path);
	}
	
	public void setConfOwner(boolean _is_conf_owner) {
		is_conf_owner = _is_conf_owner;
	}
	
	public void setDownloadListener(DownloadListener _listener) {
		download_listener = _listener;
	}
	
	public void removeDownloadListener() {
		download_listener = null;
	}
	
	public void setFileTreeListener(FileTreeListener _listener) {
		file_tree_listener = _listener;
	}
	
	public void removeFileTreeListener() {
		file_tree_listener = null;
	}
	
	public void setMembersListListener(MembersListListener _listener) {
		members_listener = _listener;
	}
	
	public void removeMembersListListener() {
		members_listener = null;
	}
	
	public void setListener(NetworkListener _listener) {
		listener = _listener;
	}

	public void removeListener() {
		listener = null;
	}
	
	public void connectToServer() {
		if( state != State.CONNECTION_FAILURE && state != State.DISCONNECTED )
			return;
		
		client.setUserAsListener();
		changeState(State.CONNECTING);
		
		Thread t = new Thread(() -> tryToConnect());
		t.start();
	}
	
	public void disconnectFromServer() {
		client.disconnectFromServer();
	}
	
	private void tryToConnect() {
		try {
			client.connectToServer();
		} catch (IOException _ex) {
			changeState(State.CONNECTION_FAILURE);
		}
	}
	
	public void reconnectToServer() {
		if( state != State.CONNECTION_FAILURE && state != State.DISCONNECTED )
			return;
		
		changeState(State.RECONNECTING);
		tryToReconnect();
	}
	
	private void tryToReconnect() {
		try {
			client.connectToServer();
		} catch (IOException _ex) {
			changeState(State.CONNECTION_FAILURE);
		}
	}
	
	public boolean isConnected() {
		return client.isConnected();
	}
	
	public void loadDataFromDB() {
		loadFileTreeFromDB();
		loadConferencesFromDB();
	}
	
	public void logIn(String _email, String _password) {
		client.sendLoginRequest(_email, _password);
	}
	
	public void register(String _name, String _surname, String _email, String _password) {
		client.sendRegisterRequest(_name, _surname, _email, _password);
	}
	
	private void loadConferencesFromDB() {
		client.sendLoadConferencesRequest(email);
	}
	
	public void createNewConference(String _conference_name) {
		client.sendNewConferenceRequest(email, _conference_name);
	}
	
	public void joinToConference(String _conference_name) {
		client.sendJoinToGroupRequest(email, _conference_name);
	}
	
	public void leaveConference() {
		client.sendLeaveGroupRequest(email);
		setConfOwner(false);
		removeUsedImages();
		setCurrentImage(-1);
	}
	
	public void logOut() {
		client.sendLogoutRequest(email);
		clearData();
	}
	
	private void clearData() {
		name = surname = email = null;
		file_tree.removeTree();
		my_files_tree.removeTree();
		selected_group_file_tree.removeTree();
		uploading_file_path = downloading_file_path = null;
		removeDownloadListener();
		removeFileTreeListener();
		removeMembersListListener();
		removeUsedImages();
		setCurrentImage(-1);
	}
	
	public void sendChatMessage(String _message) {
		client.sendGroupMessageRequest(email, _message);
	}
	
	public void sendMousePos(Point2D _mouse_pos) {
		client.sendDispersedActionRequest(email, current_image, _mouse_pos);
	}

	public void sendPointerChanged(BooleanProperty is_switched_on) {
		client.sendDispersedActionRequest(email, current_image, is_switched_on);
	}
	
	public void createFolder(String _folder_name) {
		String folder_name = Utils.changeStringToFolderName(_folder_name);
		client.sendImageRequest(email, file_tree.getCurrentFolder().getPath() + folder_name, null);
		loadFileTreeFromDB();
	}
	
	public void sendImage(File _image) {
		if( uploading_file_path == null ) {
			uploading_file_path = file_tree.getCurrentFolder().getPath() + _image.getName();
			client.sendImageRequest(email, file_tree.getCurrentFolder().getPath(), _image);
		} else 
			JOptionPane.showMessageDialog(null, "Obecnie trwa wysy³anie pliku: " + uploading_file_path);
	}
	
	public void downloadFile(String _filepath) {
		if( downloading_file_path == null ) {
			downloading_file_path = _filepath;
			client.downloadImageRequest(email, _filepath);
		} else {
			System.out.println("Poczekaj a¿ poprzedni plik siê pobierze: " + downloading_file_path);
			addFileToDownloadList(_filepath);
		}
	}
	
	public void newDownloadingFile(int _size) {
		downloading_file = new TempImage(_size); 
		notifyDownloadListener(1);
	}
	
	public void progressDownload(byte[] _data) {
		downloading_file.appendData(_data);
		notifyDownloadListener(2);
	}
	
	public void notifyAboutNewImage(String _image_path, String _image_name) {
		client.sendAddImageToGroupRequest(email, _image_path, _image_name);
	}
	
	public void saveFileToDisk(String _path, int _image_id) {
		try {				
			System.out.println("Zapisujemy w: " + _path);
			FileOutputStream imageOutFile = new FileOutputStream(_path);
			imageOutFile.write(downloading_file.getContent());
			imageOutFile.close();		
			ImageManager.instance().loadImageForUser(_image_id, _path);
		} catch (Exception e) { e.printStackTrace(); } 
		  finally { 
			  downloading_file = null; 
			  downloading_file_path = null;
		  }	
		
		notifyDownloadListener(3);
	}
	
	public void getAllGroupMembers() {
		client.sendGetAllGroupMembersRequest(email);
	}
	
	// updateConferences()
	// removeNotExistingConferences()
	// addNewConferences()
	
	private void loadFileTreeFromDB() {
		client.sendGetAllImagesDescriptionRequest(email);
	}
	
	public void addUploadedFileToTree() {
		file_tree.addFile(uploading_file_path);
		my_files_tree.addFile(uploading_file_path);
		uploading_file_path = null;
		
		notifyFileTreeListener(1, 1);
	}
	
	private void loadFileForGroup(String _group_name) {
		client.sendGetAllGroupImagesRequest(email, _group_name);
	}
	
	public void showGroupFiles(String _group_name) {
		loadFileForGroup(_group_name);
	}
	
	public void showMyFiles() {
		notifyFileTreeListener(2, 2);
	}
	
	public void showAllFiles() {
		notifyFileTreeListener(2, 1);
	}

	public void addFilesToMyFilesTree(List<String> _list_of_myfilepaths) {
		for( String path : _list_of_myfilepaths )
			my_files_tree.addFile(path);
	}

	public void addFilesToTree(List<String> _list_of_filepaths) {
		for( String path : _list_of_filepaths )
			file_tree.addFile(path);
		
		notifyFileTreeListener(1, 1);
	}

	public void replaceFilesInSelectedGroupTree(List<String> _filepaths) {
		selected_group_file_tree.removeTree();
		
		for( String path : _filepaths ) 
			selected_group_file_tree.addFile(path);

		notifyFileTreeListener(2, 3);
	}
	
	public void removeFileFromTree(String _filepath) {
		file_tree.removeFile(_filepath);
		
		notifyFileTreeListener(1, 1);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String _name) {
		name = _name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String _surname) {
		surname = _surname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String _email) {
		email = _email;
		client.setUsername(_email);
	}
	
	public interface NetworkListener {
		public void onStateChanged(State _state);
		public void onReceive(Connection _connection, Object _message);
	}
	
	public interface FileTreeListener {
		public void onFileTreeChanged(FileTree _file_tree);
		public void onFileTreePreviewChanged(FileTree _file_tree);
	}
	
	public interface MembersListListener {
		public void onMembersListChanged(Member _member, boolean is_removing);
	}
	
	public interface DownloadListener {
		public void onDownloadBegin();
		public void onDownloadProgress();
		public void onDownloadFinish();
	}
	
	public interface ImageListener {
		public void onCurrentImageChanged(int _current_img_id);
		public void onImageAdded(int _img_id);
		public void onImageRemoved(int _img_id);
	}
	
	public void addUsedImage(Integer _image_key) {
		if( !used_images.containsKey(_image_key) )
			used_images.put(_image_key, _image_key);
	}
	
	public void setImageListener(ImageListener _listener) {
		image_listener = _listener;
	}
	
	public void removeImageListener() {
		image_listener = null;
	}
	
	private void notifyImageListener(int _state) {
		if( image_listener != null ) {
			if( _state == 1 )
				image_listener.onCurrentImageChanged(getCurrentImage());
		}
	}
	
	public void removeUsedImage(Integer _db_id) {
		used_images.remove(_db_id);
	}
	
	public void removeUsedImages() {
		used_images.clear();
	}
	
	public Map<Integer, Integer> getUsedImages() {
		return used_images;
	}
	
	public boolean isMemberInCurrentConference(String _member_mail) {
		return findMemberInCurrentConference(_member_mail) != null;
	}
	
	public Member findMemberInCurrentConference(String _member_mail) {
		for( Member m : members ) 
			if( m.email.equals(_member_mail) )
				return m;
		
		return null;
	}
	
	public void addMember(Member _member) {
		members.add(_member);
		notifyMembersListListener(_member, false);
	}
	
	public void removeMember(String _member_email) {
		Member m = findMemberInCurrentConference(_member_email);
		
		if( m != null )
			removeMember(m);
	}
	
	public void removeMember(Member _member) {
		members.remove(_member);
		notifyMembersListListener(_member, true);
	}
	
	public void removeMembers() {
		members.clear();
		notifyMembersListListener(null, true);
	}
	
	public void setCurrentImage(int _current_image) {
		current_image = _current_image;
		
		if( current_image > -1 )
			notifyImageListener(1);
	}
	
	public int getCurrentImage() {
		return current_image;
	}

	public FileTree getFileTree() {
		return file_tree;
	}
	
	private void notifyFileTreeListener(int _state, int _tree_idx) {
		if( file_tree_listener == null )
			return;
		
		if( _state == 1 )
			file_tree_listener.onFileTreeChanged(file_tree);
		else if( _state == 2 ) {
			if( _tree_idx == 1 )
				file_tree_listener.onFileTreePreviewChanged(file_tree);
			else if( _tree_idx == 2 )
				file_tree_listener.onFileTreePreviewChanged(my_files_tree);
			else if( _tree_idx == 3 )
				file_tree_listener.onFileTreePreviewChanged(selected_group_file_tree);
		}
	}
	
	private void notifyMembersListListener(Member _member, boolean _is_removing) {
		if( members_listener != null )
			members_listener.onMembersListChanged(_member, _is_removing);
	}
	
	// TODO
	private void notifyDownloadListener(int _state) {
		if( download_listener != null ) {
			switch( _state ) {
				case 1:
					download_listener.onDownloadBegin();
					break;
				case 2:
					download_listener.onDownloadProgress();
					break;
				case 3:
					download_listener.onDownloadFinish();
					break;
			}
		}
	}
	
	public void sendAddLineAction(String _parameters) {
		client.sendActionRequest(email, current_image, ActionType.ADD_LINE.ordinal(), _parameters);
	}
	
	public void sendAddBrokenLineAction(String _parameters) {
		client.sendActionRequest(email, current_image, ActionType.ADD_BROKEN_LINE.ordinal(), _parameters);
	}
	
	public void sendAddAnnotationAction(String _parameters) {
		client.sendActionRequest(email, current_image, ActionType.ADD_ANNOTATION.ordinal(), _parameters);
	}
	
	public void sendUpdateAnnotationAction(String _parameters) {
		client.sendActionRequest(email, current_image, ActionType.UPDATE_ANNOTATION.ordinal(), _parameters);
	}

	public void sendMoveObjectAction(String _parameters) {
		client.sendActionRequest(email, current_image, ActionType.MOVE_OBJECT.ordinal(), _parameters);
	}
	
	public void sendDeleteObjectAction(String _parameters) {
		client.sendActionRequest(email, current_image, ActionType.DELETE_OBJECT.ordinal(), _parameters);
	}

	public boolean isOwnerOfCurrentGroup() {
		return is_conf_owner;
	}

	public boolean hasAnImage(int _id) {
		return ImageManager.instance().hasUserAnImage(_id);
	}

	public void getAllGroupImages() {
		client.sendGetAllGroupImagesRequest(email, "");
	}
}
