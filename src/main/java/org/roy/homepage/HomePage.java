package org.roy.homepage;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import org.roy.homepage.calendar.CalendarCustom;

public class HomePage extends JPanel {

	private static final long serialVersionUID = 1L;
	private CalendarCustom calendarCustom;
	/**
	 * Create the panel.
	 */
	public HomePage() {
//		setBounds(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		calendarCustom = new CalendarCustom();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(calendarCustom, GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(calendarCustom, GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
	public CalendarCustom getCalendarCustom() {
		return calendarCustom;
	}



}
