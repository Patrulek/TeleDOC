package com.pp.iwm.teledoc.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Utils {
	public static final double BYTES_PER_MEGABYTE = 1048576.0;
	public static final double BYTES_PER_KILOBYTE = 1024.0;
	
	// COLORS
	public static final Color PRIMARY_COLOR = Color.rgb(30, 54, 60);
	public static final Color PRIMARY_DARK_COLOR = Color.rgb(15, 27, 30);
	public static final Color PRIMARY_LIGHT_COLOR = Color.rgb(60, 108, 120);
	public static final Color PRIMARY_VERY_LIGHT_COLOR = Color.rgb(90, 162, 180);
	public static final Color ACCENT_LIGHT_COLOR = Color.rgb(255, 205, 205);
	public static final Color ACCENT_COLOR = Color.rgb(222, 135, 205);
	public static final Color ACCENT_DARK_COLOR = Color.rgb(162, 105, 148);
	public static final Color TEXT_COLOR = Color.rgb(110, 110, 140);
	public static final Color TEXT_DARK_COLOR = Color.rgb(30, 30, 40);
	// 140, 140, 170
	// 160, 160, 200
	public static final Color TEXT_LIGHT_COLOR = Color.rgb(170, 170, 240);
	
	public static final Color ERROR_TEXT_COLOR = Color.rgb(205, 100, 100);
	public static final Color APPROVE_TEXT_COLOR = Color.rgb(100, 205, 100);
	public static final Color TRANSPARENT = Color.rgb(0, 0, 0, 0.0);
	
	// FONTS
	public static final Font TF_FONT_SMALL = Font.font("Helvetica", 10.0);
	public static final Font TF_FONT = Font.font("Helvetica", 14.0);
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
	public static final int ACT_CREATE = 1017;
	public static final int ACT_SEARCH = 1018;
	public static final int ACT_MICROPHONE = 1019;
	public static final int ACT_CAMERA = 1020;
	public static final int ACT_CHAT = 1021;
	public static final int ACT_MEMBERS = 1022;
	public static final int ACT_LINE = 1023;
	public static final int ACT_LINE2 = 1024;
	public static final int ACT_ANNOTATION = 1025;
	public static final int ACT_POINTER = 1026;
	public static final int ACT_DISTANCE = 1027;
	public static final int ACT_IMAGE_PANEL = 1028;
	public static final int ACT_PICK_VIDEO = 1029;
	public static final int ACT_LAYERS = 1030;
	public static final int ACT_LOCK = 1031;
	public static final int ACT_UNLOCK = 1032;
	public static final int ACT_EYE = 1033;
	public static final int ACT_DISABLED = 1034;
	public static final int ACT_DELETE = 1035;
	public static final int ACT_MINIMIZE = 1036;
	public static final int ACT_MAXIMIZE = 1037;
	public static final int ACT_ADD_FOLDER = 1038;
	public static final int ACT_MY_FILES = 1039;
	
	public static final int ACT_EXIT_APP = 1050;
	
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
	public static final String HINT_CREATE = "Stwórz";
	public static final String HINT_SEARCH = "Szukaj";
	public static final String HINT_CREATE_NEW_CONF = "Stwórz now¹ konferencjê";
	public static final String HINT_SEARCH_CONF = "Wyszukaj konferencjê";
	public static final String HINT_UPLOAD_FILE = "Wgraj plik";
	public static final String HINT_DOWNLOAD_FILE = "Pobierz plik";
	public static final String HINT_CREATE_CONF_FROM_FILE = "Stwórz now¹ konferencjê z aktywnego pliku: ";
	public static final String HINT_SEARCH_FILE = "Wyszukaj plik";
	public static final String HINT_HELP = "Pomoc";
	public static final String HINT_LOGOUT = "Wyloguj";
	public static final String HINT_LEAVE_CONF = "Opuœæ konferencjê";
	public static final String HINT_MICROPHONE_ON = "Wy³¹cz mikrofon";
	public static final String HINT_MICROPHONE_OFF = "W³¹cz mikrofon";
	public static final String HINT_CAMERA_ON = "Wy³¹cz kamerê";
	public static final String HINT_CAMERA_OFF = "W³¹cz kamerê";
	public static final String HINT_CHAT = "Czat";
	public static final String HINT_CHAT_NEW = "Czat: nowa wiadomoœæ";
	public static final String HINT_MEMBERS = "Uczestnicy";
	public static final String HINT_MEMBERS_NEW = "Uczestnicy: nowa osoba";
	public static final String HINT_LINE = "Rysuj liniê";
	public static final String HINT_LINE2 = "Rysuj ³aman¹";
	public static final String HINT_ANNOTATION = "Dodaj adnotacjê";
	public static final String HINT_POINTER_ON = "Wy³¹cz wskaŸnik";
	public static final String HINT_POINTER_OFF = "W³¹cz wskaŸnik";
	public static final String HINT_DISTANCE = "Oblicz odleg³oœæ";
	public static final String HINT_IMAGE_PANEL = "Wyœwietl panel z otwartymi obrazami";
	public static final String HINT_PICK_VIDEO = "Oderwij i powiêksz obraz z kamery";
	public static final String HINT_LAYERS = "Warstwy";
	public static final String HINT_LOCK = "Zablokuj panel";
	public static final String HINT_UNLOCK = "Odblokuj panel";
	public static final String HINT_EYE = "Pokaz warstwê";
	public static final String HINT_DISABLED = "Ukryj warstwê";
	public static final String HINT_DELETE = "Usuñ obiekt";
	public static final String HINT_MINIMIZE = "Zwiñ";
	public static final String HINT_MAXIMIZE = "Rozwiñ";
	public static final String HINT_ADD_FOLDER = "Utwórz nowy folder";
	public static final String HINT_MY_FILES = "Wyœwietl pliki wgrane przeze mnie";
	
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
	public static final int IMG_APP_ICON = 2020;
	public static final int IMG_EMAIL_ICON = 2021;
	public static final int IMG_SURNAME_ICON = 2022;
	public static final int IMG_HIDE_PANEL_SMALL = 2023;
	public static final int IMG_HIDE_PANEL = 2024;
	public static final int IMG_PARENT_FOLDER_SMALL = 2025;
	public static final int IMG_MICROPHONE_ON = 2026;
	public static final int IMG_MICROPHONE_OFF = 2027;
	public static final int IMG_CAMERA_ON = 2028;
	public static final int IMG_CAMERA_OFF = 2029;
	public static final int IMG_CHAT = 2030;
	public static final int IMG_CHAT_NEW = 2031;
	public static final int IMG_MEMBERS = 2032;
	public static final int IMG_MEMBERS_NEW = 2033;
	public static final int IMG_LINE = 2034;
	public static final int IMG_LINE2 = 2035;
	public static final int IMG_ANNOTATION = 2036;
	public static final int IMG_POINTER_ON = 2037;
	public static final int IMG_POINTER_OFF = 2038;
	public static final int IMG_DISTANCE = 2039;
	public static final int IMG_IMAGE_PANEL = 2040;
	public static final int IMG_PICK_VIDEO = 2041;
	public static final int IMG_LAYERS = 2042;
	public static final int IMG_LOCK = 2043;
	public static final int IMG_UNLOCK = 2044;
	public static final int IMG_EYE = 2045;
	public static final int IMG_DISABLED = 2046;
	public static final int IMG_DELETE = 2047;
	public static final int IMG_EMPTY_CAMERA = 2048;
	public static final int IMG_MINIMIZE = 2049;
	public static final int IMG_MAXIMIZE = 2050;
	public static final int IMG_ADD_FOLDER = 2051;
	public static final int IMG_ADD_FOLDER_BIG = 2052;
	public static final int IMG_MY_FILES = 2053;
	public static final int IMG_ALL_FILES = 2054;
	
	// other utils
	
	public static boolean isStringEmpty(String _str) {
		return _str.trim().equals("");
	}
	
	public static boolean isTextFieldEmpty(TextField _tf) {
		return _tf.getText().trim().equals("");
	}
	
	public static boolean isTextFieldEqual(TextField _tf, String _text) {
		return _tf.getText().trim().equals(_text);
	}
	
	public static boolean isTextFieldLongerThan(TextField _tf, int _length) {
		return _tf.getText().trim().length() > _length;
	}
	
	public static boolean isTextFieldAnEmail(TextField _tf) {
		String str = _tf.getText().trim();
		
		return str.contains("@") && str.contains(".") && str.lastIndexOf(".") > str.lastIndexOf("@");
	}
	
	public static boolean isFileSizeGreaterThan(File _file, double _megabytes) {
		return _file.length() > _megabytes * BYTES_PER_MEGABYTE;
	}
	
	public static Color mixColors(Color _color1, Color _color2) {
		List<Color> colors = new ArrayList<>();
		colors.add(_color1); colors.add(_color2);
		return mixColors(colors);
	}
	
	public static Color mixColors(List<Color> _colors) {
		if( _colors == null || _colors.isEmpty() )
			return Color.BLACK;
		
		double r = 0.0, g = 0.0, b = 0.0, a = 0.0;
		int s = _colors.size();
		
		for( Color c : _colors ) {
			r += c.getRed();
			g += c.getGreen();
			b += c.getBlue();
			a += c.getOpacity();
		}
		
		return new Color(r / s, g / s, b / s, a / s);
	}
	
	public static String changeStringToFolderName(String _str) {
		String folder_name = _str;
		folder_name = folder_name.replace(' ', '_');
		folder_name = folder_name.replace("/", "");
		folder_name = folder_name.concat("/");
		
		return folder_name;
	}
}
