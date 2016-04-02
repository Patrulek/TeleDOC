package com.pp.iwm.teledoc.gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Utils {
	// COLORS
	public static final Color PRIMARY_COLOR = Color.rgb(30, 54, 60);
	public static final Color PRIMARY_DARK_COLOR = Color.rgb(15, 27, 30);
	public static final Color PRIMARY_LIGHT_COLOR = Color.rgb(60, 108, 120);
	public static final Color PRIMARY_VERY_LIGHT_COLOR = Color.rgb(90, 162, 180);
	public static final Color ICONS_COLOR = Color.rgb(255, 255, 255, 0.8);
	public static final Color ACCENT_COLOR = Color.rgb(222, 135, 205);
	public static final Color ACCENT_DARK_COLOR = Color.rgb(162, 105, 148);
	public static final Color TEXT_COLOR = Color.rgb(114, 114, 114);
	public static final Color TEXT_DARK_COLOR = Color.rgb(33, 33, 33);
	public static final Color TEXT_LIGHT_COLOR = Color.rgb(200, 200, 255);
	
	//public static final Color TEXT_PROMPT_COLOR = Color.rgb(, green, blue)
	public static final Color ERROR_TEXT_COLOR = Color.rgb(205, 100, 100);
	public static final Color APPROVE_TEXT_COLOR = Color.rgb(100, 205, 100);
	public static final Color TRANSPARENT = Color.rgb(0, 0, 0, 0.0);
	
	// FONTS
	public static final Font TF_FONT_SMALL = Font.font("Sans-Serif", 12.0);
	public static final Font TF_FONT = Font.font("Sans-Serif", 16.0);
	public static final Font LBL_FONT = Font.font("Verdana", FontWeight.BOLD, 12.0);
	public static final Font LBL_STATUSBAR_FONT = Font.font("Verdana", 10.0);
	
	// BTN_ACTIONS
	public static final int ACT_REGISTER = 1000;
	public static final int ACT_RESET_PASS = 1001;
	public static final int ACT_LOGIN = 1002;
	
	public static final int ACT_BACK_TO_LOGIN = 1003;
	
	public static final int ACT_NEW_CONF = 1004;
	public static final int ACT_FIND_CONF = 1005;
	public static final int ACT_UPLOAD_FILE = 1006;
	public static final int ACT_DOWNLOAD_FILE = 1007;
	public static final int ACT_NEW_CONF_FROM_FILE = 1008;
	public static final int ACT_FIND_FILE = 1009;
	public static final int ACT_SHOW_HELP = 1010;
	public static final int ACT_LOGOUT = 1011;
	public static final int ACT_JOIN_CONF = 1012;
	public static final int ACT_OPEN_CONF = 1013;
	public static final int ACT_CONF_DETAILS = 1014;
	public static final int ACT_PARENT_FOLDER = 1015;
	public static final int ACT_HIDE_PANEL = 1016;
	
	public static final int ACT_EXIT_APP = 1022;
	
	// PROMPTS
	public static final String PROMPT_EMAIL = "Email";
	public static final String PROMPT_PASS = "Has³o";
	public static final String PROMPT_CONF_NAME = "Nazwa konferencji";
	public static final String PROMPT_FILE_NAME = "Nazwa pliku";
	
	// HINTS
	public static final String HINT_CLOSE_APP = "Zamknij program";
	public static final String HINT_REGISTER = "Zarejestruj";
	public static final String HINT_LOGIN = "Zaloguj";
	public static final String HINT_BACK_TO_LOGIN = "Powrót do okna logowania";
	public static final String HINT_RESET_PASS = "Zresetuj has³o";
	public static final String HINT_PARENT_FOLDER = "W górê";
	public static final String HINT_JOIN_CONF = "Do³¹cz do konferencji: ";
	public static final String HINT_CONF_DETAILS = "Szczegó³owe informacje o konferencji: ";
	public static final String HINT_OPEN_CONF = "Otwórz ponownie konferencjê: ";
	public static final String HINT_HIDE_PANEL = "Ukryj panel";
	
	// MESSAGES
	public static final String MSG_INPUT_EMAIL = "Podaj swój email";
	public static final String MSG_YOUR_PASSWORD = "Twoje has³o to: ";
	public static final String MSG_FILL_ALL_FIELDS = "Proszê wype³niæ wszystkie pola";
	public static final String MSG_INCORRECT_MAIL = "Niepoprawny email";
	public static final String MSG_PASS_TOO_SHORT = "Podane has³o jest za krótkie";
	
	// IMAGES
	public static final int IMG_LOGO = 2000;
	public static final int IMG_NEW_CONF_ICON = 2001;
	public static final int IMG_BACK_ICON = 2002;
	public static final int IMG_CURSOR = 2003;
	public static final int IMG_DOWNLOAD_ICON = 2004;
	public static final int IMG_EXIT_APP_ICON = 2005;
	public static final int IMG_SEARCH_FILE_ICON = 2006;
	public static final int IMG_FOLDER_ICON = 2007;
	public static final int IMG_HELP_ICON = 2008;
	public static final int IMG_FILE_IMAGE_ICON = 2009;
	public static final int IMG_LOGIN_ICON = 2010;
	public static final int IMG_LOGOUT_ICON = 2011;
	public static final int IMG_NAME_ICON = 2012;
	public static final int IMG_NEW_CONF_FROM_FILE_ICON = 2013;
	public static final int IMG_OPTIONS_ICON = 2014;
	public static final int IMG_PASSWORD_ICON = 2015;
	public static final int IMG_REGISTER_ICON = 2016;
	public static final int IMG_RESET_PASS_ICON = 2017;
	public static final int IMG_SEARCH_CONF_ICON = 2018;
	public static final int IMG_UPLOAD_ICON = 2019;
}
