package org.roy.customerData.viewFunction;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;

public class AddShopDetails extends JFrame implements FocusListener{

	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	private JTextField id;
	private JTextField amount;
	private JTextField size;
	private JTextField price;
	private JTextField discount;
	private JTextField total;
	private JTextArea note;
	protected JButton btn_next;
	protected JButton btn_previous;
	protected JButton btn_confirm;
	protected JButton btn_cancel;
	private List<List<String>> commodity;
	protected int inputNum;
	private int currentNum;
	protected JComboBox<Integer> comboBox;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_1_1;
	private JLabel lblNewLabel_1_2;
	private JLabel lblNewLabel_1_3;
	private JLabel lblNewLabel_1_4;
	private JLabel lblNewLabel_1_5;
	private JScrollPane scrollPane;

	protected String key;
	protected String date;

	/**
	 * Create the frame.
	 */
	public AddShopDetails(String key,String date,int inputNum) {
		this.key = key;
		this.date = date;
		this.inputNum = inputNum;

		Functions.defaultJFrameSettings(this,2.5,1.3);
		setTitle("1");
		initComponents();
		buttonEnable();
		initLayout();
	}

	private void initComponents() {
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);

		currentNum = Integer.parseInt(getTitle());

		commodity = new ArrayList<>(inputNum);
		for(int i = 0;i<inputNum;i++) {
			commodity.add(Arrays.asList("","","","","","",""));
		}

		lblNewLabel = new JLabel("商品貨號：");
		lblNewLabel_1 = new JLabel("件數：");
		lblNewLabel_1_1 = new JLabel("尺寸：");
		lblNewLabel_1_2 = new JLabel("單價：");
		lblNewLabel_1_3 = new JLabel("折扣：");
		lblNewLabel_1_4 = new JLabel("銷售額：");
		lblNewLabel_1_5 = new JLabel("備註：");

		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1_2.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1_3.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1_4.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1_5.setFont(new Font("Dialog", Font.BOLD, 16));

		lblNewLabel_1_5.setHorizontalAlignment(SwingConstants.TRAILING);

		id = new JTextField();
		id.setFont(new Font("Dialog", Font.BOLD, 16));
		id.setColumns(10);


		amount = new JTextField();
		size = new JTextField();
		price = new JTextField();
		discount = new JTextField();
		total = new JTextField();

		amount.setFont(new Font("Dialog", Font.BOLD, 16));
		size.setFont(new Font("Dialog", Font.BOLD, 16));
		price.setFont(new Font("Dialog", Font.BOLD, 16));
		discount.setFont(new Font("Dialog", Font.BOLD, 16));
		total.setFont(new Font("Dialog", Font.BOLD, 16));

		amount.setHorizontalAlignment(SwingConstants.TRAILING);
		price.setHorizontalAlignment(SwingConstants.TRAILING);
		discount.setHorizontalAlignment(SwingConstants.TRAILING);
		total.setHorizontalAlignment(SwingConstants.TRAILING);

		amount.setColumns(10);
		size.setColumns(10);
		price.setColumns(10);
		discount.setColumns(10);
		total.setColumns(10);

		btn_previous = new JButton("上一個");
		btn_next = new JButton("下一個");
		btn_confirm = new JButton("確定");
		btn_cancel = new JButton("取消");

		btn_previous.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_next.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_confirm.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_cancel.setFont(new Font("Dialog", Font.BOLD, 16));

		btn_previous.addActionListener(this::previousAction);
		btn_next.addActionListener(this::nextAction);
		btn_confirm.addActionListener(this::confirm);
		btn_cancel.addActionListener(e->dispose());

		note = new JTextArea();
		note.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		note.setLineWrap(true);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(note);

		comboBox = new JComboBox<>(IntStream.rangeClosed(1, inputNum).boxed().toArray(Integer[]::new));
		comboBox.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		comboBox.setRenderer(Const.DLCR);
		comboBox.addFocusListener(this);
	}

	private void comboBoxAction(ActionEvent e) {
		int selectedNum = Integer.parseInt(comboBox.getSelectedItem().toString());
		commodity.set(currentNum-1, getInput());
		setInfo(commodity.get(selectedNum-1));
		currentNum = selectedNum;
		setTitleNum();
		buttonEnable();
	}

	private void nextAction(ActionEvent e) {
		commodity.set(currentNum-1, getInput());
		setInfo(commodity.get(currentNum));
		currentNum++;
		setTitleNum();
		comboBox.setSelectedIndex(Integer.parseInt(getTitle())-1);
		buttonEnable();
	}

	private void previousAction(ActionEvent e) {
		commodity.set(currentNum-1,getInput());
		setInfo(commodity.get(currentNum-2));
		currentNum--;
		setTitleNum();
		comboBox.setSelectedIndex(Integer.parseInt(getTitle())-1);
		buttonEnable();
	}

	protected void confirm(ActionEvent e) {
		commodity.set(inputNum-1, getInput());
		Map<String, List<List<String>>> shoppingDetailData = DataFile.customer.get(key).getShoppingDetailData();

		if(shoppingDetailData != null) {
			if(shoppingDetailData.containsKey(date)) {
				shoppingDetailData.get(date).addAll(commodity);
			}else {
				shoppingDetailData.put(date,commodity);
			}
		}else {
			DataFile.customer.get(key).setShoppingDetailData(new TreeMap<>(Map.of(date,commodity)));
		}
		StringBuilder sb = new StringBuilder();
		commodity.forEach(com->sb.append(com.get(0)+","));
		sb.deleteCharAt(sb.length()-1);
		HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("購物明細",key,"新增","購物明細日期："+date+"\n"+"貨號："+sb.toString(),"","")));
//		GlamChamber.shopping_details.changeShoppingDetails(DataFile.shoppingDetailData.get(key));
		GlamChamber.shopping_details.setComboBoxModel(shoppingDetailData.keySet());
		GlamChamber.shopping_details.comboBox.setSelectedIndex(GlamChamber.shopping_details.comboBox.getSelectedIndex());
		GlamChamber.shopping_details.setTotalConsume();
		dispose();
	}

	public List<String> getInput() {
		return Arrays.asList(id.getText(),amount.getText(),size.getText(),
				price.getText(),discount.getText(),total.getText(),note.getText());
	}

	public void setTitleNum() {
		setTitle(String.valueOf(currentNum));
	}

	public void cleanText() {
		id.setText("");
		amount.setText("");
		size.setText("");
		price.setText("");
		discount.setText("");
		total.setText("");
		note.setText("");
	}

	public void setInfo(List<String> list) {
		id.setText(list.get(0));
		amount.setText(list.get(1));
		size.setText(list.get(2));
		price.setText(list.get(3));
		discount.setText(list.get(4));
		total.setText(list.get(5));
		note.setText(list.get(6));
	}

	public void buttonEnable() {
		if(inputNum==1) {
			btn_previous.setEnabled(false);
			btn_next.setEnabled(false);
			btn_confirm.setEnabled(true);
			return;
		}


		if(currentNum==inputNum) {
			btn_next.setEnabled(false);
			btn_previous.setEnabled(true);
			btn_confirm.setEnabled(true);
		}else if(currentNum==1) {
			btn_next.setEnabled(true);
			btn_previous.setEnabled(false);
			btn_confirm.setEnabled(false);
		}else {
			btn_next.setEnabled(true);
			btn_previous.setEnabled(true);
			btn_confirm.setEnabled(false);
		}

	}

	@Override
	public void focusGained(FocusEvent e) {
		comboBox.addActionListener(this::comboBoxAction);
	}

	@Override
	public void focusLost(FocusEvent e) {
		comboBox.removeActionListener(this::comboBoxAction);
	}

	private void initLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(67)
					.addComponent(btn_previous, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
					.addGap(67)
					.addComponent(comboBox, 0, 119, Short.MAX_VALUE)
					.addGap(67)
					.addComponent(btn_next, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
					.addGap(70))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(125)
					.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
					.addGap(125)
					.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
					.addGap(126))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(162)
							.addComponent(lblNewLabel_1_3)
							.addGap(4)
							.addComponent(discount, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(146)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_1_5, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(scrollPane))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_1_4)
									.addGap(4)
									.addComponent(total, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(162)
							.addComponent(lblNewLabel_1_2)
							.addGap(4)
							.addComponent(price))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(162)
							.addComponent(lblNewLabel_1_1)
							.addGap(4)
							.addComponent(size))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(162)
							.addComponent(lblNewLabel_1)
							.addGap(4)
							.addComponent(amount))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addGap(130)
							.addComponent(lblNewLabel)
							.addGap(4)
							.addComponent(id, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)))
					.addGap(175))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_previous, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(4)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_next, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(1)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(51)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(38))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(46)
							.addComponent(id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(amount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(38)
							.addComponent(lblNewLabel_1_1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(33)
							.addComponent(size, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(38)
							.addComponent(lblNewLabel_1_2))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(33)
							.addComponent(price, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(37)
							.addComponent(lblNewLabel_1_3))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(32)
							.addComponent(discount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(38)
							.addComponent(lblNewLabel_1_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(33)
							.addComponent(total, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(32)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(72)
							.addComponent(lblNewLabel_1_5)))
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(22))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
