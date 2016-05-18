package com.pp.iwm.teledoc.windows.assistants;

import java.util.Date;

import com.esotericsoftware.kryonet.Connection;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.models.ConfWindowModel;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.network.User.NetworkListener;
import com.pp.iwm.teledoc.network.User.State;
import com.pp.iwm.teledoc.network.packets.GroupMessageResponse;
import com.pp.iwm.teledoc.objects.ChatMessage;
import com.pp.iwm.teledoc.windows.ConfWindow;

import javafx.application.Platform;

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

	@Override
	public void onStateChanged(com.pp.iwm.teledoc.network.User.State _state) {
		
	}

	@Override
	public void onReceive(Connection _connection, Object _message) {
		if( User.instance().isConnected() ) {
			if( _message instanceof GroupMessageResponse )
				onGroupMessageResponseReceive((GroupMessageResponse)_message);
		}
	}
	
	private void onGroupMessageResponseReceive(GroupMessageResponse _response) {
		ChatMessage message = new ChatMessage(new Date(), _response.getAuthorName() + " " + _response.getAuthorSurname(), _response.getMessage());
		Platform.runLater(() -> layout.chat_pane.addMessage(message));
	}
}
