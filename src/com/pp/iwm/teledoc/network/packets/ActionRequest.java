package com.pp.iwm.teledoc.network.packets;

public class ActionRequest extends Request {
	private int fileID;
	private int typeID;
	private String parameters;
	
	public ActionRequest() {
		super();
	}

	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
}
