package com.pp.iwm.teledoc.network.packets;

public class JoinToGroupRequest extends Request {
	private String groupName;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
