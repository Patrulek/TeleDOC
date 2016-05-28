package com.pp.iwm.teledoc.network.packets.images;

import com.pp.iwm.teledoc.network.packets.Response;

public class ConfirmSendImageResponse extends Response {

	public ConfirmSendImageResponse() {
		super();
	}

	public ConfirmSendImageResponse(boolean answer, String message) {
		super(answer, message);
	}

	public ConfirmSendImageResponse(boolean answer) {
		super(answer);
	}
}
