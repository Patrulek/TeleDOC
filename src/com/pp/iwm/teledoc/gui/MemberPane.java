package com.pp.iwm.teledoc.gui;

import java.util.ArrayList;
import java.util.List;

import com.pp.iwm.teledoc.animations.FadeAnimation;
import com.pp.iwm.teledoc.animations.TranslateAnimation;
import com.pp.iwm.teledoc.layouts.ConfWindowLayout;
import com.pp.iwm.teledoc.objects.Member;
import com.pp.iwm.teledoc.windows.Window;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MemberPane extends Pane {
	
	// =======================================
	// FIELDS
	// =======================================
	
	private ScrollPane scroll_pane;
	private VBox content_pane;

	private ConfWindowLayout layout;
	
	private FadeAnimation fade_animation;
	private TranslateAnimation translate_animation;
	private List<MemberCard> members;
	
	
	// =======================================
	// METHODS 
	// =======================================
	
	public MemberPane(ConfWindowLayout _layout) {
		layout = _layout;
		
		members = new ArrayList<>();
		createLayout();
		addAnimations();
	}
	
	public ConfWindowLayout getLayout() {
		return layout;
	}
	
	private void createLayout() {
		content_pane = new VBox();
		
		scroll_pane = new ScrollPane(content_pane);
		scroll_pane.setPrefSize(222.0, 493.0);
		scroll_pane.getStylesheets().add("/styles/conf_pane.css");
		scroll_pane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll_pane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll_pane.setOnKeyPressed(null);
		
		getChildren().add(scroll_pane);
		
		setOpacity(0.0);
		setVisible(false);
	}
	
	private void addAnimations() {
		fade_animation = new FadeAnimation(this);
		fade_animation.customize(1.0, 0.0, 300, 200);
		
		translate_animation = new TranslateAnimation(this);
		translate_animation.customize(-225.0, 0.0, 200, 200);
	}
	
	public void animateToLeft() {
		translate_animation.playForward();
	}
	
	public void animateToRight() {
		translate_animation.playBackward();
	}
	
	public void show() {
		setVisible(true);
		fade_animation.setOnFinished(null);
		fade_animation.playForward();
	}
	
	public void hide() {
		fade_animation.setOnFinished(ev -> setVisible(false));
		fade_animation.playBackward();
	}
	
	public void addMember(Member _member) {
		MemberCard member_card = new MemberCard(this, _member);
		members.add(member_card);
		System.out.println("DOdano uzytkownika z emailem: " + _member.email);
		content_pane.getChildren().add(member_card);
	}
	
	public void clearMembers() {
		members.clear();
		content_pane.getChildren().clear();
	}
	
	public Member findMember(String _email) {
		for( MemberCard card : members )
			if( card.hasMember(_email) )
				return card.getMember();
		
		// TODO
		return null;
	}
}
