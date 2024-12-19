package org.roy.inventory.viewFunction;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.roy.bean.Inventory;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;

public class InputOutputOD_BAG extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField id;
	private JTextField amount;
	private String key;
	private JButton btn_confirm;
	private JComboBox<String> comboBox;
	private JTextArea note;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_1_2;
	private JLabel lblNewLabel_1_1;
	private JButton btn_cancel;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public InputOutputOD_BAG(String key,JMenuItem menuInput,JMenuItem menuOutput) {
		this.key = key;
		Functions.defaultJFrameSettings(this,3.5,2);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				menuInput.setEnabled(true);
				menuOutput.setEnabled(true);
			}

			@Override
			public void windowActivated(WindowEvent e) {
				menuInput.setEnabled(false);
				menuOutput.setEnabled(false);
			}

		});

		initComponents();
		initLayout();
	}

	private void initComponents() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		lblNewLabel = new JLabel("貨號：");
		lblNewLabel_1 = new JLabel("數量：");
		lblNewLabel_1_2 = new JLabel("經手人：");
		lblNewLabel_1_1 = new JLabel("備註：");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.TRAILING);

		btn_confirm = new JButton("入庫");
		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_confirm.addActionListener(e->{
			if(confirmInput(amount.getText())) {
				inputOrOutput();
				dispose();
			}
		});

		btn_cancel = new JButton("取消");
		btn_cancel.addActionListener(e->dispose());
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		id = new JTextField();
		amount = new JTextField();
		id.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		amount.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		id.setColumns(10);
		amount.setColumns(10);
		id.setEnabled(false);
		id.setText(key);

		note = new JTextArea();
		note.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		note.setLineWrap(true);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(note);

		comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		comboBox.setModel(new DefaultComboBoxModel<>(DataFile.inputOutputPerson.toArray(String[]::new)));
		comboBox.setRenderer(Const.DLCR);
	}

	public void inputOrOutput() {
		Inventory info = DataFile.inventory.get(key);
		int amo = Integer.parseInt(amount.getText());
		List<String> event = new ArrayList<>(Arrays.asList("庫存",key,btn_confirm.getText(),comboBox.getSelectedItem().toString(),note.getText()));
		switch (btn_confirm.getText()) {
		case "入庫":
			info.setAmount(info.getAmount()+amo);
			info.setInventoryValue();
			break;
		case "出庫":
			info.setAmount(info.getAmount()-amo);
			info.setInventoryValue();
			break;
		}
		event.add(3,"品名："+info.getName()+"\n件數："+amo);
		GlamChamber.inventoryData.flushTable(key);
//		TableSettings.show(InventoryData.table_inventoryData,Const.COLUMNS_FOR_OD_AND_BAG, new Object[][] {DataFile.inventory.get(key).getODBAGInfo()}, Const.OD_BAG_COLUMN_SIZE);
		HistoryRecords.addRecords(event);
	}

	public void setBtn_ConfirmText() {
		btn_confirm.setText("出庫");
	}

	public boolean confirmInput(String inputNum) {

		if(Functions.isNumberic(inputNum,false,false)) {
			if(btn_confirm.getText().equals("出庫")) {
				if(DataFile.inventory.get(key).getAmount()-Integer.parseInt(inputNum)<0) {
					JOptionPane.showMessageDialog(this,"出庫數量大於庫存數量",null,JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}else {
			JOptionPane.showMessageDialog(this,"數量錯誤",null,JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void initLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(71)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(id, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(127))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(71)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(amount, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(127))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(71)
					.addComponent(lblNewLabel_1_2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(comboBox, 0, 130, Short.MAX_VALUE)
					.addGap(127))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(86)
					.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(127))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(59)
					.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(59)
					.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(59))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(id))
					.addGap(55)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(amount))
					.addGap(48)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)))
					.addGap(40)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(41)
							.addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(39))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(19))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
