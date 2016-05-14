package com.pp.iwm.teledoc.network.packets;

public class JoinToGroupResponse extends Response {

	private String groupName;
		
	public JoinToGroupResponse() {
		super();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
