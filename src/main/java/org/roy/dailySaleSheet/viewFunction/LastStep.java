package org.roy.dailySaleSheet.viewFunction;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.roy.bean.SaleInfo;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.listener.extend.InputConfirmListener;

public class LastStep extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField lastStepValue;
	private JComboBox<String> lastStepType;
	private JComboBox<String> paymentMethod;
	private JComboBox<String> salePerson;
	private int totalConsume = 0;
	private int shoppingMoneyRemain = 0;
	private AddNewSale addNewSale;
	private InputConfirmListener<JTextField> lastStepValueInputConfirmListener;
	private DefaultComboBoxModel<String> shopMoneyPay;
	private DefaultComboBoxModel<String> normalPay;
	private JTextArea note;
	private JTextPane total;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_1_1;
	private JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	public LastStep() {
		setBounds(0,0,500,570);
		initComponents();
		initLayout();
	}

	private void initComponents() {
		lblNewLabel_1 = new JLabel("交易方式：");
		lblNewLabel_1_1 = new JLabel("銷售人員：");

		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.TRAILING);

		lastStepValue = new JTextField();
		lastStepValue.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lastStepValue.setColumns(10);
		lastStepValueInputConfirmListener = new InputConfirmListener<>(lastStepValue);

		paymentMethod = new JComboBox<>();
		paymentMethod.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		paymentMethod.setModel(new DefaultComboBoxModel<>(new String[] {"現金", "刷卡", "匯款", "官網"}));
		paymentMethod.setRenderer(Const.DLCR);

		shopMoneyPay = new DefaultComboBoxModel<>(new String[] {"無","加5%"});
		normalPay = new DefaultComboBoxModel<>(new String[] {"無", "總額扣", "去尾數","加5%"});

		salePerson = new JComboBox<>(DataFile.salePerson.toArray(String[]::new));
		lastStepType = new JComboBox<>();

		lastStepType.setModel(normalPay);

		salePerson.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lastStepType.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		lastStepType.addActionListener(this::lastStepTypeAction);
		salePerson.setRenderer(Const.DLCR);
		lastStepType.setRenderer(Const.DLCR);
		lastStepType.setSelectedIndex(0);

		total = new JTextPane();
//		total.setText("總額："+totalConsume+"元"+"\n實際銷售額：＄150000\n購物金餘額：＄30000");
		total.setOpaque(false);
		StyledDocument doc = total.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center,StyleConstants.ALIGN_CENTER);
		StyleConstants.setLineSpacing(center,1f);
		doc.setParagraphAttributes(0,doc.getLength(), center,false);

		note = new JTextArea();
		note.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		total.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		note.setLineWrap(true);


		scrollPane = new JScrollPane();
		scrollPane.setViewportView(note);
	}

	private void lastStepTypeAction(ActionEvent e) {
		lastStepValue.setEnabled(false);
		StringBuilder sb = new StringBuilder();
		switch (lastStepType.getSelectedItem().toString()) {
		case "總額扣":
		case "去尾數":
			lastStepValue.setEnabled(true);
			lastStepValue.getDocument().addDocumentListener(lastStepValueInputConfirmListener);
			lastStepValueInputConfirmListener.setOutlineError();
			lastStepValue.setText(lastStepValue.getText());
//			shopMoneyTotal.setText("");
			total.setText(sb.append("總額：＄").append(totalConsume).toString());
			break;
		case "無":
			lastStepValue.setEnabled(false);
			lastStepValue.setText("");
			lastStepValueInputConfirmListener.setOutlineNormal();
			lastStepValue.getDocument().removeDocumentListener(lastStepValueInputConfirmListener);
			if(addNewSale!=null) {
				if(addNewSale.firstStep.getDiscountValue().getText().length()!=0) {
					if(addNewSale.firstStep.getDiscountValueType().equals("購物金付款")) {
						total.setText(totalDetailString(sb, totalConsume, shoppingMoneyRemain < 0,false));
						return;
					}
				}
				total.setText(sb.append(totalConsume < 0 ? "補差額：＄" : "總額：＄").append(totalConsume).toString());
			}
			break;
		case "加5%":
			lastStepValue.setEnabled(false);
			lastStepValue.setText("");
			lastStepValueInputConfirmListener.setOutlineNormal();
			lastStepValue.getDocument().removeDocumentListener(lastStepValueInputConfirmListener);
			if(addNewSale!=null) {
				if(addNewSale.firstStep.getDiscountValue().getText().length()!=0) {
					if(addNewSale.firstStep.getDiscountValueType().equals("購物金付款")) {
						total.setText(totalDetailString(sb,Math.round(totalConsume*1.05f),shoppingMoneyRemain < 0,true));
						return;
					}
				}
				total.setText(sb.append(totalConsume < 0 ? "補差額：＄" : "總額：＄").append(Math.abs(Math.round(totalConsume*1.05))).append("（含5%稅金）").toString());
			}
			break;
		}
	}

	public String totalDetailString(StringBuilder sb,int totalConsume,boolean remainLessThanZero,boolean includeFivePersent) {
		return sb.append("總額：＄").append(totalConsume).append(includeFivePersent ? "（含5%稅金）" : "").append("\n")
				 .append("實際銷售額：＄").append(addNewSale.secondStep.getCommodityTotalPrice()).append("\n")
				 .append(remainLessThanZero ? "補差額：＄" : "購物金餘額：＄")
				 .append(remainLessThanZero ? shoppingMoneyRemain*-1 : shoppingMoneyRemain).toString();
	}

	public String getNote() {
		return note.getText();
	}

	public String getSalePerson() {
		return salePerson.getSelectedItem().toString();
	}

	public String getPaymentMethod() {
		return paymentMethod.getSelectedItem().toString();
	}

	public String getLastStepType() {
		return lastStepType.getSelectedItem().toString();
	}

	public void setShoppingMoneyRemain(int shoppingMoneyRemain) {
		this.shoppingMoneyRemain = shoppingMoneyRemain;
	}

	public void setTotalDetail() {
		lastStepType.setSelectedIndex(lastStepType.getSelectedIndex());
	}

	public void setTotalConsume(int totalConsume) {
		this.totalConsume = totalConsume;
	}

	public int getTotalConsume() {
		return totalConsume;
	}

	public int getShoppingMoneyRemain() {
		return shoppingMoneyRemain;
	}

	public void setUpdateInfo(SaleInfo s) {
		lastStepType.setSelectedItem(s.getLastStepType());
		lastStepValue.setText(s.getLastStepValue());
		paymentMethod.setSelectedItem(s.getPaymentMethod());
		salePerson.setSelectedItem(s.getSalePerson());
		note.setText(s.getNote());
	}

	public void setAddNewSale(AddNewSale addNewSale) {
		this.addNewSale = addNewSale;
	}

	public void setDiscountTypeModel() {
		if(addNewSale.isShoppingMoneyPay()) {
			lastStepType.setModel(shopMoneyPay);
		}else {
			lastStepType.setModel(normalPay);
		}
	}

	public String getLastStepValue() {
		return lastStepValue.getText();
	}

	public void setLastSelectedType() {
		lastStepType.setSelectedIndex(lastStepType.getSelectedIndex());
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(139)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(paymentMethod, 0, 100, Short.MAX_VALUE)
					.addGap(153))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(139)
					.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(salePerson, 0, 100, Short.MAX_VALUE)
					.addGap(154))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(109)
					.addComponent(lastStepType, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lastStepValue, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
					.addGap(129))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(total, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(59)
					.addComponent(total, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lastStepType, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lastStepValue, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
							.addGap(1)))
					.addGap(80)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(paymentMethod, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addGap(75)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(4))
						.addComponent(salePerson, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
					.addGap(21)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
}
