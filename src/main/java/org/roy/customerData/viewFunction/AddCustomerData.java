package org.roy.customerData.viewFunction;

import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;

import org.roy.bean.Customer;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.dateChooser.CustomDateChooser;
import org.roy.settings.dateChooser.CustomJCalendar;
import org.roy.settings.listener.extend.InputConfirmListener;

import com.toedter.calendar.JTextFieldDateEditor;

public class AddCustomerData extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected JTextField cus_id;
	protected JTextField cus_name;
	protected CustomDateChooser cus_birthdate;
	protected JTextField cus_phone;
	protected JTextField cus_facebook;
	protected JTextField cus_lineOrWechat;
	protected JTextField cus_email;
	protected JTextArea cus_address;
	protected JRadioButton rdbtnMale;
	protected JRadioButton rdbtnFemale;
	protected JCheckBox checkBoxUnknown;
	protected JButton btn_confirm;
	protected ButtonGroup buttonGroup = new ButtonGroup();
	protected int newGender;
	private JButton btn_cancel;

	protected final String defaultYear = "1972年";
	protected final String removeYearRegex = "(\\d*年)";
	private final String cusIdRegex = "(\\d*年)|月|日";

	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_1_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblFacebook;
	private JLabel lblLinewechat;
	private JLabel lblFacebook_2;
	private JLabel lblFacebook_2_1;
	private JScrollPane scrollPane;
	protected JTextFieldDateEditor birthDateEditor;



	/**
	 * Create the frame.
	 */
	public AddCustomerData() {
		Functions.defaultJFrameSettings(this,2.5,1.3);
		initComponents();
		initLayout();
	}



	private void initComponents() {
		contentPane = new JPanel();
		setContentPane(contentPane);

		cus_id = new JTextField();
		cus_id.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		cus_id.setColumns(10);
		cus_id.setEnabled(false);

		lblNewLabel = new JLabel("顧客編號：");
		lblNewLabel_1 = new JLabel("顧客姓名：");
		lblNewLabel_1_1 = new JLabel("性別：");
		lblNewLabel_2 = new JLabel("生日：");
		lblNewLabel_3 = new JLabel("手機：");
		lblFacebook = new JLabel("Facebook：");
		lblLinewechat = new JLabel("Line/Wechat：");
		lblFacebook_2 = new JLabel("地址：");
		lblFacebook_2_1 = new JLabel("email：");

		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblFacebook.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblLinewechat.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblFacebook_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblFacebook_2_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFacebook.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLinewechat.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFacebook_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFacebook_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);

		cus_name = new JTextField();
		cus_phone = new JTextField();
		cus_facebook = new JTextField();
		cus_lineOrWechat = new JTextField();
		cus_email = new JTextField();

		cus_name.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		cus_phone.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		cus_facebook.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		cus_lineOrWechat.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		cus_email.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		cus_name.setColumns(10);
		cus_phone.setColumns(10);
		cus_facebook.setColumns(10);
		cus_lineOrWechat.setColumns(10);
		cus_email.setColumns(10);

		cus_address = new JTextArea();
		cus_address.setLineWrap(true);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(cus_address);


		CustomJCalendar customJCalendar = new CustomJCalendar(Calendar.getInstance().getTime(),true,true,null);
		cus_birthdate = new CustomDateChooser(customJCalendar,Calendar.getInstance().getTime(),"yyyy年MM月dd日",null);
		birthDateEditor = (JTextFieldDateEditor)cus_birthdate.getDateEditor();


		btn_confirm = new JButton("確定");
		btn_cancel = new JButton("取消");
		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_confirm.addActionListener(e->{
			if(cus_name.getText().length()==0) {
				JOptionPane.showMessageDialog(null,"顧客姓名不可為空");
				return;
			}
			if(isRepeatedID()) {
				JOptionPane.showMessageDialog(null,"顧客編號已存在");
				return;
			}
			confirm();
			dispose();
		});
		btn_cancel.addActionListener(e->dispose());

		rdbtnMale = new JRadioButton("男");
		rdbtnFemale = new JRadioButton("女");
		checkBoxUnknown = new JCheckBox("不詳");

		rdbtnMale.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		rdbtnFemale.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		checkBoxUnknown.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		checkBoxUnknown.addActionListener(e->{
			if(checkBoxUnknown.isSelected()) {
				cus_birthdate.setEnabled(false);
				birthDateEditor.setText("");
			}else {
				cus_birthdate.getCalendarButton().setEnabled(true);
				cus_birthdate.setDate(Calendar.getInstance().getTime());
			}
		});
		buttonGroup.add(rdbtnMale);
		buttonGroup.add(rdbtnFemale);
		rdbtnFemale.setSelected(true);

		InputConfirmListener<JTextField> inputConfirmListener = new InputConfirmListener<>(cus_id) {

			@Override
			public void changedUpdate(DocumentEvent e) {
				if(!checkBoxUnknown.isSelected()) {
					cus_id.setText(birthDateEditor.getText().replaceAll(cusIdRegex,"")+" "+cus_name.getText());
				}else {
					cus_id.setText(cus_name.getText());
				}
			}
		};
		cus_name.getDocument().addDocumentListener(inputConfirmListener);
		birthDateEditor.getDocument().addDocumentListener(inputConfirmListener);
	}

	public boolean isRepeatedID() {
		return DataFile.customer.containsKey(cus_id.getText());
	}

	public String getGender() {
		if(rdbtnFemale.isSelected()) {
			return rdbtnFemale.getText();
		}else {
			return rdbtnMale.getText();
		}
	}

	public void confirm() {
		Customer customer = getInfo();
		DataFile.customer.put(customer.getId(), customer);
		GlamChamber.customer_data.setSearchText(customer.getId());
		GlamChamber.customer_data.searchClick();
		HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("顧客資料",customer.getId(),"新增","","","")));
	}

	public Customer getInfo(){
		return new Customer(cus_id.getText(),cus_name.getText(),checkBoxUnknown.isSelected()?"不詳":birthDateEditor.getText().replaceAll(removeYearRegex,""),
				cus_phone.getText(),cus_facebook.getText(),cus_lineOrWechat.getText(),
				cus_address.getText(),cus_email.getText(),getGender(), new TreeMap<>(), null, new ArrayList<>());
	}

	public void setInput(Customer customer) {
		cus_id.setText(customer.getId());
		cus_name.setText(customer.getName());
		cus_phone.setText(customer.getPhoneNumber());
		cus_facebook.setText(customer.getFacebook());
		cus_lineOrWechat.setText(customer.getLineOrWechat());
		cus_address.setText(customer.getAddress());
		cus_email.setText(customer.getEmail());
		if(customer.getBirthDate().equals("不詳")) {
			checkBoxUnknown.doClick();
			return;
		}else {
			try {
				if(customer.getGender().equals("女")) {
					rdbtnFemale.setSelected(true);
				}else {
					rdbtnMale.setSelected(true);
				}
				cus_birthdate.setDate(Const.CHINESE_YMD_SIMPLE_DATE_FORMAT.parse(defaultYear+customer.getBirthDate().replaceAll(removeYearRegex,"")));
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null,"日期格式無效："+customer.getBirthDate());
				e.printStackTrace();
				return;
			}
		}

	}

	private void initLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(91)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(cus_id)
					.addGap(160))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(91)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(cus_name)
					.addGap(160))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(91)
					.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(rdbtnFemale, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
					.addGap(60)
					.addComponent(rdbtnMale, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(160))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(91)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(208)
							.addComponent(cus_birthdate, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(141)
							.addComponent(checkBoxUnknown, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
							.addGap(114)))
					.addGap(160))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(91)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(cus_phone)
					.addGap(160))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(91)
					.addComponent(lblFacebook, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(cus_facebook)
					.addGap(160))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(91)
					.addComponent(lblLinewechat, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(cus_lineOrWechat)
					.addGap(160))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(114)
					.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(113)
					.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(115))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(91)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblFacebook_2, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(scrollPane))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(lblFacebook_2_1, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(cus_email)))
					.addGap(160))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(cus_id))
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(cus_name))
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(3))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(rdbtnFemale, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(rdbtnMale, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(cus_birthdate, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(checkBoxUnknown, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(cus_phone))
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFacebook, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(cus_facebook))
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLinewechat, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(cus_lineOrWechat))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(33)
							.addComponent(lblFacebook_2, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(27))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(19)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFacebook_2_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(cus_email))
					.addGap(46)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_confirm)
						.addComponent(btn_cancel))
					.addGap(22))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
