package org.roy.customerData.viewFunction;

import java.awt.Font;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.roy.homepage.application.GlamChamber;
import org.roy.settings.Functions;
import org.roy.settings.dateChooser.CustomDateChooser;
import org.roy.settings.dateChooser.CustomJCalendar;
import org.roy.settings.listener.extend.InputConfirmListener;

import com.toedter.calendar.JTextFieldDateEditor;

public class EnterDateAndAmount extends JFrame{

	private static final long serialVersionUID = -3355202570300240296L;


	public EnterDateAndAmount() {
		setTitle("請輸入日期和商品數量");
		Functions.defaultJFrameSettings(this,3,4);
//		setVisible(true);
//		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
//		setBounds(new Rectangle((int) (screensize.getWidth()/3),(int) (screensize.getHeight()/4)));
//		setLocationRelativeTo(null);
//		setResizable(false);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("日期：");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));

		CustomJCalendar customJCalendar = new CustomJCalendar(Calendar.getInstance().getTime(),true,true,null);
		CustomDateChooser shoppingDate = new CustomDateChooser(customJCalendar,Calendar.getInstance().getTime(),"yyyy/MM/dd",null);

		JLabel lblNewLabel_1 = new JLabel("商品數量：");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));

		JTextField amount = new JTextField();
		amount.setFont(new Font("Dialog", Font.BOLD, 16));
		amount.setColumns(10);
		amount.getDocument().addDocumentListener(new InputConfirmListener<>(amount));

		JButton btn_confirm = new JButton("確定");
		btn_confirm.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_confirm.addActionListener(e->{
			if(!Functions.isNumberic(amount.getText(), false, false)) {
				JOptionPane.showMessageDialog(null,"數量錯誤");
				return;
			}
			new AddShopDetails(GlamChamber.shopping_details.getKey(),((JTextFieldDateEditor)shoppingDate.getDateEditor()).getText(),Integer.parseInt(amount.getText()));
			dispose();
		});

		JButton btn_cancel = new JButton("取消");
		btn_cancel.setFont(new Font("Dialog", Font.BOLD, 16));
		btn_cancel.addActionListener(e->dispose());

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(90)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(shoppingDate, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(amount, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
							.addGap(100)
							.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
							.addGap(2)))
					.addGap(88))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(shoppingDate, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(amount))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(27))
		);
		contentPane.setLayout(gl_contentPane);

	}
}
