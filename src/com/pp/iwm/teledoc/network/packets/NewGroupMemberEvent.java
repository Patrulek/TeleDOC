package com.pp.iwm.teledoc.network.packets;

public class NewGroupMemberEvent extends Response {
	private Member newMember;

	public NewGroupMemberEvent() {
		super();
	}

	public Member getNewMember() {
		return newMember;
	}

	public void setNewMember(Member newMember) {
		this.newMember = newMember;
	}
}
