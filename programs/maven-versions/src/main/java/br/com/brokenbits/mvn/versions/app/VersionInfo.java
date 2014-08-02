package br.com.brokenbits.mvn.versions.app;

import java.util.ResourceBundle;

public class VersionInfo {

	private static String version;
	
	private static String finalName;
	
	private static String getString(String key) {
		ResourceBundle res;
		
		res = ResourceBundle.getBundle("br.com.brokenbits.mvn.versions.app.VersionInfo");
		return res.getString(key);
	}
	
	public static synchronized String getVersion() {
		
		if (version == null) {
			version = getString("version");
		}
		return version;
	}
	
	public static synchronized String getFinalName() {
		
		if (finalName == null) {
			finalName = getString("finalName");
		}
		return finalName;
	}
	
}
