package com.pp.iwm.teledoc.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileTree {
	
	// ==================================
	// FIELDS
	// ==================================
	
	private File root_folder;
	private File current_folder;
	private String name;
	
	// ====================================
	// METHODS 
	// ====================================
	
	public FileTree(String _name) {
		root_folder = new File("root/", null);
		current_folder = root_folder;
		name = _name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCurrentAsRoot() {
		current_folder = root_folder;
	}
	
	// do optymalizacji
	// TODO nie wiem czy potrzebne
	public List<File> getFilesForFolder(String _folder) {
		List<File> visible_files = new ArrayList<>();
		File temp_root = findFolder(_folder);
		
		for( Entry<String, File> entry : temp_root.getChildren().entrySet() )
			visible_files.add(entry.getValue());
		
		return visible_files;
	}
	
	public Map<String, File> getFilesForCurrentFolder() {
		return current_folder.getChildren();
	}
	
	public boolean isCurrentFolderEmpty() {
		return current_folder.getChildren().isEmpty();
	}
	
	public boolean hasCurrentFolderAParent() {
		return current_folder.getParent() != current_folder;
	}
	
	public void goIntoFolder(String _folder_name) {
		File child_folder = getFilesForCurrentFolder().get(_folder_name);
		setCurrentFolder(child_folder);
	}
	
	public void goParentFolderIfExist() {
		File parent_folder = current_folder.getParent();
		
		if( parent_folder != null )
			setCurrentFolder(parent_folder);
	}
	
	public void goIntoFolder(File _folder) {
		setCurrentFolder(_folder);
	}
	
	private File findFolder(String _folder_path) {
		int pos = -1;
		File temp_folder = root_folder;
		_folder_path = _folder_path.substring(5);
		
		while( (pos = _folder_path.indexOf("/")) != -1 && !_folder_path.equals("") ) {
			String filename = _folder_path.substring(0, pos + 1);
			_folder_path = _folder_path.substring(pos + 1);
			
			if( temp_folder.getChildren().containsKey(filename) )
				temp_folder = temp_folder.getChildren().get(filename);
			else
				break;
		}
		
		return temp_folder;
	}
	
	// TODO addFile(String _parent_path (folder), String _filename (moze byc folderem)
	// jeœli filename zawiera '/', wtedy jest folderem, jeœli nie zawiera, to plikiem, jeœli "" to nie robimy nic
	// TODO dobrze by³oby cache'owaæ ileœ folderów, ¿eby nie wykonywaæ ci¹gle tego samego wyszukiwania, przy wczytywaniu
	// z bazy chociazby
	public void addFile(String _path) {
		File temp_folder = root_folder;
		int pos = -1;
		String parent_path = "root/";
		String filename = "";
		_path = _path.substring(5);
		
		while( (pos = _path.indexOf("/")) != -1 ) {
			filename = _path.substring(0, pos + 1);
			_path = _path.substring(pos + 1);
			
			if( !temp_folder.getChildren().containsKey(filename) ) {
				parent_path = temp_folder.getPath() + filename;
				File new_file = new File(parent_path, temp_folder);
				temp_folder.getChildren().put(filename, new_file);
				temp_folder = new_file;
			} else
				temp_folder = temp_folder.getChildren().get(filename);
		} 
		
		if( !_path.equals("")  ) {
			parent_path = temp_folder.getPath() + _path;
			if( !temp_folder.getChildren().containsKey(_path) ) {
				File new_file = new File(parent_path, temp_folder);
				temp_folder.getChildren().put(_path, new_file);
			}
		}
	}
	
	public File getRootFolder() {
		return root_folder;
	}
	
	public File getCurrentFolder() {
		return current_folder;
	}
	
	public void setCurrentFolder(File _current_folder) {
		current_folder = _current_folder;
	}
	
	public void removeFile(String _file_path) {
		root_folder.removeFile(_file_path);
		// notify
	}
	
	public void removeFile(File _file) {
		root_folder.removeFile(_file);
	}
	
	public void removeTree() {
		root_folder.getChildren().clear();
		setCurrentAsRoot();
	}
}
