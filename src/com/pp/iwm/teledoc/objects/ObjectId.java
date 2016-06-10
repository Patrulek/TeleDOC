package com.pp.iwm.teledoc.objects;

public class ObjectId {
	public int id;
	public int image_id;
	public String owner;
	
	public ObjectId(int _id, int _image_id, String _owner) {
		id = _id;
		image_id = _image_id;
		owner = _owner;
	}

	public ObjectId() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return id + " | " + image_id + " | " + owner;
	}
	
	@Override
	public boolean equals(Object _obj) {
		if( _obj == null || !(_obj instanceof ObjectId) )
			return false;
		
		ObjectId obj = (ObjectId)_obj;
		
		if( obj.id == id && obj.image_id == image_id && obj.owner.equals(owner) )
			return true;
		
		return false;
	}
}