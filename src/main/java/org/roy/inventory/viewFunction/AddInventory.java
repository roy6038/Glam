package org.roy.inventory.viewFunction;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.roy.bean.Inventory;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;

public class AddInventory extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	protected JTextField i_idNum;
	private JLayeredPane layeredPane;
	protected JComboBox<String> i_idVar;
	protected JButton btn_confirm;
	protected OD_BAG_inventory od_BAG_inventory;
	protected JY_CY_inventory jy_CY_inventory;
	private JButton btn_cancel;
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public AddInventory() {
		Functions.defaultJFrameSettings(this, 2.4, 1.3);
		initComponents();
		initLayout();
	}

	private void initComponents() {
		//components
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		od_BAG_inventory = new OD_BAG_inventory();
		jy_CY_inventory = new JY_CY_inventory();

		layeredPane = new JLayeredPane();
		layeredPane.setLayout(new CardLayout(0, 0));
		layeredPane.add(od_BAG_inventory);
		layeredPane.add(jy_CY_inventory);

		lblNewLabel = new JLabel("貨號：");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		i_idVar = new JComboBox<>();
		i_idVar.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		i_idVar.setModel(new DefaultComboBoxModel<>(new String[] {"OD", "CY", "JY", "BAG", "客訂"}));
		i_idVar.setRenderer(Const.DLCR);
		i_idVar.addActionListener(e->{
			switch (i_idVar.getSelectedItem().toString()) {
			case "JY":
			case "CY":
				changeType(jy_CY_inventory);
				break;
			case "OD":
			case "BAG":
			case "客訂":
				changeType(od_BAG_inventory);
				break;
			}
		});

		btn_confirm = new JButton("新增");
		btn_cancel = new JButton("取消");
		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.addActionListener(e->dispose());
		btn_confirm.addActionListener(e->{
			confirmDetails();
		});

		i_idNum = new JTextField();
		i_idNum.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		i_idNum.setColumns(10);
	}

	public void confirmDetails() {
			if(i_idNum.getText().length()==0) {
				JOptionPane.showMessageDialog(null,"貨號不可為空");
				return;
			}

			String id =i_idVar.getSelectedItem().toString()+i_idNum.getText();
			String name = null;

			if(DataFile.inventory.containsKey(id)) {
				JOptionPane.showMessageDialog(null,"貨號已存在");
				return;
			}
			switch (i_idVar.getSelectedItem().toString()) {
			case "JY":
			case "CY":
				if(jy_CY_inventory.confirm()) {
					Inventory in = jy_CY_inventory.getInventory();
					in.setId(id);
					in.setType(i_idVar.getSelectedItem().toString());
					name = in.getName();
					DataFile.inventory.put(id,in);
					jy_CY_inventory.cleanInput();
//					TableSettings.show(InventoryData.table_inventoryData,Const.COLUMNS_FOR_JY_AND_CY,
//							new Object[][] {DataFile.inventory.get(id).getJYCYInfo()}, Const.JY_CY_COLUMN_SIZE);

				}else {
					return;
				}
				break;
			case "OD":
			case "BAG":
			case "客訂":
				if(od_BAG_inventory.confirm()) {
					Inventory in = od_BAG_inventory.getInventory();
					in.setId(id);
					in.setType(i_idVar.getSelectedItem().toString());
					name = in.getName();
					DataFile.inventory.put(id,in);
					od_BAG_inventory.cleanInput();
//					TableSettings.show(InventoryData.table_inventoryData,Const.COLUMNS_FOR_OD_AND_BAG,
//							new Object[][] {DataFile.inventory.get(id).getODBAGInfo()}, Const.OD_BAG_COLUMN_SIZE);
				}else {
					return;
				}
				break;
			}
			GlamChamber.inventoryData.flushTable(id);
			i_idNum.setText("");
			HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("庫存",id,"新增","品名："+name,"","")));
	}


	public void changeType(Component c) {
		layeredPane.removeAll();
		layeredPane.add(c);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	public OD_BAG_inventory getOd_BAG_inventory() {
		return od_BAG_inventory;
	}

	public JY_CY_inventory getJy_CY_inventory() {
		return jy_CY_inventory;
	}

	public JTextField getI_idNum() {
		return i_idNum;
	}

	public JComboBox<String> getI_idVar() {
		return i_idVar;
	}

	private void initLayout() {
		//layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(153)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(70)
							.addComponent(i_idVar, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(6)
					.addComponent(i_idNum, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
					.addGap(140))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(48)
					.addComponent(layeredPane)
					.addGap(48))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(114)
					.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(114)
					.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(114))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(63)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(2))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(i_idVar, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(i_idNum)))
					.addGap(1)
					.addComponent(layeredPane)
					.addGap(42)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(24))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
