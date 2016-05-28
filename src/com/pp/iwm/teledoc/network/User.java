package com.pp.iwm.teledoc.network;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.pp.iwm.teledoc.objects.Conference;
import com.pp.iwm.teledoc.objects.FileTree;

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

	private String name;
	private String surname;
	private String email;
	
	private NetworkClient client;
	private UserImageClient image_client;
	
	private List<Conference> closed_conferences;
	private List<Conference> open_conferences;
	private FileTree file_tree;
	private FileTreeListener file_tree_listener;
	private String uploading_file_path;
	private String downloading_file_path;
	
	// TODO aktywna konferencja
	
	private static User user;
	private NetworkListener listener;
	private List<Integer> used_images;
	private int current_image;
	
	
	// =======================================
	// METHODS
	// =======================================
	
	public UserImageClient getImageClient() {
		return image_client;
	}
	
	@Override
	public void connected(Connection _connection) {
		changeState(State.CONNECTED);
		System.out.println("Connected");
		//super.connected(_connection);
	}
	
	@Override
	public void disconnected(Connection _connection) {
		changeState(State.DISCONNECTED);
		System.out.println("Disconnected");
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
	
	private User() {
		state = State.DISCONNECTED;
		closed_conferences = new ArrayList<>();
		open_conferences = new ArrayList<>();
		used_images = new ArrayList<>();
		file_tree = new FileTree();
		client = new NetworkClient();
		uploading_file_path = downloading_file_path = null;
		image_client = new UserImageClient();
	}
	
	public void setFileTreeListener(FileTreeListener _listener) {
		file_tree_listener = _listener;
	}
	
	public void removeFileTreeListener() {
		file_tree_listener = null;
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
		loadConferencesFromDB();
		loadFileTreeFromDB();
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
	}
	
	public void logOut() {
		client.sendLogoutRequest(email);
	}
	
	public void sendChatMessage(String _message) {
		client.sendGroupMessageRequest(email, _message);
	}
	
	public void sendMousePos(Point2D _mouse_pos) {
		client.sendDispersedActionRequest(email, _mouse_pos);
	}

	public void sendPointerChanged(BooleanProperty is_switched_on) {
		client.sendDispersedActionRequest(email, is_switched_on);
	}
	
	public void createFolder(String _folder_name) {
		client.sendImageRequest(email, file_tree.getCurrentFolder().getPath() + _folder_name, new File(""));
	}
	
	public void sendImage(File _image) {
		if( uploading_file_path == null ) {
			uploading_file_path = file_tree.getCurrentFolder().getPath() + _image.getName();
			client.sendImageRequest(email, file_tree.getCurrentFolder().getPath(), _image);
		} else 
			JOptionPane.showMessageDialog(null, "Obecnie trwa wysy³anie pliku: " + uploading_file_path);
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
		uploading_file_path = null;
		
		notifyFileTreeListener();
	}

	public void addFilesToTree(List<String> _list_of_filepaths) {
		for( String path : _list_of_filepaths )
			file_tree.addFile(path);
		
		notifyFileTreeListener();
	}
	
	public void removeFileFromTree(String _filepath) {
		file_tree.removeFile(_filepath);
		
		notifyFileTreeListener();
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
	}
	
	public void addUsedImage(Integer _image_key) {
		used_images.add(_image_key);
	}
	
	public void removeUsedImage(Integer _image_key) {
		used_images.remove(_image_key);
	}
	
	public void removeUsedImages() {
		used_images.clear();
	}
	
	public List<Integer> getUsedImages() {
		return used_images;
	}
	
	public void setCurrentImage(int _current_image) {
		current_image = _current_image;
	}
	
	public int getCurrentImage() {
		return current_image;
	}

	public FileTree getFileTree() {
		return file_tree;
	}
	
	private void notifyFileTreeListener() {
		if( file_tree_listener != null )
			file_tree_listener.onFileTreeChanged(file_tree);
	}
	
	private class UserImageClient extends Listener {
		@Override
		public void connected(Connection _connection) {
			System.out.println("UserImageClient Connected");
			super.connected(_connection);
		}
		
		@Override
		public void disconnected(Connection _connection) {
			System.out.println("UserImageClient Disconnected");
			super.disconnected(_connection);
		}
		
		@Override
		public void received(Connection _connection, Object _message) {
			if( listener != null )
				listener.onReceive(_connection, _message);
		}
	}
}
