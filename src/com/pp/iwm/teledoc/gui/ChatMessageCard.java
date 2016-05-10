package com.pp.iwm.teledoc.gui;

import com.pp.iwm.teledoc.objects.ChatMessage;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ChatMessageCard extends VBox {		// TODO czy chat_pane jest tu w ogóle potrzebne?
	
	// ==========================================
	// FIELDS
	// ==========================================
	
	private ChatMessage chat_message;
	private ChatPane chat_pane;
	private Label lbl_date;
	private Label lbl_username;
	private Label lbl_message;
	
	
	// ==========================================
	// METHODS
	// ==========================================
	
	public ChatMessageCard(ChatPane _chat_pane, ChatMessage _chat_message) {
		chat_pane = _chat_pane;
		chat_message = _chat_message;
		
		createLayout();
	}
	
	private void createLayout() {
		setStyle("-fx-background-color: transparent;");
		setPadding(new Insets(0.0, 4.0, 0.0, 4.0));
		
		lbl_date = new Label(chat_message.getTime().toString().substring(0, 16));
		lbl_date.setPrefSize(200.0, 20.0);
		lbl_date.setLayoutY(5.0);
		lbl_date.setStyle("-fx-text-fill: rgb(160, 160, 200); -fx-font-weight: bold;");
		lbl_date.setFont(Utils.TF_FONT_SMALL);
		
		lbl_username = new Label(chat_message.getUsername());
		lbl_username.setPrefSize(200.0, 10.0);
		lbl_username.setLayoutY(30.0);
		lbl_username.setStyle("-fx-text-fill: rgb(160, 160, 200); -fx-font-weight: bold;");
		lbl_username.setFont(Utils.TF_FONT_SMALL);
		
		lbl_message = new Label(chat_message.getMessage());
		lbl_message.setPrefWidth(200.0); lbl_message.setWrapText(true);
		lbl_message.setLayoutY(55.0);
		lbl_message.setStyle("-fx-text-fill: rgb(160, 160, 200);");
		lbl_message.setFont(Utils.TF_FONT_SMALL);
		
		getChildren().add(lbl_date);
		getChildren().add(lbl_username);
		getChildren().add(lbl_message);
	}
	
	public void setOddStyle() {
		setStyle("-fx-background-color: rgb(30, 54, 60);");
		lbl_date.setStyle("-fx-text-fill: rgb(160, 160, 200); -fx-font-weight: bold;");
		lbl_username.setStyle("-fx-text-fill: rgb(160, 160, 200); -fx-font-weight: bold;");
		lbl_message.setStyle("-fx-text-fill: rgb(160, 160, 200);");
	}
	
	public void setEvenStyle() {
		setStyle("-fx-background-color: rgb(45, 81, 90);");
		lbl_date.setStyle("-fx-text-fill: rgb(210, 210, 240); -fx-font-weight: bold;");
		lbl_username.setStyle("-fx-text-fill: rgb(210, 210, 240); -fx-font-weight: bold;");
		lbl_message.setStyle("-fx-text-fill: rgb(210, 210, 240);");
	}
}
