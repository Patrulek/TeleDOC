package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Response;
import java.util.ArrayList;

public class GetAllGroupImagesResponse extends Response {
	private ArrayList<ImageDescription> images;

	public GetAllGroupImagesResponse() { }

	public ArrayList<ImageDescription> getImages() {
		return images;
	}

	public void setImages(ArrayList<ImageDescription> images) {
		this.images = images;
	}	

}
