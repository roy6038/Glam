package org.roy.homepage.application;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.roy.customerData.view.CustomerData;
import org.roy.customerData.view.CustomizedTable;
import org.roy.customerData.view.ShoppingDetails;
import org.roy.dailySaleSheet.view.DailySaleTable;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.HomePage;
import org.roy.inventory.view.InventoryData;
import org.roy.pettyCash.view.PettyCashDetail;
import org.roy.settings.Const;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.Preferences;

import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

/**
 *
 * @author Raven
 */
public class GlamChamber extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	public static GlamChamber glamChamber;
	public static CustomerData customer_data;
	public static ShoppingDetails shopping_details;
	public static CustomizedTable customized_table;
	public static InventoryData inventoryData;
	public static HistoryRecords history_Records;
	public static DailySaleTable dailySaleTable;
	public static HomePage homePage;
	public static PettyCashDetail pettyCashDetail;
	public static MainForm mainForm;
	public static LoginForm loginForm;
	public static boolean isDataSaved = true;
	public static String userName;
	private static MenuBar menuBar;


	public GlamChamber() {
		initComponents();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!isDataSaved) {
					if(Functions.showConfirmDialog("資料尚未儲存，是否儲存？","資料未儲存") == 1) {
						try {
							DataFile.doWriteData();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		setSize(Const.SCREENSIZE);
		setLocationRelativeTo(null);
		mainForm = new MainForm();
		setSelectedMenu(0, 0);
		loginForm = new LoginForm();
		setContentPane(loginForm);
		menuBar.getMenu_system().setEnabled(false);
		menuBar.getMenu_edit().setEnabled(false);
//		mainForm.setSelectedMenu(0, 0);
	}

    public static void login() {
        FlatAnimatedLafChange.showSnapshot();
        glamChamber.setContentPane(mainForm);
        glamChamber.applyComponentOrientation(glamChamber.getComponentOrientation());
        mainForm.hideMenu();
		menuBar.getMenu_system().setEnabled(true);
		menuBar.getMenu_edit().setEnabled(true);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public static void logOut() {
    	FlatAnimatedLafChange.showSnapshot();
    	glamChamber.setContentPane(loginForm);
    	loginForm.txtPassWordRequestFocus();
		menuBar.getMenu_system().setEnabled(false);
		menuBar.getMenu_edit().setEnabled(false);
		FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

	public static void setSelectedMenu(int index, int subIndex) {
		mainForm.setSelectedMenu(index, subIndex);
	}

	public static void dataNotSaved() {
		isDataSaved = false;
	}

	private void initComponents() {

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		menuBar = new MenuBar(this);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 719, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 521, Short.MAX_VALUE));

		pack();
	}

	public static void main(String args[]) {

		EventQueue.invokeLater(new Runnable() {


			@Override
			public void run() {
				long s = System.currentTimeMillis();
				try {
					UIManager.setLookAndFeel(DataFile.preferences.getConfig(Preferences.LAST_SELECTED_THEME));
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				ToolTipManager.sharedInstance().setDismissDelay(100000);
				UIManager.put("Table.intercellSpacing",new Dimension(1,1));
				UIManager.put("Table.showHorizontalLines", true);
				UIManager.put("Table.showVerticalLines", true);
				UIManager.put("Table.gridColor", UIManager.getColor("Table.dropLineColor"));

				homePage = new HomePage();
				glamChamber = new GlamChamber();
				glamChamber.setVisible(true);
				System.out.println("開啟時間："+(System.currentTimeMillis()-s));
			}
		});
	}

}
