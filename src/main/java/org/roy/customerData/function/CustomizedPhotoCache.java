package org.roy.customerData.function;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import org.roy.customerData.view.CustomizedTable;
import org.roy.homepage.application.GlamChamber;
import org.roy.settings.DataFile;
import org.roy.settings.Functions;
import org.roy.settings.Preferences;

public class CustomizedPhotoCache extends SwingWorker<LinkedHashMap<String,ImageIcon>,Void>{
	private List<String> imageKey;
	private LinkedHashMap<String,ImageIcon> icons=null;
	private String key;

	public CustomizedPhotoCache() {
		super();
	}

	public CustomizedPhotoCache(List<String> imageKey,String key) {
		super();
		this.imageKey = imageKey;
		this.key = key;
	}

	public List<String> getImageKey() {
		return imageKey;
	}

	public void setImageKey(List<String> imageKey) {
		this.imageKey = imageKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	@Override
	protected LinkedHashMap<String, ImageIcon> doInBackground() throws Exception {
		if(imageKey!=null) {
			icons=new LinkedHashMap<>();
			imageKey.stream().forEach(str->icons.put(str,Functions.createAutoAdjustIcon(DataFile.preferences.getConfig(Preferences.CUSTOMIZED_SHEET_IMG_FOLDER)+File.separator+str,true)));
		}
		return icons;
	}

	@Override
	protected void done() {

		if(GlamChamber.customized_table == null) {
			GlamChamber.customized_table = new CustomizedTable();
		}
		if(!key.equals(GlamChamber.customized_table.getKey())) {
			GlamChamber.customized_table.releaseImageMemory();
			GlamChamber.customized_table.setKey(key);
		}
		try {
			Optional<LinkedHashMap<String, ImageIcon>> target = Optional.ofNullable(get());
			target.ifPresentOrElse(s->GlamChamber.customized_table.setImage(s),()->GlamChamber.customized_table.setImage(null));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		GlamChamber.customized_table.removeAllPics();
		if(GlamChamber.customized_table.getImage()!=null) {
			GlamChamber.customized_table.setPage(1);
			GlamChamber.customized_table.decidePicTable();
			GlamChamber.customized_table.setLabelPage("《第" + GlamChamber.customized_table.getPage() + "頁》");
		}else {
			GlamChamber.customized_table.setNoCustomizedSheet();
		}
		GlamChamber.customized_table.setCusName(key);
		GlamChamber.customized_table.pageButtonEnable();
		GlamChamber.customized_table.cusButtonEnable();
//		Functions.recycle(this);

	}



}
