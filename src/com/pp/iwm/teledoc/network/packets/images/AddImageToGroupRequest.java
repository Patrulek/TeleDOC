package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Request;

// Za³o¿enie ¿e u¿ytkownik zanim doda obrazek to do³¹czy do grupy
public class AddImageToGroupRequest extends Request {

	private int imageID;
	private String groupName; // opcjonalne jeœli uzytkownik nie do³¹czy do grupy ale jednak chce dodaæ do niej obrazek
	
	public AddImageToGroupRequest() {
		super();
		this.groupName = null;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
	
}
