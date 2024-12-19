package org.roy.dailySaleSheet.view;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.TableSettings;
import org.roy.settings.listener.extend.SaleSheetTableListener;

public class DailySaleTable extends JPanel {

	private static final long serialVersionUID = 1L;
	public static JTable table_saleSheet;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public DailySaleTable() {

		initComponents();
		initLayout();
		TableSettings.show(table_saleSheet,Const.COLUMNS_FOR_SALES_SHEET,
				DataFile.saleSheet.entrySet().stream().map(Map.Entry::getValue).flatMap(s->Arrays.stream(s.convertBeanToTableInfo())).toArray(Object[][]::new)
				, Const.SALE_SHEET_COLUMN_SIZE);
	}

	private void initComponents() {
		table_saleSheet = TableSettings.getInfoTable();
		table_saleSheet.addMouseListener(new SaleSheetTableListener(table_saleSheet));
		table_saleSheet.getTableHeader().setPreferredSize(new Dimension(table_saleSheet.getColumnModel().getTotalColumnWidth(),90));
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_saleSheet);
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
		);
		setLayout(groupLayout);
	}
}
