package com.pp.iwm.teledoc;
	
import com.pp.iwm.teledoc.windows.LoginWindow;
import com.pp.iwm.teledoc.windows.RegisterWindow;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		new LoginWindow();
	}
	 
	public static void main(String[] args) {
		launch(args);
	}
}
