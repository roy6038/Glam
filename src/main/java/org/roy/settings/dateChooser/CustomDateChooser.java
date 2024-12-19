package org.roy.settings.dateChooser;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class CustomDateChooser extends JDateChooser{

	private static final long serialVersionUID = 1362534078575740208L;
	private Color darkgreen = new Color(0,150,0);

	public CustomDateChooser() {
		super();
	}

	public CustomDateChooser(Date date, String dateFormatString, IDateEditor dateEditor) {
		super(date, dateFormatString, dateEditor);
	}

	public CustomDateChooser(Date date, String dateFormatString) {
		super(date, dateFormatString);
		editTextField();
	}

	public CustomDateChooser(Date date) {
		super(date);
	}

	public CustomDateChooser(IDateEditor dateEditor) {
		super(dateEditor);
	}

	public CustomDateChooser(JCalendar jcal, Date date, String dateFormatString, IDateEditor dateEditor) {
		super(jcal, date, dateFormatString, dateEditor);
		editTextField();
	}

	public CustomDateChooser(String datePattern, String maskPattern, char placeholder) {
		super(datePattern, maskPattern, placeholder);
	}

	public void editTextField() {
		JTextField jTextField =  (JTextField)((JSpinner)getJCalendar().getYearChooser().getSpinner()).getEditor();
		jTextField.removeCaretListener(jTextField.getCaretListeners()[0]);
		jTextField.addCaretListener(e->{
			if(!(jTextField.getForeground().equals(UIManager.getColor("ComboBox.foreground")))) {
				jTextField.setForeground(UIManager.getColor("ComboBox.foreground"));
				jTextField.repaint();
			}
		});
		jTextField.addPropertyChangeListener("foreground", event -> {
            if (Color.BLACK.equals(event.getNewValue())||darkgreen.equals(event.getNewValue())) {
            	jTextField.setForeground(UIManager.getColor("ComboBox.foreground"));
            }
        });
		JTextFieldDateEditor txtFld = (JTextFieldDateEditor)getDateEditor();
		txtFld.setEnabled(false);
		txtFld.setFont(new Font("Lucida Grande", Font.BOLD, 16));
//        txtFld.addPropertyChangeListener("foreground", event -> {
//            if (Color.BLACK.equals(event.getNewValue())||darkgreen.equals(event.getNewValue())) {
//
//                txtFld.setForeground(UIManager.getColor("ComboBox.foreground"));
//            }
//        });
        getCalendarButton().addActionListener(e->{
        	getJCalendar().getDayChooser().setDecorationBackgroundColor(UIManager.getColor("Button.background"));
        	setFont(new Font("Lucida Grande", Font.BOLD, 16));
        });
	}

	public Date getLastSelectedDate() {
		return lastSelectedDate;
	}

}
