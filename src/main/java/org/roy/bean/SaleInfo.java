package org.roy.bean;

import java.io.Serializable;
import java.util.List;

public class SaleInfo implements Serializable{

	private static final long serialVersionUID = 4443639278429008978L;

	private String name;
	private String discountValueType;
	private String discountValue;
	private boolean isFriend;
	private String lastStepType;
	private String lastStepValue;
	private String paymentMethod;
	private String salePerson;
	private List<Commodity> commodities;
	private String note;
	private Integer consume;
	private Integer remain;

	public SaleInfo() {
		super();
	}

	public SaleInfo(String name, String discountValueType, String discountValue, boolean isFriend, String lastStepType,
			String lastStepValue, String paymentMethod, String salePerson, List<Commodity> commodities, String note,
			Integer consume, Integer remain) {
		super();
		this.name = name;
		this.discountValueType = discountValueType;
		this.discountValue = discountValue;
		this.isFriend = isFriend;
		this.lastStepType = lastStepType;
		this.lastStepValue = lastStepValue;
		this.paymentMethod = paymentMethod;
		this.salePerson = salePerson;
		this.commodities = commodities;
		this.note = note;
		this.consume = consume;
		this.remain = remain;
	}

	public String getConsumeString() {
		StringBuilder s = new StringBuilder();
		if (discountValue.length() != 0) {
			if (discountValueType.equals("購物金付款")) {
				return modified(s).insert(0, "購物金付款：＄").append("\n").append("實際銷售額：＄").append(consume-remain).append("\n")
						.append(remain >= 0 ? "購物金餘額：＄" + remain : "補差額：＄" + Math.abs(remain)).append("\n").append(note).toString();
			}
		}
		return modified(s).append("\n").append(note).toString();
	}

	public StringBuilder modified(StringBuilder s) {
		if (lastStepType.equals("加5%")) {
			s.append(consume < 0 ? "補差額：＄"+Math.abs(Math.round(consume*1.05f)):Math.round(consume*1.05f)).append("(含5%稅金)");
		} else if (lastStepType.equals("無")) {
			s.append(consume < 0 ? "補差額：＄"+Math.abs(consume) : consume);
		} else {
			consume-=Integer.parseInt(lastStepValue);
			s.append(lastStepType).append("：").append(lastStepValue).append("\n").append(consume);
		}
		return s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscountValueType() {
		return discountValueType;
	}

	public void setDiscountValueType(String discountValueType) {
		this.discountValueType = discountValueType;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public boolean isFriend() {
		return isFriend;
	}

	public void setFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}

	public String getLastStepType() {
		return lastStepType;
	}

	public void setLastStepType(String lastStepType) {
		this.lastStepType = lastStepType;
	}

	public String getLastStepValue() {
		return lastStepValue;
	}

	public void setLastStepValue(String lastStepValue) {
		this.lastStepValue = lastStepValue;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getSalePerson() {
		return salePerson;
	}

	public void setSalePerson(String salePerson) {
		this.salePerson = salePerson;
	}

	public List<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public Integer getConsume() {
		return consume;
	}

	public void setConsume(Integer consume) {
		this.consume = consume;
	}

	@Override
	public String toString() {
		return "SaleInfo [name=" + name + ", discountValueType=" + discountValueType + ", discountValue="
				+ discountValue + ", isFriend=" + isFriend + ", lastStepType=" + lastStepType + ", lastStepValue="
				+ lastStepValue + ", paymentMethod=" + paymentMethod + ", salePerson=" + salePerson + ", commodities="
				+ commodities + "]";
	}

	public Object[][] convertBeanToTableInfo() {
		Object[][] info = commodities.stream().map(Commodity::convertBeanToTableInfo).toArray(Object[][]::new);
		info[0][5] = discountValueType.equals("折抵購物金") && discountValue.length() != 0
				? discountValueType + "\n" + discountValue + "\n" + name
				: name;
		info[0][11] = isFriend ? "Ｖ" : "";
		info[0][12] = salePerson;
		switch (paymentMethod) {
		case "現金" -> info[0][7] = getConsumeString();
		case "刷卡" -> info[0][8] = getConsumeString();
		case "匯款" -> info[0][9] = getConsumeString();
		case "官網" -> info[0][10] = getConsumeString();
		}
		return info;
	}

}
