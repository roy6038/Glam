package org.roy.settings.listener.extend;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.PopUp;
import org.roy.settings.dateChooser.CustomDateChooser;
import org.roy.settings.dateChooser.CustomJCalendar;

import com.toedter.calendar.JTextFieldDateEditor;

public class ShoppingDateComboBoxListener extends PopUp<Component> {

	private Date parsedDate;
	private Map<String, List<List<String>>> shoppingDetailData;

	public ShoppingDateComboBoxListener(Component t) {
		super(t);
		jm.removeAll();
		jm.add(menuUpdate);
		jm.addSeparator();
		jm.add(menuDelete);
		setpopupsize(jm,120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String oldDate = GlamChamber.shopping_details.getSelectedDate();
		parsedDate = null;
		shoppingDetailData = DataFile.customer.get(GlamChamber.shopping_details.getKey()).getShoppingDetailData();
		if (e.getSource().equals(menuUpdate)) {
			try {
				parsedDate = new SimpleDateFormat("yyyy/MM/dd").parse(oldDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			initEditWindow(oldDate);
		} else if (e.getSource().equals(menuDelete)) {
			int con = Functions.showConfirmDialog("確定刪除？","刪除購物日期");
			if (con == 1) {
				shoppingDetailData.remove(GlamChamber.shopping_details.getSelectedDate());
				GlamChamber.shopping_details.setComboBoxModel(shoppingDetailData.keySet());
				GlamChamber.shopping_details.getComboBox().setSelectedIndex(GlamChamber.shopping_details.getComboBox().getItemCount()-1);
				HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("購物明細",GlamChamber.shopping_details.getKey(),"刪除","購物明細日期："+oldDate+"。","","")));
				GlamChamber.shopping_details.repaint();
			}
		}
	}

	public void initEditWindow(String oldDate) {
		JFrame frame = new JFrame("更改日期");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(new Rectangle((int) (screensize.getWidth()/4),(int) (screensize.getHeight()/5)));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		CustomJCalendar customJCalendar = new CustomJCalendar(parsedDate,true,true,null);
		CustomDateChooser jDateChooser = new CustomDateChooser(customJCalendar,parsedDate,"yyyy/MM/dd",null);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(null);
		frame.setContentPane(contentPane);

		JButton btn_confirm = new JButton("確定");
		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_confirm.addActionListener(e->{
			String newDate = ((JTextFieldDateEditor)jDateChooser.getDateEditor()).getText();
			if (newDate != null) {
				shoppingDetailData.put(newDate,shoppingDetailData.get(GlamChamber.shopping_details.getSelectedDate()));
				shoppingDetailData.remove(GlamChamber.shopping_details.getSelectedDate());
				GlamChamber.shopping_details.setComboBoxModel(shoppingDetailData.keySet());
				GlamChamber.shopping_details.getComboBox().setSelectedItem(newDate);
				HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("購物明細",GlamChamber.shopping_details.getKey(),"更改","購物明細日期："+oldDate+"->"+newDate+"。","","")));
				GlamChamber.shopping_details.repaint();
			}
			frame.dispose();
		});


		JButton btn_cancel = new JButton("取消");
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.addActionListener(e->frame.dispose());

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(100)
					.addComponent(jDateChooser, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
					.addGap(100))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(70)
					.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
					.addGap(70)
					.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
					.addGap(70))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(47)
					.addComponent(jDateChooser, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(31)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)))
					.addGap(15))
		);
		contentPane.setLayout(gl_contentPane);
		frame.setVisible(true);
	}

}
