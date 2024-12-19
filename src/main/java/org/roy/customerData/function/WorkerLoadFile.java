package org.roy.customerData.function;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.roy.bean.Customer;
import org.roy.customerData.view.CustomerData;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Preferences;
import org.roy.settings.TableSettings;

public class WorkerLoadFile extends JFrame {

	private static final long serialVersionUID = 8415742948529952222L;
	private JPanel contentPane;
	private File[] openFiles;
	private JProgressBar progressBar;
	private int current = 0;
	private int totalFileNums;
	private int count = 0;
	private JLabel progressLabel;
	private Map<String, List<String>> imageKey;
	private ProgressWorker progressWorker;
	private String gender;
	private boolean isUnKnown;
	private Pattern comPattern = Pattern.compile("\\d+月\\d+日");
	private Matcher matcher;

	/**
	 * Create the frame.
	 */
	public WorkerLoadFile(File[] openFiles, String gender, boolean isUnknown) {
		this.openFiles = openFiles;
		this.gender = gender;
		this.isUnKnown = isUnknown;
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				progressWorker.cancel(true);
			}

		});
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 752, 179);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		progressBar = new JProgressBar(0, 100);
		progressBar.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		progressBar.setBounds(29, 78, 694, 42);
		progressBar.setStringPainted(true);
		progressLabel = new JLabel("準備中");
		progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		progressLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		progressLabel.setBounds(29, 31, 694, 32);

		contentPane.add(progressBar);
		contentPane.add(progressLabel);

		progressWorker = new ProgressWorker();
		progressWorker.execute();

	}

	class ProgressWorker extends SwingWorker<Void, Integer> {

		private final List<String> vaildFileType = Arrays.asList("xlsx", "jpg", "jpeg", "png");
		private final List<String> format = Arrays.asList("General", "@", "0_", "m'月'd'日");
		private final int[] customerInfoLocate = { 1, 9, 10, 11, 12, 13, 14 };
		private DecimalFormat df = new DecimalFormat("##%");

		@Override
		protected Void doInBackground() throws Exception {

			imageKey = new HashMap<>();
			totalFileNums = getFileNums(openFiles, 0);
			System.out.println(totalFileNums);
			long ms = System.currentTimeMillis();
			getValidFiles(openFiles).stream().forEach(s -> {
				if (!this.isCancelled()) {
					String type = s.getName().substring(s.getName().lastIndexOf(".") + 1);
					switch (type) {
					case "xlsx" -> {
						saveData(s);
						count++;
					}
					case "jpeg", "jpg", "png" -> {
						saveImage(s);
						count++;
					}
					}
				}
			});
			DataFile.customer.entrySet().stream().forEach(en -> {
				if (imageKey.containsKey(en.getKey())) {
					en.getValue().setImageKey(imageKey.get(en.getKey()));
				}
			});
			System.out.println(System.currentTimeMillis() - ms);
			System.out.println(count);

//			errormessage.forEach(System.out::println);
//			File error = new File("需更改.txt");
//			if(!error.exists()) {
//				error.createNewFile();
//			}
//			FileOutputStream writerStream = new FileOutputStream(error);
//			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
//			for(String str:errormessage) {
//				fw.write(str+System.lineSeparator());
//			}
//			fw.close();
			return null;

		}

		@Override
		protected void done() {
			TableSettings.show(
					CustomerData.table_CusData, Const.COLUMNS_FOR_CUSTOMERDATA, DataFile.customer.entrySet().stream()
							.map(Map.Entry::getValue).map(Customer::showInfosOnTable).toArray(Object[][]::new),
					Const.CUSDATA_COLUMN_SIZE);
			GlamChamber.customer_data.addSearchKey();
			if (count == totalFileNums) {
				JOptionPane.showMessageDialog(null, "讀取完成");
			}
			GlamChamber.dataNotSaved();
			dispose();

		}

		private void saveImage(File file) {
			File[] currentFileArray = Stream.of(file.getParentFile().listFiles())
					.filter(f -> f.getName().endsWith(".xlsx")).toArray(File[]::new);
			String name = null;
			if (currentFileArray.length == 0) {
				JOptionPane.showMessageDialog(null, file.getName() + "：請將圖片放在和excel檔同一個資料夾中");
				return;
			} else {
				name = currentFileArray[0].getName().replace(".xlsx", "");
			}

			if (!imageKey.containsKey(name)) {
				imageKey.put(name, new ArrayList<>(Arrays.asList(file.getName())));
			} else {
				if (!imageKey.get(name).contains(file.getName())) {
					imageKey.get(name).add(file.getName());
				} else {
					JOptionPane.showMessageDialog(null, file.getName() + "，已存在");
					return;
				}
			}
			File img = new File(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)+File.separator+file.getName());
			try {
				ImageIO.write(ImageIO.read(file),file.getName().substring(file.getName().lastIndexOf(".")+1),img);
			}catch(EmptyFileException e){
				return;
			}catch (ClosedByInterruptException e) {
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
			current++;

			double num = (double) current / (double) totalFileNums;
			String percent = df.format(num);
			progressLabel.setText(file.getPath());
			progressBar.setString(percent);
			progressBar.setValue((int) (num * 100));

		}

		private void saveData(File file) {
			String keyName = file.getName().replace(".xlsx", "");
			String parentFolderName = file.getParentFile().getName();

			if (!parentFolderName.equals(keyName)) {
				JOptionPane.showMessageDialog(null, parentFolderName + "----" + file.getName() + "  檔案名稱必須和資料夾名稱相同");
				return;
			}
			XSSFSheet sheet = null;
			TreeMap<String, List<List<String>>> map = new TreeMap<>();
			List<Object[]> detailKeyLocate = new ArrayList<>();

			try (XSSFWorkbook wb =new XSSFWorkbook(file)){
				sheet = wb.getSheetAt(0);
				XSSFRow rowForInfo;
				Customer customer = new Customer();

				customer.setId(keyName);
				customer.setGender(gender);
				for (int info : customerInfoLocate) {
					rowForInfo = sheet.getRow(1);
					XSSFCell cells = rowForInfo.getCell(info);
					String cellValue;
					if (cells != null) {
						cellValue = getCellTypeToString(cells, Const.CHINESE_MD_SIMPLE_DATE_FORMAT);
					} else {
						cellValue = "";
					}
					switch (info) {
					case 1 -> customer.setName(cellValue);
					case 9 -> {
						if(cellValue.length()==0 && isUnKnown) {
							cellValue = "不詳";
						}else if(cellValue.length()==0){
							cellValue = new StringBuilder(customer.getId().substring(0,4)).insert(2,"月").insert(5,"日").toString();
						}else {
							matcher = comPattern.matcher(cellValue);
							cellValue = matcher.find()?matcher.group():new StringBuilder(customer.getId().substring(0,4)).insert(2,"月").insert(5,"日").toString();
						}
						customer.setBirthDate(cellValue);
					}
					case 10 -> customer.setPhoneNumber(cellValue);
					case 11 -> customer.setFacebook(cellValue);
					case 12 -> customer.setLineOrWechat(cellValue);
					case 13 -> customer.setAddress(cellValue);
					case 14 -> customer.setEmail(cellValue);
					}
				}

				int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
				for (int i = 1; i < physicalNumberOfRows; i++) {
					XSSFRow rows;
					XSSFCell cells;
					rows = sheet.getRow(i);
					cells = rows.getCell(0);
					String cellValue;
					if (cells != null) {
						cellValue = getCellTypeToString(cells, Const.SLASH_YMD_SIMPLE_DATE_FORMAT);
						if (cellValue.length() != 0) {
							detailKeyLocate.add(new Object[] { i, cellValue });
						}
					}
				}

				for (int i = 0; i < detailKeyLocate.size(); i++) {
					if (i == detailKeyLocate.size() - 1) {
						dealShoppingDetails((int) detailKeyLocate.get(i)[0], physicalNumberOfRows, sheet,
								detailKeyLocate.get(i)[1].toString(), map);
					} else {
						dealShoppingDetails((int) detailKeyLocate.get(i)[0], (int) detailKeyLocate.get(i + 1)[0], sheet,
								detailKeyLocate.get(i)[1].toString(), map);
					}
				}
				customer.setShoppingDetailData(map);
				DataFile.customer.put(keyName, customer);

				progressLabel.setText(file.getPath());
				current++;
				double num = (double) current / (double) totalFileNums;
				String percent = df.format(num);
				progressBar.setString(percent);
				progressBar.setValue((int) (num * 100));

			} catch (EmptyFileException e) {
				return;
			} catch (ClosedByInterruptException e) {
				return;
			}catch (NullPointerException e) {
				e.printStackTrace();
				showErrorLineMesage(file, sheet);
			}catch (InvalidFormatException e) {
				e.printStackTrace();
				showErrorLineMesage(file, sheet);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private void showErrorLineMesage(File file, XSSFSheet sheet) {
			int physicalNumberOfRows2 = sheet.getPhysicalNumberOfRows();
			JOptionPane.showMessageDialog(null, file.getName() + "格式錯誤" + "有效行" + physicalNumberOfRows2 + "行");
		}

		private String getCellTypeToString(XSSFCell cell, DateFormat formater) {
			String cellValue = null;
			switch (cell.getCellType()) {
			case STRING:
				cellValue = cell.getStringCellValue();
				break;

			case FORMULA:
				cellValue = cell.getCellFormula();
				break;

			case NUMERIC:
				String dataFormat = cell.getCellStyle().getDataFormatString();
				boolean containsFormat = format.contains(dataFormat);
				if (containsFormat) {
					cell.setCellType(CellType.STRING);
					cellValue = cell.getStringCellValue();
				} else if (DateUtil.isCellDateFormatted(cell) || dataFormat.contains("reserved")) {
					cellValue = formater.format(cell.getDateCellValue());
				} else {
					cell.setCellType(CellType.STRING);
					cellValue = cell.getStringCellValue();
				}
				break;

			case BLANK:
				cellValue = "";
				break;

			case BOOLEAN:
				cellValue = Boolean.toString(cell.getBooleanCellValue());
				break;
			case ERROR:
				cellValue = cell.getErrorCellString();
			case _NONE:
				break;
			default:
				break;
			}

			return cellValue;
		}

		private void dealShoppingDetails(int start, int end, XSSFSheet sheet, String key,
				TreeMap<String, List<List<String>>> map) {
			List<List<String>> details = new ArrayList<>();
			XSSFRow rows;
			XSSFCell cells;
			for (int i = start; i < end; i++) {
				List<String> data = new ArrayList<>();
				rows = sheet.getRow(i);
				for (int j = 2; j < 9; j++) {
					if (rows.getCell(j) == null) {
						data.add("");
					} else {
						cells = rows.getCell(j);
						cells.setCellType(CellType.STRING);
						data.add(cells.getStringCellValue());
					}
				}
				details.add(data);
			}
			if (map.containsKey(key)) {
				map.get(key).addAll(details);
			} else {
				map.put(key, details);
			}
		}

		private List<File> getValidFiles(File[] RootFiles) {
			List<File> fileList = new ArrayList<>();
			for (File stamFiles : RootFiles) {
				if (stamFiles.isDirectory()) {
					fileList = loopAddFiles(stamFiles.listFiles(), fileList);
				} else {
					fileList = loopAddFiles(sortFileArray(RootFiles), fileList);
					break;
				}
			}
//			System.out.println("可讀檔案："+fileList.size());
//			fileList.forEach(f->System.out.println(f.getName()));
			return fileList;
		}

		private List<File> loopAddFiles(File[] file, List<File> fileList) {
			for (File f : file) {
				if (isValidFile(f)) {
					fileList.add(f);
				} else if (f.isDirectory()) {
					loopAddFiles(sortFileArray(f.listFiles()), fileList);
				}
			}
			return fileList;
		}

		private boolean isValidFile(File file) {
			String name = file.getName();
			String type = name.substring(name.lastIndexOf(".") + 1);
			boolean notHiddenTag = !(name.startsWith("~$") || name.equals(".DS_Store") || name.startsWith("._"));
			if (file.isFile() && notHiddenTag && vaildFileType.contains(type)) {
				return true;
			}
			return false;
		}

		private int getFileNums(File[] file, int total) {
			for (File f : file) {
				if (isValidFile(f)) {
					total += 1;
				} else if (f.isDirectory()) {
					total = getFileNums(f.listFiles(), total);
				}
			}
			return total;
		}

		private File[] sortFileArray(File[] file) {
			return Stream.of(file).sorted(Comparator.comparing(File::getName)).toArray(File[]::new);
		}

	}
}
