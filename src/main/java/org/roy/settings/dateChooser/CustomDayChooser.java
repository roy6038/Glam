package org.roy.settings.dateChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.roy.settings.GlobalColor;

import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.MinMaxDateEvaluator;

public class CustomDayChooser extends JDayChooser{

	private static final long serialVersionUID = -4939276580656681537L;

	public CustomDayChooser() {
		this(false);
		// TODO Auto-generated constructor stub
	}

	public CustomDayChooser(boolean weekOfYearVisible) {
		setName("JDayChooser");

		dateEvaluators = new ArrayList<>(1);
		minMaxDateEvaluator = new MinMaxDateEvaluator();
		addDateEvaluator(minMaxDateEvaluator);

		this.weekOfYearVisible = weekOfYearVisible;
		locale = Locale.getDefault();
		days = new JButton[49];
		selectedDay = null;
		calendar = Calendar.getInstance(locale);
		today = (Calendar) calendar.clone();

		setLayout(new BorderLayout());

		dayPanel = new JPanel();
		dayPanel.setLayout(new GridLayout(7, 7));

		sundayForeground = Color.decode(GlobalColor.errorFocusColor);
		weekdayForeground = Color.decode(GlobalColor.menuBackground);

		// decorationBackgroundColor = new Color(194, 211, 252);
		// decorationBackgroundColor = new Color(206, 219, 246);
		decorationBackgroundColor = UIManager.getColor("Button.background");

		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				int index = x + (7 * y);

				if (y == 0) {
					// Create a button that doesn't react on clicks or focus
					// changes.
					// Thanks to Thomas Schaefer for the focus hint :)
					days[index] = new DecoratorButton();
				} else {
					days[index] = new JButton();
					days[index].addActionListener(this);
					days[index].addKeyListener(this);
					days[index].addFocusListener(this);
				}

				days[index].setMargin(new Insets(0, 0, 0, 0));
				days[index].setFocusPainted(false);
				dayPanel.add(days[index]);
			}
		}

		weekPanel = new JPanel();
		weekPanel.setLayout(new GridLayout(7, 1));
		weeks = new JButton[7];

		for (int i = 0; i < 7; i++) {
			weeks[i] = new DecoratorButton();
			weeks[i].setMargin(new Insets(0, 0, 0, 0));
			weeks[i].setFocusPainted(false);
			weeks[i].setForeground(Color.decode(GlobalColor.FocusColor));

			if (i != 0) {
				weeks[i].setText("0" + (i + 1));
			}

			weekPanel.add(weeks[i]);
		}

		init();
		oldDayBackgroundColor = decorationBackgroundColor;
		selectedColor = Color.decode(GlobalColor.menuBackground);

		setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		add(dayPanel, BorderLayout.CENTER);

		if (weekOfYearVisible) {
			add(weekPanel, BorderLayout.WEST);
		}

		initialized = true;
		updateUI();
	}





	@Override
	public void setDecorationBackgroundColor(Color decorationBackgroundColor) {
		this.decorationBackgroundColor = decorationBackgroundColor;
		sundayForeground = Color.decode(GlobalColor.errorFocusColor);
		weekdayForeground = Color.decode(GlobalColor.menuBackground);
		selectedColor = Color.decode(GlobalColor.menuBackground);
		setSundayForeground(sundayForeground);
		setWeekdayForeground(weekdayForeground);
		oldDayBackgroundColor = decorationBackgroundColor;

		if (days != null) {
			for (int i = 0; i < 49; i++) {
				days[i].setBackground(decorationBackgroundColor);
//				System.out.println(days[i].getText()+"--"+days[i].getBackground().equals(decorationBackgroundColor));
			}
		}

		if (weeks != null) {
			for (int i = 0; i < 7; i++) {
				weeks[i].setBackground(decorationBackgroundColor);
				weeks[i].setForeground(selectedColor);
			}
		}
	}

	class DecoratorButton extends JButton {
		private static final long serialVersionUID = -5306477668406547496L;

		public DecoratorButton() {
			setBackground(decorationBackgroundColor);
			setContentAreaFilled(decorationBackgroundVisible);
			setBorderPainted(decorationBordersVisible);
		}

		@Override
		public void addMouseListener(MouseListener l) {
		}

		@Override
		public boolean isFocusable() {
			return false;
		}

		@Override
		public void paint(Graphics g) {
			if ("Windows".equals(UIManager.getLookAndFeel().getID())) {
				// this is a hack to get the background painted
				// when using Windows Look & Feel
				if (decorationBackgroundVisible) {
					g.setColor(decorationBackgroundColor);
				} else {
					g.setColor(days[7].getBackground());
				}
				g.fillRect(0, 0, getWidth(), getHeight());
				if (isBorderPainted()) {
					setContentAreaFilled(true);
				} else {
					setContentAreaFilled(false);
				}
			}
			super.paint(g);
		}
	}

}
