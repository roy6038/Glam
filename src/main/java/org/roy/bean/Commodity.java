package org.roy.bean;

import java.io.Serializable;
import java.util.Objects;

public class Commodity implements Serializable,Cloneable{

	private static final long serialVersionUID = -6458471879109663367L;

	private String id="";
	private String colorSize="";
	private String amount="";
	private String price="";
	private String discount="";
	private String particular="無";
	private String particularValue="";

	public Commodity() {
		super();
	}

	public Commodity(String id, String colorSize, String amount, String price, String discount, String particular,
			String particularValue) {
		super();
		this.id = id;
		this.colorSize = colorSize;
		this.amount = amount;
		this.price = price;
		this.discount = discount;
		this.particular = particular;
		this.particularValue = particularValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColorSize() {
		return colorSize;
	}

	public void setColorSize(String colorSize) {
		this.colorSize = colorSize;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getParticular() {
		return particular;
	}

	public void setParticular(String particular) {
		this.particular = particular;
	}

	public String getParticularValue() {
		return particularValue;
	}

	public void setParticularValue(String particularValue) {
		this.particularValue = particularValue;
	}

	public boolean include() {
		return particular.equals("贈送")||particular.equals("廣告")||particular.equals("儲值購物金")||particular.equals("換貨");
	}

	public double getDiscountInDouble(String digit) {
		return digit.length() == 2 ? Double.parseDouble(digit)/100.0: Double.parseDouble(digit)/10.0;
	}

	public int getFilterTotal() {
		return include()?0:getTotal();
	}

	public int getNewMinusOld() {
		return particular.equals("換貨")?getTotal():0;
	}

	public int getTotal() {
		return (int)Math.round(Integer.parseInt(amount)*Integer.parseInt(price)*(discount.length()==0?1.0:getDiscountInDouble(discount))-(particularValue.length()==0?0:Integer.parseInt(particularValue)));
	}

	public String getParticularString() {
		if(particular.equals("售價折扣")) {
			return particular+"\n"+particularValue;
		}else if(particular.equals("換貨")) {
			int newMinusOld = getNewMinusOld();
			return newMinusOld < 0 ? particular+"\n購物金餘額：＄"+Math.abs(newMinusOld):(newMinusOld>0?particular+"\n補差額＄："+newMinusOld:particular);
		}else {
			return particular.equals("無")?"":particular;
		}
	}

	@Override
	public String toString() {
		return "Commodity [id=" + id + ", colorSize=" + colorSize + ", amount=" + amount + ", price=" + price
				+ ", discount=" + discount + ", particular=" + particular + ", particularValue=" + particularValue
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, colorSize, discount, id, particular, particularValue, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Commodity other = (Commodity) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(colorSize, other.colorSize)
				&& Objects.equals(discount, other.discount) && Objects.equals(id, other.id)
				&& Objects.equals(particular, other.particular)
				&& Objects.equals(particularValue, other.particularValue) && Objects.equals(price, other.price);
	}

	@Override
	public Commodity clone() throws CloneNotSupportedException {
		return (Commodity)super.clone();
	}

	public Object[] convertBeanToTableInfo(){
		return new Object[] {id,colorSize,amount,price,discount,"",getParticularString(),"","","","","",""};
	}


}
