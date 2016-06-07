package com.pp.iwm.teledoc.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.*;
import com.pp.iwm.teledoc.network.packets.*;
import com.pp.iwm.teledoc.network.packets.images.*;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Point2D;

public class NetworkClient {
	
	// ===============================
	// FIELDS 
	// ===============================
	
	public static final double FILESIZE_LIMIT = 10.0; 	// w MB 
	private static final int IMAGE_PACKET_SIZE = 3096;	// w B
	public static final int MSG_POINTER = 10000;
	public static final int MSG_POINTER_ON = 10001;
	public static final int MSG_POINTER_OFF = 10002;

	private final String SERVER_ADDRESS = "192.168.0.9";	// 192.168.0.6 <- wirualka  | 192.168.0.10 - Piotr
	private final int TIMEOUT = 15000;
	private final int TCP_PORT = 33000;
	private final int UDP_PORT = 32000;
	private final int TCP_PORT_IMAGES = 35000;
	private final int UDP_PORT_IMAGES = 34000;
	
	
	private boolean has_listener;
	private Client client;
	private Client image_client;
	private Kryo kryo;
	private Kryo kryo_image;
	private String username;
	
	// ===============================
	// METHODS 
	// ===============================
	
	public NetworkClient() {
		has_listener = false;
		client = new Client(12500000, 4096);//Client(10500000, 4096);
		client.start();
		
		image_client = new Client(30500000, 16384); //Client(10500000, 4096);
		image_client.start();
		
		registerPackets();
	}
	
	public void setUserAsListener() {
		if( !has_listener ) {
			client.addListener(User.instance());
			image_client.addListener(User.instance());
			has_listener = true;
		}
	}
	
	public void connectToServer() throws IOException {
		client.connect(TIMEOUT, SERVER_ADDRESS, TCP_PORT, UDP_PORT);
		image_client.connect(TIMEOUT, SERVER_ADDRESS, TCP_PORT_IMAGES, UDP_PORT_IMAGES);
	}
	
	public boolean isConnected() {
		return client.isConnected() && image_client.isConnected();
	}
	
	public void disconnectFromServer() {
		client.close();
		image_client.close();
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
		kryo.register(ActionRequest.class);
		kryo.register(ActionResponse.class);
		kryo.register(String.class);
		kryo.register(Member.class);
		kryo.register(GetAllGroupMembersRequest.class);
		kryo.register(GetAllGroupMembersResponse.class);
		kryo.register(NewGroupImageEvent.class);
		kryo.register(NewGroupMemberEvent.class);
		kryo.register(DeleteActionRequest.class);
		kryo.register(DeleteActionResponse.class);
		kryo.register(DispersedActionRequest.class);
		kryo.register(DispersedActionResponse.class);
		kryo.register(LeaveGroupEvent.class);
		kryo.register(GetActiveGroupsRequest.class);
		kryo.register(GetActiveGroupsResponse.class);
		
		kryo_image = image_client.getKryo();
		kryo_image.register(SendImage.class);
		kryo_image.register(ConfirmSendImageResponse.class);
		kryo_image.register(byte[].class);
		kryo_image.register(GetAllImagesDescriptionRequest.class);
		kryo_image.register(GetAllImagesDescriptionResponse.class);
		kryo_image.register(ImageDescription.class);
		kryo_image.register(ArrayList.class);
		kryo_image.register(DownloadImageRequest.class);
		kryo_image.register(String.class);
		kryo_image.register(AddImageToGroupRequest.class);
		kryo_image.register(AddImageToGroupResponse.class);
		kryo_image.register(GetAllGroupImagesRequest.class);
		kryo_image.register(GetAllGroupImagesResponse.class);
		kryo_image.register(Action.class);
		kryo_image.register(GetAllGroupActionsRequest.class);
		kryo_image.register(GetAllGroupActionsResponse.class);		
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
	
	public void sendGroupMessageRequest(String _email, String _message) {
		GroupMessageRequest request = new GroupMessageRequest();
		request.setEmail(_email);
		request.setMessage(_message);
		client.sendTCP(request);
	}

	public void sendDispersedActionRequest(String _email, Object _parameters) {
		DispersedActionRequest request = new DispersedActionRequest();
		request.setEmail(_email);
		
		if( _parameters instanceof Point2D )
			sendMousePos(request, (Point2D)_parameters);
		else if( _parameters instanceof BooleanProperty )
			sendPointerChanged(request, (BooleanProperty)_parameters);
		
		client.sendUDP(request);
	}
	
	private void sendPointerChanged(DispersedActionRequest _request, BooleanProperty _is_switched_on) {
		if( _is_switched_on.get() )
			_request.setTypeID(MSG_POINTER_ON);
		else
			_request.setTypeID(MSG_POINTER_OFF);
	}
	
	private void sendMousePos(DispersedActionRequest _request, Point2D _mouse_pos) {
		_request.setTypeID(MSG_POINTER);	
		String params = "x: " + _mouse_pos.getX() + " y: " + _mouse_pos.getY();
		_request.setParameters(params);
	}
	
	public void sendGetAllGroupMembersRequest(String _email) {
		GetAllGroupMembersRequest request = new GetAllGroupMembersRequest();
		request.setEmail(_email);
		client.sendTCP(request);
	}


	public void downloadImageRequest(String email, String _filepath) {
		DownloadImageRequest request = new DownloadImageRequest();
		request.setEmail(email);
		
		int pos = _filepath.lastIndexOf("/") + 1;
		String path = _filepath.substring(0, pos);
		String name = _filepath.substring(pos);

		request.setPath(path);
		request.setImageName(name);
		
		System.out.println("Pobieramy: " + path + name);
		
		image_client.sendTCP(request);	
	}

	public void sendImageRequest(String _email, String _parent_path, File _image) {
		try {
			SendImage request = new SendImage();
			request.setEmail(_email);
			request.setPath(_parent_path);
			request.setStandardPackageSize(IMAGE_PACKET_SIZE);
			
			if( _image == null ) {
				request.setSizeInBytes(1);
				request.setName("");
				request.setPartNumber(0);
				request.setEndPartNumber(0);
				request.setImageContent(null);
				
				image_client.sendTCP(request);
				return;
			}
			
			FileInputStream imageToSend = new FileInputStream(_image);
			byte imageData[] = new byte[(int)_image.length()];
			imageToSend.read(imageData);
			int numberOfPackages = ((int)imageData.length / IMAGE_PACKET_SIZE) + 1;
			
			request.setStandardPackageSize(IMAGE_PACKET_SIZE);
			request.setSizeInBytes(imageData.length);
			request.setPath(_parent_path);
			request.setEmail(_email);
			request.setName(_image.getName());
			request.setEndPartNumber(numberOfPackages - 1);
			
			int start_range = 0;
			int end_range = IMAGE_PACKET_SIZE;
				
			for(int i = 0; i < numberOfPackages - 1; i++) {
				byte[] str = Arrays.copyOfRange(imageData, start_range, end_range);
				request.setPartNumber(i);
				request.setImageContent(str);
				
				image_client.sendTCP(request);
				
				start_range = end_range;
				end_range += IMAGE_PACKET_SIZE;
			}
					
			byte[] str = Arrays.copyOfRange(imageData, start_range, imageData.length);
			request.setPartNumber(numberOfPackages - 1);
			request.setImageContent(str);
			image_client.sendTCP(request);
				
			imageToSend.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendGetAllImagesDescriptionRequest(String _email) {
		GetAllImagesDescriptionRequest request = new GetAllImagesDescriptionRequest();
		request.setEmail(_email);
		image_client.sendTCP(request);
		System.out.println("Poszed³ request o wszystkie pliki");
	}

	public void sendGetAllGroupImagesRequest(String _email, String _group_name) {
		GetAllGroupImagesRequest request = new GetAllGroupImagesRequest();
		request.setEmail(_email);
		request.setGroupName(_group_name);
		
		image_client.sendTCP(request);
	}
	
	public void sendAddImageToGroupRequest(String _email, String _group_name, String _image_path, String _image_name) {
		AddImageToGroupRequest request = new AddImageToGroupRequest();
		request.setEmail(_email);
		request.setPath(_image_path);
		request.setName(_image_name);
		request.setGroupName(_group_name);
			
		image_client.sendTCP(request);			
	}
}
