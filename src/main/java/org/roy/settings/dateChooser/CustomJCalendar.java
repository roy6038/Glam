package org.roy.settings.dateChooser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

public class CustomJCalendar extends JCalendar{

	private static final long serialVersionUID = -7830173567595334544L;
	private Calendar calendar;
	private final JPanel monthYearPanel;
	private final JPanel specialButtonPanel;
	private final JButton todayButton;

	private final JButton nullDateButton;
	@SuppressWarnings("unused")
	private boolean initialized;


	public CustomJCalendar(Date date,boolean monthSpinner,boolean weekOfYearVisible,Locale locale) {

		setName("JCalendar");

		// needed for setFont() etc.
		dayChooser = null;
		monthChooser = null;
		yearChooser = null;
		this.weekOfYearVisible = weekOfYearVisible;

		if (locale == null) {
			this.locale = Locale.getDefault();
		} else {
			this.locale = locale;
		}

		calendar = Calendar.getInstance(this.locale);

		setLayout(new BorderLayout());

		monthYearPanel = new JPanel();
		monthYearPanel.setLayout(new BorderLayout());

		monthChooser = new JMonthChooser(monthSpinner);
		yearChooser = new JYearChooser();
		monthChooser.setYearChooser(yearChooser);
		monthChooser.setLocale(this.locale);
		monthYearPanel.add(monthChooser, BorderLayout.WEST);
		monthYearPanel.add(yearChooser, BorderLayout.CENTER);
		monthYearPanel.setBorder(BorderFactory.createEmptyBorder());

		dayChooser = new CustomDayChooser(weekOfYearVisible);
		dayChooser.addPropertyChangeListener(this);
		dayChooser.setLocale(this.locale);

		monthChooser.setDayChooser(dayChooser);
		monthChooser.addPropertyChangeListener(this);
		yearChooser.setDayChooser(dayChooser);
		yearChooser.addPropertyChangeListener(this);
		add(monthYearPanel, BorderLayout.NORTH);
		add(dayChooser, BorderLayout.CENTER);

		specialButtonPanel = new JPanel();
		todayButton = new JButton();
		todayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setDate(new Date());
			}
		});
		nullDateButton = new JButton();
		nullDateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dayChooser.firePropertyChange("day", 0, -1);
			}
		});
		specialButtonPanel.setVisible(false);
		add(specialButtonPanel, BorderLayout.SOUTH);

		// Set the initialized flag before setting the calendar. This will
		// cause the other components to be updated properly.
		if (date != null) {
			calendar.setTime(date);
		}

		initialized = true;

		setCalendar(calendar);

	}

}
