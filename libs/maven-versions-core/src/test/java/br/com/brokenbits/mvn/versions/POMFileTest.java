package br.com.brokenbits.mvn.versions;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import br.com.brokenbits.mvn.versions.util.FileUtils;

public class POMFileTest {
	
	private static final File TEST_DIR = new File("tmp");
			
	private static final File SRC_FILE = new File("pom.xml");
	
	private static final File TEST_FILE = new File(TEST_DIR, "pom.xml");
	
	private void copyOriginalPOM() throws Exception {
		TEST_DIR.mkdirs();
		FileUtils.copy(SRC_FILE, TEST_FILE);
	}

	@Test
	public void testPOMFileString() throws Exception {
		POMFile pom;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE.toString());
		assertNotNull(pom);
		assertEquals(TEST_FILE, pom.getFile());
	}
	
	@Test
	public void testPOMFileFile() throws Exception {
		POMFile pom;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		assertNotNull(pom);
		assertEquals(TEST_FILE, pom.getFile());
	}
	
	@Test
	public void testSave() throws Exception {
		POMFile pom;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		TEST_FILE.delete();
		pom.save();
		assertTrue(TEST_FILE.exists());
	}
	
	@Test
	public void testSaveFile() throws Exception {
		POMFile pom;
		File f;
		
		copyOriginalPOM();
		pom = new POMFile(TEST_FILE);
		
		f = new File(TEST_DIR, "pom2.xml");
		f.delete();
		
		pom.save(f);
		assertTrue(TEST_FILE.exists());
		assertTrue(f.exists());
	}	
}
