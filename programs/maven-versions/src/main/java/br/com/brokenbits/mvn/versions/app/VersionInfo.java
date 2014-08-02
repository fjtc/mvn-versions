package br.com.brokenbits.mvn.versions.app;

import java.util.ResourceBundle;

public class VersionInfo {

	private static String version;
	
	public static synchronized String getVersion() {
		
		if (version == null) {
			ResourceBundle res = ResourceBundle.getBundle("br.com.brokenbits.mvn.versions.app.VersionInfo");
			version = res.getString("version");
		}
		return version;
	}
}
