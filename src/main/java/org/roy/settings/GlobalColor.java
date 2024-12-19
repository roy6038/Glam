package org.roy.settings;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;

public class GlobalColor {

	public static String menuBackground = colorToHex(UIManager.getColor("List.dropLineColor"));
	public static String menuItemForeground = colorToHex(UIManager.getColor("List.dropCellForeground"));
	public static String selectedColor = colorToHex(UIManager.getColor("List.dropLineColor").darker());
	public static String errorFocusColor = colorToHex(UIManager.getColor("Component.error.focusedBorderColor"));
	public static String FocusColor = colorToHex(UIManager.getColor("Component.focusedBorderColor"));
	public static String CalendarBoardColor = colorToHex(UIManager.getColor("ProgressBar.background"));
	public static String CalendarBackgroundColor = colorToHex(UIManager.getColor("Panel.background"));
	public static String CalendarDisableColor = colorToHex(UIManager.getColor("Button.disabledText"));
	public static String CalendarBoardForegroundColor = colorToHex(UIManager.getColor("ProgressBar.foreground"));

	public static String colorToHex(Color color) {
		return String.format("#%06x", color.getRGB() & 0xFFFFFF).toUpperCase();
	}

	public static void updateColorWhenChangingLAndF() {
		menuBackground = colorToHex(UIManager.getColor("List.dropLineColor"));
		menuItemForeground = colorToHex(UIManager.getColor("List.dropCellForeground"));
		selectedColor = colorToHex(UIManager.getColor("List.dropLineColor").darker());
		errorFocusColor = colorToHex(UIManager.getColor("Component.error.focusedBorderColor"));
		FocusColor = colorToHex(UIManager.getColor("Component.focusedBorderColor"));
		CalendarBoardColor = colorToHex(UIManager.getColor("ProgressBar.background"));
		CalendarBackgroundColor = colorToHex(UIManager.getColor("Panel.background"));
		CalendarDisableColor = colorToHex(UIManager.getColor("Button.disabledText"));
		CalendarBoardForegroundColor = colorToHex(UIManager.getColor("ProgressBar.foreground"));
	}

	public static void updateMenuUiColor(JComponent c,String detail) {
		c.putClientProperty(FlatClientProperties.STYLE, detail);
	}

}
