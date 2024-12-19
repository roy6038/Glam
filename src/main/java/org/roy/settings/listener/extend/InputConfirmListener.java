package org.roy.settings.listener.extend;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.roy.settings.Functions;
import org.roy.settings.GlobalColor;

public class InputConfirmListener<T extends JTextComponent> implements DocumentListener{

	protected T t;



	public InputConfirmListener() {
		super();
	}


	public InputConfirmListener(T t){
		this.t = t;
		changedUpdate(null);
	}


	@Override
	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if(Functions.isNumberic(t.getText(),false,false)) {
			setOutlineNormal();
		}else {
			setOutlineError();
		}
	}

	public void setOutlineError() {
		t.putClientProperty("JComponent.outline", "error");
	}

	public void setOutlineNormal() {
		t.putClientProperty("JComponent.outline",GlobalColor.FocusColor);
	}

}
