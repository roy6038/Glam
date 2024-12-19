package org.roy.customerData.function;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportDataGender extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public ImportDataGender() {
		setTitle("請選擇性別");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(new Rectangle((int) (screensize.getWidth()/3),(int) (screensize.getHeight()/4)));
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		setVisible(true);

		setContentPane(contentPane);

		JRadioButton rdbtnFemale = new JRadioButton("女");
		rdbtnFemale.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnFemale.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		buttonGroup.add(rdbtnFemale);

		JRadioButton rdbtnMale = new JRadioButton("男");
		rdbtnMale.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnMale.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		buttonGroup.add(rdbtnMale);

		JCheckBox checkBoxUnknown = new JCheckBox("不詳");
		checkBoxUnknown.setHorizontalAlignment(SwingConstants.CENTER);
		checkBoxUnknown.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		rdbtnFemale.setSelected(true);

		JButton btnConfirm = new JButton("確定");
		btnConfirm.addActionListener(e->{
			buttonGroup.getElements().asIterator().forEachRemaining(s->{
				if(s.isSelected()) {
					importCustomerFromExcel(s.getText(),checkBoxUnknown.isSelected());
				}
			});
			dispose();
		});
		btnConfirm.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		JButton btnCancel = new JButton("取消");
		btnCancel.addActionListener(e->dispose());
		btnCancel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		JLabel lblNewLabel = new JLabel("生日：");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));

		JLabel lblNewLabel_1 = new JLabel("性別：");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(93)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(99)
									.addComponent(rdbtnFemale, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)))
							.addGap(12)
							.addComponent(rdbtnMale, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
							.addGap(50))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(checkBoxUnknown, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
							.addGap(64))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnConfirm, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
							.addGap(93)
							.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
					.addGap(94))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(rdbtnFemale, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(rdbtnMale, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
						.addComponent(checkBoxUnknown, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
					.addGap(16)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(41))
		);
		contentPane.setLayout(gl_contentPane);
	}

	public void importCustomerFromExcel(String gender,boolean isUnknown) {
		JFileChooser jc = new JFileChooser();
		jc.setCurrentDirectory(new File(System.getProperty("user.home")));
		jc.setMultiSelectionEnabled(true);
		jc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jc.setPreferredSize(new Dimension(800, 600));
		jc.setFont(new Font("Dialog", Font.BOLD, 16));
		jc.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter fnef = new FileNameExtensionFilter("客戶資料和訂製表，excel和圖片檔", "xlsx", "jpg", "jpeg",
				"png");
		jc.addChoosableFileFilter(fnef);

		int checkInput = jc.showOpenDialog(null);
		if (checkInput == JFileChooser.APPROVE_OPTION) {
			new WorkerLoadFile(jc.getSelectedFiles(),gender,isUnknown);
		}
	}
}
