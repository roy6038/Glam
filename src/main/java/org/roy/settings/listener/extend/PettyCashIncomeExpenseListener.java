package org.roy.settings.listener.extend;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import org.roy.settings.Functions;

public class PettyCashIncomeExpenseListener extends InputConfirmListener<JTextField>{

	private JTextField remain;
	private JTextField incomeOrExpense;
	private JComboBox<String> comboBox;
	private int tempRemain;

	public PettyCashIncomeExpenseListener(JTextField incomeOrExpense,JTextField remain,JComboBox<String> comboBox) {
//		super(incomeOrExpense);
		super.t = incomeOrExpense;
		this.incomeOrExpense = incomeOrExpense;
		this.remain = remain;
		this.comboBox = comboBox;
		changedUpdate(null);
		comboBox.addActionListener(e->{
			changedUpdate(null);
		});
	}


	public int getTempRemain() {
		return tempRemain;
	}

	public void setTempRemain(int tempRemain) {
		this.tempRemain = tempRemain;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if(!Functions.isNumberic(incomeOrExpense.getText(), false, false)) {
			setOutlineError();
			remain.setText(String.valueOf(tempRemain));
		}else {
//			if(incomeOrExpense.getText().length()>18||Long.parseLong(incomeOrExpense.getText())>Integer.MAX_VALUE) {
//				setOutlineError();
//				return;
//			}
			setOutlineNormal();
			switch(comboBox.getSelectedItem().toString()) {
			case "收入"->{
				remain.setText(String.valueOf(tempRemain+Integer.parseInt(incomeOrExpense.getText())));
			}
			case "支出"->{
				remain.setText(String.valueOf(tempRemain-Integer.parseInt(incomeOrExpense.getText())));
			}

			}
		}
	}


}
