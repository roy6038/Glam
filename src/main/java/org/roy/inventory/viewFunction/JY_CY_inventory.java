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

public class JY_CY_inventory extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField i_name;
	private JComboBox<String> i_positionId;
	private JComboBox<String> i_unit;
	private JTextField i_price;
	private JTextArea i_note;
	private JTextField i_XXSNum;
	private JTextField i_XSNum;
	private JTextField i_SNum;
	private JTextField i_flaw;
	private JTextField i_LNum;
	private JTextField i_XLNum;
	private JTextField i_XXLNum;
	private JTextField i_MNum;
	private JTextField i_sampleNum;

	private final String[] size = { "樣衣", "XXS", "XS", "S", "M", "L", "XL", "XXL", "瑕疵品","單價" };
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_3_1;
	private JLabel lblNewLabel_3_2;
	private JLabel lblNewLabel_3_3;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_3_4;
	private JLabel lblNewLabel_3_5;
	private JLabel lblNewLabel_3_5_1;
	private JLabel lblNewLabel_3_5_2;
	private JLabel lblNewLabel_3_5_3;
	private JLabel lblNewLabel_3_5_4;
	private JLabel lblNewLabel_3_5_5;
	private JScrollPane scrollPane;


	/**
	 * Create the panel.
	 */
	public JY_CY_inventory() {
		setBounds(0,0,(int)(Const.SCREENSIZE.getWidth()/3),(int) (Const.SCREENSIZE.getHeight()/1.9));
		initComponents();
		initLayout();
	}

	private void initComponents() {
		lblNewLabel_1 = new JLabel("名稱：");
		lblNewLabel_2 = new JLabel("倉位編號：");
		lblNewLabel_3 = new JLabel("樣衣：");
		lblNewLabel_3_1 = new JLabel("單位：");
		lblNewLabel_3_2 = new JLabel("單價：");
		lblNewLabel_3_3 = new JLabel("備註：");
		lblNewLabel = new JLabel("XXS：");
		lblNewLabel_3_4 = new JLabel("XS：");
		lblNewLabel_3_5 = new JLabel("S：");
		lblNewLabel_3_5_1 = new JLabel("M：");
		lblNewLabel_3_5_2 = new JLabel("L：");
		lblNewLabel_3_5_3 = new JLabel("XL：");
		lblNewLabel_3_5_4 = new JLabel("XXL：");
		lblNewLabel_3_5_5 = new JLabel("瑕疵品：");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_4.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_5.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_5_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_5_2.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_5_3.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_5_4.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_3_5_5.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_4.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_5.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_5_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_5_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_5_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_5_4.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3_5_5.setHorizontalAlignment(SwingConstants.TRAILING);

		i_positionId = new JComboBox<>();
		i_unit = new JComboBox<>();
		i_positionId.setFont(new Font("Dialog", Font.BOLD, 16));
		i_unit.setFont(new Font("Dialog", Font.BOLD, 16));
		i_positionId.setModel(new DefaultComboBoxModel<>(DataFile.depotLocate.keySet().toArray(String[]::new)));
		i_unit.setModel(new DefaultComboBoxModel<>(new String[] { "單件" }));
		i_positionId.setRenderer(Const.DLCR);
		i_unit.setRenderer(Const.DLCR);

		i_name = new JTextField();
		i_price = new JTextField();
		i_sampleNum = new JTextField();
		i_XXSNum = new JTextField();
		i_XSNum = new JTextField();
		i_SNum = new JTextField();
		i_MNum = new JTextField();
		i_LNum = new JTextField();
		i_XLNum = new JTextField();
		i_XXLNum = new JTextField();
		i_flaw = new JTextField();

		i_name.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_price.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_sampleNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_XXSNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_XSNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_SNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_MNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_LNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_XLNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_XXLNum.setFont(new Font("Dialog", Font.PLAIN, 16));
		i_flaw.setFont(new Font("Dialog", Font.PLAIN, 16));

		i_name.setColumns(10);
		i_price.setColumns(10);
		i_sampleNum.setColumns(10);
		i_XXSNum.setColumns(10);
		i_XSNum.setColumns(10);
		i_SNum.setColumns(10);
		i_MNum.setColumns(10);
		i_LNum.setColumns(10);
		i_XLNum.setColumns(10);
		i_XXLNum.setColumns(10);
		i_flaw.setColumns(10);

		i_price.setHorizontalAlignment(SwingConstants.RIGHT);
		i_sampleNum.setHorizontalAlignment(SwingConstants.RIGHT);
		i_XXSNum.setHorizontalAlignment(SwingConstants.RIGHT);
		i_XSNum.setHorizontalAlignment(SwingConstants.RIGHT);
		i_SNum.setHorizontalAlignment(SwingConstants.RIGHT);
		i_MNum.setHorizontalAlignment(SwingConstants.RIGHT);
		i_LNum.setHorizontalAlignment(SwingConstants.RIGHT);
		i_XLNum.setHorizontalAlignment(SwingConstants.RIGHT);
		i_XXLNum.setHorizontalAlignment(SwingConstants.RIGHT);
		i_flaw.setHorizontalAlignment(SwingConstants.RIGHT);

		i_note = new JTextArea();
		i_note.setLineWrap(true);
		i_note.setFont(new Font("Dialog", Font.PLAIN, 16));

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(i_note);
	}

	public Inventory getInventory(){
		Inventory i = new Inventory(i_name.getText(),i_positionId.getSelectedItem().toString(),i_unit.getSelectedItem().toString(),
				i_price.getText(), i_sampleNum.getText(), i_XXSNum.getText(), i_XSNum.getText(), i_SNum.getText(), i_MNum.getText(),
				i_LNum.getText(), i_XLNum.getText(), i_XXLNum.getText(), i_flaw.getText(),i_note.getText());

		return i;
	}


	public JTextField getI_name() {
		return i_name;
	}

	public void cleanInput() {
		i_name.setText("");
		i_positionId.setSelectedItem("");
		i_sampleNum.setText("");
		i_XXSNum.setText("");
		i_XSNum.setText("");
		i_SNum.setText("");
		i_MNum.setText("");
		i_LNum.setText("");
		i_XLNum.setText("");
		i_XXLNum.setText("");
		i_flaw.setText("");
		i_price.setText("");
		i_note.setText("");
	}

	public void setInput(Inventory info) {
		i_name.setText(info.getName());
		i_positionId.setSelectedItem(info.getPositionId());
		i_sampleNum.setText(info.getSampleNum().toString());
		i_XXSNum.setText(info.getxXSNum().toString());
		i_XSNum.setText(info.getxSNum().toString());
		i_SNum.setText(info.getsNum().toString());
		i_MNum.setText(info.getmNum().toString());
		i_LNum.setText(info.getlNum().toString());
		i_XLNum.setText(info.getxLNum().toString());
		i_XXLNum.setText(info.getxXLNum().toString());
		i_flaw.setText(info.getFlaw().toString());
		i_price.setText(info.getPrice().toString());
		i_note.setText(info.getNote());
	}

	public boolean confirm() {
		JTextField[] mustChecked = {i_sampleNum,i_XXSNum,i_XSNum,i_SNum,i_MNum,i_LNum,i_XLNum,i_XXLNum,i_flaw,i_price};

		for (int i = 0; i < size.length; i++) {

			if(mustChecked[i].getText().length()==0 && i!=9) {
				mustChecked[i].setText("0");
			}

			if(!Functions.isNumberic(mustChecked[i].getText(),true,false)) {
				JOptionPane.showMessageDialog(null,size[i] + "：錯誤");
				return false;
			}

		}
		return true;

	}

	private void initLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(86)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(i_name)
					.addGap(122))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(86)
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(i_positionId, 0, 160, Short.MAX_VALUE)
					.addGap(122))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_XXSNum, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(lblNewLabel_3_5_1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_MNum, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(lblNewLabel_3_5_4, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_XXLNum, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(44))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addComponent(lblNewLabel_3_4, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_XSNum, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_sampleNum, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(lblNewLabel_3_5_3, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_XLNum, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(44))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addComponent(lblNewLabel_3_5, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_SNum, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(lblNewLabel_3_5_5, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_flaw, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(lblNewLabel_3_5_2, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
					.addComponent(i_LNum, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
					.addGap(44))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(86)
					.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(i_unit, 0, 160, Short.MAX_VALUE)
					.addGap(122))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(86)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3_3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(i_price)))
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
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(i_positionId))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(i_XXSNum, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_3_5_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(i_MNum, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_3_5_4, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(i_XXLNum, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addGap(3)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3_4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(i_XSNum, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(i_sampleNum, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNewLabel_3_5_3, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(i_XLNum, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addGap(2)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(lblNewLabel_3_5, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(i_SNum, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_3_5_5, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(i_flaw, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_3_5_2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(i_LNum, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(lblNewLabel_3_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(5))
						.addComponent(i_unit))
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_3_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(3))
						.addComponent(i_price, GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(58)
							.addComponent(lblNewLabel_3_3, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
							.addGap(42))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(23)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
							.addContainerGap())))
		);
		setLayout(groupLayout);
	}
}
