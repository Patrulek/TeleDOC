package com.pp.iwm.teledoc.objects;

import java.util.HashMap;
import java.util.Map;

import com.pp.iwm.teledoc.network.User;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class ImageManager {
	
	// ===============================================
	// FIELDS
	// ===============================================
	
	private static ImageManager s_instance;
	private Map<Integer, Image> images;
	private Map<Integer, Image> conference_images;
	private int last_id;
	
	// ===============================================
	// METHODS
	// ===============================================
	
	public static ImageManager instance() {
		if( s_instance == null )
			s_instance = new ImageManager();
		
		return s_instance;
	}
	
	public void loadImage(Integer _image_key, Image _img) {
		if( images.containsValue(_img) ) {
			System.out.println("(" + _image_key + ") B³¹d: Próbujesz wczytaæ drugi raz ten sam obraz!");
			Platform.exit();
		}
			
		if( !images.containsKey(_image_key) )
			images.put(_image_key, _img);
		else 
			System.out.println("(" + _image_key + ") Uwaga: Próbujesz nadpisaæ wczytany wczeœniej obraz!");
	}
	
	public void loadImage(Integer _image_key, String _img_path) {
		if( !images.containsKey(_image_key) )
			loadImage(_image_key, new Image(_img_path));
	}
	
	public void loadImageForUser(Integer _image_id, String _img_path) {
		if( !conference_images.containsKey(_image_id) ) 
			conference_images.put(_image_id, new Image(_img_path));
		
		setLastDownloadedImageId(_image_id);
	}
	
	public Image getConferenceImageById(int _img_id) {
		return conference_images.get(_img_id);
	}
	
	public void setLastDownloadedImageId(int _id) {
		last_id = _id;
	}
	
	public int getLastDownloadedImageId() {
		return last_id;
	}
	
	public Image getLastDownloadedImage() {
		return conference_images.get(last_id);
	}
	
	public void unloadImage(Integer _key) {
		if( images.containsKey(_key) )
			images.remove(_key);
	}
	
	public void unloadImages() {
		images.clear();
	}
	
	public Image getImage(Integer _key) {
		return images.get(_key);
	}
	
	private ImageManager() {
		images = new HashMap<>();
		conference_images = new HashMap<>();
		last_id = -1;
	}

	public boolean hasUserAnImage(int _id) {
		return conference_images.containsKey(_id);
	}
}
