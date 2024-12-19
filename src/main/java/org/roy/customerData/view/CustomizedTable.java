package org.roy.customerData.view;

import java.awt.Font;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.roy.customerData.function.CustomizedPhotoCache;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.PhotoTableSettings;
import org.roy.settings.listener.extend.CustomizedSheetTableWindowListener;

public class CustomizedTable extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTable table_2;
	private JTable table_3;
	private JTable table_1;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnPreviousCus;
	private JButton btnNextCus;
	private JLabel label_CusName;
	private JLabel labelPage;
	private String key;
	private int page = 1;
	private LinkedHashMap<String, ImageIcon> image ;
	private List<JTable> tables;
	private final String NO_CUSTOMIZED_SHEET= "無訂製表";
	private final String NO_CUSTOMER= "《》";
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;

	/**
	 * Create the panel.
	 */
	public CustomizedTable() {

		initComponents();

		pageButtonEnable();

		initLayout();

	}

	private void initComponents() {
		label_CusName = new JLabel(NO_CUSTOMER);
		labelPage = new JLabel(NO_CUSTOMIZED_SHEET);
		label_CusName.setFont(new Font("Dialog", Font.BOLD, 26));
		labelPage.setFont(new Font("Dialog", Font.BOLD, 20));
		label_CusName.setHorizontalAlignment(SwingConstants.CENTER);
		labelPage.setHorizontalAlignment(SwingConstants.CENTER);

		btnPreviousCus = new JButton("上一位");
		btnNextCus = new JButton("下一位");
		btnPrevious = new JButton("上一頁");
		btnNext = new JButton("下一頁");

		btnPreviousCus.setFocusPainted(false);
		btnNextCus.setFocusPainted(false);
		btnPrevious.setFocusPainted(false);
		btnNext.setFocusPainted(false);

		btnPreviousCus.setFont(new Font("Dialog", Font.BOLD, 16));
		btnNextCus.setFont(new Font("Dialog", Font.BOLD, 16));
		btnPrevious.setFont(new Font("Dialog", Font.BOLD, 16));
		btnNext.setFont(new Font("Dialog", Font.BOLD, 16));

		btnPreviousCus.addActionListener(e -> {
			new CustomizedPhotoCache(DataFile.customer.get(getPreviousKey()).getImageKey(),getPreviousKey()).execute();
			Functions.setShopDetails(getPreviousKey());
		});
		btnNextCus.addActionListener(e -> {
			new CustomizedPhotoCache(DataFile.customer.get(getNextKey()).getImageKey(),getNextKey()).execute();
			Functions.setShopDetails(getNextKey());
		});
		btnPrevious.addActionListener(e-> {
			page -= 1;
			removeAllPics();
			decidePicTable();
			setLabelPage("《第" + page + "頁》");
			pageButtonEnable();
		});
		btnNext.addActionListener(e -> {
			page += 1;
			removeAllPics();
			decidePicTable();
			setLabelPage("《第" + page + "頁》");
			pageButtonEnable();
		});
		btnPreviousCus.setEnabled(false);
		btnNextCus.setEnabled(false);

		table_1 = new JTable();
		table_2 = new JTable();
		table_3 = new JTable();
		tables = Arrays.asList(table_1,table_2,table_3);
		tables.forEach(e->e.setFocusable(false));
		tables.forEach(s->s.addMouseListener(new CustomizedSheetTableWindowListener(s)));

		scrollPane_1 = new JScrollPane();
		scrollPane_2 = new JScrollPane();
		scrollPane_3 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setViewportView(table_1);
		scrollPane_2.setViewportView(table_2);
		scrollPane_3.setViewportView(table_3);
	}

	public void releaseImageMemory() {
		if(getImage()!=null) {
			getImage().entrySet().stream().forEach(entry->entry.getValue().getImage().flush());
			Functions.recycle(getImage());
		}
	}

	public void setCusName(String name) {
		label_CusName.setText("《" + name + "》");
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}

	public String getNextKey() {
		return DataFile.searchKey.get(DataFile.searchKey.indexOf(key) + 1);
	}

	public String getPreviousKey() {
		return DataFile.searchKey.get(DataFile.searchKey.indexOf(key) - 1);
	}

	public void setImage(LinkedHashMap<String, ImageIcon> linkedHashMap) {
		this.image = linkedHashMap;
	}

	public List<JTable> getTables() {
		return tables;
	}

	public void setTables(List<JTable> tables) {
		this.tables = tables;
	}

	public void setLabelPage(String page) {
		labelPage.setText(page);
	}

	public String getLabelPage() {
		return labelPage.getText();
	}

	public LinkedHashMap<String, ImageIcon> getImage() {
		return image;
	}

	public List<String> getSearchList(){
		return DataFile.searchKey;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void cusButtonEnable() {
		if (DataFile.searchKey == null || DataFile.searchKey.size() == 1 || DataFile.searchKey.size() == 0) {
			btnPreviousCus.setEnabled(false);
			btnNextCus.setEnabled(false);
		} else if (DataFile.searchKey.indexOf(key) == 0) {
			btnPreviousCus.setEnabled(false);
			btnNextCus.setEnabled(true);
		} else if (DataFile.searchKey.indexOf(key) == DataFile.searchKey.size() - 1) {
			btnPreviousCus.setEnabled(true);
			btnNextCus.setEnabled(false);
		} else {
			btnPreviousCus.setEnabled(true);
			btnNextCus.setEnabled(true);
		}
	}

	public void pageButtonEnable() {
		int limit = 1;
		if(image == null || image.size()==0) {
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			return;
		}else {
			limit = image.size() % 3 == 0 ? image.size() / 3 : image.size() / 3 + 1;
		}

		if (limit == 1) {
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
		} else if (page == 1) {
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(true);
		} else if (page == limit) {
			btnNext.setEnabled(false);
			btnPrevious.setEnabled(true);
		} else {
			btnPrevious.setEnabled(true);
			btnNext.setEnabled(true);
		}
	}

	public void setNoCustomizedSheet() {
		labelPage.setText(NO_CUSTOMIZED_SHEET);
	}

	public void removeAllPics() {
		for (int i = 0; i < 3; i++) {
			((DefaultTableModel)tables.get(i).getModel()).setNumRows(0);
			((DefaultTableModel)tables.get(i).getModel()).setColumnCount(0);
		}
	}

	public void decidePicTable() {
		int tempPage = (page - 1) * 3;
		for (JTable t : tables) {
			getImage().entrySet().stream().skip(tempPage + tables.indexOf(t)).limit(1)
			.forEach(entry -> PhotoTableSettings.showImage(t,entry.getKey(),entry.getValue()));
		}
	}

	public void clearDetail() {
		setKey(null);
		label_CusName.setText(NO_CUSTOMER);
		labelPage.setText(NO_CUSTOMIZED_SHEET);
		removeAllPics();
		releaseImageMemory();
		setImage(null);
		pageButtonEnable();
		btnNextCus.setEnabled(false);
		btnPreviousCus.setEnabled(false);
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(0,180,268)
							.addComponent(btnPreviousCus,100, 100, Short.MAX_VALUE)
							.addGap(4)
							.addComponent(btnNextCus,100,100, Short.MAX_VALUE)
							.addGap(30)
							.addComponent(label_CusName, 373, 373, Short.MAX_VALUE)
							.addGap(30)
							.addComponent(btnPrevious,82,82, Short.MAX_VALUE)
							.addGap(24,24,24)
							.addComponent(labelPage,172, 172, Short.MAX_VALUE)
							.addGap(24,24,24)
							.addComponent(btnNext,82,82, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
							.addGap(4)
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
							.addGap(4)
							.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)))
					.addGap(5))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnPreviousCus, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNextCus, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelPage, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(btnPrevious, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(label_CusName)))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
						.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
						.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE))
					.addGap(5))
		);
		setLayout(groupLayout);
	}

}
