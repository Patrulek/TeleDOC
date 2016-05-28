package com.pp.iwm.teledoc.network.packets;

public class DeleteActionRequest extends Request {
	private int fileId;
	private int typeId;
	private String parameters;
	
	public DeleteActionRequest() {
		super();
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	
	
	
}
