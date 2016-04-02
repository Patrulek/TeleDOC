package com.pp.iwm.teledoc.gui;

public class DockImageButton extends ImageButton {

	Dockbar dockbar;
	
	public DockImageButton(String image_url, String hint, String action, Dockbar dockbar) {
		super(image_url, hint, action);
		
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
