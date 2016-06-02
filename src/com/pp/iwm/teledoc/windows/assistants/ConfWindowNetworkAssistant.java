package com.pp.iwm.teledoc.windows.assistants;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.models.ConfWindowModel;
import com.pp.iwm.teledoc.network.NetworkClient;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.network.User.NetworkListener;
import com.pp.iwm.teledoc.network.User.State;
import com.pp.iwm.teledoc.network.packets.*;
import com.pp.iwm.teledoc.network.packets.images.ConfirmSendImageResponse;
import com.pp.iwm.teledoc.network.packets.images.SendImage;
import com.pp.iwm.teledoc.objects.ChatMessage;
import com.pp.iwm.teledoc.windows.ConfWindow;

import javafx.application.Platform;
import javafx.geometry.Point2D;

public class ConfWindowNetworkAssistant implements NetworkListener {
	
	// ==================================
	// FIELDS
	// ==================================
	
	private ConfWindow window;
	private ConfWindowModel model;
	private ConfWindowLayout layout;
	
	// ==================================
	// METHODS
	// ==================================
	
	public ConfWindowNetworkAssistant(ConfWindow _window) {
		window = _window;
		model = window.getWindowModel();
		layout = window.getWindowLayout();
	}
	
	public void sendNewMessage(String _message) {
		User.instance().sendChatMessage(_message);
		layout.chat_pane.clearTextArea();
	}
	
	public void sendMousePos() {
		User.instance().sendMousePos(model.image_mouse_pos);
	}

	@Override
	public void onStateChanged(State _state) {
		
	}

	@Override
	public void onReceive(Connection _connection, Object _message) {
		if( User.instance().isConnected() ) {
			if( _message instanceof GroupMessageResponse )
				onGroupMessageResponseReceive((GroupMessageResponse)_message);
			else if( _message instanceof DispersedActionResponse )
				onDispersedActionResponseReceive((DispersedActionResponse)_message);
			else if( _message instanceof GetAllGroupMembersResponse ) 
				onGetAllGroupMembersResponseReceive((GetAllGroupMembersResponse)_message);
			else if( _message instanceof NewGroupMemberEvent )
				onNewGroupMemberEventReceive((NewGroupMemberEvent)_message);
			else if( _message instanceof SendImage )
				onSendImageReceive((SendImage)_message);
			else if( _message instanceof ConfirmSendImageResponse )
				onConfirmSendImageResponseReceive((ConfirmSendImageResponse)_message);
		}
	}
	
	private void onConfirmSendImageResponseReceive(ConfirmSendImageResponse _response) {
		if( _response.getAnswer() ) {
			User.instance().addUploadedFileToTree();
			// czy chcesz utworzyc z wczytanego obrazka konferencje?
		} else
			JOptionPane.showMessageDialog(null, "Nie uda³o siê wys³aæ obrazu na server!");
	}

	private void onSendImageReceive(SendImage _response) {
		if( _response.getPartNumber() == 0 ) {
			User.instance().newDownloadingFile(_response.getSizeInBytes());
			User.instance().progressDownload(_response.getImageContent());
		} else if( _response.getPartNumber() < _response.getEndPartNumber() )
			User.instance().progressDownload(_response.getImageContent());		
		else {
			User.instance().progressDownload(_response.getImageContent());
			String path = "assets/" + _response.getImageID() + _response.getName();
			User.instance().saveFileToDisk(path, _response.getImageID());			
		}
	}
	
	private void onGroupMessageResponseReceive(GroupMessageResponse _response) {
		ChatMessage message = new ChatMessage(new Date(), _response.getAuthorName() + " " + _response.getAuthorSurname(), _response.getMessage());
		Platform.runLater(() -> layout.chat_pane.addMessage(message));
	}
	
	private void onDispersedActionResponseReceive(DispersedActionResponse _response) {	
		if( _response.getAuthorEmail().equals(User.instance().getEmail()))
			return;
		
		com.pp.iwm.teledoc.objects.Member m = User.instance().findMemberInCurrentConference(_response.getAuthorEmail());
		
		if( m == null )
			return;
		
		if( _response.getTypeID() == NetworkClient.MSG_POINTER && m.is_sending_pointer  ) {
			Point2D sender_mouse_pos = parseStringParameterToMousePos(_response.getParameters());
			layout.drawable_pane.relocatePointerFor(m.email, sender_mouse_pos);
			//System.out.println(m.name + " " + m.surname + " wskazuje na " + sender_mouse_pos);
		} else if( _response.getTypeID() == NetworkClient.MSG_POINTER_ON ) {
			m.is_sending_pointer = true;
			layout.drawable_pane.showPointerFor(m.email);
			System.out.println("Uzytkownik: " + m.name + " " + m.surname + " wysy³a teraz wskaŸnik");
		}
		else if( _response.getTypeID() == NetworkClient.MSG_POINTER_OFF ) {
			m.is_sending_pointer = false;
			layout.drawable_pane.hidePointerFor(m.email);
			System.out.println("Uzytkownik: " + m.name + " " + m.surname + " przestaje wysy³aæ wskaŸnik");
		}
	}
	
	private Point2D parseStringParameterToMousePos(String _parameters) {
		int x_pos = _parameters.indexOf("x:");
		int y_pos = _parameters.indexOf("y:");
		
		double x = Double.valueOf(_parameters.substring(x_pos + 2, y_pos));
		double y = Double.valueOf(_parameters.substring(y_pos + 2));
		
		return new Point2D(x, y);
	}
	
	private void onGetAllGroupMembersResponseReceive(GetAllGroupMembersResponse _response) {
		List<Member> group_members = _response.getAllGroupMembers();
		
		Platform.runLater(() -> {
			for( Member m : group_members )
				User.instance().addMember(new com.pp.iwm.teledoc.objects.Member(m.getName(), m.getSurname(), m.getEmail()));
		});
	}
	
	private void onNewGroupMemberEventReceive(NewGroupMemberEvent _response) {
		Platform.runLater(() -> {
			Member m = _response.getNewMember();
			User.instance().addMember(new com.pp.iwm.teledoc.objects.Member(m.getName(), m.getSurname(), m.getEmail()));
		});
	}
}
