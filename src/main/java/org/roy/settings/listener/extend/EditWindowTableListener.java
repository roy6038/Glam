package org.roy.settings.listener.extend;

import java.awt.event.ActionEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.roy.customerData.view.ShoppingMoney;
import org.roy.settings.PopUp;

public class EditWindowTableListener extends PopUp<JTable>{

	private JTable table;
	private ShoppingMoney shoppingMoney;

	public EditWindowTableListener(JTable table) {
		super(table);
		this.table = table;
		jm.removeAll();
		jm.add(menuDelete);
		setpopupsize(jm,120);
	}

	public EditWindowTableListener(JTable table, ShoppingMoney shoppingMoney) {
		super(table);
		this.table = table;
		this.shoppingMoney = shoppingMoney;
		jm.removeAll();
		jm.add(menuDelete);
		setpopupsize(jm,120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(menuDelete)) {
			((DefaultTableModel)table.getModel()).removeRow(currentRow);
			if(shoppingMoney!=null) {
				shoppingMoney.updateRemain(currentRow);
			}
		}
	}


}
