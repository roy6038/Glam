package org.roy.settings;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.roy.customerData.view.ShoppingDetails;
import org.roy.homepage.application.GlamChamber;
import org.roy.homepage.menu.MenuItem;
import org.roy.homepage.menu.PopupSubmenu;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;


public class Functions {

	private static final String DIGIT_CAN_BE_ZERO = "^(0|[1-9][0-9]*)$";
	private static final String DIGIT_CANT_BE_ZERO = "^\\+?[1-9][0-9]*$";
	public static final String[] OPTIONS = {"取消","確定"};

	private Functions() {}

	public static void defaultJFrameSettings(JFrame frame,double width,double height) {
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBounds(new Rectangle((int) (Const.SCREENSIZE.getWidth()/width),(int) (Const.SCREENSIZE.getHeight()/height)));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public static int showConfirmDialog(String message,String title) {
		return JOptionPane.showOptionDialog(null,message,title,JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE, null,OPTIONS,OPTIONS[1]);
	}

//	public static int showInputDialog(String title,String message,int rows,int columns,Component... components) {
//
//		JPanel panel = new JPanel(new GridLayout(rows,columns));
//		JTextArea area = new JTextArea(message);
//		area.setFont(new Font("Lucida Grande", Font.BOLD, 14));
//		area.setFocusable(false);
//		area.setEditable(false);
//		area.setOpaque(false);
//		if(message.length()!=0) {
//			panel.add(area);
//		}
//		if(components!= null) {
//			for(Component c : components) {
//				c.setSize(100,30);
//				panel.add(c);
//			}
//		}
//		return JOptionPane.showOptionDialog(null,panel, title,JOptionPane.YES_NO_OPTION,-1, null, OPTIONS,-1);
//	}

	public static void setShopDetails(String key) {

		if(GlamChamber.shopping_details==null) {
			GlamChamber.shopping_details = new ShoppingDetails();
		}
		GlamChamber.shopping_details.setKey(key);
		Map<String, List<List<String>>> shoppingDetailData = DataFile.customer.get(key).getShoppingDetailData();
		if(shoppingDetailData!=null) {
			GlamChamber.shopping_details.setComboBoxModel(shoppingDetailData.keySet());
			GlamChamber.shopping_details.setCurrentShoppingDetail(shoppingDetailData);
			GlamChamber.shopping_details.setTotalConsume();
		}
		if(GlamChamber.shopping_details.comboBox.getItemCount() != 0) {
			GlamChamber.shopping_details.comboBox.setSelectedIndex(0);
		}else {
			DefaultTableModel model=(DefaultTableModel)GlamChamber.shopping_details.getTable_details().getModel();
			model.setColumnCount(0);
			model.setRowCount(0);
		}
		GlamChamber.shopping_details.setCusName(key);
		GlamChamber.shopping_details.buttonEnable();
	}

	public static boolean isNumberic(String str,boolean canBeZero,boolean canBeEmpty) {

		if(canBeEmpty && str.length() == 0) {
			return true;
		}else {
			if(canBeZero) {
				return str.matches(DIGIT_CAN_BE_ZERO);
			}else {
				return str.matches(DIGIT_CANT_BE_ZERO);
			}
		}
	}

	public static boolean containsIgnoreCase(String str, String searchStr)     {
	    if(str == null || searchStr == null) {
			return false;
		}

	    final int length = searchStr.length();
	    if (length == 0) {
			return true;
		}

	    for (int i = str.length() - length; i >= 0; i--) {
	        if (str.regionMatches(true, i, searchStr, 0, length)) {
				return true;
			}
	    }
	    return false;
	}

	public static void updateAllUI(String laf) {
		FlatAnimatedLafChange.showSnapshot();
		try {
			UIManager.setLookAndFeel(laf);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		UIManager.put("Table.gridColor", UIManager.getColor("Table.dropLineColor"));
		 FlatLaf.updateUI();
		 if(GlamChamber.mainForm==null) {
			return;
		}

		GlobalColor.updateColorWhenChangingLAndF();

		GlobalColor.updateMenuUiColor(GlamChamber.mainForm.getMenu().getHeader(), ""
				+ "foreground:"+GlobalColor.menuItemForeground);
		GlobalColor.updateMenuUiColor(GlamChamber.mainForm.getMenu(),  ""
		        + "border:20,2,2,2;"
		        + "background:"+GlobalColor.menuBackground+";"
		        + "arc:10");
		GlobalColor.updateMenuUiColor(GlamChamber.mainForm.getMenuButton(),""
		        + "background:"+GlobalColor.selectedColor+";"
		        + "arc:999;"
		        + "focusWidth:0;"
		        + "borderWidth:0");
		GlobalColor.updateMenuUiColor(GlamChamber.mainForm.getMenu().getPanelMenu(),  ""
		        + "border:5,5,5,5;"
		        + "background:"+GlobalColor.menuBackground);

		for(Component c :GlamChamber.mainForm.getMenu().getPanelMenu().getComponents()) {
			GlobalColor.updateMenuUiColor(((MenuItem)c).getPopup(), ""
		            + "background:"+GlobalColor.menuBackground+";");
			GlobalColor.updateMenuUiColor((MenuItem)c, ""
		            + "border:0,3,0,3;"
		            + "background:"+GlobalColor.menuBackground+";"
		            + "foreground:"+GlobalColor.menuItemForeground);
			for(Component ct:((MenuItem)c).getComponents()) {
				GlobalColor.updateMenuUiColor((JButton) ct, ""
		                + "background:"+GlobalColor.menuBackground+";"
		                + "foreground:"+GlobalColor.menuItemForeground+";"
		                + "selectedBackground:"+GlobalColor.selectedColor+";"
		                + "selectedForeground:"+GlobalColor.menuItemForeground+";"
		                + "borderWidth:0;"
		                + "focusWidth:0;"
		                + "innerFocusWidth:0;"
		                + "arc:10;"
		                + "iconTextGap:10;"
		                + "margin:3,11,3,11");
			}
			for(Component ct:((MenuItem)c).getPopup().getComponents()) {
				GlobalColor.updateMenuUiColor((JButton) ct, ""
		                + "background:"+GlobalColor.menuBackground+";"
		                + "foreground:"+GlobalColor.menuItemForeground+";"
		                + "selectedBackground:"+GlobalColor.selectedColor+";"
		                + "selectedForeground:"+GlobalColor.menuItemForeground+";"
		                + "borderWidth:0;"
		                + "arc:10;"
		                + "focusWidth:0;"
		                + "iconTextGap:10;"
		                + "margin:5,11,5,11");
				GlobalColor.updateMenuUiColor(((PopupSubmenu)ct.getParent()).getPopup(), ""
		                + "background:"+GlobalColor.menuBackground+";"
		                + "borderColor:"+GlobalColor.menuBackground+";");
			}
		}
		GlamChamber.mainForm.getMenu().setHeaderIcon();
		GlamChamber.homePage.getCalendarCustom().setUIcolorWhenChangingTheme();
		FlatAnimatedLafChange.hideSnapshotWithAnimation();
	}

	 public static ImageIcon createAutoAdjustIcon(String b, boolean constrained) {
	        ImageIcon icon = new ImageIcon(b) {
	            private static final long serialVersionUID = 1L;

				@Override
	            public synchronized void paintIcon(java.awt.Component cmp, Graphics g, int x, int y) {
	                //初始化参数
	                Point startPoint = new Point(0, 0);//默認繪制起點
	                Dimension cmpSize = cmp.getSize();//獲取組件大小
	                Dimension imgSize = new Dimension(getIconWidth(), getIconHeight());//获取图像大小

	                //計算繪製起點和區域
	                if(constrained) {//等比例缩放
	                    //計算圖像寬高比例
	                    double ratio = 1.0*imgSize.width/imgSize.height;
	                    //計算等比例縮放後的區域大小
	                    imgSize.width = (int) Math.min(cmpSize.width, ratio*cmpSize.height);
	                    imgSize.height = (int) (imgSize.width/ratio);
	                    //計算繪製起點
	                    startPoint.x = (int)
	                            (cmp.getAlignmentX()*(cmpSize.width - imgSize.width));
	                    startPoint.y = (int)
	                            (cmp.getAlignmentY()*(cmpSize.height - imgSize.height));
	                } else {//完全填充
	                    imgSize = cmpSize;
	                }

	                //根據起點和區域大小進行繪製
	                if(getImageObserver() == null) {
	                    g.drawImage(getImage(), startPoint.x, startPoint.y,
	                            imgSize.width, imgSize.height, cmp);
	                 } else {
	                    g.drawImage(getImage(), startPoint.x, startPoint.y,
	                            imgSize.width, imgSize.height, getImageObserver());
	                 }
	            }
	        };
			return icon;
	    }

	@SuppressWarnings("unused")
	public static <T> void recycle(T t) {
//		String s = t==null?"null":t.getClass().getName();
		WeakReference<T> trash = new WeakReference<>(t);
		t = null;
		System.gc();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (trash.get()!=null) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        System.err.println(e);
//                    }
//                    System.gc();
//                    System.out.println("recycling");
//                    if(trash.get()==null) {
//                    	System.out.println(s+"已回收");
//                    }
//                }
//            }
//        }).start();
//		System.out.println(trash.get());
	}


}
