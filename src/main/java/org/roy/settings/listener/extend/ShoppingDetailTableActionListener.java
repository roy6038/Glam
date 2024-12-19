package org.roy.settings.listener.extend;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.roy.customerData.viewFunction.UpdateShopDetail;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.PopUp;

public class ShoppingDetailTableActionListener extends PopUp<JTable>{
	private JTable table;

	public ShoppingDetailTableActionListener(JTable table) {
		super(table);
		jm.removeAll();
		jm.add(menuUpdate);
		jm.addSeparator();
		jm.add(menuDelete);
		this.table = table;
		setpopupsize(jm,120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> detailDatecomboBox = GlamChamber.shopping_details.comboBox;
		if(e.getSource().equals(menuUpdate)) {
			if(detailDatecomboBox.getSelectedIndex()==0) {
				new UpdateShopDetail(GlamChamber.shopping_details.getKey(),getDetailDate(),getEditingDetailLocate(),currentRow);
			}else {
				new UpdateShopDetail(GlamChamber.shopping_details.getKey(),GlamChamber.shopping_details.getSelectedDate(),currentRow,currentRow);
			}
		}else if(e.getSource().equals(menuDelete)) {
			int con = Functions.showConfirmDialog("確定刪除？","刪除購物明細");
			if(con == 1) {
				Map<String, List<List<String>>> shoppingDetailData = DataFile.customer.get(GlamChamber.shopping_details.getKey()).getShoppingDetailData();
				String selectedDate = null;
				int removeLocate = 0;

				if(detailDatecomboBox.getSelectedIndex()==0) {
					selectedDate = getDetailDate();
					removeLocate = getEditingDetailLocate();
				}else {
					selectedDate = GlamChamber.shopping_details.getSelectedDate();
					removeLocate = currentRow;
				}
				String id = shoppingDetailData.get(selectedDate).get(removeLocate).get(0);
				shoppingDetailData.get(selectedDate).remove(removeLocate);
				((DefaultTableModel)table.getModel()).removeRow(removeLocate);
				if(shoppingDetailData.get(selectedDate).size()==0) {
					shoppingDetailData.remove(selectedDate);
					GlamChamber.shopping_details.setComboBoxModel(shoppingDetailData.keySet());
				}

				detailComboBoxSelectingAction(detailDatecomboBox,shoppingDetailData.keySet(),selectedDate);
				GlamChamber.shopping_details.setTotalConsume();
				HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("購物明細",GlamChamber.shopping_details.getKey()+"-"+id,"刪除","購物明細日期："+selectedDate+"。","","")));
			}
		}
	}

	private void detailComboBoxSelectingAction(JComboBox<String> comboBox, Set<String> set, String selectedDate) {
		if(comboBox.getItemCount()==0) {
			((DefaultTableModel)GlamChamber.shopping_details.getTable_details().getModel()).setColumnCount(0);
		}else {
			if(comboBox.getSelectedIndex()==0) {
				comboBox.setSelectedIndex(0);
			}else {
				if(set.contains(selectedDate)) {
					comboBox.setSelectedItem(selectedDate);
				}else {
					comboBox.setSelectedIndex(comboBox.getItemCount()-1);
				}
			}
		}
	}

	private int getEditingDetailLocate() {
		for(int i = currentRow;i>=0;i--) {
			if(table.getValueAt(i,0).toString().length()!=0) {
				return currentRow - i;
			}
		}
		return 0;
	}

	private String getDetailDate() {
		String s = null;
		for(int i = currentRow;i>=0;i--) {
			if((s = table.getValueAt(i,0).toString()).length()!=0) {
				return s;
			}
		}
		return s;
	}


}
