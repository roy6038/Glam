package org.roy.dailySaleSheet.viewFunction;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.roy.bean.Commodity;
import org.roy.settings.Const;
import org.roy.settings.listener.extend.InputConfirmListener;
import org.roy.settings.listener.extend.SaleSheetCommodityFunctionListener;

public class SecondStep extends JPanel implements FocusListener{

	private static final long serialVersionUID = 1L;
	private JTextField id;
	private JTextField colorSize;
	private JTextField amount;
	private JTextField price;
	private JTextField particularValue;
	private JTextField discount;

	private JComboBox<String> particular;
	private JComboBox<Integer> commodityIndexComboBox;

	private JButton btn_next;
	private JButton btn_previous;

	private int totalCommodityNum = 0;
	private int currentCommodityNumber = 1;
	private int commodityTotalPrice;

	private List<Commodity> commodityList;

	private AddNewSale addNewSale;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblJoy;
	private JLabel lblNewLabel_4;
	private InputConfirmListener<JTextField> valueInputConfirmListener;
	private SaleSheetCommodityFunctionListener saleSheetCommodityFunctionListener;

	/**
	 * Create the panel.
	 */
	public SecondStep() {
		setBounds(0, 0, 500, 570);
		commodityList = new ArrayList<>();
		initComponents();
		initLayout();
	}

	private void initComponents() {
		btn_previous = new JButton("上一個");
		btn_next = new JButton("下一個");

		btn_previous.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_next.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		btn_previous.addActionListener(this::buttonPreviousAction);
		btn_next.addActionListener(this::buttonNextAction);

		lblNewLabel = new JLabel("貨號：");
		lblNewLabel_1 = new JLabel("顏色尺寸：");
		lblNewLabel_2 = new JLabel("件數：");
		lblNewLabel_3 = new JLabel("售價：");
		lblJoy = new JLabel("特殊情況：");
		lblNewLabel_4 = new JLabel("折數：");

		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblJoy.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 16));

		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblJoy.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.TRAILING);

		id = new JTextField();
		colorSize = new JTextField();
		amount = new JTextField();
		price = new JTextField();
		particularValue = new JTextField();
		discount = new JTextField();

		id.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		colorSize.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		amount.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		price.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		particularValue.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		discount.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		id.setColumns(10);
		colorSize.setColumns(10);
		amount.setColumns(10);
		price.setColumns(10);
		particularValue.setColumns(10);
		discount.setColumns(10);

		amount.getDocument().addDocumentListener(new InputConfirmListener<>(amount));
		price.getDocument().addDocumentListener(new InputConfirmListener<>(price));

		commodityIndexComboBox = new JComboBox<>();
		particular = new JComboBox<>();

		saleSheetCommodityFunctionListener = new SaleSheetCommodityFunctionListener(commodityIndexComboBox, commodityList,this);
		valueInputConfirmListener = new InputConfirmListener<>(particularValue);

		commodityIndexComboBox.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		particular.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		commodityIndexComboBox.addMouseListener(saleSheetCommodityFunctionListener);
		commodityIndexComboBox.addFocusListener(this);
		particular.addActionListener(this::particularAction);

		commodityIndexComboBox.setRenderer(Const.DLCR);
		particular.setRenderer(Const.DLCR);

		particular.setModel(new DefaultComboBoxModel<>(new String[] {"無", "贈送", "廣告", "換貨", "售價折扣", "儲值購物金"}));
		particular.setSelectedIndex(0);

	}

	private void buttonPreviousAction(ActionEvent e) {
		setCommodityInfo(currentCommodityNumber - 1);
		setInput(commodityList.get(currentCommodityNumber - 2));
		currentCommodityNumber -= 1;
		buttonEnable();
		commodityIndexComboBox.setSelectedItem(currentCommodityNumber);
	}

	private void buttonNextAction(ActionEvent e) {
		setCommodityInfo(currentCommodityNumber - 1);
		setInput(commodityList.get(currentCommodityNumber));
		currentCommodityNumber += 1;
		buttonEnable();
		commodityIndexComboBox.setSelectedItem(currentCommodityNumber);
	}

	private void particularAction(ActionEvent e) {
		switch (particular.getSelectedItem().toString()) {
		case "無":
		case "贈送":
		case "廣告":
		case "儲值購物金":
			particularValue.setEnabled(false);
			particularValue.setText("");
			particularValue.getDocument().removeDocumentListener(valueInputConfirmListener);
			valueInputConfirmListener.setOutlineNormal();
			break;
		case "售價折扣":
		case "換貨":
			particularValue.setEnabled(true);
			particularValue.getDocument().addDocumentListener(valueInputConfirmListener);
			valueInputConfirmListener.changedUpdate(null);
			break;
		}
	}

	private void commodityIndexComboBoxAction(ActionEvent e) {
		setCommodityInfo(currentCommodityNumber - 1);
		setInput(commodityList.get(commodityIndexComboBox.getSelectedIndex()));
		currentCommodityNumber = Integer.parseInt(commodityIndexComboBox.getSelectedItem().toString());
		buttonEnable();
	}

	@Override
	public void focusGained(FocusEvent e) {
		commodityIndexComboBox.addActionListener(this::commodityIndexComboBoxAction);
	}

	@Override
	public void focusLost(FocusEvent e) {
		commodityIndexComboBox.removeActionListener(this::commodityIndexComboBoxAction);
	}


	public void setCommodityInfo(int index) {
		Commodity c = commodityList.get(index);
		c.setId(id.getText());
		c.setColorSize(colorSize.getText());
		c.setAmount(amount.getText());
		c.setPrice(price.getText());
		c.setDiscount(discount.getText());
		c.setParticular(particular.getSelectedItem().toString());
		c.setParticularValue(particularValue.getText());
	}

	public void buttonEnable() {
		if (commodityList.size() == 1) {
			btn_next.setEnabled(false);
			btn_previous.setEnabled(false);
			addNewSale.btn_nextStep.setEnabled(true);
		} else if (currentCommodityNumber == 1) {
			btn_next.setEnabled(true);
			btn_previous.setEnabled(false);
			addNewSale.btn_nextStep.setEnabled(false);
		} else if (currentCommodityNumber == commodityList.size()) {
			btn_next.setEnabled(false);
			btn_previous.setEnabled(true);
			addNewSale.btn_nextStep.setEnabled(true);
		} else {
			btn_previous.setEnabled(true);
			btn_next.setEnabled(true);
			addNewSale.btn_nextStep.setEnabled(false);
		}
	}

	public void setTotalCommodityListDetailByAmount() {
		if (commodityList.size() < totalCommodityNum) {
			for (int i = commodityList.size(); i < totalCommodityNum; i++) {
				commodityList.add(new Commodity());
			}
		} else if (commodityList.size() > totalCommodityNum) {
			for (int i = commodityList.size() - 1; i > totalCommodityNum - 1; i--) {
				commodityList.remove(i);
			}
		}
	}

	public void setCommodityIndexComboBox() {
		commodityIndexComboBox.setModel(
				new DefaultComboBoxModel<>(IntStream.rangeClosed(1, totalCommodityNum).boxed().toArray(Integer[]::new)));
	}

	public void setInput(Commodity c) {
		id.setText(c.getId());
		colorSize.setText(c.getColorSize());
		amount.setText(c.getAmount());
		price.setText(c.getPrice());
		discount.setText(c.getDiscount());
		particular.setSelectedItem(c.getParticular());
		particularValue.setText(c.getParticularValue());
	}

	public Integer getTotalConsume() {

		int totalConsumeBefore = commodityList.stream().mapToInt(c->c.getFilterTotal()).sum();
		int postiveCommodityChange = commodityList.stream().filter(c->c.getNewMinusOld() >= 0).mapToInt(com->com.getNewMinusOld()).sum();
		int negativeCommodityChange = commodityList.stream().filter(c->c.getNewMinusOld() < 0).mapToInt(com->com.getNewMinusOld()).sum();
		totalConsumeBefore = totalConsumeBefore + postiveCommodityChange - Math.abs(negativeCommodityChange);
		commodityTotalPrice = totalConsumeBefore;
		int firstStepDiscountValue = addNewSale.firstStep.getDiscountValueToInt();
		if(firstStepDiscountValue != 0) {
			if(addNewSale.firstStep.getDiscountValueType().equals("購物金付款")) {
				int i = firstStepDiscountValue - totalConsumeBefore;
				addNewSale.lastStep.setShoppingMoneyRemain(i);
				return firstStepDiscountValue;
			}else {
				return totalConsumeBefore - firstStepDiscountValue;
			}
		}
		return commodityTotalPrice;
	}

	public Commodity getInputs() {
		return new Commodity(id.getText(),colorSize.getText(),amount.getText(),price.getText(),discount.getText(),
				particular.getSelectedItem().toString(),particularValue.getText());
	}

	public SaleSheetCommodityFunctionListener getSaleSheetCommodityFunctionListener() {
		return saleSheetCommodityFunctionListener;
	}

	public JComboBox<Integer> getCommodityIndexComboBox() {
		return commodityIndexComboBox;
	}

	public int getCommodityTotalPrice() {
		return commodityTotalPrice;
	}

	public void setAddNewSale(AddNewSale addNewSale) {
		this.addNewSale = addNewSale;
	}

	public void setTotalCommodityList(List<Commodity> commodityList) {
		this.commodityList = commodityList;
	}

	public List<Commodity> getCommodityList() {
		return commodityList;
	}

	public int getTotalCommodityNum() {
		return totalCommodityNum;
	}

	public void setTotalCommodityNum(int num) {
		totalCommodityNum = num;
	}

	public int getCurrentCommodityNumber() {
		return currentCommodityNumber;
	}

	public void setcurrentCommodityNumber(int currentCommodityNumber) {
		this.currentCommodityNumber = currentCommodityNumber;
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addComponent(btn_previous, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
					.addGap(40)
					.addComponent(commodityIndexComboBox, 0, 100, Short.MAX_VALUE)
					.addGap(40)
					.addComponent(btn_next, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
					.addGap(40))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(73)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(id, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(166))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(73)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(colorSize, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(166))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(73)
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(amount, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(166))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(73)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(price, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(166))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(73)
					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(discount, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addGap(166))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(50)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(238)
							.addComponent(particularValue, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(109)
							.addComponent(particular, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 147, Short.MAX_VALUE))
						.addComponent(lblJoy, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
					.addGap(64))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btn_previous, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(commodityIndexComboBox, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(btn_next, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
					.addGap(48)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(id))
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(colorSize)
							.addGap(1)))
					.addGap(45)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(amount))
					.addGap(45)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(price))
					.addGap(45)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(discount))
					.addGap(40)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(particularValue)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(particular)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblJoy, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
					.addGap(84))
		);
		setLayout(groupLayout);
	}


}
