package com.pp.iwm.teledoc.network.packets;

public class CreateGroupRequest extends Request {
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
