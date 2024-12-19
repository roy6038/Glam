package org.roy.settings.listener.extend;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JTable;

import org.roy.dailySaleSheet.view.DailySaleTable;
import org.roy.dailySaleSheet.viewFunction.UpdateSale;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.TableSettings;

public class SaleSheetTableListener extends ShoppingDetailTableActionListener{

	private JTable table;

	public SaleSheetTableListener(JTable table) {
		super(table);
		this.table = table;
		jm.removeAll();
		jm.add(menuUpdate);
		jm.addSeparator();
		jm.add(menuDelete);
		menuDelete.setText("刪除整筆銷售");
		setpopupsize(jm,160);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String key = getKey();
		if(e.getSource().equals(menuUpdate)) {
			new UpdateSale(DataFile.saleSheet.get(key),key);
		}else if(e.getSource().equals(menuDelete)) {
			if(Functions.showConfirmDialog("確定刪除整筆銷售？","刪除")==1) {
				DataFile.saleSheet.remove(key);
				TableSettings.show(DailySaleTable.table_saleSheet,Const.COLUMNS_FOR_SALES_SHEET,
						DataFile.saleSheet.entrySet().stream().map(Map.Entry::getValue).flatMap(s->Arrays.stream(s.convertBeanToTableInfo())).toArray(Object[][]::new)
						, Const.SALE_SHEET_COLUMN_SIZE);
			}
		}
	}

	public String getKey() {
		String key = null;
		for(int i = currentRow;i>=0;i--) {
			if(table.getValueAt(i,5).toString().length()==0) {
				continue;
			}else {
				key = table.getValueAt(i,5).toString().contains("\n")
						?table.getValueAt(i,5).toString().split("\n")[2]:table.getValueAt(i,5).toString();
				return key;
			}
		}
		return key;
	}


}
