package com.pp.iwm.teledoc;
	
import com.pp.iwm.teledoc.objects.ImageManager;
import com.pp.iwm.teledoc.utils.Utils;
import com.pp.iwm.teledoc.windows.AppWindow;
import com.pp.iwm.teledoc.windows.ConfWindow;
import com.pp.iwm.teledoc.windows.LoginWindow;
import com.pp.iwm.teledoc.windows.Window;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	// =====================================
	// FIELDS
	// =====================================
	
	Window main_window;
	
	// =====================================
	// METHODS
	// =====================================
	
	@Override
	public void init() {
		loadImages();
	}
	
	@Override
	public void start(Stage _primaryStage) throws Exception {
		main_window = new LoginWindow();
		main_window.show();
	}
	 
	public static void main(String[] _args) {
		launch(_args);
	}
	
	
	private void loadImages() {
		ImageManager.instance().loadImage(Utils.IMG_LOGO, "/assets/teledoc_logo.png");
		ImageManager.instance().loadImage(Utils.IMG_BACK_ICON, "/assets/back_to_login.png");
		ImageManager.instance().loadImage(Utils.IMG_CURSOR, "/assets/cursor.png");
		ImageManager.instance().loadImage(Utils.IMG_DOWNLOAD_ICON, "/assets/download_file.png");
		ImageManager.instance().loadImage(Utils.IMG_EXIT_APP_ICON, "/assets/exit_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_FILE_IMAGE_ICON, "/assets/image_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_FOLDER_ICON, "/assets/folder_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_HELP_ICON, "/assets/help_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_LOGIN_ICON, "/assets/login_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_LOGOUT_ICON, "/assets/logout_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_NAME_ICON, "/assets/name_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_NEW_CONF_FROM_FILE_ICON, "/assets/new_conf_from_file.png");
		ImageManager.instance().loadImage(Utils.IMG_NEW_CONF_ICON, "/assets/add_new_conf.png");
		ImageManager.instance().loadImage(Utils.IMG_OPTIONS_ICON, "/assets/options.png");
		ImageManager.instance().loadImage(Utils.IMG_PASSWORD_ICON, "/assets/password_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_REGISTER_ICON, "/assets/register_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_RESET_PASS_ICON, "/assets/reset_password.png");
		ImageManager.instance().loadImage(Utils.IMG_SEARCH_CONF_ICON, "/assets/search_conf.png");
		ImageManager.instance().loadImage(Utils.IMG_SEARCH_FILE_ICON, "/assets/search_file.png");
		ImageManager.instance().loadImage(Utils.IMG_UPLOAD_ICON, "/assets/upload_file.png");
		ImageManager.instance().loadImage(Utils.IMG_APP_ICON, "/assets/app_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_EMAIL_ICON, "/assets/email_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_SURNAME_ICON, "/assets/surname_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_HIDE_PANEL, "/assets/hide_panel.png");
		ImageManager.instance().loadImage(Utils.IMG_HIDE_PANEL_SMALL, "/assets/hide_panel_small.png");
		ImageManager.instance().loadImage(Utils.IMG_PARENT_FOLDER_SMALL, "/assets/parent_folder.png");
		ImageManager.instance().loadImage(Utils.IMG_MICROPHONE_ON, "/assets/microphone_on.png");
		ImageManager.instance().loadImage(Utils.IMG_MICROPHONE_OFF, "/assets/microphone_off.png");
		ImageManager.instance().loadImage(Utils.IMG_CAMERA_ON, "/assets/camera_on.png");
		ImageManager.instance().loadImage(Utils.IMG_CAMERA_OFF, "/assets/camera_off.png");
		ImageManager.instance().loadImage(Utils.IMG_CHAT, "/assets/chat_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_CHAT_NEW, "/assets/chat_new_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_MEMBERS, "/assets/members_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_MEMBERS_NEW, "/assets/members_icon_new.png");
		ImageManager.instance().loadImage(Utils.IMG_LINE, "/assets/line_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_LINE2, "/assets/line_icon_2.png");
		ImageManager.instance().loadImage(Utils.IMG_ANNOTATION, "/assets/add_annotation.png");
		ImageManager.instance().loadImage(Utils.IMG_POINTER_ON, "/assets/pointer_on.png");
		ImageManager.instance().loadImage(Utils.IMG_POINTER_OFF, "/assets/pointer_off.png");
		ImageManager.instance().loadImage(Utils.IMG_DISTANCE, "/assets/distance_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_IMAGE_PANEL, "/assets/image_panel_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_PICK_VIDEO, "/assets/pick_video.png");
		ImageManager.instance().loadImage(Utils.IMG_LAYERS, "/assets/layers_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_LOCK, "/assets/lock_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_UNLOCK, "/assets/unlock_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_EYE, "/assets/eye_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_DISABLED, "/assets/disabled_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_DELETE, "/assets/delete_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_EMPTY_CAMERA, "/assets/empty_camera.png");
		ImageManager.instance().loadImage(Utils.IMG_MINIMIZE, "/assets/minimize_icon.png");
		ImageManager.instance().loadImage(Utils.IMG_MAXIMIZE, "/assets/maximize_icon.png");
	}
}
