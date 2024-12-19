package org.roy.dailySaleSheet.viewFunction;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.roy.bean.Commodity;
import org.roy.bean.SaleInfo;
import org.roy.dailySaleSheet.view.DailySaleTable;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.TableSettings;

public class AddNewSale extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLayeredPane layeredPane;
	protected FirstStep firstStep;
	protected SecondStep secondStep;
	protected LastStep lastStep;
	protected JButton btn_confirm;
	protected JButton btn_previousStep;
	protected JButton btn_nextStep;
	private JButton btn_cancel;
	protected SaleInfo saleInfo = new SaleInfo();
	private CardLayout cardLayout;

	/**
	 * Create the frame.
	 */
	public AddNewSale() {
		Functions.defaultJFrameSettings(this, 2.4, 1.3);
		initComponents();
		stepEnable();
		initLayout();
	}

	private void initComponents() {
		contentPane = new JPanel();
		setContentPane(contentPane);

		cardLayout = new CardLayout();
		layeredPane = new JLayeredPane();
		layeredPane.setLayout(cardLayout);

		btn_previousStep = new JButton("上一步");
		btn_nextStep = new JButton("下一步");
		btn_confirm = new JButton("確定");
		btn_cancel = new JButton("取消");

		btn_previousStep.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_nextStep.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		btn_previousStep.addActionListener(this::previousStepAction);
		btn_nextStep.addActionListener(this::nextStepAction);
		btn_confirm.addActionListener(this::confirmAction);
		btn_cancel.addActionListener(e->dispose());

		firstStep = new FirstStep();
		secondStep = new SecondStep();
		lastStep = new LastStep();
		secondStep.setAddNewSale(this);
		lastStep.setAddNewSale(this);
		firstStep.setAddNewSale(this);
		secondStep.buttonEnable();

		layeredPane.add(firstStep);
		layeredPane.add(secondStep);
		layeredPane.add(lastStep);
	}

	private void confirmAction(ActionEvent e) {
		if (confirmLastStep()) {
			firstStep.setSaleInfo();
			saleInfo.setLastStepType(lastStep.getLastStepType());
			saleInfo.setLastStepValue(lastStep.getLastStepValue());
			saleInfo.setPaymentMethod(lastStep.getPaymentMethod());
			saleInfo.setSalePerson(lastStep.getSalePerson());
			saleInfo.setNote(lastStep.getNote());
			saleInfo.setConsume(lastStep.getTotalConsume());
			saleInfo.setRemain(lastStep.getShoppingMoneyRemain());

			showSheet(saleInfo.getName());
			GlamChamber.dataNotSaved();
			dispose();
		}
	}

	private void nextStepAction(ActionEvent e) {
		if (firstStep.isVisible() && (!confirmfirstStep())) {
			return;
		}

		if (secondStep.isVisible()) {
			secondStep.setCommodityInfo(secondStep.getTotalCommodityNum() - 1);
			if (confirmSecondStep(secondStep.getCommodityList())) {
				cardLayout.next(layeredPane);
				stepEnable();
			}
			return;
		}
		cardLayout.next(layeredPane);
		stepEnable();
	}

	private void previousStepAction(ActionEvent e) {
		if (secondStep.isVisible()) {
			secondStep.setCommodityInfo(secondStep.getCurrentCommodityNumber()-1);
			firstStep.getCommodityAmount().setText(String.valueOf(secondStep.getCommodityList().size()));
		}
		cardLayout.previous(layeredPane);
		stepEnable();
	}

	public void showSheet(String key) {
		DataFile.saleSheet.put(saleInfo.getName(), saleInfo);
		TableSettings.show(DailySaleTable.table_saleSheet, Const.COLUMNS_FOR_SALES_SHEET,
				DataFile.saleSheet.entrySet().stream().map(Map.Entry::getValue)
						.flatMap(s -> Arrays.stream(s.convertBeanToTableInfo())).toArray(Object[][]::new),
				Const.SALE_SHEET_COLUMN_SIZE);
	}

	public boolean confirmfirstStep() {
		if (firstStep.getCusName().length() == 0) {
			JOptionPane.showMessageDialog(null, "客戶姓名不可為空");
			return false;
		} else if (DataFile.saleSheet.keySet().contains(firstStep.getCusName())) {
			JOptionPane.showMessageDialog(null, "客戶姓名已存在");
			return false;
		}

		if (!Functions.isNumberic(firstStep.getCommodityAmount().getText(), false, false)) {
			JOptionPane.showMessageDialog(null, "請檢查商品數量");
			return false;
		}
		if (!Functions.isNumberic(firstStep.getDiscountValue().getText(), false, true)) {
			JOptionPane.showMessageDialog(null, "請檢查購物金");
			return false;
		}
		return true;
	}

	public boolean confirmSecondStep(List<Commodity> input) {
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).getId().length() == 0) {
				JOptionPane.showMessageDialog(null, "請確認第" + (i + 1) + "個商品資料", "錯誤", JOptionPane.WARNING_MESSAGE);
				return false;
			} else if (!(Functions.isNumberic(input.get(i).getAmount(), false, false)
					&& Functions.isNumberic(input.get(i).getPrice(), false, false)
					&& Functions.isNumberic(input.get(i).getDiscount(), false, true))) {
				JOptionPane.showMessageDialog(null, "請確認第" + (i + 1) + "個商品資料", "錯誤", JOptionPane.WARNING_MESSAGE);
				return false;
			} else if (!vaildFormat(input.get(i).getParticular())) {
				if (!Functions.isNumberic(input.get(i).getParticularValue(), false, false)) {
					JOptionPane.showMessageDialog(null, "請確認第" + (i + 1) + "個商品資料", "錯誤", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
		}
		return true;
	}

	public boolean confirmLastStep() {
		String type = lastStep.getLastStepType();
		if (type.equals("總額扣") || type.equals("去尾數")) {
			if (!Functions.isNumberic(lastStep.getLastStepValue(), false, false)) {
				JOptionPane.showMessageDialog(null, type + "：金額錯誤");
				return false;
			}
		}
		return true;
	}

	public boolean vaildFormat(String particular) {
		return particular.equals("贈送") || particular.equals("廣告") || particular.equals("儲值購物金")
				|| particular.equals("無");
	}

	public void stepEnable() {
		if (firstStep.isVisible()) {
			btn_previousStep.setEnabled(false);
			btn_nextStep.setEnabled(true);
			btn_confirm.setEnabled(false);
		} else if (secondStep.isVisible()) {
			btn_previousStep.setEnabled(true);
			btn_confirm.setEnabled(false);

			if (firstStep.getCommodityAmountToInt() != secondStep.getTotalCommodityNum()) {
				secondStep.setTotalCommodityNum(firstStep.getCommodityAmountToInt());
				secondStep.setTotalCommodityListDetailByAmount();
				secondStep.getSaleSheetCommodityFunctionListener().setCo(secondStep.getCommodityList());
				secondStep.setCommodityIndexComboBox();
				if (!secondStep.getCommodityList().contains(secondStep.getInputs())) {
					secondStep.setInput(secondStep.getCommodityList().get(secondStep.getCommodityList().size() - 1));
					secondStep.setcurrentCommodityNumber(secondStep.getCommodityList().size());
					secondStep.getCommodityIndexComboBox().setSelectedItem(secondStep.getCommodityList().size());
				} else {
					int indexOf = secondStep.getCommodityList().indexOf(secondStep.getInputs());
//					System.out.println(secondStep.getCommodityList());
					secondStep.getCommodityIndexComboBox().setSelectedIndex(indexOf);
					secondStep.setcurrentCommodityNumber(indexOf+1);
				}
			}
			secondStep.buttonEnable();
		} else if (lastStep.isVisible()) {
			btn_nextStep.setEnabled(false);
			btn_confirm.setEnabled(true);
			lastStep.setDiscountTypeModel();
			lastStep.setTotalConsume(secondStep.getTotalConsume());
			lastStep.setTotalDetail();
			lastStep.setLastSelectedType();
		}
	}

	public boolean isShoppingMoneyPay() {
		return firstStep.getDiscountValueType().equals("購物金付款") && (firstStep.getDiscountValueToInt() != 0);
	}

	private void initLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(50)
						.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE).addGap(50))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(24)
						.addComponent(btn_previousStep, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE).addGap(24)
						.addComponent(btn_nextStep, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE).addGap(24)
						.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE).addGap(24)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE).addGap(24)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(20)
				.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE).addGap(29)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_previousStep, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_nextStep, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_confirm, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_cancel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(15)));

		contentPane.setLayout(gl_contentPane);
	}
}
