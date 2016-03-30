package com.pp.iwm.teledoc.gui;

public class DockImageButton extends ImageButton {

	Dockbar dockbar;
	public String btn_hint;
	
	public DockImageButton(String image_url, String btn_hint, Dockbar dockbar) {
		super(image_url);
		
		this.dockbar = dockbar;
		this.btn_hint = btn_hint;
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
