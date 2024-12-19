package org.roy.inventory.viewFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import org.roy.bean.Inventory;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;

public class UpdateInventory extends AddInventory{
	private static final long serialVersionUID = 1L;

	private Inventory info;
	private String oldId;

	public UpdateInventory(String oldId,Inventory info) {
		super();
		this.info = info;
		this.oldId = oldId;
		findIdType(info.getType());

		btn_confirm.setText("更改");
	}

	public void findIdType(String type) {
		i_idVar.setSelectedItem(type);
		i_idNum.setText(oldId.replace(type,""));
		if(type.equals("CY")||type.equals("JY")) {
			jy_CY_inventory.setInput(info);
		}else {
			od_BAG_inventory.setInput(info);
		}
	}

	@Override
	public void confirmDetails() {
		if(i_idNum.getText().length()==0) {
			JOptionPane.showMessageDialog(null,"貨號不可為空");
			return;
		}

		String id =i_idVar.getSelectedItem().toString()+i_idNum.getText();

		if((!oldId.equals(id)) && DataFile.inventory.containsKey(id)) {
			JOptionPane.showMessageDialog(null,"貨號已存在");
			return;
		}
		List<String> event = new ArrayList<>(Arrays.asList("庫存",id,"更改","",""));
		StringBuilder sb = new StringBuilder();
		switch (i_idVar.getSelectedItem().toString()) {

		case "JY":
		case "CY":
			if(jy_CY_inventory.confirm()) {
				Inventory jyCy = jy_CY_inventory.getInventory();
				jyCy.setId(id);
				jyCy.setType(i_idVar.getSelectedItem().toString());
				String[] newInfo = jyCy.getJYCYInfo();
				String[] oldInfo = info.getType().equals("CY")||info.getType().equals("JY")?info.getJYCYInfo():info.getODBAGInfo();
				editUpdateDetail(sb, newInfo, oldInfo,Const.COLUMNS_FOR_JY_AND_CY);
				event.add(3,sb.toString());
				if(!oldId.equals(id)) {
					DataFile.inventory.remove(oldId);
				}
				DataFile.inventory.put(id,jyCy);
//				TableSettings.show(InventoryData.table_inventoryData,Const.COLUMNS_FOR_JY_AND_CY,
//						new Object[][] {DataFile.inventory.get(id).getJYCYInfo()}, Const.JY_CY_COLUMN_SIZE);
				break;
			}else {
				return;
			}

		case "OD":
		case "BAG":
		case "客訂":
			if(od_BAG_inventory.confirm()) {
				Inventory odBag = od_BAG_inventory.getInventory();
				odBag.setId(id);
				odBag.setType(i_idVar.getSelectedItem().toString());
				String[] newInfo = odBag.getODBAGInfo();
				String[] oldInfo = info.getType().equals("CY")||info.getType().equals("JY")?info.getJYCYInfo():info.getODBAGInfo();
				editUpdateDetail(sb, newInfo, oldInfo,Const.COLUMNS_FOR_OD_AND_BAG);

				event.add(3,sb.toString());
				if(!oldId.equals(id)) {
					DataFile.inventory.remove(oldId);
				}
				DataFile.inventory.put(id,odBag);
//				TableSettings.show(InventoryData.table_inventoryData,Const.COLUMNS_FOR_OD_AND_BAG,
//						new Object[][] {DataFile.inventory.get(id).getODBAGInfo()}, Const.OD_BAG_COLUMN_SIZE);
				break;
			}else {
				return;
			}
		}

		GlamChamber.inventoryData.flushTable(id);
		HistoryRecords.addRecords(event);
		dispose();
	}



	private void editUpdateDetail(StringBuilder sb, String[] newInfo, String[] oldInfo,String[] columnType) {
		if(oldInfo.length!=newInfo.length){
			for(int i =0;i<newInfo.length;i++) {
				if(i==0) {
					sb.append(columnType[i]).append("：").append(oldInfo[i]).append(" -> ").append(newInfo[i]).append("。");
				}else {
					sb.append(columnType[i]).append("：").append(newInfo[i]).append("。");
				}
			}
		}else {
			for(int i=0;i<newInfo.length;i++) {
				if(!oldInfo[i].equals(newInfo[i])) {
					sb.append(columnType[i]).append("：").append(oldInfo[i]).append(" -> ").append(newInfo[i]).append("。");
				}
			}
		}
	}


}
