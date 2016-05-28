package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Response;
import java.util.ArrayList;

public class GetAllGroupActionsResponse extends Response {
	ArrayList<Action> actions = new ArrayList<>();

	public GetAllGroupActionsResponse() {
		super();
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}
	
	
	
}
