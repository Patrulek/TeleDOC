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
	
	public List<File> getFilesForFolder(String folder) {
		List<File> visible_files = new ArrayList<>();
		int pos = -1;
		File temp_root = root_file;
		folder = folder.substring(5);
		
		while( (pos = folder.indexOf("/")) != -1 && !folder.equals("") ) {
			String file_name = folder.substring(0, pos + 1);
			folder = folder.substring(pos + 1);
			
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
	
	public void addFile(String path) {
		File temp_root = root_file;
		int pos = -1;
		String parent_path = "root/";
		String file_name = "";
		path = path.substring(5);
		
		while( (pos = path.indexOf("/")) != -1 ) {
			file_name = path.substring(0, pos + 1);
			path = path.substring(pos + 1);
			
			if( !temp_root.children.containsKey(file_name) ) {
				parent_path = temp_root.path + file_name;
				File new_file = new File(parent_path, temp_root);
				temp_root.children.put(file_name, new_file);
				temp_root = new_file;
			} else
				temp_root = temp_root.children.get(file_name);
		}
		
		if( !path.equals("")  ) {
			parent_path = temp_root.path + path;
			if( !temp_root.children.containsKey(path) ) {
				File new_file = new File(parent_path, temp_root);
				temp_root.children.put(path, new_file);
			}
		}
	}
	
	public void printTree(File root) {
		File temp_root = root;
		
		for( Entry<String, File> entry : temp_root.children.entrySet() ) {
			File file = entry.getValue();
			
			System.out.println(file.path + " | " + file.parent.path);
			printTree(file);
		}
	}
	
	public void removeFile(String file) {
		root_file.children.remove(file);
		// notify
	}
	
	
	public void removeTree() {
		root_file.children.clear();
	}
}
