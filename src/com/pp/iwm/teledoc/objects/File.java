package com.pp.iwm.teledoc.objects;

import java.util.HashMap;
import java.util.Map;

public class File {		// TODO struktura a nie obiekt
	
	// ======================================
	// FIELDS 
	// ======================================

	private String path;
	private String filename;
	private boolean is_folder;
	private File parent;
	private Map<String, File> children;
	
	public File(String _path, File _parent) {
		path = _path;
		parent = _parent;
		children = new HashMap<>();

		int length = path.length();
		int pos = path.lastIndexOf("/");
		
		is_folder = (pos == length - 1) ? true : false;
		
		if( is_folder ) {
			int pos2 = path.lastIndexOf("/", pos - 1);
			filename = path.substring(pos2 + 1);
		} else
			filename = path.substring(pos + 1);
	}
	
	public void removeFile(String _file_path) {
		if( !is_folder )
			return;
		
		if( children.containsKey(_file_path) )
			children.remove(_file_path);
	}
	
	// TODO requires testing
	public void removeFile(File _file) {
		if( !is_folder )
			return;
		
		if( children.containsValue(_file) )
			children.remove(_file.getName());
	}
	
	public String getPath() {
		return path;
	}
	
	public Map<String, File> getChildren() {
		return children;
	}
	
	public boolean isFolder() {
		return is_folder;
	}
	
	public String getName() {
		return filename;
	}
	
	public File getParent() {
		return parent == null ? this : parent;
	}
	
	@Override
	public boolean equals(Object _obj) {
		if( _obj instanceof File ) {
			File f = (File)_obj;
			
			if( path.equals(f.path) )
				return true;
		}
		
		return false;
	}
}
