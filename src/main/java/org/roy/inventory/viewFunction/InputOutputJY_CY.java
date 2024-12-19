package org.roy.inventory.viewFunction;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

public class InputOutputJY_CY extends JFrame {

	private static final long serialVersionUID = 4888356834466272942L;
	private JPanel contentPane;
	private String key;
	private JButton btn_confirm;
	private JButton btn_cancel;
	private JComboBox<String> comboBoxAgent;
	private JTextArea note;
	private JScrollPane scrollPane;

	private JTextField sample;
	private JTextField XXS;
	private JTextField XS;
	private JTextField S;
	private JTextField M;
	private JTextField L;
	private JTextField XL;
	private JTextField XXL;
	private JTextField Flaw;
	private JTextField id;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_3_1;
	private JLabel lblNewLabel_1_1;
	private JLabel lblNewLabel_3_2;
	private JLabel lblNewLabel_3_1_1;
	private JLabel lblNewLabel_1_2;
	private JLabel lblNewLabel_3_3;
	private JLabel lblNewLabel_3_1_2;
	private JLabel lblNewLabel_3_3_1;
	private JLabel lblNewLabel_2;

	public InputOutputJY_CY(String key,JMenuItem menuInput,JMenuItem menuOutput) {
		this.key = key;
		Functions.defaultJFrameSettings(this, 3.4, 1.6);
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
		lblNewLabel_1 = new JLabel("樣衣：");
		lblNewLabel_3 = new JLabel("XXS：");
		lblNewLabel_3_1 = new JLabel("XS：");
		lblNewLabel_1_1 = new JLabel("S：");
		lblNewLabel_3_2 = new JLabel("M：");
		lblNewLabel_3_1_1 = new JLabel("L：");
		lblNewLabel_1_2 = new JLabel("XL：");
		lblNewLabel_3_3 = new JLabel("XXL：");
		lblNewLabel_3_1_2 = new JLabel("瑕疵品：");
		lblNewLabel_3_3_1 = new JLabel("經手人：");
		lblNewLabel_2 = new JLabel("備註：");

		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_1_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_3_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);

		id = new JTextField();
		sample = new JTextField();
		XXS = new JTextField();
		XS = new JTextField();
		S = new JTextField();
		M = new JTextField();
		L = new JTextField();
		XL = new JTextField();
		XXL = new JTextField();
		Flaw = new JTextField();

		id.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		sample.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		XXS.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		XS.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		S.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		M.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		L.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		XL.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		XXL.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		Flaw.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		id.setColumns(10);
		sample.setColumns(10);
		XXS.setColumns(10);
		XS.setColumns(10);
		S.setColumns(10);
		M.setColumns(10);
		L.setColumns(10);
		XL.setColumns(10);
		XXL.setColumns(10);
		Flaw.setColumns(10);
		id.setEnabled(false);
		id.setText(key);

		comboBoxAgent = new JComboBox<>();
		comboBoxAgent.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		comboBoxAgent.setModel(new DefaultComboBoxModel<>(DataFile.inputOutputPerson.toArray(String[]::new)));
		comboBoxAgent.setRenderer(Const.DLCR);

		btn_confirm = new JButton("入庫");
		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_confirm.addActionListener(e -> {
			List<JTextField> newSizeNum = Arrays.asList(sample, XXS, XS, S, M, L, XL, XXL, Flaw);
			List<Integer> oldSizeNum = DataFile.inventory.get(key).inputOutputForJYCY();
			if(confirmInput(newSizeNum,oldSizeNum)) {
				inputOrOutput(newSizeNum.stream().map(num -> convertTextFieldToInt(num)).collect(Collectors.toList()));
				dispose();
			}
		});

		btn_cancel = new JButton("取消");
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.addActionListener(e ->dispose());

		note = new JTextArea();
		note.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		note.setLineWrap(true);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(note);
	}

	public void inputOrOutput(List<Integer> inputSizeNum) {
		StringBuilder sb = new StringBuilder();
		List<String> event = new ArrayList<>(Arrays.asList("庫存",key,btn_confirm.getText(),comboBoxAgent.getSelectedItem().toString(),note.getText()));
		Inventory info = DataFile.inventory.get(key);
		switch (btn_confirm.getText()) {
		case "入庫":
			info.setSampleNum(info.getSampleNum()+inputSizeNum.get(0));
			info.setxXSNum(info.getxXSNum()+inputSizeNum.get(1));
			info.setxSNum(info.getxSNum()+inputSizeNum.get(2));
			info.setsNum(info.getsNum()+inputSizeNum.get(3));
			info.setmNum(info.getmNum()+inputSizeNum.get(4));
			info.setlNum(info.getlNum()+inputSizeNum.get(5));
			info.setxLNum(info.getxLNum()+inputSizeNum.get(6));
			info.setxXLNum(info.getxXLNum()+inputSizeNum.get(7));
			info.setFlaw(info.getFlaw()+inputSizeNum.get(8));
			info.setAmount();
			info.setInventoryValue();

			break;
		case "出庫":

			info.setSampleNum(info.getSampleNum()-inputSizeNum.get(0));
			info.setxXSNum(info.getxXSNum()-inputSizeNum.get(1));
			info.setxSNum(info.getxSNum()-inputSizeNum.get(2));
			info.setsNum(info.getsNum()-inputSizeNum.get(3));
			info.setmNum(info.getmNum()-inputSizeNum.get(4));
			info.setlNum(info.getlNum()-inputSizeNum.get(5));
			info.setxLNum(info.getxLNum()-inputSizeNum.get(6));
			info.setxXLNum(info.getxXLNum()-inputSizeNum.get(7));
			info.setFlaw(info.getFlaw()-inputSizeNum.get(8));
			info.setAmount();
			info.setInventoryValue();

			break;
		}
		GlamChamber.inventoryData.flushTable(key);
//		TableSettings.show(InventoryData.table_inventoryData,Const.COLUMNS_FOR_JY_AND_CY, new Object[][] {DataFile.inventory.get(key).getJYCYInfo()}, Const.JY_CY_COLUMN_SIZE);
		sb.append("品名：").append(info.getName()).append("\n");
		for(int i = 0;i<inputSizeNum.size();i++) {
			if(inputSizeNum.get(i)!=0) {
				sb.append(Const.COLUMNS_FOR_JY_AND_CY[i+4]).append("：").append(inputSizeNum.get(i)).append("件。 ");
			}
		}
		sb.append("\n總件數：").append(inputSizeNum.stream().reduce(0,Integer::sum));
		event.add(3,sb.toString());
		HistoryRecords.addRecords(event);
	}

	public boolean confirmInput(List<JTextField> newSizeNum,List<Integer> oldSizeNum) {

		int total = 0;

		switch (btn_confirm.getText()) {
		case "入庫":
			for(int i = 0;i<newSizeNum.size();i++) {
				if(!Functions.isNumberic(newSizeNum.get(i).getText(), true, true)) {
					JOptionPane.showMessageDialog(this,Const.COLUMNS_FOR_JY_AND_CY[i+4]+"：數量錯誤");
					return false;
				}else {
					total += convertTextFieldToInt(newSizeNum.get(i));
				}
			}
			if(total == 0) {
				JOptionPane.showMessageDialog(this,"總入庫量不可為零");
				return false;
			}
			break;
		case "出庫":
			for(int i = 0;i<newSizeNum.size();i++) {
				if(!Functions.isNumberic(newSizeNum.get(i).getText(), true, true)) {
					JOptionPane.showMessageDialog(this,Const.COLUMNS_FOR_JY_AND_CY[i+4]+"：數量錯誤");
					return false;
				}else if(convertTextFieldToInt(newSizeNum.get(i))>oldSizeNum.get(i)) {
					JOptionPane.showMessageDialog(this,Const.COLUMNS_FOR_JY_AND_CY[i+4]+"數量大於庫存數量");
					return false;
				}
				total += convertTextFieldToInt(newSizeNum.get(i));
			}
			if(total == 0) {
				JOptionPane.showMessageDialog(this,"總出庫量不可為零");
				return false;
			}
			break;

		}

		return true;
	}

	public void setBtn_ConfirmText() {
		btn_confirm.setText("出庫");
	}

	public int convertTextFieldToInt(JTextField j) {
		return j.getText().length() != 0 ? Integer.parseInt(j.getText()) : 0;
	}

	private void initLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(91)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(12).addComponent(id, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE).addGap(112))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(6)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(sample, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(12)
						.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(XXS, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(12)
						.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(XS, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(6))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(6)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(S, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(12)
						.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(M, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(12)
						.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(L, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(6))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(6)
						.addComponent(lblNewLabel_1_2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(XL, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(12)
						.addComponent(lblNewLabel_3_3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(XXL, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(12)
						.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(5).addComponent(Flaw, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE).addGap(6))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(91)
						.addComponent(lblNewLabel_3_3_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(12).addComponent(comboBoxAgent, 0, 116, Short.MAX_VALUE).addGap(112))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(91)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGap(12).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
						.addGap(112))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(59)
						.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE).addGap(59)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE).addGap(59)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(20)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(5)
										.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGap(5))
								.addComponent(id))
						.addGap(35)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(lblNewLabel_1))
								.addComponent(sample, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(6).addComponent(lblNewLabel_3))
								.addComponent(XXS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(
										gl_contentPane.createSequentialGroup().addGap(5).addComponent(lblNewLabel_3_1))
								.addComponent(
										XS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(44)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_contentPane.createSequentialGroup().addGap(5).addComponent(lblNewLabel_1_1))
								.addComponent(S, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(
										gl_contentPane.createSequentialGroup().addGap(6).addComponent(lblNewLabel_3_2))
								.addComponent(M, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(5)
										.addComponent(lblNewLabel_3_1_1))
								.addComponent(
										L, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(48)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_contentPane.createSequentialGroup().addGap(5).addComponent(lblNewLabel_1_2))
								.addComponent(XL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(
										gl_contentPane.createSequentialGroup().addGap(6).addComponent(lblNewLabel_3_3))
								.addComponent(XXL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(5)
										.addComponent(lblNewLabel_3_1_2))
								.addComponent(Flaw, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(43)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(3)
										.addComponent(lblNewLabel_3_3_1, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(7))
								.addComponent(comboBoxAgent, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGap(20)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup().addGap(42)
										.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGap(43))
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
						.addGap(19)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGap(1))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(1).addComponent(btn_cancel,
										GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGap(20)));
		contentPane.setLayout(gl_contentPane);
	}
}
