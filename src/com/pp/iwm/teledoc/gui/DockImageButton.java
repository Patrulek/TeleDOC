package com.pp.iwm.teledoc.gui;

public class DockImageButton extends ImageButton {

	Dockbar dockbar;
	
	public DockImageButton(Integer image_key, String hint, Integer action, Dockbar dockbar) {
		super(image_key, hint, action);
		
		this.dockbar = dockbar;
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
