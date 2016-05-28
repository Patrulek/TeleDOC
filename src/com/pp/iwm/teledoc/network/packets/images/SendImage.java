package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Request;

// if email==null {/*nadawca to server*/}
public class SendImage extends Request {
	private String name;
	private String path;
	private int imageID;
	private int standardPackageSize;
	private int partNumber;
	private int endPartNumber;
	private int sizeInBytes;
	private byte[] imageContent;
	
	public int getSizeInBytes() {
		return sizeInBytes;
	}

	public void setSizeInBytes(int size) {
		this.sizeInBytes = size;
	}	
	
	public SendImage() {
		super();
	}

	public byte[] getImageContent() {
		return imageContent;
	}

	public void setImageContent(byte[] content) {
		this.imageContent = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

	public int getEndPartNumber() {
		return endPartNumber;
	}

	public void setEndPartNumber(int endPartNumber) {
		this.endPartNumber = endPartNumber;
	}

	public int getStandardPackageSize() {
		return standardPackageSize;
	}

	public void setStandardPackageSize(int size) {
		this.standardPackageSize = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
}
