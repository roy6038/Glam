package org.roy.inventory.view;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
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

import org.roy.bean.Inventory;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.TableSettings;
import org.roy.settings.listener.extend.ButtonKeyListener;
import org.roy.settings.listener.extend.InventoryDataTableActionListener;

public class InventoryData extends JPanel {
	private static final long serialVersionUID = -6999933631088862711L;
	private JTextField i_search;
	private JButton btn_search;
	public static JTable table_inventoryData;
	private static JScrollPane scrollPane;
	private JComboBox<String> comboBox;
	private ButtonKeyListener bkl;

	private boolean isSortTypeSearch;
	private String previousSearchText="";

	/**
	 * Create the panel.
	 */
	public InventoryData() {
//		setSize(Const.SCREENSIZE);

		initComponents();
		comboBox.setSelectedItem("OD");
		initLayout();
	}

	private void initComponents() {
		table_inventoryData = TableSettings.getInfoTable();
//		table_inventoryData.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//		table_inventoryData.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
//		table_inventoryData.setFocusable(false);
		table_inventoryData.addMouseListener(new InventoryDataTableActionListener(table_inventoryData));

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_inventoryData);

		i_search = new JTextField();
		i_search.setName("");
		i_search.setFont(new Font("Dialog", Font.BOLD, 16));
		i_search.setToolTipText("請輸入貨號");
		i_search.setColumns(10);


		comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Dialog", Font.BOLD, 16));
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"OD", "JY", "CY", "BAG", "客訂"}));
		comboBox.addActionListener(e->{
			sortInventoryByType();
		});
		comboBox.setRenderer(Const.DLCR);
		btn_search = new JButton("搜尋");
		btn_search.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_search.setFocusPainted(false);
		btn_search.addActionListener(e->{
			showSearchResult();
		});
		bkl = new ButtonKeyListener(btn_search);
		i_search.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				i_search.removeKeyListener(bkl);
			}

			@Override
			public void focusGained(FocusEvent e) {
				i_search.addKeyListener(bkl);
			}
		});
	}

	public void flushTable(String id) {
		if(isSortTypeSearch) {
			i_search.setText(previousSearchText);
			btn_search.doClick();
		}else {
			comboBox.setSelectedIndex(comboBox.getSelectedIndex());
		}
//		searchResult.forEach(System.out::println);
		TableSettings.scrollTo(table_inventoryData, TableSettings.getVerticalScrollBarValue(table_inventoryData));
		for(int i = 0;i<table_inventoryData.getRowCount();i++) {
			if(id.equals(table_inventoryData.getValueAt(i,0))) {
				table_inventoryData.addRowSelectionInterval(i,i);
			}
		}
	}

	public void showSearchResult() {
//		long s = System.currentTimeMillis();
		previousSearchText = i_search.getText();
		isSortTypeSearch = true;
		List<Inventory> searchResult = DataFile.inventory
				.entrySet()
				.stream()
				.filter(entry->searchConfirm(entry.getValue().getId(),entry.getValue().getName()))
				.map(en->en.getValue())
				.collect(Collectors.toList());

		for(Inventory in:searchResult) {
			if(containsJYorCY(in.getType())) {
				TableSettings.show(table_inventoryData,Const.COLUMNS_FOR_JY_AND_CY, searchResult.stream().map(Inventory::getInfoFitAllType).toArray(Object[][]::new), Const.JY_CY_COLUMN_SIZE);
				i_search.setText("");
				return;
			}
		}
		TableSettings.show(table_inventoryData,Const.COLUMNS_FOR_OD_AND_BAG,searchResult.stream().map(Inventory::getODBAGInfo).toArray(Object[][]::new), Const.OD_BAG_COLUMN_SIZE);
		i_search.setText("");
//		searchResult.forEach(System.out::println);
	}

	public void sortInventoryByType() {
		String type = comboBox.getSelectedItem().toString();
		isSortTypeSearch = false;
		List<Inventory> sortResult = DataFile.inventory.entrySet().stream().filter(entry->entry.getValue().getType().equals(type)).map(en->en.getValue()).collect(Collectors.toList());
		if(type.equals("JY")||type.equals("CY")) {
			TableSettings.show(table_inventoryData,Const.COLUMNS_FOR_JY_AND_CY,
					sortResult.stream().map(in->in.getJYCYInfo()).toArray(Object[][]::new),
					Const.JY_CY_COLUMN_SIZE);
		}else {
			TableSettings.show(table_inventoryData,Const.COLUMNS_FOR_OD_AND_BAG,
					sortResult.stream().map(in->in.getODBAGInfo()).toArray(Object[][]::new),
					Const.OD_BAG_COLUMN_SIZE);
		}
	}

	public boolean searchConfirm(String ...strings ) {
		for(String s:strings) {
			if(Functions.containsIgnoreCase(s,i_search.getText())) {
				return true;
			}
		}
		return false;
	}

	public boolean containsJYorCY(String type) {
		return type.contains("JY")||type.contains("CY");
	}

	public boolean containsODorBAG(String type) {
		return type.contains("OD")||type.contains("BAG")||type.contains("客訂");
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0,100,225)
					.addComponent(comboBox, 150, 163, Short.MAX_VALUE)
					.addGap(0,100,225)
					.addComponent(i_search, 150, 208, Short.MAX_VALUE)
					.addGap(12)
					.addComponent(btn_search, 60, 79, Short.MAX_VALUE)
					.addGap(528))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(i_search, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btn_search)))
					.addGap(16)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
