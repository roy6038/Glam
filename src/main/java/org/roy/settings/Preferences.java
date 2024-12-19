package org.roy.settings;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

public class Preferences {

	public static final String DATA_FOLDER ="datafolder";
	public static final String CUSTOMER_DATA_EXPORT_FOLDER ="customerdataexportfolder";
	public static final String SALE_SHEET_EXPORT_FOLDER ="salesheetexportfolder";
	public static final String INPUT_OUTPUT_SHEET_EXPORT_FOLDER ="inputoutputsheetexportfolder";
	public static final String CUSTOMIZED_SHEET_IMG_FOLDER ="customizedsheet-img";
	public static final String STENCIL_FOLDER ="stencil";
	public static final String PREFENENCE_FILE = "config.pref";
	public static final String LAST_SELECTED_THEME ="lastselectedtheme";

	private Properties properties;
	private String filePath;



	public Preferences(String filePath) {
		super();
		this.setFilePath(filePath);
		if(filePath!=null) {
			load();
//			properties.entrySet().forEach(entry->System.out.println(entry.getKey().toString()+"=="+entry.getValue().toString()));
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties p) {
		this.properties = p;
	}

	public void setConfig(String configKey,String newValue) {
		getProperties().setProperty(configKey, newValue);
	}

	public String getConfig(String configKey) {
		return getProperties().getProperty(configKey);
	}

	public void loadDefaults() {

		properties  = new Properties();
		properties.setProperty(DATA_FOLDER,"Data");
		properties.setProperty(CUSTOMIZED_SHEET_IMG_FOLDER,"CustomizedSheet-Img");
		properties.setProperty(CUSTOMER_DATA_EXPORT_FOLDER,"Export/CustomerData");
		properties.setProperty(SALE_SHEET_EXPORT_FOLDER,"Export/SaleSheet");
		properties.setProperty(INPUT_OUTPUT_SHEET_EXPORT_FOLDER,"Export/InputOutputSheet");
		properties.setProperty(STENCIL_FOLDER,"Stencils");
		properties.setProperty(LAST_SELECTED_THEME,"com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme");


	}

	public void load() {

		File file = new File(getFilePath());
		if(getFilePath()==null || !(file.exists())) {
			loadDefaults();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		properties = new Properties();
		try (Reader reader = new FileReader(getFilePath())){
			properties.load(reader);
			if(properties.isEmpty()) {
				loadDefaults();
			}
		} catch (IOException e) {
			loadDefaults();
			e.printStackTrace();
		}

	}

	public void save() {
		try (Writer writer = new FileWriter(getFilePath())){
			getProperties().store(writer,"User Config");
			if(getProperties().isEmpty()) {
				loadDefaults();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
