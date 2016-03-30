package com.pp.iwm.teledoc;
	
import com.pp.iwm.teledoc.windows.AppWindow;
import com.pp.iwm.teledoc.windows.LoginWindow;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//new LoginWindow();
		new AppWindow();
	}
	 
	public static void main(String[] args) {
		launch(args);
	}
}
