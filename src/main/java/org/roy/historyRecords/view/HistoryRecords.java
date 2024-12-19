package org.roy.historyRecords.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.TableSettings;
import org.roy.settings.listener.extend.ButtonKeyListener;
import org.roy.settings.listener.extend.HistoryRecordTableListener;

public class HistoryRecords extends JPanel implements FocusListener{
	private static final long serialVersionUID = 5033973921317585154L;
	public static JTable table_records;
	private JScrollPane scrollPane;
	private JTextField i_search;
	private JButton btn_search;
	private ButtonKeyListener bkl;
	private static JComboBox<String> comboBox_sortType;
	private List<List<String>> filterRecords;
	private JComboBox<String> comboBox_SystemType;
	private final String[] systemType = {"全部","顧客資料", "購物明細", "庫存", "庫存-出入庫", "零用金","銷售表","銷售額","公告"};


	public HistoryRecords() {
		setSize(Const.SCREENSIZE);
		initComponents();
		initLayout();
		comboBox_sortType.setSelectedIndex(0);
	}

	private void initComponents() {

		table_records = TableSettings.getInfoTable();
		table_records.addMouseListener(new HistoryRecordTableListener(table_records));
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_records);

		btn_search = new JButton("搜尋");
		btn_search.setFocusPainted(false);
		btn_search.addActionListener(this::searchRecords);
		btn_search.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		bkl = new ButtonKeyListener(btn_search);

		i_search = new JTextField();
		i_search.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		i_search.addFocusListener(this);
		i_search.setColumns(10);


		comboBox_SystemType = new JComboBox<>();
		comboBox_sortType = new JComboBox<>();
		comboBox_sortType.setFont(new Font("Dialog", Font.BOLD, 16));
		comboBox_SystemType.setFont(new Font("Dialog", Font.BOLD, 16));
		comboBox_sortType.setModel(new DefaultComboBoxModel<>(new String[] { "全部","今天", "昨天", "一星期內", "一個月內"}));
		comboBox_SystemType.setModel(new DefaultComboBoxModel<>(systemType));
		comboBox_sortType.setRenderer(Const.DLCR);
		comboBox_SystemType.setRenderer(Const.DLCR);
		comboBox_sortType.addActionListener(this::sortRecords);
		comboBox_SystemType.addActionListener(this::sortRecords);
	}

	private void sortRecords(ActionEvent e) {
		switch (comboBox_sortType.getSelectedItem().toString()) {
		case "今天" -> setTimeSort(0);
		case "昨天" -> setTimeSort(1);
		case "一星期內" -> setTimeSort(7);
		case "一個月內" -> setTimeSort(30);
		case "全部" -> filterRecords = DataFile.historyRecords.values().stream().flatMap(re->re.stream()).collect(Collectors.toList());
		}

		String type = comboBox_SystemType.getSelectedItem().toString();

		switch(comboBox_SystemType.getSelectedIndex()) {
		case 0-> showRecordsOnTable(filterRecords);
		case 4-> {
			filterRecords = filterRecords.stream().filter(this::isInputOutput).collect(Collectors.toList());
			showRecordsOnTable(filterRecords);
		}
		default -> {
			filterRecords = filterRecords.stream().filter(li->type.equals(li.get(1))).collect(Collectors.toList());
			showRecordsOnTable(filterRecords);
		}
		}
		TableSettings.scrollToBottom(table_records,table_records.getRowCount()-1);
	}

	public boolean isInputOutput(List<String> list) {
		return "出庫".equals(list.get(3)) || "入庫".equals(list.get(3));
	}

	public boolean containsSearch(List<String> ele) {
		String search = i_search.getText();
		for(String str:ele) {
			if(Functions.containsIgnoreCase(str,search)) {
				return true;
			}
		}
		return false;
	}

	public static void showRecordsOnTable(List<List<String>> list) {
		TableSettings.show(table_records, Const.COLUMNS_FOR_HISTORY_RECORDS, list.stream().map(li->li.toArray()).toArray(Object[][]::new),Const.HISTORY_RECORDS_COLUMN_SIZE);
	}

	public static void addRecords(List<String> event) {
//		System.out.println(getRecordsTotalSize());
		if(getRecordsTotalSize()>2000) {
			Entry<String, List<List<String>>> firstEntry = DataFile.historyRecords.firstEntry();
			firstEntry.getValue().remove(0);
			if(firstEntry.getValue().size()==0) {
				DataFile.historyRecords.remove(firstEntry.getKey());
			}
		}
		Date date = new Date();
		event.add(0,Const.HYPHEN_YMDHM_SIMPLE_DATE_FORMAT.format(date));
		event.add(4,GlamChamber.userName);
		String dateString = Const.HYPHEN_YMDHM_SIMPLE_DATE_FORMAT.format(date).substring(0,10);
		if(DataFile.historyRecords.containsKey(dateString)) {
			DataFile.historyRecords.get(dateString).add(event);
		}else {
			List<List<String>> records= new ArrayList<>();
			records.add(event);
			DataFile.historyRecords.put(dateString,records);
		}
		if(GlamChamber.history_Records != null) {
			comboBox_sortType.setSelectedIndex(0);
		}
		GlamChamber.dataNotSaved();
	}

	private void setTimeSort(int range) {
		LocalDate now = LocalDate.now();
		LocalDate end = range < 2 ? (range == 0 ? now.plusDays(1) : now) : now.plusDays(1);
		LocalDate start = range == 30 ? end.minusMonths(1) : end.minusDays(range < 2 ? 1 : range);

		List<String> duration = start.datesUntil(end).map(d->Const.DATETIMEFORMATTER.format(d)).collect(Collectors.toList());
		filterRecords = DataFile.historyRecords.entrySet().stream()
						.filter(entry-> duration.contains(entry.getKey()))
						.flatMap(en->en.getValue().stream())
						.collect(Collectors.toList());
	}

	private void searchRecords(ActionEvent e) {
//		long a = System.currentTimeMillis();
		filterRecords = DataFile.historyRecords.values().stream()
				.flatMap(value->value.stream())
				.filter(ele->containsSearch(ele))
				.collect(Collectors.toList());
		showRecordsOnTable(filterRecords);
		i_search.setText("");
//		System.out.println(System.currentTimeMillis()-a);
	}

	public static int getRecordsTotalSize() {
		return DataFile.historyRecords.entrySet().stream().mapToInt(entry->entry.getValue().size()).sum();
	}

	public List<List<String>> getFilterRecords() {
		return filterRecords;
	}

	public static JComboBox<String> getComboBox_sortType() {
		return comboBox_sortType;
	}

	@Override
	public void focusLost(FocusEvent e) {
		i_search.removeKeyListener(bkl);
	}

	@Override
	public void focusGained(FocusEvent e) {
		i_search.addKeyListener(bkl);
	}

	private void initLayout() {

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(185)
					.addComponent(i_search, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
					.addGap(22)
					.addComponent(btn_search, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(103)
					.addComponent(comboBox_SystemType, 0, 273, Short.MAX_VALUE)
					.addGap(51)
					.addComponent(comboBox_sortType, 0, 273, Short.MAX_VALUE)
					.addGap(152))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(i_search, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(btn_search))
						.addComponent(comboBox_sortType, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_SystemType, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
