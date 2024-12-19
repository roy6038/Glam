package org.roy.bean;

import java.io.Serializable;

public class PettyCash implements Serializable{

	private static final long serialVersionUID = -6765618585791665942L;

	private String date;
	private String detail;
	private String detailType;
	private Integer incomeOrexpense;
	private Integer remain;
	private String editedPerson;
	private boolean hasInvoice;
	private boolean hasReceipt;
	private String note;


	public PettyCash(String date, String detail, String detailType, String incomeOrexpense, String remain,
			String editedPerson, boolean hasInvoice, boolean hasReceipt, String note) {
		super();
		this.date = date;
		this.detail = detail;
		this.detailType = detailType;
		this.incomeOrexpense = Integer.parseInt(incomeOrexpense);
		this.remain = Integer.parseInt(remain);
		this.editedPerson = editedPerson;
		this.hasInvoice = hasInvoice;
		this.hasReceipt = hasReceipt;
		this.note = note;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public Integer getIncomeOrexpense() {
		return incomeOrexpense;
	}

	public void setIncomeOrexpense(Integer incomeOrexpense) {
		this.incomeOrexpense = incomeOrexpense;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}

	public String getEditedPerson() {
		return editedPerson;
	}

	public void setEditedPerson(String editedPerson) {
		this.editedPerson = editedPerson;
	}

	public boolean isHasInvoice() {
		return hasInvoice;
	}

	public void setHasInvoice(boolean hasInvoice) {
		this.hasInvoice = hasInvoice;
	}

	public boolean isHasReceipt() {
		return hasReceipt;
	}

	public void setHasReceipt(boolean hasReceipt) {
		this.hasReceipt = hasReceipt;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "PettyCash [date=" + date + ", detail=" + detail + ", detailType=" + detailType + ", incomeOrexpense="
				+ incomeOrexpense + ", remain=" + remain + ", editedPerson=" + editedPerson + ", hasInvoice="
				+ hasInvoice + ", hasReceipt=" + hasReceipt + ", note=" + note + "]";
	}

	public String[] DetailInfo() {
		boolean hasIncome = detailType.equals("收入");
		return new String[] {date,detail,hasIncome?incomeOrexpense.toString():"",!hasIncome?incomeOrexpense.toString():"",remain.toString(),
				editedPerson,hasInvoice?"Ｖ":"",hasReceipt?"Ｖ":"",note};
	}

}
