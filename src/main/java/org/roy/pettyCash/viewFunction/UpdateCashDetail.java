package org.roy.pettyCash.viewFunction;

import java.util.ArrayList;
import java.util.Arrays;

import org.roy.bean.PettyCash;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.pettyCash.view.PettyCashDetail;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.TableSettings;

public class UpdateCashDetail extends AddCashDetail {

	private static final long serialVersionUID = 1099371795852480876L;
	private int editInfoIndex;
	private PettyCash oldInfo;

	public UpdateCashDetail(PettyCash oldInfo) {
		super();
		this.oldInfo = oldInfo;
		setTitle("更改零用金明細");
		btn_confirm.setText("更改");
		int tempRemain = DataFile.pettyMoneyRemain;
		tempRemain = oldInfo.getDetailType().equals("收入") ? oldInfo.getRemain()-oldInfo.getIncomeOrexpense() : oldInfo.getRemain()+oldInfo.getIncomeOrexpense();
		listener.setTempRemain(tempRemain);
		setInputs(oldInfo);
		editInfoIndex = DataFile.pettyCashDetails.indexOf(oldInfo);
	}

	@Override
	public void confirm() {
//		long s = System.currentTimeMillis();
		PettyCash newPc = getPettyCash();
		DataFile.pettyMoneyRemain = newPc.getRemain();
		DataFile.pettyCashDetails.set(editInfoIndex,newPc);
		if(newPc.getRemain()!=oldInfo.getRemain()) {
			for (int i = editInfoIndex + 1; i < DataFile.pettyCashDetails.size(); i++) {
				PettyCash editedDetail = DataFile.pettyCashDetails.get(i);
				editedDetail.setRemain(editedDetail.getDetailType().equals("收入")?DataFile.pettyMoneyRemain+editedDetail.getIncomeOrexpense():DataFile.pettyMoneyRemain-editedDetail.getIncomeOrexpense());
				DataFile.pettyMoneyRemain = editedDetail.getRemain();
			}
		}
		int locate = TableSettings.getVerticalScrollBarValue(PettyCashDetail.pettyMoneyTable);
		GlamChamber.pettyCashDetail.showDetailsByMonth();
		TableSettings.scrollTo(PettyCashDetail.pettyMoneyTable, locate);
		StringBuilder sb = new StringBuilder();
		String[] oldPCash = oldInfo.DetailInfo();
		String[] newPCash = newPc.DetailInfo();
		final String[] columns = Const.COLUMNS_FOR_PETTYMONEY;
		for (int i = 0; i < newPCash.length; i++) {
			if (!oldPCash[i].equals(newPCash[i])) {
				switch(i) {
				case 2:
				case 3:
					if(oldPCash[i].length()==0) {
						break;
					}
					if (!oldInfo.getDetailType().equals(newPc.getDetailType())) {
						String oldType = oldInfo.getDetailType(),newType = newPc.getDetailType();
							sb.append(oldType).append("：").append(oldInfo.getIncomeOrexpense()).append("->")
									.append(newType).append("：").append(newPc.getIncomeOrexpense()).append("。");
					}else {
						sb.append(columns[i]).append("：").append(oldInfo.getIncomeOrexpense()).append("->").append(newPc.getIncomeOrexpense()).append("。");
					}
					break;
				case 6:
					sb.append(columns[i]).append("：").append(oldPCash[i]).append("->").append(columns[i]).append("：").append(newPCash[i]).append("。");
					break;
				case 7:
					sb.append(columns[i]).append("：").append(oldPCash[i]).append("->").append(columns[i]).append("：").append(newPCash[i]).append("。");
					break;
				default:
						sb.append(columns[i]).append("：").append(oldPCash[i]).append("->").append(newPCash[i]).append("。");
					break;
				}

			}
		}
		if(oldInfo.getDate().equals(newPc.getDate())) {
			sb.insert(0,columns[0]+"："+oldInfo.getDate()+"\n");
		}
		HistoryRecords.addRecords(
				new ArrayList<>(Arrays.asList("零用金", "", "更改", sb.toString(), comboBox_person.getSelectedItem().toString(), "")));
//		System.out.println(System.currentTimeMillis()-s);
		dispose();
	}
}
