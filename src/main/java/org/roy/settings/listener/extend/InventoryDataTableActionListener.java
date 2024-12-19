package org.roy.settings.listener.extend;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.roy.bean.Inventory;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.inventory.viewFunction.AddInventory;
import org.roy.inventory.viewFunction.InputOutputJY_CY;
import org.roy.inventory.viewFunction.InputOutputOD_BAG;
import org.roy.inventory.viewFunction.UpdateInventory;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.PopUp;

public class InventoryDataTableActionListener extends PopUp<JTable>{

	private JTable table;
	private JMenuItem menuInput;
	private JMenuItem menuOutput;
	private JMenuItem copyInventory;

	public InventoryDataTableActionListener(JTable table) {
		super(table);
		this.table = table;
		jm.removeAll();
		menuInput = new JMenuItem("入庫");
		menuOutput = new JMenuItem("出庫");
		copyInventory = new JMenuItem("複製庫存");
		menuInput.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		menuOutput.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		copyInventory.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		copyInventory.addActionListener(this);
		menuInput.addActionListener(this);
		menuOutput.addActionListener(this);
		jm.add(menuInput);
		jm.addSeparator();
		jm.add(menuOutput);
		jm.addSeparator();
		jm.add(copyInventory);
		jm.addSeparator();
		jm.add(menuUpdate);
		jm.addSeparator();
		jm.add(menuDelete);
		setpopupsize(jm,120);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String key = table.getValueAt(currentRow,0).toString();
		if(e.getSource().equals(menuUpdate)) {
			new UpdateInventory(key,DataFile.inventory.get(table.getValueAt(currentRow,0)));
		}else if(e.getSource().equals(menuDelete)) {
			int con = Functions.showConfirmDialog("確定刪除？","刪除庫存資料");
			if(con == 1) {
				String name = DataFile.inventory.get(key).getName();
				DataFile.inventory.remove(table.getValueAt(currentRow,0));
				((DefaultTableModel)table.getModel()).removeRow(currentRow);
				HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("庫存",key,"刪除","品名："+name,"","")));
			}
		}else if(e.getSource().equals(menuInput)) {

			switch (key.substring(0,2)) {
			case "OD":
			case "BA":
			case "客訂":
				new InputOutputOD_BAG(key,menuInput,menuOutput);
				break;
			case "CY":
			case "JY":
				new InputOutputJY_CY(key,menuInput,menuOutput);
				break;
			}

		}else if(e.getSource().equals(menuOutput)) {
			switch (key.substring(0,2)) {
			case "OD":
			case "BA":
			case "客訂":
				if(DataFile.inventory.get(key).getAmount()>0) {
					InputOutputOD_BAG outOD_BAG=new InputOutputOD_BAG(key,menuInput,menuOutput);
					outOD_BAG.setBtn_ConfirmText();
				}else {
					JOptionPane.showMessageDialog(null,"數量為零，無法出庫");
				}
				break;
			case "CY":
			case "JY":
				if(DataFile.inventory.get(key).getAmount()>0) {
					InputOutputJY_CY outJY_CY=new InputOutputJY_CY(key,menuInput,menuOutput);
					outJY_CY.setBtn_ConfirmText();
				}else {
					JOptionPane.showMessageDialog(null,"數量為零，無法出庫");
				}
				break;
			}
		}else if(e.getSource().equals(copyInventory)) {
			Inventory in = DataFile.inventory.get(table.getValueAt(currentRow,0));
			AddInventory addIn = new AddInventory();
			addIn.getI_idVar().setSelectedItem(in.getType());
			addIn.getI_idNum().setText(in.getId().replace(in.getType(),""));
			if("JY".equals(in.getType())||"CY".equals(in.getType())) {
				addIn.getJy_CY_inventory().getI_name().setText(in.getName());
			}else {
				addIn.getOd_BAG_inventory().getI_name().setText(in.getName());
			}
		}
	}



}
