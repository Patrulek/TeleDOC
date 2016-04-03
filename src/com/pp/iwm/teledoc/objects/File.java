package com.pp.iwm.teledoc.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class File {
	
	public String name;
	public boolean is_folder;
	public String path;
	public File parent = null;
	public Map<String, File> children = null;
	
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
			//parent_path = path.substring(0, pos2 + 1);
		} 
		else { 
			name = path.substring(pos + 1);
			//parent_path = path.substring(0, pos + 1);
		}
		
		//System.out.println(path + " | " + is_folder + " | " + name + " | ");// + parent_path);
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
	
	//private void
}
