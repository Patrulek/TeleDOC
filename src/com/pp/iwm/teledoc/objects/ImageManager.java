package com.pp.iwm.teledoc.objects;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class ImageManager {
	
	// ===============================================
	// FIELDS
	// ===============================================
	
	private static ImageManager s_instance;
	private Map<Integer, Image> images;
	
	
	// ===============================================
	// METHODS
	// ===============================================
	
	public static ImageManager instance() {
		if( s_instance == null )
			s_instance = new ImageManager();
		
		return s_instance;
	}
	
	public void loadImage(Integer key, Image img) {
		if( !images.containsKey(key) )
			images.put(key, img);
	}
	
	public void loadImage(Integer key, String img_path) {
		if( !images.containsKey(key) )
			images.put(key, new Image(img_path));
	}
	
	public void unloadImage(Integer key) {
		if( images.containsKey(key) )
			images.remove(key);
	}
	
	public void unloadImages() {
		images.clear();
	}
	
	public Image getImage(Integer key) {
		return images.get(key);
	}
	
	private ImageManager() {
		images = new HashMap<>();
	}
	
}
