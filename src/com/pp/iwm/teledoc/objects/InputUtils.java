package com.pp.iwm.teledoc.objects;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputUtils {
	
	// key checking
	public static boolean onKeyEnter(KeyEvent _ev) { return _ev.getCode() == KeyCode.ENTER; }
	public static boolean onKeySpace(KeyEvent _ev) { return _ev.getCode() == KeyCode.SPACE; }
	public static boolean onKeyC(KeyEvent _ev) { return _ev.getCode() == KeyCode.C; }
	public static boolean onKeyH(KeyEvent _ev) { return _ev.getCode() == KeyCode.H; }
	public static boolean onKeyM(KeyEvent _ev) { return _ev.getCode() == KeyCode.M; }
	public static boolean onKeyUp(KeyEvent _ev) { return _ev.getCode() == KeyCode.UP; }
	public static boolean onKeyDown(KeyEvent _ev) { return _ev.getCode() == KeyCode.DOWN; }
	public static boolean onKeyLeft(KeyEvent _ev) { return _ev.getCode() == KeyCode.LEFT; }
	public static boolean onKeyRight(KeyEvent _ev) { return _ev.getCode() == KeyCode.RIGHT; }
	public static boolean onAnyArrow(KeyEvent _ev) { return (onKeyUp(_ev) || onKeyDown(_ev) || onKeyRight(_ev) || onKeyLeft(_ev));}
	public static boolean onKey0(KeyEvent _ev) { return _ev.getCode() == KeyCode.DIGIT0; }
	public static boolean onKey1(KeyEvent _ev) { return _ev.getCode() == KeyCode.DIGIT1; }
	public static boolean onKey2(KeyEvent _ev) { return _ev.getCode() == KeyCode.DIGIT2; }
	public static boolean onKey3(KeyEvent _ev) { return _ev.getCode() == KeyCode.DIGIT3; }
	
	
	// modifier checking
	public static boolean withShift(KeyEvent _ev) { return _ev.isShiftDown(); }
	public static boolean withControl(KeyEvent _ev) { return _ev.isControlDown(); } 
	public static boolean withoutModifiers(KeyEvent _ev) { return (!_ev.isShiftDown() && !_ev.isControlDown() && !_ev.isAltDown()); }
}
