package org.roy.settings.listener.extend;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.roy.historyRecords.view.HistoryRecords;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.PopUp;
import org.roy.settings.Preferences;

public class CustomizedSheetTableWindowListener extends PopUp<JTable>{
	private JTable table;

	public CustomizedSheetTableWindowListener(JTable table) {
		super(table);
		this.table = table;
		jm.removeAll();
		jm.add(menuCheck);
		jm.addSeparator();
		jm.add(menuDelete);
		setpopupsize(jm,120);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(menuCheck)) {
			checkPhotoFullScreen();
		}else if(e.getSource().equals(menuDelete)) {
			int con = Functions.showConfirmDialog("確定刪除？","刪除訂製表");
			if(con==1) {
				String key = GlamChamber.customized_table.getKey();
				String removedPic = table.getColumnName(0);
				List<String> imageKey = DataFile.customer.get(key).getImageKey();
				imageKey.remove(removedPic);
//				System.out.println(DataFile.customer.get(key).getImageKey());
				if(imageKey.size()==0) {
					DataFile.customer.get(key).setImageKey(null);
				}
				GlamChamber.customized_table.getImage().remove(table.getColumnName(0));
				new File(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)+File.separator+removedPic).delete();
				HistoryRecords.addRecords(new ArrayList<>(Arrays.asList("訂製表",key,"刪除","刪除訂製表："+removedPic+"。","","")));
//				System.out.println(Application.customized_table.getImage().size());
				GlamChamber.customized_table.removeAllPics();
				if(GlamChamber.customized_table.getImage().size() == 0) {
					GlamChamber.customized_table.setNoCustomizedSheet();
				}else {
					switch(GlamChamber.customized_table.getImage().size()%3) {
					case 1,2->GlamChamber.customized_table.setPage(GlamChamber.customized_table.getImage().size()/3+1);
					case 0->GlamChamber.customized_table.setPage(GlamChamber.customized_table.getImage().size()/3);
					}
					GlamChamber.customized_table.setLabelPage("《第" + GlamChamber.customized_table.getPage() + "頁》");
				}
				GlamChamber.customized_table.decidePicTable();
				GlamChamber.customized_table.pageButtonEnable();
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2 && !e.isConsumed()) {
			checkPhotoFullScreen();
		}

	}

	public void checkPhotoFullScreen() {
		try {
			Desktop.getDesktop().open(new File(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)+File.separator+table.getColumnName(0)));
		} catch (IllegalArgumentException e1) {
			JOptionPane.showMessageDialog(null,"找不到訂製表");
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
