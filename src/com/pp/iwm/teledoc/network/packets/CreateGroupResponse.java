package com.pp.iwm.teledoc.network.packets;

public class CreateGroupResponse extends Response {
	private String groupName;
	private String groupOwner;
	
	public CreateGroupResponse() {
		super();
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupOwner() {
		return groupOwner;
	}
	public void setGroupOwner(String groupOwner) {
		this.groupOwner = groupOwner;
	}
}
