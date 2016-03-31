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
	
	public File(String path, File parent) {
		this.path = path;
		children = new HashMap<>();
		this.parent = parent;
		
		int length = path.length();
		int pos = path.lastIndexOf("/");
		
		is_folder = (pos == length - 1) ? true : false;
		
		if( is_folder ) {
			int pos2 = path.lastIndexOf("/", pos - 1);
			this.name = path.substring(pos2 + 1);
			//this.parent_path = path.substring(0, pos2 + 1);
		} 
		else { 
			this.name = path.substring(pos + 1);
			//this.parent_path = path.substring(0, pos + 1);
		}
		
		//System.out.println(this.path + " | " + is_folder + " | " + this.name + " | ");// + this.parent_path);
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof File ) {
			File f = (File)obj;
			
			if( this.path.equals(f.path) )
				return true;
		}
		
		return false;
	}
	
	//private void
}
