package org.roy.pettyCash.view;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.roy.bean.PettyCash;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.TableSettings;
import org.roy.settings.listener.extend.PettyCashTableListener;

public class PettyCashDetail extends JPanel {

	private static final long serialVersionUID = 1L;
	public static JTable pettyMoneyTable;
	private final String[] filterMonth= {"01","02","03","04","05","06","07","08","09","10","11","12"};
	private List<PettyCash> detailFilterByMonth;
	private JComboBox<String> comboBox;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public PettyCashDetail() {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		initComponents();
		initLayout();

	}

	private void initComponents() {
		comboBox = new JComboBox<>(new String[] {"全部","1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"});
		comboBox.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		comboBox.setRenderer(Const.DLCR);
		comboBox.addActionListener(e->{
			showDetailsByMonth();
		});

		pettyMoneyTable = TableSettings.getInfoTable();
		pettyMoneyTable.addMouseListener(new PettyCashTableListener(pettyMoneyTable));

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(pettyMoneyTable);
	}


	public List<PettyCash> getDetailFilterByMonth() {
		return detailFilterByMonth;
	}

	public void setDetailFilterByMonth(List<PettyCash> detailFilterByMonth) {
		this.detailFilterByMonth = detailFilterByMonth;
	}

	public void showDetailsByMonth() {
		if(comboBox.getSelectedIndex()==0) {
			detailFilterByMonth = DataFile.pettyCashDetails;
		}else {
			detailFilterByMonth = DataFile.pettyCashDetails.stream().filter(p->p.getDate().substring(5,7).equals(filterMonth[comboBox.getSelectedIndex()-1])).collect(Collectors.toList());
		}
		TableSettings.show(pettyMoneyTable,
				Const.COLUMNS_FOR_PETTYMONEY,
				detailFilterByMonth.stream().map(e->e.DetailInfo()).toArray(Object[][]::new),
				Const.PETTY_MONEY_COLUMN_SIZE);
		TableSettings.scrollToBottom(pettyMoneyTable,pettyMoneyTable.getRowCount()-1);
//		System.out.println(detailFilterByMonth);
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(500,600,630)
						.addComponent(comboBox,100,180, Short.MAX_VALUE)
						.addGap(500,600,630))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(20)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(20)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE))
				);
		setLayout(groupLayout);
		comboBox.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH)+1);
	}
}
