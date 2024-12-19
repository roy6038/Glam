package org.roy.homepage.menu;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.roy.settings.GlobalColor;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.IntelliJTheme.ThemeLaf;
import com.formdev.flatlaf.util.UIScale;

/**
 *
 * @author Raven
 */
public class Menu extends JPanel {

    private static final long serialVersionUID = 1L;
	private final String menuItems[][] = {
    	{"首頁"},
        {"客資系統","顧客資料","購物明細","訂製表"},
        {"庫存系統"},
        {"日銷售表"},
        {"零用金"},
        {"歷史紀錄"},
    };

    public void s() {
    }

    public JLabel getHeader() {
		return header;
	}

	public JPanel getPanelMenu() {
		return panelMenu;
	}


	public boolean isMenuFull() {
        return menuFull;
    }

    public void setMenuFull(boolean menuFull) {
        this.menuFull = menuFull;
        if (menuFull) {
            header.setText(headerName);
            header.setHorizontalAlignment(getComponentOrientation().isLeftToRight() ? SwingConstants.LEFT : SwingConstants.RIGHT);
        } else {
            header.setText("");
            header.setHorizontalAlignment(SwingConstants.CENTER);
        }
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                ((MenuItem) com).setFull(menuFull);
            }
        }

    }

    private final List<MenuEvent> events = new ArrayList<>();
    private boolean menuFull = true;
    private final String headerName = "Glam Chamber";

    protected final boolean hideMenuTitleOnMinimum = true;
    protected final int menuTitleLeftInset = 5;
    protected final int menuTitleVgap = 5;
    protected final int menuMaxWidth = 250;
    protected final int menuMinWidth = 60;
    protected final int headerFullHgap = 5;

    public Menu() {
        init();
    }

    private void init() {
        setLayout(new MenuLayout());
        GlobalColor.updateMenuUiColor(this,  ""
                + "border:20,2,2,2;"
                + "background:"+GlobalColor.menuBackground+";"
                + "arc:10");
//        putClientProperty(FlatClientProperties.STYLE, ""
//                + "border:20,2,2,2;"
//                + "background:"+GlobalColor.menuBackground+";"
//                + "arc:10");
        header = new JLabel(headerName);
        header.setFont(new Font("sansserif", Font.BOLD, 16));
        setHeaderIcon();
        GlobalColor.updateMenuUiColor(header,  ""
                + "foreground:"+GlobalColor.menuItemForeground);
//        header.putClientProperty(FlatClientProperties.STYLE, ""
//                + "font:$Menu.header.font;"
//                + "foreground:"+GlobalColor.menuItemForeground);

        //  Menu
        scroll = new JScrollPane();
        panelMenu = new JPanel(new MenuItemLayout(this));
        GlobalColor.updateMenuUiColor(panelMenu,  ""
                + "border:5,5,5,5;"
                + "background:"+GlobalColor.menuBackground);
//        panelMenu.putClientProperty(FlatClientProperties.STYLE, ""
//                + "border:5,5,5,5;"
//                + "background:"+GlobalColor.menuBackground);

        scroll.setViewportView(panelMenu);
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:null");
        JScrollBar vscroll = scroll.getVerticalScrollBar();
        vscroll.setUnitIncrement(10);
//        vscroll.putClientProperty(FlatClientProperties.STYLE, ""
//                + "width:$Menu.scroll.width;"
//                + "trackInsets:$Menu.scroll.trackInsets;"
//                + "thumbInsets:$Menu.scroll.thumbInsets;"
//                + "background:$Menu.ScrollBar.background;"
//                + "thumb:$Menu.ScrollBar.thumb");
        createMenu();

        add(header);
        add(scroll);

    }

//    public void callUpdateFunction(JComponent j,String details) {
//    	GlobalColor.updateMenuUiColor(j, details);
//    }
    public void setHeaderIcon() {
        ((ThemeLaf)UIManager.getLookAndFeel()).isDark();
        header.setIcon(((ThemeLaf)UIManager.getLookAndFeel()).isDark()?
        		new ImageIcon(getClass().getResource("/org/roy/icon/png/logo.png")):
        		new ImageIcon(getClass().getResource("/org/roy/icon/png/logo-black.png")));
    }

    private void createMenu() {
        int index = 0;
        for (String[] menuItem2 : menuItems) {
            String menuName = menuItem2[0];
            if (menuName.startsWith("~") && menuName.endsWith("~")) {
                panelMenu.add(createTitle(menuName));
            } else {
                MenuItem menuItem = new MenuItem(this, menuItem2, index++, events);
                panelMenu.add(menuItem);
            }
        }
    }

    private JLabel createTitle(String title) {
        String menuName = title.substring(1, title.length() - 1);
        JLabel lbTitle = new JLabel(menuName);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$Menu.label.font;"
                + "foreground:$Menu.title.foreground");
        return lbTitle;
    }

    public void setSelectedMenu(int index, int subIndex) {
        runEvent(index, subIndex);
    }

    protected void setSelected(int index, int subIndex) {
        int size = panelMenu.getComponentCount();
        for (int i = 0; i < size; i++) {
            Component com = panelMenu.getComponent(i);
            if (com instanceof MenuItem) {
                MenuItem item = (MenuItem) com;
                if (item.getMenuIndex() == index) {
                    item.setSelectedIndex(subIndex);
                } else {
                    item.setSelectedIndex(-1);
                }
            }
        }
    }

    public void runEvent(int index, int subIndex) {
        MenuAction menuAction = new MenuAction();
        for (MenuEvent event : events) {
            event.menuSelected(index, subIndex, menuAction);
        }
        if (!menuAction.isCancel()) {
            setSelected(index, subIndex);
        }
    }

    public void addMenuEvent(MenuEvent event) {
        events.add(event);
    }

    public void hideMenuItem() {
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                ((MenuItem) com).hideMenuItem();
            }
        }
        revalidate();
    }

    public boolean isHideMenuTitleOnMinimum() {
        return hideMenuTitleOnMinimum;
    }

    public int getMenuTitleLeftInset() {
        return menuTitleLeftInset;
    }

    public int getMenuTitleVgap() {
        return menuTitleVgap;
    }

    public int getMenuMaxWidth() {
        return menuMaxWidth;
    }

    public int getMenuMinWidth() {
        return menuMinWidth;
    }

    private JLabel header;
    private JScrollPane scroll;
    private JPanel panelMenu;


    private class MenuLayout implements LayoutManager {

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
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int gap = UIScale.scale(5);
                int sheaderFullHgap = UIScale.scale(headerFullHgap);
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int iconWidth = width;
                int iconHeight = header.getPreferredSize().height;
                int hgap = menuFull ? sheaderFullHgap : 0;
                int accentColorHeight = 0;


                header.setBounds(x + hgap, y, iconWidth - (hgap * 2), iconHeight);


                int menux = x;
                int menuy = y + iconHeight + gap;
                int menuWidth = width;
                int menuHeight = height - (iconHeight + gap) -  (accentColorHeight);
                scroll.setBounds(menux, menuy, menuWidth, menuHeight);


            }
        }
    }
}
