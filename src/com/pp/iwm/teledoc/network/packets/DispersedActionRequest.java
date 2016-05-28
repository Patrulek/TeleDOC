package com.pp.iwm.teledoc.network.packets;

public class DispersedActionRequest extends Request {

	private int fileID;
	private int typeID;
	private String parameters;
	
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}	
	
	
}
