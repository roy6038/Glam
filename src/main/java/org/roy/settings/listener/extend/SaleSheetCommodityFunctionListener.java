package org.roy.settings.listener.extend;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.roy.bean.Commodity;
import org.roy.dailySaleSheet.viewFunction.SecondStep;
import org.roy.settings.Functions;
import org.roy.settings.PopUp;

public class SaleSheetCommodityFunctionListener extends PopUp<JComboBox<Integer>>{

	private JMenuItem menuSwap;
	private JComboBox<Integer> t;
	private List<Commodity> co;
	private SecondStep secondStep;

	public SaleSheetCommodityFunctionListener(JComboBox<Integer> t,List<Commodity> co, SecondStep secondStep) {
		super(t);
		this.t = t;
		this.co = co;
		this.secondStep = secondStep;
		jm.removeAll();
		menuSwap = new JMenuItem("交換");
		menuSwap.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		jm.add(menuSwap);
		jm.addSeparator();
		jm.add(menuDelete);
		menuSwap.addActionListener(this);
		setpopupsize(jm,120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(menuSwap)) {
			if(co.size()==1) {
				JOptionPane.showMessageDialog(null,"商品總數為1時無法交換");
				return;
			}
	        JComboBox<Integer> combo = new JComboBox<>(new DefaultComboBoxModel<>(IntStream.rangeClosed(1, co.size()).filter(i->i!=(int)t.getSelectedItem()).boxed().toArray(Integer[]::new)));
			menuSwap.setEnabled(false);
			menuDelete.setEnabled(false);
			int con = JOptionPane.showOptionDialog(null,combo, "請選擇要交換的編號", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, Functions.OPTIONS,Functions.OPTIONS[1]);
			if(con ==1) {
				secondStep.setCommodityInfo(t.getSelectedIndex());
				Collections.swap(co, t.getSelectedIndex(), ((int)combo.getSelectedItem())-1);
				secondStep.setInput(co.get(t.getSelectedIndex()));
			}
			menuSwap.setEnabled(true);
			menuDelete.setEnabled(true);

		}else if(e.getSource().equals(menuDelete)) {
			if(co.size()==1) {
				JOptionPane.showMessageDialog(null,"商品總數為1時無法刪除");
				return;
			}
			int removeLocate = t.getSelectedIndex();
			secondStep.setCommodityInfo(removeLocate);
			co.remove(removeLocate);
			secondStep.setTotalCommodityNum(co.size());
			secondStep.setCommodityIndexComboBox();
			if(co.size()==removeLocate) {
				secondStep.setInput(co.get(removeLocate-1));
				t.setSelectedIndex(removeLocate-1);
			}else {
				secondStep.setInput(co.get(removeLocate));
				t.setSelectedIndex(removeLocate);
			}
			secondStep.setcurrentCommodityNumber(t.getSelectedIndex()+1);
			secondStep.buttonEnable();
		}
	}

	public void setCo(List<Commodity> co) {
		this.co = co;
	}


}
