package com.pp.iwm.teledoc.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.pp.iwm.teledoc.objects.Conference;
import com.pp.iwm.teledoc.objects.FileTree;

import javafx.application.Platform;

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
	
	private List<Conference> closed_conferences;
	private List<Conference> open_conferences;
	private FileTree file_tree;
	
	// TODO aktywna konferencja
	
	private static User user;
	private NetworkListener listener;
	// private List<Image> used_images;
	
	// =======================================
	// METHODS
	// =======================================
	
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
		file_tree = new FileTree();
		client = new NetworkClient();
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
	//	loadFileTreeFromDB();
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
	
	// updateConferences()
	// removeNotExistingConferences()
	// addNewConferences()
	
	private void loadFileTreeFromDB() {
		for( int i = 0; i < 30; i++ ) {
			file_tree.addFile("root/folder" + i + "/");
			file_tree.addFile("root/image" + i + ".png");
			file_tree.addFile("root/folder/image" + i + ".png");
			file_tree.addFile("root/arrow.hdtv.720p/s04e17" + i + ".avi");
			file_tree.addFile("root/_d_u_p_a_" + i + "/png" + i + ".png");
		}
		
		//((AppWindow)window).setFileExplorerRoot(file_tree);
		//((AppWindow)window).refreshFileExplorerView();
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
}
