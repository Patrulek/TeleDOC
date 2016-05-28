package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Response;
import java.util.ArrayList;

public class GetAllImagesDescriptionResponse extends Response {
	ArrayList<ImageDescription> allImagesDescrition;

	public ArrayList<ImageDescription> getAllImagesDescrition() {
		return allImagesDescrition;
	}

	public void setAllImagesDescrition(ArrayList<ImageDescription> allImagesDescrition) {
		this.allImagesDescrition = allImagesDescrition;
	}

}
