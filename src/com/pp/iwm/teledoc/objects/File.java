package com.pp.iwm.teledoc.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class File {
	
	// ======================================
	// FIELDS 
	// ======================================
	
	private String name;
	private boolean is_folder;
	private String path;
	private File parent = null;
	private Map<String, File> children = null;
	
	public File(String _path, File _parent) {
		path = _path;
		parent = _parent;
		children = new HashMap<>();

		int length = path.length();
		int pos = path.lastIndexOf("/");
		
		is_folder = (pos == length - 1) ? true : false;
		
		if( is_folder ) {
			int pos2 = path.lastIndexOf("/", pos - 1);
			name = path.substring(pos2 + 1);
		} else
			name = path.substring(pos + 1);
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
		return name;
	}
	
	public File getParent() {
		return parent;
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
