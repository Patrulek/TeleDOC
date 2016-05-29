package com.pp.iwm.teledoc.objects;

public class TempImage {
	
	// ===========================
	// FIELDS
	// ===========================
	
	private byte[] content;
	private int last_byte;
	
	// ===========================
	// METHODS
	// ===========================
	
	
	public TempImage(int _size) {
		content = new byte[_size];
		last_byte = 0;
	}
	
	public void appendData(byte[] _data) {
		System.arraycopy(_data, 0, content, last_byte, _data.length);
		last_byte += _data.length;
	}
	
	public byte[] getContent() {
		return content;
	}
}
