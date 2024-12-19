package org.roy.settings.listener.extend;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JTable;

import org.roy.bean.PettyCash;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.pettyCash.viewFunction.UpdateCashDetail;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.PopUp;

public class PettyCashTableListener extends PopUp<JTable>{

	public PettyCashTableListener(JTable table) {
		super(table);
		jm.removeAll();
		jm.add(menuUpdate);
		jm.addSeparator();
		jm.add(menuDelete);
		setpopupsize(jm,120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(menuUpdate)) {
			new UpdateCashDetail(GlamChamber.pettyCashDetail.getDetailFilterByMonth().get(currentRow));
		}else if(e.getSource().equals(menuDelete)) {
			int con = Functions.showConfirmDialog("確定刪除？","刪除零用金明細");
			if(con == 1) {
				int index = DataFile.pettyCashDetails.indexOf(GlamChamber.pettyCashDetail.getDetailFilterByMonth().get(currentRow));
				PettyCash pcToBeRemoved = DataFile.pettyCashDetails.get(index);
				StringBuilder sb = new StringBuilder();
				String detailForRecordString = sb.append("日期：").append(pcToBeRemoved.getDate()).append("\n明細：").append(pcToBeRemoved.getDetail()).append("\n").append(pcToBeRemoved.getDetailType()).append("：").append(pcToBeRemoved.getIncomeOrexpense()).toString();
				PettyCash cashStartToAdjust = DataFile.pettyCashDetails.get(index == 0?0:index-1);
				int tempRemain = index == 0
						? (cashStartToAdjust.getDetailType().equals("收入")
						  ?cashStartToAdjust.getRemain()-cashStartToAdjust.getIncomeOrexpense():cashStartToAdjust.getRemain()+cashStartToAdjust.getIncomeOrexpense())
						:cashStartToAdjust.getRemain();
				DataFile.pettyCashDetails.remove(index);
				for (int i = index ; i < DataFile.pettyCashDetails.size(); i++) {
					PettyCash editedCash = DataFile.pettyCashDetails.get(i);
					editedCash.setRemain(editedCash.getDetailType().equals("收入")
									? tempRemain + editedCash.getIncomeOrexpense()
									: tempRemain - editedCash.getIncomeOrexpense());
					tempRemain = editedCash.getRemain();
				}
				DataFile.pettyMoneyRemain = tempRemain;
				GlamChamber.pettyCashDetail.showDetailsByMonth();
				HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("零用金","","刪除",detailForRecordString,"","")));
			}
		}
	}



}
