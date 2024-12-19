package org.roy.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.roy.bean.Customer;
import org.roy.historyRecords.view.HistoryRecords;

public class ExportAsExcel {

	private final String fs = File.separator;
	private String filename;
	private String stencilFileName;
	private List<List<String>> detail;
	private int total;
	private int current = 1;
	private int perSheetSize;
	private Customer customer;
	private String key;


	public ExportAsExcel(String stencilFileName,Customer customer) {
		this.stencilFileName = stencilFileName;
		this.customer = customer;
		this.key = customer.getId();
		export(0,0,0);
	}

	public ExportAsExcel(String stencilFileName,List<List<String>> detail,int total,int perSheetSize) {
		this.stencilFileName = stencilFileName;
		this.detail = detail;
		this.total = total;
		this.perSheetSize = perSheetSize;
		export(current,0,perSheetSize);
	}

	public void export(int current, int start, int end) {
		File dir = null;
		String message = null;
		String action = null;
		StringBuilder sb = new StringBuilder();
		switch(stencilFileName) {
		case "saleSheetStencil.xlsx"-> {
			dir = new File(DataFile.preferences.getConfig(Preferences.SALE_SHEET_EXPORT_FOLDER));
			message = sb.append("共").append(detail.size()).append("筆資料\n第").append(current).append("張銷售表/共").append(total).append("張銷售表").toString();
			action = "匯出銷售表";
		}
		case "inputOutputDetailStencil.xlsx"->{
			dir = new File(DataFile.preferences.getConfig(Preferences.INPUT_OUTPUT_SHEET_EXPORT_FOLDER));
			message = sb.append("共").append(detail.size()).append("筆資料\n第").append(current).append("張明細表/共").append(total).append("張明細表").toString();
			action = "匯出明細表";
		}
		case "customerDataStencil.xlsx"->{
			dir = new File(DataFile.preferences.getConfig(Preferences.CUSTOMER_DATA_EXPORT_FOLDER)+fs+key);
			if(confirmDirectory(dir)) {
				exportCustomerDataAsExcel(dir);
			}
			return;
		}
		}
		confirmFileName(dir,message,action);
		createTargetFile(dir,start,end);

	}

	private boolean confirmDirectory(File dir) {
		File root = new File(DataFile.preferences.getConfig(Preferences.CUSTOMER_DATA_EXPORT_FOLDER));
			for(File f:root.listFiles()) {
				if(f.getName().equals(dir.getName())) {
					int con =Functions.showConfirmDialog("資料夾已存在，是否覆蓋？","資料夾已存在");
					if(con == 1) {
						f.delete();
						dir.mkdirs();
						return true;
					}
					return false;
				}
			}
			dir.mkdirs();
			return true;

	}

	public void createTargetFile(File dir, int start, int end) {
		if (filename != null) {
			String filePath = dir.getAbsolutePath() + fs + filename + ".xlsx";
			File file = new File(filePath);
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch(stencilFileName) {
			case "saleSheetStencil.xlsx"->exportSaleSheetAsExcel(filePath,start,end);
			case "inputOutputDetailStencil.xlsx"-> exportInputOutputSheetAsExcel(filePath,start,end);
			}
		}
	}

	public void confirmFileName(File dir,String message,String action) {
		switch(stencilFileName) {
		case "saleSheetStencil.xlsx" :
		case "inputOutputDetailStencil.xlsx":
			String inputFileName = JOptionPane.showInputDialog(null,message,action,JOptionPane.PLAIN_MESSAGE);
			this.filename = inputFileName == null ? null : inputFileName.replaceAll("/",".");
			break;
		}
		if(filename==null) {
			return;
		}
		for (File f : dir.listFiles()) {
			if (f.getName().equals(filename + ".xlsx")) {
				int con = Functions.showConfirmDialog("檔案名稱已存在,是否覆蓋?","檔案已存在");
				if(con!=1) {
					confirmFileName(dir,message,action);
				}
			}
		}
	}

	public void exportCustomerDataAsExcel(File dir) {
		String filePath = dir.getAbsolutePath() + fs + key + ".xlsx";
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		FileInputStream fis = null;
		XSSFWorkbook book = null;
		List<String> data = null;
		try {
			fis = new FileInputStream(new File(DataFile.preferences.getConfig(Preferences.STENCIL_FOLDER)+fs+stencilFileName));
			book= new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"找不到模板檔案");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = book.cloneSheet(0);
		book.setSheetName(1,customer.getId());

		XSSFRow rows ;
		XSSFCell cells;
		CellStyle style = book.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		data = List.of(customer.getName(),customer.getBirthDate(),customer.getPhoneNumber(),customer.getFacebook(),customer.getLineOrWechat(),customer.getAddress(),customer.getEmail(),customer.getGender());
		Map<String, List<List<String>>> shoppingDetailData = customer.getShoppingDetailData();
		List<String> imageKey = customer.getImageKey();
		int[] num = shoppingDetailData.entrySet().stream().mapToInt(entry->entry.getValue().size()).toArray();
		int size = Arrays.stream(num).sum();
//		System.out.println(Arrays.toString(num)+"\n"+size);

		rows = sheet.getRow(1);
		for(int j = 0;j<16;j++) {
			if(j == 1 || (j > 8 && j < 16)) {
				if(rows.getCell(j) == null) {
					rows.createCell(j);
				}
				cells = rows.getCell(j);
				cells.setCellStyle(style);
				switch(j) {
				case 1 -> cells.setCellValue(data.get(0));
				default -> cells.setCellValue(data.get(j-8));
				}
			}
		}
		if(size>0) {
			List<List<String>> details = shoppingDetailData.values().stream().flatMap(e->e.stream()).collect(Collectors.toList());
			for (int i = 0; i < size; i++) {
				if(sheet.getRow(i+1) == null) {
					sheet.createRow(i+1);
				}
				rows = sheet.getRow(i+1);
				for(int j = 2;j<9;j++) {
					if(rows.getCell(j) == null) {
						rows.createCell(j);
					}
					cells = rows.getCell(j);
					cells.setCellValue(details.get(i).get(j-2).toString());
					cells.setCellStyle(style);
				}
			}
			int start = 1;
			String[] date = shoppingDetailData.keySet().toArray(String[]::new);
			for(int i = 0;i<date.length;i++) {
				rows = sheet.getRow(start);
				if(rows.getCell(0) == null) {
					rows.createCell(0);
				}
				cells = rows.getCell(0);
				cells.setCellValue(date[i]);
				start+=num[i];
			}

		}
		book.removeSheetAt(0);
		FileOutputStream fileOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(file);
			book.write(fileOutputStream);
			if(imageKey!=null) {
				imageKey.stream().forEach(img-> {
					try {
						ImageIO.write(ImageIO.read(new File(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)+fs+img)),
								      img.substring(img.lastIndexOf(".")+1),
								      new File(dir.getAbsoluteFile()+fs+img));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fileOutputStream.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		JOptionPane.showMessageDialog(null,"匯出完成");
	}


	public void exportSaleSheetAsExcel(String filePath, int start, int end) {
		FileInputStream fis = null;
		XSSFWorkbook book = null;
		try {
			fis = new FileInputStream(new File(DataFile.preferences.getConfig(Preferences.STENCIL_FOLDER)+fs+stencilFileName));
			book= new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"找不到模板檔案");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = book.cloneSheet(0);
		book.setSheetName(1,"銷售表");

		XSSFRow rows ;
		XSSFCell cells;
		for (int i = 4; i < 20; i++) {
			rows = sheet.getRow(i);
			if (i < 4 + detail.size() && start < detail.size()) {
				for (int j = 0; j < 13; j++) {
					cells = rows.getCell(j);
					cells.setCellValue(detail.get(start).get(j));
					if ((j > 1 && j < 5) || (j > 6 && j < 11)) {
						String num = detail.get(start).get(j);
						if (num.length() == 0 || num.contains("\n")) {
							cells.setCellValue(num);
						} else {
							cells.setCellValue(Integer.parseInt(num));
						}
					}else{
						cells.setCellValue(detail.get(start).get(j));
					}
				}
				start++;
			}else {
				break;
			}
		}
//		XSSFPrintSetup print = sheet.getPrintSetup();
		book.removeSheetAt(0);
		File file = new File(filePath);
		FileOutputStream fileOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(file);
			book.write(fileOutputStream);
		}catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"找不到資料夾");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fileOutputStream.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("銷售表","","匯出","檔名："+file.getName(),"","")));
		if (current < total) {
			current++;
			start = end;
			end = end + perSheetSize;
			export(current, start, end);
		}else {
			JOptionPane.showMessageDialog(null,"匯出完成");
		}
	}

	public void exportInputOutputSheetAsExcel(String filePath, int start, int end) {
		FileInputStream fis = null;
		XSSFWorkbook book = null;
		try {
			fis = new FileInputStream(new File(DataFile.preferences.getConfig(Preferences.STENCIL_FOLDER) + fs + stencilFileName));
			book = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "找不到模板檔案");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = book.cloneSheet(0);
		book.setSheetName(1, "明細表");

		XSSFRow rows;
		XSSFCell cells;
		for (int i = 2; i < 20; i++) {
			rows = sheet.getRow(i);
			if (start < detail.size()) {
				for (int j = 0; j < 8; j++) {
					cells = rows.getCell(j);
					String inventoryId = detail.get(start).get(2);
					String descriptions = detail.get(start).get(5);
					switch (j) {
					case 0 -> cells.setCellValue(detail.get(start).get(0).substring(5, 10).replace("-", "/"));
					case 1 -> cells.setCellValue(inventoryId);
					case 2 -> cells.setCellValue(detail.get(start).get(3));
					case 3 -> cells.setCellValue(descriptions);
					case 4 -> {
						if (inventoryId.startsWith("JY") || inventoryId.toString().startsWith("CY")) {
							cells.setCellValue(descriptions.substring(descriptions.indexOf("總件數：") + 4));
						} else {
							cells.setCellValue(descriptions.substring(descriptions.indexOf("件數：") + 3));
						}
					}
					case 5 -> {
						String price = DataFile.inventory.get(inventoryId) == null ? "無資料"
								: DataFile.inventory.get(inventoryId).getPrice().toString();
						cells.setCellValue(price);
					}
					case 6 -> cells.setCellValue(detail.get(start).get(6));
					case 7 -> cells.setCellValue(detail.get(start).get(7));
					}
				}
				start++;
			} else {
				break;
			}
		}

		book.removeSheetAt(0);
		File file = new File(filePath);
		FileOutputStream fileOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(file);
			book.write(fileOutputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (current < total) {
			current++;
			start = end;
			end = end + perSheetSize;
			export(current, start, end);
		}else {
			JOptionPane.showMessageDialog(null,"匯出完成");
		}
	}

	public int getTotal() {
		return total;
	}

	public int getCurrent() {
		return current;
	}

}
