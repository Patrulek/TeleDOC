package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.objects.ChatMessage;
import com.pp.iwm.teledoc.utils.InputUtils;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.Window;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ChatPane extends Pane implements ChangeListener<Number> {	// najpierw instance var -> potem przypisanie z arg. -> potem reszta
	
	// ======================================
	// FIELDS
	// ======================================
	
	private FadeAnimation fade_animation;
	
	private ScrollPane scroll_pane;
	private VBox content_pane;
	private TextArea text_area;
	
	private ConfWindowLayout layout;
	
	private List<ChatMessageCard> messages;
	
	// ======================================
	// METHODS
	// ======================================
	
	public ChatPane(ConfWindowLayout _layout) {
		messages = new ArrayList<>();
		layout = _layout;
		
		createLayout();
		addAnimation();
	}
	
	private void createLayout() {
		content_pane = new VBox();
		
		scroll_pane = new ScrollPane(content_pane);
		scroll_pane.setPrefSize(222.0, 493.0);
		scroll_pane.getStylesheets().add("/styles/conf_pane.css");
		scroll_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		text_area = new TextArea();
		text_area.setPromptText("Nowa wiadomoœæ...");
		text_area.setPrefSize(222.0, 64.0);
		text_area.setLayoutY(493.0);
		text_area.setWrapText(true);
		text_area.setFont(Utils.TF_FONT_SMALL);
		text_area.setStyle("-fx-text-fill: rgb(210, 210, 240);");
		
		getChildren().add(scroll_pane);
		getChildren().add(text_area);
		
		Platform.runLater(() -> text_area.lookup(".content").setStyle("-fx-background-color: rgb(45, 81, 90);"));

		setOpacity(0.0);
		setVisible(false);
		
		content_pane.heightProperty().addListener(this);
	}
	
	private void addAnimation() {
		fade_animation = new FadeAnimation(this);
		fade_animation.customize(1.0, 0.0, 200, 200);
	}
	
	public void addMessage(ChatMessage _message) {
		if( _message == null )
			return;
		
		if( !isVisible() )
			layout.ibtn_chat.switchOff();
		
		ChatMessageCard message_card = new ChatMessageCard(this, _message);
		messages.add(message_card);
		content_pane.getChildren().add(message_card);
		
		if( _message.isMyMessage() )
			message_card.setOddStyle();
		else
			message_card.setEvenStyle();
	}
	
	public void addMessages(List<ChatMessage> _messages) {
		if( _messages == null || _messages.isEmpty() )
			return;
		
		List<ChatMessageCard> message_cards = new ArrayList<>();
		
		for( int i = 0; i < _messages.size(); i++ ) {
			ChatMessageCard card = new ChatMessageCard(this, _messages.get(i));
			
			if( i % 2 == 0 )
				card.setEvenStyle();
			else
				card.setOddStyle();
			
			message_cards.add(card);
		}
		
		content_pane.getChildren().addAll(message_cards);
	}
	
	public void clearMessages() {
		messages.clear();
		content_pane.getChildren().clear();
	}
	
	public void show() {
		setVisible(true);
		layout.ibtn_chat.switchOn();
		fade_animation.setOnFinished(null);
		fade_animation.playForward();
		text_area.requestFocus();
	}
	
	public void hide() {
		fade_animation.setOnFinished(ev -> setVisible(false));
		fade_animation.playBackward();
	}
	
	public void goToTop() {
		scroll_pane.setVvalue(0.0);
	}
	
	public void goToBottom() {
		scroll_pane.setVvalue(1.0);
	}
	
	public void requestFocusForTextArea() {
		text_area.requestFocus();
	}
	
	public boolean isTextAreaFocused() {
		return text_area.isFocused();
	}
	
	public void appendLine() {
		text_area.appendText("\n");
	}

	@Override
	public void changed(ObservableValue<? extends Number> _observable, Number _old, Number _new) {
		scroll_pane.setVvalue(1.0);
	}
	
	public TextArea getTextArea() {
		return text_area;
	}
	
	public void clearTextArea() {
		text_area.setText("");
	}
	
	public String getTextFromTextArea() {
		return text_area.getText();
	}
}
