package org.roy.homepage.application;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.roy.customerData.view.CustomerData;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.calendar.CalendarCustom;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.TableSettings;
import org.roy.settings.listener.extend.EditWindowTableListener;

public class EditWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextArea info;
	private Object[] addedRows;
	private Object editInfo;
	private Object oldValue = "";
	private DefaultTableModel defaultTableModel;
	private JButton btn_confirm;
	private JButton btn_addRow;
	private JScrollPane scrollPane;
	private String editType;
	private JButton btn_cancel;
	private int selectedRowForHistoryRecordNote;

	/**
	 * Create the frame.
	 */
	public EditWindow(Object editInfo,String editType) {
		this.editInfo = editInfo;
		this.editType = editType;
		Functions.defaultJFrameSettings(this, 4, 2);
		setEditTitle();
		initComponents();
		settingDatas();
		initLayout();

	}

	private void initComponents() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		btn_confirm = new JButton("確定");
		btn_cancel = new JButton("取消");
		btn_addRow = new JButton("新增");
		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_addRow.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		btn_confirm.addActionListener(this::confirm);
		btn_cancel.addActionListener(e->dispose());
		btn_addRow.addActionListener(this::addRows);

		scrollPane = new JScrollPane();
	}

	private void addRows(ActionEvent e) {
		defaultTableModel.addRow(addedRows);
		table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));
	}

	private void confirm(ActionEvent e) {
		CalendarCustom calendarCustom = GlamChamber.homePage.getCalendarCustom();
		switch(editType) {
		case "depotLocate"-> {
			DataFile.depotLocate.clear();
			for(int i = 0;i<table.getRowCount();i++) {
				if(!(table.getValueAt(i,0).toString().length()==0&&table.getValueAt(i,1).toString().length()==0)){
					DataFile.depotLocate.put(table.getValueAt(i,0).toString(), table.getValueAt(i,1).toString());
				}
			}
			}
		case "salePerson"->{
			DataFile.salePerson.clear();
			for(int i = 0;i<table.getRowCount();i++) {
				if(!(table.getValueAt(i,0).toString().length()==0)) {
					DataFile.salePerson.add(table.getValueAt(i,0).toString());
				}
			}
		}
		case "inputOutput"->{
			DataFile.inputOutputPerson.clear();
			for(int i = 0;i<table.getRowCount();i++) {
				if(!(table.getValueAt(i,0).toString().length()==0)) {
					DataFile.inputOutputPerson.add(table.getValueAt(i,0).toString());
				}
			}
		}
		case "pettyCash"->{
			DataFile.pettyCashPerson.clear();
			for(int i = 0;i<table.getRowCount();i++) {
				if(!(table.getValueAt(i,0).toString().length()==0)) {
					DataFile.pettyCashPerson.add(table.getValueAt(i,0).toString());
				}
			}
		}
		case "announcement"->{
			String oldAnnouncement = DataFile.announcement;
			if(!oldAnnouncement.equals(info.getText())) {
				HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("公告","","更改",oldAnnouncement+"->\n"+info.getText(),"","")));
			}
			DataFile.announcement = info.getText();
			calendarCustom.setAnnouncement(DataFile.announcement);
		}
		case "VIPCUS"->{
			DataFile.vipList=Stream.of(info.getText().split("\n")).map(t->t.trim()).collect(Collectors.toList());
			CustomerData.refreshTableCell();
		}
		case "dailySaleIncome"->{
			StringBuilder sb = new StringBuilder();
			for(int i = 1;i<table.getRowCount();i++) {
				String income = table.getValueAt(i,1).toString();
				if(income.equals("0")) {
					continue;
				}else {
					String date = table.getValueAt(i,0).toString();

					if(DataFile.dailySaleIncome.get(date) == null) {
						sb.append("日期：").append(date).append("。").append("銷售額：＄").append(income).append("\n");
					}else if(!(DataFile.dailySaleIncome.get(date) == Integer.parseInt(income))){
						sb.append("日期：").append(date).append("。").append("銷售額：＄").append(DataFile.dailySaleIncome.get(date)).append("->").append(income).append("\n");
					}
					DataFile.dailySaleIncome.put(date,Integer.parseInt(income));
				}
			}
			sb.deleteCharAt(sb.length()-1);
			calendarCustom.repaint();
			calendarCustom.setSaleIncome();
			String[] split = calendarCustom.getSaleDate().split("/");
			if((Integer.valueOf(split[0]) == calendarCustom.getYear()) && (Integer.valueOf(split[1]) == calendarCustom.getMonth())) {
				calendarCustom.setMonthlySaleIncome(calendarCustom.getMonthlySaleIncome(calendarCustom.getYear(), calendarCustom.getMonth()));
			}
			HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("銷售額","","更改",sb.toString(),"","")));
		}
		case "historyRecordNote"->{
			GlamChamber.history_Records.getFilterRecords().get(selectedRowForHistoryRecordNote).set(7,info.getText());
			int locate = TableSettings.getVerticalScrollBarValue(HistoryRecords.table_records);
			HistoryRecords.showRecordsOnTable(GlamChamber.history_Records.getFilterRecords());
			TableSettings.scrollTo(HistoryRecords.table_records,locate);
		}
		}
		GlamChamber.dataNotSaved();
		dispose();
	}

	private void settingDatas() {
		if(editInfo instanceof String[][]) {
			table = new JTable();
			switch(editType) {
			case "checkDepotLocate"->{
				setTable((String[][])editInfo,new Object[] {"編號","位置"});
				setTableOnlyRead();
				return;
			}
			case "depotLocate"-> {
				setTable((String[][])editInfo,new Object[] {"編號","位置"});
			}
			case "salePerson"->{
				setTable((String[][])editInfo,new Object[] {"銷售人員"});
			}
			case "inputOutput"->{
				setTable((String[][])editInfo,new Object[] {"出入庫經手人"});
			}
			case "pettyCash"->{
				setTable((String[][])editInfo,new Object[] {"零用金經手人"});
			}
			case "dailySaleIncome"-> {
				initDailySaleIncome();
				return;
			}
			}
			tableSettings(scrollPane);
			addedRows = new Object[table.getColumnCount()];
			table.addMouseListener(new EditWindowTableListener(table));
		}else if(editInfo instanceof String) {
			btn_addRow.setVisible(false);
			info = new JTextArea();
			info.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			info.setLineWrap(true);
			switch(editType) {
			case "VIPCUS"->info.setToolTipText("請換行分割");
			case "historyRecordNote"->selectedRowForHistoryRecordNote = HistoryRecords.table_records.getSelectedRow();
			}
			scrollPane.setViewportView(info);
			info.setText(editInfo.toString());
		}
	}

	private void setTableOnlyRead() {
		btn_addRow.setVisible(false);
		btn_cancel.setVisible(false);
		btn_confirm.setVisible(false);
		table.setEnabled(false);
		table.setRowHeight(40);
		table.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		scrollPane.setViewportView(table);
	}

	private void initDailySaleIncome() {
		table = new JTable() {

			private static final long serialVersionUID = 2929605551667084647L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0||row == 0) {
					return false;
				}
				return true;
			}

		};
		setTable((Object[][])editInfo,new Object[] {"銷售日期","銷售額"});
		tableSettings(scrollPane);
		table.addPropertyChangeListener(this::saleIncomeTableCellChange);
		btn_addRow.setVisible(false);
		((DefaultTableModel)table.getModel()).insertRow(0,new Object[] {"月銷售額",0});
		table.setValueAt(getTotalSaleByMonth(),0,1);
	}

	private void tableSettings(JScrollPane scrollPane) {
		table.setCellSelectionEnabled(true);
		DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
		defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i = 0 ;i<table.getColumnCount();i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
		}
		table.setRowHeight(40);
		table.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		scrollPane.setViewportView(table);
	}

	private void setTable(Object[][] data,Object[] columns) {
		defaultTableModel = new DefaultTableModel(data,columns);
		table.setModel(defaultTableModel);
	}

	private Integer getTotalSaleByMonth() {
		int total = 0;
		for(int i = 1;i<table.getRowCount();i++) {
			total += Integer.valueOf(table.getValueAt(i,1).toString());
		}
		return total;
	}

	public void setEditTitle() {
		switch(editType) {
		case "checkDepotLocate"->setTitle("查看倉位編號");
		case "depotLocate"->setTitle("更改倉位名");
		case "salePerson"->setTitle("更改銷售人員");
		case "inputOutput"->setTitle("更改出入庫經手人");
		case "pettyCash"->setTitle("更改零用金經手人");
		case "announcement"->setTitle("更改公告");
		case "dailySaleIncome"->setTitle("更改銷售額");
		case "historyRecordNote"->setTitle("更改歷史紀錄備註");
		}
	}

	private void saleIncomeTableCellChange(PropertyChangeEvent e) {
	    if ("tableCellEditor".equals(e.getPropertyName())){
	        int selectedRow = table.getSelectedRow();
			if (table.isEditing()) {
	        	oldValue = table.getValueAt(selectedRow,1);
	        	((JTextField)((DefaultCellEditor)(table.getCellEditor())).getComponent()).selectAll();
	        }else{
				if(Functions.isNumberic(table.getValueAt(selectedRow,1).toString(),true,false)) {
					table.setValueAt(getTotalSaleByMonth(),0,1);
				}else {
					JOptionPane.showMessageDialog(null,table.getColumnName(1)+"錯誤");
					table.setValueAt(oldValue,selectedRow,1);
					return;
				}
	        }
	    }

	}

	private void initLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(6)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
						.addGap(6))
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
						.addComponent(btn_addRow, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(6)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
						.addGap(12)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(btn_addRow, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGap(9))
				);
		contentPane.setLayout(gl_contentPane);
	}
}
