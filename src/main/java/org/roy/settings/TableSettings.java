package org.roy.settings;

import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TableSettings {


	private TableSettings() {}

	public static void show(JTable table, Object[] columns, Object[][] datainfo,
			Map<Integer, Integer> columnsSize) {

		DefaultTableModel tablemodel = new DefaultTableModel(datainfo, columns);
		table.setModel(tablemodel);
		table.setRowHeight(60);

		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n; i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setCellRenderer(new RowRenderer());
		}
		JTableHeader jh = table.getTableHeader();
		jh.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		columnsSize.entrySet().stream()
				.forEach(entry -> table.getColumnModel().getColumn(entry.getKey()).setPreferredWidth(entry.getValue()));
	}

	public static JTable getInfoTable() {
		JTable table = new JTable() {
			private static final long serialVersionUID = 4390992057092163921L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		table.setFocusable(false);
		return table;
	}

	public static int getVerticalScrollBarValue(JTable table) {
		return ((JScrollPane)table.getParent().getParent()).getVerticalScrollBar().getValue();
	}

	public static void scrollToBottom(JTable table,int rowCount) {

		Rectangle cellRect = table.getCellRect(rowCount, 0, true);
		table.scrollRectToVisible(cellRect);
		table.scrollRectToVisible(cellRect);

	}

	public static void scrollTo(JTable table, int cord) {
		JScrollPane sp =((JScrollPane)table.getParent().getParent());
		sp.getVerticalScrollBar().setValue(cord);

	}
}



class RowRenderer extends JTextPane implements TableCellRenderer {

	private static final long serialVersionUID = -8687311573566902569L;
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (value != null) {
			setText(value.toString());

			StyledDocument sd = getStyledDocument();
			SimpleAttributeSet sa = new SimpleAttributeSet();
			StyleConstants.setAlignment(sa, StyleConstants.ALIGN_CENTER);
			sd.setParagraphAttributes(0, sd.getLength(), sa, false);
			setDocument(sd);
			setFont(new Font("Lucida Grande", Font.BOLD, 16));
			setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
			if (table.getRowHeight(row) < getPreferredSize().height) {
				table.setRowHeight(row, getPreferredSize().height);
			}
			if(getPreferredSize().height > 60 && (getPreferredSize().height != table.getRowHeight(row))) {
				table.setRowHeight(row, getPreferredSize().height);
			}
//			System.out.println(row+":"+table.getRowHeight(row)+",prefer"+getPreferredSize().height);
		}
		if (column == 0 && DataFile.vipList.contains(value.toString())) {
			setBackground(DataFile.vipMark);
		} else {
			setBackground(UIManager.getColor("Table.background"));
		}
		if (value!= null &&value.toString().length() != 0  ) {
			setToolTipText(value.toString());
		}
		if (isSelected) {
			setBackground(UIManager.getColor("Table.selectionBackground"));
		} else {
			setForeground(UIManager.getColor("Button.foreground"));
		}

		return this;
	}

//	public void updateRowHeight(JTable table) {
//	    final int rowCount = table.getRowCount();
//	    final int colCount = table.getColumnCount();
//	    for (int i = 0; i < rowCount; i++) {
//	        int maxHeight = 0;
//	        for (int j = 0; j < colCount; j++) {
//	            final TableCellRenderer renderer = table.getCellRenderer(i, j);
//	            maxHeight = Math.max(maxHeight, table.prepareRenderer(renderer, i, j).getPreferredSize().height);
//	        }
//	        table.setRowHeight(i, maxHeight);
//	    }
//	}
}


