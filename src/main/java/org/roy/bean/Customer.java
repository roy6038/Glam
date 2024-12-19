package org.roy.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Customer implements Serializable{

	private static final long serialVersionUID = -6599470812427831395L;

	private String id;
	private String name;
	private String birthDate;
	private String phoneNumber;
	private String facebook;
	private String lineOrWechat;
	private String address;
	private String email;
	private String gender;
	private Map<String,List<List<String>>> shoppingDetailData;
	private List<String> imageKey;
	private List<List<String>> shoppingMoney;

	public Customer() {
		super();
	}

	public Customer(String id, String name, String birthDate, String phoneNumber, String facebook,
			String lineOrWechat, String address, String email, String gender,
			Map<String, List<List<String>>> shoppingDetailData, List<String> imageKey,
			List<List<String>> shoppingMoney) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.facebook = facebook;
		this.lineOrWechat = lineOrWechat;
		this.email = email;
		this.address = address;
		this.gender = gender;
		this.shoppingDetailData = shoppingDetailData;
		this.imageKey = imageKey;
		this.shoppingMoney = shoppingMoney;
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getLineOrWechat() {
		return lineOrWechat;
	}

	public void setLineOrWechat(String lineOrWechat) {
		this.lineOrWechat = lineOrWechat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Map<String, List<List<String>>> getShoppingDetailData() {
		return shoppingDetailData;
	}

	public void setShoppingDetailData(Map<String, List<List<String>>> shoppingDetailData) {
		this.shoppingDetailData = shoppingDetailData;
	}

	public List<String> getImageKey() {
		return imageKey;
	}

	public void setImageKey(List<String> imageKey) {
		this.imageKey = imageKey;
	}

	public List<List<String>> getShoppingMoney() {
		return shoppingMoney;
	}

	public void setShoppingMoney(List<List<String>> shoppingMoney) {
		this.shoppingMoney = shoppingMoney;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", phoneNumber=" + phoneNumber
				+ ", facebook=" + facebook + ", lineOrWechat=" + lineOrWechat + ", email=" + email + ", address="
				+ address + ", gender=" + gender + ", shoppingDetailData=" + shoppingDetailData + ", imageKey="
				+ imageKey + ", shoppingMoney=" + shoppingMoney + "]";
	}

	public String[] showInfosOnTable() {
		return new String[] {id,name,birthDate,phoneNumber,facebook,lineOrWechat,address,email,gender};
	}

}
