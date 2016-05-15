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
	
	public void loadImageForUser(Integer _image_key, String _img_path) {
		loadImage(_image_key, _img_path);
		User.instance().addUsedImage(_image_key);
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
	}
}
