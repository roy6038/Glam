package org.roy.inventory.viewFunction;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.roy.bean.Inventory;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;

public class OD_BAG_inventory extends JPanel {
	private static final long serialVersionUID = -4077036618003572076L;
	protected static JTextArea i_memo;
	private JTextField i_name;
	private JTextField i_amount;
	private JTextField i_price;
	private JComboBox<String> i_positionId;
	private JComboBox<String> i_unit;
	private JTextArea i_note;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_3_1;
	private JLabel lblNewLabel_3_3;
	private JLabel lblNewLabel_3_2;
	private JScrollPane scrollPane;


	/**
	 * Create the panel.
	 */
	public OD_BAG_inventory() {
		setBounds(0,0,(int)(Const.SCREENSIZE.getWidth()/3),(int) (Const.SCREENSIZE.getHeight()/1.9));
		initComponents();
		initLayout();
	}

	private void initComponents() {
		lblNewLabel_1 = new JLabel("名稱：");
		lblNewLabel_2 = new JLabel("倉位編號：");
		lblNewLabel_3 = new JLabel("數量：");
		lblNewLabel_3_1 = new JLabel("單位：");
		lblNewLabel_3_3 = new JLabel("單價：");
		lblNewLabel_3_2 = new JLabel("備註：");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));


		i_name = new JTextField();
		i_amount = new JTextField();
		i_price = new JTextField();
		i_name.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_amount.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_price.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_name.setColumns(10);
		i_amount.setColumns(10);
		i_price.setColumns(10);
		i_amount.setHorizontalAlignment(SwingConstants.RIGHT);
		i_price.setHorizontalAlignment(SwingConstants.RIGHT);

		i_positionId = new JComboBox<>();
		i_unit = new JComboBox<>();
		i_positionId.setFont(new Font("Dialog", Font.BOLD, 16));
		i_unit.setFont(new Font("Dialog", Font.BOLD, 16));
		i_positionId.setModel(new DefaultComboBoxModel<>(DataFile.depotLocate.keySet().toArray(String[]::new)));
		i_unit.setModel(new DefaultComboBoxModel<>(new String[] { "單件" }));
		i_positionId.setRenderer(Const.DLCR);
		i_unit.setRenderer(Const.DLCR);

		i_note = new JTextArea();
		i_note.setLineWrap(true);
		i_note.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_note.setRows(4);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(i_note);
	}

	public void cleanInput() {
		i_name.setText("");
		i_positionId.setSelectedIndex(0);
		i_amount.setText("");
		i_price.setText("");
		i_note.setText("");
	}

	public Inventory getInventory(){
		return new Inventory(i_name.getText(),i_positionId.getSelectedItem().toString(),i_amount.getText(),
				i_unit.getSelectedItem().toString(),i_price.getText(),i_note.getText());
	}

	public void setInput(Inventory info) {
		i_name.setText(info.getName());
		i_positionId.setSelectedItem(info.getPositionId());
		i_amount.setText(info.getAmount().toString());
		i_price.setText(info.getPrice().toString());
		i_note.setText(info.getNote());
	}

	public boolean confirm() {
		if(i_amount.getText().length()==0) {
			i_amount.setText("0");
		}

		if(!Functions.isNumberic(i_amount.getText(),true,false)) {
			JOptionPane.showMessageDialog(null,"數量錯誤");
			return false;
		}else if(!Functions.isNumberic(i_price.getText(),false,false)) {
			JOptionPane.showMessageDialog(null,"單價錯誤");
			return false;
		}
		return true;
	}

	public JTextField getI_name() {
		return i_name;
	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(86)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(i_name))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(i_positionId, 0, 160, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(i_amount))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(i_unit, 0, 160, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3_3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(i_price))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(scrollPane)))
					.addGap(122))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(i_name))
					.addGap(51)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(i_positionId))
					.addGap(48)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(i_amount))
					.addGap(51)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNewLabel_3_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(i_unit))
					.addGap(48)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_3_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(i_price))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(53)
							.addComponent(lblNewLabel_3_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(41))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(20)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)))
					.addGap(11))
		);
		setLayout(groupLayout);
	}
}
