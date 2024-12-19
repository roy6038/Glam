package org.roy.settings;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;



public class PopUp<T extends Component> implements ActionListener,MouseListener{
	protected int currentRow;
	protected JPopupMenu jm;
	protected JMenuItem menuCheck;
	protected JMenuItem menuUpdate;
	protected JMenuItem menuDelete;
	protected T t;

	public PopUp(T t) {
		this.t = t;
		jm=new JPopupMenu();

		menuCheck=new JMenuItem("查看");
		menuCheck.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		menuUpdate = new JMenuItem("更改");
		menuUpdate.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		menuDelete=new JMenuItem("刪除");
		menuDelete.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		menuCheck.addActionListener(this);
		menuUpdate.addActionListener(this);
		menuDelete.addActionListener(this);
		jm.add(menuCheck);
		jm.addSeparator();
		jm.add(menuUpdate);
		jm.addSeparator();
		jm.add(menuDelete);

		setpopupsize(jm,120);
		addPopup(t, jm);
		t.addMouseListener(this);
	}



	public void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});


	}

	public void setpopupsize(JPopupMenu jm,int width) {
//		System.out.println(Arrays.stream(jm.getComponents()).filter(menuitem->menuitem instanceof JMenuItem).count());
		jm.setPopupSize(new Dimension(width,(int) (Arrays.stream(jm.getComponents()).filter(menuitem->menuitem instanceof JMenuItem).count()*40)));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(t instanceof JTable) {
			Point point = e.getPoint();
			currentRow = ((JTable)t).rowAtPoint(point);
			((JTable)t).setRowSelectionInterval(currentRow,currentRow);
		}

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
