package org.roy.settings;

import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class PhotoTableSettings {



	public static void showImage(JTable table,Object columns,ImageIcon image) {
		Object[][] data = new Object[1][1];
		data[0][0] = image;
		DefaultTableModel tablemodel = new DefaultTableModel(data,new Object[]{columns}) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		table.setModel(tablemodel);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		for (int i = 0, n = tcm.getColumnCount(); i < n; i++) {
			TableColumn tc = tcm.getColumn(i);
			tc.setCellRenderer(new ImageRender());
		}
		table.getTableHeader().setFont(new Font("Lucida Grande",Font.BOLD,16));
	}

}

class ImageRender extends DefaultTableCellRenderer{


	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(getIcon() != (ImageIcon)value) {
			setIcon((ImageIcon)value);
			setHorizontalAlignment(SwingConstants.CENTER);
		}
		if(getAlignmentX()!=0.5f||getAlignmentY()!=0.5f) {
			setAlignmentX(0.5f);
			setAlignmentY(0.5f);
		}
		if(table.getRowHeight()!=table.getParent().getHeight()) {
			table.setRowHeight(table.getParent().getHeight());
		}
		return this;
	}

}


