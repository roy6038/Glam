package org.roy.settings.listener.extend;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.roy.customerData.function.CustomizedPhotoCache;
import org.roy.customerData.view.ShoppingMoney;
import org.roy.customerData.viewFunction.UpdateCustomerData;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.DataFile;
import org.roy.settings.ExportAsExcel;
import org.roy.settings.Functions;
import org.roy.settings.PopUp;
import org.roy.settings.Preferences;

public class CustomerDataTableActionListener extends PopUp<JTable>{
	private JTable table;
	private JMenuItem menuCheckMoney;
	private JMenuItem menuExportData;

	public CustomerDataTableActionListener(JTable table) {
		super(table);
		this.table = table;
		menuExportData = new JMenuItem("匯出顧客資料");
		menuCheckMoney = new JMenuItem("查看購物金");
		menuExportData.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		menuCheckMoney.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		menuExportData.addActionListener(this);
		menuCheckMoney.addActionListener(this);
		jm.removeAll();
		menuCheck.setText("查看購物明細");
		jm.add(menuCheck);
		jm.addSeparator();
		jm.add(menuCheckMoney);
		jm.addSeparator();
		jm.add(menuExportData);
		jm.addSeparator();
		jm.add(menuUpdate);
		jm.addSeparator();
		jm.add(menuDelete);
		setpopupsize(jm,160);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String key = table.getValueAt(currentRow,0).toString();
		if(e.getSource().equals(menuCheck)) {
			checkDetails();
		} else {
			if(e.getSource().equals(menuDelete)) {
				int con =Functions.showConfirmDialog("確定刪除？","刪除顧客資料");
				if(con==1) {
					if(DataFile.customer.get(key).getImageKey()!=null) {
						DataFile.customer.get(key).getImageKey().forEach(s->new File(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER+File.separator+s)).delete());
					}
					DataFile.customer.remove(key);
					((DefaultTableModel)table.getModel()).removeRow(currentRow);
					HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("顧客資料",key,"刪除","","","")));
				}
			}else if(e.getSource().equals(menuUpdate)) {
				new UpdateCustomerData(DataFile.customer.get(key));
			}else if(e.getSource().equals(menuCheckMoney)) {
				new ShoppingMoney(key);
			}else if(e.getSource().equals(menuExportData)) {
				new ExportAsExcel("customerDataStencil.xlsx",DataFile.customer.get(key));
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2 && !e.isConsumed()) {
			checkDetails();
		}
	}

	public void checkDetails() {
		String key = table.getValueAt(currentRow,0).toString();
		//到購物明細
		Functions.setShopDetails(key);
		GlamChamber.mainForm.showForm(GlamChamber.shopping_details);
		GlamChamber.mainForm.getMenu().runEvent(1,2);
		//到訂製表
		new CustomizedPhotoCache(DataFile.customer.get(key).getImageKey(),key).execute();
	}


}
