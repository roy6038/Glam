package org.roy.homepage.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.roy.customerData.function.ImportDataGender;
import org.roy.customerData.view.CustomerData;
import org.roy.customerData.viewFunction.AddCustomerData;
import org.roy.customerData.viewFunction.EnterDateAndAmount;
import org.roy.dailySaleSheet.view.DailySaleTable;
import org.roy.dailySaleSheet.viewFunction.AddNewSale;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.historyRecords.viewFunction.StartDateAndEndDate;
import org.roy.inventory.viewFunction.AddInventory;
import org.roy.pettyCash.viewFunction.AddCashDetail;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.ExportAsExcel;
import org.roy.settings.Functions;
import org.roy.settings.Preferences;
import org.roy.settings.TableSettings;
import org.roy.settings.listener.MenuAdapter;

import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;

public class MenuBar extends JMenuBar{

	private static final long serialVersionUID = 1L;
	private JMenu menu_edit;
	private JMenu menu_system;
	private JMenu menu_cusData;
	private JMenu menu_inventoryData;
	private JMenu menu_saleSheet;
	private JMenu menu_theme;
	private JMenu menu_alter;

	private ButtonGroup buttonGroup;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private JMenuItem item_importCusDataExcel;
	private JMenuItem item_addCus;
	private JMenuItem item_addShopdetail;
	private JMenuItem item_uploadSheet;
	private JMenuItem item_addNewSale;
	private JMenuItem item_addNewInventory;
	private JMenuItem item_exportSaleSheetToExcel;
	private JMenuItem item_writeData;
	private JMenuItem item_editVipList;
	private JMenuItem item_checkDepotLocate;
	private JMenu menu_pettyMoney;
	private JMenu menu_historyRecord;
	private JMenuItem item_editDepotLocate;
	private JMenuItem item_editSalePerson;
	private JMenuItem item_editInputOutput;
	private JMenuItem item_editPettyCash;
	private JMenuItem item_editAnnouncement;
	private JMenuItem item_editSaleIncome;
	private JMenuItem item_addCashDetail;
	private JMenuItem item_exportInputAndOutputSheet;
	private JMenuItem item_editRemain;
	private JMenuItem item_editCalendarAnimationSpeed;
	private JMenuItem item_clearSaleSheet;
	private JMenuItem item_vipMark;
	private JMenuItem item_logOut;


	public MenuBar(GlamChamber glamChamber) {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Dialog", Font.BOLD, 16));
		glamChamber.setJMenuBar(menuBar);

		buttonGroup = new ButtonGroup();

		menu_system = new JMenu("系統");
		menu_cusData = new JMenu("客資系統");
		menu_inventoryData = new JMenu("庫存系統");
		menu_saleSheet = new JMenu("銷售表");
		menu_pettyMoney = new JMenu("零用金");
		menu_historyRecord = new JMenu("歷史紀錄");
		menu_edit = new JMenu("編輯");
		menu_alter = new JMenu("更改");
		menu_theme = new JMenu("主題");


		//系統區域
		item_importCusDataExcel = new JMenuItem("讀取客戶資料(excel)");
		item_writeData = new JMenuItem("儲存資料");
		item_addCus = new JMenuItem("新增客戶資料");
		item_addShopdetail = new JMenuItem("新增購物明細");
		item_uploadSheet = new JMenuItem("上傳訂製表");
		item_editVipList = new JMenuItem("編輯VIP名單");
		item_addNewSale = new JMenuItem("新增貨品");
		item_addNewInventory = new JMenuItem("新增貨品");
		item_exportSaleSheetToExcel = new JMenuItem("匯出銷售表(excel)");
		item_checkDepotLocate = new JMenuItem("查看倉位編號");
		item_addCashDetail = new JMenuItem("新增零用金明細");
		item_editRemain = new JMenuItem("更改餘額");
		item_exportInputAndOutputSheet = new JMenuItem("匯出出入庫表");
		item_clearSaleSheet = new JMenuItem("清空銷售表");
		item_vipMark = new JMenuItem("VIP標記");
		item_logOut = new JMenuItem("登出");

		//編輯區域
		item_editDepotLocate = new JMenuItem("更改倉位名");
		item_editSalePerson = new JMenuItem("更改銷售人員");
		item_editInputOutput = new JMenuItem("更改出入庫經手人");
		item_editPettyCash = new JMenuItem("更改零用金經手人");
		item_editAnnouncement = new JMenuItem("更改公告");
		item_editSaleIncome = new JMenuItem("更改銷售額");
		item_editCalendarAnimationSpeed = new JMenuItem("更改月份切換速度");


		//菜單字體大小
		menu_system.setFont(new Font("Dialog", Font.BOLD, 16));
		menu_cusData.setFont(new Font("Dialog", Font.BOLD, 16));
		menu_saleSheet.setFont(new Font("Dialog", Font.BOLD, 16));
		menu_inventoryData.setFont(new Font("Dialog", Font.BOLD, 16));
		menu_pettyMoney.setFont(new Font("Dialog", Font.BOLD, 16));
		menu_historyRecord.setFont(new Font("Dialog", Font.BOLD, 16));

		menu_edit.setFont(new Font("Dialog", Font.BOLD, 16));
		menu_alter.setFont(new Font("Dialog", Font.BOLD, 16));
		menu_theme.setFont(new Font("Dialog", Font.BOLD, 16));

		//菜單大小
		menu_system.setPreferredSize(new Dimension(50, 20));
		menu_cusData.setPreferredSize(new Dimension(150, 20));
		menu_saleSheet.setPreferredSize(new Dimension(150, 20));
		menu_inventoryData.setPreferredSize(new Dimension(150, 20));
		menu_pettyMoney.setPreferredSize(new Dimension(150, 20));
		menu_historyRecord.setPreferredSize(new Dimension(150, 20));

		menu_edit.setPreferredSize(new Dimension(50, 20));
		menu_alter.setPreferredSize(new Dimension(150, 20));

		menu_theme.setPreferredSize(new Dimension(50, 20));


		menu_system.addMenuListener(new MenuAdapter() {
			@Override
			public void menuSelected(MenuEvent e) {
				menuItemEnable();
			}

		});

		item_importCusDataExcel.setFont(new Font("Dialog", Font.BOLD, 16));
		item_writeData.setFont(new Font("Dialog", Font.BOLD, 16));
		item_addCus.setFont(new Font("Dialog", Font.BOLD, 16));
		item_addShopdetail.setFont(new Font("Dialog", Font.BOLD, 16));
		item_uploadSheet.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editVipList.setFont(new Font("Dialog", Font.BOLD, 16));
		item_addNewSale.setFont(new Font("Dialog", Font.BOLD, 16));
		item_addNewInventory.setFont(new Font("Dialog", Font.BOLD, 16));
		item_exportSaleSheetToExcel.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editDepotLocate.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editSalePerson.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editInputOutput.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editPettyCash.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editAnnouncement.setFont(new Font("Dialog", Font.BOLD, 16));
		item_checkDepotLocate.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editSaleIncome.setFont(new Font("Dialog", Font.BOLD, 16));
		item_addCashDetail.setFont(new Font("Dialog", Font.BOLD, 16));
		item_exportInputAndOutputSheet.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editRemain.setFont(new Font("Dialog", Font.BOLD, 16));
		item_editCalendarAnimationSpeed.setFont(new Font("Dialog", Font.BOLD, 16));
		item_clearSaleSheet.setFont(new Font("Dialog", Font.BOLD, 16));
		item_vipMark.setFont(new Font("Dialog", Font.BOLD, 16));
		item_logOut.setFont(new Font("Dialog", Font.BOLD, 16));

		item_importCusDataExcel.setPreferredSize(new Dimension(200, 20));
		item_writeData.setPreferredSize(new Dimension(200, 20));
		item_addCus.setPreferredSize(new Dimension(200, 20));
		item_addShopdetail.setPreferredSize(new Dimension(200, 20));
		item_uploadSheet.setPreferredSize(new Dimension(200, 20));
		item_editVipList.setPreferredSize(new Dimension(200, 20));
		item_addNewSale.setPreferredSize(new Dimension(200, 20));
		item_addNewInventory.setPreferredSize(new Dimension(200, 20));
		item_exportSaleSheetToExcel.setPreferredSize(new Dimension(200, 20));
		item_editDepotLocate.setPreferredSize(new Dimension(200, 20));
		item_editSalePerson.setPreferredSize(new Dimension(200, 20));
		item_editInputOutput.setPreferredSize(new Dimension(200, 20));
		item_editPettyCash.setPreferredSize(new Dimension(200, 20));
		item_editAnnouncement.setPreferredSize(new Dimension(200, 20));
		item_checkDepotLocate.setPreferredSize(new Dimension(200, 20));
		item_editSaleIncome.setPreferredSize(new Dimension(200, 20));
		item_addCashDetail.setPreferredSize(new Dimension(200, 20));
		item_exportInputAndOutputSheet.setPreferredSize(new Dimension(200, 20));
		item_editRemain.setPreferredSize(new Dimension(200, 20));
		item_editCalendarAnimationSpeed.setPreferredSize(new Dimension(200, 20));
		item_clearSaleSheet.setPreferredSize(new Dimension(200, 20));
		item_vipMark.setPreferredSize(new Dimension(200, 20));
		item_logOut.setPreferredSize(new Dimension(200, 20));

		menuBar.add(menu_system);
		menuBar.add(menu_edit);
		menuBar.add(menu_theme);

		menu_system.add(menu_cusData);
		menu_system.addSeparator();
		menu_system.add(menu_inventoryData);
		menu_system.addSeparator();
		menu_system.add(menu_saleSheet);
		menu_system.addSeparator();
		menu_system.add(menu_pettyMoney);
		menu_system.addSeparator();
		menu_system.add(menu_historyRecord);
		menu_system.addSeparator();
		menu_system.add(item_writeData);
		menu_system.addSeparator();
		menu_system.add(item_logOut);

		menu_edit.add(menu_alter);

		setThemeDetails();

		menu_cusData.add(item_addCus);
		menu_cusData.addSeparator();
		menu_cusData.add(item_addShopdetail);
		menu_cusData.addSeparator();
		menu_cusData.add(item_uploadSheet);
		menu_cusData.addSeparator();
		menu_cusData.add(item_editVipList);
		menu_cusData.addSeparator();
		menu_cusData.add(item_importCusDataExcel);
		menu_cusData.addSeparator();
		menu_cusData.add(item_vipMark);

		menu_inventoryData.add(item_addNewInventory);
		menu_inventoryData.addSeparator();
		menu_inventoryData.add(item_checkDepotLocate);

		menu_saleSheet.add(item_addNewSale);
		menu_saleSheet.addSeparator();
		menu_saleSheet.add(item_exportSaleSheetToExcel);
		menu_saleSheet.addSeparator();
		menu_saleSheet.add(item_clearSaleSheet);

		menu_pettyMoney.add(item_addCashDetail);
		menu_pettyMoney.addSeparator();
		menu_pettyMoney.add(item_editRemain);

		menu_historyRecord.add(item_exportInputAndOutputSheet);

		menu_alter.add(item_editDepotLocate);
		menu_alter.addSeparator();
		menu_alter.add(item_editSalePerson);
		menu_alter.addSeparator();
		menu_alter.add(item_editInputOutput);
		menu_alter.addSeparator();
		menu_alter.add(item_editPettyCash);
		menu_alter.addSeparator();
		menu_alter.add(item_editAnnouncement);
		menu_alter.addSeparator();
		menu_alter.add(item_editSaleIncome);
		menu_alter.addSeparator();
		menu_alter.add(item_editCalendarAnimationSpeed);


		item_importCusDataExcel.addActionListener(e -> new ImportDataGender());

		item_writeData.addActionListener(e -> new DataFile("write").execute());

		item_addCus.addActionListener(e -> new AddCustomerData());

		item_addShopdetail.addActionListener(e -> new EnterDateAndAmount());

		item_uploadSheet.addActionListener(e -> uploadCustomizedSheet(GlamChamber.customized_table.getKey()));

		item_editVipList.addActionListener(e -> {
			StringBuilder sb = new StringBuilder();
			DataFile.vipList.forEach(vip->sb.append(vip.toString()+"\n"));
			new EditWindow(sb.toString(),"VIPCUS");
			});

		item_addNewInventory.addActionListener(e-> new AddInventory());

		item_addNewSale.addActionListener(e-> new AddNewSale());

		item_editDepotLocate.addActionListener(e-> new EditWindow(DataFile.depotLocate.entrySet().stream().map(entry->new String[] {entry.getKey(),entry.getValue()}).toArray(String[][]::new), "depotLocate"));

		item_editSalePerson.addActionListener(e-> new EditWindow(DataFile.salePerson.stream().map(person->new String[] {person}).toArray(String[][]::new),"salePerson"));

		item_editInputOutput.addActionListener(e-> new EditWindow(DataFile.inputOutputPerson.stream().map(person->new String[] {person}).toArray(String[][]::new),"inputOutput"));

		item_editPettyCash.addActionListener(e-> new EditWindow(DataFile.pettyCashPerson.stream().map(person->new String[] {person}).toArray(String[][]::new),"pettyCash"));

		item_exportSaleSheetToExcel.addActionListener(e->{
			List<List<String>> collect = ((DefaultTableModel)DailySaleTable.table_saleSheet.getModel()).getDataVector().stream().map(v->Arrays.asList(v.toArray())).map(l->Arrays.asList(l.toArray(String[]::new))).collect(Collectors.toList());
			new ExportAsExcel("saleSheetStencil.xlsx",collect,collect.size()%16==0?collect.size()/16:collect.size()/16+1,16);
			});

		item_checkDepotLocate.addActionListener(e-> new EditWindow(DataFile.depotLocate.entrySet().stream().map(entry->new String[] {entry.getKey(),entry.getValue()}).toArray(String[][]::new), "checkDepotLocate"));

		item_editAnnouncement.addActionListener(e-> new EditWindow(DataFile.announcement, "announcement"));

		item_addCashDetail.addActionListener(e-> new AddCashDetail());

		item_editSaleIncome.addActionListener(e->new EditWindow(getDateList(GlamChamber.homePage.getCalendarCustom().getYear(),GlamChamber.homePage.getCalendarCustom().getMonth()), "dailySaleIncome"));

		item_editRemain.addActionListener(e->editremain());

		item_exportInputAndOutputSheet.addActionListener(e->new StartDateAndEndDate());

		item_editCalendarAnimationSpeed.addActionListener(e->editAnimationSpeed());

		item_clearSaleSheet.addActionListener(e-> clearSaleSheet());

		item_vipMark.addActionListener(e-> editVipMarkColor());

		item_logOut.addActionListener(e->GlamChamber.logOut());
	}

	private void editVipMarkColor() {
		Color c =JColorChooser.showDialog(null,"更改VIP標記顏色",DataFile.vipMark);
		if(c!=null) {
			DataFile.vipMark = c;
			CustomerData.refreshTableCell();
			GlamChamber.dataNotSaved();
		}
	}

	private void clearSaleSheet() {
		int con = Functions.showConfirmDialog("確定清空？","清空銷售表");
		if(con == 1) {
			DataFile.saleSheet.clear();
			TableSettings.show(DailySaleTable.table_saleSheet, Const.COLUMNS_FOR_SALES_SHEET,
					DataFile.saleSheet.entrySet().stream().map(Map.Entry::getValue)
							.flatMap(s -> Arrays.stream(s.convertBeanToTableInfo())).toArray(Object[][]::new),
					Const.SALE_SHEET_COLUMN_SIZE);
		}
	}

	private void setThemeDetails() {

		String selectedTheme = Arrays.stream(FlatAllIJThemes.INFOS).filter(e->e.getClassName().equals(DataFile.preferences.getConfig(Preferences.LAST_SELECTED_THEME))).map(theme->theme.getName()).findAny().orElse("com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme");
		JRadioButtonMenuItem[] themeArray = Arrays.stream(FlatAllIJThemes.INFOS)
				.map(e -> new JRadioButtonMenuItem(e.getName())).toArray(JRadioButtonMenuItem[]::new);
		for (JRadioButtonMenuItem mi : themeArray) {
			mi.setSize(500,mi.getHeight());
			menu_theme.add(mi);
			buttonGroup.add(mi);
			if(mi.getText().equals(selectedTheme)) {
				mi.setSelected(true);
			}
			mi.addActionListener(e -> {
				if (mi.isSelected()) {
					Arrays.stream(FlatAllIJThemes.INFOS).filter(menu -> mi.getText().equals(menu.getName()))
							.forEach(look ->{
								Functions.updateAllUI(look.getClassName());
								DataFile.preferences.setConfig(Preferences.LAST_SELECTED_THEME,look.getClassName());
								DataFile.preferences.save();
								});
				}
			});
			menu_theme.addSeparator();
		}
	}


	private void menuItemEnable() {
		switch(GlamChamber.mainForm.getPanelBody().getComponents()[0].getClass().getName()) {
		case "org.roy.customerData.view.CustomerData" -> {
			disableAllMenuItem();
			item_addCus.setEnabled(true);
			item_importCusDataExcel.setEnabled(true);
		}
		case "org.roy.customerData.view.ShoppingDetails" -> {
			disableAllMenuItem();
			if(GlamChamber.shopping_details.getKey()!=null) {
				item_addShopdetail.setEnabled(true);
			}
		}
		case "org.roy.customerData.view.CustomizedTable" -> {
			disableAllMenuItem();
			if(GlamChamber.customized_table.getKey()!=null) {
				item_uploadSheet.setEnabled(true);
			}
		}
		case "org.roy.dailySaleSheet.view.DailySaleTable" ->{
			disableAllMenuItem();
			item_addNewSale.setEnabled(true);
			item_exportSaleSheetToExcel.setEnabled(true);
			item_clearSaleSheet.setEnabled(true);
		}
		case "org.roy.inventory.view.InventoryData"->{
			disableAllMenuItem();
			item_addNewInventory.setEnabled(true);
		}
		case "org.roy.pettyCash.view.PettyCashDetail"->{
			disableAllMenuItem();
			item_addCashDetail.setEnabled(true);
		}
		case "org.roy.historyRecords.view.HistoryRecords"->{
			disableAllMenuItem();
			item_exportInputAndOutputSheet.setEnabled(true);
		}
		default->{
			disableAllMenuItem();
		}

		}
	}

	private void disableAllMenuItem() {
		item_addNewInventory.setEnabled(false);
		item_addNewSale.setEnabled(false);
		item_exportSaleSheetToExcel.setEnabled(false);
		item_addCus.setEnabled(false);
		item_addShopdetail.setEnabled(false);
		item_uploadSheet.setEnabled(false);
		item_importCusDataExcel.setEnabled(false);
		item_addCashDetail.setEnabled(false);
		item_exportInputAndOutputSheet.setEnabled(false);
		item_clearSaleSheet.setEnabled(false);
	}

	private void editremain() {
		String newRemain = JOptionPane.showInputDialog(null,"更改餘額",DataFile.pettyMoneyRemain);
		if(newRemain != null) {
			if(Functions.isNumberic(newRemain,true,false)) {
				int oldRemain = DataFile.pettyMoneyRemain;
				DataFile.pettyMoneyRemain = Integer.parseInt(newRemain);
				if(oldRemain!=DataFile.pettyMoneyRemain) {
					HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("零用金","","更改餘額",oldRemain+"->"+newRemain,"","")));
				}
			}else {
				JOptionPane.showMessageDialog(null,"請輸入數字");
				editremain();
			}
		}
	}

	private String[][] getDateList(int year,int month) {
	    return IntStream
	            .rangeClosed(1, YearMonth.of(year, month).lengthOfMonth())
	            .mapToObj(i ->{
	            	String date = formatter.format(LocalDate.of(year, month, i));
	            	return new String[] {date, DataFile.dailySaleIncome.get(date)==null?"0":DataFile.dailySaleIncome.get(date).toString()};
	            })
	            .toArray(String[][]::new);
	}

	private void editAnimationSpeed() {
		String speed = null;
		speed = JOptionPane.showInputDialog("更改月份切換速度",GlamChamber.homePage.getCalendarCustom().getSlide().getAnimate());
		if(speed!=null) {
			if(!Functions.isNumberic(speed,false,false)) {
				JOptionPane.showMessageDialog(null,"數值錯誤");
				editAnimationSpeed();
			}else {
				GlamChamber.homePage.getCalendarCustom().getSlide().setAnimate(speed);
				GlamChamber.dataNotSaved();
			}
		}
	}

	private void uploadCustomizedSheet(String key) {
		JFileChooser jc = new JFileChooser();
		jc.setMultiSelectionEnabled(true);
		jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jc.setPreferredSize(new Dimension(800, 600));
		jc.setFont(new Font("Dialog", Font.BOLD, 16));
		jc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter fnef = new FileNameExtensionFilter("顧客編號："+key+"，上傳訂製表","jpg", "jpeg","png");
		jc.addChoosableFileFilter(fnef);

		int checkInput = jc.showOpenDialog(null);
		if (checkInput == JFileChooser.APPROVE_OPTION) {
			List<String> allImages = Arrays.stream(new File(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)).listFiles()).map(f->f.getName()).collect(Collectors.toList());
			List<String> uploads = new ArrayList<>();
			for(File file:jc.getSelectedFiles()) {
				if(allImages.contains(file.getName())) {
					JOptionPane.showMessageDialog(null,"圖片檔已存在："+file.getName());
					continue;
				}
				try {
					File img = new File(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)+File.separator+file.getName());
					ImageIO.write(ImageIO.read(file),file.getName().substring(file.getName().lastIndexOf(".")+1),img);
					uploads.add(file.getName());

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(uploads.size()>0) {
				List<String> imageKey = DataFile.customer.get(key).getImageKey();
				if(imageKey == null) {
					imageKey = new ArrayList<>();
				}
				imageKey.addAll(uploads);
				DataFile.customer.get(key).setImageKey(imageKey);
				JOptionPane.showMessageDialog(GlamChamber.customized_table,"上傳完成");
				LinkedHashMap<String, ImageIcon> image = GlamChamber.customized_table.getImage()==null?new LinkedHashMap<>():GlamChamber.customized_table.getImage();
				uploads.forEach(str->image.put(str,Functions.createAutoAdjustIcon(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)+File.separator+str,true)));
				GlamChamber.customized_table.setImage(image);
				int page = image.size()%3==0?image.size()/3:image.size()/3+1;
				GlamChamber.customized_table.setPage(page);
				GlamChamber.customized_table.removeAllPics();
				GlamChamber.customized_table.decidePicTable();
				GlamChamber.customized_table.setLabelPage("《第" + GlamChamber.customized_table.getPage() + "頁》");
				GlamChamber.customized_table.pageButtonEnable();
				GlamChamber.dataNotSaved();
			}
		}
	}

	public JMenu getMenu_edit() {
		return menu_edit;
	}

	public JMenu getMenu_system() {
		return menu_system;
	}

}
