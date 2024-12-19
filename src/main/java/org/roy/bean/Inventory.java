package org.roy.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.roy.settings.DataFile;

public class Inventory implements Serializable{

	private static final long serialVersionUID = -3243430578605307991L;

	private String id;
	private String type;
	private String name;
	private String positionId;
	private String position;
	private Integer amount = 0;
	private String unit;
	private Integer price = 0;
	private Integer sampleNum = 0;
	private Integer xXSNum = 0;
	private Integer xSNum = 0;
	private Integer sNum = 0;
	private Integer mNum = 0;
	private Integer lNum = 0;
	private Integer xLNum = 0;
	private Integer xXLNum = 0;
	private Integer flaw = 0;
	private String inventoryValue;
	private String note = "";

	public Inventory() {
		super();
	}

	public Inventory(String name,String positionId,String amount, String unit,
			String price, String note) {
		super();
		this.name = name;
		this.positionId = positionId;
		this.position = DataFile.depotLocate.get(positionId);
		this.amount = strToInt(amount);
		this.unit = unit;
		this.price = strToInt(price);
		setInventoryValue();
		this.note = note;
	}


	public Inventory(String name, String positionId,String unit,String price,
			String sampleNum, String xXSNum, String xSNum, String sNum, String mNum, String lNum,
			String xLNum, String xXLNum, String flaw,String note) {
		super();
		this.name = name;
		this.positionId = positionId;
		this.position = DataFile.depotLocate.get(positionId);
		this.unit = unit;
		this.price = strToInt(price);
		this.sampleNum = strToInt(sampleNum);
		this.xXSNum = strToInt(xXSNum);
		this.xSNum = strToInt(xSNum);
		this.sNum = strToInt(sNum);
		this.mNum = strToInt(mNum);
		this.lNum = strToInt(lNum);
		this.xLNum = strToInt(xLNum);
		this.xXLNum = strToInt(xXLNum);
		this.flaw = strToInt(flaw);
		setAmount();
		setInventoryValue();
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setAmount(String amount) {
		this.amount = strToInt(amount);
	}

	public void setAmount() {
		this.amount = sampleNum+xXSNum+xSNum+sNum+mNum+lNum+xLNum+xXLNum+flaw;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = strToInt(price);
	}

	public Integer getSampleNum() {
		return sampleNum;
	}

	public void setSampleNum(String sampleNum) {
		this.sampleNum = strToInt(sampleNum);
	}

	public void setSampleNum(Integer sampleNum) {
		this.sampleNum = sampleNum;
	}

	public Integer getxXSNum() {
		return xXSNum;
	}

	public void setxXSNum(String xXSNum) {
		this.xXSNum = strToInt(xXSNum);
	}

	public void setxXSNum(Integer xXSNum) {
		this.xXSNum = xXSNum;
	}

	public Integer getxSNum() {
		return xSNum;
	}

	public void setxSNum(String xSNum) {
		this.xSNum = strToInt(xSNum);
	}

	public void setxSNum(Integer xSNum) {
		this.xSNum = xSNum;
	}

	public Integer getsNum() {
		return sNum;
	}

	public void setsNum(String sNum) {
		this.sNum = strToInt(sNum);
	}

	public void setsNum(Integer sNum) {
		this.sNum = sNum;
	}


	public Integer getmNum() {
		return mNum;
	}

	public void setmNum(String mNum) {
		this.mNum = strToInt(mNum);
	}

	public void setmNum(Integer mNum) {
		this.mNum = mNum;
	}

	public Integer getlNum() {
		return lNum;
	}

	public void setlNum(String lNum) {
		this.lNum = strToInt(lNum);
	}

	public void setlNum(Integer lNum) {
		this.lNum = lNum;
	}

	public Integer getxLNum() {
		return xLNum;
	}

	public void setxLNum(String xLNum) {
		this.xLNum = strToInt(xLNum);
	}

	public void setxLNum(Integer xLNum) {
		this.xLNum = xLNum;
	}

	public Integer getxXLNum() {
		return xXLNum;
	}

	public void setxXLNum(String xXLNum) {
		this.xXLNum = strToInt(xXLNum);
	}

	public void setxXLNum(Integer xXLNum) {
		this.xXLNum = xXLNum;
	}

	public Integer getFlaw() {
		return flaw;
	}

	public void setFlaw(String flaw) {
		this.flaw = strToInt(flaw);
	}

	public void setFlaw(Integer flaw) {
		this.flaw = flaw;
	}

	public String getInventoryValue() {
		return inventoryValue;
	}

	public void setInventoryValue() {
		this.inventoryValue = "NT$"+(price*amount);
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", name=" + name + ", positionId=" + positionId + ", position=" + position
				+ ", amount=" + amount + ", unit=" + unit + ", price=" + price + ", sampleNum=" + sampleNum
				+ ", xXSNum=" + xXSNum + ", xSNum=" + xSNum + ", sNum=" + sNum + ", mNum=" + mNum + ", lNum=" + lNum
				+ ", xLNum=" + xLNum + ", xXLNum=" + xXLNum + ", flaw=" + flaw + ", inventoryValue=" + inventoryValue
				+ ", note=" + note + "]";
	}

	public Integer strToInt(String str) {
		return Integer.parseInt(str);
	}

	public List<Integer> inputOutputForJYCY(){
		return Arrays.asList(sampleNum,xXSNum,xSNum,sNum,mNum,lNum,xLNum,xXLNum,flaw);
	}

	public String[] getJYCYInfo() {
		return new String[] {id,name,positionId,position,sampleNum.toString(),xXSNum.toString(),xSNum.toString(),
				sNum.toString(),mNum.toString(),lNum.toString(),xLNum.toString(),xXLNum.toString(),flaw.toString(),
				amount.toString(),unit,price.toString(),inventoryValue,note};
	}

	public String[] getODBAGInfo() {
		return new String[] {id,name,positionId,position,amount.toString(),unit,price.toString(),inventoryValue,note};
	}

	public String[] getInfoFitAllType() {
		return type.equals("JY") || type.equals("CY") ?getJYCYInfo():new String[] {id,name,positionId,position,"","","",
				"","","","","","",amount.toString(),unit,price.toString(),inventoryValue,note};
	}

//	public Inventory(String id, String type, String name, String positionId, String position, Integer amount,
//	String unit, Integer price, String inventoryValue, String note) {
//super();
//this.id = id;
//this.type = type;
//this.name = name;
//this.positionId = positionId;
//this.position = position;
//this.amount = amount;
//this.unit = unit;
//this.price = price;
//this.inventoryValue = inventoryValue;
//this.note = note;
//}
//
//public Inventory(String id, String type, String name, String positionId, String position, Integer amount,
//	String unit, Integer price, Integer sampleNum, Integer xXSNum, Integer xSNum, Integer sNum, Integer mNum,
//	Integer lNum, Integer xLNum, Integer xXLNum, Integer flaw, String inventoryValue, String note) {
//super();
//this.id = id;
//this.type = type;
//this.name = name;
//this.positionId = positionId;
//this.position = position;
//this.amount = amount;
//this.unit = unit;
//this.price = price;
//this.sampleNum = sampleNum;
//this.xXSNum = xXSNum;
//this.xSNum = xSNum;
//this.sNum = sNum;
//this.mNum = mNum;
//this.lNum = lNum;
//this.xLNum = xLNum;
//this.xXLNum = xXLNum;
//this.flaw = flaw;
//this.inventoryValue = inventoryValue;
//this.note = note;
//}
}
