package com.pp.iwm.teledoc.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MemberCard extends Pane {
	
	// ==========================================
		// FIELDS
		// ==========================================
		
	//private Member member = null;
	private MemberPane member_pane;
	private Pane camera_image;
	private Label lbl_username;
	private ImageButton ibtn_fullscreen;
		
		
		// ==========================================
		// METHODS
		// ==========================================
		
		public MemberCard(MemberPane _member_pane, String _name, String _surname) {
			member_pane = _member_pane;
			//chat_message = _chat_message;
			createLayout(_name, _surname);
		}
		
		// TODO temp
		private void createLayout(/*temp*/ String _name, String _surname) {
			setStyle("-fx-background-color: transparent;");
			setPadding(new Insets(0.0, 4.0, 0.0, 4.0));
			
			lbl_username = new Label(_name + "\n" + _surname);
			lbl_username.setPrefSize(110.0, 40.0);
			lbl_username.setWrapText(true);
			lbl_username.setLayoutY(30.0); lbl_username.setLayoutX(110.0);
			lbl_username.setStyle("-fx-text-fill: rgb(160, 160, 200); -fx-font-weight: bold; -fx-text-alignment: center;");
			lbl_username.setFont(Utils.TF_FONT_SMALL);

			camera_image = new Pane();
			camera_image.setStyle("-fx-background-color: red;");
			camera_image.setPrefSize(100.0, 100.0);
			
			ibtn_fullscreen = new ImageButton(Utils.IMG_PICK_VIDEO, Utils.HINT_PICK_VIDEO, Utils.ACT_PICK_VIDEO);
			ibtn_fullscreen.setLayoutX(65.0); ibtn_fullscreen.setLayoutY(65.0);
			
			getChildren().add(camera_image);
			getChildren().add(ibtn_fullscreen);
			getChildren().add(lbl_username);
		}
		
		public void setOddStyle() {
			setStyle("-fx-background-color: rgb(30, 54, 60);");
			lbl_username.setStyle("-fx-text-fill: rgb(160, 160, 200); -fx-font-weight: bold;");
		}
		
		public void setEvenStyle() {
			setStyle("-fx-background-color: rgb(45, 81, 90);");
			lbl_username.setStyle("-fx-text-fill: rgb(210, 210, 240); -fx-font-weight: bold;");
		}
}
