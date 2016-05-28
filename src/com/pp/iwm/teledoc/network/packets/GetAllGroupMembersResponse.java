package com.pp.iwm.teledoc.network.packets;

import java.util.ArrayList;

public class GetAllGroupMembersResponse extends Response {
	
	ArrayList<Member> allGroupMembers;

	public GetAllGroupMembersResponse() {
		super();
	}

	public ArrayList<Member> getAllGroupMembers() {
		return allGroupMembers;
	}

	public void setAllGroupMembers(ArrayList<Member> allGroupMembers) {
		this.allGroupMembers = allGroupMembers;
	}	
}
