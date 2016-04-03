package com.pp.iwm.teledoc.gui;

public class DockImageButton extends ImageButton {

	Dockbar dockbar;
	
	public DockImageButton(Integer _image_key, String _hint, Integer _action, Dockbar _dockbar) {
		super(_image_key, _hint, _action);
		
		dockbar = _dockbar;
	}
	
	protected void onMouseEntered() {
		super.onMouseEntered();
		dockbar.onIconMouseEntered(this.getLayoutX());
	}
	
	protected void onMouseExited() {
		super.onMouseExited();
		dockbar.onIconMouseExited();
	}
}
