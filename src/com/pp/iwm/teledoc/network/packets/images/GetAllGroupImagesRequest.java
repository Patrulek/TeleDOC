package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Request;

public class GetAllGroupImagesRequest extends Request {
	private String groupName; // gdy grupa jest null lub "" to ode�le te opisy obrazk�w znajduj�ce si� w grupie w kt�rej aktualnie jestem zalogowany

	
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
