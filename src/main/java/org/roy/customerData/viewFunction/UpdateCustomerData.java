package org.roy.customerData.viewFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.roy.bean.Customer;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;

public class UpdateCustomerData extends AddCustomerData {
	private static final long serialVersionUID = 1L;

	private Customer oldInfo;

	public UpdateCustomerData(Customer oldInfo) {
		super();
		this.oldInfo = oldInfo;
		btn_confirm.setText("更改");
		setInput(oldInfo);
	}

	@Override
	public void confirm() {
		List<String> updateCustomerInfo = getUpdateInfoList();
		List<String> oldCustomerInfo = getOldInfoList();
		Customer newInfo = oldInfo;

		newInfo.setName(cus_name.getText());
		newInfo.setBirthDate(checkBoxUnknown.isSelected()?"不詳":birthDateEditor.getText().replaceAll(removeYearRegex,""));
		newInfo.setPhoneNumber(cus_phone.getText());
		newInfo.setFacebook(cus_facebook.getText());
		newInfo.setLineOrWechat(cus_lineOrWechat.getText());
		newInfo.setAddress(cus_address.getText());
		newInfo.setEmail(cus_email.getText());
		newInfo.setGender(getGender());

		if(!newInfo.getId().equals(cus_id.getText())) {
			DataFile.customer.remove(newInfo.getId());
			newInfo.setId(cus_id.getText());
			DataFile.customer.put(newInfo.getId(),newInfo);
		}

		StringBuilder sb = new StringBuilder();
		for(int i = 0;i<oldCustomerInfo.size();i++) {
			if(!updateCustomerInfo.get(i).equals(oldCustomerInfo.get(i))) {
				sb.append(Const.COLUMNS_FOR_CUSTOMERDATA[i]).append("：").append(oldCustomerInfo.get(i)).append("->").append(updateCustomerInfo.get(i)).append("。 ");
			}
		}
		GlamChamber.customer_data.setSearchText(newInfo.getId());
		GlamChamber.customer_data.searchClick();
		HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("顧客資料",newInfo.getId(),"更改",sb.toString(),"","")));
	}

	public List<String> getOldInfoList(){
		return Arrays.asList(oldInfo.getId(),oldInfo.getName(),oldInfo.getBirthDate(),oldInfo.getPhoneNumber(),
				oldInfo.getFacebook(),oldInfo.getLineOrWechat(),oldInfo.getAddress(),oldInfo.getEmail(),oldInfo.getGender());
	}

	public List<String> getUpdateInfoList(){
		return Arrays.asList(cus_id.getText(),cus_name.getText(),checkBoxUnknown.isSelected()?"不詳":birthDateEditor.getText().replaceAll(removeYearRegex,""),
				cus_phone.getText(),cus_facebook.getText(),cus_lineOrWechat.getText(),
				cus_address.getText(),cus_email.getText(),getGender());
	}

	@Override
	public boolean isRepeatedID() {

		boolean sameKey = oldInfo.getId().equals(cus_id.getText());
		if(!sameKey) {
			return DataFile.customer.containsKey(cus_id.getText());
		}

		return false;
	}


}
