package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.objects.Member;
import com.pp.iwm.teledoc.utils.Utils;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MemberCard extends Pane {
	
	// ==========================================
		// FIELDS
		// ==========================================
		
	private Member member;
	private MemberPane member_pane;
	private ImageView camera_image;
	private Label lbl_username;
	private ImageButton ibtn_fullscreen;
		
		
		// ==========================================
		// METHODS
		// ==========================================
	
		public Member getMember() {
			return member;
		}
		
		public boolean hasMember(String _email) {
			return member.email.equals(_email);
		}
		
		public MemberCard(MemberPane _member_pane, Member _member) {
			member_pane = _member_pane;
			member = _member;
			createLayout();
		}
		
		private void createLayout() {
			setStyle("-fx-background-color: transparent;");
			setPadding(new Insets(0.0, 4.0, 0.0, 4.0));
			setPrefHeight(100.0);
			
			lbl_username = new Label(member.name + "\n" + member.surname);
			lbl_username.setPrefSize(110.0, 40.0);
			lbl_username.setWrapText(true);
			lbl_username.setLayoutY(18.0); lbl_username.setLayoutX(110.0);
			lbl_username.setStyle("-fx-text-fill: rgb(160, 160, 200); -fx-font-weight: bold; -fx-text-alignment: center;");
			lbl_username.setFont(Utils.TF_FONT_SMALL);

			camera_image = new ImageView(ImageManager.instance().getImage(Utils.IMG_EMPTY_CAMERA));
			camera_image.setStyle("-fx-background-color: transparent;");
			camera_image.setFitHeight(75.0); camera_image.setFitWidth(100.0);
			
			ibtn_fullscreen = new ImageButton(Utils.IMG_PICK_VIDEO, Utils.HINT_PICK_VIDEO, Utils.ACT_PICK_VIDEO);
			ibtn_fullscreen.setLayoutX(74.0); ibtn_fullscreen.setLayoutY(54.0);
			ibtn_fullscreen.addEventHandler(ActionEvent.ACTION, ev -> member_pane.getLayout().openCameraWindow(this));
			
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
