package com.pp.iwm.teledoc;
	
import com.pp.iwm.teledoc.windows.LoginWindow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	LoginWindow login = null;
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		login = new LoginWindow();
		primaryStage = login.stage;
		primaryStage.show();
	}
	 
	public static void main(String[] args) {
		launch(args);
	}
}
