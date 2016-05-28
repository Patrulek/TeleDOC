package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Request;

public class GetAllImagesDescriptionRequest extends Request {

	private String path;
	
	public GetAllImagesDescriptionRequest() {
		super();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}	
}
