package org.roy.homepage.calendar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.roy.homepage.application.GlamChamber;
import org.roy.settings.DataFile;
import org.roy.settings.GlobalColor;

public class Cell extends JButton {

    private static final long serialVersionUID = 1452236575845296914L;
	private Date date;
    private boolean title;
    private boolean isToDay;
    private int circleSize = GlamChamber.homePage == null?5:GlamChamber.homePage.getCalendarCustom().getSliderValue()/10;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public Cell() {
        setContentAreaFilled(false);
        setBorder(null);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void asTitle() {
        title = true;
    }

    public boolean isTitle() {
        return title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
    	return date;
    }

    public int getCircleSize() {
		return circleSize;
	}

	public void setCircleSize(int circleSize) {
		this.circleSize = circleSize;
	}

	public void currentMonth(boolean act) {
        if (!act) {
        	GlobalColor.updateMenuUiColor(this, ""
                    + "foreground:"+GlobalColor.CalendarDisableColor+";");
        }
    }

    public void setAsToDay() {
        isToDay = true;
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (title) {
            grphcs.setColor(new Color(213, 213, 213));
            grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        }else if(date!=null && DataFile.dailySaleIncome.get(simpleDateFormat.format(date))!=null) {
        	hasDailyIncome(Color.green.darker(),grphcs);
        }else{
        	hasDailyIncome(Color.red.darker(),grphcs);
        }
        if (isToDay) {
        	setTodayPainted(Color.decode(GlobalColor.CalendarDisableColor),grphcs);
        }
        if(date!=null && simpleDateFormat.format(date).equals(GlamChamber.homePage.getCalendarCustom().getSaleDate())) {
        	setTodayPainted(Color.decode(GlobalColor.FocusColor),grphcs);
        }else {
        	grphcs.setColor(this.getBackground());
        }
        super.paintComponent(grphcs);
    }

    public void hasDailyIncome(Color color,Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        int x = getWidth() / 2-(circleSize/2);
        int y = getHeight() / 2+25;
        g2.fillRoundRect(x, y,circleSize,circleSize, 100, 100);
    }

    public void setTodayPainted(Color color,Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        int x = getWidth() / 2 - 17;
        int y = getHeight() / 2 - 17;
        g2.fillRoundRect(x, y, 35, 35, 100, 100);

    }
}
