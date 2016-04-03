package com.pp.iwm.teledoc.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class FileTree {
	
	public File root_file = null;
	public File current_root = null;
	
	public FileTree() {
		root_file = new File("root/", null);
		current_root = root_file;

		addFile("root/folder/");
		addFile("root/image.png");
		addFile("root/folder/image.png");
		addFile("root/arrow.hdtv.720p/s04e17.avi");
		addFile("root/_d_u_p_a_/png.png");
	}
	
	public List<File> getFilesForFolder(String _folder) {
		List<File> visible_files = new ArrayList<>();
		int pos = -1;
		File temp_root = root_file;
		_folder = _folder.substring(5);
		
		while( (pos = _folder.indexOf("/")) != -1 && !_folder.equals("") ) {
			String file_name = _folder.substring(0, pos + 1);
			_folder = _folder.substring(pos + 1);
			
			if( temp_root.children.containsKey(file_name) )
				temp_root = temp_root.children.get(file_name);
			else
				break;
		}
		
		for( Entry<String, File> entry : temp_root.children.entrySet() )
			visible_files.add(entry.getValue());
 		
		// group by {folder ; file}
		// sort alphabetical groups
		
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
			
			if( !temp_root.children.containsKey(file_name) ) {
				parent_path = temp_root.path + file_name;
				File new_file = new File(parent_path, temp_root);
				temp_root.children.put(file_name, new_file);
				temp_root = new_file;
			} else
				temp_root = temp_root.children.get(file_name);
		}
		
		if( !_path.equals("")  ) {
			parent_path = temp_root.path + _path;
			if( !temp_root.children.containsKey(_path) ) {
				File new_file = new File(parent_path, temp_root);
				temp_root.children.put(_path, new_file);
			}
		}
	}
	
	public void printTree(File _root) {
		File temp_root = _root;
		
		for( Entry<String, File> entry : temp_root.children.entrySet() ) {
			File file = entry.getValue();
			
			System.out.println(file.path + " | " + file.parent.path);
			printTree(file);
		}
	}
	
	public void removeFile(String _file) {
		root_file.children.remove(_file);
		// notify
	}
	
	
	public void removeTree() {
		root_file.children.clear();
	}
}
