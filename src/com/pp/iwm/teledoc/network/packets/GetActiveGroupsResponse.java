package com.pp.iwm.teledoc.network.packets;

import java.util.ArrayList;

public class GetActiveGroupsResponse extends Response {
	ArrayList<Group> groups;

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}	
}
