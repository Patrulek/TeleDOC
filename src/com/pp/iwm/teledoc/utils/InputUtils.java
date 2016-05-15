package com.pp.iwm.teledoc.utils;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputUtils {
	
	// key checking
	public static boolean onEnter(KeyEvent _ev) { return _ev.getCode() == KeyCode.ENTER; }
	public static boolean onSpace(KeyEvent _ev) { return _ev.getCode() == KeyCode.SPACE; }
	public static boolean onLetterC(KeyEvent _ev) { return _ev.getCode() == KeyCode.C; }
	public static boolean onLetterH(KeyEvent _ev) { return _ev.getCode() == KeyCode.H; }
	public static boolean onLetterM(KeyEvent _ev) { return _ev.getCode() == KeyCode.M; }
	public static boolean onArrowUp(KeyEvent _ev) { return _ev.getCode() == KeyCode.UP; }
	public static boolean onArrowDown(KeyEvent _ev) { return _ev.getCode() == KeyCode.DOWN; }
	public static boolean onArrowLeft(KeyEvent _ev) { return _ev.getCode() == KeyCode.LEFT; }
	public static boolean onArrowRight(KeyEvent _ev) { return _ev.getCode() == KeyCode.RIGHT; }
	public static boolean onAnyArrow(KeyEvent _ev) { return (onArrowUp(_ev) || onArrowDown(_ev) || onArrowRight(_ev) || onArrowLeft(_ev));}
	public static boolean onDigit0(KeyEvent _ev) { return _ev.getCode() == KeyCode.DIGIT0; }
	public static boolean onDigit1(KeyEvent _ev) { return _ev.getCode() == KeyCode.DIGIT1; }
	public static boolean onDigit2(KeyEvent _ev) { return _ev.getCode() == KeyCode.DIGIT2; }
	public static boolean onDigit3(KeyEvent _ev) { return _ev.getCode() == KeyCode.DIGIT3; }
	public static boolean onF4(KeyEvent _ev) { return _ev.getCode() == KeyCode.F4; }
	
	// modifier checking
	public static boolean withShift(KeyEvent _ev) { return _ev.isShiftDown(); }
	public static boolean withControl(KeyEvent _ev) { return _ev.isControlDown(); } 
	public static boolean withAlt(KeyEvent _ev) { return _ev.isAltDown(); }
	public static boolean withoutModifiers(KeyEvent _ev) { return (!withShift(_ev) && !withControl(_ev) && !withAlt(_ev)); }
}
