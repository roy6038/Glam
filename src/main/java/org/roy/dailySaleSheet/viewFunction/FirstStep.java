package org.roy.dailySaleSheet.viewFunction;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.roy.bean.SaleInfo;
import org.roy.settings.listener.extend.InputConfirmListener;

public class FirstStep extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField name;
	private JTextField commodityAmount;
	private JTextField discountValue;

	private JCheckBox isfriend;
	private JComboBox<String> discountValueType;
	private AddNewSale addNewSale;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Create the panel.
	 */
	public FirstStep() {
		setBounds(0,0,500,570);

		initComponents();
		initLayout();
	}

	private void initComponents() {
		lblNewLabel = new JLabel("客戶姓名：");
		lblNewLabel_1 = new JLabel("商品數量：");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);

		isfriend = new JCheckBox("Tina友人");
		isfriend.setHorizontalAlignment(SwingConstants.CENTER);
		isfriend.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		name = new JTextField();
		commodityAmount = new JTextField();
		discountValue = new JTextField();
		name.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		commodityAmount.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		discountValue.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		name.setColumns(10);
		commodityAmount.setColumns(10);
		discountValue.setColumns(10);

		commodityAmount.getDocument().addDocumentListener(new InputConfirmListener<>(commodityAmount));

		discountValueType = new JComboBox<>();
		discountValueType.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		discountValueType.setModel(new DefaultComboBoxModel<>(new String[] {"折抵購物金", "購物金付款"}));
	}

	public void setSaleInfo() {
		addNewSale.saleInfo.setName(name.getText());
		addNewSale.saleInfo.setFriend(isfriend.isSelected());
		addNewSale.saleInfo.setDiscountValueType(getDiscountValueType());
		addNewSale.saleInfo.setDiscountValue(discountValue.getText());
		addNewSale.saleInfo.setCommodities(addNewSale.secondStep.getCommodityList());
	}

	public void setUpdateInfo(SaleInfo s) {
		name.setText(s.getName());
		commodityAmount.setText(String.valueOf(s.getCommodities().size()));
		discountValueType.setSelectedItem(s.getDiscountValueType());
		discountValue.setText(s.getDiscountValue());
		isfriend.setSelected(s.isFriend());
	}

	public String getDiscountValueType() {
		return discountValueType.getSelectedItem().toString();
	}

	public JTextField getCommodityAmount() {
		return commodityAmount;
	}

	public int getCommodityAmountToInt() {
		return Integer.parseInt(commodityAmount.getText());
	}

	public int getDiscountValueToInt() {
		return discountValue.getText().length()==0?0:Integer.parseInt(discountValue.getText());
	}

	public String getCusName() {
		return name.getText();
	}

	public JTextField getDiscountValue() {
		return discountValue;
	}

	public void setAddNewSale(AddNewSale addNewSale) {
		this.addNewSale = addNewSale;
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(112)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(name)
					.addGap(136))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(112)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(commodityAmount)
					.addGap(136))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(92)
					.addComponent(discountValueType, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(discountValue, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(136))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(186)
					.addComponent(isfriend, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
					.addGap(186))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(name, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(120)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(commodityAmount, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(109)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(discountValueType)
							.addGap(1))
						.addComponent(discountValue, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(84)
					.addComponent(isfriend, GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
					.addGap(89))
		);
		setLayout(groupLayout);
	}
}
