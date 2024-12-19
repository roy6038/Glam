package org.roy.historyRecords.viewFunction;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.roy.settings.DataFile;
import org.roy.settings.ExportAsExcel;
import org.roy.settings.Functions;
import org.roy.settings.dateChooser.CustomDateChooser;
import org.roy.settings.dateChooser.CustomJCalendar;

import com.toedter.calendar.JTextFieldDateEditor;

public class StartDateAndEndDate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CustomDateChooser startDate;
	private CustomDateChooser endDate;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btn_confirm;
	private JButton btn_cancel;

	/**
	 * Create the frame.
	 */
	public StartDateAndEndDate() {
		Functions.defaultJFrameSettings(this,3,3);
		initComponents();
		initLayout();
	}

	private void initComponents() {
		contentPane = new JPanel();
		setContentPane(contentPane);

		CustomJCalendar customJCalendarStart = new CustomJCalendar(Calendar.getInstance().getTime(),true,true,null);
		CustomJCalendar customJCalendarEnd = new CustomJCalendar(Calendar.getInstance().getTime(),true,true,null);
		startDate = new CustomDateChooser(customJCalendarStart,Calendar.getInstance().getTime(),"yyyy-MM-dd",null);
		endDate = new CustomDateChooser(customJCalendarEnd,Calendar.getInstance().getTime(),"yyyy-MM-dd",null);

		lblNewLabel = new JLabel("開始：");
		lblNewLabel_1 = new JLabel("結束：");

		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);

		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));


		btn_confirm = new JButton("確定");
		btn_cancel = new JButton("取消");

		btn_confirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btn_cancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		btn_confirm.addActionListener(this::addInputOutputCotainInDateList);
		btn_cancel.addActionListener(e->dispose());

	}

	private void addInputOutputCotainInDateList(ActionEvent e) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startLocalDate = LocalDate.parse(((JTextFieldDateEditor)startDate.getDateEditor()).getText(),format);
		LocalDate endLocalDate = LocalDate.parse(((JTextFieldDateEditor)endDate.getDateEditor()).getText(),format);
		try {
			List<String> range = startLocalDate.datesUntil(endLocalDate.plusDays(1)).map(d->d.toString()).collect(Collectors.toList());
			List<List<String>> list = DataFile.historyRecords
					.entrySet()
					.stream()
					.filter(entry->range.contains(entry.getKey()))
					.flatMap(a->a.getValue().stream())
					.filter(obj->obj.get(3).equals("出庫")||obj.get(3).equals("入庫"))
					.collect(Collectors.toList());
			if(list.size()==0) {
				JOptionPane.showMessageDialog(null,"此日期區間無出入庫資料");
				return;
			}
			ExportAsExcel exportAsExcel =  new ExportAsExcel("inputOutputDetailStencil.xlsx",list,list.size()%18==0?list.size()/18:list.size()/18+1,18);
			if(exportAsExcel.getTotal()==exportAsExcel.getCurrent()) {
				dispose();
			}
		} catch (IllegalArgumentException e2) {
			JOptionPane.showMessageDialog(null,"開始日期不可在結束日期之後");
			return;
		}
	}

	private void initLayout() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(85)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(startDate, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
					.addGap(102))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(85)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(endDate, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
					.addGap(102))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(82)
					.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(82)
					.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(82))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(55)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(startDate, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(66)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(endDate, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(42)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btn_confirm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(1)
							.addComponent(btn_cancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(19))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
