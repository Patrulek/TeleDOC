package com.pp.iwm.teledoc.windows.assistants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Connection;
import com.pp.iwm.teledoc.drawables.Annotation;
import com.pp.iwm.teledoc.drawables.DrawableBrokenLine;
import com.pp.iwm.teledoc.drawables.DrawableLine;
import com.pp.iwm.teledoc.drawables.DrawableObject;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.models.ConfWindowModel;
import com.pp.iwm.teledoc.network.NetworkClient;
import com.pp.iwm.teledoc.network.User;
import com.pp.iwm.teledoc.network.User.NetworkListener;
import com.pp.iwm.teledoc.network.User.State;
import com.pp.iwm.teledoc.network.packets.*;
import com.pp.iwm.teledoc.network.packets.images.AddImageToGroupResponse;
import com.pp.iwm.teledoc.network.packets.images.ConfirmSendImageResponse;
import com.pp.iwm.teledoc.network.packets.images.GetAllGroupImagesResponse;
import com.pp.iwm.teledoc.network.packets.images.ImageDescription;
import com.pp.iwm.teledoc.network.packets.images.SendImage;
import com.pp.iwm.teledoc.objects.ChatMessage;
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.objects.ObjectId;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.ConfWindow;
import com.sun.javafx.geom.Line2D;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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
			else if( _message instanceof LeaveGroupEvent )
				onLeaveGroupEventReceive((LeaveGroupEvent)_message);
			else if( _message instanceof SendImage )
				onSendImageReceive((SendImage)_message);
			else if( _message instanceof ConfirmSendImageResponse )
				onConfirmSendImageResponseReceive((ConfirmSendImageResponse)_message);
			else if( _message instanceof NewGroupImageEvent )
				onNewGroupImageEventReceive((NewGroupImageEvent)_message);
			else if( _message instanceof AddImageToGroupResponse )
				onAddImageToGroupResponseReceive((AddImageToGroupResponse)_message);
			else if( _message instanceof GetAllGroupImagesResponse )
				onGetAllGroupImagesResponse((GetAllGroupImagesResponse)_message);
			else if( _message instanceof ActionResponse )
				onActionResponseReceive((ActionResponse)_message);
		}
	}
	
	private void onActionResponseReceive(ActionResponse _response) {
		if( _response.getAuthorEmail().equals(User.instance().getEmail()) )
			return;
		
		switch( _response.getTypeID() ) {
			case 0: // ADD_LINE
				onActionAddLine(_response.getFileID(), _response.getParameters());
				break;
			case 1: // ADD_BROKEN_LINE
				onActionAddBrokenLine(_response.getFileID(), _response.getParameters());
				break;
			case 2: // ADD_ANNOTATION
				onActionAddAnnotation(_response.getFileID(), _response.getParameters());
				break;
			case 3: // UPDATE_ANNOTATION
				onActionUpdateAnnotation(_response.getFileID(), _response.getParameters());
				break;
			case 4: // MOVE_OBJECT
				onActionMoveObject(_response.getFileID(), _response.getParameters());
				break;
			case 5: // DELETE_OBJECT
				onDeleteObject(_response.getFileID(), _response.getParameters());
				break;
		}
	}

	private void onDeleteObject(int _file_id, String _parameters) {
		System.out.println(_parameters);
		
		ObjectId id = Utils.stringToObjectId(_parameters.substring(1));
		DrawableObject drawable = window.getDrawableAssistant().findDrawable(id);
		
		if( drawable == null )
			return;

		layout.action_pane.notifyThumbnailPanel(id);
		
		Platform.runLater(() -> {
			window.getDrawableAssistant().removeDrawable(drawable);
		});
	}

	private void onActionMoveObject(int _file_id, String _parameters) {
		System.out.println(_parameters);
		
		int pos = 1;
		int new_pos = _parameters.indexOf("#", pos);
		String x_str = _parameters.substring(pos, new_pos);
		
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		String y_str = _parameters.substring(pos, new_pos);
		
		pos = new_pos + 1;
		
		Point2D p2d = new Point2D(Double.parseDouble(x_str), Double.parseDouble(y_str));
		ObjectId id = Utils.stringToObjectId(_parameters.substring(pos));
		
		DrawableObject drawable = window.getDrawableAssistant().findDrawable(id);
		
		if( drawable == null )
			return;

		layout.action_pane.notifyThumbnailPanel(id);
		drawable.move(p2d);
	}

	private void onActionUpdateAnnotation(int _file_id, String _parameters) {
		System.out.println(_parameters);
		
		int pos = 1;
		int new_pos = _parameters.indexOf("#", pos);
		String text = _parameters.substring(pos, new_pos);
		
		pos = new_pos + 1;
		ObjectId id = Utils.stringToObjectId(_parameters.substring(pos));
		
		DrawableObject drawable = window.getDrawableAssistant().findDrawable(id);
		
		if( drawable == null )
			return;
		
		Annotation ann = (Annotation)drawable;
		layout.action_pane.notifyThumbnailPanel(id);
		ann.setText(text);
	}

	private void onActionAddAnnotation(int _file_id, String _parameters) {
		System.out.println(_parameters);
		
		int pos = 1;
		int new_pos = _parameters.indexOf("#", pos);
		String x_str = _parameters.substring(pos, new_pos);
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		String y_str = _parameters.substring(pos, new_pos);
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		String color_str = _parameters.substring(pos, new_pos);
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		String text = _parameters.substring(pos, new_pos);
		pos = new_pos + 1;
		
		final int final_pos = pos;
		
		Point2D p2d = new Point2D(Double.parseDouble(x_str), Double.parseDouble(y_str));
		Color color = Utils.intToColor(Long.parseLong(color_str));
		
		Platform.runLater(() -> {
			Annotation ann = new Annotation(text, color, layout.drawable_pane.getLayoutBounds(), p2d, layout.drawable_pane);
			ann.id = Utils.stringToObjectId(_parameters.substring(final_pos));
			ann.changeState(Annotation.State.DRAWN);
			layout.action_pane.notifyThumbnailPanel(ann.id);
			window.getDrawableAssistant().addDrawable(ann);
			ann.hideIfOtherImage();
		});
	}

	private void onActionAddBrokenLine(int _file_id, String _parameters) {
		System.out.println(_parameters);
		
		int pos = 1;
		int new_pos = _parameters.indexOf("#", pos);
		String size_str = _parameters.substring(pos, new_pos);
		int size = Integer.parseInt(size_str);
		List<Point2D> points = new ArrayList<>();
		
		for( int i = 0; i < size + 1; i++ ) {
			pos = new_pos + 1;
			new_pos = _parameters.indexOf("#", pos);
			String x_str = _parameters.substring(pos, new_pos);
			
			System.out.println("x: "  + x_str);
			
			pos = new_pos + 1;
			new_pos = _parameters.indexOf("#", pos);
			String y_str = _parameters.substring(pos, new_pos);

			System.out.println("y: "  + y_str);
			
			points.add(new Point2D(Double.parseDouble(x_str), Double.parseDouble(y_str)));
		}
		
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		String color_str = _parameters.substring(pos, new_pos);
		final int final_pos = new_pos + 1;
		long int_color = Long.valueOf(color_str);
		Color color = Utils.intToColor(int_color);
		
		Platform.runLater(() -> {
			DrawableBrokenLine line = new DrawableBrokenLine(points.get(0), points.get(1), color, layout.drawable_pane);
			line.id = Utils.stringToObjectId(_parameters.substring(final_pos));
			
			for( int i = 1; i < size; i++ )
				line.addLine(new Line(points.get(i).getX(), points.get(i).getY(), points.get(i + 1).getX(), points.get(i + 1).getY()));

			layout.action_pane.notifyThumbnailPanel(line.id);
			window.getDrawableAssistant().addDrawable(line);
			line.hideIfOtherImage();
		});
	}

	private void onActionAddLine(int _file_id, String _parameters) {
		int pos = 1;
		int new_pos = _parameters.indexOf("#", pos);
		System.out.println(pos + " | " + new_pos);
		String x_str = _parameters.substring(pos, new_pos);
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		System.out.println(pos + " | " + new_pos);
		String y_str = _parameters.substring(pos, new_pos);
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		System.out.println(pos + " | " + new_pos);
		String x_str2 = _parameters.substring(pos, new_pos);
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		System.out.println(pos + " | " + new_pos);
		String y_str2 = _parameters.substring(pos, new_pos);
		pos = new_pos + 1;
		new_pos = _parameters.indexOf("#", pos);
		
		String color_str = _parameters.substring(pos, new_pos);
		
		final int final_pos = new_pos + 1;

		System.out.println(x_str + "|" + y_str + "|" + x_str2 + "|" + y_str2 + "|" + color_str);
		double x1 = Double.valueOf(x_str);
		double y1 = Double.valueOf(y_str);
		double x2 = Double.valueOf(x_str2);
		double y2 = Double.valueOf(y_str2);
		long int_color = Long.valueOf(color_str);
		Color color = Utils.intToColor(int_color);
		Point2D p1 = new Point2D(x1, y1);
		Point2D p2 = new Point2D(x2, y2);
		
		
		Platform.runLater(() -> {
			DrawableLine l = new DrawableLine(p1, p2, color, layout.drawable_pane);
			l.id = Utils.stringToObjectId(_parameters.substring(final_pos));
			
			layout.action_pane.notifyThumbnailPanel(l.id);
			window.getDrawableAssistant().addDrawable(l);
			l.hideIfOtherImage();
		});
	}

	private void onGetAllGroupImagesResponse(GetAllGroupImagesResponse _response) {
		List<ImageDescription> desc = _response.getImages();
		
		for( ImageDescription d : desc ) {
			String filepath = d.getPath() + d.getName();
			User.instance().downloadFile(filepath);
		}
	}

	private void onLeaveGroupEventReceive(LeaveGroupEvent _response) {
		String member = _response.getEmail();
		User.instance().removeMember(member);
	}

	private void onAddImageToGroupResponseReceive(AddImageToGroupResponse _response) {
		//System.out.println(_response.get);
	}

	private void onNewGroupImageEventReceive(NewGroupImageEvent _response) {
		String filepath = _response.getPath() + _response.getName();
		System.out.println(filepath);
		
		if( !User.instance().hasAnImage(_response.getId()) ) {
			User.instance().downloadFile(filepath);
			System.out.println("Nie mam wiec pobieram: " + filepath);
		} else {
			User.instance().addUsedImage(_response.getId());
			System.out.println("Mam wiec dodaje: " + filepath);
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
			User.instance().addUsedImage(_response.getImageID());
			
			if( User.instance().getCurrentImage() == -1 ) {
				System.out.println("img id = " + _response.getImageID());
				User.instance().setCurrentImage(_response.getImageID());
				layout.minimap_pane.setImage(ImageManager.instance().getLastDownloadedImage());
				layout.drawable_pane.setImageAndResetCanvas(ImageManager.instance().getLastDownloadedImage());
			}
			
		}
	}
	
	private void onGroupMessageResponseReceive(GroupMessageResponse _response) {
		boolean is_my_message = _response.getAuthorEmail().equals(User.instance().getEmail());
		ChatMessage message = new ChatMessage(new Date(), _response.getAuthorName() + " " + _response.getAuthorSurname(), _response.getMessage(), is_my_message);
		Platform.runLater(() -> layout.chat_pane.addMessage(message));
	}
	
	private void onDispersedActionResponseReceive(DispersedActionResponse _response) {	
		if( _response.getAuthorEmail().equals(User.instance().getEmail()))
			return;
		
		com.pp.iwm.teledoc.objects.Member m = User.instance().findMemberInCurrentConference(_response.getAuthorEmail());
		
		if( m == null )
			return;
		
		int pos = _response.getParameters().indexOf("#") + 1;
		int img_id = Integer.parseInt(_response.getParameters().substring(pos));
		layout.drawable_pane.setImageIdForPointer(m.email, img_id);
		
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
		double y = Double.valueOf(_parameters.substring(y_pos + 2, _parameters.indexOf("#")));
		
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
