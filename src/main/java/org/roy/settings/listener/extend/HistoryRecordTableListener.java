package org.roy.settings.listener.extend;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JTable;

import org.roy.homepage.application.EditWindow;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.PopUp;

public class HistoryRecordTableListener extends PopUp<JTable>{

	private JMenuItem menuUpdateNote;

	public HistoryRecordTableListener(JTable t) {
		super(t);
		jm.removeAll();
		menuUpdateNote = new JMenuItem("更改備註");
		menuUpdateNote.addActionListener(this);
		menuUpdateNote.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		jm.add(menuUpdateNote);
		setpopupsize(jm,120);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(menuUpdateNote)) {
			new EditWindow(GlamChamber.history_Records.getFilterRecords().get(currentRow).get(7), "historyRecordNote");
		}
	}



}
