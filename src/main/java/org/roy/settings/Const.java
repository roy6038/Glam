package org.roy.settings;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.SwingConstants;


public final class Const {

	public static final DefaultListCellRenderer DLCR = new DefaultListCellRenderer();
	public static final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final SimpleDateFormat CHINESE_MD_SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM月dd日");
	public static final SimpleDateFormat CHINESE_YMD_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat SLASH_YMD_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
	public static final SimpleDateFormat HYPHEN_YMDHM_SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();
	// 欄位名稱
	public static final String[] COLUMNS_FOR_CUSTOMERDATA = { "顧客編號", "顧客姓名", "生日", "手機", "Facebook", "Line/WeChat", "地址", "email","性別" };
	public static final String[] COLUMNS_FOR_SHOPPINGDETAILS = { "商品貨號", "數量", "尺寸", "單價", "折扣", "銷售額", "備註" };
	public static final String[] COLUMNS_FOR_SHOPPINGDETAILS_WITH_DATE = {"日期","商品貨號", "數量", "尺寸", "單價", "折扣", "銷售額", "備註" };
	public static final String[] COLUMNS_FOR_OD_AND_BAG = { "貨號", "名稱", "倉位編號", "位置", "數量", "單位", "單價", "庫存價值", "備註" };
	public static final String[] COLUMNS_FOR_JY_AND_CY = { "貨號", "名稱", "倉位編號", "位置", "樣衣", "XXS", "XS", "S", "M", "L", "XL", "XXL", "Flaw", "數量", "單位", "單價", "庫存價值", "備註" };
	public static final String[] COLUMNS_FOR_HISTORY_RECORDS = { "時間", "系統", "貨號/ID", "操作","操作人", "描述", "經手人", "備註" };
	public static final String[] COLUMNS_FOR_SALES_SHEET = { "貨號", "顏色尺寸", "件數", "售價", "折數", "<html><body><p align=\"center\">折抵<br>購物金<br>介紹金<br>註名客戶名</p></body></html>", "特殊情況", "現金", "刷卡", "匯款", "官網", "<html><body><p align=\"center\">Tina<br>友人<br>請Ｖ</p></body></html>", "銷售人員" };
	public static final String[] COLUMNS_FOR_PETTYMONEY = { "日期", "明細", "收入", "支出", "餘額", "經手人", "發票", "收據", "備註" };
	// 欄位寬度
	public static final Map<Integer, Integer> CUSDATA_COLUMN_SIZE = Map.of(0, 150, 1, 50, 2,50, 3, 60, 4, 60, 5, 80, 6,140, 7, 250,8,15);
	public static final Map<Integer, Integer> SHOP_DETAIL_COLUMN_SIZE = Map.of(0,100,1,20, 2, 100, 3, 50, 4, 30, 5, 80, 6,500);
	public static final Map<Integer, Integer> SHOP_DETAIL_WITH_DATE_COLUMN_SIZE = Map.of(0,100,1,80,2, 20, 3, 100, 4, 50, 5, 30, 6, 80, 7,500);
	public static final Map<Integer, Integer> OD_BAG_COLUMN_SIZE = Map.of(0, 90, 1, 110, 2, 70, 3, 150, 4, 20, 5, 20, 6,50, 7, 80, 8, 190);
	public static final Map<Integer, Integer> PETTY_MONEY_COLUMN_SIZE = Map.of(0, 150, 1, 250, 2, 100, 3, 100, 4, 120,5, 70, 6, 50, 7, 50, 8, 200);
	public static final Map<Integer, Integer> JY_CY_COLUMN_SIZE = Map.ofEntries(Map.entry(0, 105), Map.entry(1, 105),Map.entry(2, 80), Map.entry(3, 70), Map.entry(4, 20), Map.entry(5, 20), Map.entry(6, 20), Map.entry(7, 20),Map.entry(8, 20), Map.entry(9, 20), Map.entry(10, 2), Map.entry(11, 20), Map.entry(12, 20),Map.entry(13, 20), Map.entry(14, 20), Map.entry(15, 55), Map.entry(16, 80), Map.entry(17, 100));
	public static final Map<Integer, Integer> SALE_SHEET_COLUMN_SIZE = Map.ofEntries(Map.entry(0, 80), Map.entry(1, 80),Map.entry(2, 50), Map.entry(3, 60), Map.entry(4, 20), Map.entry(5, 80), Map.entry(6, 80), Map.entry(7, 100),Map.entry(8, 100), Map.entry(9, 100), Map.entry(10, 100), Map.entry(11, 20), Map.entry(12, 40));
	public static final Map<Integer, Integer> HISTORY_RECORDS_COLUMN_SIZE = Map.of(0, 200, 1, 100, 2, 120, 3, 70,4,70,5,680, 6, 80, 7, 230);

	static {
		DLCR.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private Const() {}


}
