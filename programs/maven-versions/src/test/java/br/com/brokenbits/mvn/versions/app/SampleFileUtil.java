package br.com.brokenbits.mvn.versions.app;

import java.io.File;
import java.io.IOException;

public class SampleFileUtil {
	
	public static final File SRC_DIR = new File("sample");
	public static final File TMP_DIR = new File("tmp");
	
	
	public static void delAll(File dir, boolean recursive) throws IOException {
		
		for (File f: dir.listFiles()){
			if (f.isDirectory()) {
				if (recursive) {
					delAll(f, recursive);
				}
				f.delete();
			} else {
				f.delete();
			}
		}
	}

}
