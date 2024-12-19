package org.roy.customerData.view;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.roy.bean.Customer;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.TableSettings;
import org.roy.settings.listener.extend.ButtonKeyListener;
import org.roy.settings.listener.extend.CustomerDataTableActionListener;

public class CustomerData extends JPanel {
	private static final long serialVersionUID = -5086244626146864L;

	private JTextField search;
	public static JTable table_CusData;
	private JButton btn_search;
	private final String[] filterMonth= {"01","02","03","04","05","06","07","08","09","10","11","12"};

	private JScrollPane scrollPane;

	private JComboBox<String> comboBox;

	private ButtonKeyListener bkl;

	/**
	 * Create the panel.
	 */
	public CustomerData() {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());

		initComponents();

		TableSettings.show(table_CusData,Const.COLUMNS_FOR_CUSTOMERDATA,
				DataFile.customer.entrySet()
				.stream()
				.map(Map.Entry::getValue)
				.map(Customer::showInfosOnTable)
				.toArray(Object[][]::new)
				,Const.CUSDATA_COLUMN_SIZE);

		comboBox.setSelectedIndex(0);
		addSearchKey();
		initLayout();
	}

	private void initComponents() {
		search = new JTextField();
		search.setFont(new Font("Dialog", Font.PLAIN, 16));
		search.setColumns(10);

		btn_search = new JButton("搜尋");
		btn_search.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_search.setFocusPainted(false);
		btn_search.addActionListener(this::getSearchResult);

		bkl = new ButtonKeyListener(btn_search);
		search.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				search.removeKeyListener(bkl);
			}

			@Override
			public void focusGained(FocusEvent e) {
				search.addKeyListener(bkl);
			}
		});

		comboBox = new JComboBox<>(new String[] { "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月","不詳"});
		comboBox.addActionListener(this::sortCusByMonth);
		comboBox.setFont(new Font("Dialog", Font.BOLD, 16));
		comboBox.setRenderer(Const.DLCR);

		table_CusData = TableSettings.getInfoTable();
		table_CusData.addMouseListener(new CustomerDataTableActionListener(table_CusData));

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_CusData);
	}

	private void sortCusByMonth(ActionEvent e) {
		if(comboBox.getSelectedIndex()!=12) {
			TableSettings.show(table_CusData, Const.COLUMNS_FOR_CUSTOMERDATA,
					DataFile.customer.entrySet()
					.stream()
					.filter(entry->entry.getKey().substring(0,2).equals(filterMonth[comboBox.getSelectedIndex()]))
					.map(Map.Entry::getValue)
					.map(Customer::showInfosOnTable)
					.toArray(Object[][]::new),
					Const.CUSDATA_COLUMN_SIZE);
		}else {
			TableSettings.show(table_CusData, Const.COLUMNS_FOR_CUSTOMERDATA,
					DataFile.customer.entrySet()
					.stream()
					.filter(entry->entry.getValue().getBirthDate().equals("不詳"))
					.map(Map.Entry::getValue)
					.map(Customer::showInfosOnTable)
					.toArray(Object[][]::new),
					Const.CUSDATA_COLUMN_SIZE);
		}
		addSearchKey();
		clearCusDetailAfterSearching();
	}

	public static void refreshTableCell() {
		if(table_CusData != null) {
			((DefaultTableModel)table_CusData.getModel()).fireTableDataChanged();
		}
	}

	public void clearCusDetailAfterSearching() {
		if(GlamChamber.shopping_details!=null) {
			GlamChamber.shopping_details.clearDetails();
		}
		if(GlamChamber.customized_table!=null) {
			GlamChamber.customized_table.clearDetail();
		}
	}

	public void addSearchKey() {
		DataFile.searchKey.clear();
		for(int i = 0;i<((DefaultTableModel)table_CusData.getModel()).getRowCount();i++) {
			DataFile.searchKey.add(table_CusData.getValueAt(i,0).toString());
		}
	}

	public void searchClick() {
		btn_search.doClick();
	}

	public void setSearchText(String text) {
		search.setText(text);
	}

	private void getSearchResult(ActionEvent e) {

		TableSettings.show(table_CusData, Const.COLUMNS_FOR_CUSTOMERDATA,
				DataFile.customer.entrySet().stream()
						.filter(entry -> Functions.containsIgnoreCase(entry.getKey(),search.getText()))
						.map(Map.Entry::getValue)
						.map(Customer::showInfosOnTable)
						.toArray(Object[][]::new),
				Const.CUSDATA_COLUMN_SIZE);
		addSearchKey();
		clearCusDetailAfterSearching();
		search.setText("");
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(216)
					.addComponent(comboBox, 0, 193, Short.MAX_VALUE)
					.addGap(233)
					.addComponent(search, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btn_search, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
					.addGap(393))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(search, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btn_search)))
					.addGap(19)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
