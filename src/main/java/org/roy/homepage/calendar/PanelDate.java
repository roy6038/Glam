package org.roy.homepage.calendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PanelDate extends javax.swing.JLayeredPane {

    private static final long serialVersionUID = 1L;
	private int month;
    private int year;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public PanelDate(int month, int year) {
        initComponents();
        this.month = month;
        this.year = year;
        init();
    }

    private void init() {
        mon.asTitle();
        tue.asTitle();
        wed.asTitle();
        thu.asTitle();
        fri.asTitle();
        sat.asTitle();
        sun.asTitle();
        setDate();
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);  //  month jan as 0 so start from 0
        calendar.set(Calendar.DATE, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;  //  get day of week -1 to index
        calendar.add(Calendar.DATE, -startDay);
        ToDay toDay = getToDay();
        for (Component com : getComponents()) {
            Cell cell = (Cell) com;
            cell.setFont(new Font("sansserif", 0,cell.getCircleSize()+10));
            if (!cell.isTitle()) {
//            	System.out.println(calendar.get(Calendar.DATE));
//            	System.out.println(calendar.getTime());
                cell.setText(calendar.get(Calendar.DATE) + "");
                cell.setDate(calendar.getTime());
                cell.currentMonth(calendar.get(Calendar.MONTH) == month - 1);
                if (toDay.isToDay(new ToDay(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR)))) {
                    cell.setAsToDay();
                }
                cell.addActionListener(e->{
            		CalendarCustom calendarCustom = (CalendarCustom)this.getParent().getParent();
            		Calendar c = Calendar.getInstance();
            		c.setTime(cell.getDate());
            		calendarCustom.setMonthlySaleIncome(calendarCustom.getMonthlySaleIncome(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1));
            		calendarCustom.setSaleDate(simpleDateFormat.format(cell.getDate()));
            		calendarCustom.setSaleIncome();
            		calendarCustom.repaint();
                });
                calendar.add(Calendar.DATE, 1); //  up 1 day
            }
        }
    }

//	public void cellAction(Cell cell) {
//		long s = System.currentTimeMillis();
//
//		System.out.println(y+"--"+calendarCustom.getYear()+","+m+"--"+(calendarCustom.getMonth()-1));
//		if(!(y==calendarCustom.getYear()&&m == calendarCustom.getMonth()-1)) {
//			System.out.println(calendarCustom.getMonthlySaleIncome(calendarCustom.getYear(),calendarCustom.getMonth()).toString());
//		}
//		System.out.println(System.currentTimeMillis()-s);
//	}

    public void setDisbleAfterChangingTheme() {
    	Calendar calendar = Calendar.getInstance();
        for (Component com : getComponents()) {
            Cell cell = (Cell) com;
            if (!cell.isTitle()) {
            	calendar.setTime(cell.getDate());
                cell.currentMonth( calendar.get(Calendar.MONTH)== month - 1);
            }
        }
    }

    private ToDay getToDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return new ToDay(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sun = new Cell();
        mon = new Cell();
        tue = new Cell();
        wed = new Cell();
        thu = new Cell();
        fri = new Cell();
        sat = new Cell();
        cell8 = new Cell();
        cell9 = new Cell();
        cell10 = new Cell();
        cell11 = new Cell();
        cell12 = new Cell();
        cell13 = new Cell();
        cell14 = new Cell();
        cell15 = new Cell();
        cell16 = new Cell();
        cell17 = new Cell();
        cell18 = new Cell();
        cell19 = new Cell();
        cell20 = new Cell();
        cell21 = new Cell();
        cell22 = new Cell();
        cell23 = new Cell();
        cell24 = new Cell();
        cell25 = new Cell();
        cell26 = new Cell();
        cell27 = new Cell();
        cell28 = new Cell();
        cell29 = new Cell();
        cell30 = new Cell();
        cell31 = new Cell();
        cell32 = new Cell();
        cell33 = new Cell();
        cell34 = new Cell();
        cell35 = new Cell();
        cell36 = new Cell();
        cell37 = new Cell();
        cell38 = new Cell();
        cell39 = new Cell();
        cell40 = new Cell();
        cell41 = new Cell();
        cell42 = new Cell();
        cell43 = new Cell();
        cell44 = new Cell();
        cell45 = new Cell();
        cell46 = new Cell();
        cell47 = new Cell();
        cell48 = new Cell();
        cell49 = new Cell();

        setLayout(new GridLayout(7, 7));

        add(sun);
        add(mon);
        add(tue);
        add(wed);
        add(thu);
        add(fri);
        add(sat);
        add(cell8);
        add(cell9);
        add(cell10);
        add(cell11);
        add(cell12);
        add(cell13);
        add(cell14);
        add(cell15);
        add(cell16);
        add(cell17);
        add(cell18);
        add(cell19);
        add(cell20);
        add(cell21);
        add(cell22);
        add(cell23);
        add(cell24);
        add(cell25);
        add(cell26);
        add(cell27);
        add(cell28);
        add(cell29);
        add(cell30);
        add(cell31);
        add(cell32);
        add(cell33);
        add(cell34);
        add(cell35);
        add(cell36);
        add(cell37);
        add(cell38);
        add(cell39);
        add(cell40);
        add(cell41);
        add(cell42);
        add(cell43);
        add(cell44);
        add(cell45);
        add(cell46);
        add(cell47);
        add(cell48);
        add(cell49);

        sun.setForeground(new Color(222, 12, 12));

        sun.setText("Sun");
        mon.setText("Mon");
        tue.setText("Tue");
        wed.setText("Wed");
        thu.setText("Thu");
        fri.setText("Fri");
        sat.setText("Sat");

//        sun.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        mon.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        tue.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        wed.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        thu.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        fri.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        sat.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell8.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell9.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell10.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell11.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell12.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell13.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell14.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell15.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell16.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell17.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell18.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell19.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell20.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell21.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell22.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell23.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell24.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell25.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell26.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell27.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell28.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell29.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell30.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell31.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell32.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell33.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell34.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell35.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell36.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell37.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell38.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell39.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell40.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell41.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell42.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell43.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell44.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell45.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell46.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell47.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell48.setFont(new Font("sansserif", 0, 20)); // NOI18N
//
//        cell49.setFont(new Font("sansserif", 0, 20)); // NOI18N
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Cell cell10;
    private Cell cell11;
    private Cell cell12;
    private Cell cell13;
    private Cell cell14;
    private Cell cell15;
    private Cell cell16;
    private Cell cell17;
    private Cell cell18;
    private Cell cell19;
    private Cell cell20;
    private Cell cell21;
    private Cell cell22;
    private Cell cell23;
    private Cell cell24;
    private Cell cell25;
    private Cell cell26;
    private Cell cell27;
    private Cell cell28;
    private Cell cell29;
    private Cell cell30;
    private Cell cell31;
    private Cell cell32;
    private Cell cell33;
    private Cell cell34;
    private Cell cell35;
    private Cell cell36;
    private Cell cell37;
    private Cell cell38;
    private Cell cell39;
    private Cell cell40;
    private Cell cell41;
    private Cell cell42;
    private Cell cell43;
    private Cell cell44;
    private Cell cell45;
    private Cell cell46;
    private Cell cell47;
    private Cell cell48;
    private Cell cell49;
    private Cell cell8;
    private Cell cell9;
    private Cell fri;
    private Cell mon;
    private Cell sat;
    private Cell sun;
    private Cell thu;
    private Cell tue;
    private Cell wed;
}
