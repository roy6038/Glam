package org.roy.customerData.view;

import java.awt.AWTEvent;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.roy.customerData.function.CustomizedPhotoCache;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.TableSettings;
import org.roy.settings.listener.extend.ShoppingDateComboBoxListener;
import org.roy.settings.listener.extend.ShoppingDetailTableActionListener;

public class ShoppingDetails extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTable table_details;
	public JComboBox<String> comboBox;
	private JLabel Label_CusName;
	private JButton btn_next;
	private JButton btn_previous;
	private String key;
	private Map<String, List<List<String>>> currentShoppingDetail = new TreeMap<>();
	private JLabel label_TotalConsume;
	private final DefaultComboBoxModel<String> emptyModel = new DefaultComboBoxModel<>(new String[0]);
	private final String NO_CUSTOMER= "《》";
	private JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	public ShoppingDetails() {
		initComponents();
		initLayout();
	}

	private void initComponents() {
		btn_previous = new JButton("上一位");
		btn_next = new JButton("下一位");
		btn_previous.setFocusPainted(false);
		btn_next.setFocusPainted(false);
		btn_previous.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_next.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_previous.addActionListener(e-> {
				new CustomizedPhotoCache(DataFile.customer.get(getPreviousKey()).getImageKey(),getPreviousKey()).execute();
				Functions.setShopDetails(getPreviousKey());
		});
		btn_next.addActionListener(e-> {
			new CustomizedPhotoCache(DataFile.customer.get(getNextKey()).getImageKey(),getNextKey()).execute();
			Functions.setShopDetails(getNextKey());
		});
		btn_previous.setEnabled(false);
		btn_next.setEnabled(false);

		Label_CusName = new JLabel(NO_CUSTOMER);
		label_TotalConsume = new JLabel("");
		Label_CusName.setFont(new Font("Dialog", Font.BOLD, 26));
		label_TotalConsume.setFont(new Font("Dialog", Font.BOLD, 26));
		Label_CusName.setHorizontalAlignment(SwingConstants.CENTER);
		label_TotalConsume.setHorizontalAlignment(SwingConstants.CENTER);


		comboBox = new JComboBox<>(currentShoppingDetail.keySet().toArray(String[]::new));
		comboBox.addActionListener(e-> {
			if (comboBox.getSelectedObjects().length != 0 ) {
				if(comboBox.getSelectedIndex() != 0) {
					showDetailsByDate(comboBox.getSelectedItem().toString());
				}else {
					TableSettings.show(table_details,Const.COLUMNS_FOR_SHOPPINGDETAILS_WITH_DATE,getAllDateDetails(currentShoppingDetail),Const.SHOP_DETAIL_WITH_DATE_COLUMN_SIZE);
					TableSettings.scrollToBottom(table_details, table_details.getRowCount()-1);
				}
			}
		});
		comboBox.addMouseListener(new ShoppingDateComboBoxListener(comboBox));
		comboBox.setFont(new Font("Dialog", Font.BOLD, 16));
		comboBox.setRenderer(Const.DLCR);
		if (comboBox.getSelectedObjects().length != 0) {
			comboBox.setSelectedIndex(currentShoppingDetail.keySet().toArray().length - 1);
		}

		table_details = TableSettings.getInfoTable();
		table_details.addMouseListener(new ShoppingDetailTableActionListener(table_details));

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table_details);

		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				if (event.getID() == MouseEvent.MOUSE_CLICKED) {
					MouseEvent mevent = (MouseEvent) event;
					int row = table_details.rowAtPoint(mevent.getPoint());
					if (row == -1) {
						table_details.clearSelection();
					}
				}
			}
		}, AWTEvent.MOUSE_EVENT_MASK);
	}

	public String[][] getAllDateDetails(Map<String,List<List<String>>> map){

		return map.entrySet().stream().flatMap(en->en.getValue().stream().map(va->{
			if(en.getValue().indexOf(va)==0) {
				return detailWtihDate(en.getKey(), va);
			}
			return detailWtihDate("", va);
		})).toArray(String[][]::new);

	}

	public String[] detailWtihDate(String date,List<String> list) {
		String[] copy = new String[8];
		for(int i = 1;i<8;i++) {
			copy[i] = list.get(i-1);
		}
		copy[0] = date;
		return copy;
	}

	public void setTotalConsume() {
		label_TotalConsume.setText("累積消費：$"+currentShoppingDetail.entrySet().stream().flatMap(entry->entry.getValue().stream()).mapToInt(e->getSaleToInt(e.get(5).toString())).sum()+"元");
	}

	public Integer getSaleToInt(String sale) {
		if(sale.split("\\.").length==2) {
			String[] decimal = sale.split("\\.");
			if(Functions.isNumberic(decimal[0],true,false) && Functions.isNumberic(decimal[1],true,false)) {
				return Math.round(Float.parseFloat(sale));
			}
		}
		return Functions.isNumberic(sale,true,false) ? Integer.parseInt(sale) : 0 ;
	}

	public void buttonEnable() {
		if (DataFile.searchKey == null || DataFile.searchKey.size() == 1 || DataFile.searchKey.size() == 0) {
			btn_previous.setEnabled(false);
			btn_next.setEnabled(false);
		} else if (DataFile.searchKey.indexOf(key) == 0) {
			btn_previous.setEnabled(false);
			btn_next.setEnabled(true);
		} else if (DataFile.searchKey.indexOf(key) == DataFile.searchKey.size() - 1) {
			btn_previous.setEnabled(true);
			btn_next.setEnabled(false);
		} else {
			btn_previous.setEnabled(true);
			btn_next.setEnabled(true);
		}
	}

	public void setComboBoxModel(Set<String> model) {
		if(model.size()==0) {
			comboBox.setModel(emptyModel);
			return;
		}
		List<String> list = model.stream().collect(Collectors.toList());
		list.add(0,"全部");
		comboBox.setModel(new DefaultComboBoxModel<>(list.toArray(String[]::new)));
	}

	public void setCurrentShoppingDetail(Map<String, List<List<String>>> currentShoppingDetail) {
		this.currentShoppingDetail = currentShoppingDetail;
	}

	public JTable getTable_details() {
		return table_details;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public String getSelectedDate() {
		return comboBox.getSelectedItem().toString();
	}

	public void setCusName(String name) {
		Label_CusName.setText("《"+name+"》");
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

	public void showDetailsByDate(String date) {
		TableSettings.show(table_details, Const.COLUMNS_FOR_SHOPPINGDETAILS, currentShoppingDetail.get(date).stream().map(e->e.toArray()).toArray(Object[][]::new),
				Const.SHOP_DETAIL_COLUMN_SIZE);
		TableSettings.scrollToBottom(table_details,table_details.getRowCount()-1);
	}

	public void clearDetails() {
		setKey(null);
		Label_CusName.setText(NO_CUSTOMER);
		label_TotalConsume.setText("");
		comboBox.setModel(emptyModel);
		btn_next.setEnabled(false);
		btn_previous.setEnabled(false);
		((DefaultTableModel)table_details.getModel()).setNumRows(0);
		((DefaultTableModel)table_details.getModel()).setColumnCount(0);
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(162)
					.addComponent(comboBox, 151, 192, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(Label_CusName, 350, 391, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btn_previous, 82, 123, Short.MAX_VALUE)
					.addGap(12)
					.addComponent(btn_next, 82, 123, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_TotalConsume, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(Label_CusName)
						.addComponent(btn_previous, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_next, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_TotalConsume, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
