package org.roy.homepage.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.roy.customerData.view.CustomerData;
import org.roy.customerData.view.CustomizedTable;
import org.roy.customerData.view.ShoppingDetails;
import org.roy.dailySaleSheet.view.DailySaleTable;
import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.menu.Menu;
import org.roy.homepage.menu.MenuAction;
import org.roy.inventory.view.InventoryData;
import org.roy.pettyCash.view.PettyCashDetail;
import org.roy.settings.GlobalColor;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;

/**
 *
 * @author Raven
 */
public class MainForm extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private JPanel panelBody;
	private JButton menuButton;

	public MainForm() {
		init();
	}

	public JPanel getPanelBody() {
		return panelBody;
	}

	private void init() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new MainFormLayout());
		menu = new Menu();
		panelBody = new JPanel(new BorderLayout());
		initMenuArrowIcon();
		GlobalColor.updateMenuUiColor(menuButton,
				"" + "background:" + GlobalColor.selectedColor + ";" + "arc:999;" + "focusWidth:0;" + "borderWidth:0");
//        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
//                + "background:"+GlobalColor.selectedColor+";"
//                + "arc:999;"
//                + "focusWidth:0;"
//                + "borderWidth:0");
		menuButton.addActionListener((ActionEvent e) -> {
			setMenuFull(!menu.isMenuFull());
		});
		initMenuEvent();
		setLayer(menuButton, JLayeredPane.POPUP_LAYER);
		add(menuButton);
		add(menu);
		add(panelBody);
	}

	@Override
	public void applyComponentOrientation(ComponentOrientation o) {
		super.applyComponentOrientation(o);
		initMenuArrowIcon();
	}

	private void initMenuArrowIcon() {
		if (menuButton == null) {
			menuButton = new JButton();
		}
		String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
		menuButton.setIcon(new FlatSVGIcon("org/roy/icon/svg/" + icon, 0.8f));
	}

	private void initMenuEvent() {
		menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
			// Application.mainForm.showForm(new DefaultForm("Form : " + index + " " +
			// subIndex));
			switch (index) {
			case 0 -> {//首頁
				showForm(GlamChamber.homePage);
			}

			case 1 -> {//客資
				switch (subIndex) {
				case 1 -> {
					if (GlamChamber.customer_data == null) {
						GlamChamber.customer_data = new CustomerData();
					}
					showForm(GlamChamber.customer_data);
				}
				case 2 -> {
					if (GlamChamber.shopping_details == null) {
						GlamChamber.shopping_details = new ShoppingDetails();
					}
					showForm(GlamChamber.shopping_details);
				}
				case 3 -> {
					if (GlamChamber.customized_table == null) {
						GlamChamber.customized_table = new CustomizedTable();
					}
					showForm(GlamChamber.customized_table);
				}
				}
			}
			case 2 -> {//庫存
				if (GlamChamber.inventoryData == null) {
					GlamChamber.inventoryData = new InventoryData();
				}
				showForm(GlamChamber.inventoryData);
			}
			case 3 -> {//銷售
				if (GlamChamber.dailySaleTable == null) {
					GlamChamber.dailySaleTable = new DailySaleTable();
				}
				showForm(GlamChamber.dailySaleTable);
			}
			case 4 -> {//銷售
				if (GlamChamber.pettyCashDetail == null) {
					GlamChamber.pettyCashDetail = new PettyCashDetail();
				}
				showForm(GlamChamber.pettyCashDetail);
			}
			case 5 -> {//歷史紀錄
				if(GlamChamber.history_Records == null) {
					GlamChamber.history_Records = new HistoryRecords();
				}
				showForm(GlamChamber.history_Records);
			}
			default -> action.cancel();
			}

		});
	}

	private void setMenuFull(boolean full) {
		String icon;
		if (getComponentOrientation().isLeftToRight()) {
			icon = (full) ? "menu_left.svg" : "menu_right.svg";
		} else {
			icon = (full) ? "menu_right.svg" : "menu_left.svg";
		}
		menuButton.setIcon(new FlatSVGIcon("org/roy/icon/svg/" + icon, 0.8f));
		menu.setMenuFull(full);
		revalidate();
	}

	public void hideMenu() {
		menu.hideMenuItem();
	}

	public void showForm(Component component) {
		panelBody.removeAll();
		panelBody.add(component);
		panelBody.repaint();
		panelBody.revalidate();
		FlatLaf.updateUI();
	}

	public void setSelectedMenu(int index, int subIndex) {
		menu.setSelectedMenu(index, subIndex);
	}

	public void updateAppearence() {
		menu.revalidate();
		menuButton.revalidate();
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public JButton getMenuButton() {
		return menuButton;
	}

	public void setMenuButton(JButton menuButton) {
		this.menuButton = menuButton;
	}

	private class MainFormLayout implements LayoutManager {

		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			synchronized (parent.getTreeLock()) {
				return new Dimension(5, 5);
			}
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			synchronized (parent.getTreeLock()) {
				return new Dimension(0, 0);
			}
		}

		@Override
		public void layoutContainer(Container parent) {
			synchronized (parent.getTreeLock()) {
				boolean ltr = parent.getComponentOrientation().isLeftToRight();
				Insets insets = UIScale.scale(parent.getInsets());
				int x = insets.left;
				int y = insets.top;
				int width = parent.getWidth() - (insets.left + insets.right);
				int height = parent.getHeight() - (insets.top + insets.bottom);
				int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
				int menuX = ltr ? x : x + width - menuWidth;
				menu.setBounds(menuX, y, menuWidth, height);
				int menuButtonWidth = menuButton.getPreferredSize().width;
				int menuButtonHeight = menuButton.getPreferredSize().height;
				int menubX;
				if (ltr) {
					menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
				} else {
					menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
				}
				menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
				int gap = UIScale.scale(5);
				int bodyWidth = width - menuWidth - gap;
				int bodyHeight = height;
				int bodyx = ltr ? (x + menuWidth + gap) : x;
				int bodyy = y;
				panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
			}
		}
	}
}
