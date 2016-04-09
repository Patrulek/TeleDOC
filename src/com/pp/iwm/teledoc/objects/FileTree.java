package com.pp.iwm.teledoc.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class FileTree {
	
	// ==================================
	// FIELDS
	// ==================================
	
	private File root_file = null;
	private File current_root = null;
	
	// ====================================
	// METHODS 
	// ====================================
	
	public FileTree() {
		root_file = new File("root/", null);
		current_root = root_file;
	}
	
	// do optymalizacji
	public List<File> getFilesForFolder(String _folder) {
		List<File> visible_files = new ArrayList<>();
		int pos = -1;
		File temp_root = root_file;
		_folder = _folder.substring(5);
		
		while( (pos = _folder.indexOf("/")) != -1 && !_folder.equals("") ) {
			String file_name = _folder.substring(0, pos + 1);
			_folder = _folder.substring(pos + 1);
			
			if( temp_root.getChildren().containsKey(file_name) )
				temp_root = temp_root.getChildren().get(file_name);
			else
				break;
		}
		
		for( Entry<String, File> entry : temp_root.getChildren().entrySet() )
			visible_files.add(entry.getValue());
		
		return visible_files;
	}
	
	public void addFile(String _path) {
		File temp_root = root_file;
		int pos = -1;
		String parent_path = "root/";
		String file_name = "";
		_path = _path.substring(5);
		
		while( (pos = _path.indexOf("/")) != -1 ) {
			file_name = _path.substring(0, pos + 1);
			_path = _path.substring(pos + 1);
			
			if( !temp_root.getChildren().containsKey(file_name) ) {
				parent_path = temp_root.getPath() + file_name;
				File new_file = new File(parent_path, temp_root);
				temp_root.getChildren().put(file_name, new_file);
				temp_root = new_file;
			} else
				temp_root = temp_root.getChildren().get(file_name);
		}
		
		if( !_path.equals("")  ) {
			parent_path = temp_root.getPath() + _path;
			if( !temp_root.getChildren().containsKey(_path) ) {
				File new_file = new File(parent_path, temp_root);
				temp_root.getChildren().put(_path, new_file);
			}
		}
	}
	
	public File getRoot() {
		return root_file;
	}
	
	public File getCurrentRoot() {
		return current_root;
	}
	
	public void setCurrentRoot(File _current_root) {
		current_root = _current_root;
	}
	
	public void removeFile(String _file) {
		root_file.getChildren().remove(_file);
		// notify
	}
	
	public void removeTree() {
		root_file.getChildren().clear();
	}
}
