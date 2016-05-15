package com.pp.iwm.teledoc.network;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.pp.iwm.teledoc.network.packets.AllGropusRequest;
import com.pp.iwm.teledoc.network.packets.AllGroupsResponse;
import com.pp.iwm.teledoc.network.packets.CreateGroupRequest;
import com.pp.iwm.teledoc.network.packets.CreateGroupResponse;
import com.pp.iwm.teledoc.network.packets.Group;
import com.pp.iwm.teledoc.network.packets.GroupMessageRequest;
import com.pp.iwm.teledoc.network.packets.GroupMessageResponse;
import com.pp.iwm.teledoc.network.packets.JoinToGroupRequest;
import com.pp.iwm.teledoc.network.packets.JoinToGroupResponse;
import com.pp.iwm.teledoc.network.packets.LeaveGroupRequest;
import com.pp.iwm.teledoc.network.packets.LeaveGroupResponse;
import com.pp.iwm.teledoc.network.packets.LoginRequest;
import com.pp.iwm.teledoc.network.packets.LoginResponse;
import com.pp.iwm.teledoc.network.packets.LogoutRequest;
import com.pp.iwm.teledoc.network.packets.RegisterRequest;
import com.pp.iwm.teledoc.network.packets.RegisterResponse;
import com.pp.iwm.teledoc.network.packets.Request;
import com.pp.iwm.teledoc.network.packets.Response;

public class NetworkClient {
	
	// ===============================
	// FIELDS 
	// ===============================
	
	public static final double FILESIZE_LIMIT = 10.0; 	// w MB 

	private final String SERVER_ADDRESS = "192.168.0.6";
	private final int TIMEOUT = 5000;
	private final int TCP_PORT = 33000;
	private final int UDP_PORT = 32000;
	
	private boolean has_listener;
	private Client client;
	private Kryo kryo;
	private String username;
	
	// ===============================
	// METHODS 
	// ===============================
	
	public NetworkClient() {
		has_listener = false;
		client = new Client();
		client.start();
		registerPackets();
	}
	
	public void setUserAsListener() {
		if( !has_listener ) {
			client.addListener(User.instance());
			has_listener = true;
		}
	}
	
	public void connectToServer() throws IOException {
		client.connect(TIMEOUT, SERVER_ADDRESS, TCP_PORT, UDP_PORT);
	}
	
	public boolean isConnected() {
		return client.isConnected();
	}
	
	private void registerPackets() {
		kryo = client.getKryo();
		
		kryo.register(Request.class);
		kryo.register(LoginRequest.class);
		kryo.register(Response.class);
		kryo.register(LoginResponse.class);
		kryo.register(RegisterRequest.class);
		kryo.register(RegisterResponse.class);
		kryo.register(User.class);
		kryo.register(CreateGroupRequest.class);
		kryo.register(CreateGroupResponse.class);
		kryo.register(AllGropusRequest.class);
		kryo.register(AllGroupsResponse.class);
		kryo.register(Group.class);
		kryo.register(ArrayList.class);
		kryo.register(JoinToGroupRequest.class);
		kryo.register(JoinToGroupResponse.class);
		kryo.register(LeaveGroupRequest.class);
		kryo.register(LeaveGroupResponse.class);
		kryo.register(LogoutRequest.class);
		kryo.register(GroupMessageRequest.class);
		kryo.register(GroupMessageResponse.class);
	}
	
	public void setUsername(String _username) {
		username = _username;
	}
	
	public void sendLoginRequest(String _email, String _password) {
		LoginRequest request = new LoginRequest();
		request.setEmail(_email);
		request.setPassword(_password);
		client.sendTCP(request);
	}
	
	public void sendRegisterRequest(String _name, String _surname, String _email, String _password) {
		RegisterRequest request = new RegisterRequest();
		request.setName(_name);
		request.setSurname(_surname);
		request.setEmail(_email);
		request.setPassword(_password);
		request.setPosition("1");
		client.sendTCP(request);
	}
	
	public void sendLoadConferencesRequest(String _email) {
		AllGropusRequest request = new AllGropusRequest();
		request.setEmail(_email);
		client.sendTCP(request);
	}
	
	public void sendNewConferenceRequest(String _email, String _group_name) {
		CreateGroupRequest request = new CreateGroupRequest();
		request.setEmail(_email);
		request.setName(_group_name);
		client.sendTCP(request);			
	}
	
	public void sendJoinToGroupRequest(String _email, String _group_name) {
		JoinToGroupRequest request = new JoinToGroupRequest();
		request.setEmail(_email);
		request.setGroupName(_group_name);
		client.sendTCP(request);	
	}
	
	public void sendLeaveGroupRequest(String _email) {
		LeaveGroupRequest request = new LeaveGroupRequest();
		request.setEmail(_email);
		client.sendTCP(request);
	}
	
	public void sendLogoutRequest(String _email) {
		LogoutRequest request = new LogoutRequest();
		request.setEmail(_email);
		client.sendTCP(request);
	}
}
