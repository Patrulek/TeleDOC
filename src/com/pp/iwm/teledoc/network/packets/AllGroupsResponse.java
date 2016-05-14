package com.pp.iwm.teledoc.network.packets;

import java.util.ArrayList;

public class AllGroupsResponse extends Response {
	private ArrayList<Group> groups;
	
	public AllGroupsResponse() {
		super();
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	
}
