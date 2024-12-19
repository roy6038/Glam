package org.roy.customerData.viewFunction;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.TableSettings;

public class UpdateShopDetail extends AddShopDetails{

	private static final long serialVersionUID = -6780170656883656281L;
	private List<List<String>> shoppingDetailData;
	@SuppressWarnings("unused")
	private int selectedRow;

	public UpdateShopDetail(String key, String date, int inputNum,int selectedRow) {
		super(key, date, inputNum);
		this.setContentPane(super.contentPane);
		this.selectedRow = selectedRow;
		super.remove(comboBox);
		super.remove(btn_next);
		super.remove(btn_previous);
		setTitle("更改");
		shoppingDetailData = DataFile.customer.get(key).getShoppingDetailData().get(date);
		setInfo(shoppingDetailData.get(inputNum));
		btn_confirm.setText("更改");
		btn_confirm.setEnabled(true);
	}

	@Override
	protected void confirm(ActionEvent e) {
		StringBuilder sb = new StringBuilder();
		List<String> inputInfo = getInput();
		for(int i = 0;i<inputInfo.size();i++) {
			if(!(inputInfo.get(i).equals(shoppingDetailData.get(inputNum).get(i)))) {
				sb.append(Const.COLUMNS_FOR_SHOPPINGDETAILS[i]).append("：").append(shoppingDetailData.get(inputNum).get(i)).append("->").append(inputInfo.get(i)).append("。 ");
			}
		}
		HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("購物明細",key+"-"+inputInfo.get(0),"更改","購物明細日期："+date+"。\n"+sb.toString(),"","")));
		shoppingDetailData.set(inputNum, inputInfo);
		int locate = TableSettings.getVerticalScrollBarValue(GlamChamber.shopping_details.getTable_details());
		GlamChamber.shopping_details.comboBox.setSelectedIndex(GlamChamber.shopping_details.comboBox.getSelectedIndex());
		TableSettings.scrollTo(GlamChamber.shopping_details.getTable_details(),locate);
		GlamChamber.shopping_details.setTotalConsume();
		dispose();
	}


}
