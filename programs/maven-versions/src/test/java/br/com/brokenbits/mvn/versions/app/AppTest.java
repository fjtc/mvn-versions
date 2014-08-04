package br.com.brokenbits.mvn.versions.app;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class AppTest {

	public static final File SRC_DIR = new File("sample");
	public static final File TMP_DIR = new File("tmp");
	
	public void resetTemp() throws IOException {
		
		FileUtils.deleteDirectory(TMP_DIR);
		FileUtils.copyDirectory(SRC_DIR, TMP_DIR);
	}	
	
	@Test
	public void testHelp() throws Exception {
		App a;
		
		a = new App();
		assertEquals(App.RET_HELP, a.run(new String[]{}));
		
		a = new App();
		assertEquals(App.RET_HELP, a.run(new String[]{"-h"}));

		a = new App();
		assertEquals(App.RET_HELP, a.run(new String[]{"-help"}));
		
		a = new App();
		assertEquals(App.RET_HELP, a.run(new String[]{"-help", "--", "pom.xml"}));		
	}
	
	
	@Test
	public void testUpdateDeps() throws Exception {
		App a;
		
		// Reset the temporary directory
		resetTemp();
			
		a = new App();
		assertEquals(App.RET_SUCCESS, a.run(new String[]{
				"-snapshot",
				"-ro",
				(new File(TMP_DIR, "pom-ro.xml")).getPath(),
				"-minor",
				(new File(TMP_DIR, "pom1.xml")).getPath(),
				(new File(TMP_DIR, "pom2.xml")).getPath(),
				(new File(TMP_DIR, "pom3.xml")).getPath()}));
	}

}
