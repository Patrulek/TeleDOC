package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Request;

public class GetAllGroupImagesRequest extends Request {
	private String groupName; // gdy grupa jest null lub "" to odeœle te opisy obrazków znajduj¹ce siê w grupie w której aktualnie jestem zalogowany

	
	public GetAllGroupImagesRequest() {
		groupName = null;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
