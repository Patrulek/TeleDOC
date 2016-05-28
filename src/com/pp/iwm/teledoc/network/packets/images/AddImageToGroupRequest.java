package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Request;

// Za�o�enie �e u�ytkownik zanim doda obrazek to do��czy do grupy
public class AddImageToGroupRequest extends Request {

	private int imageID;
	private String groupName; // opcjonalne je�li uzytkownik nie do��czy do grupy ale jednak chce doda� do niej obrazek
	
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
