package org.roy.homepage.calendar;

import java.awt.Cursor;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.roy.settings.DataFile;
import org.roy.settings.GlobalColor;

public class CalendarCustom extends JPanel {

    private static final long serialVersionUID = 1L;
	private int month;
    private int year;
    private JButton cmdBack;
    private JButton cmdNext;
    private JLayeredPane jLayeredPane1;
    private JPanel jPanel1;
    private JLabel lbDate;
    private JLabel lbMonthYear;
    private JLabel lbTime;
    private JLabel lbType;
    private JLabel lbAno;
    private PanelSlide slide;
    private PanelDate panelDate;
	private JLabel saleIncome;
	private JLabel saleDate;
	private JLabel lbSaleIncome;
	private JLabel lbSaleDate;
	private JLabel[] boardText;
	private JTextArea announcement;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	private JLabel lbMonthlySaleIncome;
	private JLabel monthlySaleIncome;
	private JSlider slider;
	private SimpleDateFormat tf = new SimpleDateFormat("h:mm:ss aa");
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM/dd,EEEE");

    public CalendarCustom() {
        initComponents();
        thisMonth();
        panelDate = new PanelDate(month, year);
        slider.setValue(100);
        slide.show(panelDate, PanelSlide.AnimateType.TO_RIGHT);
        showMonthYear();
        new Thread(()-> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                    Date date = new Date();
                    String time = tf.format(date);
                    lbTime.setText(time.split(" ")[0]);
                    lbType.setText(time.split(" ")[1]);
                    lbDate.setText(df.format(date));
                }
        }).start();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        slide = new PanelSlide();
        jPanel1 = new JPanel();
        lbTime = new JLabel();
        lbType = new JLabel();
        lbDate = new JLabel();
        jLayeredPane1 = new JLayeredPane();
        cmdBack = new JButton();
        lbMonthYear = new JLabel();
        cmdNext = new JButton();

        GlobalColor.updateMenuUiColor(this,  ""
                + "background:"+GlobalColor.CalendarBackgroundColor+";");

        GlobalColor.updateMenuUiColor(slide, ""
                + "background:"+GlobalColor.CalendarBackgroundColor+";");

        GroupLayout slideLayout = new javax.swing.GroupLayout(slide);
        slide.setLayout(slideLayout);
        slideLayout.setHorizontalGroup(
            slideLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0,0, Short.MAX_VALUE)
        );
        slideLayout.setVerticalGroup(
            slideLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0,0, Short.MAX_VALUE)
        );

        GlobalColor.updateMenuUiColor(jPanel1, ""
                + "background:"+GlobalColor.CalendarBoardColor+";");

        lbTime.setFont(new Font("sansserif", 1, 48)); // NOI18N
        lbTime.setHorizontalAlignment(SwingConstants.RIGHT);
        lbTime.setText("9:32:00");

        lbType.setFont(new Font("sansserif", 1, 25)); // NOI18N
        lbType.setText("PM");

        lbDate.setFont(new Font("sansserif", 0, 18)); // NOI18N
        lbDate.setHorizontalAlignment(SwingConstants.CENTER);
        lbDate.setText("Sunday, 30/05/2021");

        cmdBack.setIcon(new ImageIcon(getClass().getResource("/org/roy/icon/back.png")));
        cmdBack.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cmdBack.setContentAreaFilled(false);
        cmdBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdBack.addActionListener(e-> cmdBackActionPerformed());

        lbMonthYear.setFont(new Font("sansserif", 1, 30)); // NOI18N
        lbMonthYear.setHorizontalAlignment(SwingConstants.CENTER);
        lbMonthYear.setText("Month - Year");

        cmdNext.setIcon(new ImageIcon(getClass().getResource("/org/roy/icon/next.png"))); // NOI18N
        cmdNext.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cmdNext.setContentAreaFilled(false);
        cmdNext.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdNext.addActionListener(e-> cmdNextActionPerformed());

        jLayeredPane1.setLayer(cmdBack,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbMonthYear,JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cmdNext,JLayeredPane.DEFAULT_LAYER);
//        jLayeredPane1.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent ce) {
//                getSlide().getComShow().repaint();
//                getSlide().getComShow().revalidate();
//            }
//        });

        GroupLayout jLayeredPane1Layout = new GroupLayout(jLayeredPane1);
        jLayeredPane1Layout.setHorizontalGroup(
        	jLayeredPane1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jLayeredPane1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(cmdBack, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lbMonthYear, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(cmdNext, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
        	jLayeredPane1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jLayeredPane1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jLayeredPane1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(cmdBack, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        				.addComponent(lbMonthYear)
        				.addComponent(cmdNext, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
        			.addContainerGap())
        );
        jLayeredPane1.setLayout(jLayeredPane1Layout);

        lbSaleDate = new JLabel();
        lbSaleDate.setHorizontalAlignment(SwingConstants.TRAILING);
        lbSaleDate.setText("銷售日期：");
        lbSaleDate.setFont(new Font("SansSerif", Font.BOLD, 20));

        lbSaleIncome = new JLabel();
        lbSaleIncome.setHorizontalAlignment(SwingConstants.TRAILING);
        lbSaleIncome.setText("銷售額：");
        lbSaleIncome.setFont(new Font("SansSerif", Font.BOLD, 20));

        String toDay = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        saleDate = new JLabel();
        saleDate.setText(toDay);
        saleDate.setFont(new Font("SansSerif", Font.BOLD, 20));

        saleIncome = new JLabel();
        saleIncome.setText(DataFile.dailySaleIncome==null||DataFile.dailySaleIncome.get(toDay)==null?"$0":"$"+DataFile.dailySaleIncome.get(toDay));
        saleIncome.setFont(new Font("SansSerif", Font.BOLD, 20));

        JScrollPane scrollPane = new JScrollPane();

        announcement = new JTextArea();
        announcement.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        announcement.setLineWrap(true);
        announcement.setWrapStyleWord(true);
        scrollPane.setViewportView(announcement);
        announcement.setText(DataFile.announcement);
        announcement.setEnabled(false);

        lbAno = new JLabel();
        lbAno.setText("公布欄");
        lbAno.setHorizontalAlignment(SwingConstants.CENTER);
        lbAno.setFont(new Font("SansSerif", Font.BOLD, 20));


        lbMonthlySaleIncome = new JLabel();
        lbMonthlySaleIncome.setText("月銷售額：");
        lbMonthlySaleIncome.setHorizontalAlignment(SwingConstants.TRAILING);
        lbMonthlySaleIncome.setFont(new Font("SansSerif", Font.BOLD, 20));

        monthlySaleIncome = new JLabel();
        monthlySaleIncome.setText("$0");
        monthlySaleIncome.setFont(new Font("SansSerif", Font.BOLD, 20));

        boardText = new JLabel[]{lbDate,lbTime,lbType,lbAno,saleIncome,saleDate,lbSaleDate,lbSaleIncome,lbMonthlySaleIncome,monthlySaleIncome};
        for(JLabel label:boardText) {
        	GlobalColor.updateMenuUiColor(label,  ""
        			+ "foreground:"+GlobalColor.CalendarBoardForegroundColor+";");
        }

        slider = new JSlider();
        slider.setMinimum(50);
        slider.setToolTipText("調整標記和數字大小");
        slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int size = slider.getValue()/10;
				Arrays.stream(panelDate.getComponents()).forEach(ce->{
					Cell com = (Cell)ce;
					com.setCircleSize(size);
					com.setFont(new Font("sansserif", 0, size+10));
					});
				repaint();
			}
		});
        slider.setFont(new Font("Lucida Grande", Font.BOLD, 16));

        GroupLayout gl_jPanel1 = new GroupLayout(jPanel1);
        gl_jPanel1.setHorizontalGroup(
        	gl_jPanel1.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addComponent(lbTime, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lbType, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addGap(12)
        			.addComponent(lbDate, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE))
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addComponent(lbSaleDate, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
        			.addGap(24)
        			.addComponent(saleDate, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
        			.addContainerGap())
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addComponent(lbSaleIncome, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
        			.addGap(25)
        			.addComponent(saleIncome, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addGap(85)
        			.addComponent(lbAno, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(89, Short.MAX_VALUE))
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
        			.addContainerGap())
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addComponent(lbMonthlySaleIncome, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
        			.addGap(25)
        			.addComponent(monthlySaleIncome, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addGap(52)
        			.addComponent(slider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGap(47))
        );
        gl_jPanel1.setVerticalGroup(
        	gl_jPanel1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanel1.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_jPanel1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lbTime, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lbType))
        			.addGap(6)
        			.addComponent(lbDate)
        			.addGap(16)
        			.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_jPanel1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(saleDate, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lbSaleDate, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_jPanel1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(saleIncome, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lbSaleIncome, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING)
        				.addComponent(lbMonthlySaleIncome, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
        				.addComponent(monthlySaleIncome, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lbAno, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        			.addGap(6))
        );
        jPanel1.setLayout(gl_jPanel1);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(6)
        					.addComponent(jLayeredPane1, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(slide, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
        					.addContainerGap())))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(13)
        			.addComponent(jLayeredPane1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(slide, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
        			.addContainerGap())
        );
        setLayout(groupLayout);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdNextActionPerformed() {//GEN-FIRST:event_cmdNextActionPerformed
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        panelDate = new PanelDate(month, year);
        slide.show(panelDate, PanelSlide.AnimateType.TO_LEFT);
        showMonthYear();
    }//GEN-LAST:event_cmdNextActionPerformed

    private void cmdBackActionPerformed() {//GEN-FIRST:event_cmdBackActionPerformed
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        panelDate = new PanelDate(month, year);
        slide.show(panelDate, PanelSlide.AnimateType.TO_RIGHT);
        showMonthYear();
    }//GEN-LAST:event_cmdBackActionPerformed

    private void thisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());   //  today
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        setMonthlySaleIncome(getMonthlySaleIncome(year, month).toString());
    }

    public void showMonthYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMMM");
        lbMonthYear.setText(df.format(calendar.getTime()));
    }

    public void setUIcolorWhenChangingTheme() {
        GlobalColor.updateMenuUiColor(jPanel1, ""
                + "background:"+GlobalColor.CalendarBoardColor+";");
        GlobalColor.updateMenuUiColor(slide, ""
                + "background:"+GlobalColor.CalendarBackgroundColor+";");
        GlobalColor.updateMenuUiColor(this,  ""
                + "background:"+GlobalColor.CalendarBackgroundColor+";");
        lbMonthYear.setForeground(UIManager.getColor("Panel.foreground"));

        for(JLabel label:boardText) {
        	GlobalColor.updateMenuUiColor(label,  ""
                       + "foreground:"+GlobalColor.CalendarBoardForegroundColor+";");
        }
        panelDate.setDisbleAfterChangingTheme();
    }

    public void setSaleDate(String date) {
    	saleDate.setText(date);
    }

    public String getSaleDate() {
    	return saleDate.getText();
    }

    public void setSaleIncome() {
    	saleIncome.setText(DataFile.dailySaleIncome.get(getSaleDate())==null?"$0":"$"+DataFile.dailySaleIncome.get(getSaleDate()));
    }

    public void setAnnouncement(String anno) {
    	announcement.setText(anno);
    }

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public int getSliderValue() {
		return slider.getValue();
	}


	public PanelDate getPanelDate() {
		return panelDate;
	}


	public JLayeredPane getjLayeredPane1() {
		return jLayeredPane1;
	}

	public void setMonthlySaleIncome(String monthlySaleIncome) {
		this.monthlySaleIncome.setText("$"+monthlySaleIncome);
	}

	public void setMonthlySaleIncome(int monthlySaleIncome) {
		this.monthlySaleIncome.setText("$"+monthlySaleIncome);
	}


	public PanelSlide getSlide() {
		return slide;
	}

	public Integer getMonthlySaleIncome(int year,int month) {

	    return IntStream
	            .rangeClosed(1, YearMonth.of(year, month).lengthOfMonth())
	            .map(i ->DataFile.dailySaleIncome.get(formatter.format(LocalDate.of(year, month, i))) == null ? 0:DataFile.dailySaleIncome.get(formatter.format(LocalDate.of(year, month, i))))
	            .sum();
	}
}
