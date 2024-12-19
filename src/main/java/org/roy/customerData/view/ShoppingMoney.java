package org.roy.customerData.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.roy.homepage.application.GlamChamber;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.listener.extend.EditWindowTableListener;

public class ShoppingMoney extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Object[] columns = { "日期", "收入", "支出", "餘額" };
	private Object oldValue = "";
	private DefaultTableModel defaultTableModel;
	private List<List<String>> shoppingMoney;
	private JButton btn_addNewRow;
	private JButton btn_cancel;
	private JLabel lblNewLabel;
	private JButton btn_confirm;
	private JScrollPane scrollPane;
	private String key;

	public ShoppingMoney(String key) {
		this.key = key;
		Functions.defaultJFrameSettings(this,2.5,1.3);

		initComponents();
		initLayout();
	}

	private void initComponents() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		scrollPane = new JScrollPane();

		lblNewLabel = new JLabel(key);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		shoppingMoney = DataFile.customer.get(key).getShoppingMoney();
		defaultTableModel = new DefaultTableModel(
				shoppingMoney == null ? null
						: shoppingMoney.stream().map(s -> s.toArray()).toArray(Object[][]::new),
				columns);
		table = new JTable() {
			private static final long serialVersionUID = -272833393488353905L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 3) {
					return false;
				}
				return true;
			}

		};
		table.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		table.setModel(defaultTableModel);
		table.setRowHeight(30);
		table.setCellSelectionEnabled(true);
		DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
		defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(defaultTableCellRenderer);
		}
		table.addMouseListener(new EditWindowTableListener(table,this));
		table.addPropertyChangeListener(this::tableCellValueChange);

		scrollPane.setViewportView(table);

		btn_confirm = new JButton("確定");
		btn_addNewRow = new JButton("新增");
		btn_cancel = new JButton("取消");

		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_addNewRow.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		btn_confirm.addActionListener(this::confirmShoppingMoney);
		btn_addNewRow.addActionListener(this::addNewRow);
		btn_cancel.addActionListener(e->dispose());
	}


	public void updateRemain(int selectedRow) {
		for (int i = selectedRow; i < table.getRowCount(); i++) {
			defaultTableModel.setValueAt(getRemain(i), i, 3);
		}
	}

	public int getRemain(int row) {
		int income = table.getValueAt(row, 1).toString().length() == 0 ? 0
				: Integer.parseInt(table.getValueAt(row, 1).toString());
		int expense = table.getValueAt(row, 2).toString().length() == 0 ? 0
				: Integer.parseInt(table.getValueAt(row, 2).toString());
		return row == 0 ? 0 + income - expense
				: Integer.parseInt(table.getValueAt(row - 1, 3).toString()) + income - expense;
	}

	public boolean checkRows() {
		for(int i = 0;i <table.getRowCount();i++) {
			for(int j = 0;j<4;j++) {
				boolean incomeExpense = table.getValueAt(i,1).toString().length()==0&&table.getValueAt(i,2).toString().length()==0;
				if(table.getValueAt(i,0).toString().length()==0 || incomeExpense) {
					JOptionPane.showMessageDialog(null,"請檢查第"+(i+1)+"行");
					return false;
				}
			}
		}
		return true;
	}

	public void tableCellValueChange(PropertyChangeEvent e) {
        if ("tableCellEditor".equals(e.getPropertyName())){
            int selectedRow = table.getSelectedRow();
			int selectedColumn = table.getSelectedColumn();
			if (table.isEditing()) {
            	oldValue = table.getValueAt(selectedRow,selectedColumn);
            	((JTextField)((DefaultCellEditor)(table.getCellEditor())).getComponent()).selectAll();
            }else{
				if (selectedColumn == 1 || selectedColumn == 2) {
					if(Functions.isNumberic(table.getValueAt(selectedRow,selectedColumn).toString(),false,true)) {
						switch (selectedColumn) {
						case 1 -> defaultTableModel.setValueAt(getRemain(selectedRow), selectedRow, 3);
						case 2 -> defaultTableModel.setValueAt(getRemain(selectedRow), selectedRow, 3);
						}
						if (selectedRow < table.getRowCount() - 1) {
							updateRemain(selectedRow);
						}
					}else {
						JOptionPane.showMessageDialog(null,table.getColumnName(selectedColumn)+"錯誤");
						table.setValueAt(oldValue,selectedRow,selectedColumn);
						return;
					}
				}
            }
        }
	}

	public void confirmShoppingMoney(ActionEvent e) {
		if(table.getRowCount()!=0) {
			List<List<String>> list= new ArrayList<>();
			if(checkRows()) {
				for(int i = 0;i<table.getRowCount();i++) {
					list.add(Arrays.asList(table.getValueAt(i,0).toString(),
										   table.getValueAt(i,1).toString(),
										   table.getValueAt(i,2).toString(),
										   table.getValueAt(i,3).toString()));
				}
				DataFile.customer.get(key).setShoppingMoney(list);
			}else {
				return;
			}
		}else {
			shoppingMoney.clear();
		}
		GlamChamber.dataNotSaved();
		dispose();
	}

	public void addNewRow(ActionEvent e) {
		defaultTableModel.addRow(new Object[] { "", "", "",table.getRowCount()==0?"0":table.getValueAt(table.getRowCount()-1, 3)});
	}

	private void initLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(88)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
						.addGap(88))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(54)
						.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
						.addGap(54)
						.addComponent(btn_addNewRow, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
						.addGap(54)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
						.addGap(54))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
						.addGap(27)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(btn_addNewRow, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGap(24))
				);
		contentPane.setLayout(gl_contentPane);
	}
}
