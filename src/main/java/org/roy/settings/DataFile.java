package org.roy.settings;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.roy.bean.Customer;
import org.roy.bean.Inventory;
import org.roy.bean.PettyCash;
import org.roy.bean.SaleInfo;
import org.roy.homepage.application.GlamChamber;
import org.roy.homepage.calendar.CalendarCustom;

public class DataFile extends SwingWorker<Void, Void> {
	private String action;

	public static String announcement = "";
	public static int pettyMoneyRemain = 0;
	public static int animationSpeed = 20;
	public static Color vipMark = UIManager.getColor("Table.dropLineShortColor");

	public static List<String> salePerson = new ArrayList<>(Arrays.asList("J", "P", "JOY", "官網", "修改室"));
	public static List<String> inputOutputPerson = new ArrayList<>(Arrays.asList("P", "J"));
	public static List<String> pettyCashPerson = new ArrayList<>(Arrays.asList("J", "P", "JOY"));
	public static Map<String, String> depotLocate = new TreeMap<>(Map.ofEntries(Map.entry("GM-1", "架上樣衣"), Map.entry("GM-2", "倉庫"), Map.entry("GM-3", "抽屜"),Map.entry("JO-1", "工作室衣桿上"), Map.entry("JO-2", "工作桌底下"), Map.entry("JO-3", "工作室椅子後方"),Map.entry("JO-4", "工作室流理台前方"), Map.entry("JO-5", "流理台底下"), Map.entry("JOH", "JOY的家")));
	public static Map<String,Inventory> inventory;
	public static TreeMap<String, List<List<String>>> historyRecords;
	public static Map<String,SaleInfo> saleSheet;
	public static Map<String, Integer> dailySaleIncome;
	public static Map<String,Customer> customer;
	public static List<PettyCash> pettyCashDetails;
	public static List<String> vipList;
	public static Map<String, Object> otherSettings;
	public static List<String> searchKey = new ArrayList<>();

//	public static List<List<Object>> pettyMoneyDetails = new ArrayList<>();
//	public static Map<String, List<Object>> inventoryData = new TreeMap<>();
//	public static final List<String> TYP = List.of("OD", "JY", "CY", "BAG", "客訂");

	// 偏好設定
	public static Preferences preferences;

	public DataFile(String action) {
		this.action = action;
	}

	static {
		UIManager.put("ToolTip.font", new Font("Dialog", Font.BOLD, 16));
		preferences = new Preferences(Preferences.PREFENENCE_FILE);
		File[] files = { new File(preferences.getConfig(Preferences.CUSTOMER_DATA_EXPORT_FOLDER)),
						 new File(preferences.getConfig(Preferences.SALE_SHEET_EXPORT_FOLDER)),
						 new File(preferences.getConfig(Preferences.INPUT_OUTPUT_SHEET_EXPORT_FOLDER)),
						 new File(preferences.getConfig(Preferences.DATA_FOLDER)),
						 new File(preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)),
						 new File(preferences.getConfig(Preferences.STENCIL_FOLDER))
						 };
		for (File f : files) {
			f.mkdirs();
		}
		new DataFile("read").execute();

	}

	@SuppressWarnings("unchecked")
	public static void doReadData() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		long s = System.currentTimeMillis();

		dailySaleIncome = readData("dailySaleIncome",TreeMap.class);
		customer = readData("customer",TreeMap.class);
		inventory = readData("inventory",TreeMap.class);
		historyRecords = readData("historyRecords",TreeMap.class);
		vipList = readData("vipList",ArrayList.class);
		pettyCashDetails = readData("pettyCashDetails",ArrayList.class);
		saleSheet = readData("saleSheet", LinkedHashMap.class);
		otherSettings = readData("otherSettings",HashMap.class);

		announcement = (String)otherSettings.getOrDefault("announcement",announcement);
		vipMark = (Color)otherSettings.getOrDefault("vipMark",vipMark);
		pettyMoneyRemain = (int)otherSettings.getOrDefault("pettyMoneyRemain",pettyMoneyRemain);
		animationSpeed = (int)otherSettings.getOrDefault("animationSpeed",animationSpeed);
		salePerson = (List<String>)otherSettings.getOrDefault("salePerson",salePerson);
		inputOutputPerson = (List<String>)otherSettings.getOrDefault("inputOutputPerson",inputOutputPerson);
		pettyCashPerson = (List<String>)otherSettings.getOrDefault("pettyCashPerson",pettyCashPerson);
		depotLocate = (TreeMap<String,String>)otherSettings.getOrDefault("depotLocate",depotLocate);

		while(true) {
//			System.out.println("load");
			if(GlamChamber.homePage != null) {
				CalendarCustom calendarCustom = GlamChamber.homePage.getCalendarCustom();
				calendarCustom.setAnnouncement(announcement);
				calendarCustom.getSlide().setAnimate(animationSpeed);
				break;
			}
		}
		System.out.println("讀檔時間："+(System.currentTimeMillis()-s));
//		inventoryData = readData("inventoryData") != null? (TreeMap<String,List<Object>>) readData("inventoryData"):inventoryData;
//		pettyMoneyDetails = readData("pettyMoneyDetails") != null? (List<List<Object>>) readData("pettyMoneyDetails"):pettyMoneyDetails;
//		modifiedOldInventory();
//		inventory.entrySet().forEach(en->System.out.println(en.getValue()));
//		List<Commodity> list = new ArrayList<>();
//		for(int i = 1;i<34;i++) {
//			list.add(new Commodity("od001","s","1","15000","","無",""));
//		}
//		SaleInfo si = new SaleInfo("劉欣婷","","", false,"無","","刷卡","P", list,"aaa",15000*34,0);
//		saleSheet.put("劉雅婷", si);
	}

	public static void doWriteData() throws IOException {
		long s = System.currentTimeMillis();
		writeData("dailySaleIncome",dailySaleIncome);
		writeData("customer",customer);
		writeData("saleSheet",saleSheet);
		writeData("historyRecords",historyRecords);
		writeData("inventory",inventory);
		writeData("vipList",vipList);
		writeData("pettyCashDetails",pettyCashDetails);
		refreshOtherSettings();
		writeData("otherSettings",otherSettings);
		GlamChamber.isDataSaved = true;
		System.out.println("寫入時間："+(System.currentTimeMillis()-s));
		JOptionPane.showMessageDialog(null,"儲存完成");
	}

	public static void refreshOtherSettings() {
		otherSettings.put("salePerson", salePerson);
		otherSettings.put("inputOutputPerson", inputOutputPerson);
		otherSettings.put("pettyCashPerson", pettyCashPerson);
		otherSettings.put("depotLocate", depotLocate);
		otherSettings.put("vipMark", vipMark);
		otherSettings.put("pettyMoneyRemain", pettyMoneyRemain);
		otherSettings.put("announcement", announcement);
		otherSettings.put("animationSpeed", animationSpeed);
	}

	public static<T> T readData(String fileName,Class<T> c) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		File file = new File(preferences.getConfig(Preferences.DATA_FOLDER) + File.separator +fileName);
		file.createNewFile();

		if (file.length() == 0) {
			return c.getDeclaredConstructor().newInstance();
		}

		try (FileInputStream fis = new FileInputStream(file);ObjectInputStream ois = new ObjectInputStream(fis)){
			return c.cast(ois.readObject());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeData(String fileName,Object obj) throws IOException {

		File file = new File(preferences.getConfig(Preferences.DATA_FOLDER) + File.separator +fileName);
		file.createNewFile();

		try (FileOutputStream fos = new FileOutputStream(file);ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(obj);
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		switch (action) {
		case "read":
			doReadData();
			break;
		case "write":
			doWriteData();
			break;
		}
		return null;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

//	public static void modifiedOldInventory() {
//		pettyMoneyDetails.forEach(e->{
//		boolean hasIncome = e.get(2).toString().length()!=0;
//		String type = hasIncome?"收入":"支出";
//		pettyCashDetails.add(new PettyCash(e.get(0).toString(),e.get(1).toString(),type, hasIncome?e.get(2).toString():e.get(3).toString(), e.get(4).toString(), e.get(5).toString(),e.get(6).toString().toString().length()!=0, e.get(7).toString().length()!=0,e.get(8).toString()));
//	});
//	System.out.println(pettyCashDetails);
//
//	inventoryData.entrySet().forEach(en->{
//		List<String> li = en.getValue().stream().map(st->st.toString()).collect(Collectors.toList());
//		String id = en.getKey();
//		if(containsJYorCY(id)) {
//			inventory.put(id,new Inventory(id,containsType(id),li.get(1), li.get(2), li.get(3), strToInt(li.get(13)),
//					li.get(14),strToInt(li.get(15)),strToInt(li.get(4)),strToInt(li.get(5)),strToInt(li.get(6)),
//					strToInt(li.get(7)),strToInt(li.get(8)),strToInt(li.get(9)),strToInt(li.get(10)),
//					strToInt(li.get(11)),strToInt(li.get(12)),li.get(16),li.get(17)));
//		}else {
//			inventory.put(id,new Inventory(id, containsType(id),li.get(1),li.get(2),li.get(3), strToInt(li.get(4)),li.get(5),strToInt(li.get(6)), li.get(7),li.get(8)));
//		}
//	});
//	}
//
//	public static boolean containsJYorCY(String type) {
//		return type.contains("JY")||type.contains("CY");
//	}
//
//	public static Integer strToInt(String str) {
//		return Integer.parseInt(str);
//	}
//
//	public static String containsType(String str) {
//		for(String s:TYP) {
//			if(str.contains(s)) return s;
//		}
//		return null;
//	}
//
//	public static boolean containsODorBAG(String type) {
//		return type.contains("OD")||type.contains("BAG")||type.contains("客訂");
//	}
}
