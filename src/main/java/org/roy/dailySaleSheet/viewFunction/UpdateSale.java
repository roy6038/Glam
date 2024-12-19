package org.roy.dailySaleSheet.viewFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.roy.bean.Commodity;
import org.roy.bean.SaleInfo;
import org.roy.dailySaleSheet.view.DailySaleTable;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.TableSettings;

public class UpdateSale extends AddNewSale {

	private static final long serialVersionUID = 1L;
	private String oldKey;
	private List<Commodity> com = new ArrayList<>();

	public UpdateSale(SaleInfo saleInfo,String oldKey){
		super();
		this.oldKey = oldKey;
		super.saleInfo = saleInfo;

		List<Commodity> commodities = saleInfo.getCommodities();
		commodities.forEach(co->{
			try {
				com.add(co.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		});

		secondStep.setTotalCommodityList(com);
		secondStep.getSaleSheetCommodityFunctionListener().setCo(com);
		secondStep.setcurrentCommodityNumber(com.size());
		secondStep.setInput(com.get(com.size()-1));
		btn_confirm.setText("更改");

		firstStep.setUpdateInfo(saleInfo);
		lastStep.setDiscountTypeModel();
		lastStep.setUpdateInfo(saleInfo);

	}

	@Override
	public void showSheet(String key) {

		if(!DataFile.saleSheet.containsKey(key)) {
			DataFile.saleSheet.remove(oldKey);
		}

		DataFile.saleSheet.put(key,saleInfo);
		TableSettings.show(DailySaleTable.table_saleSheet,Const.COLUMNS_FOR_SALES_SHEET,
				DataFile.saleSheet.entrySet().stream().map(Map.Entry::getValue).flatMap(s->Arrays.stream(s.convertBeanToTableInfo())).toArray(Object[][]::new),
				Const.SALE_SHEET_COLUMN_SIZE);
	}

	public String getOldKey() {
		return oldKey;
	}


	@Override
	public boolean confirmfirstStep() {
		if(firstStep.getCusName().length()==0) {
			JOptionPane.showMessageDialog(null,"客戶姓名不可為空");
			return false;
		}else if((!oldKey.equals(firstStep.getCusName()))&&DataFile.saleSheet.keySet().contains(firstStep.getCusName())) {
			JOptionPane.showMessageDialog(null,"客戶姓名已存在");
			return false;
		}

		if(!Functions.isNumberic(firstStep.getCommodityAmount().getText(),false,false)) {
			JOptionPane.showMessageDialog(null,"請檢查商品數量");
			return false;
		}

		if(!Functions.isNumberic(firstStep.getDiscountValue().getText(),false,true)) {
			JOptionPane.showMessageDialog(null,"請檢查購物金");
			return false;
		}
		return true;
	}

}
