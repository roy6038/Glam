package org.roy.pettyCash.viewFunction;

import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.roy.bean.PettyCash;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.dateChooser.CustomDateChooser;
import org.roy.settings.dateChooser.CustomJCalendar;
import org.roy.settings.listener.extend.PettyCashIncomeExpenseListener;

import com.toedter.calendar.JTextFieldDateEditor;


public class AddCashDetail extends JFrame {

	private static final long serialVersionUID = 4071813075163261456L;
	private JPanel contentPane;
	protected JTextField incomeOrExpense;
	private JTextField remain;
	private JTextArea detail;
	private CustomDateChooser jDateChooser;
	protected JComboBox<String> comboBox_incomeOrExpense;
	protected JComboBox<String> comboBox_person;
	private JCheckBox checkBox_invoice;
	private JCheckBox checkBox_receipt;
	private JTextArea note;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	protected JButton btn_confirm;
	private JButton btn_cancel;
	protected PettyCashIncomeExpenseListener listener;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_1_1;
	private JLabel lblNewLabel_1_1_1;
	private JLabel lblNewLabel_1_1_1_1;
	private JScrollPane detailScrollPane;
	private JScrollPane noteScrollPane;

	/**
	 * Create the frame.
	 */
	public AddCashDetail() {
		Functions.defaultJFrameSettings(this,2.4,1.3);
		setTitle("新增零用金明細");
		initComponents();
		initLayout();

	}

	private void initComponents() {
		contentPane = new JPanel();
		setContentPane(contentPane);

		CustomJCalendar customJCalendar = new CustomJCalendar(Calendar.getInstance().getTime(),true,true,null);
		jDateChooser = new CustomDateChooser(customJCalendar,Calendar.getInstance().getTime(),"yyyy/MM/dd",null);

		lblNewLabel = new JLabel("日期：");
		lblNewLabel_1 = new JLabel("明細：");
		lblNewLabel_1_1 = new JLabel("餘額：");
		lblNewLabel_1_1_1 = new JLabel("經手人：");
		lblNewLabel_1_1_1_1 = new JLabel("備註：");

		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_1_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.TRAILING);

		comboBox_person = new JComboBox<>(DataFile.pettyCashPerson.toArray(String[]::new));
		comboBox_incomeOrExpense = new JComboBox<>(new String[] {"收入", "支出"});
		comboBox_person.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		comboBox_incomeOrExpense.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		incomeOrExpense = new JTextField();
		remain = new JTextField();
		incomeOrExpense.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		remain.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		incomeOrExpense.setColumns(10);
		remain.setColumns(10);
		remain.setEnabled(false);

		listener = new PettyCashIncomeExpenseListener(incomeOrExpense, remain, comboBox_incomeOrExpense);
		listener.setTempRemain(DataFile.pettyMoneyRemain);
		listener.changedUpdate(null);
		incomeOrExpense.getDocument().addDocumentListener(listener);

		checkBox_invoice = new JCheckBox("發票");
		checkBox_receipt = new JCheckBox("收據");
		checkBox_invoice.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		checkBox_receipt.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		checkBox_invoice.addActionListener(e->{
			if(checkBox_invoice.isSelected()) {
				checkBox_receipt.setSelected(false);
			}
		});
		checkBox_receipt.addActionListener(e->{
			if(checkBox_receipt.isSelected()) {
				checkBox_invoice.setSelected(false);
			}
		});

		detailScrollPane = new JScrollPane();
		noteScrollPane = new JScrollPane();

		detail = new JTextArea();
		note = new JTextArea();
		detail.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		note.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		detail.setLineWrap(true);
		note.setLineWrap(true);
		detailScrollPane.setViewportView(detail);
		noteScrollPane.setViewportView(note);


		btn_confirm = new JButton("確定");
		btn_cancel = new JButton("取消");
		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_confirm.addActionListener(e->{
			if(confirmInput()) {
				confirm();
			}
		});
		btn_cancel.addActionListener(e->dispose());
	}

	public boolean confirmInput() {
		boolean hasIncome = comboBox_incomeOrExpense.getSelectedIndex()==0 && incomeOrExpense.getText().length()!=0;
		boolean hasExpense = comboBox_incomeOrExpense.getSelectedIndex()==1 && incomeOrExpense.getText().length()!=0;
		if((!hasIncome) && (!hasExpense)) {
			JOptionPane.showMessageDialog(null,"請輸入收入或支出");
			return false;
		}else if(hasIncome && (!Functions.isNumberic(incomeOrExpense.getText(),false,false))){
			JOptionPane.showMessageDialog(null,"收入錯誤");
			return false;
		}else if(hasExpense && (!Functions.isNumberic(incomeOrExpense.getText(),false,false))) {
			JOptionPane.showMessageDialog(null,"支出錯誤");
			return false;
		}
		return true;
	}

	public void confirm() {
		PettyCash pc = getPettyCash();
		DataFile.pettyCashDetails.add(pc);
		DataFile.pettyMoneyRemain = pc.getRemain();
		listener.setTempRemain(DataFile.pettyMoneyRemain);
		listener.changedUpdate(null);
		GlamChamber.pettyCashDetail.showDetailsByMonth();
		HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("零用金","","新增",
				"日期："+pc.getDate()+"\n"+pc.getDetailType()+"："+incomeOrExpense.getText(),
				pc.getEditedPerson(),"")));
	}

	public PettyCash getPettyCash() {
		return new PettyCash(((JTextFieldDateEditor)jDateChooser.getDateEditor()).getText(),
				detail.getText(),comboBox_incomeOrExpense.getSelectedItem().toString(),incomeOrExpense.getText(),remain.getText(),
				comboBox_person.getSelectedItem().toString(),checkBox_invoice.isSelected(),checkBox_receipt.isSelected(),note.getText());
	}

	public void setInputs(PettyCash info) {
		try {
			jDateChooser.setDate(simpleDateFormat.parse(info.getDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		detail.setText(info.getDetail());
		incomeOrExpense.setText(info.getIncomeOrexpense().toString());
		remain.setText(info.getRemain().toString());
		comboBox_incomeOrExpense.setSelectedItem(info.getDetailType());
		comboBox_person.setSelectedItem(info.getEditedPerson());
		checkBox_invoice.setSelected(info.isHasInvoice());
		checkBox_receipt.setSelected(info.isHasReceipt());
		note.setText(info.getNote());
	}
	private void initLayout() {
		//layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(128)
										.addComponent(comboBox_incomeOrExpense, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
										.addGap(2)
										.addComponent(incomeOrExpense, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(136)
										.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(remain, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(136)
										.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(comboBox_person, 0, 200, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(224)
										.addComponent(checkBox_invoice, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
										.addGap(40)
										.addComponent(checkBox_receipt, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(136)
										.addComponent(lblNewLabel_1_1_1_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(noteScrollPane, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(151)
										.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
										.addGap(39)
										.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(136)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(detailScrollPane, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(jDateChooser, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))))
						.addGap(176))
				);
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(26)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(jDateChooser, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(41)
										.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
										.addGap(20))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(22)
										.addComponent(detailScrollPane, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(31)
										.addComponent(comboBox_incomeOrExpense, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(30)
										.addComponent(incomeOrExpense, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
						.addGap(48)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(remain, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGap(48)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1_1_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(comboBox_person, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGap(29)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(checkBox_invoice, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(checkBox_receipt, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
						.addGap(25)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(39)
										.addComponent(lblNewLabel_1_1_1_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
										.addGap(42))
								.addComponent(noteScrollPane, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
						.addGap(53)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(1))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGap(1)
										.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGap(23))
				);
		contentPane.setLayout(gl_contentPane);
	}
}
